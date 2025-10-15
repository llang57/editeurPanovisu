# ❓ FAQ - Version 3.1.0 : Questions fréquentes sur le correctif case-sensitivity

## Questions générales

### 1. 🤔 Suis-je affecté par ce bug ?

**Vous êtes affecté si :**
- ✅ Vous hébergez vos visites sur un serveur **Linux** (Apache, Nginx, etc.)
- ✅ Vos visites ont été exportées avec une version **< 3.1.0**
- ✅ Vous constatez des erreurs 404 pour `jquerymenu` ou `openlayers`

**Vous n'êtes PAS affecté si :**
- ❌ Vous hébergez sur **Windows Server** (IIS)
- ❌ Vous testez uniquement en local sur Windows
- ❌ Vous n'avez pas encore publié de visites

---

### 2. 🔄 Dois-je vraiment ré-exporter toutes mes visites ?

**OUI**, si elles sont hébergées sur Linux et ne fonctionnent pas correctement.

**NON**, si :
- Vos visites fonctionnent déjà parfaitement
- Vous êtes sur Windows Server
- Vous n'avez pas encore publié

---

### 3. 💾 Puis-je simplement renommer les dossiers au lieu de ré-exporter ?

**Non recommandé**. Le correctif ne se limite pas aux noms de dossiers, il corrige également :
- Les chemins dans le code JavaScript
- Les références dans les fichiers HTML
- Les imports dans les modules

**Solution sûre** : Ré-exporter avec la v3.1.0.

---

### 4. 🕐 Combien de temps prend la mise à jour ?

**Installation de la v3.1.0 :**
- Windows : ~2 minutes (installation automatique)
- macOS : ~3 minutes
- Linux : ~1 minute (via package manager)

**Ré-export d'une visite :**
- Petite visite (10-20 photos) : ~1 minute
- Visite moyenne (50-100 photos) : ~3-5 minutes
- Grande visite (200+ photos) : ~10-15 minutes

---

## Questions techniques

### 5. 🔍 Comment vérifier si mes visites sont affectées ?

Ouvrez la console du navigateur (F12) sur votre visite publiée et cherchez :

```
GET https://votresite.com/visite/panovisu/libs/jquerymenu/... → 404
GET https://votresite.com/visite/panovisu/libs/openlayers/... → 404
```

Si vous voyez ces erreurs, vous êtes affecté.

---

### 6. 🛠️ Puis-je corriger manuellement sans ré-exporter ?

**Possible mais déconseillé**. Il faudrait :

1. Renommer les dossiers sur le serveur
2. Modifier `panovisuInit.js` lignes 164-165
3. Vérifier tous les chemins dans les fichiers HTML
4. Tester exhaustivement

**Risque** : Oubli de certains fichiers, inconsistances.

**Recommandation** : Ré-exporter, c'est plus rapide et plus sûr.

---

### 7. 📦 Les anciennes versions restent-elles disponibles ?

Les versions précédentes restent téléchargeables, mais **nous recommandons fortement la v3.1.0** pour :
- ✅ Compatibilité Linux garantie
- ✅ Corrections de bugs
- ✅ Support futur

---

### 8. 🔐 Y a-t-il d'autres changements dans la v3.1.0 ?

**Non**. Cette version est un **correctif ciblé** uniquement pour la case-sensitivity. Aucune nouvelle fonctionnalité, pas de changement d'interface.

**Changements :**
- Correction des chemins `jquerymenu` → `jqueryMenu`
- Correction des chemins `openlayers` → `openLayers`
- Build incrémenté à 2609

---

## Questions d'installation

### 9. 🪟 Sur Windows, dois-je désinstaller l'ancienne version ?

**Non nécessaire**. L'installeur Windows détecte automatiquement l'ancienne version et la remplace.

**Chemin d'installation** : Le même que l'ancienne version (généralement `C:\Program Files\EditeurPanovisu`)

---

### 10. 🍎 Sur macOS, comment mettre à jour ?

