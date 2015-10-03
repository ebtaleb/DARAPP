package upmc.restservice;  
 
import org.eclipse.jetty.server.Server;  
import org.eclipse.jetty.servlet.ServletHandler;  

public class RestServer {  
	public static void main(String[] args) throws Exception {  
	    int port = 2001;  
	    if(args.length == 1) {  
		port = Integer.parseInt(args[0]);  
	    }  
	    Server server = new Server(port);  
	    ServletHandler handler = new ServletHandler();  
	    handler.addServletWithMapping(HelloServlet.class, "/hello");  
	    server.setHandler(handler);  
	      
	    server.start();  
	    server.join();  
	}  
}  