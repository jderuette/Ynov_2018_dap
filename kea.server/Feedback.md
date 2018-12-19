# Général
- Tu ne m'as pas livré de client
- Tu ajoute souvent des getter (parfois des setters) public qui sont inutile. Évite d'exposer ton code (et tes attributs) si ca n'est pas nécessaire.
- Tu as partagé les logger de tes services a tes controllers. Ça n'est pas une bonne idée. Les messages de chaque logger peuvent être configurés SI ils sont dans des catégorie différentes. Dans ton code il ne sera pas possible d'appliquer des "filtres" différents sur les messages provenant d'un service "X" et ceux provenant du controller qui manipule le service "X"
- Dans eclipse pourquoi as-tu un projet "web". Avec spring Boot nous construisons un projet "java classique" et spring Boot s'occupe de faire la partie "web" nécessaire

# Remarques
- Ta configuration est externalisé, mais il ça aurait été pratique de pouvoir modifier les emplacements des fichier (au moins le dossier "racine").
- Tu n'as pas fini d'implémenter Microsoft (appels des services lors du comptages de contact/emails,...)