# Général
- Où es le code de ton client ? 
- Comment configurer l'application de l’extérieur ? 
- Il FALLAIT externaliser le "StoredCredcential"
- Après tout ces effort pour externaliser les "mot de passe Gogole" pourquoi ajouter dans gitHub ceux de Microsoft ? 
- Ou est la doc technique ?
- Ton fichier Maven est "tout pourris". Dépendance sans aucun ordre, "mainClass" faux, pas de "spring-boot-build". Du coup pas de jar auto-éxécutable. Et un "Run as dans Eclipse" ne fonctionne pas mieux.... Comment as-tu vérifié ton code ? 
- Comment as-tu vérifier tes View (thymeleaf) elle aucune ne fonctionne.
- L'ajout d'utilisateur ne fonctionne pas/plus du coup difficile de tester ... 
- Soit vigilants aux détails, ton code ressemble à un "patchwork" de chose qui "pourrais" fonctionner, ou "fonctionne presque"


# Remarques
- Il est nécessaire de contextualiser les logs. Souvent il suffit d'y ajouter, une partie, des paramètre de la méthode.
- Attention à la structure de ton code, parfois des bonnes idées mais pas "complétement" réaliser.
- Supprime ton code "inutile" (tu peux le garder un moment si tu as un "doute" mais avant une "livraison" supprime le code "commenté"
- Vérifie et re vérifie notamment ta configuration Maven qui ne fonctionne pas du tout ! 


======== Ton code =====
Je t'ai fait quelques corrections "minimal" (par rapport à certains TODO) pour tester ton code, mais il reste encore du boulot pour vérifier que cela fonctionne.