/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author tamng
 */
public class Message implements Serializable {

    private int type;
    private Object object;
    private ArrayList<User> lstUser;
    private ArrayList<Question> questions;

    public Message() {
    }

    public Message(int type, ArrayList<User> lstUser) {
        this.type = type;
        this.lstUser = lstUser;
    }

    public Message(int type, ArrayList<User> lstUser, ArrayList<Question> questions) {
        this.type = type;
        this.lstUser = lstUser;
        this.questions = questions;
    }

    public Message(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ArrayList<User> getLstUser() {
        return lstUser;
    }

    public void setLstUser(ArrayList<User> lstUser) {
        this.lstUser = lstUser;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
