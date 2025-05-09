package jungle;

public class Ape extends Thread {

    private static final boolean debug = true;
    private static final double rungDelayMin = 0.8;
    private static final double rungDelayVar = 1.0;

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
            if (debug) {
                System.out.println("Ape " + name + " wants rung " + startRung);
            }
            ladder.grabRung(startRung);
            if (debug) {
                System.out.println("Ape " + name + " got rung " + startRung);
            }

            for (int i = startRung + move; i != endRung + move; i += move) {
                Jungle.tryToSleep(rungDelayMin, rungDelayVar);
                if (debug) {
                    System.out.println("Ape " + name + " wants rung " + i);
                }
                ladder.grabRung(i);
                if (debug) {
                    System.out.println("Ape " + name + " got " + i + " releasing " + (i - move));
                }
                ladder.releaseRung(i - move);
            }
            ladder.releaseRung(endRung);
            System.out.println("Ape " + name + " finished going " + (goingEast ? "East." : "West."));

        } catch (InterruptedException e) {
            System.out.println("Ape " + name + " was interrupted and fell into the river.");
        }
    }
}
