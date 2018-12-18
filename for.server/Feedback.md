# Général
- Suit les recommandation de ton IDE, elles sont juste à 99.99%
- Configure et suis les conseilles des outils d'audit de code
- Ton client ne fonctionne pas (ajout d'utilisateurs, ajout de comptes au moins)
- Ta doc technique est TRÈS superficiel (et en partie fausse !)


# Remarques
- Spring lorsque tu as besoin de la config, utilise l'injection, plutôt que faire référence à un attributs static de ta classe main. TU créer une dépendance forte entre ta classe Main, et tes services/controllers !
- Tes service "Microsoft" dépendent entièrement de la sessions, tu ne peux donc plus avoir une API "stateLess". Pour avoir une api "stateFull" fonctionnelle il faut que le "client" puisse se connecter. En général on ne fait pas d'API stateFull (pleins d'inconvénient et très peu d'avantages). Pour que tes services ne dépendent plus de la session, stocke en BDD, et utilise les données de la BDD. Tu peux aussi rappeler systématique le WS Microsoft lorsque tu en as besoin (cela est valable pour obtenir l'email de l'utilisateur, pour t'éviter de créer une table "user")
- Pense à ajouter des logs, ça pourrais t'aider à voir ce qui ne fonctionne pas