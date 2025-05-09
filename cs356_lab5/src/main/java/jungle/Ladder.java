package jungle;

import java.util.concurrent.Semaphore;

public class Ladder {
    private final Semaphore[] rungLocks;

    public Ladder(int nRungs) {
        rungLocks = new Semaphore[nRungs];
        for (int i = 0; i < nRungs; i++) {
            // 1 permit = 1 ape at a time; fair=true for FIFO
            rungLocks[i] = new Semaphore(1, true);
        }
    }

    public int nRungs() {
        return rungLocks.length;
    }

    public void grabRung(int which) throws InterruptedException {
        rungLocks[which].acquire();
    }

    public void releaseRung(int which) {
        rungLocks[which].release();
    }
} 