
public class MainStart extends BasicJFrame {
    public MainStart(StartSystemDriver myPanel) {
        super(Const.WINDOW_W, Const.WINDOW_H);
        this.add(myPanel);
        this.setVisible(true);
    }
}
