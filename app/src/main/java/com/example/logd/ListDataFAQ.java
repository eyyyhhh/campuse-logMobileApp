package com.example.logd;

public class ListDataFAQ {

    private String question, answer;


    public ListDataFAQ(String question, String answer) {

        this.question = question;
        this.answer = answer;

    }
    public  String getQuestion(){
        return question;
    }
    protected void setQuestion(String question){
        this.question = question;
    }
    public  String getAnswer(){
        return answer;
    }
    protected void setAnswer(String date){
        this.answer = answer;
    }
}
