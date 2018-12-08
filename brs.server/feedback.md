# Général
- Pense à modifier les identifiants de ton application Microsoft et Google car les mot de passe sont **publics** ! 
- Dans la documentation tu aurais dut expliquer comment un "administrateur" peu configurer DaP (ou déposer les fichiers). Tu aurais pu constater qu'il te manquait des éléments de configurations (Microsoft, choisir les dossiers)
- Il FAUT configurer ton IDE pour qu'il fasse une partie du boulot à ta place. A minima écoute ses conseilles.
- Il faut que tu configure les outils d'audit de code pour t'aider à conserver/obtenir du code "propre"
- Ton client ne fonctionne vraiment pas bien

# Remarques
- Tu as une configuration et/ou une version spécial de MySQL, et tu ne précise pas dans la configuration comment le configurer car j'ai une exception au démarrage du serveur `Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Row size too large. The maximum row size for the used table type, not counting BLOBs, is 65535. This includes storage overhead, check the manual. You have to change some columns to TEXT or BLOBs`
- Ton client n'est pas un jar "auto-executable"
- Impossible d'ajouter des comptes via ton client (PB de session)
- la consultation du "new event" (via le naviguateur) provoque l'erreur `org.json.JSONException: JSONObject["start"] not found.`
- Pourquoi il y a-t-il des jars dans le dossier "checkstyle" ? 