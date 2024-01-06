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
