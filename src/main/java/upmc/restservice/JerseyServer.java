package upmc.restservice;

import java.util.logging.Level;  
import java.util.logging.Logger;  
import org.eclipse.jetty.server.Server;  
import org.eclipse.jetty.servlet.ServletContextHandler;  
import org.eclipse.jetty.servlet.ServletHolder;  
  
public class JerseyServer {  
    public static void main(String[] args) {  
        int port = 2001;  
        if(args.length == 1) {  
            port = Integer.parseInt(args[0]);  
        }  
        Server server = new Server(port);  
          
        ServletHolder servletHolder = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);  
        servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");  
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "upmc.restservice.resources");//Set the package where the services reside  
        servletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");  
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);  
        server.setHandler(contextHandler);  
        contextHandler.addServlet(servletHolder, "/api/*");  
          
        try {  
            server.start();  
            server.join();  
        } catch (Exception ex) {  
            Logger.getLogger(JerseyServer.class.getName()).log(Level.SEVERE, null, ex);  
            System.exit(1);  
        }  
    }
}