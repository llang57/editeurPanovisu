# 📖 Documentation de la classe EditeurPanovisu

## ✅ Travail effectué

La classe principale `EditeurPanovisu.java` a été documentée avec des commentaires Javadoc détaillés.

### 🎯 Éléments documentés

#### 1. Documentation de classe

```java
/**
 * Editeur de visites virtuelles panoramiques
 * 
 * Cette classe principale de l'application permet de créer et éditer des visites virtuelles
 * basées sur des images panoramiques 360°...
 */
public class EditeurPanovisu extends Application {
```

**Contenu ajouté :**
- Description générale du rôle de la classe
- Liste des fonctionnalités principales (7 points)
- Tags @author, @version, @since

---

#### 2. Propriétés statiques (Configuration & Internationalisation)

**Documentées :**
- `strStyleCSS` - Gestion des thèmes (clair/foncé)
- `strCodesLanguesTraduction[]` - Codes locales (fr_FR, en_EN, etc.)
- `strLanguesTraduction[]` - Noms affichés des langues
- `strCurrentDir` - Répertoire courant
- `locale` - Locale de l'application
- `rbLocalisation` - Bundle de ressources

**Format :**
```java
/**
 * Style CSS de l'interface utilisateur
 * 
 * Définit le thème graphique de l'application. Valeurs possibles :
 * - "clair" - Thème clair (par défaut)
 * - "fonce" - Thème sombre
 * 
 * @see #getStrStyleCSS()
 * @see #setStrStyleCSS(String)
 */
public static String strStyleCSS = "clair";
```

---

#### 3. Propriétés statiques (Données du projet)

**Documentées :**
- `iNumHTML` - Compteur de fichiers HTML
- `iNumDiapo` - Compteur de diaporamas
- `panoramiquesProjet[]` - Tableau des panoramiques (max 100)
- `plans[]` - Tableau des plans (max 20)
- `iNombrePanoramiques` - Nombre de panoramiques
- `iNombrePanoramiquesFichier` - Fichiers chargés

**Organisation :**
Ajout de séparateurs thématiques :
```java
// ====================================================================
// DONNÉES DU PROJET
// ====================================================================
```

---

#### 4. Propriétés statiques (Répertoires)

**Documentées (avec descriptions enrichies) :**
- `strRepertAppli` - Répertoire d'installation
- `strRepertTemp` - Répertoire temporaire
- `strRepertPanos` - Images panoramiques
- `strRepertHSImages` - Images des hotspots
- `strRepertoireProjet` - Projet courant (.pvi)
- `strDernierRepertoireVisite` - Mémorisation pour génération
- `fileRepertConfig` - Fichier de configuration

---

#### 5. Méthodes principales

##### `netIsAvailable()`
```java
/**
 * Vérifie la disponibilité de la connexion Internet
 * 
 * Cette méthode tente de se connecter à Google pour vérifier la connexion réseau.
 * Elle est utilisée pour activer/désactiver les fonctionnalités nécessitant Internet
 * (géolocalisation, cartes, etc.).
 * 
 * @return true si la connexion Internet est disponible, false sinon
 */
private static boolean netIsAvailable()
```

##### `genereVisite()`
```java
/**
 * Génère la visite virtuelle HTML complète
 * 
 * Cette méthode est le point d'entrée principal pour la génération d'une visite virtuelle.
 * Elle effectue les opérations suivantes :
 * 1. Vérifie que le projet est sauvegardé
 * 2. Crée l'arborescence des répertoires nécessaires
 * 3. Copie les fichiers de ressources (JavaScript, CSS, images)
 * ...
 * 
 * @throws IOException Si une erreur se produit lors de la création des fichiers
 * @see #projetSauve()
 * @see #isbDejaSauve()
 */
private static void genereVisite() throws IOException
```

##### `start(Stage)`
```java
/**
 * Méthode de démarrage de l'application JavaFX
 * 
 * Cette méthode est appelée automatiquement au lancement de l'application.
 * Elle initialise tous les composants de l'interface utilisateur :
 * - Détection du système d'exploitation et adaptation de l'interface
 * - Vérification de la connexion Internet
 * - Chargement de la localisation et des préférences utilisateur
 * ...
 * 
 * @param stPrimaryStage La scène principale JavaFX fournie par le framework
 * @throws Exception Si une erreur se produit lors de l'initialisation
 */
@Override
public void start(Stage stPrimaryStage) throws Exception
```

