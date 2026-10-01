
import java.io.InputStreamReader;

public class SumaCuadrados {
    public static void main(String[] args) {
        ProcessBuilder pb1= new ProcessBuilder("./cudrados.bat", "1", "2", "3", "4");
        ProcessBuilder pb2= new ProcessBuilder("./suma.bat");

        try (Process p1= pb1.start();){
            BufferedReader readP1= new BufferedReader(new InputStreamReader(new InputStream(p1.getInputStream())));
        } catch (Exception e) {
        }
    }
}
