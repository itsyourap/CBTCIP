import java.util.Scanner;

public class GuessTheNumber {
    private static final int MIN_RANDOM_NUMBER = 1;
    private static final int MAX_RANDOM_NUMBER = 100;
    private static final int MAX_GUESSES = 10;
    private static int randomNumber;
    private static int roundNumber = 0;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        playGame();
        scanner.close();
    }

    private static void playGame(){
        roundNumber++;
        System.out.printf("Round %d\n", roundNumber);
        System.out.println("---------------------------------");

        renewRandomNumber();
        System.out.println("Try to guess it!");
        System.out.printf("You have %d tries\n", MAX_GUESSES);

        int guessCount = 1;

        while (guessCount <= MAX_GUESSES) {
            System.out.printf("[Guess %d] Enter your guess: ", guessCount);
            int guess = scanner.nextInt();

            if (guess < randomNumber) {
                System.out.println("Your guess is too low!");
            } else if (guess > randomNumber) {
                System.out.println("Your guess is too high!");
            } else {
                System.out.println("Congrats! You guessed the number correctly!");
                break;
            }
            if (guess > MAX_RANDOM_NUMBER || guess < MIN_RANDOM_NUMBER) {
                System.out.printf("Your guess must be between %d and %d\n", MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
            }

            guessCount++;
        }

        if (guessCount > MAX_GUESSES) {
            System.out.println("You ran out of guesses!");
            System.out.printf("The number was %d\n", randomNumber);
        }

        System.out.printf("You scored %d points out of %d\n", MAX_GUESSES - guessCount + 1, MAX_GUESSES);

        System.out.print("Do you want to play another round? [yes (y) / no (n)] [default: no] : ");
        String playAgain = scanner.next();
        System.out.println();
        if (playAgain.equalsIgnoreCase("yes") || playAgain.equalsIgnoreCase("y"))
            playGame();
        else
           System.out.println("Thanks for playing!");
    }

    private static void renewRandomNumber() {
        randomNumber = (int) (Math.random() * (MAX_RANDOM_NUMBER - MIN_RANDOM_NUMBER + 1)) + MIN_RANDOM_NUMBER;
        System.out.printf("A random number has been generated between %d and %d", MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
        System.out.println();
    }
}
