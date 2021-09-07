package com.example.newtriviagame.model;

public class Question {

    private String kysymys;
    private boolean isCorrect;

    public Question(String kysymys, boolean isCorrect) {
        this.kysymys = kysymys;
        this.isCorrect = isCorrect;
    }

    public Question() {
    }

    public String getKysymys() {
        return kysymys;
    }

    public void setKysymys(String kysymys) {
        this.kysymys = kysymys;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }




}
