/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nathan
 */

package pixels;

public class ColorPixel {
    int red, green, blue, alpha, sepiaRed, sepiaGreen, sepiaBlue, lightGray, averageGray, lumiGray;
    protected ColorPixel(int colorFromBuffRGB){
        // A pixel from RGB is a 4 byte, 32 bit integer formatted
        // First 8- alpha. Second 8- red. Third 8- green. Fourth 8- blue
        // 2^8 - 1 = 255, so this ranges 0 to 255
        int r, g, b, a, sr, sg, sb;
        r = (colorFromBuffRGB >> 16) & 0x000000FF;
        g = (colorFromBuffRGB >> 8) & 0x000000ff;
        b = colorFromBuffRGB & 0x000000FF;
        a = (colorFromBuffRGB >> 24) & 0x000000FF;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
        sr = (int)((r * .393) + (g * .769) + (b * .189));
        sg = (int)((r * .349) + (g * .686) + (b * .168));
        sb = (int)((r * .272) + (g * .534) + (b * .131));
        this.sepiaRed = getMin(255, sr);
        this.sepiaGreen = getMin(255, sg);
        this.sepiaBlue = getMin(255, sb);
        // Lightness = max(r, g, b) + min(r, g, b) / 2
        this.lightGray = ((getMax(b, getMax(r, g))) + getMin(b, getMin(r, g))) >> 1;
        // Average = (r + g + b) / 3
        this.averageGray = (r + b + b) / 3;
        // Luminosity = .21 R + .72 G + .07 B
        this.lumiGray = (int)((.21 * r) + (.72 * g) + (.07 * b));
    }
    protected int getMax(int a, int b){
        if (a > b){return a;}
        else{return b;}
    }
    protected int getMin(int a, int b){
        if(a < b){ return a;}
        else{return b;}
    }
    protected int getRed() { return this.red;}
    protected int getGreen() { return this.green;}
    protected int getBlue(){return this.blue;}
    protected int getAlpha(){return this.alpha;}
    protected int getLightGray(){return this.lightGray;}
    protected int getAverageGray(){return this.averageGray;}
    protected int getLumiGray(){return this.lumiGray;}
    protected int getSepiaRed(){return this.sepiaRed;}
    protected int getSepiaGreen(){return this.sepiaGreen;}
    protected int getSepiaBlue(){return this.sepiaBlue;}
}

