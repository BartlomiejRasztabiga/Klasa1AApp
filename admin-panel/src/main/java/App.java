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

            if (!authenticated) {
                response.redirect("/loginAdmin");
            }
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

        post("/adminLoginCallback", (request, response) -> {
            System.out.println(request.contentType()); // What type of data am I sending?
            System.out.println(request.params()); // What are the params sent?
            System.out.println(request.raw()); // What's the raw data sent?
            System.out.println(request.attributes());
            System.out.println(request.queryParams());
            return request.body();
        });

        post("/lol", (request, response) -> {
            return "lol";
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