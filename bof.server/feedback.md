# Général
- Je ne vois pas la conf CheckStyle et PMD, l'avais-tu d’activé dans ton IDE ? (PMD dans Maven est configuré sur des règles "par défaut", sans génération de rapport et le rapport Checkstyle n'est pas activé)
- Configure ton IDE pour le formatage du code, l’organisation des imports pour t'aider à avoir un code "lisible"
- Ton client n'a pas été adapté pour la version Multi-user. Ni pour la version Microsoft
- Ou sont les routes **d'API** pour consulter les données (notamment via ton client en ligne de commande) ?
- ZéroConf ? Configuration externalisable ? Ne pas mettre de mot de passe sur gitHub ? 
- Javadoc ? 
- Tes controllers font un petit peu de traitement métier ce n'est pas TOP. Surtout si tu doit créer un "deuxième" controller pour les APIs (code dupliqué avec risque d'erreur lors de mise à jour)


La structure "global" de ton code est bonne, mais attention au "codage" et vérifie que cela continue a fonctionner au fur et à mesure des évolutions.

# Remarques
- Ne mélange pas les getter/setter et les attributs
