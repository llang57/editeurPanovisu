# ❓ FAQ - Version 3.1.0: Frequently Asked Questions about the case-sensitivity fix

## General Questions

### 1. 🤔 Am I affected by this bug?

**You ARE affected if:**
- ✅ You host your tours on a **Linux** server (Apache, Nginx, etc.)
- ✅ Your tours were exported with version **< 3.1.0**
- ✅ You see 404 errors for `jquerymenu` or `openlayers`

**You are NOT affected if:**
- ❌ You host on **Windows Server** (IIS)
- ❌ You only test locally on Windows
- ❌ You haven't published any tours yet

---

### 2. 🔄 Do I really need to re-export all my tours?

**YES**, if they are hosted on Linux and not working properly.

**NO**, if:
- Your tours are already working perfectly
- You're on Windows Server
- You haven't published yet

---

### 3. 💾 Can I just rename folders instead of re-exporting?

**Not recommended**. The fix is not limited to folder names, it also corrects:
- Paths in JavaScript code
- References in HTML files
- Module imports

**Safe solution**: Re-export with v3.1.0.

---

### 4. 🕐 How long does the update take?

**Installing v3.1.0:**
- Windows: ~2 minutes (automatic installation)
- macOS: ~3 minutes
- Linux: ~1 minute (via package manager)

**Re-exporting a tour:**
- Small tour (10-20 photos): ~1 minute
- Medium tour (50-100 photos): ~3-5 minutes
- Large tour (200+ photos): ~10-15 minutes

---

## Technical Questions

### 5. 🔍 How can I check if my tours are affected?

Open your browser console (F12) on your published tour and look for:

```
GET https://yoursite.com/tour/panovisu/libs/jquerymenu/... → 404
GET https://yoursite.com/tour/panovisu/libs/openlayers/... → 404
```

If you see these errors, you're affected.

---

### 6. 🛠️ Can I fix it manually without re-exporting?

**Possible but not recommended**. You would need to:

1. Rename folders on the server
2. Modify `panovisuInit.js` lines 164-165
3. Check all paths in HTML files
4. Test exhaustively

**Risk**: Forgetting some files, inconsistencies.

**Recommendation**: Re-export, it's faster and safer.

---

### 7. 📦 Are previous versions still available?

Previous versions remain downloadable, but **we strongly recommend v3.1.0** for:
- ✅ Guaranteed Linux compatibility
- ✅ Bug fixes
- ✅ Future support

---

### 8. 🔐 Are there other changes in v3.1.0?

**No**. This version is a **targeted fix** only for case-sensitivity. No new features, no interface changes.

**Changes:**
- Path correction `jquerymenu` → `jqueryMenu`
- Path correction `openlayers` → `openLayers`
- Build incremented to 2609

---

## Installation Questions

### 9. 🪟 On Windows, do I need to uninstall the old version?

**Not necessary**. The Windows installer automatically detects the old version and replaces it.

**Installation path**: Same as the old version (usually `C:\Program Files\EditeurPanovisu`)

---

### 10. 🍎 On macOS, how do I update?

1. Download `EditeurPanovisu-3.1.0.dmg`
2. Mount the disk image
3. Drag `EditeurPanovisu.app` to `/Applications` (replaces the old one)
4. Eject the disk image

---

### 11. 🐧 On Linux, how do I manage dependencies?

The `.deb` and `.rpm` packages include **all dependencies**:
- Java Runtime (OpenJDK 25)
- JavaFX 19
- Native libraries

**No manual installation required**.

---

### 12. 🔄 Can I have multiple versions installed?

**Not recommended**. Only one version should be installed to avoid conflicts.

If necessary for testing:
- Windows: Install in different folders
- macOS: Rename `EditeurPanovisu.app` before installing the new one
- Linux: Use isolated environments (containers, VMs)

---

## Migration Questions

### 13. 📂 Are my existing projects compatible?

**YES**, 100%. Project files (`.pano`, `.cfg`, etc.) are **fully compatible**.

You can:
- ✅ Open your old projects in v3.1.0
- ✅ Modify them
- ✅ Re-export them

**No conversion needed**.

---

### 14. 🔙 Can I go back to the old version if needed?

**Yes**, but it's not recommended. Project files are backward compatible.

**Warning**: If you create new projects with v3.1.0, they may not open correctly in older versions.

---

### 15. 🌐 What if I already have published tours that work?

**Option 1** (Recommended): Update anyway
- Install v3.1.0
- Prepare for your next tours
- Future compatibility guarantee

**Option 2**: Wait
- Keep current version
- Risk on future exports
- No support for old bugs

---

## Support

### 16. 💬 Where can I get help?

**GitHub Discussions**: https://github.com/llang57/editeurPanovisu/discussions
**Issues (bugs)**: https://github.com/llang57/editeurPanovisu/issues
**Documentation**: https://llang57.github.io/editeurPanovisu/

---

### 17. 🐛 I found a bug, how do I report it?

1. Go to https://github.com/llang57/editeurPanovisu/issues
2. Click "New issue"
3. Describe the problem with:
   - Panovisu Editor version
   - Operating system
   - Steps to reproduce
   - Screenshots if possible

---

### 18. 💡 I want to suggest an improvement?

Use **GitHub Discussions** in the "Ideas" category:
https://github.com/llang57/editeurPanovisu/discussions/categories/ideas

---

## Compatibility Questions

### 19. 🖥️ Which systems are supported?

**Windows:**
- Windows 10 (64-bit)
- Windows 11
- Windows Server 2019+

**macOS:**
- macOS 11 (Big Sur) and later
- Intel and Apple Silicon (M1/M2/M3) architecture

**Linux:**
- Ubuntu 20.04+
- Debian 11+
- Fedora 35+
- CentOS 8+
- Other recent distributions with glibc 2.31+

---

### 20. 📱 Can I use the editor on tablet/mobile?

**No**. Panovisu Editor is a **desktop-only** application.

**But**: Exported tours work perfectly on:
- 📱 Smartphones (iOS, Android)
- 📱 Tablets
- 💻 All modern browsers

---

## Have more questions?

Ask them in this discussion! 👇
