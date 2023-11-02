import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int randomNumber = (int) (Math.random() * 100) + 1;
        System.out.println("A random number has been generated between 1 and 100");
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
