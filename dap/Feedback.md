# Général
- Ton client ne permet pas de créer de compte utilisateur (erreur dans l'URL)
- Les quelques éléments de configuration "extérieur" ne sont pas top, il manque notamment de "rootDirectory" !
- Ta documentation ne précise RIEN sur la configuration de l'application Microsoft (à minima les "autorisations" nécessaires).
- Dans ta doc fourni un fichier "template" pour le "auth.properties" plutôt que tien avec tes données **confidentiels**.
- Le comptage des contacts Microsoft ne fonctionne pas bien

# Remarques

- Attention à la **javadoc** et au **nommage** des méthodes/paramètres/attributs, cela peu provoquer des erreurs, et y réfléchir évite des ambiguïtés (et des futurs bugs")
- Penses à tester ton code (y compris le client) certains "bugs" sont difficile à voir dans le code, mais "facile" à voir à l’exécution.
- Soit précis (et juste) dans tes messages de logs sinon le jour ou on en a besoin, on ne peu pas bien les utiliser.
- Attention au code dupliqué, et à quelques paramètres dont le type n'est pas "optimal"
