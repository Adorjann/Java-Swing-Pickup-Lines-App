package model;

import service.ProfanityBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PickupLine {

    private final String line;

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
        List<String> lineWords = Arrays.stream(line.split(" "))
                                       .map(String::toLowerCase)
                                       .collect(Collectors.toList());

        return Arrays.stream(ProfanityBase.SwearWords)
                .anyMatch(badWord -> lineWords.contains(badWord)
                        || lineWords.contains(badWord+".")
                        || lineWords.contains(badWord+"?")
                        || lineWords.contains(badWord+"!")
                        || lineWords.contains(badWord+"]"));
    }

}
