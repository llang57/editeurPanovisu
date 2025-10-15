# 🔧 Version 3.1.0 - Critical Case-Sensitivity Fix Available

## ⚠️ CRITICAL Update - Action Required

Dear editeurPanoVisu  users,

We have just released **version 3.1.0** which fixes a critical bug affecting the operation of virtual tours on **Linux servers**.

---

## 🐛 Fixed Issue

### Symptoms
If you exported tours with previous versions and host them on a Linux server, you may have encountered:
- ❌ 404 errors in browser console
- ❌ Non-functional navigation interface
- ❌ Interactive map not displaying
- ❌ Missing `jquerymenu` and `openlayers` files

### Cause
The bug was due to **case inconsistency** in folder names:
- The code referenced: `jquerymenu` and `openlayers` (lowercase)
- Created folders were: `jqueryMenu` and `openLayers` (mixed case)

On Windows (case-insensitive filesystem), everything worked. On Linux (case-sensitive), files were not found.

### Solution
Version 3.1.0 fixes all paths to use the correct case: **`jqueryMenu`** and **`openLayers`**.

---

## 📥 Installation

### Windows
Download and install:
👉 [EditeurPanovisu-Setup-3.1.0.exe](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-Setup-3.1.0.exe)

### macOS
Download and install:
👉 [EditeurPanovisu-3.1.0.dmg](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-3.1.0.dmg)

### Linux
**Debian/Ubuntu:**
```bash
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu_3.1.0_amd64.deb
sudo dpkg -i editeurpanovisu_3.1.0_amd64.deb
```

**Fedora/RHEL/CentOS:**
```bash
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu-3.1.0.x86_64.rpm
sudo rpm -i editeurpanovisu-3.1.0.x86_64.rpm
```

---

## ⚡ ACTION REQUIRED

### For existing tours hosted on Linux

**You MUST re-export all your tours** with version 3.1.0 to fix the paths:

1. ✅ Install version 3.1.0
2. ✅ Open each virtual tour project
3. ✅ Export again (File > Export)
4. ✅ Replace files on your server

### For new tours

No special action needed, just use version 3.1.0.

---

## 📋 Technical Details

**Modified files:**
- `src/editeurpanovisu/EditeurPanovisu.java` (lines 2675, 2681-2682)
- `panovisu/panovisuInit.js` (lines 164-165)

**Build:** 2609  
**Release date:** October 16, 2025  
**Commit:** `93ece95`

---

## 🔗 Useful Links

- 📦 [Complete release page](https://github.com/llang57/editeurPanovisu/releases/tag/v3.1.0)
- 📝 [Detailed release notes](https://github.com/llang57/editeurPanovisu/blob/master/RELEASE_NOTES_3.1.0.md)
- 🐛 [Report an issue](https://github.com/llang57/editeurPanovisu/issues)
- 💬 [Discussion forum](https://github.com/llang57/editeurPanovisu/discussions)

---

## 💬 Questions?

Feel free to ask your questions in this discussion or open a ticket if you encounter any problems.

**Thank you for using Panovisu Editor!** 🎉
