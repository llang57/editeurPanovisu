# 🎨 Thèmes Disponibles - EditeurPanovisu

## Vue d'ensemble

EditeurPanovisu supporte maintenant **19 thèmes** différents provenant de 5 bibliothèques :
- **AtlantaFX** : 7 thèmes
- **JMetro** : 2 thèmes
- **FlatLaf** : 4 thèmes
- **MaterialFX** : 2 thèmes
- **Personnalisés** : 2 thèmes (legacy CSS)

---

## 🌞 Thèmes Clairs (8 thèmes)

### AtlantaFX (3 thèmes clairs)

1. **Primer Light** ⭐ *Par défaut*
   - Style moderne GitHub
   - Couleurs douces et professionnelles
   - Excellent pour une utilisation quotidienne

2. **Nord Light**
   - Palette nordique apaisante
   - Tons bleus et verts pastel
   - Idéal pour réduire la fatigue oculaire

3. **Cupertino Light**
   - Style macOS/iOS
   - Design minimaliste et épuré
   - Interface claire et aérée

### JMetro (1 thème clair)

4. **JMetro Light**
   - Design Windows Metro/Fluent
   - Style moderne Microsoft
   - Animations fluides

### FlatLaf (2 thèmes clairs)

5. **FlatLaf Light**
   - Interface plate moderne
   - Inspiré des IDEs modernes
   - Haute lisibilité

6. **FlatLaf IntelliJ**
   - Thème officiel IntelliJ IDEA
   - Familier pour les développeurs Java
   - Excellent contraste

### MaterialFX (1 thème clair)

7. **Material Light**
   - Material Design de Google
   - Ombres et élévations
   - Interface tactile-friendly

### Personnalisé (1 thème clair)

8. **Thème Clair Personnalisé**
   - CSS legacy de l'application
   - Style original EditeurPanovisu
   - Compatible avec anciennes configurations

---

## 🌙 Thèmes Sombres (11 thèmes)

### AtlantaFX (4 thèmes sombres)

9. **Primer Dark**
   - Version sombre de Primer
   - Fond noir doux
   - Bon équilibre luminosité

10. **Nord Dark**
    - Palette nordique sombre
    - Tons bleus profonds
    - Très reposant pour les yeux

11. **Cupertino Dark**
    - Dark mode macOS
    - Élégant et minimaliste
    - Contraste optimal

12. **Dracula** 🧛
    - Thème culte pour développeurs
    - Violet profond caractéristique
    - Excellent pour sessions nocturnes

### JMetro (1 thème sombre)

13. **JMetro Dark**
    - Windows Metro sombre
    - Style moderne Microsoft
    - Fond gris anthracite

### FlatLaf (2 thèmes sombres)

14. **FlatLaf Dark**
    - Interface plate sombre
    - Gris neutres
    - Fatigue oculaire minimale

15. **FlatLaf Darcula**
    - Inspiré du thème IntelliJ Darcula
    - Très populaire chez les développeurs
    - Contraste parfait pour le code

### MaterialFX (1 thème sombre)

16. **Material Dark**
    - Material Design sombre
    - Fond noir profond (#121212)
    - Ombres adaptées au dark mode

### Personnalisé (1 thème sombre)

17. **Thème Foncé Personnalisé**
    - CSS legacy sombre
    - Style original EditeurPanovisu
    - Compatible avec anciennes configurations

---

## 📊 Tableau Comparatif

| Thème | Bibliothèque | Type | Caractéristiques |
|-------|-------------|------|------------------|
| Primer Light | AtlantaFX | Clair | Moderne, GitHub-style |
| Nord Light | AtlantaFX | Clair | Nordique, apaisant |
| Cupertino Light | AtlantaFX | Clair | macOS-style |
| JMetro Light | JMetro | Clair | Windows Metro |
| FlatLaf Light | FlatLaf | Clair | Plat, moderne |
| FlatLaf IntelliJ | FlatLaf | Clair | IDE-style |
| Material Light | MaterialFX | Clair | Material Design |
| Custom Light | Custom | Clair | Legacy |
| Primer Dark | AtlantaFX | Sombre | Moderne, équilibré |
| Nord Dark | AtlantaFX | Sombre | Nordique profond |
| Cupertino Dark | AtlantaFX | Sombre | macOS dark mode |
| Dracula | AtlantaFX | Sombre | Violet iconique |
| JMetro Dark | JMetro | Sombre | Windows Metro |
| FlatLaf Dark | FlatLaf | Sombre | Gris neutres |
| FlatLaf Darcula | FlatLaf | Sombre | IntelliJ-style |
| Material Dark | MaterialFX | Sombre | Material Design |
| Custom Dark | Custom | Sombre | Legacy |

---

## 🔧 Dépendances Maven

```xml
<!-- AtlantaFX -->
<dependency>
    <groupId>io.github.mkpaz</groupId>
    <artifactId>atlantafx-base</artifactId>
    <version>2.0.1</version>
</dependency>

<!-- JMetro -->
<dependency>
    <groupId>org.jfxtras</groupId>
    <artifactId>jmetro</artifactId>
    <version>11.6.16</version>
</dependency>

<!-- MaterialFX -->
<dependency>
    <groupId>io.github.palexdev</groupId>
    <artifactId>materialfx</artifactId>
    <version>11.17.0</version>
</dependency>

<!-- FlatLaf -->
<dependency>
    <groupId>com.formdev</groupId>
    <artifactId>flatlaf</artifactId>
    <version>3.5.4</version>
</dependency>
```

---

## 💡 Utilisation

### Changer de thème par code

```java
// Appliquer un thème spécifique
ThemeManager.applyTheme(scene, ThemeManager.Theme.DRACULA);

// Charger le thème sauvegardé
ThemeManager.applySavedTheme(scene);

// Basculer clair/sombre
ThemeManager.toggleDarkMode(scene);
```

### Obtenir les thèmes disponibles

```java
// Tous les thèmes
Theme[] allThemes = ThemeManager.getAllThemes();

// Seulement clairs
Theme[] lightThemes = ThemeManager.getLightThemes();

// Seulement sombres
Theme[] darkThemes = ThemeManager.getDarkThemes();
```

---

## 🎯 Recommandations d'utilisation

### Pour le développement
- **FlatLaf IntelliJ** (clair) ou **FlatLaf Darcula** (sombre)
- Familiers pour les développeurs Java
- Excellent contraste pour le code

### Pour la productivité
- **Primer Light** (clair) ou **Primer Dark** (sombre)
- Design moderne et professionnel
- Équilibre parfait lisibilité/esthétique

### Pour réduire la fatigue oculaire
- **Nord Light** (clair) ou **Nord Dark** (sombre)
- Palette de couleurs apaisante
- Tons pastel non agressifs

### Pour un look moderne
- **Material Light** (clair) ou **Material Dark** (sombre)
- Design Google Material
- Animations et élévations élégantes

### Pour les sessions nocturnes
- **Dracula** ⭐
- Cult classic pour développeurs
- Excellent pour travail de nuit

---

## 📝 Notes techniques

- Les thèmes sont sauvegardés automatiquement dans les préférences utilisateur
- Le dernier thème utilisé est restauré au démarrage
- Tous les thèmes supportent les CSS personnalisés complémentaires
- FlatLaf et MaterialFX utilisent des CSS JavaFX adaptés (non natifs)

---

## 🔮 Ajouts futurs possibles

- BootstrapFX
- Thèmes Solarized Light/Dark
- Thèmes personnalisés additionnels
- Éditeur de thème visuel

---

*Dernière mise à jour : Build 2322*
