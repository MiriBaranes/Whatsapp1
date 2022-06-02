import java.awt.*;

public class LoginThread extends MyRunnable {

    public LoginThread(Start start) {
        super(start);
    }


    @Override
    public void _run() {
        if (getStart().login()) {
            getStart().setBackGround("52141889_101.jpg");
            getStart().getSystemMessages().setText("Login Successfully");
            getStart().setMessageList();
            stop();
        }
    }
}
