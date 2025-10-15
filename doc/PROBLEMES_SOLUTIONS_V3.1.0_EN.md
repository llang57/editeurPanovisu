# 🔧 Known Issues and Solutions - Version 3.1.0

This document lists known issues with their solutions to facilitate support.

---

## 🐛 Migration Issues

### P1: 404 errors persist after re-export

**Symptom:**
```
GET /tour/panovisu/libs/jquerymenu/... → 404
GET /tour/panovisu/libs/openlayers/... → 404
```

**Possible causes:**
1. Browser cache not cleared
2. Files not completely uploaded
3. Incorrect server permissions
4. CDN/Proxy caching old version

**Solutions:**

**Solution 1 - Clear cache**
```bash
# Browser: Ctrl+Shift+R (Windows/Linux) or Cmd+Shift+R (Mac)
# Or F12 > Network > Disable cache
```

**Solution 2 - Verify upload**
```bash
# On server
cd /var/www/my-tour/panovisu/libs/
ls -la | grep -E "(jquery|open)"

# Should display:
# drwxr-xr-x jqueryMenu
# drwxr-xr-x openLayers
```

**Solution 3 - Fix permissions**
```bash
chmod -R 755 /var/www/my-tour/
chown -R www-data:www-data /var/www/my-tour/
```

**Solution 4 - Purge CDN cache**
- Cloudflare: Purge Everything
- OVH: Clear CDN cache
- Other: Check your hosting provider's documentation

---

### P2: Tour displays but interface doesn't work

**Symptom:**
- Panoramic photo displays
- Navigation bar absent/non-functional
- No navigation between photos
- Console: JavaScript errors

**Cause:**
`jqueryMenu` JavaScript files not loading correctly.

**Diagnosis:**
```javascript
// Browser console (F12)
// Look for:
Uncaught ReferenceError: jQuery is not defined
Failed to load resource: jquerymenu/...
```

**Solution:**
1. Verify folder is named `jqueryMenu` (capital M)
2. Check `panovisuInit.js` lines 164-165:
   ```javascript
   require(['panovisu/libs/jqueryMenu/...'
   require(['panovisu/libs/openLayers/...'
   ```
3. Re-export if necessary

---

### P3: OpenStreetMap map doesn't display

**Symptom:**
- Tour works
- "Map" tab empty or error
- Console: OpenLayers-related errors

**Cause:**
OpenLayers files not found (case issue).

**Diagnosis:**
```javascript
// Console
Failed to load resource: openlayers/... → 404
```

**Solution:**
Same procedure as P1, focus on `openLayers/`.

---

## 🔧 Installation Issues

### P4: Windows - "Application cannot start"

**Symptom:**
```
The application failed to start correctly (0xc000007b)
```

**Cause:**
Missing C++ runtime (rare, normally included).

**Solution:**
```bash
# Install Microsoft Visual C++ Redistributables
# Download from:
https://aka.ms/vs/17/release/vc_redist.x64.exe

# Install, then restart EditeurPanovisu
```

---

### P5: macOS - "Application is damaged"

**Symptom:**
```
"EditeurPanovisu.app" is damaged and can't be opened.
```

**Cause:**
Gatekeeper blocking unsigned application.

**Solution:**
```bash
# Terminal
sudo xattr -cr /Applications/EditeurPanovisu.app

# Or via Interface
# Right-click > Open (then confirm)
```

---

### P6: Linux - Missing dependencies

**Symptom:**
```bash
dpkg: dependency problems prevent configuration of editeurpanovisu
```

**Cause:**
Missing system packages.

**Debian/Ubuntu Solution:**
```bash
sudo apt-get update
sudo apt-get install -f
sudo apt-get install libgtk-3-0 libgl1 libglib2.0-0
```

**Fedora Solution:**
```bash
sudo dnf install gtk3 mesa-libGL glib2
```

---

## 📦 Export Issues

### P7: Export fails with "Write error"

**Symptom:**
```
Export error: Unable to write file...
```

**Possible causes:**
1. Insufficient permissions on destination folder
2. Disk full
3. Path too long (>260 characters on Windows)

**Solutions:**

**Solution 1 - Permissions**
```bash
# Windows: Right-click > Properties > Security
# Add "Full control" for your user

# Linux/macOS
chmod 755 /path/to/destination/
```

**Solution 2 - Disk space**
```bash
# Check available space
# Windows: This PC > Drive properties
# Linux: df -h
```

**Solution 3 - Shorter path**
- Export to `C:\Temp\tour` instead of `C:\Users\...\Documents\...\...\tour`

---

### P8: Export slow / frozen

**Symptom:**
Export takes much longer than usual or seems frozen.

**Causes:**
1. Antivirus scanning each created file
2. Many high-resolution photos
3. Slow disk (HDD vs SSD)

**Solutions:**

**Solution 1 - Antivirus**
```
Add an exception in your antivirus for:
- The export folder
- EditeurPanovisu.exe
```

