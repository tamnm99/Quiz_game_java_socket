/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Message;
import Model.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author tamng
 */
public class ListenServer extends Thread {

    Socket socket;
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    public User user;
    public inReceiveMessage receive;

    public ListenServer(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);

    }

    @Override
    public void run() {
        do {
            try {
                // receive data
                Object o = objectInputStream.readObject();
                if (o != null && receive != null) {
                    receive.ReceiveMessage((Message) o);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);

    }

    public void SendMessage(int ty, Object obj) throws IOException {
        Message temp = new Message(ty, obj);
        SendMessage(temp);
    }

    //Send to Server
    public void SendMessage(Message msg) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(msg);
    }
}
