import javax.swing.*;
import java.awt.*;

public class BasicJFrame extends JFrame {

    public BasicJFrame(int w, int h){
        this.setSize(w,h);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}

