# 📋 Plan d'amélioration de la Javadoc - Classe par classe

**Date :** 26 octobre 2025  
**Objectif :** Améliorer la documentation de 989 méthodes (30 sans doc + 959 incomplètes)

## 🎯 Stratégie

**Principe :** Procéder classe par classe, en commençant par les classes les plus critiques

### Critères de priorisation

1. **Critiques (🔴)** : Classes principales de l'application
2. **Importantes (🟠)** : Classes de contrôleurs et services
3. **Utilitaires (🟡)** : Classes d'aide et tests

---

## 📊 État actuel

- **Total fichiers :** 64
- **Méthodes sans doc :** 30
- **Méthodes doc incomplète :** 959
- **Total à améliorer :** 989

---

## 🔴 PRIORITÉ 1 : Classes principales (8 classes)

### 1. ✅ EditeurPanovisu.java
- **État :** 0 méthodes sans doc, 145 méthodes incomplètes
- **Importance :** Classe principale de l'application
- **Action :** Documenter les getters/setters principaux + méthodes publiques critiques

### 2. ✅ GestionnaireInterfaceController.java
- **État :** 4 méthodes sans doc, 379 méthodes incomplètes
- **Importance :** Contrôleur principal de l'interface
- **Action :** Documenter les méthodes sans doc en priorité, puis les méthodes publiques

### 3. ⏳ NavigateurPanoramique.java
- **État :** 0 méthodes sans doc, 40 méthodes incomplètes
- **Importance :** Gestion de la navigation panoramique
- **Action :** Améliorer la documentation des méthodes de navigation

### 4. ⏳ Panoramique.java
- **État :** 4 méthodes sans doc, 32 méthodes incomplètes
- **Importance :** Classe modèle principale pour les panoramas
- **Action :** Documenter les méthodes sans doc + propriétés principales

### 5. ⏳ GestionnaireInterfaceController.java
- **État :** 4 méthodes sans doc, 379 méthodes incomplètes
- **Importance :** Interface utilisateur principale
- **Action :** Focus sur les méthodes d'événements et d'initialisation

### 6. ⏳ OrdrePanoramique.java
- **État :** 0 méthodes sans doc, 13 méthodes incomplètes
- **Importance :** Gestion de l'ordre des panoramas
- **Action :** Documentation complète des méthodes de tri

### 7. ⏳ Plan.java
- **État :** 2 méthodes sans doc, 17 méthodes incomplètes
- **Importance :** Gestion des plans/cartes
- **Action :** Documenter méthodes sans doc + méthodes publiques

### 8. ⏳ VisualiseurImagesPanoramiques.java
- **État :** 0 méthodes sans doc, 8 méthodes incomplètes
- **Importance :** Visualisation des images
- **Action :** Documentation complète avec exemples

---

## 🟠 PRIORITÉ 2 : Contrôleurs et dialogues (12 classes)

### 9. ⏳ GestionnaireDiaporamaController.java
- **État :** 0 méthodes sans doc, 30 méthodes incomplètes
- **Action :** Documenter les méthodes d'initialisation et de gestion

### 10. ⏳ GestionnairePlanController.java
- **État :** 1 méthode sans doc, 18 méthodes incomplètes
- **Action :** Documenter méthode sans doc + méthodes publiques

### 11. ⏳ ConversionRatio2to1DialogController.java
- **État :** 1 méthode sans doc, 0 méthodes incomplètes
- **Action :** Documenter `updateItem()`

### 12. ⏳ EquiCubeDialogController.java
- **État :** 1 méthode sans doc, 2 méthodes incomplètes
- **Action :** Documenter `updateItem()` + améliorer les 2 autres

### 13. ⏳ RedimensionnementImagesDialogController.java
- **État :** 2 méthodes sans doc, 0 méthodes incomplètes
- **Action :** Documenter les 2 méthodes `updateItem()`

### 14. ⏳ PopUpDialogController.java
- **État :** 1 méthode sans doc, 0 méthodes incomplètes
- **Action :** Documenter la méthode manquante

### 15. ⏳ PrevisualiserConversionDialog.java
- **État :** 1 méthode sans doc, 0 méthodes incomplètes
- **Action :** Documenter la méthode manquante

### 16. ⏳ ConfigDialogController.java
- **État :** 0 méthodes sans doc, 1 méthode incomplète
- **Action :** Améliorer la documentation existante

### 17-20. Autres contrôleurs
- AideDialogController.java
- DocumentationDialog.java
- NavigateurCarteSeul.java
- NavigateurOpenLayersSeul.java

---

