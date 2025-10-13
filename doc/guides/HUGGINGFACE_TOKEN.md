# Configuration du Token Hugging Face

## Pourquoi un token ?

Depuis 2024, l'API Inference de Hugging Face nécessite une authentification pour utiliser les modèles d'IA gratuitement. C'est totalement **gratuit** et prend 2 minutes.

## Comment obtenir un token (gratuit)

### 1. Créer un compte Hugging Face (gratuit)

Allez sur : **https://huggingface.co/join**

- Créez un compte avec votre email
- Confirmez votre adresse email

### 2. Générer un token d'accès

Allez sur : **https://huggingface.co/settings/tokens**

1. Cliquez sur **"New token"**
2. Donnez-lui un nom (exemple : "EditeurPanovisu")
3. Sélectionnez **"Read"** comme permission (suffisant)
4. Cliquez sur **"Generate token"**
5. **Copiez le token** (il commence par `hf_...`)

⚠️ **Important** : Gardez ce token secret, ne le partagez jamais publiquement !

### 3. Configurer le token dans l'application

#### Option A : Via Maven (développement)

```bash
mvn javafx:run -Dhuggingface.token=hf_VotreTOKENici
```

#### Option B : Via ligne de commande Java

```bash
java -Dhuggingface.token=hf_VotreTOKENici -jar editeurPanovisu.jar
```

#### Option C : Variable d'environnement Windows

**PowerShell :**
```powershell
$env:JAVA_TOOL_OPTIONS="-Dhuggingface.token=hf_VotreTOKENici"
mvn javafx:run
```

**CMD :**
```cmd
set JAVA_TOOL_OPTIONS=-Dhuggingface.token=hf_VotreTOKENici
mvn javafx:run
```

#### Option D : Fichier de configuration Maven

Créez ou modifiez `~/.mavenrc` (Linux/Mac) ou `%USERPROFILE%\.mavenrc` (Windows) :

```
MAVEN_OPTS="-Dhuggingface.token=hf_VotreTOKENici"
```

#### Option E : Directement dans le code (NON RECOMMANDÉ pour git)

⚠️ **Ne faites ceci que pour un usage personnel sans commit git !**

Dans `OllamaService.java`, ligne ~32 :
```java
private static final String HUGGINGFACE_TOKEN = "hf_VotreTOKENici";
```

**Attention** : Ne commitez JAMAIS ce fichier avec le token !

## Alternative : Utiliser Ollama (local, sans token)

Si vous préférez ne pas utiliser Hugging Face, vous pouvez installer **Ollama** :

### 1. Télécharger Ollama

Allez sur : **https://ollama.ai/download**

### 2. Installer et configurer

```bash
# Installer le modèle Mistral (gratuit, ~4 GB)
ollama pull mistral

# Lancer Ollama
ollama serve
```

### 3. Avantages d'Ollama

- ✅ **100% local** : fonctionne sans Internet
- ✅ **Aucun token requis**
- ✅ **Données privées** : rien n'est envoyé en ligne
- ✅ **Rapide** : pas de latence réseau
- ❌ Requiert ~4-8 GB de RAM
- ❌ Requiert ~4 GB d'espace disque

## Comparaison des services

| Service | Token requis | Internet | Vitesse | Gratuit | Privé |
|---------|--------------|----------|---------|---------|-------|
| **Ollama** | ❌ Non | ❌ Non | ⚡ Rapide | ✅ Oui | ✅ 100% |
| **Hugging Face** | ✅ Oui | ✅ Oui | 🐌 Moyen | ✅ Oui | ⚠️ Envoyé en ligne |

## Dépannage

### "Service IA non disponible"

**Cause** : Ni Ollama ni Hugging Face ne sont disponibles.

**Solutions** :
1. Vérifiez que vous avez configuré le token Hugging Face (voir ci-dessus)
2. OU installez Ollama (voir ci-dessus)

### "HTTP 401: Invalid username or password"

**Cause** : Token Hugging Face manquant ou invalide.

**Solutions** :
1. Vérifiez que vous avez copié le token complet (commence par `hf_`)
2. Vérifiez qu'il n'y a pas d'espaces avant/après le token
3. Générez un nouveau token sur https://huggingface.co/settings/tokens

### "HTTP 503: Model is loading"

**Cause** : Le modèle Hugging Face est en cours de chargement (normal la première fois).

**Solution** : Attendez 10-20 secondes et réessayez. L'application réessaie automatiquement.

### Ollama ne démarre pas

**Cause** : Port 11434 déjà utilisé ou service non lancé.

**Solutions** :
1. Vérifiez qu'Ollama est installé : `ollama --version`
2. Lancez le service : `ollama serve`
3. Vérifiez qu'il écoute : http://localhost:11434

## Support

Pour plus d'informations :
- Hugging Face : https://huggingface.co/docs/api-inference
- Ollama : https://github.com/ollama/ollama
- Documentation complète : voir `DESCRIPTION_IA.md`
