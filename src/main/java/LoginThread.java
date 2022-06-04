public class LoginThread extends MyRunnable {
    private static final String PHOTO_PATH="52141889_101.jpg";
    private static final String SYSTEM_MESSAGE="Login Successfully";

    public LoginThread(StartSystemDriver startSystemDriver) {
        super(startSystemDriver);
    }
    @Override
    public void _run() {
        if (getStart().login()) {
            getStart().setBackGround(PHOTO_PATH); //wow
            getStart().getSystemMessages().setText(SYSTEM_MESSAGE);
            getStart().setMessageList();
            stop();
        }
    }
}
