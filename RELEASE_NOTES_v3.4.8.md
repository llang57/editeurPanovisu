# PanoVisu - Release Notes v3.4.8

## Nouvelles fonctionnalités IA & Mises à jour des modèles (Mai 2026)
* **Système dynamique d'interface :** Les informations de coûts, étoiles de qualité et tailles des modèles sont maintenant lues de façon totalement dynamique depuis les fichiers JSON (`openrouter-models.json` et `ollama-models.json`) et ne sont plus codées en dur.
* **OpenRouter (Cloud) :**
  * Ajout de **Gemini 3.1 Flash Lite** (modèle très abordable et ultra-rapide de Google).
  * Ajout de **Gemini 3.1 Pro Preview**.
  * Intégration des modèles de pointe : Claude 4.6 Sonnet, Claude Opus 4.7, DeepSeek V4 Pro et GPT-5.5.
  * Nettoyage des anciens modèles redondants (suppression de Gemini 2.5 Flash).
* **Ollama (Local) :**
  * Intégration de **Qwen 3.5**, sélectionné spécifiquement pour sa spécialisation multilingue et son expertise en toponymie/géographie (remplace Qwen 2.5).
  * Ajout des derniers modèles open source : DeepSeek R1 (14B), Phi-4 (14B), Gemma 4 et Mistral Nemo.

## Améliorations de la Génération de Descriptions (Anti-Hallucination)
* **Refonte du prompt système dans OllamaService :** Implémentation d'un ensemble de règles strictes "zéro-hallucination" pour la génération des textes des panoramas.
* **Précision renforcée :** Le modèle d'IA reçoit formellement le rôle d'un guide expert soumis à des interdictions absolues (interdiction stricte d'inventer des noms propres, des événements historiques non avérés, des dates ou des métriques).
* **Comportement de repli (Fallback) :** En cas d'absence de données historiques certifiées, le système impose à l'IA d'utiliser des descriptions visuelles, sensorielles et géographiques génériques plutôt que d'inventer des faits.
* **Formatage propre :** Génération stricte du contenu factuel ciblé, sans formules de politesse ni fioritures conversationnelles ("Voici la description...", etc.).

## Environnement & Dépendances
* **Script de développement :** Ajout du script `install-java-maven.ps1` facilitant le déploiement rapide de l'environnement complet Java 25 et Maven 3.9.16 pour la compilation du projet.
* **Mise à jour des propriétés :** Montée de version à 3.4.8 dans `pom.xml`, `project.properties`, `installer.iss` et les fichiers d'internationalisation.

---

## 📅 Historique Récent (depuis v3.4.0)

### v3.4.6
* **Visualiseur panoramique cube** : Remplacement du `Box` JavaFX par un maillage triangulaire complet avec corrections d'orientation (haut/bas, miroir).
* **Qualité plein écran** : Passage du cache de grande résolution à 2000px/face pour une immersion maximale.
* **Correction des projections** : Résolution des bugs de distorsion en utilisant strictement la projection équirectangulaire.

### v3.4.4
* **Rendu 3D Panoramique Cube** : Introduction initiale de `PanoramicCube.java` pour un rendu en 6 faces sans déformations.
* **Stabilité** : Correction du `NoClassDefFoundError` avec Maven lors de l'exécution avec JavaFX.
* **CI/CD** : Détection dynamique de la version dans les scripts de build (Linux/Windows).

### v3.4.2
* **Détection GPU (Windows)** : Support officiel de l'accélération OpenCL sur cartes NVIDIA/AMD récentes (ex: RTX 5070 Ti).
* **Optimisation Mémoire** : Résolution du plantage mémoire lors de l'import de nombreux panoramas (issue #12).

### v3.4.0
* **Modèles IA Dynamiques** : Introduction de la configuration JSON avec `openrouter-models.json` et `ollama-models.json`.
* **Interface UI dédiée** : Ajout d'un panneau complet (`Ctrl+M`) pour activer, désactiver et réordonner les modèles.
