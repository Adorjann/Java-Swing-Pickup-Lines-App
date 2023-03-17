package view;

import ca.odell.glazedlists.gui.TableFormat;
import model.PickupLine;

public class PickupLineTableFormat implements TableFormat<PickupLine> {
    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int i) {
        return "The Lines";
    }

    @Override
    public Object getColumnValue(PickupLine pickupLine, int i) {
        return pickupLine.getLine();
    }
}
