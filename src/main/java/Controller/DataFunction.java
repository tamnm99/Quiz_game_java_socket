/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Question;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author tamng
 */
public class DataFunction {

    Connection con = Connections.newConnect();

    //get list user from DB
    public ArrayList<User> getUserList() {

        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<User> listUser = new ArrayList<User>();
        try {
            String sql = "SELECT * FROM User";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setUserName(rs.getString(2));
                u.setPassWord(rs.getString(3));
                u.setOnline(rs.getInt(4));
                u.setPoint(rs.getFloat(5));
                listUser.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listUser;
    }

    //registry new user
    public int addUser(User u) {
        try {
            String sql = "INSERT INTO User(account, password, status, point)"
                    + "VALUE(?, ?, ?, ?)";
            PreparedStatement add = con.prepareStatement(sql);
            add.setString(1, u.getUserName());
            add.setString(2, u.getPassWord());
            add.setInt(3, 0);
            add.setFloat(4, 0);
            int row = add.executeUpdate();
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //get point from user from DB to ranking
    public ArrayList<User> getUserRank() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<User> listUser = new ArrayList<User>();
        try {
            String sql = "SELECT * FROM User ORDER BY point DESC";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setUserName(rs.getString(2));
                u.setPassWord(rs.getString(3));
                u.setOnline(rs.getInt(4));
                u.setPoint(rs.getFloat(5));
                listUser.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listUser;
    }

    //get random 5 question in DB
    public ArrayList<Question> getQuestion() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Question> listQuestion = new ArrayList<Question>();

        try {
            String sql = "SELECT * FROM question ORDER BY RAND() LIMIT 5";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt(1));
                q.setQuestion(rs.getString(2));
                q.setAnswerA(rs.getString(3));
                q.setAnswerB(rs.getString(4));
                q.setAnswerC(rs.getString(5));
                q.setAnswerD(rs.getString(6));
                q.setCorrectAnswer(rs.getInt(7));
                listQuestion.add(q);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return listQuestion;
    }

    //update status of user
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE User SET status = ? WHERE userID = ?";

        PreparedStatement updateQuery = con.prepareStatement(sql);
        updateQuery.setInt(1, user.getOnline());
        updateQuery.setInt(2, user.getId());
        updateQuery.execute();
        return true;
    }

    //update point of user
    public void updatePoint(User user) {
        try {
            String sql = "UPDATE User SET point = ? WHERE userID = ?";
            PreparedStatement updateQuery = con.prepareStatement(sql);
            updateQuery.setFloat(1, user.getPoint());
            updateQuery.setInt(2, user.getId());
            updateQuery.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
