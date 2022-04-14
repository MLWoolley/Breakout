/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author Maddie
 */
public class Breakout {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createWindow("Breakout");
    }
    
    public static void createWindow(String frameTitle) {
        
        //declare dimensions of window
        int frameHeight = 600;
        int frameWidth = 840;
        
        //create jpanels
        CardLayout cl = new CardLayout();
        JPanel cards = new JPanel(cl);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        //Game over
        JPanel gameOver = new JPanel();
        JLabel labelGO = new JLabel("GAME OVER");
        gameOver.setBackground(Color.RED);
        labelGO.setForeground(Color.BLACK);
        labelGO.setFont(new Font("Arial Black", Font.PLAIN, 72));
        gameOver.setLayout(new GridBagLayout());
        gameOver.add(labelGO);
        
        //You win
        JPanel youWin = new JPanel();
        JLabel labelW = new JLabel("YOU WIN");
        youWin.setBackground(Color.GREEN);
        labelW.setForeground(Color.BLACK);
        labelW.setFont(new Font("Arial Black", Font.PLAIN, 72));
        youWin.setLayout(new GridBagLayout());
        youWin.add(labelW);
        
        Score score = new Score(); //string works as an icon, figure out how to increment score
        score.setBackground(Color.BLACK);
        score.setForeground(Color.WHITE);
        score.setOpaque(true);
        score.setVerticalTextPosition(JLabel.CENTER);
        score.setHorizontalTextPosition(JLabel.CENTER);
        //add component to top 40 pixels of jpanel
        int sw = 75;
        score.setBounds(frameWidth/2-(sw*3/4), 0, sw, 20);
        //score.setPrefferedSize(0, 0, 840, 40); //trying this
        panel.add(score);
        
        //add cards
        cards.add(panel, "main");
        cards.add(gameOver, "game over");
        cards.add(youWin, "you win");
        cl.show(cards, "main");
        
        //create and set up the window
        int borderWidth = 20;
        JFrame frame = new JFrame(frameTitle);
        //create space at the edges of the game
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, borderWidth));
        frame.setContentPane(cards); // should be panel
        //set application to end when window closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set dimensions of window
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        //Display the window
        frame.setVisible(true);
        
        //create blocks
        int blockWidth = 80;
        int blockHeight = 40;
        //use these to create blocks in a matrix - dimensions 10x5
        
        Block myBlock;
        String[] colors = {"red", "yellow", "green", "blue"};
        Block[] blocks = new Block[50];
        
        //start posY is 40
        for (int x = 0; x < 10; x++){
            for (int y = 0; y < 5; y++){
                String color = colors[(x + y) % 4]; //make block pattern
                myBlock = new Block(x*blockWidth, y*blockHeight + 60, blockWidth, blockHeight, color);
                panel.add(myBlock);
                blocks[y*10 + x] = myBlock;//set blocks, in x then y order
            }
        }
        
        //create platform
        int platWidth = 150;
        int platY = 490;
        Platform platform = new Platform(0, platY, platWidth, 15, frameWidth - borderWidth*2);
        
        //create ball
        Ball ball = new Ball(50, platY-25, 25, 25, Color.WHITE, platWidth, platY);
        ball.setBounds(frameWidth-borderWidth*2, frameHeight-borderWidth*2);
        
        //set panel
        panel.setBackground(Color.BLACK);
        panel.add(platform);
        panel.add(ball);
        panel.repaint();
        
        //movement of platform
        //help from https://stackoverflow.com/questions/28003395/java-move-sprite-constantly-while-key-binding-is-pressed
        
        class MoveAction extends AbstractAction{
            int moveBy;
            
            public MoveAction(int myMove){
                this.moveBy = myMove;
            }
            
            @Override
            public void actionPerformed(ActionEvent e){
                platform.move(moveBy);
                platform.repaint();
            }
        }
        
        //... Instance variables for the ball animiation
        int interval = 35;  // Milliseconds between updates.
        Timer timer = new Timer(interval, null); // Timer fires to anmimate one step.
        
        
        //from http://www.fredosaurus.com/notes-java/examples/animation/40BouncingBall/bouncingball.html
        
        class BallAction implements ActionListener {
        //================================================== actionPerformed
        /** ActionListener of the timer.  Each time this is called,
         *  the ball's position is updated, creating the appearance of
         *  movement.
         *@param e This ActionEvent parameter is unused.
         */
            @Override
            public void actionPerformed(ActionEvent e) {
                ball.move(platform.getX());  // Move the ball.
                checkCollisions(); //return a bool, then tell timer to stop
                ball.repaint(); //repaint panel
            }
            
            public void checkCollisions() {

                Rectangle r1 = ball.getBounds();
                float rad = ball.width/2; //get radius of ball for relative position detection

                for (Block block : blocks) {
                    if (block.visible == false){
                        continue;
                    }
                    Rectangle r2 = block.getBounds();

                    if (r2.intersects(r1))
                    {
                        panel.remove(block);
                        block.visible = false;
                        block.repaint();
                        panel.repaint();
                        
                        if ((r1.y-rad < r2.getMinY() || r1.y+rad > r2.getMaxY()) && r1.x-rad <= r2.getMaxX() && r1.x+rad >= r2.getMinX()){
                            ball.reverseY();
                        }
                        else {  ball.reverseX(); }
                        score.addScore();
                        ball.IncreaseVelocity(score.getScore());
                        break;
                    }   
                }
                Line2D.Double line = new Line2D.Double(0.0, platY+5, frameWidth, platY+5);
                if (r1.intersectsLine(line)){
                    timer.stop();
                    cl.show(cards, "game over");
                }
                if (score.getScore() == 5000){
                    cl.show(cards, "you win");
                }
            }
        }
        
        timer.addActionListener(new BallAction());
        timer.start();
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "move left");
        panel.getActionMap().put("move left", new MoveAction(-20));

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "move right");
        panel.getActionMap().put("move right", new MoveAction(20));
        
    }

    
}
