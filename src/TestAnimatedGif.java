import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;

public class TestAnimatedGif {
    protected void initUI() throws InterruptedException {
        SerialPort comPort = SerialPort.getCommPorts()[0];  // Ищем первый готовый COM порт
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        //Открываем порт

        JFrame frame = new JFrame(); //Создаем основное окно GUI
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(); // Панель размещения бананов
        JPanel panel2 = new JPanel(); // Панель размещения счетчика бананов
        JFrame frame2 = new JFrame(); // Отисовка черного окна
        JPanel panel3 = new JPanel(); // Панель для черного окна
        panel3.setBackground(Color.BLACK); // Устновка цвета
        panel3.setSize(1024,1024); // Установка разрешения на весь экран
        frame2.add(panel3,BorderLayout.CENTER);
        frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        panel2.setSize(200,300);
        JLabel text = new JLabel("0"+"/100");
        text.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 45));
        frame.getContentPane().setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);
        panel.setSize(1024,1024);
        panel.setPreferredSize(new Dimension(1280,1024));
        panel2.setBackground(Color.WHITE);
        panel2.add(text);
        panel2.revalidate();
        panel2.repaint();
        frame.add(panel,BorderLayout.LINE_START);
        frame.add(panel2,BorderLayout.SOUTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame2.setUndecorated(true);
        frame2.setVisible(true);
        final Timer t = new Timer(100, null);

        t.addActionListener(new ActionListener() {
            int countBanana = 0;
            int CheckLife=0;
            int b;
            int c;
            String s = null;
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckLife++;
                if (CheckLife==50){
                    JLabel image = new JLabel(new ImageIcon(this.getClass().getResource(
                            "error.png")));
                    panel3.setVisible(true);
                    panel3.add(image);
                }
                text.setText(countBanana+"/100");
                panel2.revalidate();
                panel2.repaint();

               // try {
             //       s = String.valueOf((char)in.read())+String.valueOf((char)in.read());
            //        System.out.println("string:"+s);

            //    } catch (IOException e1) {
             //       e1.printStackTrace();
             //   }

                try {
                    {
                        while (comPort.bytesAvailable() == 0) {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }

                        byte[] readBuffer = new byte[comPort.bytesAvailable()];
                        int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                        System.out.println("Read " + numRead + " bytes.");
                        System.out.println(String.valueOf((char) readBuffer[0])+String.valueOf((char) readBuffer[1])+String.valueOf((char) readBuffer[2]));
                        c = Integer.parseInt(String.valueOf((char) readBuffer[0])+String.valueOf((char) readBuffer[1])+String.valueOf((char) readBuffer[2]));
                    }}
                    catch (Exception m){}
                    if(c<101){
                        b = c;
                    }

                System.out.println("с="+c);
                System.out.println("."+b+".");
                if (c==300){
                    Sound.playSound("C:\\Users\\mixanes\\Desktop\\testgif\\src\\testmelodi.wav");
                }
                if (c==777){
                    CheckLife=0;
                }
                if (b<countBanana && countBanana<100 && !(countBanana ==b)) {   // Удаляем бананы
                    int countBananaMinus = countBanana-b;
                    Sound.playSound("C:\\Banana\\-banana.wav");
                    panel2.revalidate();
                    panel2.repaint();
                    panel.revalidate();
                    panel.repaint();
                    while (countBananaMinus>0){
                        try {
                            try {panel.remove(0);
                                System.err.println("image del");

                            }
                             catch (Exception x){
                                 System.out.println("Кончились бананы");
                             }//Проверь, возможно тут косяк
                            countBananaMinus--;

                            if(countBanana<0){
                                countBanana=0;
                            }
                            text.setText(countBanana+"/100");
                            panel2.revalidate();
                            panel2.repaint();
                            panel.revalidate();
                            panel.repaint();

                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    countBanana=b;

                }
                if (b>countBanana&&countBanana<100&& !(countBanana ==b)) {         //   Добавляем Бананы
                    int countBananaPlus = b-countBanana;
                    System.out.println(countBananaPlus);
                    frame2.setVisible(false);
                    Sound.playSound("C:\\Banana\\+banana.wav");
                    panel2.revalidate();
                    panel2.repaint();
                    panel.revalidate();
                    panel.repaint();
                    System.err.println("image added");
                    while (countBananaPlus>0){
                        try {
                            JLabel image = new JLabel(new ImageIcon(this.getClass().getResource(
                                    "banan.jpg")));
                            panel.add(image, BorderLayout.LINE_START);
                            countBananaPlus--;
                            text.setText(countBanana+"/100");
                            panel2.revalidate();
                            panel2.repaint();
                            panel.revalidate();
                            panel.repaint();

                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    countBanana=b;
                }


                if(countBanana>=100){                  // Если достигаем 100 бананов заканчиваем игру
                    text.setText(countBanana+"/100");
                    panel2.revalidate();
                    panel2.repaint();
                    panel.removeAll();
                    JLabel headerLabel = new JLabel();
                    headerLabel.setFont(new java.awt.Font("Comic Sans MS", Font.BOLD, 65));
                    headerLabel.setText(" You Win!!!");
                    panel.add(headerLabel, java.awt.BorderLayout.NORTH);
                    JLabel image = new JLabel(new ImageIcon(this.getClass().getResource(
                            "happy-banana-stickers.jpg")));
                    image.setBackground(Color.WHITE);
                    panel.add(image, BorderLayout.CENTER);
                    frame.add(panel,BorderLayout.LINE_START);
                    if(c==666){             // Если получаем с COM "666" - new game - начинаем новую игру
                        frame2.setVisible(true);
                        panel.removeAll();
                        panel.setBackground(Color.WHITE);
                        countBanana=0;
                        panel2.revalidate();
                        panel2.repaint();
                        panel.revalidate();
                        panel.repaint();
                    }
                }
                text.setText(countBanana+"/100");
                panel2.revalidate();
                panel2.repaint();

            }

        }
        );
        t.start();
    }

    public static void main(String[] args) throws InterruptedException {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                try {
                    new TestAnimatedGif().initUI();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}