package website.main;

import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcatServer {
	
	private static Tomcat tomcat;

    public static void start() {
        try {
            tomcat = new Tomcat();
            tomcat.setBaseDir("target/tomcat");
            tomcat.setPort(8080);
            tomcat.addWebapp("work/Tomcat/localhost/MainWebsite/", "src/main/java"); 

            tomcat.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            if (tomcat != null) {
                tomcat.stop();
                tomcat.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