## 🟡 PRIORITÉ 3 : Classes modèles et utilitaires (20 classes)

### Modèles de données (8 classes)
- CartePanoVisu.java (12 méthodes incomplètes)
- CoordonneesGeographiques.java (3 méthodes incomplètes)
- Diaporama.java (1 sans doc + 25 incomplètes)
- HotSpot.java (20 méthodes incomplètes)
- HotspotDiaporama.java (8 méthodes incomplètes)
- HotspotHTML.java (8 méthodes incomplètes)
- HotspotImage.java (8 méthodes incomplètes)
- ImageFond.java (8 méthodes incomplètes)

### Services externes (3 classes)
- HuggingFaceClient.java (1 sans doc)
- OpenRouterClient.java (1 sans doc)
- OllamaService.java (1 sans doc)

### Utilitaires graphiques (9 classes)
- NavigateurCarte.java (2 méthodes incomplètes)
- NavigateurCarteGluon.java (0 méthodes incomplètes)
- NavigateurOpenLayers.java (0 méthodes incomplètes)
- EditeurHTML.java (2 sans doc + 12 incomplètes)
- ImageEditeurHTML.java (17 méthodes incomplètes)
- MarqueurGeolocalisation.java (4 méthodes incomplètes)
- ListePanoramiqueCellule.java (1 sans doc)
- PanoramiqueCellule.java (1 sans doc)
- ZoneTelecommande.java (6 méthodes incomplètes)

---

## 🔵 PRIORITÉ 4 : GPU et transformations (8 classes)

### Traitement GPU
- TransformationsPanoramique.java (2 sans doc + 3 incomplètes)
- TransformationsPanoramiqueGPU.java (1 sans doc + 3 incomplètes)
- gpu/GPUManager.java (1 sans doc)
- gpu/GPUKernelLoader.java (1 sans doc)
- gpu/ImageResizeGPU.java (2 sans doc + 1 incomplète)

### Tests GPU
- gpu/TestBicubicVsLanczos.java (1 sans doc)
- gpu/TestImageResize.java (1 sans doc)
- gpu/TestImageResizeInteractive.java (1 sans doc)
- gpu/TestReductionImage.java (1 sans doc)

---

## 🟢 PRIORITÉ 5 : Utilitaires et helpers (16 classes)

### Utilitaires techniques
- ApiKeysConfig.java
- BigDecimalField.java
- BuildNumberIncrementer.java
- ExtensionsFilter.java (1 sans doc)
- Launcher.java
- MarkdownViewer.java
- PaneOutil.java (5 méthodes incomplètes)
- ReadWriteImage.java
- TestAIClients.java
- TextUtils.java
- ThemeManager.java
- PanoramicCube.java (6 méthodes incomplètes)

### Services web
- LocalHTTPServer.java
- SvgIconLoader.java
- EsriTileRetriever.java

---

## 📝 Méthode de travail

### Pour chaque classe :

1. **Lire le fichier** et comprendre son rôle
2. **Identifier les méthodes critiques** (publiques, complexes)
3. **Documenter dans l'ordre** :
   - Méthodes SANS documentation (priorité absolue)
   - Méthodes publiques principales
   - Getters/setters importants
4. **Vérifier** avec les scripts Python
5. **Commit** après chaque classe ou groupe de classes

### Template de documentation

```java
/**
 * [Description détaillée de ce que fait la méthode]
 * 
 * <p>[Contexte d'utilisation ou précisions]</p>
 * 
 * @param nomParam Description du paramètre
 * @return Description de la valeur retournée
 * @throws ExceptionType Description de quand l'exception est levée
 * 
 * @see ClasseAssociée#méthodeAssociée
 * 
 * <p><b>Exemple :</b></p>
 * <pre>{@code
 * MonObjet obj = new MonObjet();
 * obj.maMethode(param);
 * }</pre>
 */
```

---

## 🎯 Objectifs par session

- **Session 1** : 5-10 classes de priorité 1 (✅ 2 déjà faites)
- **Session 2** : 10-15 classes de priorité 2
- **Session 3** : Classes restantes + revue globale

---

## ✅ Progression

**Légende :**
- ✅ Complété
- 🚧 En cours
- ⏳ À faire

**Classes documentées : 2 / 64**

---

## 📈 Métriques de succès

- [ ] 0 méthodes publiques sans documentation
- [ ] 100% des méthodes principales avec exemples
- [ ] Tous les @param/@return renseignés
- [ ] 0 duplication de Javadoc
- [ ] Javadoc générée sans erreur (100 warnings max acceptés)

---

*Dernière mise à jour : 26 octobre 2025*
