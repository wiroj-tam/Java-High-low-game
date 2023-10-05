import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class App {

  public static int getRandomNumber(int min, int max) {
    Random rn = new Random();
    return rn.nextInt(max - min + 1) + min;
  }

  public static String getAnswer(int currentNumber, int nextNumber) {
    if (nextNumber < currentNumber) {
      return "low";
    } else {
      return "high";
    }
  }

  public static void saveScore(String playerName, int score, String filename) {
    String content = "";

    // Read the file
    try {
      
      File file = new File(filename);

      // Check file is empty or not ???
      if (file.length() == 0) {
        FileWriter fw = new FileWriter(filename);
        fw.write(playerName + " " + score + "\n");
        fw.close();
        return;
      }

      // File is not empty
      boolean isNewPlayer = true; // Check is this player new ?
      String[] player_data = new String[2]; // Store player'name and player's score
      BufferedReader br = new BufferedReader(new FileReader(filename));

      for (String line = br.readLine(); line != null; line = br.readLine()) {
        player_data = line.split(" ", 0);

        if (player_data[0].equals(playerName)) { // already has this player name
          isNewPlayer = false;
          System.out.println("already has this player name");
          if (score > Integer.parseInt(player_data[1])) { // Update the score if the new score is higher
            content += playerName + " " + score + "\n";
          } else { // If not, the data stays the same.
            content += line + "\n";
          }
        } else {
          content += line + "\n";
        }

      }
      
      br.close();
      // Write the file.
      if (isNewPlayer) { // Is this a new player ?
        content += playerName + " " + score + "\n";
      }
      FileWriter fw = new FileWriter(filename);
      fw.write(content);
      fw.close();
      
    } catch (IOException e) {
      System.out.println(e);
    }

  }

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in); // Create a Scanner object
    int currentNumber;
    int nextNumber = getRandomNumber(1, 10);
    String answer;
    int score = 0;
    int lives = 3;

    System.out.print("Enter player's name: ");
    String playerName = sc.nextLine(); // Read user input
    System.out.println("Your player name is: " + playerName + " right?");
    Thread.sleep(1500);  
    System.out.println("********************");
    Thread.sleep(1500); 
    System.out.println("Let's start...");
    Thread.sleep(1500); 
    System.out.println("********************");
    Thread.sleep(1500); 

    // game
    while (true) {
      currentNumber = nextNumber;
      nextNumber = getRandomNumber(1, 10);

      if (currentNumber == nextNumber) {
        continue;
      }

      // Guess High or Low
      Thread.sleep(1500); 
      System.out.println(currentNumber);
      Thread.sleep(1500); 
      System.out.println("The next number is High or Low ?");
      System.out.print("Type your answer: ");
      answer = sc.nextLine().toLowerCase();

      // Check Answer
      if (answer.equals(getAnswer(currentNumber, nextNumber))) {
        System.out.println("\nCorrect!\n");
        score++;
      } else {
        lives--;
        System.out.println("\nWrong! you noob, Life point left: " + lives + "\n");
        System.out.println();
      }

      // Check Game Over
      if (lives <= 0) {
        Thread.sleep(2000); 
        System.out.println("***GAME OVER*** KAK MAk");
        saveScore(playerName, score, "filename.txt");
        break;
      }

    }
  }
}
