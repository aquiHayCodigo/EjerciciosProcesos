import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MenuProcesos {

    static List<Process> background_process = new ArrayList<>();

    public static void main(String[] args) {
        boolean ok = true;
        while (ok) {
            System.out.println("------------------");
            System.out.println("--> 1. Primer plano");
            System.out.println("--> 2. Segundo plano");
            System.out.println("--> 3. PIDs");
            System.out.println("--> 4. Cerrar programas");
            System.out.println("--> Salir");
            System.out.println("------------------");

            String opc = IO.readln();
            switch (opc) {
                case "1":
                    primerPlano();
                    break;
                case "2":
                    segundoPlano();
                    break;
                case "3":
                    verPIDs();
                    break;
                case "4":
                    cerrarPrograma();
                default:
                    ok = false;
                    break;
            }
        }
    }

    public static void primerPlano() {
        ProcessBuilder pBuilder = new ProcessBuilder("ping", "www.google.es");
        pBuilder.inheritIO();
        try {
            Process p = pBuilder.start();
            System.out.println("[PID] : " + p.pid());
            p.waitFor();
            System.out.println("He terminado de dormir");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void segundoPlano() {
        ProcessBuilder pBuilder = new ProcessBuilder("ping", "www.google.es");
        ProcessBuilder pBuilder2 = new ProcessBuilder("ping", "www.firefox.es");
        try {
            Process p = pBuilder.start();
            Process p2 = pBuilder2.start();
            System.out.println("Iniciando -> [PID] : " + p.pid() + " status : " + p.isAlive());
            System.out.println("Iniciando -> [PID] : " + p2.pid() + " status : " + p.isAlive());
            background_process.add(p);
            background_process.add(p2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void procesoPâusa() {
        ProcessBuilder pausaBuilder = new ProcessBuilder("sleep", "5");

        try (Process pausa = pausaBuilder.start()) {
            System.out.println("Proceso pesado corriendo");
            Instant inicio = Instant.now();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void verPIDs() {
        for (Process p : background_process) {
            System.out.println("[PID] : " + p.pid() + " status : " + p.isAlive());
        }

    }

    public static void cerrarPrograma() {
        int i = 0;
        try {
            for (Process process : background_process) {
                if (process.isAlive()) {
                    System.out.println("[PID] : " + process.pid() + " status : " + process.isAlive());
                    process.waitFor();
                } else {
                    System.out.println("No hay procesos activos");
                    System.exit(0);
                }
            }
        } catch (InterruptedException e) {
            // TODO: handle exception
        }

            // hacerlo con un for() donde el i sea distinto de 0 o con un Iterator<> iterador
    }
}
