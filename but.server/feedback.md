# Général
- Où est la doc qui explique comment utiliser la "configuration externalisée" ? (pour pouvoir testé j'ai commenté une partie de la conf "par défaut").
- showNextEvent avec un compte Microsoft (qui contient le prochain Rdv) provoque une erreur : `Exception in thread "main" org.json.JSONException: JSONObject["status"] not a string.
        at org.json.JSONObject.getString(JSONObject.java:855)
        at fr.ynov.dap.client.DapAPI.showNextEvent(DapAPI.java:156)
        at fr.ynov.dap.client.App.showNextEvent(App.java:180)
        ........`
- En consultant le "next Event" directement via l'URL, c'est le "dernier" Microsoft qui s'affiche au lieu du prochain

- Code bien structuré et tu as pris le temps d'utiliser des API un peu plus avancé (gestion des paramètre dans en ligne de commande, DataStore Google, gestion des message d'erreur)

# Remarque
- Malgrès tes effort lorsqu'il y a une Exception, le JSON n'est pas "valide" s'il y à un emplacement de fichier "Windows" dans le message.
- Comme tu as géré les "Account" par "AppUser" il faut que tu adaptes ton API, et tes messages dans les logs
- Sauf nécessite met tes "Logger" en static ET final
