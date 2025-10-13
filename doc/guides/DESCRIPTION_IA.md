# 🤖 Description IA - Documentation

## 📋 Vue d'ensemble

La fonctionnalité **Description IA** permet de générer automatiquement des descriptions géographiques précises pour vos panoramiques à l'aide d'une intelligence artificielle gratuite.

## ✨ Caractéristiques

- ✅ **Gratuit** : Deux options gratuites (Ollama local OU Hugging Face en ligne)
- ✅ **Automatique** : Choisit automatiquement le meilleur service disponible
- ✅ **Multilingue** : S'adapte automatiquement à la langue de l'interface (français, anglais, allemand, espagnol, portugais)
- ✅ **Précis** : Descriptions géographiques détaillées basées sur les données disponibles
- ✅ **Flexible** : Fonctionne en local (Ollama) ou en ligne (Hugging Face)
- ✅ **Sauvegarde automatique** : Les descriptions sont enregistrées dans les fichiers `.pvu`

## 🔄 Deux services IA gratuits

L'application supporte **deux services** et choisit automatiquement celui qui est disponible :

### 🏠 **Option 1 : Ollama (Recommandé)**
- **Avantages** : Local, rapide, privé, pas de connexion Internet requise
- **Inconvénient** : Nécessite installation (gratuite)

### ☁️ **Option 2 : Hugging Face (Fallback)**
- **Avantages** : Aucune installation, fonctionne immédiatement
- **Inconvénients** : Nécessite Internet, peut être plus lent au premier appel

## 🚀 Configuration

### Sans installation (Hugging Face)

**Rien à faire !** L'application fonctionne immédiatement avec Hugging Face si vous avez une connexion Internet.

### Avec installation Ollama (Recommandé pour meilleures performances)

#### Windows

