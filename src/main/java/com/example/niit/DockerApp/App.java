package com.example.niit.DockerApp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Message m1 = new Message("m1", "c1");
        Message m2 = new Message("m2", "c2");
        Message m3 = new Message("m3", "c3");

        File file = new File("C:/Users/Christopher-JavaLord/Downloads/DockerApp/src/main/resources/messages.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file, true));
        outputStream.writeObject(m1);
        outputStream.writeObject(m2);
        outputStream.writeObject(m3);


        List<Message> messageList = new ArrayList<>();

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        Message obj = (Message)inputStream.readObject();
        while (obj != null) {
            try {
                messageList.add(obj);
                obj = (Message) inputStream.readObject();
            }
            catch (Exception ex) {
                obj = null;
            }
        }

        messageList.forEach(System.out::println);
    }
}
