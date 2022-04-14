/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import static java.lang.Integer.signum;
import static java.lang.Math.abs;

/**
 *
 * @author Maddie
 */
public class Ball extends Sprite{
    
    //locate platform to bounce off of
    private final int platWidth;
    private final int platY;
    
    private int velocityX;   // Pixels to move each time move() is called.
    private int velocityY;
    
    private int limX;  // Maximum permissible x, y values.
    private int limY;
    
    public Ball(int myPosX, int myPosY, int myWidth, int myHeight, Color myColor, int myPlatWidth, int myPlatY) {
        super(myPosX, myPosY, myWidth, myHeight, myColor);
        velocityX = 3;
        velocityY = velocityX;
        platWidth = myPlatWidth;
        platY = myPlatY;
        this.setBackground(Color.BLACK);
    }
    
    public void IncreaseVelocity(int score){
        if (score % 2000 == 0){
            velocityX = (abs(velocityX) + 1) * signum(velocityX);
            velocityY = velocityX * signum(velocityY);
        }
    }
    
    public void setBounds(int panelWidth, int panelHeight) {
        limX  = panelWidth  - width;
        limY = panelHeight - height*2;
    }
    
    //get bounds - this is actually the ball's position, due to the way it moves -- should this change?
    @Override
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }
    
    public void reverseX(){
        velocityX = -velocityX;
    }
    public void reverseY(){
        velocityY = -velocityY;
    }
    
    public void move(int platMinX) {
        //... Move the ball at the give velocity.
        posX += velocityX;
        posY += velocityY;        
        
        //... Bounce the ball off the walls if necessary.
        if (posX < 0) {                  // If at or beyond left side
            posX = 0;            // Place against edge and
            velocityX = -velocityX; // reverse direction.
        }   
        if (posX > limX) { // If at or beyond right side
            posX = limX;    // Place against right edge.
            velocityX = -velocityX;  // Reverse direction.
        }
        //if touching platform, bounce
        //at the very least, go left on the left-hand side of the platform
        //and right when bouncing off the right half
        if (posY + height > platY && posX > platMinX-width/2 && posX < platMinX + platWidth-width/2){
            posY = platY - height;
            velocityY = -velocityY;
            if (velocityX > 0 && posX < platMinX + platWidth/2 - width/2){ //left half
            velocityX = -velocityX;
            }
            if (velocityX < 0 && posX > platMinX + platWidth/2 - width/2){ //right half
            velocityX = -velocityX;
            }
        }
        if (posY < 0) {                 // if we're at top
            posY = 0;
            velocityY = -velocityY;
        }    
        if (posY > limY) { // if we're at bottom
            posY = limY;
            velocityY = -velocityY;
        }
        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(color);
        graphics.fillOval(0, 0, width, height);
    }
    
    
    //======================================================== setPosition
    public void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }
}
