package bank;

public class InterfaceExample {

    public static void main(String[] args) {
        FunctionalImpl fi = new FunctionalImpl();
        Runner runner = new Runner();
        runner.makeItRun(fi);
    }

}

class Runner {
    void makeItRun(MyRunnable runnable) {
        runnable.run();
    }
}

interface MyRunnable {
    void run();
}

class FunctionalImpl implements MyRunnable {
    public void run() {
        System.out.println("Running...");
    }
}
