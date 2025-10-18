# 🔄 Guide d'Intégration - Redimensionnement GPU

## Migration du Code Existant

Ce guide explique comment remplacer les appels à `ReadWriteImage.resizeImage()` par la nouvelle API GPU.

---

## 📝 Remplacement Simple

### Avant (CPU uniquement)
```java
Image imgRedimensionnee = ReadWriteImage.resizeImage(imgSource, 8000, 4000);
```

### Après (GPU avec fallback CPU)
```java
import editeurpanovisu.gpu.ImageResizeGPU;
import editeurpanovisu.gpu.InterpolationMethod;

Image imgRedimensionnee = ImageResizeGPU.resizeAuto(
    imgSource, 
    8000, 4000,
    InterpolationMethod.BICUBIC  // Recommandé
);
```

---

## 🎯 Cas d'Usage Spécifiques

### 1. Agrandissement Panoramas (Équirectangulaire)

**Fichier**: `EditeurPanovisu.java` lignes 3308, 3348, etc.

```java
// AVANT
imgPano = ReadWriteImage.resizeImage(imgPano, 8192, 4096);

// APRÈS - Bicubique pour qualité/performance
try {
    imgPano = ImageResizeGPU.resizeAuto(
        imgPano, 
        8192, 4096,
        InterpolationMethod.BICUBIC
    );
} catch (InterruptedException e) {
    System.err.println("⚠️ Redimensionnement interrompu");
    Thread.currentThread().interrupt();
}
```

### 2. Agrandissement Extrême (×4+)

**Pour agrandissements critiques** :

```java
// Utiliser Lanczos3 pour meilleure qualité
try {
    imgPano = ImageResizeGPU.resizeAuto(
        imgPano, 
        16000, 8000,  // ×4 agrandissement
        InterpolationMethod.LANCZOS3  // Qualité maximale
    );
} catch (InterruptedException e) {
    // Gestion erreur
}
```

### 3. Génération Miniatures

**Fichier**: `EditeurPanovisu.java` lignes 9955, 9962, etc.

```java
// AVANT
imgPanoRetaille2 = ReadWriteImage.resizeImage(imgPanoRetaille2, 1200, 600);

// APRÈS - Petites images utilisent automatiquement CPU (< 2MP)
try {
    imgPanoRetaille2 = ImageResizeGPU.resizeAuto(
        imgPanoRetaille2, 
        1200, 600,
        InterpolationMethod.BICUBIC
    );
} catch (InterruptedException e) {
    // Fallback
    imgPanoRetaille2 = ReadWriteImage.resizeImage(imgPanoRetaille2, 1200, 600);
}
```

### 4. PanoramicCube - Ratio 2:1

**Fichier**: `PanoramicCube.java` ligne 96+

```java
// AVANT (dans resizeToEquirectangular)
private Image resizeToEquirectangular(Image source, int targetWidth, int targetHeight) {
    // Code custom avec boucles...
    return resized;
}

// APRÈS - Utiliser GPU
private Image resizeToEquirectangular(Image source, int targetWidth, int targetHeight) {
    try {
        return ImageResizeGPU.resizeAuto(
            source,
            targetWidth,
            targetHeight,
            InterpolationMethod.BICUBIC
        );
    } catch (InterruptedException e) {
        // Fallback CPU
        return ReadWriteImage.resizeImage(source, targetWidth, targetHeight);
    }
}
```

---

## 🎛️ Choix de la Méthode d'Interpolation

### Arbre de Décision

```
┌─ Taille image ?
│
├─ < 2 MP (1920×1080) ────────────────> CPU (automatic)
│
├─ 2-8 MP (4K) ──────┬─ Qualité normale ─> BICUBIC
│                    └─ Haute qualité ───> LANCZOS3
│
├─ 8-16 MP (8K) ─────┬─ Vitesse ────────> BICUBIC
│                    └─ Qualité ─────────> LANCZOS3
│
└─ > 16 MP (12K+) ───┬─ ×2-×3 ──────────> BICUBIC
                     └─ ×4+ ───────────> LANCZOS3
```

### Règles Pratiques