##### `main(String[])`
```java
/**
 * Point d'entrée principal de l'application
 * 
 * Cette méthode est appelée au démarrage du programme. Elle effectue les initialisations
 * de base avant de lancer l'interface JavaFX :
 * - Configure l'encodage des fichiers en UTF-8
 * - Détecte le système d'exploitation (Windows, Mac, Linux)
 * - Lance l'application JavaFX via {@link #start(Stage)}
 * 
 * Exemple d'utilisation :
 * java -jar EditeurPanovisu.jar
 * 
 * @param args Arguments de ligne de commande (non utilisés actuellement)
 */
public static void main(String[] args)
```

---

#### 6. Méthodes de chargement d'icônes SVG

**3 surcharges documentées :**

```java
/**
 * Charge une icône SVG avec la taille spécifiée
 * 
 * Charge un fichier SVG depuis le répertoire des ressources et le convertit en ImageView.
 * L'icône est redimensionnée en carré de la taille spécifiée.
 * 
 * Exemple :
 * ImageView icone = loadSvgIcon("nouveau-projet", 32);
 * bouton.setGraphic(icone);
 * 
 * @param iconName Nom du fichier SVG sans extension
 * @param size Taille en pixels (largeur et hauteur identiques)
 * @return ImageView contenant l'icône SVG redimensionnée
 */
public static ImageView loadSvgIcon(String iconName, int size)
```

---

## 📊 Statistiques

| Élément | Nombre |
|---------|--------|
| **Documentation de classe** | 1 |
| **Propriétés documentées** | ~18 |
| **Méthodes documentées** | 8 |
| **Séparateurs thématiques** | 3 |
| **Tags @see ajoutés** | ~15 |
| **Exemples de code** | 4 |

---

## 🎨 Qualité de la documentation

### ✅ Points forts

1. **Descriptions détaillées** : Chaque élément a une description complète et contextuelle
2. **Organisation thématique** : Séparateurs visuels pour regrouper les éléments similaires
3. **Références croisées** : Tags @see pour lier les getters/setters et méthodes liées
4. **Exemples pratiques** : Code d'exemple pour les méthodes importantes
5. **Format HTML** : Utilisation de listes `<ul>`, `<ol>`, `<pre>` pour la clarté
6. **Métadonnées** : Tags @param, @return, @throws, @author, @version, @since

### 📝 Conventions respectées

- **Javadoc standard** : Format reconnu par Doxygen et javadoc
- **Première phrase** : Résumé concis en une phrase
- **Paragraphes détaillés** : Explications approfondies ensuite
- **Tags ordonnés** : @param, @return, @throws, @see, @deprecated

---

## 🔄 Prochaines étapes suggérées

### Priorité 1 - Classes métier
- [ ] `Panoramique.java` - Classe centrale pour les panoramiques
- [ ] `HotSpot.java` - Gestion des points d'intérêt
- [ ] `Plan.java` - Plans de navigation

### Priorité 2 - Gestionnaires
- [ ] `GestionnaireInterfaceController.java`
- [ ] `GestionnairePlanController.java`
- [ ] `GestionnaireDiaporamaController.java`

### Priorité 3 - Utilitaires
- [ ] `SvgIconLoader.java` (déjà documenté partiellement)
- [ ] `ThemeManager.java`
- [ ] Classes de traitement d'images

---

## 🚀 Génération et consultation

### Régénérer la documentation

```powershell
# Depuis la racine du projet
cd doc/doxygen
doxygen Doxyfile
```

### Consulter la documentation

```powershell
# Ouvrir dans le navigateur
start doc/doxygen/html/index.html
```

Ou directement : `doc\doxygen\html\classEditeurPanovisu.html`

---

## 📚 Ressources

- **Guide complet** : `doc/doxygen/GUIDE_DOCUMENTATION.md`
- **Exemples** : `doc/doxygen/EXEMPLE_DOCUMENTATION.md`
- **Démarrage rapide** : `doc/doxygen/DEMARRAGE_RAPIDE.md`

---

**✨ Documentation créée avec soin pour faciliter la maintenance et l'évolution du projet !**
