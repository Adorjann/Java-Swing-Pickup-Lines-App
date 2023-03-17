package service.comparators;

import model.PickupLine;

import java.util.Comparator;

public class LineLengthComparator implements Comparator<PickupLine> {
    @Override
    public int compare(PickupLine line1, PickupLine line2) {

        return line1.getNumberOfWords() - line2.getNumberOfWords();
    }
}
