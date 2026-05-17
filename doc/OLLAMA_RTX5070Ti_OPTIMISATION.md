# Optimisation Ollama pour RTX 5070 Ti (16GB)

## Configuration matérielle
- **CPU** : AMD Ryzen 9800X3D
- **RAM** : 64 GB DDR5 5600 MHz
- **GPU** : NVIDIA RTX 5070 Ti 16 GB
- **CUDA** : Version 13.0
- **Driver NVIDIA** : 581.80

## Variables d'environnement optimales

```powershell
# Configuration persistante (à exécuter une fois)
[Environment]::SetEnvironmentVariable("OLLAMA_NUM_PARALLEL", "8", "User")
[Environment]::SetEnvironmentVariable("OLLAMA_MAX_LOADED_MODELS", "3", "User")
[Environment]::SetEnvironmentVariable("OLLAMA_GPU_OVERHEAD", "2048", "User")
```

### Explication des paramètres

| Variable | Valeur | Description |
|----------|--------|-------------|
| `OLLAMA_NUM_PARALLEL` | 8 | Nombre de requêtes parallèles (pour multi-utilisateurs) |
| `OLLAMA_MAX_LOADED_MODELS` | 3 | Nombre de modèles en VRAM simultanément (16GB permet 2-3 modèles) |
| `OLLAMA_GPU_OVERHEAD` | 2048 | Overhead GPU en MB (réserve pour CUDA) |

## Performances mesurées

### Mistral Nemo (7.1 GB)
- **Temps de chargement** : ~1.8s
- **Vitesse prompt** : ~1071 tokens/s
- **Vitesse génération** : ~186 tokens/s
- **Temps total (50 mots)** : ~2.8s

### Comparaison avant/après optimisation
- **Avant** : 4.7s pour une réponse courte
- **Après** : 2.8s pour une réponse courte
- **Amélioration** : ~40% plus rapide

## Modèles installés

| Modèle | Taille | Priorité | Usage recommandé |
|--------|--------|----------|------------------|
| Mistral Nemo | 7.1 GB | 1 | Descriptions géographiques détaillées |
| Gemma 2 | 5.4 GB | 3 | Génération de contenu polyvalent |
| Llama 3.1 | 4.9 GB | 4 | Traduction et analyse |
| Qwen 2.5 | 4.7 GB | 5 | Multilingue performant |
| Phi-3 | 2.2 GB | 2 | Tâches rapides, peu de VRAM |

## Optimisations supplémentaires possibles

### 1. Activer le mode Flash Attention (si supporté)
```powershell
[Environment]::SetEnvironmentVariable("OLLAMA_FLASH_ATTENTION", "1", "User")
```

### 2. Augmenter la taille du contexte pour les gros modèles
```powershell
# Pour Mistral Nemo avec 16GB VRAM
ollama run mistral-nemo --ctx-size 8192
```

### 3. Optimiser la quantification
Les modèles actuels sont en Q4 (4-bit). Pour plus de vitesse avec légère perte de qualité :
```bash
# Modèles Q3 disponibles (plus rapides, moins précis)
ollama pull mistral-nemo:Q3_K_M
```

### 4. Monitoring GPU en temps réel
```powershell
# Dans un terminal séparé
nvidia-smi -l 1
```

## Commandes de test

### Test basique
```powershell
ollama run mistral-nemo "Bonjour"
```

### Test avec verbose pour voir les stats
```powershell
ollama run mistral-nemo "Test de performance" --verbose
```

### Test avec contexte étendu
```powershell
ollama run mistral-nemo --ctx-size 8192 "Question complexe nécessitant beaucoup de contexte"
```

## Résolution de problèmes

### Ollama n'utilise pas le GPU
1. Vérifier que le GPU est détecté : `nvidia-smi`
2. Vérifier les processus : `Get-Process ollama`
3. Redémarrer Ollama : `Stop-Process -Name ollama -Force`

### Performances encore lentes
1. Vérifier qu'aucun autre processus n'utilise massivement le GPU
2. S'assurer que le driver NVIDIA est à jour
3. Vérifier la température du GPU (throttling si >80°C)
4. Essayer un modèle plus petit (Phi-3) pour comparer

### VRAM insuffisante
Si erreur "out of memory" :
1. Réduire `OLLAMA_MAX_LOADED_MODELS` à 2
2. Utiliser des modèles plus petits
3. Réduire `--ctx-size`

## Benchmarks attendus pour RTX 5070 Ti

Avec une RTX 5070 Ti, voici les performances attendues :

| Modèle | Tokens/s (génération) | Temps pour 100 tokens |
|--------|----------------------|----------------------|
| Phi-3 (2.2GB) | 250-350 | ~0.3s |
| Qwen 2.5 (4.7GB) | 200-280 | ~0.4s |
| Llama 3.1 (4.9GB) | 180-250 | ~0.5s |
| Mistral Nemo (7.1GB) | 150-220 | ~0.6s |
| Gemma 2 (5.4GB) | 180-260 | ~0.5s |

**Note** : Les performances actuelles (186 tokens/s pour Mistral Nemo) sont dans la fourchette basse. Il y a peut-être encore de la marge d'optimisation.

## Checklist d'optimisation complète

- [x] Installer Ollama
- [x] Configurer les variables d'environnement
- [x] Vérifier la détection GPU (nvidia-smi)
- [x] Télécharger les modèles
- [ ] Tester Flash Attention (si compatible)
- [ ] Benchmark tous les modèles
- [ ] Optimiser les paramètres de contexte selon l'usage
- [ ] Configurer le système pour démarrage automatique d'Ollama

## Intégration avec EditeurPanovisu

Le projet utilise `OllamaService.java` qui se connecte à `http://localhost:11434`.

Fichier de configuration : `configPV/ollama-models.json`

Les modèles sont utilisés par priorité pour générer des descriptions de panoramas.
