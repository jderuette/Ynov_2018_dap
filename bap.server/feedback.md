# Général
- Tu aurais pu repasser sur ton "ancien" code Google.
- Attention à tes algos.

# Remarque
- Évite les "multiples return" dans les méthodes, ça fait souvent des algo pas très claire (et dangereux). Si tu as un "else", pas la peine de faire un "return" dans le if, initialise juste ta valeur de retour. Si tu n'as pas de "else" c'est dangereux. Le cas ou c'est "toléré" c'est le "early break", tu contrôles tes paramètres et s'il sont pas bons, tu arrêtes, le mieux est en général une exception.
- Pour la partie "configuration" (et ZeroConf) ça ne fonctionne pas bien, et tu ne l'as pas mis en œuvre pour Microsoft !
- Ton IDE t'indique régulièrement des points de "vigilance" mais tu les ignores ... C'est dommage.