package jungle;

import java.util.concurrent.Semaphore;

public class Ladder {

    private final Semaphore[] rungLocks;

    public Ladder(int nRungs) {
        rungLocks = new Semaphore[nRungs];
        for (int i = 0; i < nRungs; i++) {
            rungLocks[i] = new Semaphore(1, true);
        }
    }

    public int nRungs() {
        return rungLocks.length;
    }

    public void grabRung(int which) throws InterruptedException {
        boolean acquired = rungLocks[which].tryAcquire();
        if (!acquired) {
            System.err.println("DEADLOCK WARNING: Ape could not acquire rung " + which + ". Terminating program.");
            System.exit(1);
        }
    }

    public void releaseRung(int which) {
        rungLocks[which].release();
    }
}