1. Téléchargez `EditeurPanovisu-3.1.0.dmg`
2. Montez l'image disque
3. Glissez `EditeurPanovisu.app` dans `/Applications` (remplace l'ancien)
4. Éjectez l'image disque

---

### 11. 🐧 Sur Linux, comment gérer les dépendances ?

Les packages `.deb` et `.rpm` incluent **toutes les dépendances** :
- Java Runtime (OpenJDK 25)
- JavaFX 19
- Bibliothèques natives

**Aucune installation manuelle requise**.

---

### 12. 🔄 Puis-je avoir plusieurs versions installées ?

**Non recommandé**. Une seule version devrait être installée pour éviter les conflits.

Si nécessaire pour des tests :
- Windows : Installez dans des dossiers différents
- macOS : Renommez `EditeurPanovisu.app` avant d'installer la nouvelle
- Linux : Utilisez des environnements isolés (containers, VMs)

---

## Questions de migration

### 13. 📂 Mes projets existants sont-ils compatibles ?

**OUI**, à 100%. Les fichiers de projet (`.pano`, `.cfg`, etc.) sont **totalement compatibles**.

Vous pouvez :
- ✅ Ouvrir vos anciens projets dans la v3.1.0
- ✅ Les modifier
- ✅ Les ré-exporter

**Aucune conversion nécessaire**.

---

### 14. 🔙 Puis-je revenir à l'ancienne version si besoin ?

**Oui**, mais ce n'est pas recommandé. Les fichiers de projet sont rétro-compatibles.

**Attention** : Si vous créez de nouveaux projets avec la v3.1.0, ils peuvent ne pas s'ouvrir correctement dans les anciennes versions.

---

### 15. 🌐 Que faire si j'ai déjà des visites publiées qui fonctionnent ?

**Option 1** (Recommandé) : Mettre à jour quand même
- Installe la v3.1.0
- Préparez-vous pour vos prochaines visites
- Garantie de compatibilité future

**Option 2** : Attendre
- Gardez la version actuelle
- Risque sur les futures exports
- Pas de support pour les anciens bugs

---

## Support

### 16. 💬 Où obtenir de l'aide ?

**GitHub Discussions** : https://github.com/llang57/editeurPanovisu/discussions
**Issues (bugs)** : https://github.com/llang57/editeurPanovisu/issues
**Documentation** : https://llang57.github.io/editeurPanovisu/

---

### 17. 🐛 J'ai trouvé un bug, comment le signaler ?

1. Allez sur https://github.com/llang57/editeurPanovisu/issues
2. Cliquez sur "New issue"
3. Décrivez le problème avec :
   - Version d'Éditeur Panovisu
   - Système d'exploitation
   - Étapes pour reproduire
   - Captures d'écran si possible

---

### 18. 💡 Je veux proposer une amélioration ?

Utilisez **GitHub Discussions** dans la catégorie "Ideas" :
https://github.com/llang57/editeurPanovisu/discussions/categories/ideas

---

## Questions de compatibilité

### 19. 🖥️ Quels systèmes sont supportés ?

**Windows :**
- Windows 10 (64-bit)
- Windows 11
- Windows Server 2019+

**macOS :**
- macOS 11 (Big Sur) et supérieur
- Architecture Intel et Apple Silicon (M1/M2/M3)

**Linux :**
- Ubuntu 20.04+
- Debian 11+
- Fedora 35+
- CentOS 8+
- Autres distributions récentes avec glibc 2.31+

---

### 20. 📱 Puis-je utiliser l'éditeur sur tablette/mobile ?

**Non**. L'Éditeur Panovisu est une application **desktop uniquement**.

**Mais** : Les visites exportées fonctionnent parfaitement sur :
- 📱 Smartphones (iOS, Android)
- 📱 Tablettes
- 💻 Tous navigateurs modernes

---

## Avez-vous d'autres questions ?

Posez-les dans cette discussion ! 👇
