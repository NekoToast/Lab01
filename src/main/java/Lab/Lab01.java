package Lab;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class Main{
    public static void main(String[] args) {
        final Main main = new Main();
        long startS, stopS, startAS, stopAS;
        double timeS, timeAS;


        startS = System.currentTimeMillis();




        for(int i=0;i<10;i++) {
            System.out.println("Sync Thread " + i + " Running");
            new Thread() {
                public void run() {
                        synchronized (main) {
                            main.download();


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

        for(int i=0;i<10;i++) {
            threadList.add(i, new Thread() {
                public void run() {
                    main.download();

                }
            });
        }
        for(int i=0;i<10;i++) {

            threadList.get(i).start();
        }

        for(int i=0;i<10;i++) {
            try {
                System.out.println("Async Thread "+i+" Running");
                threadList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stopAS = System.currentTimeMillis();
        timeAS = stopAS - startAS;
        System.out.println("Summary time of asynchronic threads: " + timeAS/1000 + "s");




    }

    private void download() {
        Image image;
        try {
            URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/b/b5/Hanover_bars_with_PAL_delay.png");

            image = ImageIO.read(url);

        } catch (IOException e) {
            System.out.println("Blad pobierania");
            e.printStackTrace();
        }
    }
}
