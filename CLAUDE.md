# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Compile
mvn clean compile

# Run the application
mvn javafx:run

# Run the test suite
mvn test

# Build fat JAR (shade) + prepare target/app-input for packaging
mvn clean package

# Skip the build-number auto-increment (what CI does)
mvn clean package -DskipTests -Dskip.build.increment=true

# Build Linux portable archive (adds Linux + Windows JavaFX natives)
mvn clean install -Pportable
```

The project requires **Java 25 with `--enable-preview`** — already wired into `maven-compiler-plugin`,
`javafx-maven-plugin`, `maven-surefire-plugin` and the jpackage config in `pom.xml`.

`api-keys.properties` is gitignored but **must exist** at the repo root for map / AI features to work
(`ApiKeysConfig` reads it from the CWD first, then from the classpath). Copy
`api-keys.properties.template`. Every CI workflow generates a stub before building.

### Windows installer

Use `.\build-installer.ps1` — it runs `mvn clean package`, then `jpackage --type app-image`, then Inno
Setup (`installer.iss`) to produce `target/installer/EditeurPanovisu-Setup-X.Y.Z.exe`. It detects the
version by globbing the JAR name, so there is nothing to update inside it.

Do **not** use `mvn jpackage:jpackage`: the `jpackage-maven-plugin` block in `pom.xml` still hardcodes
`<mainJar>editeurPanovisu-3.3.2-SNAPSHOT.jar</mainJar>` and `<appVersion>2.0.0</appVersion>`, so it
fails on any current build.

### Build Number Auto-increment

Maven's `initialize` phase runs `increment-build.ps1` (profile `windows`) or `increment-build.sh`
(profile `unix`) — selected automatically by OS family. The script:
- Reads / increments `build.num` (currently ~3748)
- Rewrites `build.numero=` in `src/editeurpanovisu/i18n/PanoVisu.properties`
- Rewrites `Application.buildnumber=` in `src/project.properties`

Do not hand-edit `build.num` or those two generated entries. Suppress with `-Dskip.build.increment=true`.

### Release versioning

When bumping the version, update these 4 files — everything else resolves the version at runtime or by glob:

| File | Key |
|------|-----|
| `pom.xml` | `<version>` |
| `installer.iss` | `#define MyAppVersion` |
| `src/project.properties` | `project.version` (major.minor only — read at runtime for the About box) |
| `src/editeurpanovisu/i18n/PanoVisu.properties` | `# Version` comment line (cosmetic) |

`build-installer.ps1` and `create-linux-portable.sh` detect the JAR name via glob. The release workflow
reads the GitHub release body from `RELEASE_NOTES_vX.Y.Z.md`, so create that file before tagging.

## Project Layout Quirks

This is **not** a standard Maven layout. `pom.xml` sets `<sourceDirectory>src</sourceDirectory>`:

