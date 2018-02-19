/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
/**
 *
 * @author Sherin
 */
public class servertry {

    public static void main(String[] args) throws Exception {

        try (ServerSocket serv = new ServerSocket(25000)) {

            System.out.println("waiting...");
            try (Socket socket = serv.accept()) {
                OutputStream dOut = socket.getOutputStream();
                System.out.println("client connected");
                while (true) {
                    Thread.sleep(3000);
                    BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    if (screencapture != null) {
                        System.out.println("sent");
                        ImageIO.write(screencapture, "png", new File("screenshot.png"));
                        File file = new File("screenshot.png");
                        FileInputStream in = new FileInputStream(file);
                        byte[] buf = new byte[1024];
                        int count = 0;
                        while ((count = in.read(buf)) >= 0) {
                            dOut.write(buf, 0, count);

                        }
                        Thread.sleep(3000);
                         if (socket.getInputStream().available() != 0) {
                           DataInputStream dis = new DataInputStream(socket.getInputStream());
                          String f = dis.readUTF();
                           System.out.println("PI=" + f);
                        if (f.contains(".") && f.contains(","))  {
                           String[] parts = f.split("\\."); //parts[0] x
                                 System.out.println("PI=" + parts[0]+parts[1]);
                            String part0 = parts[0]; 
                            String part1 = parts[1];
                            System.out.println("PI=" + part0+part1);
                            String[] ydot = part1.split(","); //ydot[1] y.0
                            String ydot0 = ydot[0]; 
                            String ydot1 =ydot[1];
                             System.out.println("PI=" + ydot0+ydot1);
                             String[] y = ydot1.split("\\."); //y[0] y
                              String y0 =y[0];
                               System.out.println("PI=" + y0);
                           int val1 = Integer.parseInt(part0);
                           int val2 = Integer.parseInt(y0);
                            Robot bot = new Robot();
                           bot.mouseMove(val1, val2);
                              } }
                         
                         
                         
                         
                         }
                        
                    }

                }
            }
        }
    }


