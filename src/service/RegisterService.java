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

    private static boolean isCompetitorAlreadyRegistered(Competitor competitor) {
        return CompetitorList.getAllCompetitors().stream()
                .anyMatch(existingCompetitor -> existingCompetitor.getEmail().equals(competitor.getEmail())
                        && existingCompetitor.getCategory().equals(competitor.getCategory()));
    }

    private static boolean isCompetitorWithEmailAndDifferentCategory(Competitor competitor) {
        return CompetitorList.getAllCompetitors().stream()
                .anyMatch(existingCompetitor -> existingCompetitor.getEmail().equals(competitor.getEmail())
                        && !existingCompetitor.getCategory().equals(competitor.getCategory()));
    }


    private static boolean isAgeIncompatibleWithLevel(Competitor competitor) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern as needed
        String dateOfBirthString = competitor.getDateOfBirth();

        try {
            LocalDate birthDate = LocalDate.parse(dateOfBirthString, dateFormatter);

            // Calculate the age of the competitor based on the date of birth
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthDate, currentDate).getYears();

            // Check the level and compare with age restrictions
            if (competitor.getLevel().equals("1") && age >= 18) {
                return true; // Incompatible age for Level 1
            } else if (competitor.getLevel().equals("2") && (age < 18 || age >= 30)) {
                return true; // Incompatible age for Level 2
            } else if (competitor.getLevel().equals("3") && age < 30) {
                return true; // Incompatible age for Level 3
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid Date");
        }

        return false; // Age is compatible with the selected level
    }
}
