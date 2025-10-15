# 📚 Documentation Version 3.1.0 - Index

Bienvenue dans la documentation complète de la version 3.1.0 de l'Éditeur Panovisu.

---

## 🎯 Navigation rapide

### Pour les nouveaux utilisateurs
- 👉 [Annonce de la version 3.1.0](DISCUSSION_V3.1.0.md) - **Commencez ici**
- 📖 [FAQ - Questions fréquentes](FAQ_V3.1.0.md)
- 📥 [Installation](MIGRATION_GUIDE_V3.1.0.md#-étape-1--installation-de-la-v310)

### Pour les utilisateurs existants
- 🚀 [Guide de migration complet](MIGRATION_GUIDE_V3.1.0.md) - **Étapes détaillées**
- ⚠️ [Actions requises](#-actions-requises)
- 🔧 [Problèmes courants et solutions](PROBLEMES_SOLUTIONS_V3.1.0.md)

### Pour les développeurs
- 📝 [Notes de version détaillées](../RELEASE_NOTES_3.1.0.md)
- 🔍 [Modifications techniques](#-modifications-techniques)
- 🐛 [Signaler un bug](https://github.com/llang57/editeurPanovisu/issues)

---

## ⚠️ Actions requises

### Si vous hébergez des visites sur Linux

**🚨 VOUS DEVEZ ré-exporter toutes vos visites avec la v3.1.0**

1. ✅ [Installer la v3.1.0](MIGRATION_GUIDE_V3.1.0.md#-étape-1--installation-de-la-v310)
2. ✅ [Ré-exporter chaque visite](MIGRATION_GUIDE_V3.1.0.md#-étape-2--ré-export-des-visites)
3. ✅ [Uploader vers le serveur](MIGRATION_GUIDE_V3.1.0.md#-étape-3--upload-vers-le-serveur)
4. ✅ [Tester le résultat](MIGRATION_GUIDE_V3.1.0.md#-étape-4--tests-post-migration)

**Temps estimé** : 2-5 minutes par visite

**Pourquoi ?** Les versions précédentes utilisaient une casse incorrecte pour les chemins de fichiers, causant des erreurs 404 sur les serveurs Linux (sensibles à la casse).

### Si vous hébergez sur Windows

**Aucune action urgente**, mais nous recommandons quand même de mettre à jour pour :
- ✅ Garantir la compatibilité future
- ✅ Éviter les problèmes si vous changez de serveur
- ✅ Bénéficier des corrections de bugs

---

## 📖 Documents disponibles

### 1. [Annonce v3.1.0](DISCUSSION_V3.1.0.md)
**Contenu :**
- Présentation du correctif
- Description du bug corrigé
- Liens de téléchargement pour tous les OS
- Instructions d'installation rapides

**Pour qui :** Tous les utilisateurs  
**Temps de lecture :** 3 minutes

---

### 2. [FAQ - Questions fréquentes](FAQ_V3.1.0.md)
**Contenu :**
- 20 questions/réponses
- Problèmes courants
- Compatibilité
- Installation
- Migration

**Pour qui :** Tous les utilisateurs  
**Temps de lecture :** 10 minutes (lecture sélective)

**Questions phares :**
- [Suis-je affecté par ce bug ?](FAQ_V3.1.0.md#1--suis-je-affecté-par-ce-bug-)
- [Dois-je vraiment ré-exporter ?](FAQ_V3.1.0.md#2--dois-je-vraiment-ré-exporter-toutes-mes-visites-)
- [Mes projets sont-ils compatibles ?](FAQ_V3.1.0.md#13--mes-projets-existants-sont-ils-compatibles-)

---

### 3. [Guide de migration](MIGRATION_GUIDE_V3.1.0.md)
**Contenu :**
- Instructions pas à pas pour tous les OS
- Checklist complète
- Scripts d'automatisation
- Tests post-migration
- Conseils d'optimisation

**Pour qui :** Utilisateurs hébergeant sur Linux  
**Temps de migration :** 30-90 minutes (selon nombre de visites)

**Sections principales :**
- [Installation Windows/macOS/Linux](MIGRATION_GUIDE_V3.1.0.md#-étape-1--installation-de-la-v310)
- [Processus de ré-export](MIGRATION_GUIDE_V3.1.0.md#-étape-2--ré-export-des-visites)
- [Upload et tests](MIGRATION_GUIDE_V3.1.0.md#-étape-3--upload-vers-le-serveur)

---

### 4. [Problèmes et solutions](PROBLEMES_SOLUTIONS_V3.1.0.md)
**Contenu :**
- 14 problèmes documentés avec solutions
- Commandes de diagnostic
- Scripts de correction
- Emplacements des logs

**Pour qui :** Support technique, dépannage  
**Temps de lecture :** 15 minutes (référence)

**Problèmes fréquents :**
- [P1 - Erreurs 404 persistent](PROBLEMES_SOLUTIONS_V3.1.0.md#p1-erreurs-404-persistent-après-ré-export)
- [P5 - macOS "Application endommagée"](PROBLEMES_SOLUTIONS_V3.1.0.md#p5-macos---application-endommagée)
- [P7 - Export échoue](PROBLEMES_SOLUTIONS_V3.1.0.md#p7-export-échoue-avec-erreur-décriture)

---

### 5. [Notes de version](../RELEASE_NOTES_3.1.0.md)
**Contenu :**
- Changelog détaillé
- Modifications techniques
- Checksums des fichiers
- Informations de build

**Pour qui :** Développeurs, admins système  
**Temps de lecture :** 5 minutes

---

## 🔍 Modifications techniques

### Fichiers modifiés dans v3.1.0

**Code source :**
```
src/editeurpanovisu/EditeurPanovisu.java
  Lignes 2675, 2681-2682 : Correction des chemins
  jquerymenu → jqueryMenu
  openlayers → openLayers

panovisu/panovisuInit.js
  Lignes 164-165 : Correction des imports AMD
  require(['panovisu/libs/jqueryMenu/...
  require(['panovisu/libs/openLayers/...
```

**Informations de build :**
- Version : 3.1.0
- Build : 2609
- Date : 16 octobre 2025
- Commit : 93ece95
- Tag Git : v3.1.0

---

## 📥 Téléchargements

### Installeurs officiels

| Plateforme | Fichier | Taille | Lien |
|------------|---------|--------|------|
| 🪟 Windows | EditeurPanovisu-Setup-3.1.0.exe | ~188 MB | [Télécharger](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-Setup-3.1.0.exe) |
| 🍎 macOS | EditeurPanovisu-3.1.0.dmg | ~180 MB | [Télécharger](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/EditeurPanovisu-3.1.0.dmg) |
| 🐧 Linux (Debian) | editeurpanovisu_3.1.0_amd64.deb | ~175 MB | [Télécharger](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu_3.1.0_amd64.deb) |
| 🐧 Linux (RPM) | editeurpanovisu-3.1.0.x86_64.rpm | ~175 MB | [Télécharger](https://github.com/llang57/editeurPanovisu/releases/download/v3.1.0/editeurpanovisu-3.1.0.x86_64.rpm) |

**Page de release complète :** https://github.com/llang57/editeurPanovisu/releases/tag/v3.1.0

---

## 🛠️ Support et aide

### Obtenir de l'aide

**GitHub Discussions** (recommandé) :
- 💬 [Poser une question](https://github.com/llang57/editeurPanovisu/discussions/categories/q-a)
- 💡 [Proposer une amélioration](https://github.com/llang57/editeurPanovisu/discussions/categories/ideas)
- 📢 [Annonces](https://github.com/llang57/editeurPanovisu/discussions/categories/announcements)

**GitHub Issues** (bugs uniquement) :
- 🐛 [Signaler un bug](https://github.com/llang57/editeurPanovisu/issues/new?template=bug_report.md)
- ✨ [Demander une fonctionnalité](https://github.com/llang57/editeurPanovisu/issues/new?template=feature_request.md)

**Documentation principale :**
- 📚 [Site de documentation](https://llang57.github.io/editeurPanovisu/)
- 📖 [README du projet](../README.md)
- 🔧 [Guide de développement](development/README.md)

---

## 🗺️ Roadmap

### Version 3.1.x (maintenance)
- 🔄 Corrections de bugs mineurs
- 📝 Amélioration documentation
- 🧪 Tests supplémentaires

### Version 3.2.0 (future)
- ✨ Nouvelles fonctionnalités à définir
- 🎨 Amélioration de l'interface
- ⚡ Optimisations de performance

**Suivez les discussions pour participer à la roadmap !**

---

## 📊 Statistiques de la release

### Corrections dans v3.1.0
- 🐛 **1 bug critique corrigé** (case-sensitivity Linux)
- 📝 **2 fichiers modifiés** (Java + JavaScript)
- 🔢 **4 lignes de code changées**
- ✅ **100% des tests passent**

### Compatibilité
- ✅ Windows 10/11
- ✅ macOS 11+ (Intel + Apple Silicon)
- ✅ Linux (Ubuntu, Debian, Fedora, CentOS...)
- ✅ Rétro-compatible avec projets v3.0.x

---

## 🎓 Tutoriels et ressources

### Vidéos (à venir)
- 🎥 Installation de la v3.1.0
- 🎥 Migration d'une visite existante
- 🎥 Tests de compatibilité Linux

### Articles
- 📝 [Comprendre le bug de case-sensitivity](https://github.com/llang57/editeurPanovisu/discussions) (à venir)
- 📝 [Meilleures pratiques d'hébergement](https://github.com/llang57/editeurPanovisu/discussions) (à venir)

---

## 📜 Historique des versions

| Version | Date | Type | Highlights |
|---------|------|------|------------|
| **3.1.0** | 16 oct 2025 | 🔧 Correctif | Correction case-sensitivity Linux |
| 3.0.0 | [Date] | 🎉 Majeure | Refonte complète interface |
| 2.x.x | ... | ... | ... |

**Voir toutes les versions :** [Releases GitHub](https://github.com/llang57/editeurPanovisu/releases)

---

## 🤝 Contribuer

### Comment contribuer ?

**Documentation :**
- 📝 Signaler des erreurs dans la doc
- ✍️ Proposer des améliorations
- 🌍 Traduire la documentation

**Code :**
- 🐛 Corriger des bugs
- ✨ Proposer des fonctionnalités
- 🧪 Ajouter des tests

**Communauté :**
- 💬 Aider d'autres utilisateurs
- 📢 Partager vos visites
- ⭐ Donner une étoile au projet !

---

## 📞 Contacts

**Projet GitHub :** https://github.com/llang57/editeurPanovisu  
**Discussions :** https://github.com/llang57/editeurPanovisu/discussions  
**Issues :** https://github.com/llang57/editeurPanovisu/issues

---

## 📄 Licence

Ce projet est sous licence [LICENSE](../LICENSE).

---

**Dernière mise à jour de cet index :** 16 octobre 2025  
**Version couverte :** 3.1.0 (build 2609)

---

<div align="center">

**Merci d'utiliser l'Éditeur Panovisu !** 🎉

[⬆️ Retour en haut](#-navigation-rapide)

</div>
