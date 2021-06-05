/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Message;
import java.io.IOException;

/**
 *
 * @author tamng
 */
public interface inReceiveMessage {
      public void ReceiveMessage(Message msg) throws IOException;
}
