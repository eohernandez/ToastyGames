/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dreamwalker;

/**
 *
 * @author Emilio
 */

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Trophies implements MouseListener {
    
    private final Botones goBack;
    private final Botones reset;
    private final Image back;
    private HighScore a = new HighScore(); 
    
    /**
     * Metodo constructor
     * @param background
     */
    public Trophies(Image background) {
        this.back = background;

        goBack = new Botones (Base.getW()/10, 4*Base.getH()/5, "Images/Botones/goBack.png");
        reset = new Botones (Base.getW()/10, 3*Base.getH()/5, "Images/Botones/reset.gif");
    }
    
    /**
     * Metodo para pintar
     * @param g
     * @param juego
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        try {
            a.leeArchivo();
        } catch (IOException ex) {
            Logger.getLogger(Trophies.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.drawImage(back, 0, 0, juego);
        g.drawImage(goBack.getImagenI(), goBack.getPosX(), goBack.getPosY(), juego);
        g.drawImage(reset.getImagenI(), reset.getPosX(), reset.getPosY(), juego);
        
        g.setFont(new Font("Serif", Font.BOLD, 34));
        for(int i =0; i < 5;i++){
            g.drawString(a.getHighscoreName(i) + " " + a.getHighscore(i), 500, 300 + i*30);
        }
        
    }

    /**
     * Revisa clicks en los botones
     * @param e 
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        if (JFrameDreamWalker.status == JFrameDreamWalker.STATUS.TROPHIES) {
            if (goBack.contiene (e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.MENU;
            }else if (reset.contiene (e.getX(), e.getY())) {
                try {
                    a.initHighScore();
                    a.grabaArchivo();
                } catch (IOException ex) {
                    Logger.getLogger(Trophies.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    

    @Override
    public void mousePressed (MouseEvent e) {}

    @Override
    public void mouseReleased (MouseEvent e) {}

    @Override
    public void mouseEntered (MouseEvent e) {}

    @Override
    public void mouseExited (MouseEvent e) {}
}
