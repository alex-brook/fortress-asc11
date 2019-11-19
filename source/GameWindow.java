import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class GameWindow {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File("test/map.txt")));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        GameState gs = new GameState(sb.toString());
    }

}
