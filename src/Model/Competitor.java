package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Competitor {
    private int competitorNumber;
    private String name;
    private String email;
    private  String country;
    private String dateOfBirth;
    private String category;
    private String level;
    private int[] scores;

    public Competitor(int competitorNumber, String name, String email,String country, String dateOfBirth, String category, String level, int[] scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.email = email;
        this.country=country;
        this.dateOfBirth = dateOfBirth;
        this.category = category;
        this.level = level;
        this.scores = scores;
    }

    public int getOverallScore() {
        if (scores.length < 3) {
            return 0;
        }

        int[] sortedScores = Arrays.copyOf(scores, scores.length);
        Arrays.sort(sortedScores);

        int sum = 0;
        for (int i = 1; i < sortedScores.length - 1; i++) {
            sum += sortedScores[i];
        }

        int average = sum / (sortedScores.length - 2);

        return average;
    }
