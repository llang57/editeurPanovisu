# 🌐 PanoVisu Editor

[![Version](https://img.shields.io/badge/version-3.4.14-blue.svg)](https://github.com/llang57/editeurPanovisu/releases)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://adoptium.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-19-green.svg)](https://openjfx.io/)
[![License](https://img.shields.io/badge/license-GPL--3.0-brightgreen.svg)](LICENSE)
[![Javadoc](https://img.shields.io/badge/docs-Javadoc-blue.svg)](https://llang57.github.io/editeurPanovisu/)
[![Wiki](https://img.shields.io/badge/docs-Wiki-blue.svg)](https://github.com/llang57/editeurPanovisu/wiki)
[![Downloads](https://img.shields.io/github/downloads/llang57/editeurPanovisu/total.svg)](https://github.com/llang57/editeurPanovisu/releases)
[![Stars](https://img.shields.io/github/stars/llang57/editeurPanovisu.svg)](https://github.com/llang57/editeurPanovisu/stargazers)

<div align="center">

**🎯 Visual editor for creating virtual tours and interactive 360° panoramas**

Easily create immersive virtual tours in HTML5/WebGL without any programming knowledge.  
PanoVisu combines power, simplicity, and freedom to offer a complete and free solution.

[📦 Download](https://github.com/llang57/editeurPanovisu/releases) • [📚 Documentation](https://github.com/llang57/editeurPanovisu/wiki) • [🌐 Website](https://lemondea360.fr/panovisu) • [💬 Discussions](https://github.com/llang57/editeurPanovisu/discussions)

**Languages:** [🇫🇷 Français](README.md) • [🇬🇧 English](README_EN.md)

</div>

---

## 📑 Table of Contents

- [What is PanoVisu?](#what-is-panovisu)
- [Main Features](#-main-features)
- [Installation](#-installation)
- [Quick Start](#-quick-start)
- [Technologies Used](#-technologies-used)
- [API Keys Configuration](#-api-keys-configuration)
- [Compilation and Development](#-compilation-and-development)
- [Documentation](#-documentation)
- [Browser Compatibility](#-browser-compatibility)
- [What is New](#-what-is-new)
- [Contributing](#-contributing)
- [Support and Contact](#-support-and-contact)
- [License](#-license)

## What is PanoVisu?

**PanoVisu** is an open-source project composed of two complementary elements:

1. **An HTML5/WebGL viewer** - Lightweight and modern technology to display interactive 360° panoramas in all web browsers
2. **A Java/JavaFX visual editor** - Intuitive desktop application to create and configure your virtual tours

### 🎯 Why PanoVisu?

- ✅ **100% free and open source** - No limitations, no watermarks
- ✅ **Cross-platform** - Windows, macOS, Linux
- ✅ **Serverless** - Generate standalone tours hostable anywhere
- ✅ **Modern technology** - HTML5, WebGL, Three.js
- ✅ **Extensible** - Open source code, fully customizable
- ✅ **Professional** - Quality comparable to commercial solutions

## 🚀 Main Features

### 📸 Supported Panorama Types
- **Equirectangular spherical** panoramas (standard 360°)
- **Cube face** panoramas (cubemap)
- **Partial** and **cylindrical** panoramas

### 🎨 Interactive Elements
- **Navigation hotspots** - Links between panoramas with crossfade transitions
  - 16 different animations (bounce, pulse, flash, shake, swing, tada, etc.)
  - Customizable colors per hotspot (HSB)
  - Configurable hover magnification
- **Image/photo hotspots** - Integrated photo galleries
- **Slideshow hotspots** - Modern HTML5 viewer with complete animations
  - Complete controls (play/pause, navigation, fullscreen, thumbnails)
  - Consistent animations with other hotspot types
  - Intelligent pause/play behavior
- **HTML hotspots** - Rich content (videos, texts, external links)
- **Interactive maps** - 2D map with points of interest and radar
- **Geolocated maps** - OpenStreetMap, Google Maps, Bing Maps
- **Dynamic compass** - Real-time orientation
- **Background images** - Splash screens, logos, banners
- **Navigation thumbnails** - Visual preview of panoramas
- **Social networks** - Facebook, Twitter, email sharing
- **Context menu** - Customizable right-click actions

### 🎛️ Controls and Navigation
- **Customizable navigation bars** - Design adapted to your branding
- **JavaScript actuators** - API to control the tour from your code
- **Automatic tour** - Demo mode with automatic rotation
- **Layer system** - 10 levels to organize elements
- **Native fullscreen** - Total immersion
- **Touch controls** - Mobile and tablet support

### 🛠️ Graphic Editor
- Intuitive **drag & drop** interface
- Real-time preview
- Image transformation management (equirectangular ↔ cubemap)
- **Integrated WYSIWYG HTML editor**: Create rich content without HTML code
- **Modernized slideshow creation**: Clean interface with transition management
- **Custom navigation bars**: Visual creation with clickable zones
- **Theme adaptation**: All windows follow chosen theme (light/dark)
- One-click export to HTML/XML

### ⚡ GPU Acceleration (OpenCL)
- **Hardware image processing**: equirectangular ↔ cube conversion, resizing and level-of-detail generation
- **Measured gains**: tours load 3.4× faster (15 s → 4.5 s), display 10× faster, batch resizing 1.7× faster
- **Higher quality**: Bicubic and Lanczos3 interpolation replacing nearest neighbour, without aliasing
- **Automatic CPU fallback** when no OpenCL-capable GPU is available (NVIDIA, AMD, Intel)

### 🗺️ Maps and Geolocation
- **Built-in Leaflet map** to place each panorama
- **Draggable markers**, with coordinates updated as you move them
- **Field-of-view radar**, adjustable from 0 to 240 m
- **Address search** via OpenStreetMap, and LocationIQ reverse geocoding to enrich descriptions

### 🤖 AI Description Generation
- **Ollama** locally, free and with no data leaving your machine, or **OpenRouter** online (Claude, Gemini, GPT)
- **Editable model catalogue**, from free to premium, with indicative pricing
- **Descriptions automatically reviewed**: dates, measurements, heritage listings, superlatives and unsupplied proper nouns are flagged

### 🎨 Interface Themes
- **27 themes**: 9 AtlantaFX (Primer, Nord, Cupertino, Dracula), 2 MaterialFX, 2 FlatLaf, 2 custom, 2 vivid, 2 modern and 8 minimalist (blue, green, red, purple, each light and dark)
- **Adaptive icons**: SVG icons automatically take the colour of the active theme
- **Dark theme legibility**: lightened form controls and contrasted borders

## 📦 Installation

### Windows

1. Download the installer: [EditeurPanovisu-Setup-3.1.0.exe](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-Setup-3.1.0.exe)
2. Run the `.exe` file
3. Follow the installation wizard
4. Launch PanoVisu Editor

**Requirements:** Windows 10/11 (64-bit)

### macOS

1. Download: [EditeurPanovisu-3.1.0.dmg](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-3.1.0.dmg)
2. Mount the `.dmg` file
3. Drag `EditeurPanovisu.app` to the Applications folder
4. Right-click > Open (first launch only)

**Requirements:** macOS 11 (Big Sur) or later

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

**Requirements:** Recent distribution with glibc 2.31+

## 🚀 Quick Start

1. **Launch the editor** - Open PanoVisu Editor
2. **Create a project** - File > New Project
3. **Add panoramas** - Import your 360° photos
4. **Add hotspots** - Connect your panoramas
5. **Customize** - Choose your interface theme
6. **Export** - File > Export Tour
7. **Publish** - Upload generated files to your server

📚 **Complete tutorial:** [Getting Started Guide](https://github.com/llang57/editeurPanovisu/wiki/Getting-Started)

## 🔧 Technologies Used

### Viewer (HTML5/WebGL)
- **Three.js** - 3D rendering engine
- **jQuery** - DOM manipulation
- **OpenLayers** - Interactive maps
- **Hammer.js** - Touch gestures
- **screenfull.js** - Fullscreen handling

### Editor (Java/JavaFX)
- **Java 25** (OpenJDK)
- **JavaFX 19** - Modern graphical interface
- **AtlantaFX / MaterialFX / FlatLaf** - Theme providers (27 themes)
- **Apache Batik** - Runtime SVG rendering
- **JOCL** - OpenCL bindings for GPU acceleration
- **Apache Commons** - Utilities
- **Gson** - AI model configuration
- **`.pvu` format** - Project-specific text format (no XML, no JAXB)

### AI Description Generation
- **Ollama** (local, free) - Models run on your own machine, no data leaves it
- **OpenRouter** (online, optional) - Unified access to Claude, Gemini, GPT and others
- **LocationIQ** (optional, free up to 5,000 requests/day) - Reverse geocoding; markedly improves the accuracy of generated descriptions

### Build and Packaging
- **Maven 3.9+** - Dependency management
- **jpackage** - Native installers
- **Inno Setup** - Windows installer
- **GitHub Actions** - CI/CD

## 🔑 API Keys Configuration

Some features require API keys (Google Maps, Bing Maps, etc.):

1. Copy the template:
   ```bash
   cp api-keys.properties.template api-keys.properties
   ```

2. Edit `api-keys.properties` and add your keys:
   ```properties
   google.maps.api.key=YOUR_GOOGLE_KEY
   bing.maps.api.key=YOUR_BING_KEY
   ```

3. Rebuild the project

📝 **Note:** The file `api-keys.properties` is ignored by Git for security.

## 🛠️ Compilation and Development

### Prerequisites

- **Java JDK 25** or later
- **Maven 3.9+**
- **Git**

### Clone and Build

```bash
# Clone the repository
git clone https://github.com/llang57/editeurPanovisu.git
cd editeurPanovisu

# Build with Maven
mvn clean package

# Run the application
java -jar target/editeurPanovisu-3.1.0-SNAPSHOT.jar
```

### Create Windows Installer

```powershell
# Windows only
.\build-installer.ps1
```

The installer will be generated in `target/installer/`

### Run Tests

The project ships a JUnit suite (15 tests).

```bash
mvn test
```

## 📚 Documentation

- 📖 **[Complete Wiki](https://github.com/llang57/editeurPanovisu/wiki)** - User guide
- 🔧 **[Developer Documentation](doc/development/)** - Architecture and APIs
- 📝 **[Release Notes](RELEASE_NOTES_3.1.0.md)** - Version 3.1.0 changes
- 🐛 **[Troubleshooting Guide](doc/PROBLEMES_SOLUTIONS_V3.1.0.md)** - Common issues
- 💬 **[Discussions](https://github.com/llang57/editeurPanovisu/discussions)** - Community forum

### Version 3.1.0 Documentation

- 🇬🇧 [Announcement](doc/DISCUSSION_V3.1.0_EN.md) • [FAQ](doc/FAQ_V3.1.0_EN.md) • [Migration Guide](doc/MIGRATION_GUIDE_V3.1.0_EN.md) • [Index](doc/INDEX_V3.1.0_EN.md)
- 🇫🇷 [Annonce](doc/DISCUSSION_V3.1.0.md) • [FAQ](doc/FAQ_V3.1.0.md) • [Guide](doc/MIGRATION_GUIDE_V3.1.0.md) • [Index](doc/INDEX_V3.1.0.md)

## 🌐 Browser Compatibility

### Fully Supported
- ✅ **Chrome/Edge** 90+
- ✅ **Firefox** 88+
- ✅ **Safari** 14+
- ✅ **Opera** 76+

### Mobile
- ✅ **Chrome Android** 90+
- ✅ **Safari iOS** 14+
- ✅ **Samsung Internet** 14+

**Requirements:** HTML5, WebGL, JavaScript enabled

## 🆕 What is New

**Current version: 3.4.14** — reliability of AI description generation.

- Model catalogues finally ship with the application: they were missing from every installation, leaving the model list empty.
- Models and prices refreshed; local generation, previously impossible on an ordinary machine, now works.
- Descriptions are more restrained and automatically reviewed: dates, measurements, heritage listings, superlatives and unsupplied proper nouns are flagged.

> ⚠️ No prompt can make hallucination impossible. This makes it rarer and visible; proofreading is still required. Setting a LocationIQ key improves accuracy far more reliably than any wording.

📖 **Full history**: [Changelog](https://github.com/llang57/editeurPanovisu/wiki/Changelog) · [Release notes](https://github.com/llang57/editeurPanovisu/releases)

---

## 🤝 Contributing

Contributions are welcome! Here's how to participate:

1. **Fork** the project
2. Create a **feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. Open a **Pull Request**

### Contribution Areas

- 🐛 Bug fixes
- ✨ New features
- 📝 Documentation improvements
- 🌍 Translations
- 🧪 Unit tests
- 🎨 UI/UX improvements

**Read:** [Contribution Guide](CONTRIBUTING.md)

## 💬 Support and Contact

- 💬 **[GitHub Discussions](https://github.com/llang57/editeurPanovisu/discussions)** - Questions, ideas, help
- 🐛 **[GitHub Issues](https://github.com/llang57/editeurPanovisu/issues)** - Bug reports
- 📧 **Email:** [contact via GitHub]
- 🌐 **Website:** [lemondea360.fr/panovisu](https://lemondea360.fr/panovisu)

## 📄 License

This project is licensed under the **GNU General Public License v3.0**.

See the [LICENSE](LICENSE) file for details.

```
Copyright (C) 2025 PanoVisu Contributors

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
```

---

## 🌟 Acknowledgments

Special thanks to:
- The **Three.js** team for the 3D engine
- The **Pannellum** project for panoramic rendering
- All **contributors** who help improve PanoVisu
- The **open source community**

---

## 📊 Project Statistics

![GitHub stars](https://img.shields.io/github/stars/llang57/editeurPanovisu?style=social)
![GitHub forks](https://img.shields.io/github/forks/llang57/editeurPanovisu?style=social)
![GitHub issues](https://img.shields.io/github/issues/llang57/editeurPanovisu)
![GitHub pull requests](https://img.shields.io/github/issues-pr/llang57/editeurPanovisu)
![GitHub last commit](https://img.shields.io/github/last-commit/llang57/editeurPanovisu)

---

<div align="center">

**Made with ❤️ by the PanoVisu community**

⭐ **Star the project if you like it!** ⭐

[🔝 Back to top](#-panovisu-editor)

</div>
