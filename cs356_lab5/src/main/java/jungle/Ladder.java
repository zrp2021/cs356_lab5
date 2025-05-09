package jungle;

import java.util.concurrent.Semaphore;

public class Ladder {

    private final Semaphore[] rungLocks;

    // Coordination for direction-safe crossing
    private final Semaphore mutex = new Semaphore(1); // protect shared state
    private final Semaphore directionLock = new Semaphore(1, true); // only one direction at a time
    private int eastboundCount = 0;
    private int westboundCount = 0;

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

    public void enterLadder(boolean goingEast) throws InterruptedException {
        mutex.acquire();
        if (goingEast) {
            if (eastboundCount == 0) {
                directionLock.acquire();
            }
            eastboundCount++;
        } else {
            if (westboundCount == 0) {
                directionLock.acquire();
            }
            westboundCount++;
        }
        mutex.release();
    }

    public void exitLadder(boolean goingEast) throws InterruptedException {
        mutex.acquire();
        if (goingEast) {
            eastboundCount--;
            if (eastboundCount == 0) {
                directionLock.release();
            }
        } else {
            westboundCount--;
            if (westboundCount == 0) {
                directionLock.release();
            }
        }
        mutex.release();
    }
}
