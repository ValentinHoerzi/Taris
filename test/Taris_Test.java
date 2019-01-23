/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import taris.Person;

/**
 *
 * @author A N N A
 */
public class Taris_Test {

    private static Socket socket; //THIS socket
    private static PrintWriter server_writer; //Writer to server
    private static BufferedReader server_reader; //Server reade
    private static Person testPerson;

    public Taris_Test() {
        this.testPerson = new Person("TestPerson", "localhost", "localIP");
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        socket = new Socket("localhost", 4711);
        server_writer = new PrintWriter(socket.getOutputStream(), true);
        server_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        server_writer.println("<login=" + testPerson.toString() + ">");
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        server_writer.println("<logout>");
        socket.close();
        server_writer.close();
        server_reader.close();
    }

    class Receiver implements Runnable {

        private final BufferedReader reader;

        public Receiver(BufferedReader br) {
            this.reader = br;
        }

        @Override
        public void run() {
            
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetWantedStringFromServer() {
        
    }
}