- Java sources live in `src/editeurpanovisu/**` (flat — there is no `src/main/java`); tests live in
  `src/test/java`, which the main compile excludes — see [Tests](#tests)
- Runtime resources are copied in from eight *root-level* directories declared as `<resources>`:
  `aide/`, `configPV/`, `css/`, `diaporama/`, `images/`, `pagesHTML/`, `templates/`, `theme/`, plus
  `doc/**/*.md` and the non-Java files under `src/`
- `panovisu/` (the JS viewer, see below) is **not** a Maven resource — `maven-antrun-plugin` copies it
  into `target/app-input/panovisu` during `package`
- `lib/jmapviewer-2.18.jar` is a `system`-scope dependency (not on Maven Central)

## Architecture

### Entry Points

- **`editeurpanovisu.Launcher`** — jpackage / shade main class; calls `Application.launch(EditeurPanovisu.class)`
- **`editeurpanovisu.EditeurPanovisu`** — the actual `javafx.application.Application` (~780 KB, ~15 000 lines)

### The two monoliths

Almost all behaviour lives in two files, and they communicate through **static global state**
(`EditeurPanovisu` alone declares ~385 static members; other classes `import static` its getters, e.g.
`getPanoramiquesProjet()`, `getiPanoActuel()`):

- **`EditeurPanovisu.java`** — lifecycle, `.pvu` project I/O, the whole HTML/XML export pipeline, menu/toolbar wiring
- **`GestionnaireInterfaceController.java`** (~740 KB) — panorama editing surface, hotspot placement, property panels

Despite the `*Controller` naming, the UI is built **programmatically**, not from FXML — only
`ModelsConfig.fxml` and `popUpAccueil.fxml` exist, and `FXMLLoader` is used in just two classes.

### Domain Model

| Class | Role |
|-------|------|
| `Panoramique.java` | A panoramic scene (sphere or cube); fixed 100-slot arrays for each hotspot kind |
| `HotSpot.java` | Navigation marker: spherical coords (lon/lat), animation type, custom icon / colour |
| `HotspotImage` / `HotspotDiaporama` / `HotspotHTML` | Other hotspot flavours, each with its own export writer |
| `Diaporama.java` | Slideshow model (image sequence, background, opacity) |
| `Plan.java` | Floor plan / interactive map overlay |

### `.pvu` project format

**Not XML and not JAXB** (there is no JAXB anywhere in the codebase). `EditeurPanovisu` builds one big
`String` and writes it UTF-8 through a `BufferedWriter` (save routine around
`EditeurPanovisu.java:4800-4940`). The format is line- and block-oriented:

```
[hotspot==>longitude:12.5;latitude:-3.2;image:foo.png;typeAnimation:pulse;...]
```

`:` separates key from value and `;` separates fields, so payload text is escaped as **`&dp`** (`:`),
**`&pv`** (`;`) and **`&nl`** (newline). Any new field must honour this on both the write and the parse
side, or previously saved projects break.

### Export pipeline (editor → published visit)

The editor is essentially a generator for the **`panovisu/` HTML5 viewer** (Three.js + jQuery,
`panovisu.js`), which is shipped alongside the app rather than compiled. `EditeurPanovisu` /
`EditeurHTML` emit, by string concatenation, into a temp directory:

- `xml/panovisu<i>.xml` — one scene descriptor per panorama (hotspots, map points, thumbnails), read by the viewer at runtime
- `panos/` — panorama tiles / cube faces plus `…Vignette.jpg` thumbnails
- `index.html` — page with inlined JS bootstrapping `panovisu.js`
- `.htaccess` — aggressive anti-recompression rules (mod_pagespeed, CloudFlare Polish/Mirage, mod_deflate
  all disabled), because CDN image "optimisation" visibly destroys panorama seams

The result is then either copied to a chosen directory or zipped.

### Image Processing

- **`TransformationsPanoramique.java`** — CPU equirectangular ↔ cube-face conversion
- **`TransformationsPanoramiqueGPU.java`** / **`gpu/ImageResizeGPU.java`** — OpenCL (JOCL) equivalents
- **`gpu/GPUManager.java`** — singleton owning the OpenCL context / queue; device priority is
  NVIDIA > AMD > rusticl (Mesa 3.0) > Intel > others > Clover, with CPU fallback via `isGPUAvailable()`
- OpenCL kernels are **`src/editeurpanovisu/gpu/*.cl`** (`equi2cube`, `cube2equi`, `resize_bicubic`,
  `resize_lanczos3`), loaded from classpath `/editeurpanovisu/gpu/` by `gpu/GPUKernelLoader.java`.
  (`src/kernels/` and `src/editeurpanovisu/kernels/` are empty leftovers — ignore them.)

### Map / Navigation / Preview

- **`NavigateurCarte.java`** — Leaflet in a `WebView`; the LocationIQ key is injected via `executeScript`
  from a `LoadWorker` listener *after* the page loads, and `setOnMapReady()` defers callers — this
  ordering works around the JavaFX WebEngine "texture is null" crash
- **`NavigateurOpenLayers.java`** / **`NavigateurCarteGluon.java`** — alternative map backends
- **`NavigateurPanoramique.java`** — in-editor 360° preview
- **`PanoramicCube.java`** — JavaFX 3D cube preview built from `TriangleMesh` quads (not `Box`) so each
  face gets its own correct UV mapping
- **`util/LocalHTTPServer.java`** — preview server built on the JDK's `com.sun.net.httpserver.HttpServer`,
  probing upward from port 8080 for a free port. (Javalin is declared in `pom.xml` but **never imported**.)

### AI Integration

`OllamaService.java` (~72 KB, static API, talks to `http://localhost:11434`) generates panorama
descriptions, with `HuggingFaceClient` / `OpenRouterClient` as remote fallbacks. `config/ModelConfig` +
`ModelConfigManager` persist model choices as JSON in `configPV/ollama-models.json` and
`configPV/openrouter-models.json`.

### Theming

`ThemeManager.java` exposes **27 themes** in a single enum across four providers (AtlantaFX, MaterialFX,
FlatLaf, custom CSS), falling back to Primer light/dark when a provider fails to load.
`util/SvgIconLoader.java` rasterises SVG → PNG at runtime with Apache Batik, recolouring `currentColor`
per theme; it reaches `ThemeManager` **by reflection** to avoid a package cycle.

### Internationalization

`src/editeurpanovisu/i18n/PanoVisu.properties` (default bundle, English text) plus `_fr`, `_en`, `_de`,
`_es_ES`, `_pt`. The build script rewrites `build.numero` in the default bundle — leave that line alone.

## Tests

JUnit Jupiter 5 tests live in **`src/test/java`**, declared explicitly as `<testSourceDirectory>`.
`--enable-preview` reaches them via `maven-surefire-plugin`.

```bash
mvn test                                   # run the suite
mvn test -Dtest=OrdrePanoramiqueDimensionTest   # one class
```

**The layout needs two cooperating settings.** `<sourceDirectory>` is `src`, so everything under
`src/` — including `src/test/java` — would otherwise compile as *main* source, duplicating the test
classes into the shipped JAR. The `default-compile` execution therefore excludes `test/**`. Change
one without the other and tests either fail to compile or get shipped.

Current tests, both covering the issue #16 regression (list height must equal a whole number of
*imposed* rows):

