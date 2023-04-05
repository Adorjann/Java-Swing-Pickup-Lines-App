package view;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import model.PickupLine;
import service.filterator.PickupLineFilterator;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ca.odell.glazedlists.swing.GlazedListsSwing.eventTableModelWithThreadProxyList;

public class PickupLineListView {

    private final EventList<PickupLine> pickupLinesEventLis = new BasicEventList<>();

    private final Comparator<PickupLine> lineComparator = Comparator.comparing(PickupLine::getNumberOfWords);

    public PickupLineListView(Collection<PickupLine> pickupLines) {
        pickupLinesEventLis.addAll(pickupLines);
    }

    private AdvancedTableModel<PickupLine> getTableModel(JTextField filterTextField, SortedList<PickupLine> sortedLines) {

        PickupLineFilterator filterator = new PickupLineFilterator();
        MatcherEditor<PickupLine> matcherEditor = new TextComponentMatcherEditor<>(filterTextField, filterator);
        FilterList<PickupLine> textFilteredLines = new FilterList<>(sortedLines, matcherEditor);
        return eventTableModelWithThreadProxyList(textFilteredLines, new PickupLineTableFormat());
    }

    private SortedList<PickupLine> getSortedList(boolean[] params) {
        return new SortedList<>(new BasicEventList<>(
                pickupLinesEventLis.stream()
                        .filter(p -> !params[0] || !p.isBadLanguage())
                        .filter(p -> !params[1] || !p.isQuestionType())
                        .collect(Collectors.toList())
        ), lineComparator);
    }

    public void display() {
        boolean[] sortListFilterParams = new boolean[]{false, false};
        final SortedList<PickupLine>[] sortedLines = new SortedList[]{getSortedList(sortListFilterParams)};

        JTextField filterTextField = new JTextField(20);
        JTable pickupLineJTable = new JTable(getTableModel(filterTextField, sortedLines[0]));
        JScrollPane pickupLineTableScrollPane = new JScrollPane(pickupLineJTable);

        JPanel mainPanel = new JPanel(new BorderLayout());
        //north

        TabbedToolbar tabbedToolbar = new TabbedToolbar(filterTextField);

        tabbedToolbar.setProfanityListener(value -> {
            sortListFilterParams[0] = value;
            sortedLines[0] = getSortedList(sortListFilterParams);
            pickupLineJTable.setModel(getTableModel(filterTextField, sortedLines[0]));
        });

        tabbedToolbar.setQuestionableLineListener(value -> {
            sortListFilterParams[1] = value;
            sortedLines[0] = getSortedList(sortListFilterParams);
            pickupLineJTable.setModel(getTableModel(filterTextField, sortedLines[0]));
        });

        tabbedToolbar.setSortListener(sortButton -> {
            String ascSortButtonText = "/\\";
            String descSortButtonText = "\\/";
            if (sortedLines[0].getComparator().equals(lineComparator)) {
                sortedLines[0].setComparator(lineComparator.reversed());
                sortButton.setText(descSortButtonText);

            } else {
                sortedLines[0].setComparator(lineComparator);
                sortButton.setText(ascSortButtonText);
            }
        });

        mainPanel.add(BorderLayout.NORTH, tabbedToolbar);


        //display lines

        mainPanel.add(BorderLayout.CENTER, pickupLineTableScrollPane);

        // create a frame with that panel
        JFrame frame = new JFrame("PickUp Lines");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 680);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }
}
