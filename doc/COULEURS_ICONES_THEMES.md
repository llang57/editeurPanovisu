# Guide des couleurs d'icônes SVG par thème

Ce document détaille les couleurs utilisées pour les icônes SVG selon le thème actif.

## 🎨 Thèmes avec couleurs spéciales

### Dracula
- **Couleur** : `#BD93F9` (Mauve Dracula)
- **Thème** : `DRACULA`
- **Description** : Couleur signature du thème Dracula, un mauve vibrant et caractéristique

### FlatLaf Darcula
- **Couleur** : `#9876AA` (Violet Darcula)
- **Thème** : `FLATLAF_DARCULA`
- **Description** : Couleur inspirée d'IntelliJ IDEA, un violet plus sobre et professionnel

## 🌙 Thèmes sombres (couleur par défaut)

Tous les autres thèmes sombres utilisent :
- **Couleur** : `#E0E0E0` (Gris très clair)
- **Thèmes** :
  - `PRIMER_DARK` - Thème GitHub sombre
  - `NORD_DARK` - Thème nordique sombre
  - `CUPERTINO_DARK` - Style macOS sombre
  - `MATERIAL_DARK` - Material Design sombre
  - `FLATLAF_DARK` - FlatLaf sombre
  - `CUSTOM_DARK` - Thème personnalisé foncé

## 🌞 Thèmes clairs (couleur par défaut)

Tous les thèmes clairs utilisent :
- **Couleur** : `#333333` (Gris foncé)
- **Thèmes** :
  - `PRIMER_LIGHT` - Thème GitHub clair
  - `NORD_LIGHT` - Thème nordique clair
  - `CUPERTINO_LIGHT` - Style macOS clair
  - `MATERIAL_LIGHT` - Material Design clair
  - `FLATLAF_LIGHT` - FlatLaf clair
  - `FLATLAF_INTELLIJ` - IntelliJ style clair
  - `CUSTOM_LIGHT` - Thème personnalisé clair

## 📝 Notes techniques

### Détection automatique
La couleur est détectée automatiquement dans `SvgIconLoader.getThemeColor()` en utilisant :
1. `ThemeManager.getCurrentTheme()` pour obtenir le thème actuel
2. `.name()` pour récupérer le nom du thème
3. Vérification si le nom correspond à `DRACULA` ou `FLATLAF_DARCULA`
4. Sinon, utilisation de `.isDark()` pour choisir entre clair/sombre

### Cache
Le cache des icônes est automatiquement vidé lors du changement de thème pour forcer le rechargement avec la nouvelle couleur.

### Ajout de nouveaux thèmes colorés
Pour ajouter une couleur spécifique à un nouveau thème, modifier `SvgIconLoader.getThemeColor()` :

```java
if ("DRACULA".equals(themeName)) {
    return Color.web("#BD93F9"); // Mauve Dracula
} else if ("FLATLAF_DARCULA".equals(themeName)) {
    return Color.web("#9876AA"); // Violet Darcula
} else if ("MON_NOUVEAU_THEME".equals(themeName)) {
    return Color.web("#VOTRE_COULEUR"); // Votre couleur personnalisée
}
```

## 🎨 Palette de couleurs Dracula complète

Pour référence, voici les couleurs officielles du thème Dracula :
- Background : `#282A36`
- Foreground : `#F8F8F2`
- Selection : `#44475A`
- Comment : `#6272A4`
- **Purple (utilisé pour les icônes)** : `#BD93F9`
- Pink : `#FF79C6`
- Cyan : `#8BE9FD`
- Green : `#50FA7B`
- Orange : `#FFB86C`
- Red : `#FF5555`
- Yellow : `#F1FA8C`

## 📚 Références

- [Dracula Theme Official](https://draculatheme.com/)
- [AtlantaFX Documentation](https://github.com/mkpaz/atlantafx)
- [FlatLaf Themes](https://www.formdev.com/flatlaf/)
- [Material Design Colors](https://material.io/design/color/)
