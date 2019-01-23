/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taris;

/**
 * @author Valentin
 */
public class Person {
    private  String name;
    private  String server_ip;
    private  String client_ip;

    public Person(String name, String server_ip,String client_ip) {
        this.name = name;
        this.server_ip = server_ip;
        this.client_ip = client_ip;
    }

    public String getClient_ip() {
        return client_ip;
    }
    
    public String getName() {
        return name;
    }


    public String getServer_ip() {
        return server_ip;
    }

    @Override
     public String toString() {
        return "["+name+","+server_ip+","+client_ip+"]";
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
