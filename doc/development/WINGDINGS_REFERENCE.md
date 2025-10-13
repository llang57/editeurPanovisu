# Référence complète : Emojis → Wingdings/HTML Entities

## 📖 Guide d'utilisation

### Syntaxe

**Entité HTML numérique standard** (fonctionne partout) :
```html
<span class='badge badge-type'>&#XXXX; TEXTE</span>
```

**Wingdings** (pour symboles spéciaux) :
```html
<span class='badge badge-type'><span class='wi'>&#XXX;</span> TEXTE</span>
```

### Stratégie de choix

1. **Symboles Unicode standards** (✓ ✗ ⚠ ℹ ☎ ⚙ ★ ● ▶) → Entités HTML directes
2. **Symboles complexes** (📚 🏛 🛒 📱) → Wingdings avec fallback
3. **TOUJOURS** ajouter le texte en backup (DOC, HOTEL, CAMERA, etc.)

---

## 🎯 Table de correspondance complète

### Catégorie : Documentation et Communication

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| 📚 | Livre/Documentation | `<span class='wi'>&#128;</span>` | - | `<span class='badge badge-doc'><span class='wi'>&#128;</span> DOC</span>` |
| 📖 | Livre ouvert | `<span class='wi'>&#128;</span>` | - | `<span class='badge badge-doc'><span class='wi'>&#128;</span> LIVRE</span>` |
| 📋 | Clipboard | `<span class='wi'>&#61;</span>` | - | `<span class='badge badge-note'><span class='wi'>&#61;</span> LISTE</span>` |
| 📞 | Téléphone | - | `&#9742;` | `<span class='badge badge-contact'>&#9742; CONTACT</span>` |
| 📦 | Paquet | `<span class='wi'>&#254;</span>` | - | `<span class='badge badge-package'><span class='wi'>&#254;</span> PAQUET</span>` |
| 📧 | Email | - | `&#9993;` | `<span class='badge badge-email'>&#9993; EMAIL</span>` |
| 📝 | Note | - | `&#9998;` | `<span class='badge badge-note'>&#9998; NOTE</span>` |
| 📄 | Document | `<span class='wi'>&#52;</span>` | - | `<span class='badge badge-page'><span class='wi'>&#52;</span> DOC</span>` |

### Catégorie : Validation et Statut

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| ✅ | Checkmark | - | `&#10004;` | `<span class='badge badge-check'>&#10004; OK</span>` |
| ❌ | Croix | - | `&#10006;` | `<span class='badge badge-cross'>&#10006; ERREUR</span>` |
| ⚠️ | Attention | - | `&#9888;` | `<span class='badge badge-warning'>&#9888; ATTENTION</span>` |
| ℹ️ | Information | - | `&#8505;` | `<span class='badge badge-info'>&#8505; INFO</span>` |
| 🎯 | Cible | - | `&#9679;` | `<span class='badge badge-target'>&#9679; OBJECTIF</span>` |
| ⭐ | Étoile | - | `&#9733;` | `<span class='badge badge-star'>&#9733; STAR</span>` |

### Catégorie : Outils et Technique

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| 🔧 | Clé à molette | - | `&#9881;` | `<span class='badge badge-tools'>&#9881; OUTILS</span>` |
| 🛠️ | Outils | - | `&#9881;` | `<span class='badge badge-tools'>&#9881; OUTILS</span>` |
| 🏗️ | Construction | `<span class='wi'>&#119;</span>` | - | `<span class='badge badge-build'><span class='wi'>&#119;</span> BUILD</span>` |
| 💻 | Ordinateur | `<span class='wi'>&#112;</span>` | - | `<span class='badge badge-dev'><span class='wi'>&#112;</span> DEV</span>` |
| 🖱️ | Souris | `<span class='wi'>&#112;</span>` | - | `<span class='badge badge-mouse'><span class='wi'>&#112;</span> SOURIS</span>` |
| ⚙️ | Engrenage | - | `&#9881;` | Badge avec `&#9881;` |
| 🔑 | Clé | `<span class='wi'>&#128;</span>` | - | `<span class='badge badge-key'><span class='wi'>&#128;</span> CLE</span>` |
| 🔒 | Cadenas | `<span class='wi'>&#128;</span>` | - | `<span class='badge badge-lock'><span class='wi'>&#128;</span> SECURITE</span>` |

