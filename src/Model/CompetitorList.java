package model;

import util.FileUtility;

import java.util.ArrayList;

public class CompetitorList {
    private static ArrayList<Competitor> competitors = new ArrayList<>();

    public static void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }

    public static Competitor findCompetitor(int competitorNumber) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;
    }

    public static ArrayList<Competitor> getAllCompetitors() {
        return competitors;
    }

    public static double getHighestOverallScore() {
        double highestScore = Double.MIN_VALUE;
        for (Competitor competitor : competitors) {
            double overallScore = competitor.getOverallScore();
            if (overallScore > highestScore) {
                highestScore = overallScore;
            }
        }
        return highestScore;
    }

    public static void deleteCompetitor(Competitor competitor) {
        competitors.remove(competitor);
        FileUtility.updateCsvFile(CompetitorList.getAllCompetitors());
    }

    public static void updateCompetitor(Competitor competitor) {
        for (Competitor c : competitors) {
            if (c.getCompetitorNumber() == competitor.getCompetitorNumber()) {
                c.setName(competitor.getName());
                c.setEmail(competitor.getEmail());
                c.setCountry(competitor.getCountry());
                c.setDateOfBirth(competitor.getDateOfBirth());
                c.setCategory(competitor.getCategory());
                c.setLevel(competitor.getLevel());
                break;
            }
        }
    }
}
