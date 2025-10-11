# 📖 Guides Utilisateur et Configuration

Documentation pratique pour l'utilisation et la configuration d'EditeurPanovisu.

---

## 📁 Guides Disponibles

### 🔑 [CONFIGURATION_API_KEYS.md](CONFIGURATION_API_KEYS.md)
**Guide complet de configuration des clés API**

**Contenu :**
- Configuration rapide (3 minutes)
- Services utilisés (LocationIQ, Bing Maps, OpenWeather, Mapbox)
- Obtention des clés pour chaque service
- Sécurité et bonnes pratiques
- Dépannage des problèmes courants

**Statut :** ✅ Complet

---

### 📄 [API_KEYS_README.md](API_KEYS_README.md)
**README simplifié pour les clés API**

**Contenu :**
- Instructions de base
- Configuration minimale
- Usage dans le code

**Statut :** ✅ Complet

---

### 📄 RESUME_CONFIGURATION_API.md
**Contenu :** Résumé de la configuration des API (LocationIQ, Hugging Face, OpenRouter)

**Statut :** ✅ Complet

---

### 🤖 [INTEGRATION_API_IA.md](INTEGRATION_API_IA.md)
**Guide complet d'intégration des API d'Intelligence Artificielle**

**Contenu :**
- Vue d'ensemble de HuggingFaceClient et OpenRouterClient
- Configuration des clés API
- Exemples d'utilisation (génération de texte, chat, résumé)
- Cas d'usage spécifiques pour PanoVisu
  * Génération de descriptions de panoramas
  * Suggestion de hotspots
  * Aide à la navigation
  * Génération de contenu HTML
  * Traduction automatique
- Dépannage et bonnes pratiques
- Classe de test : `TestAIClients.java`

**Services intégrés :**
- 🤗 Hugging Face Inference API (modèle : gpt2)
- 🚀 OpenRouter API Gateway (modèle : llama-3.2-3b-instruct:free)

**Statut :** ✅ Complet avec exemples de code

---

## 🚀 Guides à Venir

### Installation et Démarrage
- [ ] Guide d'installation complet
- [ ] Configuration de l'environnement de développement
- [ ] Compilation et lancement
- [ ] Résolution des problèmes courants

### Utilisation
- [ ] Guide utilisateur de l'interface
- [ ] Création d'un panorama
- [ ] Ajout de hotspots
- [ ] Export et publication
- [ ] Géolocalisation des panoramas

### Configuration Avancée
- [ ] Personnalisation des thèmes
- [ ] Configuration des plugins
- [ ] Intégration avec des services tiers
- [ ] Optimisation des performances

### Développement
- [ ] Architecture du code
- [ ] Ajout de nouvelles fonctionnalités
- [ ] Création de thèmes personnalisés
- [ ] API de plugins

---

## 📋 Structure des Guides

Chaque guide suit cette structure :

### En-tête
```markdown
# 🎯 [Titre du Guide]

**Date :** [Date de création]
**Version :** [Version de l'application]
**Niveau :** [Débutant/Intermédiaire/Avancé]
```

### Sections Standard
1. **Vue d'ensemble** - Présentation rapide
2. **Prérequis** - Ce qu'il faut avant de commencer
3. **Configuration/Utilisation** - Instructions pas à pas
4. **Exemples** - Cas pratiques
5. **Dépannage** - Solutions aux problèmes courants
6. **Ressources** - Liens utiles

---

## 🎯 Niveaux de Difficulté

| Icône | Niveau | Description |
|-------|--------|-------------|
| 🟢 | Débutant | Aucune connaissance préalable requise |
| 🟡 | Intermédiaire | Connaissance de base de l'outil |
| 🟠 | Avancé | Connaissance approfondie requise |
| 🔴 | Expert | Pour les développeurs du projet |

---

## 📚 Guides par Niveau

### 🟢 Débutant
- [x] Configuration des clés API
- [ ] Installation de l'application
- [ ] Premier panorama
- [ ] Interface utilisateur de base

### 🟡 Intermédiaire
- [ ] Géolocalisation avancée
- [ ] Personnalisation de l'interface
- [ ] Import/Export de projets
- [ ] Optimisation des images

### 🟠 Avancé
- [ ] Configuration multi-environnement
- [ ] Intégration avec des CMS
- [ ] Automatisation avec scripts
- [ ] Performance tuning

### 🔴 Expert
- [ ] Architecture interne
- [ ] Développement de plugins
- [ ] Contribution au code
- [ ] Tests et CI/CD

---

## 🔍 Comment Trouver un Guide

### Par Thématique
- **Installation** : `/doc/guides/installation/`
- **Configuration** : `/doc/guides/configuration/`
- **Utilisation** : `/doc/guides/utilisation/`
- **Développement** : `/doc/guides/developpement/`

### Par Fonctionnalité
- **Géolocalisation** : Voir aussi `/doc/geolocalisation/`
- **Debug** : Voir aussi `/doc/debug/`
- **Migration** : Voir aussi `/doc/migration/`

---

## ✍️ Contribuer aux Guides

### Proposer un Nouveau Guide

1. Créer un fichier `.md` dans le dossier approprié
2. Suivre le template standard
3. Ajouter des captures d'écran si pertinent
4. Mettre à jour ce README
5. Soumettre une pull request

### Template de Guide

```markdown
# 🎯 [Titre du Guide]

**Date :** [Date]
**Version :** [Version]
**Niveau :** 🟢 Débutant

---

## 📋 Vue d'Ensemble

[Description courte du guide]

---

## 🎯 Prérequis

- [ ] Prérequis 1
- [ ] Prérequis 2

---

## 🚀 Instructions

### Étape 1 : [Titre]
[Instructions détaillées]

### Étape 2 : [Titre]
[Instructions détaillées]

---

## 💡 Exemples

### Exemple 1 : [Cas d'usage]
[Code ou instructions]

---

## 🚨 Dépannage

### Problème : [Description]
**Solution :** [Explication]

---

## 📚 Ressources

- [Lien 1](url)
- [Lien 2](url)

---

**Dernière mise à jour :** [Date]
```

---

## 📞 Support

Pour toute question ou suggestion de guide :
- Consulter la documentation existante
- Vérifier les guides de debug : `/doc/debug/`
- Créer une issue sur GitHub

---

**Dernière mise à jour :** 11 octobre 2025, 09:20
