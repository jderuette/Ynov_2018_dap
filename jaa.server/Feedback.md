# Général
- Attention à l'ordre de tes constantes/attributs/constructeurs, .... dans une classe
- Pour diminuer ton travail, pour les log "Enter/exit" de méthode tu peux utiliser un paradigme de programmation Aspect Oriented Programation (AOP) qui est supporté par Spring Framework. L'idée globale : créer des "Aspect" (des comportements génériques) et les appliquer à des "points de jonctions" ("toutes les méthodes classe du package 'service'", "toutes les méthodes ayant l’annotation 'xxx'", "toutes les méthodes ayant la le nom commençant par 'get'", ...). La doc de Spring framework sur le sujet : https://docs.spring.io/spring/docs/2.5.x/reference/aop.html. D'autres cas ou les AOP sont souvent bien pratique : 
  - Chronométrer le temps d’exécution
  - Gérer des transactions (éventuellement distribuées)
  - Gérer des accès spécifiques (pas plus de X appels pour l’utilisateur XXXX)

- Attention lorsque tu "catch" une exception (pour ajouter une log) tu étouffe l’exception, parfois c'est la bonne action. Tu peux aussi en lever une plus "propre" à la place.
- Tu n'a pas externalisé ta configuration

# Remarques
- Soit vigilant dans la contextualisation de tes messages de logs. Les logs vont se "mélanger" dans une utilisation multi-utilisateur, et une information portée par une log précédente est rarement utile.
- Tu peux utiliser les "DTO" de Microsoft pour les utiliser comme des entités (en ajoutant l'annotation @Entity). Tu peux ensuite les "intégrer" dans une entite existantes avec un "OneToOne". JPA créera une table à partir de la classe et y sauvegardera les données de l'instance.