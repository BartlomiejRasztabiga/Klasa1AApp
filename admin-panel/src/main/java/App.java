import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Spark;
import spark.route.RouteOverview;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(App.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        staticFileLocation("/public");

        before("/admin/*", (request, response) -> {
            boolean authenticated = false;

            if (request.session() != null) {
                if (request.session().attribute("authenticated") == Boolean.valueOf(true)) {
                    authenticated = true;
                }
            }

            if (!authenticated) {
                response.redirect("/loginAdmin");
            }
        });

        get("/admin/dashboard", (request, response) -> {
            response.status(200);
            response.type("text/html");
            Students model = new Students();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("students", model.getAllStudents());

            System.out.println("hehs");
            System.out.println("test2");

            return freeMarkerEngine.render(new ModelAndView(attributes, "main.ftl"));
        });

        get("/admin/students", (request, response) -> {
            response.status(200);
            response.type("text/html");
            Students model = new Students();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("students", model.getAllStudents());
            return freeMarkerEngine.render(new ModelAndView(attributes, "main.ftl"));
        });

        get("/loginAdmin", (request, response) -> {
            response.type();
            response.redirect("admin_login.html");
            return null;
        });

        get("/logoutAdmin", (request, response) -> {
            request.session().invalidate();
            response.redirect("/loginAdmin");
            return null;
        });

        post("/adminLoginCallback", (request, response) -> {
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            //TODO Unhardcode it later ;)
            if (email.equals("bartlomiej.rasztabiga.official@gmail.com") && password.equals("admin")) {
                request.session(true);
                request.session().attribute("authenticated", true);
                response.redirect("/admin/dashboard");
            } else {
                halt(401, "Wrong credentials");
            }

            return null;
        });


        Spark.exception(Exception.class, (e, request, response) -> {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            System.err.println(sw.getBuffer().toString());
        });

        RouteOverview.enableRouteOverview();
    }
}