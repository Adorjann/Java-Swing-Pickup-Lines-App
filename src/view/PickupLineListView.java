package view;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import model.PickupLine;
import service.filterator.PickupLineFilterator;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Comparator;

import static ca.odell.glazedlists.swing.GlazedListsSwing.eventTableModelWithThreadProxyList;

public class PickupLineListView {

    private EventList<PickupLine> pickupLinesEventLis = new BasicEventList<>();

    private Comparator<PickupLine> lineComparator = Comparator.comparing(PickupLine::getNumberOfWords);

    public PickupLineListView(Collection<PickupLine> issues) {
        pickupLinesEventLis.addAll(issues);
    }

    public void display() {
        SortedList<PickupLine> sortedLines = new SortedList<>(pickupLinesEventLis, lineComparator);

        JTextField filterEdit = new JTextField(10);
        PickupLineFilterator filterator = new PickupLineFilterator();
        MatcherEditor<PickupLine> matcherEditor = new TextComponentMatcherEditor<>(filterEdit, filterator);

        FilterList<PickupLine> textFilteredLines = new FilterList<>(sortedLines, matcherEditor);

        JPanel panel = new JPanel(new GridBagLayout());
        AdvancedTableModel<PickupLine> tableModel = eventTableModelWithThreadProxyList(textFilteredLines, new PickupLineTableFormat());

        JTable pickupLineJTable = new JTable(tableModel);
        JScrollPane pickupLineTableScrollPane = new JScrollPane(pickupLineJTable);

        panel.add(new JLabel("Filter: "), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(filterEdit, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        panel.add(pickupLineTableScrollPane, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

        // create a frame with that panel
        JFrame frame = new JFrame("PickUp Lines");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(540, 380);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
