

public class ConfirmationWindow extends BasicJFrame {


    public ConfirmationWindow() {
        super(Const.WINDOW_W, Const.WINDOW_H);
        MessageConfirmation messageConfirmation = new MessageConfirmation();
        this.add(messageConfirmation);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        ConfirmationWindow confirmationWindow = new ConfirmationWindow();

    }
}
