import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Board extends Canvas   {

    int[][] stones = new int[19][19];
    int turn= 1 ;


    public Board(){

        addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse was clicked");
                int x = e.getX();
                int y = e.getY();


                x -= 30;
                y -= 30;

                x = x % 30 > 15 ? (x + (30 - (x % 30))) : (x - (x % 30));
                y = y % 30 > 15 ? (y + (30 - (y % 30))) : (y - (y % 30));
                if (x <555 && y < 555) {
                    if (stones[x / 30][y / 30] == 0) {
                        stones[x / 30][y / 30] = turn;
                        turn = turn == 1 ? -1 : 1;
                    } else {
                        System.out.println("Stone already there or out of bound");
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



    public void drawwhite(Graphics g,  int x, int y ) {
        g.setColor(Color.WHITE);
        g.fillOval(x-5, y-5, 10, 10);
    }

    public void drawblack(Graphics g,  int x, int y ) {
        g.setColor(Color.BLACK);
        g.fillOval(x-5, y-5, 10, 10);
    }

    public void paint(Graphics g ){
        //System.out.println(Board.stones[0][0]);
        //System.out.println(stones[0][0]);
        for (int i =30; i < 600; i+=30){
            g.setColor(Color.RED);
            g.drawLine(i,30,i, 570);
            g.drawLine(30,i,570, i);
        }

        for(int i=0; i<stones.length; i++) {
            for(int j=0; j<stones[i].length; j++) {
                if ((stones[i][j])==1){
                    drawwhite(g,i*30+30,j*30+30);
                }else if((stones[i][j])==-1){
                    drawblack(g,i*30+30,j*30+30);
                }
            }
        }



        //drawwhite(g,60 ,90);


    }
    public static void main(String[] args){
        System.out.println("Hello, World");





        JFrame frame = new JFrame("New Frame");



        Board b = new Board();

        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(b);


    }
}
