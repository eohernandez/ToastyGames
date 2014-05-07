//
//  Created by manolo on Apr 7, 2014.
//  Copyright (c) 2014 manolo. All rights reserved.
//
package dreamwalker;

import java.awt.Image;

/**
 *
 * @author manolo
 */
public class Fox extends Base {

    private int jumps, velX, velY;
    private Animacion stand, anim;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean aterriza;
    private static boolean brinco = false, brincoDoble = false, death = false;
    public static int acceleracion = 1;

    /**
     * Metodo constructor default.
     */
    public Fox() {
        super(0, 0, null);
        moveRight = false;
        moveLeft = false;
        death = false;

        jumps = 0;
        velX = 0;
        velY = 0;
    }

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Objeto</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto.
     * @param posY es el <code>posiscion en y</code> del objeto.
     * @param a es la <code>animacion</code> del objeto.
     */
    public Fox(int posX, int posY, Animacion a) {
        super(posX, posY, a);
        moveRight = false;
        moveLeft = false;
        death = false;
        jumps = 0;
        velX = 0;
        velY = 0;
    }

    /**
     * Metodo que hace que el personaje principal brinque.
     */
    void jump() {
        brinco = true;
        if (jumps < 2) {
            jumps++;
            velY = 18;
        }
    }

    /**
     * Metodo que hace que el personaje principal brinque doble.
     */
    void jumpDouble() {
        if (brinco && jumps < 3 && jumps > 1) {
            brincoDoble = true;
            jumps++;
            velY = 18;
        }
    }

    /**
     * Metodo que regresa si el personaje se encuentra en el aire
     */
    public boolean getBrinca() {
        return brinco;
    }

    /**
     * Metodo que regresa si el personaje se encuentra en el aire
     */
    public boolean getBrincaDoble() {
        return brincoDoble;
    }

    /**
     * Metodo que regresa si el personaje ya aterrizo
     */
    public boolean getAterriza() {
        return aterriza;
    }

    /**
     * Metodo cambia el estatus de aterriza
     *
     * @param b booleano
     */
    public void setAterriza(boolean b) {
        aterriza = b;
    }

    /**
     * Metodo que regresa si el personaje ya murio
     */
    public boolean getDeath() {
        return death;
    }

    /**
     * Metodo cambia el estatus de death
     *
     * @param b booleano
     */
    public void setDeath(boolean b) {
        death = b;
        if (b) {
            JFrameDreamWalker.deathSound.play();
            JFrameDreamWalker.backMusic.play();
        }
    }

    /**
     * within. regresa si la imagen se encuentra dentro del rango
     *
     * @param x posicion en x
     * @param xf posicion en x final
     * @param posX posicion en x
     * @param anchoFox ancho de la imagen posX
     *
     * @return si es que se encuentra dentro del rango
     */
    public boolean within(int x, int xf, int posX, int anchoFox) {
        if (posX > x && posX + anchoFox < xf) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo que se llama cuando el personaje toca el suelo al caer de un
     * brinco.
     */
    void landed() {
        jumps = 0;
        velY = 0;
        brinco = false;
        brincoDoble = false;
        aterriza = true;
    }

    /**
     * Metodo actualiza la velocidad en y si es que brinco el objeto
     */
    public void brinca() {
        if (brinco) {
            setY(getY() - acceleracion * velY);
            velY -= 1;
        }
    }

    /**
     * Metodo actualiza la velocidad en y si el objeto esta callendo
     */
    public void cae() {
        if (!aterriza && !brinco) {
            setY(getY() - acceleracion * velY);
            velY -= 1;
        }
        if (aterriza && !brinco) {
            setY(getY());
            velY = 0;
        }
    }

    /**
     * Metodo que regresa el numero de brincos del personaje
     *
     * @return jumps
     */
    public int getJumps() {
        return jumps;
    }

    /**
     * Metodo que regresa la velocidad en X del personaje principal.
     *
     * @return velX
     */
    int getVelX() {
        return velX;
    }

    /**
     * Metodo que regresa la velocidad en Y del personaje principal.
     *
     * @return velY
     */
    int getVelY() {
        return velY;
    }

    /**
     * Metodo que cambia la velocidad en X del personaje principal.
     *
     * @param v es la velocidad en X
     */
    void setVelX(int v) {
        velX = v;
    }

    /**
     * Metodo que cambia la velocidad en X del personaje principal.
     *
     * @param v es el cambio en la velocidad en X
     */
    void addVelX(int v) {
        velX += v;
    }

    /**
     * Metodo que cambia la velocidad en Y del personaje principal.
     *
     * @param v es la velocidad en Y
     */
    void setVelY(int v) {
        velY = v;
    }

    /**
     * Metodo que cambia la velocidad en Y del personaje principal.
     *
     * @param v es el cambio en la velocidad en Y
     */
    void addVelY(int v) {
        velY += v;
    }

    /**
     * Metodo que cambia la animacion usada cuando el personaje principal esta
     * animado.
     *
     * @param a es la animacion
     */
    void setAnim(Animacion a) {
        anim = a;
    }

    /**
     * Metodo de acceso que regresa la imagen de animacion
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * icono.
     */
    public Image getImagenA() {
        return anim.getImagen();
    }

    /**
     * Metodo que cambia la animacion usada cuando el personaje principal no
     * camina.
     *
     * @param a es la animacion
     */
    void setStand(Animacion a) {
        stand = a;
    }

    /**
     * Metodo de acceso que regresa la imagen de standing
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * icono.
     */
    public Image getImagenS() {
        return stand.getImagen();
    }

    /**
     * Metodo que activa la direccion donde se movio el objeto
     *
     * @param b booleano
     */
    public void setMoveRight(boolean b) {
        moveRight = b;
    }

    /**
     * Método que me regresa un booleano si se movio hacia la derecha
     *
     * @return boolean
     */
    public boolean getMoveRight() {
        return moveRight;
    }

    /**
     * Metodo que activa si se movio a la izquierda
     *
     * @param b booleano
     */
    public void setMoveLeft(boolean b) {
        moveLeft = b;
    }

    /**
     * Método que regresa un booleano si se movio hacia la izquierda
     *
     * @return boolean
     */
    public boolean getMoveLeft() {
        return moveLeft;
    }

    /**
     * Actualiza la imagen actual de las animación.
     *
     * @param t un <code>long</code> con el tiempo a actualizar
     */
    public void actualiza(long t) {
        animacion.actualiza(t);
        stand.actualiza(t);
        anim.actualiza(t);
    }
}
