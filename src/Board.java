import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.io.*;
import java.io.PrintWriter;
import java.util.ArrayList;



class Tuple{
    int a;
    int b;
    Tuple(int a, int b){
        this.a=a;
        this.b=b;
    }

    public String toString() {
        return "(" +a +"," + b+")";
    }

    public boolean equals(Tuple a) {
        return this.a==a.a && this.b ==a.b;
    }
}

public class Board extends Canvas   {
    Stones[][] stones = new Stones[19][19];
    static boolean editormode = true;
    static Stones placing= Stones.BLACK;
    Stones turn = Stones.BLACK;
    static Stones tempkeystone  = Stones.KEYBLACKSTONE;
    static Stones keystone = Stones.KEYBLACKSTONE;
    static ArrayList<Tuple> keystones = new ArrayList<>();




    public void initBoard(){
        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                stones[i][j] = Stones.EMPTY;
                if (editormode) stones[i][j] = Stones.INVALID;
            }
        }
    }

    public  Stones[][] getStones(){
        return this.stones;
    }

    public Board(JRadioButton w,JRadioButton b){

        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {

                int x = calulatePostionOnBoard(e.getX()-30);
                int y =calulatePostionOnBoard(e.getY()-30);

                System.out.println(x+""+y);


                if (x <= 18 && y <= 18) {
                    if (editormode){
                        stones[x][y] = placing;
                        if (placing== Stones.KEYBLACKSTONE || placing == Stones.KEYWHITESTONE){
                            keystones.add(new Tuple(x,y));
                            for (Tuple k:keystones){
                                stones[k.a][k.b]=placing;
                            }
                            if (placing==Stones.KEYBLACKSTONE){
                                placing = Stones.BLACK ;
                                b.setSelected(true);
                            }else{
                                placing = Stones.WHITE;
                                w.setSelected(true);
                            }
                        }else{
                            keystones.removeIf( new Tuple(x,y)::equals);
                            System.out.println(keystones);
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
                //System.out.println(x+","+y+","+turn);//these co-ords are relative to the component
                repaint();


            }

            public void mouseEntered(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {}
        });


    }

    public enum Stones {
        BLACK,WHITE,VALID,EMPTY,INVALID, KEYWHITESTONE,KEYBLACKSTONE
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

                    case KEYBLACKSTONE:
                    case KEYWHITESTONE:
                        if (keystone==Stones.KEYBLACKSTONE) {
                            drawoval(g, i * 30 + 30, j * 30 + 30, Color.BLACK, true);
                        }else{
                            drawoval(g, i * 30 + 30, j * 30 + 30, Color.WHITE, true);
                        }

                        break;


                    case EMPTY: break;


                }

            }
        }
    }







    public static void main(String[] args){

        JFrame frame = new JFrame("New Frame");
        if (editormode) editormode_init(frame);


    }

    public static void editormode_init(JFrame frame) {

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocation(400, 0);
        Container c = frame.getContentPane();
        //c.setBackground(Color.GRAY);



        JRadioButton radiob = new JRadioButton("Place Black Stones", true);
        radiob.setLocation(600, 100);
        radiob.setSize(220, 20);
        frame.add(radiob);

        JRadioButton radiow = new JRadioButton("Place White Stones");
        radiow.setLocation(600, 120);
        radiow.setSize(140, 20);
        frame.add(radiow);




        Board b = new Board(radiow,radiob);
        b.initBoard();

        b.setLocation(0, 0);
        b.setSize(600, 600);
        frame.add(b);

        JRadioButton radiov = new JRadioButton("Mark Valid Spots");
        radiov.setLocation(600, 140);
        radiov.setSize(220, 20);
        frame.add(radiov);

        JRadioButton radioi = new JRadioButton("Mark Invalid Spots");
        radioi.setLocation(600, 160);
        radioi.setSize(220, 20);
        frame.add(radioi);

        JRadioButton radBtoPlay = new JRadioButton("Black Plays First", true);
        radBtoPlay.setLocation(600, 200);
        radBtoPlay.setSize(220, 20);
        frame.add(radBtoPlay);

        JRadioButton radWtoPlay = new JRadioButton("White Plays First");
        radWtoPlay.setLocation(600, 220);
        radWtoPlay.setSize(220, 20);
        frame.add(radWtoPlay);

        JRadioButton radLife = new JRadioButton("Keystone/s needs to live", true);
        radLife.setLocation(600, 260);
        radLife.setSize(220, 20);
        frame.add(radLife);

        JRadioButton radDeath = new JRadioButton("Keystone/s need to die");
        radDeath.setLocation(600, 280);
        radDeath.setSize(220, 20);
        frame.add(radDeath);


        JButton keyBtn = new JButton("Place Key Stone");
        keyBtn.setLocation(600, 300);
        keyBtn.setSize(200, 40);
        frame.add(keyBtn);


        JEditorPane probDesc = new JEditorPane();
        probDesc.setBounds(600, 360, 200, 80);
        probDesc.setBorder(BorderFactory.createLineBorder(Color.black));
        probDesc.setText("Enter Description");
        probDesc.setLocation(600, 360);
        probDesc.setSize(200, 80);
        frame.add(probDesc);


        JButton finBtn = new JButton("Finished");
        finBtn.setLocation(600, 540);
        finBtn.setSize(200, 40);
        frame.add(finBtn);


        ButtonGroup radgroup1 = new ButtonGroup();
        {
            radgroup1.add(radiow);
            radgroup1.add(radiob);
            radgroup1.add(radiov);
            radgroup1.add(radioi);
        }
        ButtonGroup radgroup2 = new ButtonGroup();
        {
            radgroup2.add(radBtoPlay);
            radgroup2.add(radWtoPlay);
        }
        ButtonGroup radgroup3 = new ButtonGroup();
        {
            radgroup3.add(radLife);
            radgroup3.add(radDeath);
        }


        frame.setVisible(true);



        radiob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.BLACK;
                tempkeystone = Stones.KEYBLACKSTONE;
                System.out.println("placing black stones");
            }
        });

        radiow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placing = Stones.WHITE;
                tempkeystone = Stones.KEYWHITESTONE;
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
                keystone = tempkeystone;
                placing = keystone ;
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

                Stones[][] stones = b.getStones();
                for(int i=0; i<stones.length; i++) {
                    for(int j=0; j<stones[i].length; j++) {
                        switch (stones[j][i]) {
                            case BLACK: writer.write("| x ") ;
                                break;

                            case WHITE:  writer.write("| o ") ;
                                break;

                            case VALID:  writer.write("| + ") ;
                                break;

                            case INVALID:   writer.write("| - ") ;
                                break;

                            case KEYBLACKSTONE: writer.write("| X ") ;
                                break;

                            case KEYWHITESTONE:  writer.write("| O ") ;
                                break;

                            case EMPTY: break;
                        }
                    }
                    writer.write("|\r\n") ;

                }

                String whoplays = radBtoPlay.isSelected()?"Black":"White";
                String kill  = radDeath.isSelected()?"Yes":"No";
                String filekstone = keystones.toString();
                writer.write("Play: "+whoplays +"\r\n") ;
                writer.write("Kill: "+kill +"\r\n") ;
                writer.write("Stone: "+filekstone + "\r\n") ;
                writer.write("Description: "+probDesc.getText() + "\r\n") ;

                writer.close();

            }
        });

    }


}
