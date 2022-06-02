public abstract class MyRunnable implements Runnable {
    private boolean running = true;
    private static final int SPEED =0;
    private Start start;

    public MyRunnable(Start start) {
        this.start=start;
    }
    public Start getStart(){
        return this.start;
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
