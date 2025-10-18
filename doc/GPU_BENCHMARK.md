# 🏎️ Benchmark GPU vs CPU - Guide Utilisateur

## 📋 Vue d'ensemble

Ce guide vous explique comment tester et comparer les performances du GPU vs CPU pour les transformations panoramiques.

## 🎯 Méthode Simple (Via l'Interface)

### Transformation Equirectangulaire → Cube

1. **Ouvrir PanoVisu**
2. **Menu** → **Outils** → **Conversion Equi ↔ Cube**
3. **Sélectionner une image équirectangulaire** (plus elle est grande, plus le gain GPU est visible)
4. **Observer les logs de la console** :
   ```
   🎮 Utilisation du GPU pour Equi→Cube
   Temps GPU : ~500ms (pour une image 8K)
   ```

### Transformation Cube → Equirectangulaire

1. **Menu** → **Outils** → **Conversion Equi ↔ Cube**
2. **Charger 6 faces de cube**
3. **Convertir**
4. **Observer les logs** :
   ```
   🎮 Utilisation du GPU pour Cube→Equi
   Temps GPU : ~300ms (pour des faces 2K)
   ```

## 📊 Résultats Attendus

### Configuration Référence

- **CPU**: Modern multi-core processor
- **GPU**: NVIDIA GeForce RTX 3090
- **Image**: 8192x4096 pixels (8K équirectangulaire)

### Performance Equi → Cube

| Méthode | Temps | Speedup |
|---------|-------|---------|
| CPU Séquentiel | ~30s | 1x |
| GPU OpenCL | ~0.6s | **50x** |

### Performance Cube → Equi

| Méthode | Temps | Speedup |
|---------|-------|---------|
| CPU Séquentiel | ~45s | 1x |
| GPU OpenCL | ~1.5s | **30x** |

## 🔍 Vérification GPU Actif

### Messages au Démarrage

Au lancement de PanoVisu, vous devez voir :

```
✅ GPU initialisé avec succès
   📍 Plateforme: NVIDIA CUDA (ou AMD/Intel)
   🎮 GPU: [Nom de votre carte graphique]
   💾 Mémoire: [Mémoire disponible] MB
   ⚡ Unités de calcul: [Nombre]
   👥 Taille max workgroup: [Valeur]
```

### Messages Pendant Transformation

Pendant une transformation, vous devez voir :

```
🎮 Utilisation du GPU pour Equi→Cube
```

ou

```
🎮 Utilisation du GPU pour Cube→Equi
```

### Si GPU Non Disponible

Si le GPU n'est pas détecté :

```
⚠️  Aucun GPU OpenCL détecté, utilisation du CPU
💻 Utilisation du CPU pour Equi→Cube
```

## 🧪 Benchmark Automatique (Script)

### Prérequis

- PanoVisu compilé
- Image de test disponible
- Maven configuré

### Exécution du Benchmark

```powershell
# Depuis le dossier du projet
cd d:\developpement\java\editeurPanovisu

# Lancer le benchmark
mvn javafx:run -Dexec.args="--benchmark"
```

### Résultats du Benchmark

Le script affichera :

```
═══════════════════════════════════════════
    🏁 BENCHMARK GPU vs CPU
═══════════════════════════════════════════

📊 Configuration:
   Image: 8192x4096 pixels
   GPU: NVIDIA GeForce RTX 3090
   CPU: AMD Ryzen 9 5950X

🔄 Test Equi → Cube (5 itérations)
   ⏱️  CPU Moyen: 28.3s
   ⚡ GPU Moyen: 0.62s
   🚀 Speedup: 45.6x

🔄 Test Cube → Equi (5 itérations)
   ⏱️  CPU Moyen: 42.1s
   ⚡ GPU Moyen: 1.4s
   🚀 Speedup: 30.1x

═══════════════════════════════════════════
```

## 💡 Facteurs Impactant les Performances

### Taille d'Image

| Résolution | CPU | GPU | Speedup |
|------------|-----|-----|---------|
| 2K (2048x1024) | ~2s | ~0.1s | 20x |
| 4K (4096x2048) | ~8s | ~0.2s | 40x |
| 8K (8192x4096) | ~30s | ~0.6s | 50x |
| 16K (16384x8192) | ~120s | ~2.5s | 48x |

### Type de GPU

| GPU | Mémoire | Compute | Performance Relative |
|-----|---------|---------|---------------------|
| Intel HD Graphics | 2-4 GB | ~24 | 5-10x |
| NVIDIA GTX 1060 | 6 GB | 1280 | 15-25x |
| NVIDIA RTX 2070 | 8 GB | 2304 | 25-35x |
| NVIDIA RTX 3090 | 24 GB | 10496 | 40-50x |
| AMD RX 6800 XT | 16 GB | 4608 | 30-45x |

## ⚙️ Optimisation

### Désactiver le GPU

Si vous préférez utiliser le CPU :

1. **Ligne de commande** :
   ```bash
   java -Dgpu.enabled=false -jar editeurPanovisu.jar
   ```

2. **Dans le code** (préférences utilisateur - à venir) :
   ```java
   GPUManager.getInstance().setEnabled(false);
   ```

### Ajuster la Taille des WorkGroups

Pour GPUs moins puissants, réduire la taille :

```java
// Dans GPUManager.java
private static final int WORKGROUP_SIZE = 16; // Au lieu de 32
```

## 🐛 Dépannage

### "GPU échec, fallback CPU"

**Causes possibles** :
1. Mémoire GPU insuffisante (image trop grande)
2. Pilotes OpenCL non à jour
3. Erreur dans les kernels

**Solutions** :
1. Réduire la taille de l'image
2. Mettre à jour les pilotes GPU
3. Vérifier les logs pour l'erreur exacte

### Performances GPU Décevantes

**Vérifications** :
1. ✅ GPU bien détecté au démarrage ?
2. ✅ Message "🎮 Utilisation du GPU" s'affiche ?
3. ✅ Pilotes GPU à jour ?
4. ✅ Image assez grande (>2K) ?

**Astuce** : Les petites images (<1024x512) peuvent être plus rapides en CPU à cause des overheads de transfert GPU.

## 📈 Profiling Avancé

### Mesurer les Temps Précis

Ajouter des logs de timing dans votre code :

```java
long startTime = System.nanoTime();
Image[] result = TransformationsPanoramique.equi2cubeAuto(image, 2048);
long endTime = System.nanoTime();
double durationMs = (endTime - startTime) / 1_000_000.0;
System.out.println("⏱️  Transformation: " + durationMs + " ms");
```

### Analyser les Transferts GPU

Les temps incluent :
- **Upload** : Image CPU → GPU memory (~50-200ms pour 8K)
- **Compute** : Kernel GPU execution (~100-500ms pour 8K)
- **Download** : Résultat GPU → CPU memory (~50-200ms pour 8K)

Pour images très grandes (>16K), l'overhead de transfert devient significatif.

## 🎓 Ressources

- **Documentation OpenCL** : https://www.khronos.org/opencl/
- **JOCL** : http://www.jocl.org/
- **GPU Architecture** : `doc/GPU_ACCELERATION.md`
- **Quick Start** : `doc/GPU_QUICKSTART.md`

## 🤝 Contribuer

Partagez vos résultats de benchmark ! Créez une issue GitHub avec :
- Configuration système (CPU, GPU, RAM)
- Résultats de benchmark
- Taille d'images testées

Cela aide à améliorer l'implémentation pour tous les types de GPU.

---

**Dernière mise à jour** : Octobre 2025  
**Version PanoVisu** : 3.2.0+