**Solution 2 - Optimization**
- Export to an SSD if possible
- Close other applications
- Don't use computer during export

---

## 🌐 Server Issues

### P9: "403 Forbidden" on some files

**Symptom:**
```
Forbidden - You don't have permission to access /tour/panovisu/...
```

**Cause:**
Permissions or Apache/Nginx configuration.

**Apache Solution:**
```apache
# .htaccess
<Directory "/var/www/my-tour">
    Options +Indexes +FollowSymLinks
    AllowOverride All
    Require all granted
</Directory>
```

**Nginx Solution:**
```nginx
location /my-tour/ {
    autoindex on;
    try_files $uri $uri/ =404;
}
```

---

### P10: CORS errors (Cross-Origin)

**Symptom:**
```javascript
Access to XMLHttpRequest blocked by CORS policy
```

**Cause:**
External APIs (Google Maps, etc.) blocked by CORS.

**Apache Solution:**
```apache
# .htaccess
Header set Access-Control-Allow-Origin "*"
```

**Nginx Solution:**
```nginx
add_header Access-Control-Allow-Origin "*";
```

⚠️ **Warning**: `*` allows all domains. In production, specify your domain.

---

## 🖥️ Compatibility Issues

### P11: Incompatible Java version

**Symptom:**
```
Unsupported class file major version XX
```

**Cause:**
Attempting to run with an old Java version.

**Solution:**
**Should not happen** as Java is embedded in the installer.

If it does happen:
```bash
# Check Java version
java -version

# Should display: OpenJDK 25 or higher
```

Reinstall EditeurPanovisu v3.1.0 (Java included).

---

### P12: Display issues on HiDPI screens

**Symptom:**
- Interface too small or too large
- Blurry text

**Windows Solution:**
```
Right-click on EditeurPanovisu.exe
> Properties > Compatibility
> Change high DPI settings
> Check "Override high DPI scaling behavior..."
> Select "System"
```

**macOS Solution:**
Interface adapts automatically (Retina).

**Linux Solution:**
```bash
export GDK_SCALE=2
export GDK_DPI_SCALE=0.5
editeurpanovisu
```

---

## 🔍 Diagnostic Issues

### P13: Can't find logs

**Log locations:**

**Windows:**
```
%APPDATA%\EditeurPanovisu\logs\editeurpanovisu.log
# Or
C:\Users\YourName\AppData\Roaming\EditeurPanovisu\logs\
```

**macOS:**
```
~/Library/Application Support/EditeurPanovisu/logs/editeurpanovisu.log
```

**Linux:**
```
~/.config/EditeurPanovisu/logs/editeurpanovisu.log
# Or
~/.editeurpanovisu/logs/editeurpanovisu.log
```

**Useful commands:**
```bash
# Display last errors
tail -n 50 editeurpanovisu.log | grep ERROR

# Follow logs in real-time
tail -f editeurpanovisu.log
```

---

### P14: Uncertain installed version

**Check version:**

**Via interface:**
- Menu `Help > About`
- Should display: "Version 3.1.0 - Build 2609"

**Via files:**
```bash
# Windows
type "C:\Program Files\EditeurPanovisu\app\build.num"

# Linux/macOS
cat /opt/editeurpanovisu/app/build.num
```

Should contain: `build.number=2609`

---

## 🆘 Unresolved Issues

If your issue is not listed here:

### Step 1: Collect information

- EditeurPanovisu version
- Operating system (exact version)
- `editeurpanovisu.log` file
- Error screenshots
- Steps to reproduce the problem

### Step 2: Search

- **GitHub Discussions**: https://github.com/llang57/editeurPanovisu/discussions
- **Existing Issues**: https://github.com/llang57/editeurPanovisu/issues

### Step 3: Report

If undocumented issue:
1. https://github.com/llang57/editeurPanovisu/issues/new
2. "Bug Report" template
3. Provide all collected information

---

## 📊 Issue Statistics

### Issue Frequency

| Issue | Frequency | Severity | Resolution Time |
|-------|-----------|----------|-----------------|
| P1 - 404 after export | ⭐⭐⭐⭐⭐ | 🔴 High | 5-10 min |
| P2 - Non-functional interface | ⭐⭐⭐⭐ | 🔴 High | 10 min |
| P5 - macOS "damaged" | ⭐⭐⭐ | 🟡 Medium | 2 min |
| P7 - Export error | ⭐⭐⭐ | 🟡 Medium | 5 min |
| P9 - 403 Forbidden | ⭐⭐ | 🟡 Medium | 15 min |
| P10 - CORS | ⭐⭐ | 🟢 Low | 5 min |
| P11 - Incompatible Java | ⭐ | 🔴 High | 10 min |

---

## 🔄 Document Updates

This document is regularly updated with newly reported issues.

**Last updated**: October 16, 2025  
**Document version**: 1.0  
**Covers**: EditeurPanovisu v3.1.0 (build 2609)

---

**💡 Found a solution to an unlisted issue?**  
Share it in GitHub Discussions! It will be added to this document.
