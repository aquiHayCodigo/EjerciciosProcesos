
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContadoraparicionesVPipeLine {
   public static void main(String[] args) {
      File file1 = new File("archivoPalabras.txt");
      ProcessBuilder p1 = new ProcessBuilder("cmd", "/c", "contar_palabras.bat", "no");
      p1.redirectOutput(file1);
      File file2 = new File("archivoPalabras.txt");
      ProcessBuilder p2 = new ProcessBuilder("cmd", "/c", "more >", "resultadoPalabras.txt");
      p2.redirectOutput(file2);

      List<ProcessBuilder> procesosEncadenados = List.of(p1, p2);

      try {
         List<Process> procesos = ProcessBuilder.startPipeline(procesosEncadenados);
         Process last = procesos.getLast();
         last.waitFor();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

   }
}
