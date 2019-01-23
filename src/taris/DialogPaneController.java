/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taris;

import java.awt.Toolkit;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import taris.Person;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.util.*;

/**
 * @author Valentin
 */
public class DialogPaneController implements Initializable {

    @FXML
    private TextField textfield_name;
    @FXML
    private TextField textfield_serverIP;

    Callback<ButtonType, Person> callback;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String ip = null;

        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (Exception ex) {
            System.err.println("Error at DialogPaneController::initialize");
        }
        final String ad = ip;
        callback = param -> {
            if (param == ButtonType.APPLY) {
                return new Person(textfield_name.getText(), textfield_serverIP.getText(), ad);
            }
            return null;
        };
    }
}
