/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakout;

import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author Maddie
 */
public class Score extends JLabel {
    private int value;
    
    public Score(){
        value = 0;
        this.setText("Score: 0000");
        this.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
    }
    
    public void addScore(){
        value += 100;
        this.setText(String.format("Score: %04d", value));
        this.setVerticalAlignment(0);
        this.setHorizontalAlignment(0);
    }
    
    public int getScore(){
        return value;
    }
}
