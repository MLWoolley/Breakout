/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maddie
 */

package breakout;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
/**
 *
 * @author Maddie
 */
abstract class SpriteComponent extends JComponent {
    int posX;
    int posY;
    int width;
    int height;
    Color color;
    
    SpriteComponent(int myPosX, int myPosY, int myWidth, int myHeight, Color myColor){
        width = myWidth;
        height = myHeight;
        posX = myPosX;
        posY = myPosY;
        color = myColor;
        this.setBounds(posX, posY, width, height); //necessary for certain sprites to appear
    }
    
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBounds(posX, posY, width, height);
    }
}

