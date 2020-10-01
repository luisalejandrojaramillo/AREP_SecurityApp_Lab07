package co.edu.eci.securityApp;

import spark.Filter;
import spark.Request;
import spark.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static spark.Spark.*;

public class SecureAppServiceApp {

    private static Map<String, String> usernamePasswords = new HashMap<>();

    public static void main(String[] args) {
        port(getPort());
        // Comando para generar la llave
        // keytool -genkeypair -alias ecikeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ecikeystore.p12 -validity 3650
        //ecikeystore 123456
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        secure("MainPage/keystores/ecikeystore.p12", "123456", "MainPage/keystores/myTrustStore", "123456");

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

        post("/home", (req, res) -> homeView(req,res));

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
                +"<form name='loginForm' method='post' action='/home'>"
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
            // ec2-54-234-55-207.compute-1.amazonaws.com
            URL url = new URL("https://ec2-54-234-55-207.compute-1.amazonaws.com:46000/results?num="+params);

            System.out.println("----------------");
            System.out.println(url);
            System.out.println("----------------");

            view = URLReader.reader(url.toString());

            System.out.println(view);
            /**
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            System.out.println("Im hear");
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                view += inputLine;
            }
            */
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