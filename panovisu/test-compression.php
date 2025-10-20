<?php
/**
 * Test de compression serveur - PanoVisu
 * 
 * Ce fichier permet de vérifier si le serveur compresse les images
 * et d'identifier les optimisations actives.
 * 
 * Usage: Uploadez ce fichier dans le dossier panovisu/ et accédez-y via navigateur
 */

header('Content-Type: text/html; charset=utf-8');
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Compression Serveur - PanoVisu</title>
    <style>
        body {
            font-family: 'Courier New', monospace;
            background: #1e1e1e;
            color: #d4d4d4;
            padding: 20px;
            line-height: 1.6;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: #252526;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.5);
        }
        h1 { color: #4ec9b0; border-bottom: 2px solid #4ec9b0; padding-bottom: 10px; }
        h2 { color: #569cd6; margin-top: 30px; }
        .good { color: #4ec9b0; font-weight: bold; }
        .warning { color: #ce9178; font-weight: bold; }
        .bad { color: #f48771; font-weight: bold; }
        .info { background: #2d2d30; padding: 15px; border-left: 4px solid #007acc; margin: 10px 0; }
        .test-result { background: #1e1e1e; padding: 15px; margin: 10px 0; border-radius: 4px; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #3e3e42; }
        th { background: #2d2d30; color: #4ec9b0; }
        .code { background: #1e1e1e; padding: 3px 6px; border-radius: 3px; font-family: 'Courier New', monospace; }
        button {
            background: #007acc;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin: 5px;
        }
        button:hover { background: #005a9e; }
        #testResults { margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔍 Test de Compression Serveur - PanoVisu</h1>
        
        <div class="info">
            <strong>Objectif :</strong> Détecter si votre hébergeur compresse ou optimise automatiquement les images JPEG.
        </div>

        <h2>📊 Configuration Serveur</h2>
        <table>
            <tr>
                <th>Paramètre</th>
                <th>Valeur</th>
                <th>Statut</th>
            </tr>
            <tr>
                <td>Serveur Web</td>
                <td class="code"><?php echo $_SERVER['SERVER_SOFTWARE'] ?? 'Inconnu'; ?></td>
                <td>-</td>
            </tr>
            <tr>
                <td>PHP Version</td>
                <td class="code"><?php echo PHP_VERSION; ?></td>
                <td>-</td>
            </tr>
            <tr>
                <td>mod_pagespeed</td>
                <td class="code"><?php echo function_exists('apache_get_modules') && in_array('mod_pagespeed', apache_get_modules()) ? 'ACTIVÉ' : 'Désactivé/Inconnu'; ?></td>
                <td><?php echo function_exists('apache_get_modules') && in_array('mod_pagespeed', apache_get_modules()) ? '<span class="bad">⚠️ PROBLÈME</span>' : '<span class="good">✅ OK</span>'; ?></td>
            </tr>
            <tr>
                <td>mod_deflate</td>
                <td class="code"><?php echo function_exists('apache_get_modules') && in_array('mod_deflate', apache_get_modules()) ? 'ACTIVÉ' : 'Désactivé/Inconnu'; ?></td>
                <td>-</td>
            </tr>
            <tr>
                <td>mod_headers</td>
                <td class="code"><?php echo function_exists('apache_get_modules') && in_array('mod_headers', apache_get_modules()) ? 'ACTIVÉ' : 'Désactivé/Inconnu'; ?></td>
                <td><?php echo function_exists('apache_get_modules') && in_array('mod_headers', apache_get_modules()) ? '<span class="good">✅ OK</span>' : '<span class="warning">⚠️ Vérifier</span>'; ?></td>
            </tr>
            <tr>
                <td>.htaccess</td>
                <td class="code"><?php echo file_exists('.htaccess') ? 'Trouvé (' . filesize('.htaccess') . ' octets)' : 'ABSENT'; ?></td>
                <td><?php echo file_exists('.htaccess') ? '<span class="good">✅ OK</span>' : '<span class="bad">❌ MANQUANT</span>'; ?></td>
            </tr>
        </table>

        <h2>🖼️ Test de Compression d'Images</h2>
        <div class="info">
            Sélectionnez une image panoramique de votre visite pour tester :
        </div>
        
        <input type="text" id="imageUrl" placeholder="URL relative de l'image (ex: images/panovisu0.jpg)" 
               style="width: 70%; padding: 10px; background: #1e1e1e; color: #d4d4d4; border: 1px solid #3e3e42; border-radius: 4px;">
        <button onclick="testImage()">🔍 Tester l'Image</button>
        <button onclick="testMultipleImages()">🔍 Tester Tous les Niveaux</button>
        
        <div id="testResults"></div>

        <h2>📝 Modules Apache Chargés</h2>
        <div class="test-result">
            <?php
            if (function_exists('apache_get_modules')) {
                $modules = apache_get_modules();
                echo '<pre style="color: #d4d4d4; max-height: 400px; overflow-y: auto;">';
                foreach ($modules as $module) {
                    $color = '#d4d4d4';
                    if (stripos($module, 'pagespeed') !== false) $color = '#f48771';
                    if (stripos($module, 'deflate') !== false) $color = '#ce9178';
                    if (stripos($module, 'headers') !== false) $color = '#4ec9b0';
                    echo '<span style="color: ' . $color . ';">' . htmlspecialchars($module) . '</span>' . "\n";
                }
                echo '</pre>';
            } else {
                echo '<p class="warning">⚠️ Fonction apache_get_modules() non disponible (hébergement mutualisé?)</p>';
            }
            ?>
        </div>

        <h2>🛠️ Actions Recommandées</h2>
        <div class="info">
            <strong>Si les images sont compressées :</strong>
            <ol>
                <li>Vérifier que le fichier <span class="code">.htaccess</span> est bien uploadé</li>
                <li>Contacter votre hébergeur pour désactiver l'optimisation d'images</li>
                <li>Vérifier les paramètres CDN (CloudFlare, etc.) si utilisé</li>
                <li>Désactiver "Auto-Minify", "Polish", "Mirage" dans le panneau CDN</li>
            </ol>
        </div>
    </div>

    <script>
        function testImage() {
            const url = document.getElementById('imageUrl').value;
            if (!url) {
                alert('Veuillez entrer une URL d\'image');
                return;
            }

            const results = document.getElementById('testResults');
            results.innerHTML = '<div class="test-result">⏳ Test en cours...</div>';

            fetch(url, { method: 'HEAD', cache: 'no-store' })
                .then(response => {
                    const headers = {};
                    response.headers.forEach((value, key) => {
                        headers[key] = value;
                    });

                    const contentLength = response.headers.get('Content-Length');
                    const contentEncoding = response.headers.get('Content-Encoding');
                    const contentType = response.headers.get('Content-Type');
                    const cacheControl = response.headers.get('Cache-Control');
                    const xPagespeed = response.headers.get('X-Mod-Pagespeed') || response.headers.get('X-Page-Speed');
                    const cfPolish = response.headers.get('Cf-Polished');

                    let html = '<div class="test-result">';
                    html += '<h3>Résultats pour : ' + url + '</h3>';
                    html += '<table>';
                    html += '<tr><th>Header</th><th>Valeur</th><th>Impact</th></tr>';
                    
                    // Taille
                    if (contentLength) {
                        const sizeMB = (contentLength / 1024 / 1024).toFixed(2);
                        const sizeKB = (contentLength / 1024).toFixed(0);
                        const quality = sizeMB > 5 ? 'good' : (sizeMB > 1 ? 'warning' : 'bad');
                        html += `<tr><td>Content-Length</td><td class="${quality}">${sizeMB} MB (${sizeKB} KB)</td><td>${quality === 'bad' ? '❌ COMPRESSÉ' : '✅ OK'}</td></tr>`;
                    } else {
                        html += '<tr><td>Content-Length</td><td class="warning">Non disponible</td><td>-</td></tr>';
                    }

                    // Encodage
                    if (contentEncoding) {
                        html += `<tr><td>Content-Encoding</td><td class="bad">${contentEncoding}</td><td>❌ COMPRESSION ACTIVE</td></tr>`;
                    } else {
                        html += '<tr><td>Content-Encoding</td><td class="good">Aucun</td><td>✅ OK</td></tr>';
                    }

                    // PageSpeed
                    if (xPagespeed) {
                        html += `<tr><td>X-Mod-Pagespeed</td><td class="bad">${xPagespeed}</td><td>❌ OPTIMISATION ACTIVE</td></tr>`;
                    }

                    // CloudFlare Polish
                    if (cfPolish) {
                        html += `<tr><td>Cf-Polished</td><td class="bad">${cfPolish}</td><td>❌ CLOUDFLARE POLISH</td></tr>`;
                    }

                    // Cache Control
                    if (cacheControl) {
                        html += `<tr><td>Cache-Control</td><td>${cacheControl}</td><td>-</td></tr>`;
                    }

                    html += '</table>';

                    // Test de chargement réel
                    html += '<p>🔍 Chargement de l\'image pour vérifier dimensions réelles...</p>';
                    html += '<img id="testImg" src="' + url + '" style="max-width: 100%; display: none;" onload="displayImageInfo(this)">';
                    html += '<div id="imageInfo"></div>';
                    html += '</div>';

                    results.innerHTML = html;
                })
                .catch(error => {
                    results.innerHTML = '<div class="test-result bad">❌ Erreur : ' + error.message + '</div>';
                });
        }

        function displayImageInfo(img) {
            const info = document.getElementById('imageInfo');
            const naturalW = img.naturalWidth;
            const naturalH = img.naturalHeight;
            
            const quality = naturalW >= 4096 ? 'good' : (naturalW >= 2048 ? 'warning' : 'bad');
            
            info.innerHTML = `
                <table>
                    <tr><th>Paramètre</th><th>Valeur</th><th>Statut</th></tr>
                    <tr>
                        <td>Dimensions réelles</td>
                        <td class="${quality}">${naturalW} × ${naturalH} px</td>
                        <td>${quality === 'bad' ? '❌ REDIMENSIONNÉE' : '✅ OK'}</td>
                    </tr>
                </table>
            `;
        }

        function testMultipleImages() {
            // Test des niveaux 0 à 5
            const results = document.getElementById('testResults');
            results.innerHTML = '<div class="test-result">⏳ Test de plusieurs niveaux en cours...</div>';
            
            // TODO: Adapter selon la structure de votre visite
            alert('Fonction à adapter selon la structure de votre visite. Utilisez le test unitaire pour l\'instant.');
        }
    </script>
</body>
</html>
