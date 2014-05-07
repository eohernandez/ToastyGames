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

public class gameOver implements MouseListener {

    private final Botones newGame;
    private final Botones exit;
    private final Image[] back;
    private final Botones goBack;
    private int trofeo = -1;

    /**
     * Metodo constructor
     *
     * @param background imagen de fondo
     */
    public gameOver(Image[] background) {
        this.back = background;
        newGame = new Botones(Base.getW() / 5, 3 * Base.getH() / 6, "Images/Botones/newGame.png");
        goBack = new Botones(Base.getW() / 5, 4 * Base.getH() / 6, "Images/Botones/goBack.png");
        exit = new Botones(Base.getW() / 5, 5 * Base.getH() / 6, "Images/Botones/exit.png");

        goBack.setPosX(goBack.getPosX() - goBack.getAncho() / 2);
        goBack.setPosY(goBack.getPosY() - goBack.getAlto() / 2);
        newGame.setPosX(newGame.getPosX() - newGame.getAncho() / 2);
        newGame.setPosY(newGame.getPosY() - newGame.getAlto() / 2);
        exit.setPosX(exit.getPosX() - exit.getAncho() / 2);
        exit.setPosY(exit.getPosY() - exit.getAlto() / 2);

    }

    /**
     * Dibuja la pantalla menu
     *
     * @param g elemento grafico
     * @param juego JFrame
     */
    public void render(Graphics g, JFrameDreamWalker juego) {

        g.setFont(new Font("Sylfaen", Font.BOLD, 40));

        g.drawString("" + JFrameDreamWalker.temp, Base.getW() / 2 - 10, 8 * Base.getH() / 20);
        //System.out.println(a.getTrofeo());
        if (trofeo == 0) {
            g.drawImage(back[0], 0, 0, juego);
        } else if (trofeo == 1) {
            g.drawImage(back[1], 0, 0, juego);
        } else if (trofeo == 2) {
            g.drawImage(back[2], 0, 0, juego);
        }
        g.drawImage(newGame.getImagenI(), newGame.getPosX(), newGame.getPosY(), juego);
        g.drawImage(exit.getImagenI(), exit.getPosX(), exit.getPosY(), juego);
        g.drawImage(goBack.getImagenI(), goBack.getPosX(), goBack.getPosY(), juego);
    }

    /**
     * Checa si se pico algun boton
     *
     * @param e MouseEvent
     * @param juego JFrame
     */
    public void mouseClicked(MouseEvent e, JFrameDreamWalker juego) {
        if (JFrameDreamWalker.status == JFrameDreamWalker.STATUS.GAMEOVER) {
            if (newGame.contiene(e.getX(), e.getY())) {
                juego.restart();
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.GAME;
                JFrameDreamWalker.playMusic.play();
                JFrameDreamWalker.backMusic.stop();
            } else if (exit.contiene(e.getX(), e.getY())) {
                JFrameDreamWalker.playing = false;
            } else if (goBack.contiene(e.getX(), e.getY())) {
                JFrameDreamWalker.status = JFrameDreamWalker.STATUS.MENU;

            }
        }
    }

    /**
     * Recive el numero del trofeo
     *
     * @param t numero de trofeo
     */
    public void setTrofeo(int t) {
        trofeo = t;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
