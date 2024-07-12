import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static char[][] board = new char[3][3];
    static Random random = new Random();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {
            try {
                System.out.println("Let's play! Choose an option:");
                System.out.println("1. Play one round");
                System.out.println("2. Play 3 rounds");

                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        play();
                        break;
                    case 2:
                        int playerWins = 0;
                        int computerWins = 0;
                        for (int i = 0; i < 3; i++) {
                            System.out.println("Round " + (i + 1) + " :");
                            char winner = play();
                            if (winner == 'X') {
                                playerWins++;
                            } else if (winner == 'O') {
                                computerWins++;
                            }
                        }
                        System.out.println("Player wins " + playerWins + " rounds.");
                        System.out.println("Computer wins " + computerWins + " rounds.");
                        if (playerWins > computerWins) {
                            System.out.println("Player wins the game!");
                        } else if (playerWins < computerWins) {
                            System.out.println("Computer wins the game!");
                        } else {
                            System.out.println("The game is a tie!");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 2.");
                sc.next();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Hey,Do you want to play again? (Yes or No)");
            String response = sc.next();
            playAgain = response.equalsIgnoreCase("yes");
        }
    }

    public static void initBoard() {
        char position = '1';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = position++;
            }
        }
    }

    public static void displayBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) System.out.print(" | ");
            }
            System.out.println();
            if (i < 2) System.out.println("---------");
        }
    }

    public static void playerMove() {
        int position = -1;
        while (true) {
            try {
                System.out.println("Please enter your move (1-9): ");
                position = sc.nextInt();
                if (position < 1 || position > 9) {
                    System.out.println("Invalid position. Please choose a number between 1 and 9.");
                } else if (!isPositionAvailable(position)) {
                    System.out.println("Position already taken. Please choose another number.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                sc.next();
            }
        }
        placeMove(position, 'X');
    }

    public static void computerMove() {
        int position;
        while (true) {
            position = random.nextInt(9) + 1;
            if (isPositionAvailable(position)) {
                break;
            }
        }
        placeMove(position, 'O');
        System.out.println("Computer placed 'O' at position " + position);
    }

    public static boolean isPositionAvailable(int position) {
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        return board[row][col] != 'X' && board[row][col] != 'O';
    }

    public static void placeMove(int position, char symbol) {
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        board[row][col] = symbol;
    }

    public static char checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return ' ';
    }

    public static boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 'X' && board[i][j] != 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public static char play() {
        initBoard();
        displayBoard();
        char winner = ' ';
        boolean playerTurn = true;
        while (winner == ' ' && !isFull()) {
            if (playerTurn) {
                playerMove();
            } else {
                computerMove();
            }
            displayBoard();
            winner = checkWin();
            playerTurn = !playerTurn;
        }
        if (winner == 'X') {
            System.out.println("Player wins!");
        } else if (winner == 'O') {
            System.out.println("Computer wins!");
        } else {
            System.out.println("No one win!");
        }
        return winner;
    }
}