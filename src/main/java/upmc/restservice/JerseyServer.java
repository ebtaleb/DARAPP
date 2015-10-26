package upmc.restservice;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;

import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;

import upmc.restservice.LoginServlet;
import upmc.restservice.RegisterServlet;

public class JerseyServer {

    private static List<ContainerInitializer> jspInitializers()
    {
        JettyJasperInitializer sci = new JettyJasperInitializer();
        ContainerInitializer initializer = new ContainerInitializer(sci, null);
        List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
        initializers.add(initializer);
        return initializers;
    }

    private static ServletHolder jspServletHolder()
    {
        ServletHolder holderJsp = new ServletHolder("jsp", JspServlet.class);
        holderJsp.setInitOrder(0);
        holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
        holderJsp.setInitParameter("fork", "false");
        holderJsp.setInitParameter("xpoweredBy", "false");
        holderJsp.setInitParameter("compilerTargetVM", "1.7");
        holderJsp.setInitParameter("compilerSourceVM", "1.7");
        holderJsp.setInitParameter("keepgenerated", "true");
        return holderJsp;
    }

    public static void main(String[] args) {
        int port = 2001;
        if(args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        Server server = new Server(port);

        ServletHolder restAPIServlet = new ServletHolder(org.glassfish.jersey.servlet.ServletContainer.class);
        restAPIServlet.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        restAPIServlet.setInitParameter("jersey.config.server.provider.packages", "upmc.restservice.resources");//Set the package where the services reside
        restAPIServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

        ServletContextHandler restHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        restHandler.addServlet(restAPIServlet, "/api/*");

        // Create a resource handler for static content.
        ResourceHandler staticResourceHandler = new ResourceHandler();
        staticResourceHandler.setResourceBase("src/main/webapps/static/");
        staticResourceHandler.setDirectoriesListed(true);

        // Create WebAppContext for JSP files.
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/app/*");
        webAppContext.setResourceBase("src/main/webapps/jsp/");
        webAppContext.setInitParameter("dirAllowed", "false");
        webAppContext.setAttribute("org.eclipse.jetty.containerInitializers", jspInitializers());
        webAppContext.addBean(new ServletContainerInitializersStarter(webAppContext), true);

        //webAppContext.addServlet(jspServletHolder(), "*.jsp");
        webAppContext.addServlet(LoginServlet.class, "/login");
        webAppContext.addServlet(RegisterServlet.class, "/register");
        //webAppContext.addServlet(HomeServlet.class, "/home");

        // Create a handler list to store our static and servlet context handlers.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { staticResourceHandler, webAppContext, restHandler });

        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(JerseyServer.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
