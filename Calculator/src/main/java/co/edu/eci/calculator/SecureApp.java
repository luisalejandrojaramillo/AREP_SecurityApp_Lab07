package co.edu.eci.calculator;

import co.edu.eci.calculator.process.Calculator;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.*;
import static spark.Spark.halt;

public class SecureApp {
    public static void main(String[] args) {


        port(getPort());
        secure("keystores/ecikeystore.p12", "123456", "keystores/myTrustStore", "123456");
        get("/hello", (req, res) -> "Hello World");
        get("/results", (req,res) -> results(req,res));
    }

    /**
     * Vista con los resultados de la operacion
     * @param req
     * @param res
     * @return
     */
    private static String results(Request req, Response res){
        String pageContent = "";
        System.out.println("he try");
        System.out.println(req.queryParams("num"));
        if(req.queryParams("num").length()!=0) {
            String[] a=req.queryParams("num").split(",");
            String respuesta = "";
            double[] dataList = new double[a.length];
            for (int i = 0; i < a.length; i++) {
                dataList[i] = Double.parseDouble(a[i]);
            }
            double[] listAux = Calculator.bubbleSort(dataList);
            String prom = String.valueOf(Calculator.promedio(dataList));
            String suma = String.valueOf(Calculator.suma(dataList));
            respuesta = String.valueOf(listAux[0]);
            for (int i = 1; i < listAux.length; i++) {
                respuesta = respuesta + " , " + String.valueOf(listAux[i]);
            }
            pageContent = "<!DOCTYPE html>" + "<html>" + "<body>"
                    + "<center>" + "<h2>Resultado</h2>"
                    + "<h3> BubbleSort: " + respuesta + "</h3>"
                    + "<h3> Promedio: " + prom + "</h3>"
                    + "<h3> Suma: " + suma + "</h3>"
                    + "<p><a href=\"/\">Back</a></p>"
                    + "</center>" + "</body>" + "</html>";
        }else{
            pageContent = "<!DOCTYPE html>" + "<html>" + "<body>"
                    + "<center>"
                    + "<h2>No ha ingresado ningun numero</h2>"
                    + "<p><a href=\"/\">Back</a></p>"
                    + "</center>" + "</body>" + "</html>";
        }
        return pageContent;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 9000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
