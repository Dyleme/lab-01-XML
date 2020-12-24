package com.company;

import javax.swing.*;

public class WindowModel {
    private DefaultListModel<SubjectMark> sourceModel = new DefaultListModel<>();
    private DefaultListModel<String> resultModel = new DefaultListModel<>();

    public WindowModel() {
        super();
        sourceModel.addElement(new SubjectMark(1,"fsdjkh","jkdfh",10));
        sourceModel.addElement(new SubjectMark(1,"fsdjkh","jkdfh",10));
        sourceModel.addElement(new SubjectMark(1,"fsdjkh","jkdfh",10));
    }

    public DefaultListModel<SubjectMark> getSourceModel() {
        return sourceModel;
    }

    public void setSourceModel(DefaultListModel<SubjectMark> sourceModel) {
        this.sourceModel = sourceModel;
    }

    public DefaultListModel<String> getResultModel() {
        return resultModel;
    }

    public void setResultModel(DefaultListModel<String> resultModel) {
        this.resultModel = resultModel;
    }
}
