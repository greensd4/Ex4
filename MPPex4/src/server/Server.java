package server;

import common.ConfigHolder;
import java.io.*;
import java.net.*;

// Server class
public class Server  {

    private ServerSocket ss;
    private String ip;
    private int port;
    private int backlog;

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.backlog = 10;
        System.out.println("Creating new server with ip " + this.ip + " and port " + Integer.toString(this.port));
    }

    public Server(){
        this.ip = ConfigHolder.getCommunicationProp("serverip", "127.0.0.1");
        this.port = ConfigHolder.getCommunicationIntProp("serverport", 18080);
        this.backlog = 10;
        System.out.println("Creating new server with ip " + this.ip + " and port " + Integer.toString(this.port));
    }

    public void connect() throws IOException {
        System.out.println("Creating new connection");
        InetAddress addr = InetAddress.getByName(this.ip);
        this.ss = new ServerSocket(this.port, this.backlog, addr);
    }

    public void startServer() throws IOException{
        // running infinite loop for getting
        // client request
        System.out.println("Start accepting clients");
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                ClientHandler ch = new ClientHandler(s, dis, dos);
                ch.run();
//                Thread t = new Thread(new ClientHandler(s, dis, dos));
//                t.start();
            }
            catch (Exception e){
                s.close();
                System.out.println("exception thrown" + e);
            }
        }
    }



    public ServerSocket getSs() {
        return ss;
    }

    public void setSs(ServerSocket ss) {
        this.ss = ss;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}