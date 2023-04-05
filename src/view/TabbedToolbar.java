package view;

import view.listeners.ProfanityListener;
import view.listeners.QuestionableLineListener;
import view.listeners.SortListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TabbedToolbar extends JTabbedPane {

    private ProfanityListener profanityListener;
    private QuestionableLineListener questionableLineListener;
    private SortListener sortListener;

    public TabbedToolbar(JTextField filterEditTextField) {
        //filter
        JPanel filterCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterCard.add(new JLabel("Enter Word/s:"));
        filterCard.add(filterEditTextField);
        addTab("filter", filterCard);

        //show
        JPanel showCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox questionCheckBox = new JCheckBox("Don't show 'question-form' lines");
        questionCheckBox.setFocusable(false);
        questionCheckBox.addActionListener(getQuestionCheckBoxActionListener());
        JCheckBox badWordsCheckBox = new JCheckBox("Don't show 'bad-language' lines");
        badWordsCheckBox.setFocusable(false);
        badWordsCheckBox.addActionListener(getBadWordsCheckBoxActionListener());

        showCard.add(questionCheckBox);
        showCard.add(badWordsCheckBox);
        addTab("show", showCard);

        //sort
        JPanel sortCard = new JPanel(new FlowLayout(FlowLayout.LEFT));

        sortCard.add(new JLabel("Sort by lenght:"));
        String ascSortButtonText = "/\\";
        JButton sortButton = new JButton(ascSortButtonText);
        sortButton.setFocusable(false);
        sortButton.addActionListener(e -> sortListener.sortButtonPressed(sortButton));
        sortCard.add(sortButton);
        addTab("sort", sortCard);
    }

    private ActionListener getQuestionCheckBoxActionListener() {
        return actionEvent -> {
            AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            questionableLineListener.questionCheckBoxListener(selected);
        };
    }

    private ActionListener getBadWordsCheckBoxActionListener() {
        return actionEvent -> {
            AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            profanityListener.profanityCheckboxEvent(selected);
        };
    }


    public void setProfanityListener(ProfanityListener profanityListener) {
        this.profanityListener = profanityListener;
    }

    public void setQuestionableLineListener(QuestionableLineListener questionableLineListener) {
        this.questionableLineListener = questionableLineListener;
    }

    public void setSortListener(SortListener sortListener) {
        this.sortListener = sortListener;
    }
}
