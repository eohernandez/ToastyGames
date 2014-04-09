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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Trophies implements MouseListener {
    
    private final Botones goBack;
    private final Image back;
    
    /**
     * Metodo constructor
     * @param background
     */
    public Trophies(Image background) {
        this.back = background;

        goBack = new Botones (Base.getW()/10, 4*Base.getH()/5, "Images/Botones/goBack.png");
    }
    
    /**
     * Metodo para pintar
     * @param g
     * @param juego
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        
        g.drawImage(back, 0, 0, juego);
        g.drawImage(goBack.getImagenI(), goBack.getPosX(), goBack.getPosY(), juego);
        
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