
# DAP

/user/add/{userKey} <-- Permet d'ajouter un compte Principal (qui possédera des comptes Google & Microsoft)

/admin
 - / <-- Affiche microsoft + google combiné 
 - /microsoft <-- Affiche l'ensemble des comptes microsoft 
 - /google <-- Affichage du datastore

/calendar
- /{user} <-- Affiche le prochain événement entre google et microsoft
- /microsoft/{user}
- /google/{user}
	
/mail
- /unread
-- /{user} <-- Affiche le nombre total de unread google et microsoft
-- /microsoft/{user}
- /google/{user}
		
- /microsoft/{user} <-- Affiche les premiers mail de microsoft
	
/contact/
- /nb
-- /{user} <-- Affiche le nombre total de contact google et microsoft
-- /microsoft/{user}
-- /google/{user}
		
- /microsoft/{user} <-- Affiche les premiers contact de microsoft
	
/account/add/
- /microsoft/{user}?userKey={userKey} <-- Permet d'ajouter un compte Microsoft au userKey
- /google/{user}?userKey={userKey} <-- Permet d'ajouter un compte Google au userKey

# Documentation technique
**BARON Paul**
**Livraison du TP JAVA**

## **CLIENT :**
Utilisation du JAR avec 2 paramètres ; Listes des paramètres

    java -jar .\baron_paul_client.jar add [google|microsoft] <user> <userKey>

Permet d’ajouter un compte Google / Microsoft au **userKey**

    java -jar .\baron_paul_client.jar view <userKey>

Retourne le résultat :
 - Nombre des contacts Google + Microsoft
 - Nombre de mails non lus Google + Microsoft
 - Le prochain événement dans le calendrier Google + Microsoft

    java -jar .\baron_paul_client.jar email <user>

Retourne le nombre de mails non lus Google + Microsoft

    java -jar .\baron_paul_client.jar contact <user>

Retourne le nombre de contacts Google + Microsoft

    java -jar .\baron_paul_client.jar calendar <user>

Retourne le prochain événement dans le calendrier Google + Microsoft

## **SERVER :**

Lancer le server en tapant :

    java -jar .\baron_paul_server.jar

**/ ! \\** Si l’erreur :

    Exception in thread "main" java.lang.NoClassDefFoundError: org/springframework/boot/SpringApplication at com.ynov.dap.Launcher.main(Launcher.java:19)
    Caused by: java.lang.ClassNotFoundException: org.springframework.boot.SpringApplication
	    at java.net.URLClassLoader.findClass(Unknown Source)
	    at java.lang.ClassLoader.loadClass(Unknown Source)
	    at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
	    at java.lang.ClassLoader.loadClass(Unknown Source)
    ... 1 more

**/ ! \\** est présente, alors il faut mettre le dossier **LIB** à la racine du **.JAR**

## **Informations concernant la Base de Données :**

Pour configurer les paramètres de connexion à la base de données, il suffit de se rendre dans le fichier ‘**application.properties**’ localisé dans Ynov_2018_dap\bap.server\src\main\resources

Ce fichier contient les informations de connexion à la base de données. Elle fonctionne par défaut sur MYSQL avec un utilisateur root et sans mot de passe. Le port par défaut est 3306 et génère une base nommée ‘dap’.

## **Informations concernant le fichier ‘credentials.json’, utilisé pour le fonctionnement des services Google :**

Par défaut, placez votre fichier ‘**credentials.json**’ dans le répertoire : C/google/credential/

Vous pouvez également modifier ce répertoire par défaut dans le fichier ‘**config.properties’** localisé dans Ynov_2018_dap\bap.server\src\main\resources. Il faut modifier la valeur de la clé : **credentials_folder**

**/ ! \\** Les tokens des comptes ajoutés expirent rapidement ! Pensez à les renouveler en cas de problème ; supprimez les comptes Microsoft dans la base de données et supprimez le ‘StoredCredential’ dans votre répertoire Google défini. **/ ! \\**
