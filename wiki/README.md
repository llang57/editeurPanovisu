# 📖 Guide d'utilisation du Wiki PanoVisu

Ce dossier contient les fichiers sources Markdown du Wiki GitHub de PanoVisu.

## 📁 Structure des fichiers

| Fichier | Description | Statut |
|---------|-------------|--------|
| `Home.md` | Page d'accueil du Wiki avec navigation | ✅ Complet |
| `Installation.md` | Guide d'installation multi-plateforme | ✅ Complet |
| `Démarrage-rapide.md` | Tutoriel de démarrage (10 minutes) | ✅ Complet |
| `Guide-utilisateur.md` | Guide d'utilisation complet | 🔄 En cours |
| `Configuration-avancée.md` | Configuration avancée et optimisations | ✅ Complet |
| `Documentation-technique.md` | Documentation pour développeurs | ✅ Complet |
| `FAQ.md` | Foire aux questions | ✅ Complet |
| `Changelog.md` | Historique des versions | ✅ Complet |

## 🚀 Comment publier le Wiki sur GitHub

Le Wiki GitHub doit être créé **manuellement** via l'interface web de GitHub. Voici la procédure complète :

### 1. Activer le Wiki sur GitHub

1. Rendez-vous sur : https://github.com/llang57/editeurPanovisu/settings
2. Scrollez jusqu'à la section **Features**
3. Cochez ✅ **Wikis**
4. Cliquez sur **Save changes**

### 2. Accéder au Wiki

1. Allez sur : https://github.com/llang57/editeurPanovisu
2. Cliquez sur l'onglet **Wiki** (en haut)
3. Cliquez sur **Create the first page**

### 3. Créer les pages du Wiki

Pour chaque fichier de ce dossier, créez une page correspondante :

#### Page 1 : Home (Accueil)

1. Dans l'éditeur GitHub Wiki, le titre par défaut est **Home**
2. Copiez **tout le contenu** de `wiki/Home.md`
3. Collez dans l'éditeur
4. Cliquez sur **Save Page**

#### Page 2 : Installation

1. Cliquez sur **New Page** (bouton en haut à droite)
2. Dans **Page title**, écrivez : `Installation`
3. Copiez **tout le contenu** de `wiki/Installation.md`
4. Collez dans l'éditeur
5. Cliquez sur **Save Page**

#### Page 3 : Démarrage rapide

1. Cliquez sur **New Page**
2. Dans **Page title**, écrivez : `Démarrage-rapide` ⚠️ **Attention à l'accent circonflexe**
3. Copiez **tout le contenu** de `wiki/Démarrage-rapide.md`
4. Collez dans l'éditeur
5. Cliquez sur **Save Page**

#### Page 4 : Guide utilisateur

1. Cliquez sur **New Page**
2. Dans **Page title**, écrivez : `Guide-utilisateur`
3. Copiez **tout le contenu** de `wiki/Guide-utilisateur.md`
4. Collez dans l'éditeur
5. Cliquez sur **Save Page**

#### Page 5 : Configuration avancée

1. Cliquez sur **New Page**
2. Dans **Page title**, écrivez : `Configuration-avancée`
3. Copiez **tout le contenu** de `wiki/Configuration-avancée.md`
4. Collez dans l'éditeur
5. Cliquez sur **Save Page**

#### Page 6 : Documentation technique

1. Cliquez sur **New Page**
2. Dans **Page title**, écrivez : `Documentation-technique`
3. Copiez **tout le contenu** de `wiki/Documentation-technique.md`
4. Collez dans l'éditeur
5. Cliquez sur **Save Page**

#### Page 7 : FAQ

1. Cliquez sur **New Page**
2. Dans **Page title**, écrivez : `FAQ`
3. Copiez **tout le contenu** de `wiki/FAQ.md`
4. Collez dans l'éditeur
5. Cliquez sur **Save Page**

#### Page 8 : Changelog

1. Cliquez sur **New Page**
2. Dans **Page title**, écrivez : `Changelog`
3. Copiez **tout le contenu** de `wiki/Changelog.md`
4. Collez dans l'éditeur
5. Cliquez sur **Save Page**

### 4. Créer la barre latérale (Sidebar)

La barre latérale permet une navigation rapide dans le Wiki :

1. Dans le Wiki, cliquez sur **Add a custom sidebar**
2. Copiez-collez ce contenu :

```markdown
### 📚 Navigation

#### 🚀 Démarrer
- [Accueil](Home)
- [Installation](Installation)
- [Démarrage rapide](Démarrage-rapide)

#### 📖 Utilisation
- [Guide utilisateur](Guide-utilisateur)
- [Configuration avancée](Configuration-avancée)

#### 👨‍💻 Développement
- [Documentation technique](Documentation-technique)

#### ❓ Support
- [FAQ](FAQ)
- [Changelog](Changelog)

#### 🔗 Liens externes
- [Site web](https://lemondea360.fr/panovisu)
- [Dépôt GitHub](https://github.com/llang57/editeurPanovisu)
- [Issues](https://github.com/llang57/editeurPanovisu/issues)
- [Discussions](https://github.com/llang57/editeurPanovisu/discussions)
```

