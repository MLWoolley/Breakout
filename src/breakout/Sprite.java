package breakout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maddie
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 *
 * @author Maddie
 */
abstract class Sprite extends JPanel {
    int posX;
    int posY;
    int width;
    int height;
    Color color;
    
    Sprite(int myPosX, int myPosY, int myWidth, int myHeight, Color myColor){
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

