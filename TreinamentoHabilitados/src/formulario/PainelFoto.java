/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package formulario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Guima
 */
public class PainelFoto extends JPanel {    
    private String path="";
    private BufferedImage img;
    int cont=0;
    public PainelFoto(BufferedImage img){
        this.img = img;
    }
    
    public PainelFoto(){}
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Pintando a painel" + path + " !" + ++cont);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
        
    }
//
//    @Override
//    public void update(Graphics g) {
//        super.update(g); //To change body of generated methods, choose Tools | Templates.
//        System.out.println("Atualizando a painel" + path + " !");
//        g.drawImage(new ImageIcon(path).getImage(),0,0,this.getWidth(),this.getHeight(),this);
//    }
//    
//    
    
    
}
