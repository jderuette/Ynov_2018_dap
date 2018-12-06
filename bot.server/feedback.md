# Général
- Certains anciens TODO non corrigé ! 
- Tu m'as livré un code qui ne compile même pas !!!!
- J'ai corrigé pour que ca compile mais à cause du mapping de "/email" deux fois dans 2 controllers le serveur ne démarre pas ...
- La javadoc "pré-générée" DOIT être complétée/mis à jour
- Ou est la doc ?

# Remarque
- Je n'ai pas l'impression que tu avais PMD/checkstyle d'activer dans ton IDE, et ils n'étaient pas configurés dans Maven. Dommage ils sont d'une bonne aide pour améliorer la qualité du code.
- Pense à vérifier ton code avant de livrer
- Principe ZeroCof à revoir, tu as certes externalisé, mais rendu cette conf obligatoire, toutes tes "valeurs par défaut" ne peuvent pas être utilisées ! 
- Attention à tes Log
- Attention à tes commentaires (javadoc)
- il manque l’internationalisation dans les "pages thymleaf"
- Attention au nommage de tes variables, et à des algo "dangereux" (notamment les multiples return dans une même méthode)