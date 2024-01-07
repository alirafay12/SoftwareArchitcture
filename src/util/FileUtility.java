package util;

import model.*;

import java.io.*;
import java.util.List;

public class FileUtility {
    public static void readTrackEventCompetitors() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/TrackEventCompetitors.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    int competitorNumber = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String email = data[2].trim();
                    String country = data[3].trim();
                    String dateOfBirth = data[4].trim();
                    String category = data[5].trim();
                    String level = data[6].trim();
                    double distance = Double.parseDouble(data[7].trim());

                    int[] scores = new int[data.length - 8];

                    int counter = 8;
                    for (int i = 0; i < scores.length; i++) {
                        scores[i] = Integer.parseInt(data[counter].trim());
                        counter++;
                    }

                    TrackEventCompetitor competitor = new TrackEventCompetitor(competitorNumber, name, email,country, dateOfBirth, category, level, scores, distance);
                    CompetitorList.addCompetitor(competitor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

