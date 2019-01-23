/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taris;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

/**
 * @author Valentin
 */
public class FXML_Controller implements Initializable {

    @FXML
    private ListView<String> listView; //Displaying Messages
    public ObservableList<String> observeListView;
    @FXML
    private TextField textFieldText; // Input for text

    private boolean connected; //true if connected to server
    private Socket socket; //THIS socket
    private PrintWriter server_writer; //Writer to server
    private BufferedReader server_reader; //Server reader
    private Receiver client;

    private Dialog<Person> dialog;
    private List<Person> clients = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            observeListView = FXCollections.observableArrayList();
            listView.setItems(observeListView);
            connected = false;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogPane.fxml"));
            DialogPane root = loader.load();
            dialog = new Dialog<>();
            dialog.setDialogPane(root);
            DialogPaneController controller = loader.getController();
            dialog.setResultConverter(controller.callback);
        } catch (IOException ex) {
            Logger.getLogger(FXML_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleMenuItemLogin(ActionEvent event) {
        if (!connected) {
            dialog.showAndWait().ifPresent(person -> {
                clients.add(person);
                String name = null;
                try {
                    name = person.getName().trim();
                } catch (Exception e) {
                    return;
                }
                if (name != null && name.length() > 1 && !name.isEmpty()) {
                    try {
                        socket = new Socket(person.getServer_ip(), 4711);
                        server_writer = new PrintWriter(socket.getOutputStream(), true);
                        server_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    } catch (IOException ex) {
                        System.err.println("Exception at Creatin Socket in Controller : 108");
                    }
                    server_writer.println("<login=" + person.toString() + ">");
                    connected = true;

                    client = new Receiver(server_reader);
                    Thread runningThread = new Thread(client);
                    runningThread.start();
                } else {
                    showAlert("You are already connected");
                }
            });
        }
    }

    @FXML
    private void handleMenuItemDisconnect(ActionEvent event) {
        if (connected) {
            connected = false;
            server_writer.println("<logout>");
            try {
                socket.close();
            } catch (IOException ex) {
                System.err.println("Error at closing socket in Controller : 176");
            }
            client.setRunning(false);
            Platform.exit();
            server_writer.close();
        } else {
            showAlert("You are currently offline");
        }
    }

    private void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(message);
        a.setHeaderText(message);
        a.setContentText(message);
        a.showAndWait();
    }

    public ObservableList<String> getObserveListView() {
        return observeListView;
    }

    public PrintWriter getOut() {
        return server_writer;
    }

    @FXML
    private void handleSendMessage(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (connected) {
                String text = textFieldText.getText().trim();
                if (text != null && text.length() > 0 && !text.isEmpty()) {
                    server_writer.println(text);
                } else {
                    showAlert("No Text");
                }
                textFieldText.setText("");
            } else {
                showAlert("You are currently offline");
            }
        }
    }

    @FXML
    private void handleMouseClickedItemEven(MouseEvent event) {

    }
}
