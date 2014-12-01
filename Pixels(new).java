/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edgedetection;

/**
 *
 * @author nathan
 */
public class Pixels {
    short red, green, blue, gray;
    protected Pixels(int color){
        this.red = (short)(color >> 16 & 0xFF);
        this.green = (short)(color >> 8 & 0xFF);
        this.blue = (short)(color & 0xFF);
        this.gray = (short)((.21 * this.red) + (.72 * this.green) + (.07 * this.blue));
    }
    protected short getRed(){return this.red;}
    protected short getGreen(){return this.green;}
    protected short getBlue(){return this.blue;}
    protected short getGray(){return this.gray;}
}
