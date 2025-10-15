# 🚀 Quick Migration Guide to v3.1.0

This guide walks you through migrating to version 3.1.0 and fixing your existing tours step by step.

---

## ⏱️ Estimated Time

- **Installation**: 2-3 minutes
- **Per tour to re-export**: 1-5 minutes depending on size
- **Upload to server**: Varies by connection

---

## 📋 Checklist Before Starting

- [ ] Identify all your tours hosted on Linux
- [ ] Verify you have project files (`.pano`, `.cfg`)
- [ ] Note FTP/SFTP upload paths for each tour
- [ ] Backup old versions (optional but recommended)

---

## 🔧 Step 1: Installing v3.1.0

### Windows

1. **Download the installer**
   ```
   https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-Setup-3.1.0.exe
   ```

2. **Run the installer**
   - Double-click `EditeurPanovisu-Setup-3.1.0.exe`
   - Follow the installation wizard
   - The old version will be automatically replaced

3. **Verify installation**
   - Launch the application
   - Menu `Help > About`
   - Check: "Version 3.1.0 - Build 2609"

---

### macOS

1. **Download the DMG**
   ```
   https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-3.1.0.dmg
   ```

2. **Install the application**
   - Mount the `.dmg` file
   - Drag `EditeurPanovisu.app` to `/Applications`
   - Replace if prompted
   - Eject the disk image

3. **First launch**
   - Right-click on `EditeurPanovisu.app` > `Open`
   - Confirm opening (macOS security)

---

### Linux (Debian/Ubuntu)

```bash
# Download
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu_3.1.0_amd64.deb

# Install
sudo dpkg -i editeurpanovisu_3.1.0_amd64.deb

# If dependencies missing
sudo apt-get install -f

# Launch
editeurpanovisu
```

---

### Linux (Fedora/RHEL/CentOS)

```bash
# Download
wget https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu-3.1.0.x86_64.rpm

# Install
sudo rpm -i editeurpanovisu-3.1.0.x86_64.rpm

# Launch
editeurpanovisu
```

---

## 🔄 Step 2: Re-exporting Tours

For **each tour hosted on Linux**:

### 2.1 Open the Project

1. Launch Panovisu Editor v3.1.0
2. `File > Open Project`
3. Select your tour's `.pano` file

### 2.2 Verify the Project

- ✅ Check that all photos display
- ✅ Check links between photos
- ✅ Test navigation
- ✅ Check hotspots

### 2.3 Export the Tour

1. `File > Export Tour`
2. **IMPORTANT**: Choose a temporary folder (not directly on server)
3. Wait for export to complete
4. Verify success message

### 2.4 Verify Export Locally

1. Open `index.html` in a browser
2. Test navigation
3. Open console (F12) - **no errors should appear**
4. Verify these folders exist:
   - `panovisu/libs/jqueryMenu/` (with capital 'M')
   - `panovisu/libs/openLayers/` (with capital 'L')

---

## 📤 Step 3: Upload to Server

### 3.1 Backup (recommended)

Before replacing, backup the old version:

**Via FTP/SFTP:**
```bash
# On server
mv /var/www/my-tour /var/www/my-tour.backup
```

**Via hosting interface:**
- Rename existing folder to `my-tour.old`

### 3.2 Upload New Files

**Option A - Complete replacement:**
```bash
# Delete old folder
rm -rf /var/www/my-tour

# Upload new
scp -r /local/path/exported-tour/ user@server:/var/www/my-tour
```

**Option B - Replace modified files only:**
1. Upload entire `panovisu/` folder (contains fixes)
2. Upload `index.html` and `openstreetmap.html`
3. Other files (photos, config) haven't changed

### 3.3 Verify Permissions

```bash
# On Linux server
chmod -R 755 /var/www/my-tour
chown -R www-data:www-data /var/www/my-tour
```

---

## ✅ Step 4: Post-Migration Tests

### 4.1 Browser Test

1. Open tour: `https://yoursite.com/my-tour/`
2. **Open console** (F12)
3. **Check: no 404 errors**
4. Test navigation between photos
5. Test hotspots
6. Test interactive map (if applicable)
7. Test navigation bar

### 4.2 Multi-Browser Test

Test on:
- ✅ Chrome/Edge
- ✅ Firefox
- ✅ Safari (if possible)
- ✅ Mobile (Chrome Android / Safari iOS)

### 4.3 Multi-Device Test

- ✅ Desktop
- ✅ Tablet
- ✅ Smartphone

---

## 📊 Migration Tracking

Use this checklist to track your tours:

| Tour | URL | Re-exported | Uploaded | Tested | OK |
|------|-----|-------------|----------|--------|-----|
| Example 1 | https://... | ✅ | ✅ | ✅ | ✅ |
| Example 2 | https://... | ✅ | ✅ | ⏳ | ❌ |
| Example 3 | https://... | ⏳ | ❌ | ❌ | ❌ |

---

## 🆘 Troubleshooting

### Problem: "404 errors still present"

**Solutions:**
1. Verify you uploaded **all** files
2. Check permissions on server
3. Clear browser cache (Ctrl+Shift+R)
4. Verify folders have correct case:
   ```bash
   ls -la /var/www/my-tour/panovisu/libs/
   # Should display: jqueryMenu, openLayers (with capitals)
   ```

### Problem: "Tour doesn't display at all"

**Solutions:**
1. Verify `index.html` is at root
2. Check permissions (755 for folders, 644 for files)
3. Check web server logs:
   ```bash
   tail -f /var/log/apache2/error.log
   # or
   tail -f /var/log/nginx/error.log
   ```

### Problem: "Old project won't open"

**Solutions:**
1. Check `.pano` file integrity
2. Check logs: `logs/editeurpanovisu.log`
3. Try opening a backup project

---

## 💡 Tips and Tricks

### Time Optimization

**For multiple tours to migrate:**
1. Install v3.1.0
2. Re-export **all** tours locally (batch)
3. Upload them all at once
4. Test afterward

### Upload Automation

**FTP batch script (example):**
```bash
#!/bin/bash
TOURS=("tour1" "tour2" "tour3")
FTP_USER="your_user"
FTP_HOST="ftp.yourserver.com"

for tour in "${TOURS[@]}"; do
    echo "Uploading $tour..."
    lftp -u $FTP_USER,$FTP_PASS $FTP_HOST <<EOF
    mirror -R /local/$tour /public_html/$tour
    bye
EOF
done
```

### Migration Documentation

Keep a record:
- Migration date
- Version before/after
- Issues encountered
- Tests performed

---

## 📞 Need Help?

- **GitHub Discussions**: https://github.com/llang57/editeurPanovisu/discussions
- **Technical Issues**: https://github.com/llang57/editeurPanovisu/issues
- **Detailed FAQ**: [FAQ_V3.1.0_EN.md](FAQ_V3.1.0_EN.md)

---

## ✨ After Migration

Once all your tours are migrated:

1. ✅ Delete backups (`.backup`, `.old`)
2. ✅ Update your internal documentation
3. ✅ Inform your clients/users if necessary
4. ✅ Uninstall old versions

**Congratulations! Your tours are now compatible with all servers!** 🎉
