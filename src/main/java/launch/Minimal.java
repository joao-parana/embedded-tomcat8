package launch;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Catalina;
import org.apache.catalina.startup.Tomcat;

public class Minimal {
  public static void main(String[] args)
      throws LifecycleException, InterruptedException, ServletException, NamingException {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

    Tomcat.addServlet(ctx, "hello", new HttpServlet() {
      private static final long serialVersionUID = -700225531750118263L;

      protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer w = resp.getWriter();
        w.write("Hello, World!");
        w.flush();
      }
    });
    ctx.addServletMapping("/*", "hello");

    tomcat.start();

    Catalina c = tomcat.getServer().getCatalina();
    System.out.println("Catalina = " + c);
    ActiveDirectoryRealm realm = new ActiveDirectoryRealm();
    System.out.println("realm = " + realm);

    tomcat.getServer().await();
  }
}
