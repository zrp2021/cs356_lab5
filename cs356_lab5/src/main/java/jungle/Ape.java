package jungle;

public class Ape extends Thread {

    private static final boolean DEBUG = true;
    private static final double RUNG_DELAY_MIN = 0.8;
    private static final double RUND_DELAY_VAR = 1.0;

    private final String name;
    private final Ladder ladder;
    private final boolean goingEast;

    public Ape(String name, Ladder ladder, boolean goingEast) {
        this.name = name;
        this.ladder = ladder;
        this.goingEast = goingEast;
    }

    public void run() {
        int startRung = goingEast ? 0 : ladder.nRungs() - 1;
        int endRung = goingEast ? ladder.nRungs() - 1 : 0;
        int move = goingEast ? 1 : -1;

        try {
            ladder.enterLadder(goingEast);

            if (DEBUG) {
                System.out.println("Ape " + name + " wants rung " + startRung);
            }
            ladder.grabRung(startRung);
            if (DEBUG) {
                System.out.println("Ape " + name + " got rung " + startRung);
            }

            for (int i = startRung + move; i != endRung + move; i += move) {
                Jungle.tryToSleep(RUNG_DELAY_MIN, RUND_DELAY_VAR);
                if (DEBUG) {
                    System.out.println("Ape " + name + " wants rung " + i);
                }
                ladder.grabRung(i);
                if (DEBUG) {
                    System.out.println("Ape " + name + " got " + i + " releasing " + (i - move));
                }
                ladder.releaseRung(i - move);
            }
            ladder.releaseRung(endRung);
            ladder.exitLadder(goingEast);

            System.out.println("Ape " + name + " finished going " + (goingEast ? "East." : "West."));

        } catch (InterruptedException e) {
            System.out.println("Ape " + name + " was interrupted and fell into the river.");
        }
    }
}
