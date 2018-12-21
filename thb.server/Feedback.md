# Général
- Ton client n'est pas à jour (création d'utilisateur, ajout de compte Microsoft)

- Tu as systématiquement renvoyé du JSON, ce qui est attendu sur un API Rest. Cependant si tu renvois des instances d'objet, Spring les convertie automatiquement pour toi en JSON. Le faire toi même peut te donner un peu plus de contrôle mais te demande un peu plus de code "pas très intelligent". De plus si tu laisse faire Spring, il peut convertir des instance en XML, si le client demande ("accept") du XML.
- Ton client n'est pas auto-éxécutable.
- installe et configure les outils d'audit de code (PMD, checkstyle), il t'aident à éviter des erreurs "bêtes".


# Remarques
- Attention à préciser la porté des tes attributs, sinon il ont la porté de la classe (en général public)