package service.filterator;

import ca.odell.glazedlists.TextFilterator;
import model.PickupLine;

import java.util.List;

public class PickupLineFilterator implements TextFilterator<PickupLine> {
    @Override
    public void getFilterStrings(List<String> list, PickupLine pickupLine) {
        list.add(pickupLine.getLine());
    }
}
