/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Maddie
 */
public class Platform extends Sprite{
    
    private final int paneWidth;
    
    public Platform(int myPosX, int myPosY, int myWidth, int myHeight, int myPaneWidth) {
        super(myPosX, myPosY, myWidth, myHeight, Color.lightGray);
        paneWidth = myPaneWidth;
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(color);
        graphics.fillRect(0, 0, width, height);
        
    }
    
    public int getX(){
        return posX;
    }
    
    public void move(int moveBy){
        
        if (!(posX <= 0 && moveBy < 0) && !(posX >= paneWidth - width && moveBy > 0)){
            posX = posX + moveBy;
            this.setBounds(posX, posY, width, height);
        }
        
    }
    
}
