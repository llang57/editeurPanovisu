# 🤖 Installation d'Ollama pour les Descriptions IA

## Qu'est-ce qu'Ollama ?

**Ollama** est un logiciel gratuit qui permet d'utiliser l'intelligence artificielle directement sur votre ordinateur, **sans connexion internet** et **sans partager vos données** en ligne.

Dans **EditeurPanovisu**, Ollama vous permet de générer automatiquement des descriptions pour vos panoramiques en utilisant l'IA de manière **privée** et **gratuite**.

### ✅ Avantages d'Ollama

- 🔒 **Confidentialité totale** : Vos données restent sur votre ordinateur
- 💰 **100% gratuit** : Pas d'abonnement, pas de limite d'utilisation
- 🚀 **Rapide** : L'IA fonctionne directement sur votre PC
- 🌐 **Hors ligne** : Fonctionne sans connexion internet
- 🎯 **Qualité** : Modèles d'IA performants et précis

---

## 📋 Prérequis

Avant d'installer Ollama, vérifiez que votre ordinateur répond aux exigences minimales :

### Configuration minimale
- **Système d'exploitation** : Windows 10/11 (64 bits), macOS 11+, ou Linux (Ubuntu 20.04+, Debian, Fedora, etc.)
- **Mémoire RAM** : 8 Go minimum (16 Go recommandé)
- **Espace disque** : 10 Go d'espace libre minimum
- **Processeur** : Processeur moderne (Intel i5 ou AMD Ryzen 5 minimum)

### Configuration recommandée pour de meilleures performances
- **Mémoire RAM** : 16 Go ou plus
- **Espace disque** : 20 Go ou plus (pour plusieurs modèles)
- **Carte graphique** : NVIDIA RTX (optionnel, accélère les calculs)

> ℹ️ **Note** : Ollama fonctionne sur CPU, mais une carte graphique NVIDIA améliore considérablement les performances.

---

## 🚀 Installation d'Ollama

### 🐧 Installation sous Linux (Recommandé - Plus simple !)

L'installation sous Linux est **très simple** - une seule commande suffit !

#### Méthode automatique (toutes distributions)

1. **Ouvrez un terminal** (Ctrl+Alt+T)
2. **Copiez-collez cette commande** :
   ```bash
   curl -fsSL https://ollama.com/install.sh | sh
   ```
3. **Appuyez sur Entrée** et entrez votre mot de passe si demandé
4. **Attendez la fin de l'installation** (quelques secondes)

**C'est tout !** ✅ Ollama est maintenant installé et démarré automatiquement.

#### Vérifier l'installation

Dans le terminal, tapez :
```bash
ollama --version
```

Vous devriez voir le numéro de version (par exemple `ollama version is 0.3.12`).

#### Installer un modèle d'IA

Directement dans le terminal :
```bash
# Modèle recommandé pour débuter (petit et rapide)
ollama pull llama3.2

# OU modèle avec meilleur qualité
ollama pull mistral
```

#### Démarrer/Arrêter Ollama sous Linux

Ollama démarre automatiquement au boot. Pour le contrôler :

```bash
# Vérifier le statut
systemctl status ollama

# Arrêter Ollama
sudo systemctl stop ollama

# Démarrer Ollama
sudo systemctl start ollama

# Désactiver le démarrage automatique
sudo systemctl disable ollama
```

> 💡 **Astuce** : Sous Linux, Ollama fonctionne comme un service système, c'est encore plus pratique qu'sous Windows !

---

### 🪟 Installation sous Windows

### 🪟 Installation sous Windows

#### Étape 1 : Télécharger Ollama

1. Ouvrez votre navigateur web
2. Allez sur le site officiel : **https://ollama.com/**
3. Cliquez sur le bouton **"Download"** (Télécharger)
4. Le fichier `OllamaSetup.exe` sera téléchargé

