package util;

import java.io.*;

public class CompetitorNumberGenerator {

    public static int generateNextCompetitorNumber() {
        int generatedNumber = readNextCompetitorNumberFromFile();
        updateNextCompetitorNumberInFile(generatedNumber + 1);
        return generatedNumber;
    }
