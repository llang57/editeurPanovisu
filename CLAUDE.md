# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Compile
mvn clean compile

# Run the application
mvn javafx:run

# Build fat JAR
mvn clean package

# Run all tests
mvn test

# Run a single test
mvn test -Dtest=SvgIconLoaderTest

# Build Windows MSI installer
mvn jpackage:jpackage

# Build Linux portable archive
mvn clean install -Pportable
```

The project requires **Java 25 with `--enable-preview`** — this is already configured in pom.xml for both compile and test phases.

### Build Number Auto-increment

On Windows, Maven's `initialize` phase runs `increment-build.ps1`, which:
- Reads/increments `build.num` (current: ~3680)
- Updates version strings in `src/editeurpanovisu/i18n/PanoVisu.properties` and `src/project.properties`

Do not manually edit `build.num` or the version strings in those property files.

### Release versioning

When bumping the version for a release, update these 4 files — all others detect the version dynamically:

| Fichier | Clé |
|---------|-----|
| `pom.xml` | `<version>` |
| `src/project.properties` | `project.version` |
| `installer.iss` | `#define MyAppVersion` |
| `src/editeurpanovisu/i18n/PanoVisu.properties` | ligne `# Version` (cosmétique) |

`build-installer.ps1` and `create-linux-portable.sh` detect the JAR name via glob — no version to update there.

## Architecture

### Entry Points

- **`editeurpanovisu.Launcher`** — jpackage wrapper; calls `Application.launch(EditeurPanovisu.class)`
- **`editeurpanovisu.EditeurPanovisu`** — actual `javafx.application.Application` subclass (~15,000 lines)

### Core Structure

This is a **monolithic JavaFX desktop application**. Most UI logic lives in two very large files:

- **`EditeurPanovisu.java`** (~15,000 lines) — application lifecycle, project file I/O (`.pvu` XML format), export pipeline, toolbar/menu wiring
- **`GestionnaireInterfaceController.java`** (~741 KB) — main UI controller handling panorama editing, hotspot placement, and property panels

### Domain Model

| Class | Role |
|-------|------|
| `Panoramique.java` | A panoramic scene (sphere or cube); holds 100-slot arrays for hotspots |
| `HotSpot.java` | Interactive marker with spherical coordinates (lon/lat), animation type, custom icon/color |
| `Diaporama.java` | Slideshow model (image sequence, background, opacity) |
| `Plan.java` | Floor plan / interactive map overlay |

Project files use the `.pvu` extension (JAXB-serialized XML).

### Image Processing

- **`TransformationsPanoramique.java`** — CPU-based equirectangular ↔ cube-face conversions
- **`TransformationsPanoramiqueGPU.java`** / **`ImageResizeGPU.java`** — OpenCL (JOCL) GPU-accelerated equivalents
- **`GPUManager.java`** — singleton managing OpenCL context/device lifecycle; falls back to CPU when GPU is unavailable
- OpenCL kernel sources live in `src/editeurpanovisu/kernels/` (`.cl` files)

### Map / Navigation

- **`NavigateurCarte.java`** — Leaflet-based map (lazy-loads HTML, injects LocationIQ key after init via `setOnMapReady()` callback — needed to avoid JavaFX WebEngine "texture is null" bug)
- **`NavigateurOpenLayers.java`** — alternative OpenLayers implementation
- **`NavigateurPanoramique.java`** — in-editor 3D panorama preview

### AI Integration

- **`OllamaService.java`** — local Ollama LLM service (~71 KB)
- **`HuggingFaceClient.java`** / **`OpenRouterClient.java`** — remote AI APIs
- **`config/ModelConfig.java`** + **`ModelConfigManager.java`** — JSON-based model configuration with priority and availability tracking

### Theming

**`ThemeManager.java`** manages 24 themes across four libraries (AtlantaFX, MaterialFX, FlatLaf, legacy CSS). **`SvgIconLoader.java`** uses Apache Batik to convert SVG icons to PNG at runtime, dynamically recoloring them based on the detected theme (Dracula/Darcula = white icons; light themes = black icons).

### Preview Server

**`LocalHTTPServer.java`** embeds a Javalin 6 HTTP server for in-app visit preview.

### Internationalization

Strings are in `src/editeurpanovisu/i18n/PanoVisu.properties` (French primary). The build number is embedded there by the increment script — do not edit those entries manually.

## Key Dependencies

| Library | Version | Use |
|---------|---------|-----|
| JavaFX | 19.0.2.1 | GUI |
| Javalin | 6.3.0 | Embedded preview server |
| Apache Batik | 1.17 | SVG → PNG at runtime |
| JOCL | 2.0.5 | OpenCL GPU bindings |
| AtlantaFX | 2.0.1 | Themes |
| CommonMark | 0.22.0 | In-app Markdown help viewer (F1) |
| JMapViewer | 2.18 | Local JAR (not in Maven Central) |
| Gson | 2.11.0 | AI model config JSON |

## Tests

Tests are under `src/test/` using JUnit Jupiter 5. The `--enable-preview` flag is passed automatically via `maven-surefire-plugin`. GPU-related test utilities (`TestImageResize.java`, `TestBicubicVsLanczos.java`, etc.) are in the `gpu/` sub-package and are interactive GUI tests, not part of the standard suite.
