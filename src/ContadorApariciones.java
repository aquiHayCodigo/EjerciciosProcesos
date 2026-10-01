import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ContadorApariciones {
    public static void main(String[] args) {
        String archivoBat = "./contar_palabras.bat";
        String ficheroTexto = "loreipsum.txt";
        String palabra = "in";
        ProcessBuilder pBuilder = new ProcessBuilder( 
                archivoBat, ficheroTexto, palabra);
        
        String resultado = "resultado.txt";
        File file = new File(resultado);
        ProcessBuilder pBuilder2 = new ProcessBuilder("cmd", "/c", "more > " + resultado);
        System.out.println(file.getParentFile());
        // null -> mismo directorio que el main.java
        pBuilder2.directory(file.getParentFile());
        try (
            Process process = pBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bReader = new BufferedReader(inputStreamReader);

            Process process2 = pBuilder2.start();
            OutputStream outputStream = process2.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
        ) {
            String contador = bReader.readLine();
            writer.write(contador);
            writer.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
