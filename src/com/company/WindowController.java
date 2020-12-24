package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class WindowController {
    final private WindowModel model;
    final private WindowView view;


    public WindowController(WindowModel model, WindowView view) {
        this.model = model;
        this.view = view;

        view.setResultList(new JList<>(model.getResultModel()));
        view.setSourceList(new JList<>(model.getSourceModel()));

//        view.getResultList().setModel(model.getResultModel());
//        view.getSourceList().setModel(model.getSourceModel());

        view.getOpenFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("src");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getPath().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }
                });
                chooser.setDialogTitle("Choose file");
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = chooser.getSelectedFile();
                    model.getSourceModel().clear();
                    try {
                        Scanner scanner = new Scanner(chosenFile);
                        while (scanner.hasNextInt()) {
                            String name;
                            int mark;
                            int number;
                            int semNumber;
                            String subject;
                            try {
                                if (scanner.hasNextInt()) {
                                    number = scanner.nextInt();
                                } else {
                                    throw new Exception();
                                }
                                if (scanner.hasNext()) {
                                    name = scanner.next();
                                } else {
                                    throw new Exception();
                                }
                                if (scanner.hasNextInt()) {
                                    semNumber = scanner.nextInt();
                                } else {
                                    throw new Exception();
                                }
                                if (scanner.hasNext()) {
                                    subject = scanner.next();
                                } else {
                                    throw new Exception();
                                }
                                if (scanner.hasNextInt()) {
                                    mark = scanner.nextInt();
                                } else {
                                    throw new Exception();
                                }
                                model.getSourceModel().addElement(new SubjectMark(number,name,semNumber,subject,mark));
                            } catch (Exception exception) {
                                JOptionPane.showMessageDialog(null,"Wrong types");
                            }
                            model.getResultModel().clear();

                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Sorry cannot read file!");
                    }
                }
            }

        });

        view.getOpenXmlButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("src");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getPath().endsWith(".xml");
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }
                });
                chooser.setDialogTitle("Choose file");
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    model.getSourceModel().clear();
                    File selectedFile = chooser.getSelectedFile();
                    openXml(selectedFile);
                }
            }
        });

        view.getGenerateXmlButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser("src");
                    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    chooser.setDialogTitle("Choose where you want to save file");
                    chooser.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            return f.isDirectory() || f.getPath().endsWith(".xml");
                        }

                        @Override
                        public String getDescription() {
                            return null;
                        }
                    });
                    int result = chooser.showSaveDialog(null);
                    File file = chooser.getSelectedFile();
                    if (result == JFileChooser.APPROVE_OPTION){
                        try {
                            generateXML(file);
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });


        view.getAddElementButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dialog();
            }
        });

        view.getGetAllSubjectsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashSet<String> allSubjects = new HashSet<>();
                for(Iterator<SubjectMark> it = model.getSourceModel().elements().asIterator(); it.hasNext();){
                    allSubjects.add(it.next().getSubject());
                }
                model.getResultModel().clear();
                model.getResultModel().addAll(allSubjects);
            }
        });

        view.getGetGoodLearnersButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Boolean> greatStudents = new HashMap<>();
                for(Iterator<SubjectMark> it = model.getSourceModel().elements().asIterator(); it.hasNext();){
                    SubjectMark sub = it.next();
                    if(greatStudents.containsKey(sub.getName())){
                        if(sub.getMark() < 9  && greatStudents.get(sub.getName())){
                            greatStudents.put(sub.getName(), false);
                        }
                    } else {
                        if (sub.getMark() < 9) {
                            greatStudents.put(sub.getName(), false);
                        } else {
                            greatStudents.put(sub.getName(), true);
                        }
                    }
                }
                model.getResultModel().clear();
                greatStudents.forEach( (st,b) ->{
                    if(b) model.getResultModel().addElement(st);
                });
            }
        });

        view.getGetBadStudentsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> str = new Subjects().getStudents();
                model.getResultModel().clear();
                str.forEach((n) -> model.getResultModel().addElement(n));

            }
        });
    }

    private class Dialog extends JDialog{

        public Dialog() {
            super(view,"Add element");
            this.setSize(500,100);
            this.setVisible(true);
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JTextField numberField = new JTextField("", 5);
            JTextField nameField = new JTextField("", 5);
            JTextField semField = new JTextField("", 5);
            JTextField subjectField = new JTextField("", 5);
            JTextField markField = new JTextField("", 5);

            JButton addButton = new JButton("Add");

            panel.add(numberField);
            panel.add(nameField);
            panel.add(semField);
            panel.add(subjectField);
            panel.add(markField);
            panel.add(addButton);

            add(panel);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.getResultModel().clear();
                    try {
                        int number = Integer.parseInt(numberField.getText());
                        String name = nameField.getText();
                        int sem = Integer.parseInt(numberField.getText());
                        String subject = subjectField.getText();
                        int mark = Integer.parseInt(markField.getText());
                        if (name.equals("") || subject.equals("")){
                            throw new NullPointerException();
                        }
                        model.getSourceModel().addElement(new SubjectMark(number,name,sem, subject,mark));
                    } catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Sorry cannot parse to int!");
                    } catch (NullPointerException ex){
                        JOptionPane.showMessageDialog(null, "Sorry cannot null!");
                    }
                }
            });
        }
    }


    private  class Subjects{
        Set<Subject> subjects;
        Set<String> students;

        public Subjects() {
            super();
            subjects = new HashSet<>();
            students = new HashSet<>();

            for(Iterator<SubjectMark> it = model.getSourceModel().elements().asIterator(); it.hasNext();){
                SubjectMark sub = it.next();
                subjects.add(new Subject(sub.getSubject()));
                students.add(sub.getName());
            }
        }

        Vector<String> getStudents(){
            for(Subject a : subjects) {
                a.setZero(students);
            }
            for(Iterator<SubjectMark> it = model.getSourceModel().elements().asIterator(); it.hasNext();){
                SubjectMark subjectMark = it.next();
                String subjectName = subjectMark.getSubject();
                subjects.forEach((n) ->{
                    if(n.getName().equals(subjectName)){
                        n.addStudent(subjectMark.getName());
                    }
                });
            }
            Stream<Subject> a =  subjects.stream().sorted(new Comparator<Subject>() {
                @Override
                public int compare(Subject o1, Subject o2) {
                    return (o1.getName().compareTo(o2.getName()));
                }
            });
            Vector<String> out = new Vector<>();
            a.forEach((n) -> {
                    out.add(n.name);
                    n.studentBool.forEach((s, b) -> {
                        if (!b) {
                            out.add("   " + s);
                        }
                    });
            });
            return out;
        }
    }

    private static class Subject{


        private final String name;
        private final HashMap<String, Boolean> studentBool;

        public Subject(String name) {
            super();
            this.name = name;
            studentBool = new HashMap<>();
        }

        void setZero(Set<String> allStudents){
            allStudents.forEach((n) -> {
                studentBool.put(n,false);
            });
        }

        void addStudent(String studentName){
            studentBool.put(studentName,true);
        }

        public String getName() {
            return name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return name.equals(((Subject)obj).getName());
        }
    }

    void openXml(File file) {
        try {

            LinkedList<SubjectMark> array = new LinkedList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(file);

            System.out.println(document.getDocumentElement().getTagName());

            Node root = document.getDocumentElement();
            NodeList students = root.getChildNodes();

            System.out.println(students.item(0).getNodeName());
            System.out.println("Found main field");

            for (int i = 0; i < students.getLength(); i++) {
                Node student = students.item(i);

                NodeList parameters = student.getChildNodes();


                Node nodeNumber = parameters.item(0);
                Node nodeName = parameters.item(1);
                Node nodeSemestr = parameters.item(2);
                Node nodeSubject = parameters.item(3);
                Node nodeMark = parameters.item(4);
                array.add(new SubjectMark(
                        Integer.parseInt(nodeNumber.getTextContent()),
                        nodeName.getTextContent(),
                        Integer.parseInt(nodeSemestr.getTextContent()),
                        nodeSubject.getTextContent(),
                        Integer.parseInt(nodeMark.getTextContent())));
            }
            model.getSourceModel().addAll(array);
        } catch (Exception ex){

        }
    }

    void generateXML(File file) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element root = document.createElement("Students");
        document.appendChild(root);

        Iterator<SubjectMark> it = model.getSourceModel().elements().asIterator();
        while(it.hasNext()) {
            SubjectMark subjectMark = it.next();

            Element student = document.createElement("Student");
            root.appendChild(student);

            Element number = document.createElement("Number");
            number.appendChild(document.createTextNode(((Integer)subjectMark.getNumber()).toString()));
            student.appendChild(number);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(subjectMark.getName()));
            student.appendChild(name);

            Element semestr = document.createElement("Semestr");
            semestr.appendChild(document.createTextNode(((Integer)subjectMark.getSemNumber()).toString()));
            student.appendChild(semestr);

            Element subjecct = document.createElement("Subject");
            subjecct.appendChild(document.createTextNode(subjectMark.getSubject()));
            student.appendChild(subjecct);

            Element mark = document.createElement("Mark");
            mark.appendChild(document.createTextNode(((Integer)subjectMark.getMark()).toString()));
            student.appendChild(mark);
        }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);

        transformer.transform(domSource, streamResult);
    }
}
