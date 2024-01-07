package model;

import model.Competitor;

public class TrackEventCompetitor extends Competitor {
    private double distance;

    public TrackEventCompetitor(int competitorNumber, String name, String email,String country, String dateOfBirth, String category, String level, int[] scores, double distance) {
        super(competitorNumber, name, email,country, dateOfBirth, category, level, scores);
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }