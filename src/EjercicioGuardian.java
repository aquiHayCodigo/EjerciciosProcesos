import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class EjercicioGuardian {
    // Abrir cancion y si se cierra el navegador que se abra automaticamente
    public static void main(String[] args) {
        navegador();

        while (true) {
            ProcessBuilder pb2 = new ProcessBuilder("tasklist");
            ProcessBuilder pb3 = new ProcessBuilder("cmd", "/c", "findstr firefox");

            try (
                    Process p2 = pb2.start();
                    // recoge salida de proceso 2 (tasklist)
                    InputStream in1 = p2.getInputStream();
                    InputStreamReader input1 = new InputStreamReader(in1);
                    BufferedReader br1 = new BufferedReader(input1);

                    // lo que va a escribir la tasklist dentro del proceso 3
                    Process p3 = pb3.start();
                    PrintWriter escribiendoP3 = new PrintWriter(p3.getOutputStream());

                    // recoge salida del proceso 3 (firefox en tasklist)
                    InputStream in2 = p3.getInputStream();
                    InputStreamReader input2 = new InputStreamReader(in2);
                    BufferedReader br2 = new BufferedReader(input2);) {
                // escribiendo la salida del proceso 2 en la entrada del proceso 3
                String salida2, salida3;
                while ((salida2 = br1.readLine()) != null) {
                    escribiendoP3.write(salida2);
                }

                // comprobar que firefoz aparece al menos una vez en tasklist
                salida3 = br2.readLine();
                if (salida3 == null) {
                    navegador();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void navegador() {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
                "start firefox https://www.youtube.com/watch?v=Y4IO3GwsFPs");
        try (Process p = pb.start();) {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