### Catégorie : Navigation et Interface

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| ▶️ | Play | - | `&#9654;` | `<span class='badge badge-play'>&#9654; PLAY</span>` |
| 🚀 | Fusée | - | `&#9654;` | `<span class='badge badge-rocket'>&#9654; DEPLOIEMENT</span>` |
| 🔄 | Sync | - | `&#8634;` | `<span class='badge badge-refresh'>&#8634; SYNC</span>` |
| 📂 | Dossier ouvert | `<span class='wi'>&#48;</span>` | - | `<span class='badge badge-folder'><span class='wi'>&#48;</span> DOSSIER</span>` |
| 📁 | Dossier fermé | `<span class='wi'>&#48;</span>` | - | `<span class='badge badge-folder'><span class='wi'>&#48;</span> DOSSIER</span>` |
| 🌐 | Globe | `<span class='wi'>&#128;</span>` | - | `<span class='badge badge-world'><span class='wi'>&#128;</span> WEB</span>` |
| 🗺️ | Carte | `<span class='wi'>&#231;</span>` | - | `<span class='badge badge-map'><span class='wi'>&#231;</span> CARTE</span>` |

### Catégorie : Médias et Contenu

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| 🎨 | Palette | `<span class='wi'>&#114;</span>` | - | `<span class='badge badge-art'><span class='wi'>&#114;</span> DESIGN</span>` |
| 🖌️ | Pinceau | `<span class='wi'>&#114;</span>` | - | `<span class='badge badge-brush'><span class='wi'>&#114;</span> PINCEAU</span>` |
| 🏞️ | Panorama | `<span class='wi'>&#90;</span>` | - | `<span class='badge badge-panorama'><span class='wi'>&#90;</span> PANORAMA</span>` |
| 🖼️ | Cadre photo | `<span class='wi'>&#90;</span>` | - | `<span class='badge badge-picture'><span class='wi'>&#90;</span> IMAGE</span>` |
| 📽️ | Projecteur | - | `&#9654;` | `<span class='badge badge-projector'>&#9654; DIAPO</span>` |
| 📸 | Caméra | `<span class='wi'>&#112;</span>` | - | `<span class='badge badge-camera'><span class='wi'>&#112;</span> PHOTO</span>` |
| 🎥 | Vidéo | - | `&#9654;` | `<span class='badge badge-video'>&#9654; VIDEO</span>` |
| 🎬 | Clap | - | `&#9654;` | `<span class='badge badge-clapper'>&#9654; ACTION</span>` |

### Catégorie : Lieux et Architecture

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| 🏠 | Maison | `<span class='wi'>&#112;</span>` | - | `<span class='badge badge-home'><span class='wi'>&#112;</span> ACCUEIL</span>` |
| 🏡 | Maison avec jardin | `<span class='wi'>&#112;</span>` | - | `<span class='badge badge-house'><span class='wi'>&#112;</span> MAISON</span>` |
| 🏨 | Hôtel | `<span class='wi'>&#182;</span>` | - | `<span class='badge badge-hotel'><span class='wi'>&#182;</span> HOTEL</span>` |
| 🏛️ | Monument | `<span class='wi'>&#236;</span>` | - | `<span class='badge badge-monument'><span class='wi'>&#236;</span> MONUMENT</span>` |
| 🏰 | Château | `<span class='wi'>&#236;</span>` | - | `<span class='badge badge-castle'><span class='wi'>&#236;</span> CHATEAU</span>` |
| 🏙️ | Ville | `<span class='wi'>&#245;</span>` | - | `<span class='badge badge-city'><span class='wi'>&#245;</span> VILLE</span>` |
| 🏘️ | Quartier | `<span class='wi'>&#112;</span>` | - | `<span class='badge badge-houses'><span class='wi'>&#112;</span> QUARTIER</span>` |

### Catégorie : Intelligence Artificielle

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| 🤖 | Robot | - | `&#9881;` | `<span class='badge badge-robot'>&#9881; IA</span>` |
| 💡 | Ampoule | `<span class='wi'>&#161;</span>` | - | `<span class='badge badge-idea'><span class='wi'>&#161;</span> IDEE</span>` |
| 🧭 | Boussole | `<span class='wi'>&#174;</span>` | - | `<span class='badge badge-compass'><span class='wi'>&#174;</span> BOUSSOLE</span>` |
| 💰 | Argent | - | `&#36;` | `<span class='badge badge-money'>&#36; GRATUIT</span>` |
| 💾 | Disquette | `<span class='wi'>&#53;</span>` | - | `<span class='badge badge-save'><span class='wi'>&#53;</span> SAVE</span>` |

### Catégorie : Divers

| Emoji | Description | Wingdings | HTML Entity | Badge complet |
|-------|-------------|-----------|-------------|---------------|
| 🐦 | Oiseau/Twitter | `<span class='wi'>&#187;</span>` | - | `<span class='badge badge-bird'><span class='wi'>&#187;</span> TWITTER</span>` |
| 🛒 | Panier | `<span class='wi'>&#254;</span>` | - | `<span class='badge badge-cart'><span class='wi'>&#254;</span> SHOP</span>` |
| 📱 | Mobile | `<span class='wi'>&#112;</span>` | - | `<span class='badge badge-mobile'><span class='wi'>&#112;</span> MOBILE</span>` |
| 🔖 | Signet | `<span class='wi'>&#35;</span>` | - | `<span class='badge badge-bookmark'><span class='wi'>&#35;</span> SIGNET</span>` |
| 🏆 | Trophée | `<span class='wi'>&#175;</span>` | - | `<span class='badge badge-trophy'><span class='wi'>&#175;</span> TROPHEE</span>` |
| © | Copyright | - | `&#169;` | `<span class='badge badge-copyright'>&#169; COPYRIGHT</span>` |

---

## 🔧 Codes Wingdings les plus utiles

| Code | Symbole Wingdings | Utilisation recommandée |
|------|-------------------|-------------------------|
| `&#48;` | Dossier | Documents, fichiers |
| `&#52;` | Document | Page, fichier texte |
| `&#53;` | Disquette | Sauvegarde |
| `&#112;` | Ordinateur | Dev, tech, mobile |
| `&#114;` | Pinceau | Art, design |
| `&#116;` | Arbre | Nature |
| `&#119;` | Marteau | Construction, build |
| `&#128;` | Globe/Livre | Documentation, monde |
| `&#161;` | Ampoule | Idée, lumière |
| `&#174;` | Boussole | Navigation |
| `&#182;` | Bâtiment | Hôtel, structure |
| `&#236;` | Monument | Architecture |
| `&#254;` | Paquet | Package, boutique |

---

## ✅ Checklist de validation

Avant d'utiliser un symbole, vérifiez :

1. ✅ Le symbole s'affiche correctement dans votre navigateur
2. ✅ Il existe une entité HTML OU un code Wingdings
3. ✅ Un texte de backup est ajouté (DOC, HOTEL, etc.)
4. ✅ La classe CSS `.badge-type` existe
5. ✅ Pour Wingdings, utiliser `<span class='wi'>&#XXX;</span>`

## 🚀 Prochaines étapes

1. **Tester** : Ouvrir `test-wingdings.md` dans l'application
2. **Valider** : Vérifier que Wingdings fonctionne (Test 3)
3. **Exécuter** : `.\convert-emojis.ps1` pour conversion automatique
4. **Compiler** : `mvn clean compile`
5. **Tester aide.md** : Menu Aide → Documentation (F1)