1. **Téléchargez Ollama** : [https://ollama.ai/download](https://ollama.ai/download)
2. **Installez** le fichier téléchargé
3. **Ouvrez un terminal** (PowerShell ou CMD)
4. **Téléchargez le modèle Mistral** (recommandé pour les descriptions géographiques) :
   ```powershell
   ollama pull mistral
   ```
5. **Vérifiez l'installation** :
   ```powershell
   ollama list
   ```
6. **Redémarrez l'application** - Ollama sera automatiquement détecté

#### Linux / macOS

```bash
# Installez Ollama
curl -fsSL https://ollama.ai/install.sh | sh

# Téléchargez le modèle Mistral
ollama pull mistral

# Vérifiez l'installation
ollama list

# Redémarrez l'application
```

## 📖 Utilisation

### 1. Ouvrir le panneau Description IA

Sur l'interface principale, le panneau **"Description IA"** se trouve juste en dessous de **"Paramètres du panoramique"**.

### 2. Préparer les données

Pour générer une description de qualité, vous devez fournir **au moins une** des informations suivantes :

- **Titre de la visite** : Renseigné dans le champ correspondant
- **Titre du panoramique** : Nom du panoramique courant
- **Géolocalisation** : Coordonnées GPS du lieu

⚠️ **Important** : Si aucune de ces informations n'est disponible, une fenêtre d'aide apparaîtra pour vous guider.

### 3. Générer la description

1. Cliquez sur le bouton **"Générer description IA"**
2. L'application détecte automatiquement le service disponible (affiche "Service: Ollama (local)" ou "Service: Hugging Face (en ligne)")
3. Attendez quelques secondes (l'indicateur "Génération en cours..." s'affiche)
   - **Ollama** : ~5-10 secondes
   - **Hugging Face** : ~15-30 secondes (modèle peut être en chargement)
4. La description générée apparaît dans la zone de texte
5. **La description est automatiquement sauvegardée** dans votre projet

### 4. Modifier manuellement

Vous pouvez modifier la description générée directement dans la zone de texte. Les modifications sont **automatiquement sauvegardées** dès que vous modifiez le texte.

## 🌍 Exemples de descriptions générées

### Avec titre + coordonnées GPS

**Entrée** :
- Titre visite : "Cathédrales de France"
- Titre panoramique : "Notre-Dame de Paris"
- GPS : 48.8530, 2.3499

**Sortie (français)** :
> Notre-Dame de Paris est une cathédrale gothique emblématique située sur l'Île de la Cité, au cœur de Paris. Construite entre 1163 et 1345, elle est célèbre pour son architecture médiévale, ses rosaces impressionnantes et ses gargouilles. Le monument historique, actuellement en restauration suite à l'incendie de 2019, reste un symbole majeur du patrimoine français.

### Avec titre uniquement

**Entrée** :
- Titre panoramique : "Mont Saint-Michel"

**Sortie (français)** :
> Le Mont Saint-Michel est une commune insulaire située en Normandie, célèbre pour son abbaye médiévale perchée sur un îlot rocheux. Ce site classé au patrimoine mondial de l'UNESCO attire des millions de visiteurs chaque année grâce à son architecture exceptionnelle et ses impressionnantes marées qui transforment le paysage.

## 🔧 Configuration avancée

### Changer le modèle IA

Par défaut, le système utilise le modèle **Mistral** (recommandé). Vous pouvez en télécharger d'autres :

```bash
# Modèles disponibles
ollama pull llama2        # Modèle généraliste
ollama pull gemma         # Modèle compact
ollama pull neural-chat   # Modèle conversationnel
```

Pour modifier le modèle utilisé, éditez le fichier `OllamaService.java` :
```java
private static final String DEFAULT_MODEL = "mistral"; // Changez ici
```

### Ajuster le timeout

Si la génération prend trop de temps, vous pouvez augmenter le timeout :
```java
private static final int TIMEOUT_MS = 5000; // Temps de connexion (ms)
conn.setReadTimeout(60000); // Temps de génération (ms)
```

## ❓ Dépannage

### Erreur "Service IA non disponible"

**Cause** : Ni Ollama ni Hugging Face ne sont accessibles.

**Solutions** :

#### Pour Ollama (local) :
1. Vérifiez qu'Ollama est installé : `ollama --version`
2. Sur Windows, Ollama démarre automatiquement au démarrage du système
3. Sur Linux/macOS, lancez : `ollama serve &`
4. Vérifiez que le port 11434 n'est pas bloqué
5. Téléchargez le modèle : `ollama pull mistral`

#### Pour Hugging Face (en ligne) :
1. Vérifiez votre connexion Internet
2. Testez l'accès à : https://huggingface.co
3. Vérifiez que votre firewall ne bloque pas les connexions HTTPS
4. Attendez quelques minutes si le service est temporairement indisponible

### Erreur "Données manquantes"

**Cause** : Aucune information n'est disponible pour générer la description.

**Solution** :
1. Ajoutez un titre au panoramique
2. OU ajoutez un titre à la visite
3. OU géolocalisez le panoramique (panneau Géolocalisation)

### Génération très lente

**Causes possibles** :
- Premier appel au modèle (chargement initial)
- Machine peu puissante
- Modèle trop volumineux

**Solutions** :
1. Utilisez un modèle plus léger : `ollama pull gemma`
2. Soyez patient lors du premier appel (le modèle se charge en mémoire)
3. Augmentez le timeout si nécessaire

### Description en mauvaise langue

**Cause** : La détection de langue se base sur la locale de l'interface.

**Solution** :
- Vérifiez la langue de votre interface (Paramètres → Langue)
- La description sera générée dans la même langue

## 🔒 Confidentialité et sécurité

- ✅ **100% local** : Aucune donnée n'est envoyée sur Internet
- ✅ **Pas de compte** : Aucune inscription requise
- ✅ **Gratuit** : Pas de limite d'utilisation
- ✅ **Open source** : Ollama est un logiciel libre

## 📝 Sauvegarde des descriptions

Les descriptions IA sont automatiquement sauvegardées dans les fichiers `.pvu` avec l'encodage suivant :

```
;descriptionIA:Texte de la description...
```

Les caractères spéciaux sont échappés :
- `;` → `&pv`
- `:` → `&dp`
- Retours à la ligne → `&nl`

## 🆘 Support

Pour toute question ou problème :

1. Consultez la documentation Ollama : [https://github.com/ollama/ollama](https://github.com/ollama/ollama)
2. Vérifiez les logs de l'application
3. Contactez le support PanoVisu

## 📚 Ressources

- **Site Ollama** : [https://ollama.ai](https://ollama.ai)
- **Documentation API** : [https://github.com/ollama/ollama/blob/main/docs/api.md](https://github.com/ollama/ollama/blob/main/docs/api.md)
- **Liste des modèles** : [https://ollama.ai/library](https://ollama.ai/library)

---

**Version** : 3.0.0 | **Build** : 2208 | **Date** : 12 octobre 2025
