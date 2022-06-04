public abstract class MyRunnable implements Runnable {
    private boolean running = true;
    private static final int SPEED =0;
    private MyRunnable other;
    private StartSystemDriver startSystemDriver;

    public MyRunnable(StartSystemDriver startSystemDriver) {
        this.startSystemDriver = startSystemDriver;
    }
    public StartSystemDriver getStart(){
        return this.startSystemDriver;
    }
    public void setRunning(boolean running){
        this.running=running;
    }

    public void stop() {
        running = false;
    }
    public boolean isRunning(){
        return running;
    }

    public abstract void _run();

    @Override
    public void run() {
        while (running) {
            this._run();
        }
    }
}
