package dreamwalker;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author daniel rodriguez
 * @author Emilio Flores
 */
public class Floor extends Base {

    private boolean movement;
    private int xInicial;
    private int yInicial;
    public static double nivel = 1.0;
    private boolean paso;
    private boolean paso1;

    /**
     * Método constructor de la clase Canasta
     *
     * @param posX posicion X del objeto
     * @param posY posicion Y del objeto
     * @param animacion Animacion
     */
    public Floor(int posX, int posY, Animacion animacion) {
        super(posX, posY, animacion);
        movement = false;
        xInicial = posX;
        yInicial = posY;
        paso = false;
        paso1 = false;
    }

    /**
     * Método que regresa el objeto al inicio
     */
    public void volverInicio() {
        
        setX( xInicial);
        setY( yInicial);

    }

    /**
     * Método que activa la direccion donde se movio el objeto
     *
     * @param b booleano
     */
    public void setMovement(boolean b) {
        movement = b;
    }

    /**
     * Método que me regresa un booleano si se movio hacia la derecha la canasta
     *
     * @return boolean
     */
    public boolean getMovement() {
        return movement;
    }

    /**
     * Método que me regresa un boleano para saber si el avion ya paso este tubo
     *
     * @return paso boolean
     */
    public boolean getPassed() {
        return paso;
    }

    /**
     * Método de modificacion de la variable <code> paso </code>
     *
     * @param itPassed
     */
    public void setPassed(boolean itPassed) {
        paso = itPassed;
    }
    
        /**
     * Método que me regresa un boleano para saber si el avion ya paso este tubo
     *
     * @return paso2 boolean
     */
    public boolean getPassed2() {
        return paso1;
    }

    /**
     * Método de modificacion de la variable <code> paso </code>
     *
     * @param itPassed
     */
    public void setPassed2(boolean itPassed) {
        paso1 = itPassed;
    }

}
