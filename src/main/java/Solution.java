import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
    static enum Piece {
        CROSS,//X
        DONOUGHTS; //O
    }

    static class Cell {
        Piece piece;

        public Piece getPiece() {
            return piece;
        }

        public void putPiece(Piece piece) {
            this.piece = piece;
        }
    }

    static class Board {
        Cell[][] cells;

        Board() {
            // 3*3
            cells = new Cell[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    cells[i][j] = new Cell();
                }
            }
        }

        public Cell[][] getCells() {
            return cells;
        }
    }

    static class Player {
        int id;
        String name;
        Piece piece;

        Player(int id, String name, Piece piece) {
            this.id = id;
            this.name = name;
            this.piece = piece;
        }

        public Piece getPiece() {
            return piece;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        Player p1 = new Player(1, "Gk", Piece.CROSS);
        Player p2 = new Player(2, "Rohit", Piece.DONOUGHTS);

        Deque<Player> q = new LinkedList<>();
        q.add(p1);
        q.add(p2);
        startGame(board, q);
    }

    private static void startGame(Board board, Deque<Player> q) {
        Player winner = null;
        int count = 0;

        while (winner == null && count < 9) {
            Player cur = q.poll();
            int x = new Random().nextInt(3);
            int y = new Random().nextInt(3);
            Cell[][] cells = board.getCells();
            Cell cell = cells[x][y];
            if (cell.getPiece() != null) {
                q.addFirst(cur);
                continue;
            }
            cell.putPiece(cur.getPiece());

            count++;
            System.out.println("Player " + cur.getName() + " putting " + cur.getPiece().name() + " at (" + x + "," + y + ")");
            //Winning condition
            winner = checkWinner(cells, cur);
            q.addLast(cur);

        }
        if (winner == null) {
            System.out.println("DRAW");
        }
        Cell[][] cells = board.getCells();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j].getPiece() != null) {
                    if (cells[i][j].getPiece().equals(Piece.CROSS)) {
                        System.out.print("X");
                    } else {
                        System.out.print("O");
                    }
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        if (winner != null) {
            System.out.println("Winner is: " + winner.getName());
        }
    }

    private static Player checkWinner(Cell[][] cells, Player cur) {
        boolean flag = true;
        Player winner = null;

        for (int i = 0; i < 3; i++) {
            flag = true;
            for (int j = 0; j < 3; j++) {
                if (cells[i][j].getPiece() == null || !(cells[i][j].getPiece().equals(cur.getPiece()))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return winner = cur;
            }
        }
        for (int j = 0; j < 3; j++) {
            flag = true;
            for (int i = 0; i < 3; i++) {
                if (cells[i][j].getPiece() == null || !cells[i][j].getPiece().equals(cur.getPiece())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return winner = cur;
            }
        }
        flag = true;
        for (int i = 0; i < 3; i++) {
            if (cells[i][i].getPiece() == null || !cells[i][i].getPiece().equals(cur.getPiece())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            return winner = cur;
        }

        flag = true;
        if (cells[0][2].getPiece() != null && cells[0][2].getPiece().equals(cur.getPiece())
                && cells[1][1].getPiece() != null && cells[1][1].getPiece().equals(cur.getPiece())
                && cells[2][0].getPiece() != null && cells[2][0].getPiece().equals(cur.getPiece())) {
            return winner = cur;
        }
        return null;
    }
}