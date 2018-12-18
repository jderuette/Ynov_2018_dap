# Général
- Prend en compte les remarques de tes outils d'audit de code.
- Pour diminuer ton travail, pour les log "Enter/exit" de méthode tu peux utiliser un paradigme de programmation Aspect Oriented Programation (AOP) qui est supporté par Spring Framework. L'idée globale : créer des "Aspect" (des comportements génériques) et les appliquer à des "points de jonctions" ("toutes les méthodes classe du package 'service'", "toutes les méthodes ayant l’annotation 'xxx'", "toutes les méthodes ayant la le nom commençant par 'get'", ...). La doc de Spring framework sur le sujet : https://docs.spring.io/spring/docs/2.5.x/reference/aop.html. D'autres cas ou les AOP sont souvent bien pratique : 
  - Chronométrer le temps d’exécution
  - Gérer des transactions (éventuellement distribuées)
  - Gérer des accès spécifiques (pas plus de X appels pour l’utilisateur XXXX)


# Remarques
- Ta partie "Microsoft" ne fonctionne pas, principalement car le token ne peut pas être sauvegardé en BDD. Tu verra plus facilement ce genre de problèmes si tu laisse la pile d'exception dans tes logs d'erreur.