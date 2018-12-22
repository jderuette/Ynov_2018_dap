# Général
- Ton client a pas mal de bug (création compte Google, impossible d'avoir des résultats si on a pas un compte Google ET un compte Microsoft)
- Ta la Config tu as fait plusieurs confusion (les problèmes ne viennent PAS du PC de ton travail, mais de ton code)
1- Tu as mélanger "clientSecretFile" (le fichier JSON qui identifie ton application) et clientDir (dossier qui va contenir les autorisations accordées par tes utilisateurs)
2- Tu as créer des objets complexes (InputStream), mais sans prendre en compte dans le code du GoogleServie (laisse des String dans la conf, c'est plus facilement modifiable de l’extérieur)
- Beaucoup de "petites" remarques que tu pourrais corriger par toi même en configurant CheckStyle.

- Tu n'as pas finie d'intégrer Microsoft

# Remarques