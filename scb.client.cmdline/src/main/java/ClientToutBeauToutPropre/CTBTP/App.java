package ClientToutBeauToutPropre.CTBTP;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {	
    	if(args.length > 2) {
    		System.out.println("Il ne peux y avoir plus de 2 parametre : *action* *userKey*");
    		return;
    	}
    	
        if(args[0].equals("view")) {
        	URL url = new URL("http://localhost:8080/nbUnread?userKey="+args[1]);
        	String r = HttpHelper.ProcessGet(url);
        	System.out.println("Nombre de mail non lu:"+r);
        }else if(args[0].equals("add")) {
        	URI uri = URI.create("http://localhost:8080/account/add/"+args[1]);
        	Desktop.getDesktop().browse(uri);
        }else if(args[0].equals("contacts")) {
        	URL url = new URL("http://localhost:8080/nbPeople?userKey="+args[1]);
        	System.out.println("Nombre de contacts lié a votre compte google"+HttpHelper.ProcessGet(url));
        }else if(args[0].equals("event")) {
        	URL url = new URL("http://localhost:8080/event?userKey="+args[1]);
        	System.out.println(HttpHelper.ProcessGet(url));
        }else {
        	URL url = new URL("http://localhost:8080/nbUnread?userKey="+args[0]);
        	String r = HttpHelper.ProcessGet(url);
        	System.out.println("Nombre de mail non lu:"+r);
        }
    }
    
}
