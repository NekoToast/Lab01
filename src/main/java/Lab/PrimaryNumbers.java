package Lab;
import java.util.ArrayList;
import java.util.List;

class Fibonacci{
    public static void main(String[] args) {
        final Fibonacci main2 = new Fibonacci();
        long startS, stopS, startAS, stopAS;
        double timeS, timeAS;


        startS = System.currentTimeMillis();



        for(int i=0;i<4;i++) {
            final int var = i;
            new Thread() {
                public void run() {
                    System.out.println("Fibonacci Sync Thread "+var+" Running");
                    for (int j = 0; j < 10; j++) {
                        synchronized (main2) {
                            System.out.println(main2.fibonacci(var * 10 + j));

                        }
                    }
                }
            }.run();

        }


        stopS = System.currentTimeMillis();
        timeS = stopS - startS;
        System.out.println("Summary time of synchronic threads: " + timeS/1000 + "s");

        /* _________________________________________________________ */


        startAS = System.currentTimeMillis();
        List<Thread> threadList;
        threadList = new ArrayList<Thread>();

        for(int i=0;i<4;i++) {
            final int var = i;
            threadList.add(i, new Thread() {
                public void run() {
                    System.out.println("Fibonacci Async Thread "+var+" Running");
                    for (int j = 0; j < 10; j++) {
                        System.out.println(main2.fibonacci(var * 10 + j));

                    }

                }
            });

            }


        for(int i=0;i<4;i++) {
            threadList.get(i).start();
        }

        for(int i=0;i<4;i++) {
            try {
                threadList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stopAS = System.currentTimeMillis();
        timeAS = stopAS - startAS;
        System.out.println("Summary time of asynchronic threads: " + timeAS/1000 + "s");




    }


    public static long fibonacci(int n) {
        if (n <= 1) return n;
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }

}

