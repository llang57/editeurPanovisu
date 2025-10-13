# 🔧 Modifications de la Fenêtre de Préférences - Build 2269

## Vue d'ensemble

La fenêtre de préférences a été modernisée pour gérer les clés API et supprimer les options obsolètes.

---

## ✅ Modifications effectuées

### 1. **Suppression des éléments obsolètes**

#### ❌ Style Clair/Foncé (Radio Buttons)
- **Supprimé** : `RadioButton rbClair` et `rbFonce`
- **Supprimé** : `ToggleGroup tgStyle`
- **Supprimé** : `Label lblChoixStyle`
- **Raison** : Remplacé par le système de thèmes AtlantaFX (menu "Changer le thème..." + Ctrl+T)

#### ❌ Clé API Bing
- **Supprimé** : `TextField tfBingAPIKey`
- **Supprimé** : `Label lblBingAPIKey`
- **Raison** : L'application utilise maintenant OpenStreetMap exclusivement (Bing Maps obsolète)

---

### 2. **Ajout de nouveaux champs API** ✨

La fenêtre affiche maintenant **3 champs de clés API** :

#### 🗺️ LocationIQ API Key
- **Champ** : `TextField tfLocationIQKey`
- **Usage** : Géocodage et recherche d'adresses dans les cartes
- **Format** : `pk.xxxxx...`
- **Gratuit** : 5 000 requêtes/jour

#### 🤖 Hugging Face API Key
- **Champ** : `TextField tfHuggingFaceKey`
- **Usage** : Modèles d'IA et transformers pour génération de descriptions
- **Format** : `hf_xxxxx...`
- **Gratuit** : Accès aux modèles publics

#### 🌐 OpenRouter API Key
- **Champ** : `TextField tfOpenRouterKey`
- **Usage** : API Gateway pour accès à divers modèles LLM
- **Format** : `sk-or-v1-xxxxx...`
- **Gratuit** : Certains modèles gratuits disponibles

---

### 3. **Gestion des fichiers de configuration**

#### Fichier `panovisu.cfg` (simplifié)
**Avant** :
```
langue=fr
pays=FR
repert=D:\visites\Lastour\visite\panos
bingAPIKey=XXX
style=clair
```

**Après** :
```
langue=fr
pays=FR
repert=D:\visites\Lastour\visite\panos
```

➡️ **Supprimé** : `bingAPIKey` et `style`

#### Fichier `api-keys.properties` (centralisé)
**Contenu** :
```properties
# LocationIQ - Géocodage et Recherche d'Adresses
locationiq.api.key=pk.xxxxx...

# Hugging Face - Modèles d'IA et Transformers
huggingface.api.key=hf_xxxxx...

# OpenRouter - API Gateway pour Modèles LLM
openrouter.api.key=sk-or-v1-xxxxx...
```

➡️ **Toutes les clés API** sont maintenant dans un seul fichier dédié

---

### 4. **Interface utilisateur modernisée**

#### Dimensions
- **Largeur** : 600px (inchangé)
- **Hauteur** : 400px → **550px** (agrandie pour les nouveaux champs)
- **Pane Config** : 360px → **510px**

#### Layout des composants

```
┌─────────────────────────────────────────────────────┐
│  Langue : [Combobox]                   (y=25)       │
│  Répertoire projet : [TextField] [...]  (y=110)     │
│                                                      │
│  ═══ Clés API ═══                       (y=150)     │
│                                                      │
│  LocationIQ API Key                     (y=180)     │
│  [pk.xxxxx...]                          (y=205)     │
│                                                      │
│  Hugging Face API Key                   (y=240)     │
│  [hf_xxxxx...]                          (y=265)     │
│                                                      │
│  OpenRouter API Key                     (y=300)     │
│  [sk-or-v1-xxxxx...]                    (y=325)     │
│                                                      │
│  💡 Les clés API sont sauvegardées      (y=360)     │
│     dans api-keys.properties                        │
│                                                      │
│         [Annuler]  [Sauvegarder]                    │
└─────────────────────────────────────────────────────┘
```

---

## 🔧 Modifications du code

### ConfigDialogController.java

#### Imports ajoutés
```java
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;
```

#### Champs supprimés
```java
// Supprimé
final ToggleGroup tgStyle = new ToggleGroup();
private static Label lblChoixStyle;
private static RadioButton rbClair;
private static RadioButton rbFonce;
private static TextField tfBingAPIKey;
```

#### Champs ajoutés
```java
private static TextField tfLocationIQKey;
private static TextField tfHuggingFaceKey;
private static TextField tfOpenRouterKey;
```

#### Nouvelles méthodes

**1. `loadApiKeys()` - Chargement**
```java
private Properties loadApiKeys() {
    Properties props = new Properties();
    File apiKeysFile = new File("api-keys.properties");
    
    if (apiKeysFile.exists()) {
        try (InputStreamReader reader = new InputStreamReader(
            new FileInputStream(apiKeysFile), "UTF-8")) {
            props.load(reader);
            System.out.println("📖 Clés API chargées depuis api-keys.properties");
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName())
                .log(Level.SEVERE, null, ex);
        }
    }
    
    return props;
}
```

