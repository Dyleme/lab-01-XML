package com.company;

public class SubjectMark {


    private int number;
    private String name;
    private int semNumber;
    private String subject;
    private int mark;

    public SubjectMark(int number, String name, String subject, int mark) {
        super();
        this.number = number;
        this.name = name;
        this.subject = subject;
        this.mark = mark;
        semNumber = 0;
    }

    public SubjectMark(int number, String name, int semNumber,String subject, int mark) {
        super();
        this.number = number;
        this.name = name;
        this.subject = subject;
        this.mark = mark;
        this.semNumber = semNumber;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getSemNumber() {
        return semNumber;
    }

    public String getSubject() {
        return subject;
    }

    public int getMark() {
        return mark;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSemNumber(int semNumber) {
        this.semNumber = semNumber;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return number + " " + name + " "+semNumber +" " + subject + " " + mark;
    }
}