| Cas | Méthode | Raison |
|-----|---------|--------|
| Génération visite virtuelle | `BICUBIC` | Balance qualité/vitesse |
| Miniatures navigateur | `BILINEAR` | Vitesse (CPU auto) |
| Impression haute qualité | `LANCZOS3` | Qualité maximale |
| Agrandissement ×2 | `BICUBIC` | Standard, bon résultat |
| Agrandissement ×4+ | `LANCZOS3` | Préserve détails |
| Réduction | `BICUBIC` | Évite aliasing |

---

## 🔧 Modifications Recommandées

### 1. PanoramicCube.java

**Ligne 96-100** :
```java
// Redimensionner l'image au ratio 2:1 (obligatoire pour equi2cube)
if (sourceWidth != targetWidth || sourceHeight != targetHeight) {
    try {
        conformedImage = ImageResizeGPU.resizeAuto(
            sourceImage,
            targetWidth,
            targetHeight,
            InterpolationMethod.BICUBIC
        );
    } catch (InterruptedException e) {
        System.err.println("⚠️ Redimensionnement GPU interrompu, fallback CPU");
        conformedImage = ReadWriteImage.resizeImage(sourceImage, targetWidth, targetHeight);
    }
}
```

**Ligne 138-169** (méthode `resizeToEquirectangular`) :
```java
private Image resizeToEquirectangular(Image source, int targetWidth, int targetHeight) {
    int srcWidth = (int) source.getWidth();
    int srcHeight = (int) source.getHeight();
    
    // Si déjà à la bonne taille
    if (srcWidth == targetWidth && srcHeight == targetHeight) {
        return source;
    }
    
    // Utiliser GPU avec Bicubique
    try {
        return ImageResizeGPU.resizeAuto(
            source,
            targetWidth,
            targetHeight,
            InterpolationMethod.BICUBIC
        );
    } catch (InterruptedException e) {
        System.err.println("⚠️ Redimensionnement interrompu");
        // Fallback sur CPU
        return ReadWriteImage.resizeImage(source, targetWidth, targetHeight);
    }
}
```

### 2. EditeurPanovisu.java - Génération Visite

**Rechercher** : `ReadWriteImage.resizeImage`

**Remplacer les lignes** 3308, 3348, 3420, 3458 :
```java
// Au début de la méthode, ajouter import
import editeurpanovisu.gpu.ImageResizeGPU;
import editeurpanovisu.gpu.InterpolationMethod;

// Remplacer chaque appel
try {
    imgPano = ImageResizeGPU.resizeAuto(
        imgPano, 
        tailleTarget, 
        hauteurTarget,
        InterpolationMethod.BICUBIC
    );
} catch (InterruptedException e) {
    // Fallback CPU si interruption
    imgPano = ReadWriteImage.resizeImage(imgPano, tailleTarget, hauteurTarget);
}
```

### 3. Miniatures (optionnel - CPU plus rapide < 2MP)

**Lignes** 9955, 9962, 9963, 9966, 9967 :

Garder `ReadWriteImage.resizeImage()` car images petites (< 2MP).  
Ou migrer pour uniformité :

```java
try {
    imgPano4 = ImageResizeGPU.resizeAuto(
        imgPano4, 300, 150,
        InterpolationMethod.BILINEAR  // Rapide pour miniatures
    );
} catch (InterruptedException e) {
    imgPano4 = ReadWriteImage.resizeImage(imgPano4, 300, 150);
}
```

---

## 🎨 Ajout UI - Préférences Utilisateur

### Dans ConfigDialogController.java

**Ajouter section "Redimensionnement"** après la section GPU :

