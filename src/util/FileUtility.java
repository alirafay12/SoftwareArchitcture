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

    public static void readFieldEventCompetitors() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/FieldEventCompetitors.csv"))) {
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
                    FieldEvent fieldEvent = FieldEvent.valueOf(data[7].trim());

                    int[] scores = new int[data.length - 8];
                    int counter = 8;
                    for (int i = 0; i < scores.length; i++) {
                        scores[i] = Integer.parseInt(data[counter].trim());
                        counter++;
                    }

                    FieldEventCompetitor competitor = new FieldEventCompetitor(competitorNumber, name, email,country, dateOfBirth, category, level, scores, fieldEvent);
                    CompetitorList.addCompetitor(competitor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateCsvFile(List<Competitor> competitors) {
        try {
            BufferedWriter trackWriter = new BufferedWriter(new FileWriter("src/TrackEventCompetitors.csv"));
            BufferedWriter fieldWriter = new BufferedWriter(new FileWriter("src/FieldEventCompetitors.csv"));
            for (Competitor competitor : competitors) {
                StringBuilder csvLine = new StringBuilder();

                csvLine.append(competitor.getCompetitorNumber()).append(",");
                csvLine.append(competitor.getName()).append(",");
                csvLine.append(competitor.getEmail()).append(",");
                csvLine.append(competitor.getCountry()).append(",");
                csvLine.append(competitor.getDateOfBirth()).append(",");
                csvLine.append(competitor.getCategory()).append(",");
                csvLine.append(competitor.getLevel()).append(",");
                if (competitor instanceof TrackEventCompetitor) {
                    csvLine.append(((TrackEventCompetitor) competitor).getDistance()).append(",");
                } else if (competitor instanceof FieldEventCompetitor) {
                    csvLine.append(((FieldEventCompetitor) competitor).getFieldEvent()).append(",");
                }

                for (Integer score : competitor.getScores()) {
                    csvLine.append(score).append(",");
                }

                // Remove the trailing comma and write the line to the file
                csvLine.deleteCharAt(csvLine.length() - 1);  // Remove the last comma
                if (competitor instanceof TrackEventCompetitor) {
                    trackWriter.write(csvLine.toString());
                    trackWriter.newLine();  // Move to the next line
                } else {
                    fieldWriter.write(csvLine.toString());
                    fieldWriter.newLine();  // Move to the next line
                }
            }
            trackWriter.close();
            fieldWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
