# Général
- Ton client ne permet pas de gérer les utilisateurs ni les comtpes (Microsoft et Google)
- Respecte les remarques de ton IDE et des outils d'audit de code, elles sont juste à 99.9%
- Attention dans ton code tu mélange des log en utilisant Log4J est d'autre en utilisant CommonLogging. Les deux sont parfaitement valides, mais il vaut mieux en choisir un et s'y tenir sur tous le projet (je vous ai encourgé à utiliser Log4J, car il y a quelques "méthdoe" utilitaire bien pratiques, mais c'est "assez arbitraire")
- Une grande partie de ton code des "cotroller" pour Microsoft est un copier/coller de tes services et ne semble plus très utile
- Complète réllement la Javadoc au lieu de simplement contourner l'outils qui essaye de t'aider.

-Ton client recoit du "HMTL" en réponse ce qui n'est aboslument pas pratique pour une API. Idevrait recevoir du texte (soit "brute, soit du JSON, soit du XML) pour pouvoir en extraire les données

# Remarques
