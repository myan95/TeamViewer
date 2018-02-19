import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Micro Systems
 */
public class Client {

    public static void main(String[] args) throws Exception {
        
            //  try (Socket socket = new Socket("169.254.205.222", 25000)) {
       try (Socket socket = new Socket("localhost", 25000)) {
            BufferedImage image;
   JFrame f1 = new JFrame("remote control on server");
                      JLabel    label = new JLabel();
                    OutputStream dOut = socket.getOutputStream();
            while (true) {
                if (socket.getInputStream().available() != 0) {
                    image = ImageIO.read(socket.getInputStream());

                    if (image != null) {
                        System.out.println("recevied");
                       f1.getContentPane().add(label);  
                        f1.pack();
                       ImageIcon icon = new ImageIcon(image); 
                        label.setIcon(icon);
                         f1.setVisible(true);
                         float locationX=0;
                         float locationY=0;
                             locationX=MouseInfo.getPointerInfo().getLocation().x ;
                         locationY= MouseInfo.getPointerInfo().getLocation().y ;
                        DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("D:\\file.txt"));       
                         String X=String.valueOf(locationX);
                         String Y=String.valueOf(locationY);
                         String Blam = X + ',' + Y ;
                         byte[] b = Blam.getBytes();
                        FileInputStream in = new FileInputStream("D:\\file.txt");
                        dataOut.writeUTF(Blam);
                         byte[] buf = new byte[1024];
                        int count = 0;
                        while ((count = in.read(buf)) >= 0) {
                            dOut.write(buf, 0, count);

                        }
                         
                    }   
             

                }

            }
        }
    }
}
