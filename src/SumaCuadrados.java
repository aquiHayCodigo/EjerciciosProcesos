
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SumaCuadrados {
    public static void main(String[] args) {
        ProcessBuilder pb1 = new ProcessBuilder("cmd", "/c","cuadrados.bat", "1", "2", "3", "4");
        ProcessBuilder pb2 = new ProcessBuilder("cmd", "/c","suma.bat");

        try (Process p1 = pb1.start();
            // Resultado cuadrados
            BufferedReader lecturaSalidaP1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            Process p2 = pb2.start();
            //BufferWriter para entrada segundo proceso
            BufferedWriter escribiendoEnP2 = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
            //
            BufferedReader lecturaSalidaP2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        ) {
            //File file = new File("resultado.txt");
            //BufferedWriter escribiendo= new BufferedWritter(new FileWriter(file));
            String linea;
            while((linea=lecturaSalidaP1.readLine())!=null){
                //Escribo la salida en el proceso 2
                //System.out.println(linea);
                escribiendoEnP2.write(linea);
                escribiendoEnP2.newLine();
            }
            escribiendoEnP2.close();
            
            while((linea=lecturaSalidaP2.readLine())!=null){
                System.out.println(linea);
            }
            //String resultado = lecturaSalidaP2.readLine();
            //System.out.println(resultado);
        } catch (Exception e) {
        }
    }
}
