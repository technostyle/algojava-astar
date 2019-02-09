package algojava;

import java.util.Comparator;

public class BoardStateComparator implements Comparator<BoardState> {

    private int a, b;
    // private int count;

    // public int getComparationsCount() {
    //     return count;
    // }

    // public void reset() {
    //     count = 0;
    // }

    // public EventComparator() {
    //     super();
    // }

    public BoardStateComparator(int a, int b) {
        this.a = a;
        this.b = b;
    }


    @Override
    public int compare(BoardState o1, BoardState o2) {
        int c1 = a * o1.manhattan() + b * o1.steps();
        int c2 = a * o2.manhattan() + b * o2.steps();

        if (c1 < c2)  return -1;
        else if (c1 > c2) return 1;
        else return 0;
    }
}
