# Mode souris au démarrage — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Let the tour author choose whether the exported viewer starts in Classique or StreetView mouse mode.

**Architecture:** A small enum holds the two values and their parsing (unit-tested). `GestionnaireInterfaceController` stores the choice in the interface template and shows it in both navigation-bar panels; `EditeurPanovisu` writes it as `modeSouris` on the root `<pano>` element of every scene XML; `panovisu.js` applies it once at first load.

**Tech Stack:** Java 25 (preview), JavaFX 19, JUnit 5, jQuery viewer (`panovisu/panovisu.js`).

**Spec:** `docs/superpowers/specs/2026-11-20-mode-souris-demarrage-design.md`

## Global Constraints

- Values are exactly `"classique"` and `"streetview"`; default `"classique"`.
- Template key is exactly `modeSourisDemarrage=`; XML attribute is exactly `modeSouris` on `<pano`.
- Old projects / templates without the key open in Classique (no rendering change).
- Touch devices keep the forced StreetView mode (`if (estTactile()) iMode = 0;`, panovisu.js ~6958).
- Labels in all 6 bundles: `PanoVisu.properties`, `_fr`, `_en`, `_de`, `_es_ES`, `_pt` (follow each file's existing escaping). Never touch `build.numero`.
- Build with `mvn` from repo root; see memory `project_run_commands` for JAVA_HOME/Maven paths on this machine.

## Review Focus

- Visitor toggles mode, then navigates to another scene → mode and icon must stay as the visitor left them (no re-apply of `modeSouris`, icon not reset to `souris.png`). Covered: Task 3 manual check 3.
- Project saved with StreetView, then a second old project (no key) opened in the same session → must show Classique. Covered: `setTemplate` reset in Task 2 + manual check 2.
- Both navigation bars hidden → setting still editable and still exported. Covered: Task 2 (combos outside the disabled sub-panels) + Task 3 check 1.
- Unknown/blank value in a hand-edited `.pvu` → Classique, no exception. Covered: Task 1 tests.
- Touch device with `modeSouris="classique"` → still StreetView. Covered: Task 3 code guard + check 4.

---

### Task 1: `ModeSourisDemarrage` enum

**Files:**
- Create: `src/editeurpanovisu/ModeSourisDemarrage.java`
- Test: `src/test/java/editeurpanovisu/ModeSourisDemarrageTest.java`

**Interfaces:**
- Produces: `public enum ModeSourisDemarrage { CLASSIQUE, STREETVIEW; String getValeur(); static ModeSourisDemarrage depuisValeur(String valeur); }` — `getValeur()` returns `"classique"` / `"streetview"`.

- [ ] **Step 1: Write the failing test**

```java
@Test void valeursExportees() {
    assertEquals("classique", ModeSourisDemarrage.CLASSIQUE.getValeur());
    assertEquals("streetview", ModeSourisDemarrage.STREETVIEW.getValeur());
}
@Test void lectureValeurConnue() {
    assertEquals(ModeSourisDemarrage.STREETVIEW, ModeSourisDemarrage.depuisValeur("streetview"));
    assertEquals(ModeSourisDemarrage.STREETVIEW, ModeSourisDemarrage.depuisValeur(" StreetView "));
    assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur("classique"));
}
@Test void valeurInconnueOuAbsenteDonneClassique() {
    assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur(null));
    assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur(""));
    assertEquals(ModeSourisDemarrage.CLASSIQUE, ModeSourisDemarrage.depuisValeur("n'importe quoi"));
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `mvn test -Dtest=ModeSourisDemarrageTest -Dskip.build.increment=true`
Expected: compilation failure, `ModeSourisDemarrage` not found.

- [ ] **Step 3: Implement the enum** — trim + case-insensitive match on `getValeur()`, fallback `CLASSIQUE`.

- [ ] **Step 4: Run test to verify it passes**

Run: `mvn test -Dtest=ModeSourisDemarrageTest -Dskip.build.increment=true`
Expected: 3 tests PASS.

- [ ] **Step 5: Commit**

```bash
git add src/editeurpanovisu/ModeSourisDemarrage.java src/test/java/editeurpanovisu/ModeSourisDemarrageTest.java
git commit -m "feat(interface): enum du mode souris au demarrage"
```

### Task 2: Editor — template, UI, XML export

**Files:**
- Modify: `src/editeurpanovisu/GestionnaireInterfaceController.java` (field near line 444; `strGetTemplate()` ~5966; `setTemplate()` ~6184 and its `switch` ~6385; apply-to-UI block ~7106; classic panel ~9598-9640; custom panel ~9809-9870; listeners ~12044)
- Modify: `src/editeurpanovisu/EditeurPanovisu.java:2556` (root `<pano` writer)
- Modify: the 6 `src/editeurpanovisu/i18n/PanoVisu*.properties`

**Interfaces:**
- Consumes: `ModeSourisDemarrage` (Task 1).
- Produces: `public ModeSourisDemarrage getModeSourisDemarrage()` / `public void setModeSourisDemarrage(ModeSourisDemarrage)` on `GestionnaireInterfaceController`; XML attribute `modeSouris="<getValeur()>"` on `<pano`.

- [ ] **Step 1: Model** — field `modeSourisDemarrage = ModeSourisDemarrage.CLASSIQUE` + getter/setter; `strGetTemplate()` appends `modeSourisDemarrage=` + `getValeur()` right after the `souris=` line; `setTemplate()` resets to `CLASSIQUE` with the other resets at its top and parses `case "modeSourisDemarrage"` via `depuisValeur`.

- [ ] **Step 2: i18n** — add `interface.modeSourisDemarrage` (FR « Mode souris au démarrage », EN "Mouse mode at start-up"), `interface.modeSourisClassique` (« Classique » / "Classic"), `interface.modeSourisStreetView` ("StreetView") to all 6 bundles (DE/ES/PT translated).

- [ ] **Step 3: UI** — two `ComboBox<ModeSourisDemarrage>` (one per panel) with a `StringConverter` mapping to the localized labels, label + combo added to `apBarreClassique` (layoutY 420, panel prefHeight 420→455) and `apBarrePersonnalisee` (layoutY 720, prefHeight 680→755) — **not** to `apBarreClass` / `apBarrePers`, which are disabled when the bar is hidden. Bind `cb1.valueProperty().bindBidirectional(cb2.valueProperty())`; one listener sets the model and marks the project dirty with the same two lines as the `cbSourisBarreClassique` listener (~12045-12048). The apply-to-UI block (~7106) sets the combo value from the model.

- [ ] **Step 4: Export** — in `EditeurPanovisu` after `image="…"` of `<pano`, append `"      modeSouris=\"" + getGestionnaireInterface().getModeSourisDemarrage().getValeur() + "\"\n"`.

- [ ] **Step 5: Verify**

Run: `mvn clean compile -Dskip.build.increment=true` → BUILD SUCCESS; then `mvn test -Dskip.build.increment=true` → all green.
Manual (`mvn javafx:run`): (1) hide both bars → combos still enabled; (2) set StreetView, save, reopen → StreetView; open an old `.pvu` → Classique; (3) change in one panel → the other follows; title gets ` *`.

- [ ] **Step 6: Commit**

```bash
git add src/editeurpanovisu/GestionnaireInterfaceController.java src/editeurpanovisu/EditeurPanovisu.java src/editeurpanovisu/i18n/
git commit -m "feat(interface): choix du mode souris au demarrage (#20)"
```

### Task 3: Viewer — apply the start mode once, consistent icon

**Files:**
- Modify: `panovisu/panovisu.js` (declarations ~290; `chargeXML` after line ~5786 `var XMLPano`; `creeImagesboutons` ~5440; télécommande icon ~6637)

**Interfaces:**
- Consumes: attribute `modeSouris` on `<pano>` (Task 2).

- [ ] **Step 1: Implement**
  - Declare `bModeSourisInitialise = false` next to `iMode = 1`.
  - In `chargeXML`, right after `var XMLPano = …`: if `!bModeSourisInitialise`, set it true; if `!estTactile()`, `iMode = (XMLPano.attr('modeSouris') === 'streetview') ? 0 : 1`; if `iMode === 0`, `$("#container-" + iNumPano).css("cursor", "grab")`.
  - `creeImagesboutons` and the télécommande `<img>` pick `souris2.png` when `iMode === 0`, else `souris.png` (same mapping as `changeModeSouris`).

- [ ] **Step 2: Verify in a browser** — export a 2-scene project with StreetView, serve it (in-app preview or `python -m http.server` in the export dir), open with Playwright/Chrome:
  1. Drag immediately → image follows the pointer (grab), cursor `grab`, button shows `souris2.png`, no console error.
  2. Re-export with Classique → historical behaviour (velocity drag).
  3. StreetView export: click mouse button (→ Classique), go to scene 2 → still Classique, icon `souris.png`.
  4. Emulate touch (DevTools device mode) with Classique export → StreetView (forced).

- [ ] **Step 3: Commit**

```bash
git add panovisu/panovisu.js
git commit -m "feat(viewer): mode souris initial lu dans le XML, icone coherente (#20)"
```
