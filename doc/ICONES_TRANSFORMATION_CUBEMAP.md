# Icônes de transformation Cubemap

**Date** : 14 octobre 2025  
**Fichiers** : `vue-sphere.svg`, `vue-cube.svg`

## Description

Ces deux icônes représentent les transformations entre format équirectangulaire (sphérique) et format cubemap (6 faces).

### vue-sphere.svg (Équirectangulaire → Cubemap)

**Signification** : Transforme une image panoramique équirectangulaire en 6 faces de cube.

**Composition** :
- **Gauche** : Globe avec lignes de longitude/latitude
  - Cercle principal (r=4) représentant la sphère
  - Ligne verticale (méridien)
  - Ligne horizontale (équateur)
  - 2 ellipses (parallèles) pour l'effet 3D
  
- **Centre** : Flèche de transformation →

- **Droite** : 6 faces du cube disposées en croix
  ```
      [Haut]
  [G] [Face] [D] [Arr]
      [Bas]
  ```
  - Face centrale : pleine opacité (0.85)
  - Faces latérales : opacité réduite (0.7)
  - Faces haut/bas : opacité standard

### vue-cube.svg (Cubemap → Équirectangulaire)

**Signification** : Transforme 6 faces de cube en image panoramique équirectangulaire.

**Composition** :
- **Gauche** : 6 faces du cube disposées en croix (même disposition)
  ```
      [Haut]
  [G] [Face] [D] [Arr]
      [Bas]
  ```
  
- **Centre** : Flèche de transformation →

- **Droite** : Globe avec lignes de longitude/latitude
  - Identique au globe de vue-sphere.svg
  - Représente le résultat équirectangulaire

## Détails techniques

### Dimensions
- ViewBox : `0 0 24 24`
- Globe : rayon 4px centré à y=12
- Faces cube : 2x2 px chacune
- Flèche : triangulaire simple

### Lignes de longitude/latitude
- **Méridien** : ligne verticale traversant le globe
- **Équateur** : ligne horizontale traversant le globe
- **Parallèles** : 2 ellipses horizontales (rx=4, ry=2)
- **Méridiens secondaires** : 2 ellipses verticales (rx=2, ry=4)

### Disposition cubemap (en croix)
Position classique des faces d'un cube dépliées :
- **Centre (11,11)** : Face avant (front) - opacité 0.85
- **Haut (11,6)** : Face supérieure (top)
- **Bas (11,16)** : Face inférieure (bottom)
- **Gauche (1,11 ou 13,11)** : Face gauche (left) - opacité 0.7
- **Droite (5,11 ou 17,11)** : Face droite (right) - opacité 0.7
- **Arrière (7,11 ou 19,11)** : Face arrière (back) - opacité 0.7

### Couleurs et opacité
- `fill="currentColor"` : S'adapte automatiquement au thème
- Cercle d'arrière-plan : opacité 0.1 (subtil)
- Traits : `stroke="currentColor"` avec `fill="none"`
- Stroke-width : 0.7 pour le cercle principal, 0.5 pour les lignes
- Opacités variables pour créer de la profondeur :
  - Face centrale : 0.85 (la plus visible)
  - Faces latérales : 0.7 (un peu en retrait)
  - Faces haut/bas : 1.0 (standard)

## Utilisation dans l'application

**Menu** : Outils → Équi ↔ Cube  
**Raccourcis** : Boutons dans la barre d'outils

### vue-sphere (Équi → Cube)
Utilisé pour convertir :
- Image panoramique 360° → 6 images séparées
- Format 2:1 équirectangulaire → 6 faces carrées
- Utile pour édition face par face

### vue-cube (Cube → Équi)
Utilisé pour convertir :
- 6 faces de cube → Image panoramique 360°
- 6 images carrées → Format 2:1 équirectangulaire
- Utile pour assembler le panorama final

## Notes de conception

1. **Clarté** : Les icônes doivent être immédiatement compréhensibles
2. **Taille** : Suffisamment grandes (globe r=4, faces 2x2) pour être visibles à 32px
3. **Détails** : Lignes lat/lon pour identifier le globe, disposition en croix pour le cubemap
4. **Flèche** : Triangle simple mais visible pour indiquer la direction de transformation
5. **Symétrie** : Les deux icônes sont symétriques (gauche/droite inversés)
6. **Opacité** : Variations pour créer de la profondeur et hiérarchiser les éléments

## Tests

Pour vérifier le rendu des icônes :
```bash
mvn compile exec:java "-Dexec.mainClass=test.TestThemeDetection"
```

L'icône devrait :
- ✅ Être clairement visible à 32px
- ✅ Montrer distinctement le globe avec ses lignes
- ✅ Montrer les 6 faces du cube en croix
- ✅ Avoir une flèche visible entre les deux
- ✅ S'adapter à la couleur du thème (mauve Dracula, violet Darcula, gris clair/foncé)
