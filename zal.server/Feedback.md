# Général
- Traite les remarques de ton outils d'audit de code ! 
- Attention aux "return null" dans les boucles ! D'une façon général évite les multiples return dans une même méthode (se sont presque toujours de "fausse bonnes idées"). En faisant cela tu "casse" l’exécution de ta méthode "presque" autant qu'en levant une exception, mais "sans informations pour l'appelant", la particularité est que tu renvois une valeur, mais cette valeur est souvent "bidons".

# Remarques
- Tu n'as pas bien vérifié ton client "sans paramètre" ou avec "help" comme paramètre
- Attention à la gestion des exception, quand tu "catch" tu "étouffes" l'exception et il faut prévoir les impacts. Il est régulièrement plus correct de laisser "remonter" l'exception. Lorsque tu catch, soit : 1- tu **gères** l'exception, 2- tu fait une exception plus "général", 3- tu traces l'erreur et tu renvois l'exception catché (pas trop recommandé mais parfois adapté)
