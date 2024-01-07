package util;

import java.io.*;

public class CompetitorNumberGenerator {

    public static int generateNextCompetitorNumber() {
        int generatedNumber = readNextCompetitorNumberFromFile();
        updateNextCompetitorNumberInFile(generatedNumber + 1);
        return generatedNumber;
    }

    private static int readNextCompetitorNumberFromFile() {
        int number = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/index.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                number = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    private static void updateNextCompetitorNumberInFile(int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/index.txt"))) {
            writer.write(Integer.toString(number));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

