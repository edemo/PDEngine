package org.rulez.demokracia.pdengine.integrationtest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyThread extends Thread {

	private Server server;

	public void run() {
        ResourceConfig config = new ResourceConfig();
        config.packages("org.rulez.demokracia.pdengine.servlet");
        ServletHolder jerseyServlet 
                        = new ServletHolder(new ServletContainer(config));

        server = new Server(8080);
        ServletContextHandler context 
                = new ServletContextHandler(server, "/");
        context.addServlet(jerseyServlet, "/*");
        try {
			server.start();
			String state = "none";
			while (!state.equals("STARTED")) {
				state = server.getState();
					sleepOneSecond();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sleepOneSecond() {
		try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	public void end() throws Exception {
		server.stop();
		server.destroy();
	}
}
