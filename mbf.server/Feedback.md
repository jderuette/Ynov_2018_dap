# Général
- Utilise les outils d'audit de code. Bien que ton code soit assez propre, ils t'aident à voir les "derniers" petits oublies/erreurs
- Ton client n'est pas à jour
- La partie Microsoft n'est pas finie (dommage une bonne partie du boulot de fait)

# Remarques
- Dans les logs évite d'ajouter le message de l'exception à ton propre message. Il est plus "claire" d'ajouter l'exception en deuxième paramètre de ta log, ainsi il y a aura ton message, le message de l’exception et la pile de l’exception (avec les causes imbriquée si présentes)