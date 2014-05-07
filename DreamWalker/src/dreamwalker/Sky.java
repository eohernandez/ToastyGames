//
//  Created by manolo on Apr 21, 2014.
//  Copyright (c) 2014 manolo. All rights reserved.
//
package dreamwalker;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author manolo
 */
public class Sky extends Base {

    private Animacion sol, luna;
    private double skyY, puebloX, solX, solY, lunaX, lunaY;
    private int nube1X, nube2X, nube3X, nube4X, nube1Y, nube2Y, nube3Y, nube4Y, sX, sY, lX, lY;
    private int nube1vX, nube2vX, nube3vX, nube4vX, nube1, nube2, nube3, nube4;
    private Image nubes[], pueblo;

    /**
     * Metodo constructor default.
     */
    public Sky() {
        super(0, 0, null);
    }

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Objeto</code>.
     *
     * @param a es la <code>animacion</code> del objeto.
     */
    public Sky(Animacion a) {
        super(0, 0, a);
        sol = new Animacion();
        Image imagen;
        for (int x = 1; x <= 12; x++) {
            imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Sol/Sol" + x + ".png"));
            sol.sumaCuadro(imagen, 100);
        }
        luna = new Animacion();
        for (int i = 0; i < 3; i++) {
            for (int x = 1; x <= 3; x++) {
                imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Luna/Luna" + x + ".png"));
                luna.sumaCuadro(imagen, 400);
            }
        }
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Luna/Luna4.png"));
        luna.sumaCuadro(imagen, 100);
        solX = lunaX = -270;
        solY = lunaY = 400;
        if (Math.random() > 0.5) {
            skyY = -2749;
        } else {
            skyY = -6480 + 720;
        }
        puebloX = 300;
        nube1X = nube2X = nube3X = nube4X = 1160;
        nube1X += (int) (Math.random() * 100);
        nube2X += (int) (Math.random() * 100);
        nube3X += (int) (Math.random() * 100);
        nube4X += (int) (Math.random() * 100);
        nube1Y = (int) (Math.random() * 200) + 20;
        nube2Y = (int) (Math.random() * 200) + 20;
        nube3Y = (int) (Math.random() * 200) + 20;
        nube4Y = (int) (Math.random() * 200) + 20;
        nube1vX = (int) (Math.random() * 3) + 2;
        nube2vX = (int) (Math.random() * 3) + 2;
        nube3vX = (int) (Math.random() * 3) + 2;
        nube4vX = (int) (Math.random() * 3) + 2;
        nubes = new Image[4];
        for (int x = 1; x <= 3; x++) {
            nubes[x - 1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Cloud" + x + ".png"));
        }
        nube1 = (int) (Math.random() * 3);
        nube2 = (int) (Math.random() * 3);
        nube3 = (int) (Math.random() * 3);
        nube4 = (int) (Math.random() * 3);
        pueblo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Pueblo.png"));
    }

    /**
     * Metodo usado para avanzar la hora del dia.
     *
     * @param t es el tiempo transcurrido para avanzar las animaciones
     */
    public void move(long t) {
        int X = 1; // para testing, aumenta la velocidad de movimiento del cielo, sol, luna, nubes
        sol.actualiza(t);
        luna.actualiza(t);
        if (Math.floor(skyY) >= 0) {
            skyY = -6480 + 720;
        } else {
            skyY += 0.32 * X;
        }

        if (skyY < 0 && skyY < -2750 || (0 < solX && solX < 1152)) {
            solX += 0.16 * X;
            if (solX < 576 - (sol.getWidth() / 2)) {
                solY -= 0.1 * X;
            } else {
                solY += 0.1 * X;
            }
        } else {
            solX = -270;
            solY = 400;
        }
        if (-2750 < skyY || (0 < lunaX && lunaX < 1152)) {
            lunaX += 0.16 * X;
            if (lunaX < 576 - (luna.getWidth() / 2)) {
                lunaY -= 0.1 * X;
            } else {
                lunaY += 0.1 * X;
            }
        } else {
            lunaX = -270;
            lunaY = 400;
        }

        sX = (int) Math.floor(solX);
        sY = (int) Math.floor(solY);
        lX = (int) Math.floor(lunaX);
        lY = (int) Math.floor(lunaY);

        if (Math.floor(puebloX) == -1500) {
            puebloX = 0;
        } else {
            puebloX -= 0.2 * X;
        }

        if (-6480 + 900 < skyY && skyY < -6480 + 2840) {
            nube1X -= nube1vX * X;
            nube2X -= nube2vX * X;
            nube3X -= nube3vX * X;
            nube4X -= nube4vX * X;
        } else {
            if (nube1X > -nubes[nube1].getWidth(null) && nube1 < 1152) {
                nube1X -= nube1vX * X;
            }
            if (nube2X > -nubes[nube2].getWidth(null) && nube2 < 1152) {
                nube2X -= nube2vX * X;
            }
            if (nube3X > -nubes[nube3].getWidth(null) && nube3 < 1152) {
                nube3X -= nube3vX * X;
            }
            if (nube4X > -nubes[nube4].getWidth(null) && nube4 < 1152) {
                nube4X -= nube4vX * X;
            }
        }

        if (nube1X < -nubes[nube1].getWidth(null)) {
            nube1X = 1165;
            nube1 = (int) (Math.random() * 3);
            nube1vX = (int) (Math.random() * 3) + 2;
            nube1Y = (int) (Math.random() * 200) + 20;
        }
        if (nube2X < -nubes[nube2].getWidth(null)) {
            nube2X = 1165;
            nube2 = (int) (Math.random() * 3);
            nube2vX = (int) (Math.random() * 3) + 2;
            nube2Y = (int) (Math.random() * 200) + 20;
        }
        if (nube3X < -nubes[nube3].getWidth(null)) {
            nube3X = 1165;
            nube3 = (int) (Math.random() * 3);
            nube3vX = (int) (Math.random() * 3) + 2;
            nube3Y = (int) (Math.random() * 200) + 20;
        }
        if (nube4X < -nubes[nube4].getWidth(null)) {
            nube4X = 1165;
            nube4 = (int) (Math.random() * 3);
            nube4vX = (int) (Math.random() * 3) + 2;
            nube4Y = (int) (Math.random() * 200) + 20;
        }
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    @Override
    public int getY() {
        return (int) Math.floor(skyY);
    }

    /**
     * Metodo de acceso que regresa la posicion en x del pueblo
     *
     * @return posY es la <code>posicion en x</code> del pueblo.
     */
    public int getPuebloX() {
        return (int) Math.floor(puebloX);
    }

    /**
     * Dibuja el suelo
     *
     * @param g el elemento grafico
     * @param juego JFrame
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        g.drawImage(sol.getImagen(), sX, sY, juego);
        g.drawImage(luna.getImagen(), lX, lY, juego);
        g.drawImage(pueblo, getPuebloX(), 0, juego);
        if (getPuebloX() < 0) {
            g.drawImage(pueblo, getPuebloX() + 1500, 0, juego);
        }
        g.drawImage(nubes[nube1], nube1X, nube1Y, juego);
        g.drawImage(nubes[nube2], nube2X, nube2Y, juego);
        g.drawImage(nubes[nube3], nube3X, nube3Y, juego);
        g.drawImage(nubes[nube4], nube4X, nube4Y, juego);
    }
}
