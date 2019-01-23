/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taris;

import java.io.*;
import java.util.*;
import javafx.application.*;

/**
 * @author Valentin
 */
public class Receiver implements Runnable {

    private final BufferedReader in;
    private boolean running = true;

    public Receiver(BufferedReader in) { 
        this.in = in;

    }

    @Override
    public void run() {
        String inputText = null;
        try {
            inputText = in.readLine();
        } catch (IOException ex) {
            System.err.println("Exception at Reading line in Receive.java");
            ex.printStackTrace();
        }
        while (running) {
            final String s = inputText;
            Platform.runLater(()->Main.getController().getObserveListView().add(s));

            try {
                inputText = in.readLine();
            } catch (IOException ex) {
                System.err.println("Exception at Reading line in Receive.java");
                ex.printStackTrace();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