![Téléchargement Ollama](https://ollama.com/public/og-image.png)

#### Étape 2 : Installer Ollama

1. **Localisez** le fichier `OllamaSetup.exe` dans votre dossier Téléchargements
2. **Double-cliquez** sur le fichier pour lancer l'installation
3. Si Windows affiche une alerte de sécurité, cliquez sur **"Exécuter quand même"**
4. Suivez l'assistant d'installation :
   - Cliquez sur **"Install"** (Installer)
   - Attendez la fin de l'installation (quelques secondes)
   - Cliquez sur **"Finish"** (Terminer)

> ⚠️ **Important** : Une icône Ollama apparaîtra dans la barre des tâches (près de l'horloge). Cela signifie qu'Ollama est en cours d'exécution.

#### Étape 3 : Vérifier l'installation

Pour vérifier qu'Ollama est bien installé :

1. Ouvrez le **Menu Démarrer**
2. Tapez **"cmd"** et appuyez sur **Entrée**
3. Dans la fenêtre noire qui s'ouvre, tapez :
   ```
   ollama --version
   ```
4. Appuyez sur **Entrée**

Si vous voyez un numéro de version (par exemple `ollama version is 0.3.12`), c'est parfait ! ✅

---

### 🍎 Installation sous macOS

1. **Téléchargez** Ollama depuis : **https://ollama.com/download**
2. **Ouvrez** le fichier `.dmg` téléchargé
3. **Glissez-déposez** l'application Ollama dans le dossier Applications
4. **Lancez** Ollama depuis le dossier Applications
5. L'icône Ollama apparaît dans la barre de menu en haut

**Vérification** dans le Terminal :
```bash
ollama --version
```

**Installer un modèle** :
```bash
ollama pull llama3.2
```

---

## 📦 Installation d'un Modèle d'IA

Ollama ne contient pas de modèle d'IA par défaut. Vous devez en télécharger un.

### Modèles recommandés pour EditeurPanovisu

| Modèle | Taille | RAM nécessaire | Qualité | Vitesse |
|--------|--------|----------------|---------|---------|
| **llama3.2** | 2 GB | 8 GB | ⭐⭐⭐ | 🚀🚀🚀 |
| **mistral** | 4 GB | 8 GB | ⭐⭐⭐⭐ | 🚀🚀 |
| **llama3.1** | 5 GB | 12 GB | ⭐⭐⭐⭐⭐ | 🚀 |
| **gemma2** | 5 GB | 12 GB | ⭐⭐⭐⭐⭐ | 🚀 |

> 💡 **Recommandation** : Pour débuter, installez **llama3.2** (petit et rapide) ou **mistral** (bon compromis qualité/vitesse).

### Comment installer un modèle

#### Méthode simple (toutes plateformes)

**Sous Linux/macOS** - Ouvrez un **Terminal**

**Sous Windows** - Ouvrez le **Menu Démarrer**, tapez **"cmd"** et appuyez sur **Entrée**

Puis tapez l'une de ces commandes :

**Pour Llama 3.2 (recommandé pour débuter)** :
```
ollama pull llama3.2
```

**Pour Mistral (bon compromis)** :
```
ollama pull mistral
```

**Pour Llama 3.1 (qualité maximale)** :
```
ollama pull llama3.1
```

**Appuyez sur Entrée**. Le téléchargement commence (cela peut prendre 5 à 30 minutes selon votre connexion). Attendez que le message **"success"** s'affiche.

![Téléchargement modèle](https://i.imgur.com/example.png)

> ⏱️ **Patience** : Le téléchargement peut être long la première fois (plusieurs gigaoctets). Une fois téléchargé, le modèle est disponible immédiatement.

### Vérifier les modèles installés

Pour voir quels modèles sont installés, dans un terminal (Linux/macOS) ou cmd (Windows) :

```bash
ollama list
```

Vous verrez la liste de tous vos modèles installés.

---

## 🎯 Utilisation dans EditeurPanovisu

Une fois Ollama et un modèle installés, vous pouvez utiliser l'IA dans EditeurPanovisu :

### Générer une description automatique

1. **Ouvrez EditeurPanovisu**
2. **Chargez votre projet** de visite virtuelle
3. **Sélectionnez un panoramique** dans la liste
4. Dans le panneau **"Paramètres du panoramique"**, descendez jusqu'à la section **"Description IA"**
5. Cliquez sur le bouton **"Générer description IA"**
6. Attendez quelques secondes ⏳
7. La description apparaît automatiquement ! ✨

### Que fait l'IA ?

L'IA génère une description en se basant sur :
- 📝 **Le titre de la visite**
- 🏷️ **Le titre du panoramique**
- 🌍 **La géolocalisation** (si disponible)

**Exemple de résultat** :
> "Le Château de Versailles est un monument historique situé dans les Yvelines, à 20 km de Paris. Construit au XVIIe siècle sous Louis XIV, il est célèbre pour sa galerie des Glaces et ses jardins à la française."

### Modifier la description

Après la génération, vous pouvez :
- ✏️ **Modifier** le texte directement dans la zone de texte
- 🔄 **Régénérer** une nouvelle description en cliquant à nouveau sur le bouton
- 💾 **Sauvegarder** votre projet pour conserver la description

---

## 🔧 Dépannage

### Problème : "Ollama non disponible"

**Causes possibles** :
1. Ollama n'est pas installé
2. Ollama n'est pas démarré
3. Le port 11434 est bloqué

**Solutions** :

#### Solution 1 : Vérifier qu'Ollama est installé

**Linux/macOS** : Ouvrez un terminal et tapez `ollama --version`

**Windows** : Ouvrez **cmd** et tapez `ollama --version`

Si vous voyez "command not found", réinstallez Ollama

#### Solution 2 : Démarrer Ollama

**Sous Linux** :
```bash
sudo systemctl start ollama
# ou
ollama serve
```

**Sous Windows** :
- Cherchez l'icône **Ollama** dans la barre des tâches (près de l'horloge)
- Si elle n'est pas là, ouvrez le **Menu Démarrer** et lancez **"Ollama"**

**Sous macOS** :
- Lancez Ollama depuis le dossier Applications

#### Solution 3 : Vérifier le service

Ouvrez un terminal (ou cmd sous Windows) et tapez :
```bash
ollama serve
```

Laissez cette fenêtre ouverte et retournez dans EditeurPanovisu

### Problème : "Aucun modèle disponible"

**Cause** : Vous n'avez pas téléchargé de modèle d'IA

**Solution** :

Ouvrez un terminal (Linux/macOS) ou cmd (Windows) et tapez :
```bash
ollama pull llama3.2
```

Attendez la fin du téléchargement et relancez EditeurPanovisu

### Problème : La génération est très lente

**Causes possibles** :
1. Votre PC n'a pas assez de RAM
2. Le modèle est trop gros pour votre configuration
3. D'autres applications utilisent beaucoup de ressources

**Solutions** :
- Fermez les autres applications
- Utilisez un modèle plus petit (llama3.2 au lieu de llama3.1)
- Augmentez la RAM de votre PC si possible

### Problème : Ollama utilise beaucoup de ressources

**C'est normal !** L'IA utilise beaucoup de ressources pour fonctionner.

**Pour limiter l'utilisation** :

**Sous Linux** :
```bash
# Arrêter Ollama
sudo systemctl stop ollama

# Désactiver le démarrage automatique
sudo systemctl disable ollama
```

**Sous Windows** :
1. Clic droit sur l'icône Ollama dans la barre des tâches
2. Cliquez sur **"Quit"** (Quitter)

**Sous macOS** :
1. Cliquez sur l'icône Ollama dans la barre de menu
2. Sélectionnez **"Quit Ollama"**

Vous pouvez aussi utiliser un modèle plus petit (llama3.2) ou lancer Ollama uniquement quand vous en avez besoin.

---

## 🌐 Mode de secours : Hugging Face

Si vous ne pouvez pas installer Ollama, EditeurPanovisu peut utiliser **Hugging Face**, un service en ligne gratuit.

### Différences entre Ollama et Hugging Face

| Critère | Ollama | Hugging Face |
|---------|--------|--------------|
| **Installation** | Nécessaire | Aucune |
| **Connexion internet** | Non requise | Requise |
| **Confidentialité** | Totale | Données envoyées en ligne |
| **Vitesse** | Rapide | Variable |
| **Limite** | Illimitée | Peut avoir des limites |

### Configuration de Hugging Face

1. Créez un compte gratuit sur : **https://huggingface.co/**
2. Obtenez votre **clé API** (token) :
   - Allez dans **Settings** → **Access Tokens**
   - Cliquez sur **"New token"**
   - Copiez la clé générée
3. Dans EditeurPanovisu, ouvrez le fichier `api-keys.properties`
4. Ajoutez votre clé :
   ```
   huggingface.api.key=hf_VOTRE_CLE_ICI
   ```

> ℹ️ **Note** : EditeurPanovisu essaie automatiquement Ollama en premier, puis Hugging Face si Ollama n'est pas disponible.

---

## 📚 Pour aller plus loin

### Explorer d'autres modèles

Ollama propose de nombreux modèles d'IA. Pour voir la liste complète :
- Visitez : **https://ollama.com/library**

Vous pouvez installer n'importe quel modèle avec :
```
ollama pull nom-du-modele
```

### Modèles multilingues avancés

Certains modèles sont spécialement entraînés pour plusieurs langues :
- **aya** : 101 langues (8 GB)
- **command-r** : Excellent pour les instructions (20 GB)

### Supprimer un modèle

Si vous voulez libérer de l'espace disque, dans un terminal (ou cmd) :
```bash
ollama rm nom-du-modele
```

Exemple :
```bash
ollama rm mistral
```

---

## ❓ Questions Fréquentes (FAQ)

### Ollama est-il vraiment gratuit ?
**Oui !** Ollama est 100% gratuit et open source. Pas de frais cachés, pas d'abonnement.

### Mes données sont-elles envoyées quelque part ?
**Non !** Avec Ollama, tout reste sur votre ordinateur. Vos titres de panoramiques, vos géolocalisations et les descriptions générées ne quittent jamais votre PC.

### Puis-je utiliser Ollama sans internet ?
**Oui !** Une fois Ollama et un modèle installés, vous pouvez générer des descriptions sans connexion internet.

### Combien de modèles puis-je installer ?
**Autant que vous voulez !** La seule limite est l'espace disque disponible sur votre ordinateur.

### Puis-je utiliser Ollama pour d'autres choses ?
**Oui !** Ollama peut être utilisé pour de nombreuses tâches d'IA : rédaction, traduction, programmation, etc. C'est un outil polyvalent.

### Quelle différence avec ChatGPT ?
Ollama utilise des modèles similaires à ChatGPT, mais :
- Il fonctionne **sur votre ordinateur** (pas en ligne)
- C'est **gratuit** (pas d'abonnement)
- C'est **privé** (vos données restent chez vous)
- Il est **toujours disponible** (pas de limite de requêtes)

### Puis-je désinstaller Ollama ?

**Oui !** 

**Sous Linux** :
```bash
sudo systemctl stop ollama
sudo systemctl disable ollama
sudo rm /usr/local/bin/ollama
sudo rm -rf /usr/share/ollama
sudo userdel ollama
```

**Sous Windows** :
1. Ouvrez **Paramètres Windows**
2. Allez dans **Applications**
3. Cherchez **"Ollama"**
4. Cliquez sur **"Désinstaller"**

**Sous macOS** :
1. Glissez-déposez Ollama depuis Applications vers la Corbeille
2. Videz la Corbeille

---

## 🆘 Support et Aide

### Besoin d'aide ?

- 📧 **Email** : support@panovisu.com
- 🌐 **Site web** : https://www.lemondea360.fr
- 💬 **Forum** : Posez vos questions sur le forum de la communauté

### Documentation Ollama officielle

- 📖 **Documentation** : https://github.com/ollama/ollama/blob/main/README.md
- 🎥 **Tutoriels vidéo** : Recherchez "Ollama tutorial" sur YouTube

---

## ✨ Conclusion

Félicitations ! Vous savez maintenant comment installer et utiliser Ollama avec EditeurPanovisu.

L'intelligence artificielle est désormais à portée de main pour enrichir vos visites virtuelles avec des descriptions automatiques de qualité.

**Bon travail avec EditeurPanovisu ! 🎉**

---

**Version** : 1.0  
**Date** : 13 octobre 2025  
**Auteur** : EditeurPanovisu - Laurent LANG