**2. `saveApiKeys()` - Sauvegarde**
```java
private void saveApiKeys(String locationIQKey, 
                        String huggingFaceKey, 
                        String openRouterKey) {
    File apiKeysFile = new File("api-keys.properties");
    Properties props = new Properties();
    
    // Charger existant pour préserver autres clés
    if (apiKeysFile.exists()) {
        try (InputStreamReader reader = new InputStreamReader(
            new FileInputStream(apiKeysFile), "UTF-8")) {
            props.load(reader);
        } catch (IOException ex) {
            Logger.getLogger(ConfigDialogController.class.getName())
                .log(Level.SEVERE, null, ex);
        }
    }
    
    // Mettre à jour
    props.setProperty("locationiq.api.key", locationIQKey);
    props.setProperty("huggingface.api.key", huggingFaceKey);
    props.setProperty("openrouter.api.key", openRouterKey);
    
    // Sauvegarder
    try (OutputStreamWriter writer = new OutputStreamWriter(
        new FileOutputStream(apiKeysFile), "UTF-8")) {
        props.store(writer, "Clés API - Modifié depuis les préférences");
        System.out.println("✅ Clés API sauvegardées dans api-keys.properties");
    } catch (IOException ex) {
        Logger.getLogger(ConfigDialogController.class.getName())
            .log(Level.SEVERE, null, ex);
    }
}
```

#### Bouton Sauvegarder modifié

**Avant** :
```java
contenuFichier += "bingAPIKey=" + tfBingAPIKey.getText() + "\n";
contenuFichier += "style=" + tgStyle.getSelectedToggle().getUserData().toString() + "\n";
// ... mise à jour Bing API partout
EditeurPanovisu.setStrBingAPIKey(tfBingAPIKey.getText());
EditeurPanovisu.navigateurOpenLayers.setBingApiKey(...);
```

**Après** :
```java
// Sauvegarder panovisu.cfg (sans bingAPIKey ni style)
String contenuFichier = "langue=" + ... + "\n";
contenuFichier += "pays=" + ... + "\n";
contenuFichier += "repert=" + tfRepert.getText() + "\n";

// Sauvegarder api-keys.properties
saveApiKeys(
    tfLocationIQKey.getText(), 
    tfHuggingFaceKey.getText(), 
    tfOpenRouterKey.getText()
);
```

---

## 📋 Tests effectués

### Build 2269
✅ **Compilation réussie**  
✅ **Application lancée**  
✅ **Nouveau layout affiché correctement**  

### Tests à effectuer par l'utilisateur

1. **Ouvrir les préférences** :
   - Menu Fichier → Configuration
   - Vérifier que les 3 champs API sont visibles

2. **Vérifier le chargement** :
   - Les clés depuis `api-keys.properties` doivent être pré-remplies

3. **Modifier et sauvegarder** :
   - Modifier une clé API
   - Cliquer "Sauvegarder"
   - Vérifier que `api-keys.properties` est mis à jour

4. **Vérifier panovisu.cfg** :
   - Confirmer absence de `bingAPIKey=` et `style=`

5. **Test persistance** :
   - Relancer l'application
   - Rouvrir préférences
   - Vérifier que les clés sont bien rechargées

---

## 🔍 Console logs attendus

### Au chargement des préférences
```
📖 Clés API chargées depuis api-keys.properties
```

### À la sauvegarde
```
✅ Clés API sauvegardées dans api-keys.properties
```

### Si fichier manquant
```
⚠️ Fichier api-keys.properties non trouvé
```

---

## 📝 Notes importantes

### Sécurité
- ⚠️ `api-keys.properties` doit rester dans `.gitignore`
- Les clés sont stockées en clair (OK pour usage local)
- Partager le fichier template avec clés vides

### Migration automatique
- Pas de migration automatique des anciennes clés
- L'utilisateur doit ressaisir ses clés API une fois

### Compatibilité
- Anciens fichiers `panovisu.cfg` avec `bingAPIKey=` et `style=` : ignorés (pas d'erreur)
- Nouvelles installations : `api-keys.properties` créé à la première sauvegarde

---

## 🎯 Prochaines améliorations possibles

1. **Validation des clés** :
   - Vérifier format (pk.*, hf_*, sk-or-v1-*)
   - Test de connexion API au clic d'un bouton

2. **Boutons "Obtenir une clé"** :
   - Liens directs vers LocationIQ, Hugging Face, OpenRouter

3. **Indicateur de validité** :
   - Icône verte/rouge selon la validité de la clé

4. **Masquage optionnel** :
   - Afficher les clés en `***` avec bouton "Afficher"

5. **Import/Export** :
   - Bouton pour importer un fichier de clés
   - Exporter (sans clés sensibles) pour partage

---

**Date** : 13 octobre 2025  
**Build** : 2269  
**Fichiers modifiés** :
- `ConfigDialogController.java`
- `panovisu.cfg` (format simplifié)

**Fichiers à créer** :
- `api-keys.properties` (template sans clés)
