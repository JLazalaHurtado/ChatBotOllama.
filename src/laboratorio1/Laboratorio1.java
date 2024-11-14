package laboratorio1;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.net.MalformedURLException;

public class Laboratorio1 {

    public static void main(String[] args) throws MalformedURLException {
        FlatDarkLaf.setup();
        ChatBot frame = new ChatBot();
        frame.setVisible(true);
        frame.setSize(881, 517);
        frame.setLocationRelativeTo(null);
    }
}

