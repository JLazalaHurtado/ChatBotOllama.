package laboratorio1;

import com.formdev.flatlaf.FlatLightLaf;
import java.net.MalformedURLException;


public class Laboratorio1 {

    public static void main(String[] args) throws MalformedURLException {
        FlatLightLaf.setup();
        ChatBot frame = new ChatBot();
        frame.setVisible(true);
        frame.setSize(881, 517);
        frame.setLocationRelativeTo(null);

    }
}

