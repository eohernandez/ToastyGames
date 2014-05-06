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
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Instructions implements MouseListener {
    
    private final Botones goBack;
    private final Image back;
    private final Image instrucciones;
    
    /**
     * Metodo constructor
     * @param background
     */
    public Instructions(Image background) {
        this.back = background;
        instrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Instrucciones.png"));
			
        
        goBack = new Botones (Base.getW()/10, 4*Base.getH()/5, "Images/Botones/goBack.png");
     }
    
    /**
     * Metodo para pintar
     * @param g elemento grafico
     * @param juego JFrame
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        
        g.drawImage(back, 0, 0, juego);
        g.drawImage(instrucciones,0, Base.getH()/10, juego);
        g.drawImage(goBack.getImagenI(), goBack.getPosX()-100, goBack.getPosY(), juego);
    }

    /**
     * Revisa clicks en los botones
     * @param e MouseEvent
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        if (JFrameDreamWalker.status == JFrameDreamWalker.STATUS.INSTRUCTIONS) {
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
