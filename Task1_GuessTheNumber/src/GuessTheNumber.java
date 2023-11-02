import java.util.Scanner;

public class GuessTheNumber {
    private static final int MIN_RANDOM_NUMBER = 1;
    private static final int MAX_RANDOM_NUMBER = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int randomNumber = (int) (Math.random() * (MAX_RANDOM_NUMBER - MIN_RANDOM_NUMBER + 1) + MIN_RANDOM_NUMBER);
        System.out.printf("A random number has been generated between %d and %d\n", MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
        System.out.println("Try too guess it!");
        System.out.println("You have unlimited tries");

        int guess = -1;

        while (guess != randomNumber) {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();

            if (guess < randomNumber) {
                System.out.println("Your guess is too low!");
            } else if (guess > randomNumber) {
                System.out.println("Your guess is too high!");
            } else {
                System.out.println("Congrats! You guessed the number correctly!");
            }
        }

        System.out.println("Thank you for playing!");
    }
}
