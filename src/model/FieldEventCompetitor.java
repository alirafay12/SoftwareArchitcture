package model;

import model.Competitor;

public class FieldEventCompetitor extends Competitor {
    private FieldEvent fieldEvent;

    public FieldEventCompetitor(int competitorNumber, String name, String email,String country, String dateOfBirth,
                                String category, String level, int[] scores, FieldEvent fieldEvent) {
        super(competitorNumber, name, email,country, dateOfBirth, category, level, scores);
        this.fieldEvent = fieldEvent;
    }

    public FieldEvent getFieldEvent() {
        return fieldEvent;
    }

    public void setFieldEvent(FieldEvent fieldEvent) {
        this.fieldEvent = fieldEvent;
    }
}
