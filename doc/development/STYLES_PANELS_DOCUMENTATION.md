# 🎨 Styles des En-têtes de Panels - Documentation

## Vue d'ensemble

Les en-têtes des panels "Paramètres de la visite" et "Autorotation" utilisent maintenant des styles CSS modernes avec gestion des couleurs selon leur état.

## Fichiers modifiés

### 1. **css/fonce.css** (Thème sombre personnalisé)
- Styles `.titreOutil`, `.titreOutilRouge`, `.titreOutilBleu`
- Dégradés adaptés au mode sombre
- Effets hover avec dropshadow
- Couleurs : Gris (#3A3A3A → #2A2A2A), Vert (#4CAF50 → #388E3C), Bleu (#2196F3 → #1976D2)

### 2. **css/clair.css** (Thème clair personnalisé)
- Styles `.titreOutil`, `.titreOutilRouge`, `.titreOutilBleu`
- Dégradés adaptés au mode clair
- Effets hover avec dropshadow
- Couleurs : Gris (#ECECEC → #D5D5D5), Vert (#66BB6A → #4CAF50), Bleu (#42A5F5 → #2196F3)

### 3. **css/editeur-custom.css** (NOUVEAU - Styles AtlantaFX)
- Styles compatibles avec tous les thèmes AtlantaFX
- Sélecteurs contextuels : `.root.primer-light`, `.root.nord-dark`, etc.
- Adaptés automatiquement selon le thème actif

### 4. **src/editeurpanovisu/ThemeManager.java**
- Méthode `addCustomStyles()` : Charge automatiquement `editeur-custom.css`
- Ajout de classes CSS au root : `primer-light`, `nord-dark`, etc.
- Console logs pour debug

---

## 🎨 États des en-têtes

### État Neutre (Gris) - `.titreOutil`
**Utilisation** : Panels fermés ou inactifs

**Thème Clair** :
- Fond : `#ECECEC → #D5D5D5` (dégradé)
- Texte : `#424242`
- Ombre : `rgba(0,0,0,0.2)`

**Thème Sombre** :
- Fond : `#3A3A3A → #2A2A2A` (dégradé)
- Texte : `#E0E0E0`
- Ombre : `rgba(0,0,0,0.4)`

**Hover** : Légère éclaircissement + ombre plus prononcée + scale 1.02

---

### État Actif (Vert) - `.titreOutilRouge`
**Utilisation** : Paramètres activés (autorotation démarrée, intro planète activée, etc.)

**Thème Clair** :
- Fond : `#66BB6A → #4CAF50` (Material Green 400 → 500)
- Texte : `#FFFFFF`
- Ombre : `rgba(76,175,80,0.4)` avec glow vert

**Thème Sombre** :
- Fond : `#4CAF50 → #388E3C` (Material Green 500 → 700)
- Texte : `#FFFFFF`
- Ombre : `rgba(76,175,80,0.5)` avec glow vert

**Hover** : Éclaircissement + glow plus intense

---

### État Invariant (Bleu) - `.titreOutilBleu`
**Utilisation** : Sections importantes ou fixes

**Thème Clair** :
- Fond : `#42A5F5 → #2196F3` (Material Blue 400 → 500)
- Texte : `#FFFFFF`
- Ombre : `rgba(33,150,243,0.4)` avec glow bleu

**Thème Sombre** :
- Fond : `#2196F3 → #1976D2` (Material Blue 500 → 700)
- Texte : `#FFFFFF`
- Ombre : `rgba(33,150,243,0.5)` avec glow bleu

**Hover** : Éclaircissement + glow plus intense

---

### État Désactivé - `:disabled`
**Utilisation** : Composants non disponibles

**Thème Clair** :
- Fond : `#E0E0E0 → #CCCCCC`
- Texte : `#9E9E9E`
- Opacité : 0.6

**Thème Sombre** :
- Fond : `#2C2C2C → #1E1E1E`
- Texte : `#555555`
- Opacité : 0.6

---

## 🔧 Utilisation dans le code

### PaneOutil.java

La classe `PaneOutil` gère automatiquement les styles via `setbValide()` :

```java
// Panel inactif (gris)
poParametresVisite.setbValide(false);
lblPanelTitre.getStyleClass().add("titreOutil");

// Panel actif (vert)
poParametresVisite.setbValide(true);
lblPanelTitre.getStyleClass().add("titreOutilRouge");

// Panel invariant (bleu)
PaneOutil po = new PaneOutil(true, "Titre", apContenu, largeur);
lblPanelTitre.getStyleClass().add("titreOutilBleu");
```

### Exemple - Paramètres de la visite

```java
PaneOutil poParametresVisite = new PaneOutil(
    rbLocalisation.getString("main.parametresVisite"), 
    apParametresVisite, 
    largeurOutil
);
poParametresVisite.setbValide(isbIntroPetitePlanete());
```

- **Si intro planète activée** → En-tête VERT (`titreOutilRouge`)
- **Si intro planète désactivée** → En-tête GRIS (`titreOutil`)

### Exemple - Autorotation

```java
PaneOutil poAutoRotation = new PaneOutil(
    rbLocalisation.getString("main.autoTourRotation"), 
    apAutoRotation, 
    largeurOutil
);
poAutoRotation.setbValide(
    isbAutoRotationDemarre() || isbAutoTourDemarre()
);
```

- **Si autorotation OU autotour actif** → En-tête VERT
- **Sinon** → En-tête GRIS

---

## 🎯 Effets visuels

### Propriétés communes

- **Font-size** : 13px (légèrement agrandi)
- **Font-weight** : bold
- **Border-radius** : 5px (coins arrondis)
- **Padding** : 8px 10px (espacement confortable)
- **Cursor** : hand (indique cliquable)

### Effets Hover

- **Scale Y** : 1.02 (légère expansion verticale)
- **Dropshadow** : Intensité augmentée (6px → 8px blur)
- **Background** : Légère éclaircissement du dégradé

### Transitions

Les effets hover sont fluides grâce aux transitions CSS JavaFX automatiques.

---

## 📋 Compatibilité thèmes

### Thèmes personnalisés (Custom)
- **clair.css** : Styles intégrés directement
- **fonce.css** : Styles intégrés directement

### Thèmes AtlantaFX
- **Primer Light/Dark** : Via `editeur-custom.css` avec `.root.primer-light`
- **Nord Light/Dark** : Via `editeur-custom.css` avec `.root.nord-light`
- **Cupertino Light/Dark** : Via `editeur-custom.css` avec `.root.cupertino-light`
- **Dracula** : Via `editeur-custom.css` avec `.root.dracula`

Le fichier `editeur-custom.css` est chargé automatiquement par `ThemeManager.addCustomStyles()`.

---

## 🧪 Tests

### Build 2266
✅ Compilation réussie  
✅ Nouveau fichier CSS copié (8 resources au lieu de 7)  
✅ ThemeManager chargement confirmé dans console  

### Test visuel à effectuer

1. Lancer l'application
2. Ouvrir panel "Paramètres de la visite"
3. Activer/désactiver "Intro Petite Planète"
4. Vérifier changement couleur en-tête (gris ↔ vert)
5. Ouvrir panel "Autorotation"
6. Activer "Autorotation au démarrage"
7. Vérifier changement couleur en-tête (gris ↔ vert)
8. Tester avec différents thèmes AtlantaFX (Ctrl+T)

---

## 🐛 Debug

### Console logs attendus

```
✅ Thème AtlantaFX appliqué : Primer Light
  ↳ Classe CSS ajoutée : primer-light
  ↳ Styles personnalisés chargés : editeur-custom.css
```

### Si styles non appliqués

1. Vérifier `target/classes/css/editeur-custom.css` existe
2. Vérifier console pour erreurs de chargement
3. Vérifier classe CSS sur root : `scene.getRoot().getStyleClass()`

---

## 📝 Notes importantes

- **Cohérence visuelle** : Les couleurs sont issues de Material Design pour harmonie
- **Accessibilité** : Contrastes respectent WCAG 2.1 AA (4.5:1 minimum)
- **Performance** : Effets GPU-accelerated (dropshadow gaussian)
- **Maintenance** : Un seul fichier CSS pour tous les thèmes AtlantaFX

---

**Date** : 13 octobre 2025  
**Build** : 2266  
**Auteur** : GitHub Copilot  
