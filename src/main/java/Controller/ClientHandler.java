/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Game;
import Model.Message;
import Model.Question;
import Model.User;
import View.Server.Main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tamng
 */
public class ClientHandler extends Thread implements inReceiveMessage {

    private Socket socket;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;

    public User user;
    public static Game game1;// game send to Server
    public static Game game2;// game receive from server

    Boolean execute = true;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        execute = true;
    }

    @Override
    public void ReceiveMessage(Message msg) throws IOException {

        switch (msg.getType()) {

            // Check Login
            case 0: {
                User u = (User) msg.getObject();
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                for (User obj : lstUser) {
                    if (obj.getUserName().contains(u.getUserName()) && obj.getPassWord().contains(u.getPassWord())) {
                        user = obj;
                    }
                }
                if (user != null) {
                    User objU = user;
                    objU.setOnline(1);
                    try {
                        Boolean a = df.updateUser(objU);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println("Server: Xin chào:  " + user.getUserName());

                }
                SendMessage(0, user);
                break;
            }

            //Handle Register
            case 5: {
                boolean check = false;
                User u = (User) msg.getObject();
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                for (User obj : lstUser) {
                    if (obj.getUserName().contains(u.getUserName())) {
                        check = true;
                        break;
                    }
                }
                if (check == true) {
                    SendMessage(6, null); // Account is exist
                } else if (check == false && df.addUser(u) == 1) {
                    SendMessage(7, null); // Regsiter complete

                }
            }

            // receive List user online
            case 20: {
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                User objUser = new User();

                String s = msg.getObject().toString();

                //receive user 2 is invited by user 1
                for (User u : lstUser) {
                    if (u.getUserName().contains(s)) {
                        Main.userRoom2 = u;
                        objUser = u;
                    }
                }

                //Save data of user 1
                Main.userRoom = this.user;
                for (ClientHandler lstUser1 : Main.lstClient) {
                    if (lstUser1.user.getUserName().contains(objUser.getUserName())) {
                        lstUser1.SendMessage(30, null);// Send request to user 2 
                    }
                }
                break;
            }
            // Receive data from list and show
            case 21: {

                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                ArrayList<User> lstUserOnline = new ArrayList<User>();
                for (User lstUser1 : lstUser) {
                    if (lstUser1.getOnline() == 1 && !lstUser1.getUserName().contains(this.user.getUserName())) {
                        lstUserOnline.add(lstUser1);
                    }
                }
                SendMessage(21, lstUserOnline);// show list user online
                break;
            }

            // Send message that player have entered room
            case 31: {
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                User objUser = new User();
                for (ClientHandler lstUser1 : Main.lstClient) {
                    if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName())) { // Get Thread of user send request to enter room
                        lstUser1.SendMessage(33, null); // send Message confirm
                    }
                }
                break;
            }
            // Send Message that "user refuse challenge"
            case 32: {
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                User objUser = new User();
                for (ClientHandler lstUser1 : Main.lstClient) {
                    if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName())) {
                        lstUser1.SendMessage(34, null);// Send Message that "user don't want to play "
                    }
                }
                break;
            }

            // request ready to play
            case 40: {
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                User objUser = new User();
                for (ClientHandler lstUser1 : Main.lstClient) {
                    if (lstUser1.user.getUserName().contains(Main.userRoom2.getUserName()) && lstUser1 != this) {
                        lstUser1.SendMessage(41, null); // Gửi yêu cầu đến người còn lại
                    } else if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName()) && lstUser1 != this) {
                        lstUser1.SendMessage(41, null);// Gửi yêu cầu đến người còn lại
                    }
                }
                break;
            }

            // accept and send question to user
            case 44: {
                DataFunction df = new DataFunction();
                ArrayList<Question> questions = new ArrayList<Question>();
                questions = df.getQuestion();
                Message temp = new Message();
                temp.setType(46);
                temp.setQuestions(questions);
                // Get question from server and send to client
                for (ClientHandler lstUser1 : Main.lstClient) {
                    lstUser1.SendMessage(temp);
                }
                break;
            }
            // have not ready
            case 45: {
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                User objUser = new User();
                for (ClientHandler lstUser1 : Main.lstClient) {
                    if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName())) {
                        lstUser1.SendMessage(47, null);// send refuse
                    }
                }
                break;
            }

            // Send result of game
            case 50: {
                DataFunction df = new DataFunction();

                //someone send result first
                if (game1 == null) {
                    game1 = (Game) msg.getObject();

                    if (game1.getCount() == 5) {// if result is 5 correct
                        Main.point = 1;
                        game1.getUser().setPoint((game1.getUser().getPoint() + 1));
                        df.updatePoint(game1.getUser());
                        SendMessage(100, null);
                    } else { //send wait...

                        SendMessage(100, null);
                    }

                } else {

                    //Lose
                    game2 = (Game) msg.getObject();
                    if (Main.point == 1) {
                        Main.point = 0;
                        SendMessage(62, game1);

                        for (ClientHandler lstUser1 : Main.lstClient) {
                            if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName()) && !this.user.getUserName().contains(Main.userRoom.getUserName())) {
                                lstUser1.SendMessage(61, game2);
                            } else if (lstUser1.user.getUserName().contains(Main.userRoom2.getUserName()) && !this.user.getUserName().contains(Main.userRoom2.getUserName())) {
                                lstUser1.SendMessage(61, game2);
                            }
                        }
                    } else { //win
                        if (game2.getCount() == 5) {
                            Main.point = 1;

                            game2.getUser().setPoint((game2.getUser().getPoint() + 1));
                            df.updatePoint(game2.getUser());
                            SendMessage(61, game1);
                            for (ClientHandler lstUser1 : Main.lstClient) {
                                if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName()) && !this.user.getUserName().contains(Main.userRoom.getUserName())) {
                                    lstUser1.SendMessage(62, game2);
                                } else if (lstUser1.user.getUserName().contains(Main.userRoom2.getUserName()) && !this.user.getUserName().contains(Main.userRoom2.getUserName())) {
                                    lstUser1.SendMessage(62, game2);
                                }
                            }
                        } else { //tie

                            Main.point = (float) 0.5;
                            SendMessage(60, game1);
                            float d = (float) (game1.getUser().getPoint() + 0.5);
                            game1.getUser().setPoint(d);
                            df.updatePoint(game1.getUser());
                            d = (float) (game2.getUser().getPoint() + 0.5);
                            game2.getUser().setPoint(d);
                            df.updatePoint(game2.getUser());

                            for (ClientHandler lstUser1 : Main.lstClient) {
                                if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName()) && !this.user.getUserName().contains(Main.userRoom.getUserName())) {
                                    lstUser1.SendMessage(60, game2);
                                } else if (lstUser1.user.getUserName().contains(Main.userRoom2.getUserName()) && !this.user.getUserName().contains(Main.userRoom2.getUserName())) {
                                    lstUser1.SendMessage(60, game2);
                                }
                            }
                        }
                    }
                }
                break;
            }

            case 70: {
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserList();
                User objUser = new User();
                for (ClientHandler lstUser1 : Main.lstClient) {
                    if (lstUser1.user.getUserName().contains(Main.userRoom.getUserName()) && lstUser1 != this) {
                        lstUser1.SendMessage(71, null);
                    } else if (lstUser1.user.getUserName().contains(Main.userRoom2.getUserName()) && lstUser1 != this) {
                        lstUser1.SendMessage(71, null);
                    }
                }
                break;
            }
            //show rank
            case 80: {
                DataFunction df = new DataFunction();
                ArrayList<User> lstUser = new ArrayList<User>();
                lstUser = df.getUserRank();
                SendMessage(80, lstUser);
                break;
            }
        }
    }

    public void SendMessage(int ty, Object obj) throws IOException {
        Message temp = new Message(ty, obj);
        SendMessage(temp);
    }

    public void SendMessage(int ty, ArrayList<User> obj) throws IOException {
        Message temp = new Message(ty, obj);
        SendMessage(temp);
    }

    public void SendMessage(int ty, ArrayList<User> obj, ArrayList<Question> questions) throws IOException {
        Message temp = new Message(ty, obj, questions);
        SendMessage(temp);
    }

    public void SendMessage(Message msg) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(msg);
    }

    public Boolean closeClient() throws Throwable {
        DataFunction df = new DataFunction();
        User objU = this.user;
        objU.setOnline(0);
        try {
            Boolean a = df.updateUser(objU);
        } catch (Exception e) {
        }
        Main.lstClient.remove(this);
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Client Exit");
        execute = false;

        return true;
    }

    @Override
    public void run() {
        while (execute) {
            try {
                Object o = objectInputStream.readObject();
                if (o != null) {
                    ReceiveMessage((Message) o);
                }

            } catch (IOException e) {
                try {
                    closeClient();
                } catch (Throwable ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
