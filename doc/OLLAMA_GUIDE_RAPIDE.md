# Guide Rapide - Ollama pour EditeurPanovisu

## Installation Express (5 minutes)

### 1. Télécharger et installer Ollama
```
https://ollama.com/
→ Cliquer sur "Download"
→ Exécuter OllamaSetup.exe
```

### 2. Installer un modèle d'IA
Ouvrir **cmd** et taper :
```bash
ollama pull llama3.2
```
OU
```bash
ollama pull mistral
```

### 3. Vérifier l'installation
```bash
ollama list
```

## Utilisation dans EditeurPanovisu

1. Ouvrir EditeurPanovisu
2. Charger un projet
3. Sélectionner un panoramique
4. Aller dans "Paramètres du panoramique" → "Description IA"
5. Cliquer sur "Générer description IA"
6. ✨ Magie !

## Modèles Recommandés

| Modèle | Commande | Taille | Qualité |
|--------|----------|--------|---------|
| **llama3.2** | `ollama pull llama3.2` | 2 GB | Rapide ⚡ |
| **mistral** | `ollama pull mistral` | 4 GB | Équilibré ⭐⭐⭐⭐ |
| **llama3.1** | `ollama pull llama3.1` | 5 GB | Excellent ⭐⭐⭐⭐⭐ |

## Dépannage Express

### Problème : "Ollama non disponible"
**Solution** : Vérifier que l'icône Ollama est dans la barre des tâches. Si non, lancer Ollama depuis le Menu Démarrer.

### Problème : "Aucun modèle"
**Solution** : Installer un modèle avec `ollama pull llama3.2`

### Problème : C'est lent
**Solution** : Utiliser un modèle plus petit ou fermer d'autres applications

## Commandes Utiles

```bash
# Voir les modèles installés
ollama list

# Installer un nouveau modèle
ollama pull nom-modele

# Supprimer un modèle
ollama rm nom-modele

# Vérifier la version
ollama --version

# Tester un modèle
ollama run llama3.2 "Décris Paris en 2 phrases"
```

## Support

- 📧 support@panovisu.com
- 🌐 https://www.lemondea360.fr
- 📖 Documentation complète : `aide/installation-ollama.html`

---

**EditeurPanovisu** - Intelligence Artificielle intégrée 🤖
