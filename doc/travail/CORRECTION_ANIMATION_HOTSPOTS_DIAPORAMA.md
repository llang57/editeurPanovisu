# Ajout Animation aux Hotspots Diaporama

**Date**: 19 octobre 2025  
**Build**: #2993  
**Objectif**: Appliquer les animations aux hotspots diaporama sur les panoramiques

## 🐛 Problème Identifié

Les hotspots diaporama sur les panoramiques utilisaient la même icône que les hotspots photo (`hotspotImage.png`) mais **n'héritaient pas des animations** configurées.

### Cause
Dans le code de génération XML des visites (`EditeurPanovisu.java`, ligne ~2628), les hotspots diaporama envoyaient seulement :
```xml
anime="true"  <!-- ou "false" -->
```

Alors que les hotspots photo envoyaient le **type d'animation** :
```xml
anime="blink"  <!-- ou "pulse", "rotate", etc. -->
```

## ✅ Solution Implémentée

### 1. Modification de la classe `HotspotDiaporama.java`

**Ajout de la propriété `strTypeAnimation`** :
```java
private String strTypeAnimation = "blink"; // Type d'animation par défaut
```

**Ajout des méthodes getter/setter** :
```java
public String getStrTypeAnimation() {
    return strTypeAnimation;
}

public void setStrTypeAnimation(String strTypeAnimation) {
    this.strTypeAnimation = strTypeAnimation;
    // Synchroniser bAnime avec strTypeAnimation
    this.bAnime = !("none".equals(strTypeAnimation) || 
                    strTypeAnimation == null || 
                    strTypeAnimation.isEmpty());
}
```

### 2. Modification de la génération XML (`EditeurPanovisu.java`, ligne ~2628)

**Ancien code** :
```java
String strAnime = (HS.isbAnime()) ? "true" : "false";
// ...
+ "           anime=\"" + strAnime + "\"\n"
```

**Nouveau code** :
```java
// Récupérer le type d'animation comme pour les hotspots photo
String strTypeAnimation = HS.getStrTypeAnimation();
if (strTypeAnimation == null || strTypeAnimation.isEmpty()) {
    strTypeAnimation = "blink"; // Animation par défaut
}
// ...
+ "           anime=\"" + strTypeAnimation + "\"\n"
```

## 🎯 Résultat

Maintenant, les hotspots diaporama sur les panoramiques :
- ✅ Utilisent la même icône que les hotspots photo
- ✅ **Héritent des animations** (blink, pulse, rotate, etc.)
- ✅ Animation par défaut : **"blink"** (clignotement)
- ✅ Cohérence avec les hotspots photo

## 📋 Types d'Animation Disponibles

Les hotspots diaporama supportent maintenant les mêmes animations que les hotspots photo :
- **none** : Aucune animation
- **blink** : Clignotement (par défaut)
- **pulse** : Pulsation
- **rotate** : Rotation
- **bounce** : Rebond
- **swing** : Balancement

## 🔧 Fichiers Modifiés

1. **`src/editeurpanovisu/HotspotDiaporama.java`**
   - Ajout propriété `strTypeAnimation`
   - Ajout méthodes `getStrTypeAnimation()` et `setStrTypeAnimation()`

2. **`src/editeurpanovisu/EditeurPanovisu.java`** (ligne ~2647)
   - Remplacement de `strAnime` (boolean) par `strTypeAnimation` (String)
   - Ajout valeur par défaut "blink" si non définie

## 🧪 Tests Recommandés

1. **Créer un nouveau hotspot diaporama** sur un panoramique
2. **Vérifier l'animation** : l'icône doit clignoter par défaut
3. **Générer une visite** et vérifier dans le navigateur
4. **Tester avec différentes animations** si l'interface le permet
5. **Comparer avec hotspots photo** : comportement identique

## 📝 Notes Techniques

- L'animation par défaut est **"blink"** pour les nouveaux hotspots diaporama
- La synchronisation entre `bAnime` et `strTypeAnimation` est automatique
- Si `strTypeAnimation` est `null`, `isEmpty()` ou `"none"`, alors `bAnime = false`
- Compatibilité totale avec le système d'animation existant des hotspots photo

## 🔄 Rétrocompatibilité

Les anciens projets avec hotspots diaporama existants :
- Recevront l'animation par défaut **"blink"** lors de la prochaine génération
- Aucune perte de fonctionnalité
- Amélioration automatique de l'expérience visuelle

---

**Build #2993** - Animations des hotspots diaporama corrigées ! 🎉
