package dreamwalker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Menu implements MouseListener {
    private final Botones PLAY;
    private final Botones INSTRUCTIONS;
    private final Botones TROPHIES;
    private final Botones QUIT;
    private final Image BACKGROUND;
    private Image menuFox;

    
    /**
     * Metodo constructor
     * @param background imagen de fondo
     * @param fox imagen de zorro
     */
    public Menu(Image background, Image fox) {
        this.BACKGROUND = background;
        PLAY = new Botones(Base.getW() - 450, Base.getH()/5, "Images/Botones/newGame.png");
        INSTRUCTIONS = new Botones(Base.getW() - 450, 2*Base.getH()/5, "Images/Botones/instructions.png");
        TROPHIES = new Botones(Base.getW() - 450, 3*Base.getH()/5, "Images/Botones/trophies.png");
        QUIT = new Botones(Base.getW() - 450, 4*Base.getH()/5, "Images/Botones/exit.png");
        menuFox=fox;
    }
	
    /**
     * Dibuja la pantalla menu
     * @param g elemento grafico
     * @param juego JFrame
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        
        g.drawImage(BACKGROUND, 0, 0, juego);
        g.drawImage(PLAY.getImagenI(), PLAY.getPosX(), PLAY.getPosY(), juego);
        g.drawImage(INSTRUCTIONS.getImagenI(), INSTRUCTIONS.getPosX(), INSTRUCTIONS.getPosY(), juego);
        g.drawImage(TROPHIES.getImagenI(), TROPHIES.getPosX(), TROPHIES.getPosY(), juego);
        g.drawImage(QUIT.getImagenI(), QUIT.getPosX(), QUIT.getPosY(), juego);
        g.drawImage(menuFox, 20, 480, juego);
    }

    /**
     * Revisa clicks en los botones
     * @param e MouseEvent
	 * @param juego JFrame
     */
    public void mouseClicked (MouseEvent e, JFrameDreamWalker juego) {
        if(JFrameDreamWalker.status == JFrameDreamWalker.STATUS.MENU) {
            if (PLAY.contiene (e.getX(), e.getY())) {
                juego.restart();
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.GAME;
            } else if (INSTRUCTIONS.contiene(e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.INSTRUCTIONS;
            } else if (QUIT.contiene(e.getX(), e.getY())) {
                JFrameDreamWalker.playing = false;
            } else if (TROPHIES.contiene (e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.TROPHIES;
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
    @Override
    public void mouseClicked(MouseEvent e) {}
}