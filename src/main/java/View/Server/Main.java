/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Server;

import Controller.ClientHandler;
import Model.User;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class Main extends javax.swing.JFrame {

    private static ServerSocket serverSocket;
    public static ArrayList<ClientHandler> lstClient;
    public static User userRoom; // client 1
    public static User userRoom2; // client 2
    public static float point = 0;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        setLocationRelativeTo(null);
        labelHello.setText("Server is running........");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        serverTextPane = new javax.swing.JTextPane();
        labelHello = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane2.setViewportView(serverTextPane);

        labelHello.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        labelHello.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(labelHello)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(labelHello)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:               
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("SERVER");
                ServerListener server;
                try {
                    server = new ServerListener();
                    server.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                lstClient = new ArrayList<ClientHandler>();
                new Main().setVisible(true);
            }
        });
    }

     //mở server
    private static void openServer(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
            if (serverSocket == null) {
                System.out.println("Mở Server thất bại");
            } else {
                System.out.println("Mở Server thành công");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerListener extends Thread {

        ServerListener() throws IOException {
          openServer(9876);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // get IP of Client
                    final Socket socketToClient = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socketToClient);
                    lstClient.add(clientHandler);
                    System.out.println("Server: " + socketToClient.getInetAddress() + " is connecting");
                    
                    // start clientHandler
                    clientHandler.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelHello;
    private javax.swing.JTextPane serverTextPane;
    // End of variables declaration//GEN-END:variables
}