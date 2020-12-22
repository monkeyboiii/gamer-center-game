import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Music {
    public static void main(String[] args) throws JavaLayerException, FileNotFoundException {
        File file = new File("src/main/resources/audio/chuYu.mp3");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream stream = new BufferedInputStream(fis);
        Player player = new Player(stream);
        player.play();
    }
}