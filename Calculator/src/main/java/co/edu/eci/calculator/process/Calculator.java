package co.edu.eci.calculator.process;

public class Calculator {
    public Calculator(){

    }

    /**
     * Implemenyacion del buble sort
     * @param arr
     * @return lista ordenada
     */
    public static double[] bubbleSort(double[] arr) {
        double temp = 0;
        int longitud = arr.length;
        for(int i=0; i < longitud; i++){
            for(int j=1; j < (longitud-i); j++){
                if(arr[j-1] > arr[j]){
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * imprimimos el arreglo
     * @param arr
     */
    public static void printArray(double arr[]) {
        double n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    /**
     *
     * @param arr
     * @return el primedio de la lista
     */
    public static double promedio(double arr[]) {
        double temp = 0;
        int longitud = arr.length;
        for(int i=0; i < longitud; i++)
            temp+=arr[i];
        return temp/longitud;
    }

    /**
     *
     * @param arr
     * @return la suma de la lista
     */
    public static double suma(double arr[]) {
        double temp = 0;
        int longitud = arr.length;
        for(int i=0; i < longitud; i++)
            temp+=arr[i];
        return temp;
    }

}
