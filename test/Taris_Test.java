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
import java.util.logging.*;
import org.junit.*;
import static org.junit.Assert.*;
import taris.Person;


/**
 *
 * @author A N N A
 */
public class Taris_Test {

    private static Socket socket; //THIS socket
    private static PrintWriter server_writer; //Writer to server
    private static BufferedReader server_reader; //Server reade

    @BeforeClass
    public static void setUpClass() throws IOException {
        Person testPerson = new Person("TestPerson", "localhost", "localhost");
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

    private class Receiver implements Runnable {

        private final BufferedReader reader;
        private String outPutSTring;
        private boolean run = true;

        public void setRun(boolean run) {
            this.run = run;
        }

        public String getOutPutSTring() {
            return outPutSTring;
        }

        public Receiver(BufferedReader br) {
            this.reader = br;
        }

        @Override
        public void run() {
            String input = null;
            try {
                input = reader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Taris_Test.class.getName()).log(Level.SEVERE, null, ex);
            }

            while (run) {
                outPutSTring = input;

                try {
                    input = reader.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(Taris_Test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetWantedStringFromServer() throws InterruptedException {

        Receiver receiver = new Receiver(server_reader);
        Thread reader = new Thread(receiver);
        reader.start();

        Thread.sleep(1000);
        String outPutSTring = receiver.getOutPutSTring();

        assertEquals("TestPerson: yo", outPutSTring);
    }
}
