# DAP

TODO :
/admin
	/ <- Affiche microsoft + google combiné
	/microsoft <- Affiche l'ensemble des comptes microsoft
	/google <- Affichage du datastore
	
/calendar
	/{user} <- Affiche le prochain événement entre google et microsoft
	/microsoft/{user}
	/google/{user}
	
/mail
	/unread
		/{user} <- Affiche le nombre total de unread google et microsoft
		/microsoft/{user}
		/google/{user}
		
	/microsoft/{user} <- Affiche les premiers mail de microsoft
	
/contact/
	/nb
		/{user} <- Affiche le nombre total de contact google et microsoft
		/microsoft/{user}
		/google/{user}
		
	/microsoft/{user} <- Affiche les premiers contact de microsoft