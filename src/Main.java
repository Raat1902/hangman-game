import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final List<String> WORD_BANK = List.of(
            "APPLE", "BANANA", "ORANGE", "PEACH", "GRAPE", "LEMON", "MANGO", "CHERRY", "KIWI"
    );

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        String secretWord = WORD_BANK.get(random.nextInt(WORD_BANK.size()));
        int lives = 6;

        Set<Character> correctGuesses = new HashSet<>();
        Set<Character> wrongGuesses = new HashSet<>();

        System.out.println("====== HANGMAN ======");
        System.out.println("Guess the word! You have " + lives + " lives.");

        while (lives > 0 && !isWordGuessed(secretWord, correctGuesses)) {
            System.out.println();
            System.out.print("Word: ");
            displayWord(secretWord, correctGuesses);

            System.out.print("Wrong guesses: ");
            printSetInOrder(wrongGuesses);
            System.out.println();

            System.out.println("Lives remaining: " + lives);

            Character guess = readSingleLetter(scanner, "Enter a letter: ");
            if (guess == null) {
                System.out.println("\nGoodbye!");
                return;
            }

            if (correctGuesses.contains(guess) || wrongGuesses.contains(guess)) {
                System.out.println("You already guessed that letter! Try again.");
                continue;
            }

            if (secretWord.indexOf(guess) >= 0) {
                System.out.println("Good guess!");
                correctGuesses.add(guess);
            } else {
                System.out.println("Wrong guess!");
                wrongGuesses.add(guess);
                lives--;
            }
        }

        System.out.println();
        if (isWordGuessed(secretWord, correctGuesses)) {
            System.out.println("Congratulations! You guessed the word: " + secretWord);
        } else {
            System.out.println("Game over! The word was: " + secretWord);
        }

        scanner.close();
    }

    private static void displayWord(String word, Set<Character> correctGuesses) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (correctGuesses.contains(c)) {
                System.out.print(c + " ");
            } else {
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    private static boolean isWordGuessed(String word, Set<Character> correctGuesses) {
        for (int i = 0; i < word.length(); i++) {
            if (!correctGuesses.contains(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Character readSingleLetter(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) return null;

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Please type a letter.");
                continue;
            }

            char c = Character.toUpperCase(line.charAt(0));
            if (!Character.isLetter(c)) {
                System.out.println("That is not a letter. Try again.");
                continue;
            }

            return c;
        }
    }

    private static void printSetInOrder(Set<Character> set) {
        set.stream().sorted().forEach(c -> System.out.print(c + " "));
    }
}
