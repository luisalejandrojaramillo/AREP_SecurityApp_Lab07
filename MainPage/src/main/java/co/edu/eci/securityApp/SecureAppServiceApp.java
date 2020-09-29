package co.edu.eci.securityApp;

import spark.Filter;
import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class SecureAppServiceApp {

    private static Map<String, String> usernamePasswords = new HashMap<>();

    public static void main(String[] args) {
        port(getPort());
        // Comando para generar la llave
        // keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
        //ecikeystore 123456
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        secure("keystores/ecikeystore.p12", "123456", null, null);

        get("/hello", (req, res) -> "Hello World");

        get("/",(req,res) -> loginView(req,res));
        get("/results",(req,res) -> results(req,res));

        usernamePasswords.put("luis", "luis");
        usernamePasswords.put("admin", "admin");
        //https://localhost:5000/hello?user=foo&password=bar
        before("/home",new Filter() {
            @Override
            public void handle(Request request, Response response) {
                String user = request.queryParams("user");
                String password = request.queryParams("password");

                String dbPassword = usernamePasswords.get(user);
                if (!(password != null && password.equals(dbPassword))) {
                    halt(401, "You are NOT welcome here!!!");
                }
            }
        });
        /**
         before("/home", (request, response) -> {
         response.header("Foo", "Set by second before filter");
         });*/

        get("/home", (req, res) -> homeView(req,res));

        after("/home", (request, response) -> {
            response.header("spark", "added by after-filter");
        });
    }
    private static String  loginView(Request req, Response res){
        String view = "";

        view = "<!DOCTYPE html>"
                + "<html>"
                + "<style>"
                + "table, th, td {"
                + "border: 1px solid black;"
                + "border-collapse: collapse;"
                + "border-spacing: 0;"
                + "}"
                + "</style>"
                +"<center>"
                +"<h1>Login AREP</h1>"
                +"<br/>"
                +"<p>Ingrese con su usuario</p>"
                +"<form name='loginForm' method='get' action='/home'>"
                +"Usuario:<input type='text' name='user'/> <br/>"
                +"Contraseña:<input type='password' name='password'/> <br/>"
                +"<br/>"
                +"<input type='submit' value='Ingresar' />"
                +"</form>"
                +"<br/>"
                +"</center>"
                + "</body>"
                + "</html>";

        return view;
    }


    private static String  homeView(Request req, Response res) {
        String view = "";
        view = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<center>"
                + "<h1>Calculadora</h1>"
                + "<h2>AREP</h2>"
                + "<h2>Luis Alejandro Jaramillo</h2>"
                + "<h3>Ingrese los numeros a operar</h3>"
                + "<form action=\"/results\">"
                + "  Ingrese los números separados por coma(,):<br>"
                + "  <input type=\"text\" name=\"num\" placeholder=\"Ej. 1,2.0,3\">"
                + "  <br>"
                + "  <br> <br>"
                + "  <input type=\"submit\" value=\"Enviar\">"
                + "</form>"
                + "</center>"
                + "</body>"
                + "</html>";
        System.out.println("Hola");
        return view;
    }

    private static String results(Request req, Response res){
        String view = "";

        try {
            String params = req.queryParams("num");
            params = params.replace(",","%2C");
            System.out.println(params);



            URL url = new URL("http://localhost:9000/results?num="+params);
            System.out.println(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            System.out.println("Im hear");
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                view += inputLine;
            }
        } catch (MalformedURLException e) {
                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }


    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}