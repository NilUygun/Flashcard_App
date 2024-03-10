/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flashcards;

/**
 *
 * @author uygun
 */
public class QuizCard {
    private String question;
    private String answer;

    public QuizCard(String f, String b){
        setQuestion(f);
        setAnswer(b);
    }

 
    String getAnswer(){
        return answer;
    }

    String getQuestion(){
        return question;
    }


    void setAnswer(String text){
        answer = text;
    }

    void setQuestion(String text){
        question = text;
    }
}