```java
// ========== INTERPOLATION REDIMENSIONNEMENT ==========
Label lblTitreInterpolation = new Label("🎨 Redimensionnement d'images");
lblTitreInterpolation.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
lblTitreInterpolation.setLayoutX(45);
lblTitreInterpolation.setLayoutY(650);

Label lblMethode = new Label("Méthode d'interpolation :");
lblMethode.setLayoutX(45);
lblMethode.setLayoutY(680);

ComboBox<InterpolationMethod> cmbInterpolation = new ComboBox<>();
cmbInterpolation.getItems().addAll(InterpolationMethod.values());
cmbInterpolation.setValue(InterpolationMethod.BICUBIC); // Par défaut
cmbInterpolation.setLayoutX(250);
cmbInterpolation.setLayoutY(675);
cmbInterpolation.setPrefWidth(200);

Label lblDescription = new Label();
lblDescription.setLayoutX(45);
lblDescription.setLayoutY(710);
lblDescription.setWrapText(true);
lblDescription.setMaxWidth(500);

// Mise à jour description selon choix
cmbInterpolation.setOnAction(e -> {
    InterpolationMethod method = cmbInterpolation.getValue();
    switch (method) {
        case NEAREST:
            lblDescription.setText("⚡ Plus rapide - Qualité pixelisée - Pour pixel art");
            break;
        case BILINEAR:
            lblDescription.setText("🔵 Rapide - Qualité moyenne - Agrandissements légers");
            break;
        case BICUBIC:
            lblDescription.setText("⭐ Recommandé - Excellente qualité - Standard panoramas");
            break;
        case LANCZOS3:
            lblDescription.setText("✨ Qualité maximale - Plus lent - Agrandissements critiques");
            break;
    }
});

// Déclencher description initiale
cmbInterpolation.fireEvent(new ActionEvent());

apConfig.getChildren().addAll(
    lblTitreInterpolation, 
    lblMethode, 
    cmbInterpolation, 
    lblDescription
);
```

**Sauvegarder préférence** :
```java
// Dans la méthode de sauvegarde
String interpolationMethod = cmbInterpolation.getValue().name();
// Sauver dans preferences.cfg
strContenuFichier += "interpolationMethod=" + interpolationMethod + "\n";
```

**Charger préférence** :
```java
// Dans lisPreferences()
case "interpolationMethod":
    try {
        InterpolationMethod method = InterpolationMethod.valueOf(valeur);
        // Stocker dans variable statique accessible
        ImageResizeGPU.setDefaultInterpolation(method);
    } catch (IllegalArgumentException e) {
        // Valeur invalide, utiliser BICUBIC par défaut
    }
    break;
```

---

## 📊 Monitoring et Logs

### Activer Logs Détaillés

Les logs sont automatiques. Pour désactiver :

```java
// Dans ImageResizeGPU.java, commenter les System.out.println
```

### Exemple de Sortie Console

```
🎮 Redimensionnement Bicubique sur GPU
   📐 Source: 4096×2048 (8.4 MP)
   📐 Cible: 8192×4096
   📊 Facteur: 2.00× agrandissement
[GPU] ✅ Kernel Bicubique compilé
⏱️  Redimensionnement GPU terminé en 1247 ms
```

---

## ✅ Checklist Migration

- [ ] Ajouter imports dans fichiers modifiés
- [ ] Remplacer appels `ReadWriteImage.resizeImage()` pour images > 4K
- [ ] Ajouter gestion `InterruptedException` (try-catch)
- [ ] Tester avec GPU activé
- [ ] Tester avec GPU désactivé (fallback)
- [ ] Vérifier qualité visuelle des images
- [ ] Mesurer gains de performance
- [ ] Ajouter UI préférences (optionnel)
- [ ] Documenter changements dans CHANGELOG

---

## 🐛 Gestion d'Erreurs

### Pattern Recommandé

```java
try {
    Image result = ImageResizeGPU.resizeAuto(
        source, width, height, InterpolationMethod.BICUBIC
    );
    return result;
} catch (InterruptedException e) {
    System.err.println("⚠️ Redimensionnement interrompu par l'utilisateur");
    Thread.currentThread().interrupt();
    // Fallback CPU ou annulation
    return ReadWriteImage.resizeImage(source, width, height);
} catch (Exception e) {
    System.err.println("❌ Erreur redimensionnement GPU: " + e.getMessage());
    // Fallback CPU
    return ReadWriteImage.resizeImage(source, width, height);
}
```

---

## 📚 Références

- **Documentation complète** : `doc/GPU_IMAGE_RESIZE.md`
- **Tests** : `src/editeurpanovisu/gpu/TestImageResize.java`
- **API GPU** : `src/editeurpanovisu/gpu/ImageResizeGPU.java`
- **Benchmarks GPU** : `doc/GPU_BENCHMARK.md`

---

**Auteur** : PanoVisu Team  
**Date** : 18 octobre 2025  
**Build** : 2890+
