# 🌐 PanoVisu Editor

[![Version](https://img.shields.io/badge/version-3.3.3-blue.svg)](https://github.com/llang57/editeurPanovisu/releases)
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

## 🚀 Version 3.3.3 - Simplified Linux Portable Support

**What's new?**

### 🐧 Portable Linux Distribution (Build 3597+ - Oct 23, 2025)

**Standalone portable archive** 📦
- **ZIP and TAR.GZ**: Complete archives without system dependencies
- **Ultra-simple installation**: Extract, `chmod +x`, run
- **Triple format documentation**:
  - `INSTALLATION.md`: Complete Markdown guide with all details
  - `INSTALLATION.txt`: Text version for terminals (80 columns)
  - `INSTALLATION.html`: Styled web version with modern CSS
- **Intelligent bash script**:
  - Automatic Java detection and version check (recommends Java 25+)
  - Optimal JavaFX 3D configuration (PRISM_FORCEGL, PRISM_ORDER)
  - Clear error messages with solutions
- **Clean structure**: Windows files (.bat/.vbs) automatically excluded
- **Installation guide**: See [doc/install/INSTALLATION.md](doc/install/INSTALLATION.md)

**Critical Linux fixes** 🐛
- ✅ **Panorama cache**: Cache usage fixed (700ms → <50ms)
- ✅ **Modal blocking**: Configuration window no longer freezes (Platform.runLater + show())
- ✅ **NullPointerException**: Protection against crashes on reload (unloaded images)
- ✅ **Maven build**: All classes included in JAR (93/93 classes)

### 🎯 Enhanced 3D Panoramic Viewer (Build 3417 - Oct 20, 2025)
- **📱 Modernized interface with icons**:
  - Replaced text buttons with **intuitive PNG icons** (home, photo, compass, eye)
  - **Automatic theme adaptation**: white icons for dark themes, black for light themes
  - **Informative tooltips**: contextual help on hover for each button
  - Buttons with **hover effect**: opacity 0.6 → 1.0 for visual feedback
  - **Optimized positioning**: labels at top, 3D viewer in center, 5 buttons at bottom
- **🖼️ High-resolution fullscreen mode**:
  - **Dedicated popup window** (1200×780) instead of system fullscreen
  - **Doubled resolution**: loads original image without reduction (iRapport=1 instead of 2)
  - **4× superior cube quality**: **1000×1000 pixel** faces (instead of 500×500)
  - Intermediate equirectangular image of **3000×1500** (instead of 1500×750)
  - **Perfect preservation**: 8192×4096 image → 8192×4096 rendering (vs 4096×1024 in normal mode)
  - **Configurable high-quality mode**: activatable flag for high-resolution displays
- **🎨 Enhanced error handling**:
  - File existence verification before loading
  - **Detailed logs**: image dimensions, file paths, GPU performance
  - **Optimal spacing**: height calculated with 100px margin to avoid cut-off buttons
- **⚡ GPU Performance**:
  - **Bicubic resizing**: 8192×4096 → 3000×1500 in ~350ms
  - **Equi→Cube conversion**: 6 faces generation 1000×1000 in ~125ms
  - Total processing < 500ms for maximum quality

### 🗺️ Maps and Geolocation Refactoring (Builds 3376-3416)
- **🌐 Migration to Leaflet**:
  - Replaced NavigateurCarteGluon with **NavigateurCarte** (pure Leaflet)
  - **Lazy loading** architecture with `onMapReady` callback to avoid JavaFX bugs
  - HTML loading via `load()` preserving relative resource paths
  - Dynamic injection of LocationIQ API key after initialization
- **📍 Complete management API**:
  - **Draggable markers**: mouse-draggable with automatic update
  - **Radar (field of view)**: configurable size 0-240m (×3 vs old 0-80m)
  - **Nominatim geocoding**: address search with OpenStreetMap results
  - **Methods**: `ajouteMarqueur()`, `retireMarqueurs()`, `allerCoordonnees()`, `afficheRadar()`
- **🔧 Critical fixes**:
  - Fixed longitude/latitude inversion in `CoordonneesGeographiques` constructor
  - Distinction between `recupereCoordonnees(0)` (marker) and `recupereCoordonnees()` (center)
  - Asynchronous callback `setOnMapReady()` to avoid "texture is null" (JavaFX 19 bug)
  - `miseAJourRadarSeul()` method to optimize partial updates

### 🎨 Interface and Visualization (Builds 3376-3416)
- **📐 Viewport size adaptation**:
  - Intelligent calculation of container/image ratio for correct extraction
  - Replaced hardcoded dimensions with `getVisualisationWidth()`/`getVisualisationHeight()`
  - Viewport adjusted dynamically according to actual dimensions
- **📱 Modernized social networks**:
  - Replaced Google+/Facebook with **Meta** (unified platform)
  - Removed obsolete references to Google+
  - Streamlined interface with 3 options: Twitter, Meta, Email
- **🎛️ Enhanced custom bar**:
  - **Configurable opacity**: slider 0-1 with hover effect (opacity → 1.0 on hover)
  - Remote control and navigation bar with adjustable transparency
  - Improved visibility while preserving immersion

### 🛡️ Fixes and Stability (Builds 3376-3416)
- **🖼️ Invalid image protection**:
  - `BufferedImage` verification before JPEG writing (slideshow)
  - Skip corrupted images instead of crashing the application
  - Warning message in console with filename
- **🎯 UI positioning**:
  - Fixed interface element offsets (labels, buttons, controls)
  - Consistent spacing via `PANEL_TOP_MARGIN` and `PANEL_ELEMENT_SPACING`
  - 3-zone architecture for geolocation: Header / Map / Control panel
- **⏱️ Asynchronous management**:
  - Lazy loading map (on first geolocation click)
  - `carteEnCoursDeChargement` flag to avoid multiple re-configurations
  - WebEngine state listeners for Java/JavaScript synchronization

### ⚡ GPU Acceleration (OpenCL)
- **🎮 GPU Processing**: Hardware acceleration for all image processing operations
  - **Panoramic transformations**: Equirectangular ↔ Cube conversion **3.3× faster**
  - **Image resizing**: High-quality Bicubic and Lanczos3 algorithms on GPU
  - **Tour display**: Panoramic rendering **10× faster**
  - **Level of Detail (LOD)**: Accelerated progressive level generation
  - **Automatic fallback**: Switches to CPU if GPU unavailable
- **📊 Performance gains**:
  - Panoramic tour loading: **3.4× faster** (15s → 4.5s)
  - Batch resizing: **1.7× faster**
  - Screen display: **10× faster** (1000ms → 100ms)
  - Visual quality: Bicubic/Lanczos3 eliminates aliasing
- **🎨 Improved image quality**:
  - Bicubic interpolation replaces Nearest Neighbor
  - Lanczos3 interpolation for ×2+ enlargements
  - Reduced aliasing and better anti-aliasing

### 🎨 Modernized Interface and Slideshow Viewer
- **🖥️ Modernized editor interface**: Complete redesign of creation/editing windows
  - **Slideshow creation interface**: Clean design with automatic theme management
  - **Integrated HTML editor**: Modern JavaFX WYSIWYG for rich content (HTML hotspots)
  - **Custom navigation bar creation**: Intuitive interface with drag & drop and real-time preview
  - **Theme-Aware Design**: All windows automatically adapt to theme (light/dark)
  - **Ergonomic fixes**: Optimal resizing, properly positioned buttons
  - **Visual consistency**: Removed hardcoded colors, using theme variables
- **📽️ Modern HTML5 slideshow viewer**: Complete replacement of obsolete Supersized viewer (jQuery 2012)
  - **Material Design**: Elegant interface with glassmorphism and fluid animations
  - **Visual progress bar**: Real-time tracking with fill animation
  - **Intuitive navigation**: Buttons, directional arrows, keyboard, clickable thumbnails
  - **Position indicators**: Dots with hover effect and counter (X/Total)
  - **Complete controls**: Play/Pause, Previous/Next, Fullscreen, Thumbnails
  - **Thumbnail mode**: Gallery with hover and direct selection
  - **Auto-hide**: Controls disappear after 3s of inactivity (reappear on hover)
  - **Responsive**: Automatic adaptation mobile/tablet/desktop
  - **Lightweight and performant**: ~20 KB vs ~150 KB (old), pure HTML5/CSS3/JavaScript code
- **🎭 Slideshow hotspot animations**: 
  - Consistency with photo hotspots: "blink", "pulse", "rotation" animations, etc.
  - Configuration from editor with preview
- **⏸️ Intelligent pause behavior**:
  - Pause/play state respected during manual navigation
  - Image change while paused = stays paused (voluntary action required)
  - Only the Play/Pause button explicitly changes playback state

### 🎨 Enhanced Theme System (24 themes available)
- **🎯 New minimalist themes**: 8 professional flat design themes
  - **4 color palettes**: Blue 🔷🔹, Green 🟢🟩, Red 🔴🟥, Purple 💜🟪
  - **2 variants per palette**: Light and Dark for each color
  - **Sober flat design**: Clean interface without excessive effects, focus on readability
  - **Harmonious accent colors**: Visual consistency throughout the application
- **🎨 Dynamic colored icons**: SVG icons automatically take the theme color
  - Glyph/Ikonli icons colored via CSS
  - SVG icons with intelligent replacement of `currentColor` and `fill="white"`
  - Dynamic conversion to PNG with Apache Batik
- **🌓 Optimized visibility for dark themes**:
  - Lightened form controls (checkboxes, radio buttons, sliders, spinners, progressbars)
  - More contrasted and thickened borders for better identification
  - Menu text always white (normal, hover, dropdown open)
- **📦 Complete collection of 24 themes**:
  - 9 AtlantaFX themes (Primer, Nord, Cupertino, Dracula)
  - 2 MaterialFX themes (Light, Dark)
  - 2 FlatLaf themes (Light/IntelliJ, Dark/Darcula)
  - 2 legacy custom themes (Light, Dark)
  - 2 acidic themes (Light 🌸, Dark 🌌)
  - 2 modern themes (Light 🌿, Dark 🌃)
  - 8 minimalist themes (Blue/Green/Red/Purple × Light/Dark)
- **🔄 Universal application**: FXML welcome window integrated into the theme system

### 🔧 Technical Architecture
- OpenCL 1.2+ support (NVIDIA CUDA, AMD ROCm, Intel compatible)
- Intelligent GPU/CPU auto-routing based on image size
- Robust colorspace management (CMYK, YCbCr, RGB)
- Comprehensive technical documentation (1200+ lines)

---

## 🔧 Version 3.2.0 - Advanced Customization and Smooth Transitions

**What's new?**

### ✨ New Features
- **🎨 Individual Hotspot Customization**: 
  - **16 different animations**: bounce, pulse, flash, shake, swing, tada, wobble, jello, heartbeat, rubberBand, rotate, flip, zoomIn, zoomOut, fadeIn, slideIn
  - **Custom colors**: Choose a unique color for each hotspot with the HSB color picker
  - **Custom icons**: Replace the default icon of each hotspot with an image of your choice, with automatic color transformation
  - **Hover magnification**: Configurable zoom effect for each hotspot
  - **Complete persistence**: Custom icons and colors are saved in PVU projects
  - Complete configuration from the graphical interface with real-time preview
- **🌊 WebGL Crossfade**: Smooth and elegant transitions between panoramas with crossfade effect (2 seconds) using WebGL and shaders for a professional visual experience
- **📦 ZIP Export**: Export your tours directly as ZIP archives for simplified sharing
- **🖼️ Image Resizing**: New tool for resizing and compressing panoramic images
- **📐 2:1 Ratio Conversion**: Improved icon positioning in the user interface

### 🔧 Technical Improvements
- **Robust architecture**: Image dimension validation and enhanced error handling
- **Improved parsing**: Safe handling of empty fields in PVU files
- **Optimized UI**: Hotspot panels widened (+30px) for better ergonomics

### ⚠️ Important - Migration from v3.0.0

If you host tours on Linux servers, note that **v3.1.0** fixed a critical case-sensitivity issue. Tours created with v3.0.0 and hosted on Linux must be re-exported with v3.1.0 or higher.

### 📢 GitHub Discussions
- 🇬🇧 [📢 v3.2.0 Announcement](https://github.com/llang57/editeurPanovisu/discussions) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/9) • [🚀 Guide](https://github.com/llang57/editeurPanovisu/discussions/11)
- 🇫🇷 [📢 Annonce v3.2.0](https://github.com/llang57/editeurPanovisu/discussions) • [❓ FAQ](https://github.com/llang57/editeurPanovisu/discussions/8) • [🚀 Guide](https://github.com/llang57/editeurPanovisu/discussions/10)

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
- **Pannellum** - Panoramic display library
- **jQuery** - DOM manipulation
- **OpenLayers** - Interactive maps
- **RequireJS** - Module management

### Editor (Java/JavaFX)
- **Java 25** (OpenJDK)
- **JavaFX 19** - Modern graphical interface
- **ControlsFX** - Enhanced UI components
- **Apache Commons** - Utilities
- **JSON-java** - Configuration management

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
