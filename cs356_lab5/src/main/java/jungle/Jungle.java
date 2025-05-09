package jungle;

import java.util.Random;

public class Jungle {

    public static void main(String[] args) {
        int eastBound = 4;
        int westBound = 2;
        double apeMin = 4.0;
        double apeVar = 1.0;

        Ladder ladder = new Ladder(4);

        Thread eastThread = new Thread(() -> {
            int nRemaining = eastBound;
            int apeCounter = 1;
            while (nRemaining != 0) {
                new Ape("E-" + apeCounter++, ladder, true).start();
                tryToSleep(apeMin, apeVar);
                if (nRemaining > 0) {
                    nRemaining--;
                }
            }
        });

        Thread westThread = new Thread(() -> {
            int nRemaining = westBound;
            int apeCounter = 1;
            while (nRemaining != 0) {
                new Ape("W-" + apeCounter++, ladder, false).start();
                tryToSleep(apeMin, apeVar);
                if (nRemaining > 0) {
                    nRemaining--;
                }
            }
        });

        eastThread.start();
        westThread.start();
    }

    private static final Random dice = new Random();

    public static void tryToSleep(double secMin, double secVar) {
        try {
            // Thread.sleep(Math.round(secMin * 1000) + Math.round(dice.nextDouble() * secVar * 1000));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted.");
        }
    }
}
