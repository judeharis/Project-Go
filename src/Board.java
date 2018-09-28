import java.awt.*;
import java.awt.desktop.OpenFilesHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Board extends Canvas   {
    Stones[][] stones = new Stones[19][19];
    static boolean editormode = true;
    static Stones placing= Stones.BLACK;
    Stones turn = Stones.BLACK;
    static Color keystone = Color.BLACK;


    public void initBoard(){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                stones[i][j] = Stones.EMPTY;
                if (editormode) stones[i][j] = Stones.INVALID;


            }
        }
    }


    public Board(){

        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse was clicked");
                int x = calulatePostionOnBoard(e.getX()-30);
                int y =calulatePostionOnBoard(e.getY()-30);

                System.out.println(x+""+y);


                if (x <= 18 && y <= 18) {
                    if (editormode){
                        stones[x][y] = placing;
                        if (placing ==Stones.KEYSTONE){
                            placing = (keystone == Color.BLACK ? Stones.BLACK : Stones.WHITE);
                        }
                    }else  if (stones[x ][y] == Stones.EMPTY) {
                        stones[x][y] = turn;
                        turn = (turn == Stones.BLACK) ? Stones.WHITE : Stones.BLACK;
                    } else {
                        System.out.println("Stone already there");
                    }
                }else{
                    System.out.println("Out of bound");
                }
                System.out.println(x+","+y+","+turn);//these co-ords are relative to the component
                repaint();
            }

            public void mouseEntered(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {}
        });


    }

    public enum Stones {
        BLACK,WHITE,VALID,EMPTY,INVALID,KEYSTONE
    }

    public int calulatePostionOnBoard(int x){
        return  (x % 30 > 15 ? (x + (30 - (x % 30))) : (x - (x % 30)))/30;

    }


    public void drawoval(Graphics g,  int x, int y , Color c , boolean key) {
        g.setColor(c);
        g.fillOval(x-5, y-5, 10, 10);
        if (key){g.setColor(new Color(255,175,55));
            g.drawOval(x-7, y-7, 14, 14);
        }else{
            g.setColor(Color.BLACK);
        }

        g.drawOval(x-5, y-5, 10, 10);
    }

    public void drawsquare(Graphics g,  int x, int y , Color c) {
        g.setColor(c);
        g.fillRect(x-10, y-10, 20, 20);
    }


    public void paint(Graphics g ){
        for (int i =30; i < 600; i+=30){
            g.setColor(Color.RED);
            g.drawLine(i,30,i, 570);
            g.drawLine(30,i,570, i);
        }

        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {

                switch (stones[i][j]) {
                    case BLACK:  drawoval(g,i*30+30,j*30+30 , Color.BLACK, false);
                        break;

                    case WHITE:  drawoval(g,i*30+30,j*30+30 , Color.WHITE, false);
                        break;

                    case VALID:  drawsquare(g,i*30+30,j*30+30,new Color(0f,1f,0f,.5f ));
                        break;

                    case INVALID:  drawsquare( g,i*30+30,j*30+30 ,new Color(1f,0f,0f,.5f));
                        break;

                    case KEYSTONE: drawoval(g,i*30+30,j*30+30 , keystone , true);
                        break;
                    case EMPTY: break;


                }

            }
        }

        //g.drawString("Hello to JavaTutorial.net", 600, 10);



    }


    public void actionPerformed(ActionEvent e) {
        System.out.println("button pressed");
    }





    public static void main(String[] args){

        JFrame frame = new JFrame("New Frame");
        if (editormode) editormode_init(frame);


    }

    public static void editormode_init(JFrame frame){

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setLocation(400,0);
        Container c = frame.getContentPane();
        //c.setBackground(Color.GRAY);

        Board b = new Board();
        b.initBoard();

        b.setLocation(0,0);
        b.setSize(600,600);
        frame.add(b);


        JRadioButton radiow = new JRadioButton("Place White Stones");
        radiow.setLocation(600,120);
        radiow.setSize(140,20);
        frame.add(radiow);

        JRadioButton radiob = new JRadioButton("Place Black Stones" , true);
        radiob.setLocation(600,100);
        radiob.setSize(220,20);
        frame.add(radiob);

        JRadioButton radiov = new JRadioButton("Mark Valid Spots" );
        radiov.setLocation(600,140);
        radiov.setSize(220,20);
        frame.add(radiov);

        JRadioButton radioi = new JRadioButton("Mark Invalid Spots" );
        radioi.setLocation(600,160);
        radioi.setSize(220,20);
        frame.add(radioi);

        JRadioButton radBtoPlay = new JRadioButton("Black Plays First" , true);
        radBtoPlay.setLocation(600,200);
        radBtoPlay.setSize(220,20);
        frame.add(radBtoPlay);

        JRadioButton radWtoPlay = new JRadioButton("White Plays First" );
        radWtoPlay.setLocation(600,220);
        radWtoPlay.setSize(220,20);
        frame.add(radWtoPlay);

        JRadioButton radLife = new JRadioButton("Keystone/s needs to live" , true);
        radLife.setLocation(600,260);
        radLife.setSize(220,20);
        frame.add(radLife);

        JRadioButton radDeath = new JRadioButton("Keystone/s need to die" );
        radDeath.setLocation(600,280);
        radDeath.setSize(220,20);
        frame.add(radDeath);



        JButton keyBtn = new JButton("Place Key Stone");
        keyBtn.setLocation(600,300);
        keyBtn.setSize(200,40);
        frame.add(keyBtn);



        JEditorPane probDesc = new JEditorPane();
        probDesc.setBounds(600,360,200,80);
        probDesc.setBorder( BorderFactory.createLineBorder(Color.black));
        probDesc.setText("Enter Description");
        probDesc.repaint();
        probDesc.setLocation(600,360);
        probDesc.setSize(200,80);
        frame.add(probDesc);




        JButton finBtn = new JButton("Finished");
        finBtn.setLocation(600,540);
        finBtn.setSize(200,40);
        frame.add(finBtn);





        ButtonGroup radgroup1 = new ButtonGroup();{ radgroup1.add(radiow);radgroup1.add(radiob);radgroup1.add(radiov);radgroup1.add(radioi);}
        ButtonGroup radgroup2 = new ButtonGroup();{ radgroup2.add(radBtoPlay);radgroup2.add(radWtoPlay);}
        ButtonGroup radgroup3 = new ButtonGroup();{ radgroup3.add(radLife);radgroup3.add(radDeath);}



        frame.setVisible(true);



        radiob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.BLACK;
                keystone = Color.BLACK;
                System.out.println("placing black stones");
            }
        });

        radiow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.WHITE;
                keystone = Color.WHITE;
                System.out.println("placing white stones");
            }
        });

        radiov.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.VALID;
                System.out.println("setting valid spaces");
            }
        });

        radioi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.INVALID;
                System.out.println("setting invalid spaces");
            }
        });

        keyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.KEYSTONE;
            }
        });


        finBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("button pressed");

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter("problem1.txt");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                writer.write("hello") ;

            }
        });

    }


}
