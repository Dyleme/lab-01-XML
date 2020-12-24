package com.company;

import javax.swing.*;
import java.awt.*;

public class WindowView extends JFrame {
    private WindowModel model;

    private JMenuItem openFileButton;
    private JMenuItem openXmlButton;
    private JMenuItem generateXmlButton;
    private JButton getAllSubjectsButton;


    private JButton getGoodLearnersButton;

    private JButton getBadStudentsButton;
    private JButton addElementButton;
    private JList sourceList;
    private JList resultList;


    public WindowView(WindowModel model) throws HeadlessException {
        super("Window");
        this.model = model;
        setSize(550, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        getAllSubjectsButton = new JButton("All subjects");
        getGoodLearnersButton = new JButton("Goof learners");
        getBadStudentsButton = new JButton("Bad learners");
        addElementButton = new JButton("Add");
        sourceList = new JList(model.getSourceModel());
        resultList = new JList(model.getResultModel());

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        openFileButton = new JMenuItem("Open file");
        openXmlButton = new JMenuItem("Open xml");
        generateXmlButton = new JMenuItem("Generate xml");

        file.add(openFileButton);
        file.add(openXmlButton);
        file.add(generateXmlButton);

        menuBar.add(file);
        this.setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        JPanel lists = new JPanel();
        lists.setLayout(new FlowLayout(FlowLayout.CENTER));
        lists.add(sourceList);
        lists.add(resultList);
        this.add(lists, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttons.add(getAllSubjectsButton);
        buttons.add(getGoodLearnersButton);
        buttons.add(getBadStudentsButton);
        buttons.add(addElementButton);
        this.add(buttons, BorderLayout.SOUTH);

    }

    public JMenuItem getOpenFileButton() {
        return openFileButton;
    }

    public JMenuItem getOpenXmlButton() {return openXmlButton; }

    public JMenuItem getGenerateXmlButton() {return generateXmlButton; }

    public JButton getGetAllSubjectsButton() {
        return getAllSubjectsButton;
    }

    public JButton getAddElementButton() {
        return addElementButton;
    }

    public JList getSourceList() {
        return sourceList;
    }

    public JList getResultList() {
        return resultList;
    }

    public JButton getGetBadStudentsButton() {
        return getBadStudentsButton;
    }


    public void setGetBadStudentsButton(JButton getBadStudentsButton) {
        this.getBadStudentsButton = getBadStudentsButton;
    }


    public void setSourceList(JList sourceList) {
        this.sourceList = sourceList;
    }

    public void setResultList(JList resultList) {
        this.resultList = resultList;
    }

    public JButton getGetGoodLearnersButton() {
        return getGoodLearnersButton;
    }

    public void setGetGoodLearnersButton(JButton getGoodLearnersButton) {
        this.getGoodLearnersButton = getGoodLearnersButton;
    }
}
