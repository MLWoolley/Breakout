/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Maddie
 */
public class Block extends SpriteComponent{
    
    boolean visible = true; //this is turned off when block is destroyed
    private BufferedImage image; //stores block image
    String fullPathName = "/Users/Maddie/NetBeansProjects/Breakout/src/breakout/Images/";
    
    public Block(int myPosX, int myPosY, int myWidth, int myHeight, String color) {
        super(myPosX, myPosY, myWidth, myHeight, Color.WHITE);
        try {
            image = ImageIO.read(new File(fullPathName+color+"Block.jpeg"));
        } catch (IOException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        
        //set block to premade images - java ensures it is the right size
        graphics.drawImage(image, 0, 0, 80, 40, null);
    }
    
    public void destroy(){
        visible = false;
        this.setVisible(visible);
        this.repaint();
    }
}
