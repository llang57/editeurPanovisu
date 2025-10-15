# 📚 Version 3.1.0 Documentation - Index

Welcome to the complete documentation for version 3.1.0 of Panovisu Editor.

---

## 🌐 Languages

- 🇬🇧 **English** (this page)
- 🇫🇷 [Français](INDEX_V3.1.0.md)

---

## 🎯 Quick Navigation

### For New Users
- 👉 [Version 3.1.0 Announcement](DISCUSSION_V3.1.0_EN.md) - **Start here**
- 📖 [FAQ - Frequently Asked Questions](FAQ_V3.1.0_EN.md)
- 📥 [Installation](MIGRATION_GUIDE_V3.1.0_EN.md#-step-1-installing-v310)

### For Existing Users
- 🚀 [Complete Migration Guide](MIGRATION_GUIDE_V3.1.0_EN.md) - **Detailed steps**
- ⚠️ [Required Actions](#-required-actions)
- 🔧 [Common Issues and Solutions](PROBLEMES_SOLUTIONS_V3.1.0.md) (French only)

### For Developers
- 📝 [Detailed Release Notes](../RELEASE_NOTES_3.1.0.md)
- 🔍 [Technical Changes](#-technical-changes)
- 🐛 [Report a Bug](https://github.com/llang57/editeurPanovisu/issues)

---

## ⚠️ Required Actions

### If You Host Tours on Linux

**🚨 YOU MUST re-export all your tours with v3.1.0**

1. ✅ [Install v3.1.0](MIGRATION_GUIDE_V3.1.0_EN.md#-step-1-installing-v310)
2. ✅ [Re-export each tour](MIGRATION_GUIDE_V3.1.0_EN.md#-step-2-re-exporting-tours)
3. ✅ [Upload to server](MIGRATION_GUIDE_V3.1.0_EN.md#-step-3-upload-to-server)
4. ✅ [Test the result](MIGRATION_GUIDE_V3.1.0_EN.md#-step-4-post-migration-tests)

**Estimated time**: 2-5 minutes per tour

**Why?** Previous versions used incorrect case for file paths, causing 404 errors on Linux servers (case-sensitive).

### If You Host on Windows

**No urgent action needed**, but we still recommend updating for:
- ✅ Future compatibility guarantee
- ✅ Avoid issues if you change servers
- ✅ Benefit from bug fixes

---

## 📖 Available Documents

### 1. [v3.1.0 Announcement](DISCUSSION_V3.1.0_EN.md)
**Content:**
- Fix presentation
- Description of fixed bug
- Download links for all OS
- Quick installation instructions

**For whom:** All users  
**Reading time:** 3 minutes

---

### 2. [FAQ - Frequently Asked Questions](FAQ_V3.1.0_EN.md)
**Content:**
- 20 questions/answers
- Common issues
- Compatibility
- Installation
- Migration

**For whom:** All users  
**Reading time:** 10 minutes (selective reading)

**Featured questions:**
- [Am I affected by this bug?](FAQ_V3.1.0_EN.md#1--am-i-affected-by-this-bug)
- [Do I really need to re-export?](FAQ_V3.1.0_EN.md#2--do-i-really-need-to-re-export-all-my-tours)
- [Are my projects compatible?](FAQ_V3.1.0_EN.md#13--are-my-existing-projects-compatible)

---

### 3. [Migration Guide](MIGRATION_GUIDE_V3.1.0_EN.md)
**Content:**
- Step-by-step instructions for all OS
- Complete checklist
- Automation scripts
- Post-migration tests
- Optimization tips

**For whom:** Users hosting on Linux  
**Migration time:** 30-90 minutes (depending on number of tours)

**Main sections:**
- [Windows/macOS/Linux Installation](MIGRATION_GUIDE_V3.1.0_EN.md#-step-1-installing-v310)
- [Re-export process](MIGRATION_GUIDE_V3.1.0_EN.md#-step-2-re-exporting-tours)
- [Upload and tests](MIGRATION_GUIDE_V3.1.0_EN.md#-step-3-upload-to-server)

---

### 4. [Release Notes](../RELEASE_NOTES_3.1.0.md)
**Content:**
- Detailed changelog
- Technical modifications
- File checksums
- Build information

**For whom:** Developers, system admins  
**Reading time:** 5 minutes

---

## 🔍 Technical Changes

### Files Modified in v3.1.0

**Source code:**
```
src/editeurpanovisu/EditeurPanovisu.java
  Lines 2675, 2681-2682: Path corrections
  jquerymenu → jqueryMenu
  openlayers → openLayers

panovisu/panovisuInit.js
  Lines 164-165: AMD imports corrections
  require(['panovisu/libs/jqueryMenu/...
  require(['panovisu/libs/openLayers/...
```

**Build information:**
- Version: 3.1.0
- Build: 2609
- Date: October 16, 2025
- Commit: 93ece95
- Git Tag: v3.1.0

---

## 📥 Downloads

### Official Installers

| Platform | File | Size | Link |
|----------|------|------|------|
| 🪟 Windows | EditeurPanovisu-Setup-3.1.0.exe | ~188 MB | [Download](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-Setup-3.1.0.exe) |
| 🍎 macOS | EditeurPanovisu-3.1.0.dmg | ~180 MB | [Download](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-3.1.0.dmg) |
| 🐧 Linux (Debian) | editeurpanovisu_3.1.0_amd64.deb | ~175 MB | [Download](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu_3.1.0_amd64.deb) |
| 🐧 Linux (RPM) | editeurpanovisu-3.1.0.x86_64.rpm | ~175 MB | [Download](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu-3.1.0.x86_64.rpm) |

**Complete release page:** https://github.com/llang57/editeurPanovisu/releases/tag/v3.1.0

---

## 🛠️ Support and Help

### Getting Help

**GitHub Discussions** (recommended):
- 💬 [Ask a question](https://github.com/llang57/editeurPanovisu/discussions/categories/q-a)
- 💡 [Suggest an improvement](https://github.com/llang57/editeurPanovisu/discussions/categories/ideas)
- 📢 [Announcements](https://github.com/llang57/editeurPanovisu/discussions/categories/announcements)

**GitHub Issues** (bugs only):
- 🐛 [Report a bug](https://github.com/llang57/editeurPanovisu/issues/new?template=bug_report.md)
- ✨ [Request a feature](https://github.com/llang57/editeurPanovisu/issues/new?template=feature_request.md)

**Main documentation:**
- 📚 [Documentation site](https://llang57.github.io/editeurPanovisu/)
- 📖 [Project README](../README.md)
- 🔧 [Development guide](development/README.md)

---

## 🗺️ Roadmap

### Version 3.1.x (maintenance)
- 🔄 Minor bug fixes
- 📝 Documentation improvements
- 🧪 Additional tests

### Version 3.2.0 (future)
- ✨ New features to be defined
- 🎨 Interface improvements
- ⚡ Performance optimizations

**Follow discussions to participate in the roadmap!**

---

## 📊 Release Statistics

### Fixes in v3.1.0
- 🐛 **1 critical bug fixed** (Linux case-sensitivity)
- 📝 **2 files modified** (Java + JavaScript)
- 🔢 **4 lines of code changed**
- ✅ **100% tests passing**

### Compatibility
- ✅ Windows 10/11
- ✅ macOS 11+ (Intel + Apple Silicon)
- ✅ Linux (Ubuntu, Debian, Fedora, CentOS...)
- ✅ Backward compatible with v3.0.x projects

---

## 📜 Version History

| Version | Date | Type | Highlights |
|---------|------|------|------------|
| **3.1.0** | Oct 16, 2025 | 🔧 Fix | Linux case-sensitivity fix |
| 3.0.0 | [Date] | 🎉 Major | Complete interface redesign |
| 2.x.x | ... | ... | ... |

**See all versions:** [GitHub Releases](https://github.com/llang57/editeurPanovisu/releases)

---

## 🤝 Contributing

### How to Contribute?

**Documentation:**
- 📝 Report errors in documentation
- ✍️ Suggest improvements
- 🌍 Translate documentation

**Code:**
- 🐛 Fix bugs
- ✨ Propose features
- 🧪 Add tests

**Community:**
- 💬 Help other users
- 📢 Share your tours
- ⭐ Star the project!

---

## 📞 Contact

**GitHub Project:** https://github.com/llang57/editeurPanovisu  
**Discussions:** https://github.com/llang57/editeurPanovisu/discussions  
**Issues:** https://github.com/llang57/editeurPanovisu/issues

---

## 📄 License

This project is licensed under [LICENSE](../LICENSE).

---

**Last update of this index:** October 16, 2025  
**Version covered:** 3.1.0 (build 2609)

---

<div align="center">

**Thank you for using Panovisu Editor!** 🎉

[⬆️ Back to top](#-quick-navigation)

</div>
