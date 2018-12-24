# Général
- Ton client ne compile pas ! (une petite erreur)
- Tu n'as pas adapté ton client avec la version Microsoft
- Utilise les outils d'Audit de code, cela évite des "ereurs betes"

# Remarques
- Attention à bien préciser la porté de tes attributs, par defaut ils ont la porté de la classe (en général "public")
- Attention en surchargent les version de "jackson" dans ton ficheir pom.xml cela rendait le serveur non opérationnele pour moi. Sauf besoin particulier, laisse les versions proposées par Spring-Boot (c'est son principal boulot de mettre en "cohérence" tous les modules !)