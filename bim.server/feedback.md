# général
- Ou est la doc technique ? J'aurais bien aimé voir comment tu documente la "configuration externe" (notamment le "/" de fin de "root" sous windows )
- Globalement très corrects, attentions aux détails

# Remarques
- Tu as des logs "au bons endroits" mais pense bien au contexte. Dans le cas d'une application PROD avec de dizaines/milliers d'utilisateurs la plupart de tes logs serait "peu utiles".
- Quelques algorithmes parfois un peu compliqué "pour rien".
- Tu pourrais mieux utiliser JPA parfois tu t’embête alors que JPA fait déjà le boulot pour toi !