| Test | Needs a display? |
|------|------------------|
| `editeurpanovisu.OrdrePanoramiqueDimensionTest` | no — pure arithmetic, runs in CI |
| `editeurpanovisu.OrdrePanoramiqueRenduTest` | yes — starts the JavaFX toolkit; **skips itself** via `assumeTrue` when headless, rather than failing |

That skip matters: `linux-ci.yml` runs `mvn test` on a headless runner and treats failure as fatal, so
any UI-dependent test must degrade to skipped there.

**Manual GUI harnesses, not tests** — no `@Test`, so Surefire ignores them:

- `src/test/java/test/SvgIconLoaderTest.java`, `TestThemeDetection.java` — `Application` subclasses;
  run with `java -cp target/test-classes:target/classes --enable-preview test.SvgIconLoaderTest`
- `src/editeurpanovisu/gpu/Test*.java` and `TestAIClients.java` — still in the **main** tree, so they
  are compiled into the production JAR. Moving them to `src/test/java` would fix that.

## CI (`.github/workflows/`)

| Workflow | Trigger | Does |
|----------|---------|------|
| `build-release.yml` | tag push / manual | Windows installer via `build-installer.ps1`, Linux portable archive |
| `linux-ci.yml` | push / PR | compile + package on Ubuntu with Intel OpenCL installed |
| `pr-check.yml` | PR | quick `mvn clean compile`; test step is explicitly non-blocking |
| `javadoc.yml` / `docs.yml` / `pages.yml` | push | Javadoc and Doxygen → GitHub Pages |

All of them stub `api-keys.properties` and pass `-Dskip.build.increment=true`.

## Key Dependencies

| Library | Version | Use |
|---------|---------|-----|
| JavaFX | 19.0.2.1 | GUI (controls, fxml, web, swing) |
| Apache Batik | 1.17 | SVG → PNG at runtime |
| JOCL | 2.0.5 | OpenCL GPU bindings |
| AtlantaFX / MaterialFX / FlatLaf | 2.0.1 / 11.17.0 / 3.6.2 | Themes |
| CommonMark | 0.22.0 | In-app Markdown help viewer (F1) |
| commons-imaging / thumbnailator / metadata-extractor | — | Image I/O, EXIF / XMP |
| JMapViewer | 2.18 | Local `system`-scope JAR in `lib/` |
| Gson | 2.11.0 | AI model config JSON |
| Javalin | 6.3.0 | **Declared but unused** — safe to drop |
