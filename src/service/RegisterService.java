package service;

import model.Competitor;
import model.CompetitorList;
import util.CompetitorNumberGenerator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegisterService {

    public static String registerCompetitor(Competitor competitor, boolean isUpdate) {
        if (isAnyFieldOmitted(competitor)) {
            return "Error: Please fill in all the required fields.";
        }

        if (!isUpdate && isCompetitorAlreadyRegistered(competitor)) {
            return "Error: A competitor with the same email and category already exists.";
        }

        if (!isUpdate && isCompetitorWithEmailAndDifferentCategory(competitor)) {
            int newCompetitorNumber = CompetitorNumberGenerator.generateNextCompetitorNumber();
            competitor.setCompetitorNumber(newCompetitorNumber);
            return "Success: model.Competitor registered with a new competitor number: " + newCompetitorNumber;
        }

        if (!isAgeIncompatibleWithLevel(competitor)) {
            return "Error: Age is incompatible with the selected level. Please resubmit the form for a different level.";
        }

        return "Success: model.Competitor registered successfully.";
    }

    private static boolean isAnyFieldOmitted(Competitor competitor) {
        return competitor.getName() == null || competitor.getName().isEmpty()
                || competitor.getEmail() == null || competitor.getEmail().isEmpty()|| competitor.getCountry().isEmpty()
                || competitor.getDateOfBirth() == null
                || competitor.getCategory() == null || competitor.getLevel() == null;
    }