3. Cliquez sur **Save Page**

### 5. Vérifier les liens

Une fois toutes les pages créées :

1. Retournez sur la page **Home**
2. Cliquez sur chaque lien pour vérifier qu'il fonctionne
3. Si un lien ne fonctionne pas, vérifiez l'orthographe exacte du titre de la page

⚠️ **Important** : Les noms de pages dans les liens doivent correspondre **exactement** aux titres des pages (casse et accents).

## 🔧 Mise à jour du Wiki

Pour mettre à jour le Wiki après modifications locales :

1. **Modifiez** les fichiers `.md` dans ce dossier `wiki/`
2. **Commitez** les changements :
   ```bash
   git add wiki/
   git commit -m "docs: Mise à jour du Wiki"
   git push
   ```
3. **Copiez-collez** manuellement le contenu mis à jour dans les pages correspondantes du Wiki GitHub

⚠️ **Note** : Il n'existe pas de synchronisation automatique Git → GitHub Wiki. La mise à jour doit être manuelle.

## 🤝 Alternative : Cloner le Wiki

Le Wiki GitHub peut aussi être cloné comme un dépôt Git séparé :

```bash
# Cloner le Wiki
git clone https://github.com/llang57/editeurPanovisu.wiki.git

# Copier les fichiers depuis wiki/
cp wiki/*.md editeurPanovisu.wiki/

# Commiter et pusher
cd editeurPanovisu.wiki
git add .
git commit -m "Mise à jour complète du Wiki"
git push
```

Cette méthode permet une synchronisation plus rapide, mais nécessite de gérer deux dépôts Git.

## 📝 Conventions d'écriture

Pour maintenir une cohérence dans le Wiki :

### Titres

- **Niveau 1** (`#`) : Titre principal de la page
- **Niveau 2** (`##`) : Sections principales
- **Niveau 3** (`###`) : Sous-sections
- **Niveau 4** (`####`) : Détails

### Emojis

Utiliser des emojis pour améliorer la lisibilité :

- 🎉 Nouveautés
- 🔧 Améliorations
- 🐛 Bugs
- 📚 Documentation
- ⚠️ Avertissements
- ✅ Succès / Fait
- ❌ Erreur / Non supporté
- 📖 Lecture / Guide
- 💬 Discussion
- 🌐 Web / Lien externe

### Blocs de code

```bash
# Commandes shell avec langage spécifié
commande --option
```

```java
// Code Java avec syntaxe
public class Example { }
```

### Tableaux

Utiliser des tableaux pour les comparaisons :

| Critère | Option A | Option B |
|---------|----------|----------|
| Prix | Gratuit | Payant |
| Facilité | ✅ Facile | ⚠️ Moyen |

### Liens

- **Liens internes** (vers d'autres pages du Wiki) : `[Texte](Nom-de-la-page)`
- **Liens externes** : `[Texte](https://example.com)`
- **Liens GitHub** : `[Issues](https://github.com/llang57/editeurPanovisu/issues)`

## 📊 Statistiques

| Métrique | Valeur |
|----------|--------|
| **Pages totales** | 8 |
| **Mots (approx.)** | ~15 000 |
| **Temps de lecture** | ~60 minutes |
| **Niveau** | Débutant à avancé |

## 🎯 Objectifs du Wiki

- ✅ Fournir une documentation complète et accessible
- ✅ Réduire les questions récurrentes (FAQ)
- ✅ Faciliter la contribution au projet
- ✅ Améliorer l'expérience utilisateur
- ✅ Centraliser les connaissances

## 🆘 Besoin d'aide ?

Si vous rencontrez des problèmes lors de la création du Wiki :

1. **Vérifiez** que le Wiki est bien activé dans les paramètres du dépôt
2. **Assurez-vous** d'avoir les droits d'administration sur le dépôt
3. **Consultez** la [documentation GitHub Wiki](https://docs.github.com/fr/communities/documenting-your-project-with-wikis)
4. **Ouvrez** une [discussion](https://github.com/llang57/editeurPanovisu/discussions) si le problème persiste

## 📅 Maintenance

Le Wiki doit être mis à jour régulièrement :

- **À chaque release** : Mettre à jour le Changelog
- **Nouvelles fonctionnalités** : Documenter dans le Guide utilisateur
- **Bugs corrigés** : Mettre à jour la FAQ si pertinent
- **Changements d'API** : Mettre à jour la Documentation technique

## 📄 Licence

La documentation de ce Wiki est sous licence **GPL-3.0**, tout comme le projet PanoVisu.

---

**Créé le** : 2025-01-15  
**Dernière mise à jour** : 2025-01-15  
**Auteur** : llang57  
**Version** : 1.0
