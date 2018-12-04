# général
- Attention ton client ne compilait pas, comme c'est le seul moyen que j'ai de le tester j'ai corrigé (nommage des packages).
- Ton client ne fonctionne plsu avec ton "nouveau" serveur
- Plusieurs de tes pages "Web" sont difficiles à tester à cause des tokens qui DOIVENT être en session (et qu'on ne peut créer qu'en ajoutant un compte)
- Attention ta doc n'est pas à tout a fait à jour (ton fichier "Google credential" s'appel maintenant "web_credentials.json" et plus "credentials.json". Le dossier "root" est dans "C:\Users\**<current_user>**\dap\" et pas "C:\Users\dap\" (heureusement cela obligerait à avoir un "user" "dap" !)
- Principe ZeroConf "annulé" par la conf externe qui NÉCESSITE un fichier de configuration. Externalisation "partiel" : la majorité de la conf se retrouve dans 2 fichiers, mais on ne peut pas "changer" l’emplacement de ces fichiers, et sans ces fichiers impossibles de modifier la conf

- Correcte, dommage que tu n'ai pas tout a fait fini propre (client + Documentation + Config Externalisée et CONFIGURABLE, session dans Web)

# Remarques
- Quelques logs manquantes.
- Quelques Javadoc un peu approximatives
- Pourquoi as-tu implémente "CallBack" de Javax-security ? C'est une interface de marquage elle n'est utile que si tu implémente d'autres interfaces de ce même package
- Quelques petites méthode utilitaire aurait aidé, ou quelques petites modifications de paramètres.
- Attention tes "URL" sont parfois incohérente entre Google et Microsoft