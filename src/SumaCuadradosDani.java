import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class SumaCuadradosDani {
    public static void main(String[] args) {
                
        ProcessBuilder pBuilder = new ProcessBuilder(
            "cmd", "/c", 
            "cuadrados.bat", "1", "2", "3", "4");

        ProcessBuilder pBuilder2 = new ProcessBuilder(
            "cmd", "/c", 
            "suma.bat");

        try (
            Process process = pBuilder.start();
            InputStream iStream = process.getInputStream();
            InputStreamReader input = new InputStreamReader(iStream);
            BufferedReader bReader = new BufferedReader(input);

            Process process2 = pBuilder2.start();
            OutputStream oStream = process2.getOutputStream();
            OutputStreamWriter output = new OutputStreamWriter(oStream);
            BufferedWriter bWriter = new BufferedWriter(output);

            InputStream iStream2 = process2.getInputStream();
            InputStreamReader input2 = new InputStreamReader(iStream2);
            BufferedReader bReader2 = new BufferedReader(input2);

        ) {
            
            String linea;
            while ((linea = bReader.readLine()) != null) {
                bWriter.write(linea);
                bWriter.newLine();
            }
            bWriter.close();

             while ((linea = bReader2.readLine()) != null) {
                System.out.println(linea);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public static void conPipeline(){
        ProcessBuilder pBuilder = new ProcessBuilder(
            "cmd", "/c", 
            "cuadrados.bat");
        
        ProcessBuilder pBuilder2 = new ProcessBuilder(
            "cmd", "/c", 
            "suma.bat");

        // opcion 1
        List<ProcessBuilder> pBuilders = List.of(pBuilder, pBuilder2);
        // opcion 2
        pBuilders = new ArrayList<>();
        pBuilders.add(pBuilder);
        pBuilders.add(pBuilder2);
        try {
            List<Process> procesos = ProcessBuilder.startPipeline(pBuilders);
            Process last = procesos.getLast();//procesos.get(procesos.size()-1);
            
            InputStream iStream = last.getInputStream();
            InputStreamReader input = new InputStreamReader(iStream);
            BufferedReader bReader = new BufferedReader(input);

            String linea;
            while ((linea = bReader.readLine()) != null) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public static void conPipelineyRedireccion(){
        ProcessBuilder pBuilder = new ProcessBuilder(
            "cmd", "/c", 
            "cuadrados.bat");
            File ficheroEntrada = new File("numeros.txt");
            pBuilder.redirectInput(ficheroEntrada);
        
        ProcessBuilder pBuilder2 = new ProcessBuilder(
            "cmd", "/c", 
            "suma.bat");
            File ficheroSalida = new File("solucion.txt");
            pBuilder2.redirectOutput(ficheroSalida);
        // opcion 1
        List<ProcessBuilder> pBuilders = List.of(pBuilder, pBuilder2);
        // opcion 2
        pBuilders = new ArrayList<>();
        pBuilders.add(pBuilder);
        pBuilders.add(pBuilder2);
        try {
            List<Process> procesos = ProcessBuilder.startPipeline(pBuilders);
            Process last = procesos.getLast();
            last.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
