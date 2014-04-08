package dreamwalker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

/**
 *
 * @author Emilio
 */
public class Menu { 
    
    private final Botones play;
    private final Botones instructions;
    private final Botones trophies;
    private final Image back;
    
    /**
     * Metodo constructor
     * @param background imagen de fondo
     */
    public Menu(Image background) {
        this.back = background;
        play = new Botones(2*Base.getW()/3, Base.getH()/5, "Images/Botones/play.png");
        instructions = new Botones(2*Base.getW()/3, 2*Base.getH()/5, "Images/Botones/instructions.png");
        trophies = new Botones(2*Base.getW()/3, 3*Base.getH()/5, "Images/Botones/trophies.png");
    }
    /**
     * Dibuja la pantalla menu
     * @param g graficos
     * @param juego juego
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        
        g.drawImage(back, 0, 0, juego);
        g.drawImage(play.getImagenI(), play.getPosX(), play.getPosY(), juego);
        g.drawImage(instructions.getImagenI(), instructions.getPosX(), instructions.getPosY(), juego);
        g.drawImage(trophies.getImagenI(), trophies.getPosX(), trophies.getPosY(), juego);
        
    }

    /**
     * Revisa clicks en los botones
     * @param e 
     */

    public void mouseClicked (MouseEvent e) {
        if(JFrameDreamWalker.STATUS == JFrameDreamWalker.STATUS.MENU) {
            if (play.contiene (e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.GAME;
            } else if (instructions.contiene(e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.INSTRUCTIONS;
            } else if (trophies.contiene(e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.TROPHIES;
            }
        }
    }
}
