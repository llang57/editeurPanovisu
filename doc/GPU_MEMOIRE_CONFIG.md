# Configuration mémoire GPU intégré AMD

## Contexte
Les GPU intégrés AMD (comme le Radeon 680M) partagent la RAM système mais peuvent avoir une limite d'allocation configurée dans le BIOS.

## Valeurs typiques observées
- **Mémoire globale OpenCL** : 512 MB (valeur par défaut BIOS)
- **Allocation maximale par buffer** : Déterminée par le BIOS
- **RAM système disponible** : 32 GB

## Comment augmenter la mémoire GPU ?

### 1. Configuration BIOS/UEFI (recommandé)
Redémarrez et accédez au BIOS/UEFI (généralement F2, F10, ou DEL au démarrage):

1. Cherchez une section nommée:
   - **"UMA Frame Buffer Size"** (AMD)
   - **"GPU Memory"** ou **"iGPU Memory"**
   - **"Graphics Memory"** ou **"Shared Memory"**
   - **"VRAM Size"** ou **"DVMT Pre-Allocated"**

2. Options typiques disponibles:
   - 512 MB (défaut minimal)
   - 1 GB
   - 2 GB
   - 4 GB
   - 8 GB (maximum recommandé pour Radeon 680M)
   - Auto (dynamique selon les besoins)

3. Recommandation:
   - **Pour usage général** : 2 GB ou Auto
   - **Pour traitement d'images** : 4-8 GB
   - **Avec 32 GB RAM système** : 4-8 GB est raisonnable

### 2. Vérification après changement
Après modification BIOS, relancez l'application et vérifiez la sortie:

```
✅ GPU initialisé avec succès
   📍 Plateforme: rusticl
   🔧 Version: OpenCL 3.0
   🎮 GPU: AMD Radeon 680M
   💾 Mémoire globale: XXXX MB     ← Devrait augmenter
   📦 Alloc max par buffer: XXXX MB ← Limite par buffer
   ⚡ Unités de calcul: 12
   👥 Taille max workgroup: 1024
```

### 3. Impact sur les performances

#### Avec 512 MB (actuel)
- ✅ Images jusqu'à ~4K (4096x2048) : OK
- ⚠️  Images 8K+ : Peut échouer ou utiliser le CPU en fallback
- ⚠️  Multiples panoramas simultanés : Limité

#### Avec 4-8 GB
- ✅ Images 8K+ (8192x4096) : OK
- ✅ Multiples panoramas : OK
- ✅ Traitement vidéo : Possible
- ✅ Réserve pour textures et buffers temporaires

### 4. Alternative logicielle (si BIOS bloqué)
Si le BIOS ne permet pas de changer la valeur:

1. **Fallback automatique CPU** : L'application détecte automatiquement les échecs GPU et bascule vers CPU
2. **Traitement par lots** : Le code peut découper les grandes images en tuiles
3. **Vérification préalable** : Ajout possible d'une vérification de taille avant traitement GPU

## Code de vérification
L'application affiche maintenant:
- `getDeviceMemory()` : Mémoire totale OpenCL visible
- `getDeviceMaxAllocSize()` : Taille maximale d'un buffer unique

Pour un panorama 8K (8192×4096 pixels, RGBA):
- Taille buffer = 8192 × 4096 × 4 = **128 MB** par face
- Nécessite ~1 GB pour traitement complet avec buffers temporaires

## Recommandation finale
Pour 32 GB RAM système et usage EditeurPanovisu:
- **BIOS: Configurer 4 GB ou 8 GB pour le GPU**
- Cela laisse 24-28 GB pour le système et autres applications
- Permet traitement fluide jusqu'à 16K panoramas

## Références
- [AMD Radeon 680M specs](https://www.amd.com/en/products/apu/amd-ryzen-9-6900hs)
- OpenCL `CL_DEVICE_GLOBAL_MEM_SIZE` : Mémoire totale
- OpenCL `CL_DEVICE_MAX_MEM_ALLOC_SIZE` : Allocation max par buffer
