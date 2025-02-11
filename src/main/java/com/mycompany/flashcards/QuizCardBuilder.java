/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flashcards;

/**
 *
 * @author uygun
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;


public class QuizCardBuilder {
    private Deck deck;
    private JButton button;
    private JFileChooser fileChooser = new JFileChooser();
    private JFrame frame;
    private JTextArea answerText = new JTextArea();
    private JTextArea questionText = new JTextArea();
    private JPanel panel;
    //private File theFile;

    private QuizCardPlayer quizCardPlayer;


    public QuizCardBuilder(Deck deck) {
        this.deck = deck;
        createQuizCardPlayer();
        //this.theFile = theFile;
    }

    private void addCard(){
        deck.addQuizCard(getQuestionText().getText(), getAnswerText().getText());
        setQuestionText(null);
        setAnswerText(null);
    }

    void build() {
        SwingUtilities.invokeLater(
                () -> {
                        buildFrame();
                        buildContentPane();
                        buildMenuBar();
                        buildLabel(new JLabel("Question:"));
                        buildTextArea(questionText);
                        buildLabel(new JLabel("Answer:"));
                        buildTextArea(answerText);
                        buildButtonPanel();
                        displayFrame();
                        questionText.requestFocusInWindow();
                }
        );

    }

    private void buildButtonPanel() {
        button = new JButton("Add");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(ev -> addCard());
        panel.add(button);
    }

    private void buildContentPane() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        frame.setContentPane(panel);
    }

    private void buildFrame() {
        frame = new JFrame("Quiz card builder - " + deck.getFileName());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        close();
                    }
                }
        );
    }

    private void buildLabel(JLabel label) {
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(FontConstants.labelFont);
        panel.add(label);
    }

    private void buildMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.add(Open);
        file.add(Save);
        file.add(SaveAs);
        file.add(Exit);

        JMenu card = new JMenu("Deck");
        card.add(ShuffleDeck);
        card.add(Play);

        jMenuBar.add(file);
        jMenuBar.add(card);
        frame.setJMenuBar(jMenuBar);
    }

    private void buildTextArea(JTextArea jTextArea) {
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(FontConstants.textAreaFont);
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                deck.setIsModified(true);
            }
        });
        JScrollPane jsp = new JScrollPane(jTextArea);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(jsp);
    }

    private void close(){
        if (deck.getIsModified()) {
            if(deck.getQuizCardList().size() == 0 && getQuestionText().getText().length() == 0
                    && getAnswerText().getText().length() == 0) {
                //System.exit(0);
                frame.dispose();
                new MainMenu().setVisible(true);
            }else{
                int optionChosen = JOptionPane.showConfirmDialog(frame, "Do you want to save this deck?", "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (optionChosen == JOptionPane.YES_OPTION) {
                    save();
                }
                if (optionChosen != JOptionPane.CANCEL_OPTION) {
                    //System.exit(0);
                    frame.dispose();
                    new MainMenu().setVisible(true);
                }
            }
        }else{
            //System.exit(0);
            frame.dispose();
            new MainMenu().setVisible(true);
        }
    }


    private void createQuizCardPlayer(){
        quizCardPlayer = new QuizCardPlayer(deck);
        quizCardPlayer.registerQuizCardBuilder(this); 
    }

    private void displayFrame(){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void openFile(){
        int optionChosen = JOptionPane.YES_OPTION;
        if(deck.getIsModified()){
            optionChosen = JOptionPane.showConfirmDialog(frame, "Do you want to save this deck before " +
                            "opening another?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(optionChosen == JOptionPane.YES_OPTION){
                save();
            }
        }

        if(optionChosen != JOptionPane.CANCEL_OPTION && fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
            deck = new Deck();
            deck.readFile(fileChooser.getSelectedFile().getAbsolutePath());
            setTitle(deck.getFileName());
            setQuestionText(null);
            setAnswerText(null);
        }
    }


    private void save(){
        if(deck.getFileName().equals("Untitled")){
            saveAs();
        }else{
            if(getQuestionText().getText().length() > 0){
                addCard();
            }
            deck.save(deck.getFileLocation());
            deck.setIsModified(false);
        }
    }

    private void saveAs(){
        if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            if(getQuestionText().getText().length() > 0){
                addCard();
            }
            deck.save(fileChooser.getSelectedFile().getAbsolutePath());
            deck.setFileName(fileChooser.getSelectedFile().getName());
            setTitle(deck.getFileName());
            deck.setIsModified(false);
        }
    }


    private JTextArea getAnswerText() {
        return answerText;
    }

    JTextArea getQuestionText() {
        return questionText;
    }


    private void setAnswerText(String text) {
        SwingUtilities.invokeLater(() -> answerText.setText(text));
    }

    void setTextAreaEditability(boolean isEditable){
        questionText.setEditable(isEditable);
        answerText.setEditable(isEditable);
        button.setEnabled(isEditable);
    }

    private void setTitle(String newTitle){
        SwingUtilities.invokeLater(() -> frame.setTitle("Quiz Card Builder - " + newTitle));
    }

    private void setQuestionText(String text) {
        SwingUtilities.invokeLater(() -> questionText.setText(text));
    }

    
    private Action Exit = new AbstractAction("Quit"){
        @Override
        public void actionPerformed(ActionEvent ev){
            close();
        }
    };

    private Action Open = new AbstractAction("Open"){
        @Override
        public void actionPerformed(ActionEvent ev){
            openFile();
        }
    };

    private Action Play = new AbstractAction("Begin learning"){
        @Override
        public void actionPerformed(ActionEvent ev){

            if(deck.getQuizCardList().size() == 0) {
                openFile();
            }

            if(deck.getQuizCardList().size() > 0) {
                if (deck.getIsTestRunning()) {
                    Toolkit.getDefaultToolkit().beep();
                    quizCardPlayer.toFront();
                } else {
                    deck.setIsTestRunning(true);
                    setTextAreaEditability(false);
                    createQuizCardPlayer();
                    quizCardPlayer.build();
                }
            }
        }
    };

    private Action Save = new AbstractAction("Save"){
        @Override
        public void actionPerformed(ActionEvent ev){
            save();
        }
    };

    private Action SaveAs = new AbstractAction("Save as...") {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveAs();
        }
    };

    private Action ShuffleDeck = new AbstractAction("Shuffle deck"){
        @Override
        public void actionPerformed(ActionEvent ev){
            deck.shuffle();
        }
    };
}