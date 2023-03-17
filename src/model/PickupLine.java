package model;

import service.ProfanityBase;

import java.util.Arrays;

public class PickupLine {

    private String line;

    public PickupLine(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    @Override
    public String toString() {
        return line;
    }

    public int getNumberOfWords() {
        String[] lineWords = line.split(" ");
        return lineWords.length;
    }

    public boolean isQuestionType() {
        return line.contains("?");
    }

    public boolean isBadLanguage() {
        String[] lineWords = line.split(" ");

        return Arrays.stream(ProfanityBase.SwearWords)
                .anyMatch(badWord -> Arrays.asList(lineWords).contains(badWord));
    }

}
