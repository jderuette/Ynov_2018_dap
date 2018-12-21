# Général
- Certains des tes package avaient des majuscule (dans la "structure"). Attention git est assez mauvais sur le renommage de fichier, et encore plus mauvais quand juste la case change. Lorsque tu fait du renommage, commit le renommage PUIS fait des modifications (puis commit ensuite lorsque tu en as besoin). Pour t'aider Eclipse, lorsque tu renomme des fichier, les met automatiquement dans l'index (ils "doivent" passer au prochain commit). Je t'ai fait les modifications.
- Active les outils d'audit de code pour t'aider à éviter de petites erreurs "bêtes" (ne pas préciser les modifier,...)
- Configure les "save actions" de ton IDE pour qu'il organise les import (et formate ton code) lorsque tu sauvegarde

- Ta documentation n'est plus à jour : "add" attend 3 paramètres, l'ajoute de compte Microsoft n'est pas possible, l'ajout de compte Google n'est plus fonctionnel

# Remarques
- Attention à la qualité de ton code (utilisation des APIs, ordre des tests de "nullité", nom des méthodes, ...)
- Tu as assez bien séparé controller/Service mais ne laisse pas des paramètre "web" dans tes service (Request, session) et ne demande pas à tes service de renvoyer des "vues" ou des "messages utilisateur"