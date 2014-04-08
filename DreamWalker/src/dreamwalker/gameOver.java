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

public class gameOver implements MouseListener {
    
    private final Botones newGame;
    private final Botones exit;
    private final Image back;
    
    /**
     * Metodo constructor
     * @param background imagen de fondo
     */
    public gameOver(Image background) {
        this.back = background;
        newGame = new Botones(Base.getW()/5, Base.getH()/2, "Images/Botones/newGame.png");
        exit = new Botones(4*Base.getW()/5, Base.getH()/2, "Images/Botones/exit.png");
        newGame.setPosX(newGame.getPosX() - newGame.getAncho()/2);
        newGame.setPosY(newGame.getPosY() - newGame.getAlto()/2);
        exit.setPosX(exit.getPosX() - exit.getAncho()/2);
        exit.setPosY(exit.getPosY() - exit.getAlto()/2);
    }
    /**
     * Dibuja la pantalla menu
     * @param g
     * @param juego
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        
        g.drawImage(back, 0, 0, juego);
        g.drawImage(newGame.getImagenI(), newGame.getPosX(), newGame.getPosY(), juego);
        g.drawImage(exit.getImagenI(), exit.getPosX(), exit.getPosY(), juego);
        
    }

    /**
     * Checa si se pico algun boton
     * @param e 
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        if(JFrameDreamWalker.status == JFrameDreamWalker.STATUS.GAMEOVER) {
            if (newGame.contiene (e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.MENU;
            } else if (exit.contiene(e.getX(), e.getY())) {
                JFrameDreamWalker.playing = false;
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
