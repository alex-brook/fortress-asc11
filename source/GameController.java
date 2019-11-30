import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GameController {

    private String stringFromFile(final String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            return new String(data, "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }
}
