# Test Wingdings dans WebView

## Test 1 : Entités HTML numériques standard ✅

Ces symboles DOIVENT fonctionner partout :

- ✅ Checkmark : &#10004;
- ❌ Croix : &#10006;
- ⚠️ Attention : &#9888;
- ℹ Info : &#8505;
- ☎ Téléphone : &#9742;
- ⚙ Engrenage : &#9881;
- ★ Étoile : &#9733;
- ● Bullet : &#9679;
- ▶ Play : &#9654;

## Test 2 : Emojis qui plantent (actuellement)

Ces emojis affichent ������ :

- 📚 Livre
- 📱 Mobile
- 🏛️ Monument
- 🛒 Panier
- 📸 Caméra

## Test 3 : Wingdings avec classe `.wi`

Si Wingdings fonctionne, vous verrez des symboles lisibles ci-dessous :

**Documents** : <span class='wi'>&#128;</span> (devrait être un document/livre)
**Dossier** : <span class='wi'>&#48;</span> (devrait être un dossier)
**Ordinateur** : <span class='wi'>&#112;</span> (devrait être un ordinateur)
**Outils** : <span class='wi'>&#119;</span> (devrait être un marteau/outil)
**Globe** : <span class='wi'>&#128;</span> (devrait être un globe terrestre)

## Test 4 : Badges complets (comme dans la doc)

Voici comment ils s'afficheront dans aide.md :

- <span class='badge badge-doc'><span class='wi'>&#128;</span> DOC</span> Documentation
- <span class='badge badge-check'>&#10004; OK</span> Validation
- <span class='badge badge-cross'>&#10006; ERREUR</span> Erreur
- <span class='badge badge-warning'>&#9888; ATTENTION</span> Avertissement
- <span class='badge badge-robot'>&#9881; IA</span> Intelligence artificielle

---

## 🎯 Résultat attendu

✅ **Tests 1 et 4** : Badges colorés avec symboles lisibles et texte  
⚠️ **Test 2** : Emojis affichent toujours ������ (c'est normal, on va les remplacer)  
❓ **Test 3** : Si vous voyez des symboles Wingdings reconnaissables → ✅ Wingdings fonctionne !

Si Test 3 ne fonctionne PAS (symboles corrompus), on utilisera UNIQUEMENT des entités HTML numériques standard (Test 1).
