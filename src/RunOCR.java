import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunOCR {
    public String runOCR(String modelPath, String capturePath, String mangaOCR_Path)
            throws IOException, UnsupportedFlavorException {
        String extractedText;
        try {
            ProcessBuilder builder = new ProcessBuilder("python",
                    mangaOCR_Path,
                    modelPath, capturePath);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String lines = null;
            while ((lines=reader.readLine())!=null) {
                System.out.println("lines : " + lines);
            }
            while ((lines=readers.readLine())!=null) {
                System.out.println("Error lines : " + lines);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        extractedText = (String) Toolkit.getDefaultToolkit()
                .getSystemClipboard().getData(DataFlavor.stringFlavor);


        return extractedText;
    }
}
