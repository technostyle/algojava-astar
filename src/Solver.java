package algojava;

import java.util.PriorityQueue;
import java.util.LinkedList;

public class Solver {
    int a = 1, b = 1;

    private class StateTriple implements Comparable<StateTriple>{
        public BoardState cur;
        public BoardState prev;
        public int steps;

        public StateTriple(BoardState prev, BoardState cur, int steps) {
            this.prev = prev;
            this.cur = cur;
            this.steps = steps;
        }

        @Override
        public int compareTo(StateTriple o) {
            int c1 = a * cur.manhattan() + b * steps;
            int c2 = a * o.cur.manhattan() + b * o.steps;

            if      (c1 < c2) return -1;
            else if (c1 > c2) return 1;
            else              return 0;
        }

    }

    private PriorityQueue<StateTriple> queue;
    private StateTriple initial;
    private boolean logging = false;

    private void init(BoardMatrix matrix) {
        queue = new PriorityQueue<StateTriple>();
        BoardState cur = new BoardState(matrix, 0);
        BoardState prev = null;
        initial = new StateTriple(prev, cur, 0);
    }

    public Solver(BoardMatrix matrix) {
        init(matrix);
    }

    public Solver(BoardMatrix matrix, int a, int b) {
        init(matrix);
        setPriorityParams(a, b);
    }

    public void setPriorityParams(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public void setLogging(boolean flag) {
        logging = flag;
    }

    private LinkedList<StateTriple> getNextTriples(StateTriple state) {
        LinkedList<StateTriple> result = new LinkedList<StateTriple>();

        int steps = state.steps;
        BoardState prev = state.prev;
        BoardState cur = state.cur;
        LinkedList<BoardState> nextStates = cur.getNextStates();

        BoardState next;
        while((next = nextStates.pollFirst()) != null) {
            if (!next.isEqual(prev)) {
                result.addFirst(
                    new StateTriple(cur, next, steps + 1)
                );
            }
        }

        return result;
    }

    private void fillQueue(StateTriple state) {
        LinkedList<StateTriple> nextTriples = getNextTriples(state);
        StateTriple next;
        while((next = nextTriples.pollFirst()) != null) {
            queue.add(next);
        }
    } 

    public void solve() {
        queue.clear();
        queue.add(initial);
        StateTriple state = new StateTriple(null, null, 0);

        while(true) {
            state = queue.poll();
            if (state.cur.isVictory()) 
                break;

            fillQueue(state);
        }

        System.out.printf("a: %d\tb: %d\n", a, b);
        System.out.printf("Victory: %b.\n", state.cur.isVictory());
        System.out.printf("Queue size: %d\n", queue.size());
        System.out.printf("Done within %d steps.\n", state.steps);
    }

}