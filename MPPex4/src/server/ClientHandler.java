package server;

import handlers.MessageHandler;
import models.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by green on 6/17/2019.
 */
class ClientHandler implements Runnable {
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket s;
    private MessageHandler messageHandler;
    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        messageHandler = new MessageHandler();
    }

    @Override
    public void run() {
        System.out.println("Client handler start handling...");
        String received;
        try {
            received = readProcedure();
            System.out.println("Client handler received message: [\n" + received + "]\n");
            Response response = messageHandler.messageReceived(received);
            writeProcedure(response);
            System.out.println("Client handler sent response: [\n" + response + "]\n");
            // closing resources
            this.dis.close();
            this.dos.close();
            this.s.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception thrown: " + e);
        }
    }

    public String[] split(String str, int blockSize){
        int len = str.length();
        int size = len/blockSize + 1;
        String[] arr = new String[size];
        int i=0;
        for(i=0; i < size-1; i++){
            arr[i] = str.substring(i*blockSize, i*blockSize+blockSize);
        }
        arr[size-1] = str.substring(i*blockSize, len);
        return arr;
    }

    private String readProcedure() throws IOException {
        System.out.println("Reading!");
        int len = dis.readInt();
        StringBuilder sb = new StringBuilder();
        for (int i =0; i < len; i++)
            sb.append(dis.readUTF());
        return sb.toString();
    }

    private void writeProcedure(Response messgae)  throws IOException {
        String[] splitted = split(messgae.getMessage(), 2048);
        dos.writeInt(splitted.length);
        for(String s: splitted)
            dos.writeUTF(s);
    }

}
