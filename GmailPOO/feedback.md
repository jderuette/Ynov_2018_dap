# général
- Tu ne m'as pas livré de client
- Tu n'as pris pris en compte certains TODO de la version précédentes !!!
- Formate ton code, supprime les import inutiles (fait le faire par Eclipse via les "Save action" cf "memo Elcipse") et supprime ton code commenté qui ne te sert plus.
- Ta documentation ne m'indique pas ou/comment déposer les fichiers de configuration
- Ta configuration n'est pas modifiable de l'extérieur
- La "conception" global est, en général, acceptable mais il y a pleins de "détails" qui viennent la gangrener
- Tu m'a fourni une conception (à priori vue que j'ai vue un dictionnaire des données) de ta BDD, tu peux aussi le faire pour tes classes à l'aide de diagramme UML. Les diagrammes UML (de classe principalement) t’aideront à définir la vision "global" de ton appli et éviter que tu "dérape" en codant.


# Remarques
- Le package dao est vide, supprime-le.
- Dans un projet "Spring boot" le "Laucher" devrait être dans un package "parent" de toutes les classes (ton **O**utlok.xxxx n'est pas dans "dap"
- La majorité de tes route en fonctionne pas/plus.
- Tu dois impérativement "nettoyer" ton code au fur et à mesure ! Les "warning" d'éclipse sont des bons conseilles dans 99% des cas ! 
- Supprime les TODO une fois que tu les as traités.
- Vérifie que ton code fonctionne avant une livraison (et vérifie le fréquemment au fur et à mesure de ton développement)

**Note** : j'ai arrêté l'audit de ton code, il faut que tu fasses une première "passe" pour nettoyer sinon je me répète sans ajouter d'aide utile dans mes remarques.