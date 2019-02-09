package algojava;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        
        int N = 3;

        Game game = new Game(N);
        game.setRandom(100);
        System.out.printf("%s", game);

        BoardMatrix matrix = game.getMatrix();
        Solver solver = new Solver(matrix);
        solver.solve();

        for(int a = 2; a < 5; a++) {
            for (int b = 1; b < 3; b++) {
                solver.setPriorityParams(a, b);
                solver.solve();
            }
        }
    }
}
