# Général
- Il te faut un client pour éviter du code "inapproprié" dans le code du serveur et pour "faciliter" tes vérifications
- Configure PMD/Checkstyle, ils vont t'aider à avoir un code "propre"
- Configure aussi ton IDE qui va t'aider (les "save actions" pour organiser les import et formater ton code)
- Ton API (URL exposées) est un peu "complexe" et manque de cohérence (majuscule, ordre, ...). Tu peux créer des paths Complexes (/google/mail/unread/nb?userkey=xxx et /microsoft/mail/unread/nb?userkey=xxx par exemple)
- Le dossier "WEB-INF" n'est pas utile avec notre utilisation de Spring Boot, il va en générer un pour nous avec toutes les "bonnes" configuration en fonction des modules Spring qu'on utilise dans le code
- Vérifie ton application avant de livrer, elle ne démarre pas à cause d'une erreur de "mapping" de l'ajout de compte Microsoft
- Ta doc technique est un peu "light".

# Remarques


