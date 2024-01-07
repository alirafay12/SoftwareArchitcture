package model;

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
    public String getFullDetails() {
        StringBuilder details = new StringBuilder();
        details.append("model.Competitor number ").append(competitorNumber).append(", name ").append(name).append(".\n");
        details.append("Email: ").append(email).append("\n");
        details.append("Country: ").append(country).append("\n");
        details.append("Date of Birth: ").append(dateOfBirth).append("\n");
        details.append(name).append(" is a ").append(category).append(" in the ").append(level).append(" category.\n");
        details.append("Received these scores: ");

        for (int i = 0; i < scores.length; i++) {
            details.append(scores[i]);
            if (i < scores.length - 1) {
                details.append(", ");
            }
        }

        details.append("\nThis gives them an overall score of ").append(getOverallScore()).append(".");

        return details.toString();
    }

    public String getShortDetails() {
        return "CN " + competitorNumber + " (" + getInitials() + ") has an overall score of " + getOverallScore();
    }

    private String getInitials() {
        StringBuilder initials = new StringBuilder();
        String[] nameParts = name.split(" ");

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }

        return initials.toString().toUpperCase();
    }

    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getCountry() {
        return country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCategory() {
        return category;
    }

    public String getLevel() {
        return level;
    }

    public void setCompetitorNumber(int competitorNumber) {
        this.competitorNumber = competitorNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Integer> getScores() {
        List<Integer> sc = new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {
            sc.add(scores[i]);
        }
        return sc;
    }

    public void setScores(List<Integer> scores) {
        for (int i = 0; i < scores.size(); i++) {
            this.scores[i] = scores.get(i);
        }
    }
}
