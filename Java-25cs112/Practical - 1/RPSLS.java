import java.util.Random;
import java.util.Scanner;

enum Move {
    ROCK, PAPER, SCISSORS, LIZARD, SPOCK
}

public class RPSLS {
    public static int winner(Move a, Move b) {
        if (a == b) {
            return 0;
        }

        boolean aWins = switch (a) {
            case ROCK -> (b == Move.SCISSORS || b == Move.LIZARD);
            case PAPER -> (b == Move.ROCK || b == Move.SPOCK);
            case SCISSORS -> (b == Move.PAPER || b == Move.LIZARD);
            case LIZARD -> (b == Move.SPOCK || b == Move.PAPER);
            case SPOCK -> (b == Move.SCISSORS || b == Move.ROCK);
        };

        return aWins ? 1 : -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        Move[] moves = Move.values();

        int userScore = 0;
        int compScore = 0;

        for (int round = 1; round <= 5; round++) {
            System.out.print("Round " + round + " - Enter move (ROCK, PAPER, SCISSORS, LIZARD, SPOCK): ");
            String input = sc.next().toUpperCase();

            Move playerMove = Move.valueOf(input);
            Move compMove = moves[rand.nextInt(moves.length)];

            int result = winner(playerMove, compMove);

            String roundWinner;
            if (result == 1) {
                roundWinner = "You";
                userScore++;
            } else if (result == -1) {
                roundWinner = "Computer";
                compScore++;
            } else {
                roundWinner = "Tie";
            }

            System.out.println("Round " + round + " Summary: You played " + playerMove + ", Computer played " + compMove + " -> Winner: " + roundWinner + "\n");
        }

        if (userScore > compScore) {
            System.out.println("You win " + userScore + "–" + compScore);
        } else if (compScore > userScore) {
            System.out.println("Computer wins " + compScore + "–" + userScore);
        } else {
            System.out.println("Tie " + userScore + "–" + compScore);
        }

        sc.close();
    }
}