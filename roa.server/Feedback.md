# Général
- Ni ton client, ni ton serveur ne se lance pas en "auto" (Classe "main" mal configuré dans le pom.xml)
- Il te manque la "peopole API" (conpter le nombre de contact)
- Tu n'as pas fini d'intégré Microsoft (cumulé avec les données "Google" dans l'API exposée)
- Tu n'as pas externalisé tes configurations

# Remarques
- Ton client fonctionne plus très bien.
- Si je veux avoir une chance que l'API fonctionne il FAUT que j'utilise comme "nom de compte Google" mon adresse email. Il faut un "userKey" pour grouper tout les compte (ceci est indépendant de l'API Google), un "accountName" qu isert de clef pour suavegarder/relire les tokens, et un "googleUserId" qui permet de savoir "sur qui" les données vont être extraites (sous réserve que le token associé au "accountName" ai accès à ces données). Le "sur qui" est en général "me" (si c'est en "dur" cela ne permet pas la délagation) Ce qui est une bonne valeur par defaut.