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
public class FireBall extends Base {
//	Se declaran todas las variables

    private double velX;
    private double velY;
    private final double CONST = .97;
    private int xInicial;
    private int yInicial;
    private boolean movimiento;
    private long tiempoInicial;
    private long tiempoPausa;
    private static double aceleracion = 300;

    /**
     * Metdo constructor
     *
     * @param posX Pos inicial x
     * @param posY Pos inicial y
     * @param animacion Animacion asignada
     */
    public FireBall(int posX, int posY, Animacion animacion) {
        super(posX, posY, animacion);
        xInicial = posX;
        yInicial = posY;
        volverInicio();
    }

    /**
     * Metodo que reinicia la posicion de la bala
     */
    public void volverInicio() {
        setX(0);
        setY(0);
        movimiento = false;
    }

    /**
     * Calcula las velocidades minimas y maximas para ambos ejes en x o en y
     * Empieza a tomar el tiempo y activa el movimiento
     *
     * @param x posicion en X
     * @param y posicion en Y
     */
    public void arroja(int x, int y) {

        movimiento = true;
        tiempoInicial = System.currentTimeMillis();
        double maxVelY = CONST * getMaxVelY();
        double minVelY = .3 * maxVelY;
        velY = Math.random() * (maxVelY - minVelY) + minVelY;

        double maxVelX = CONST * getVx(getW() - getAncho() + 10, getH() - getAlto());
        double minVx = getVx(getW() / 2 + 20, getH() - 2 * getAlto());
        velX = Math.random() * (maxVelX - minVx) + minVx;

        setX(x);
        setY(y);
    }

    /**
     * Método que hace que la bala avanze utilizando las formulas de la fisica
     * de desplazamiento en cualquier momento de tiempo Solo avanza si se lo
     * permite
     */
    public void avanza() {
        if (movimiento) {
            double time = (double) (System.currentTimeMillis() - tiempoInicial) / 1000;
            setX(xInicial + (int) (velX * time));
            setY(yInicial - (int) (velY * time - 0.5 * aceleracion * time * time));
        }
    }

    /**
     * Método de pausa para el juego
     */
    public void pausa() {
        tiempoPausa = System.currentTimeMillis();
    }

    /**
     * Metodo que quita la pausa
     */
    public void despausa() {
        tiempoInicial += System.currentTimeMillis() - tiempoPausa;
    }

    /**
     * Metodo que calcula la máxima velocidad en Y que puede tener dependiendo
     * de la gravedad
     *
     * @return double que representa la máxima velocidad
     */
    private double getMaxVelY() {
        return Math.sqrt(2 * yInicial * aceleracion);
    }

    /**
     * Método que utiliza las formlas de fisica para calcular la velocidad en x
     *
     * @param posX posicion en X
     * @param posY posicion en Y
     * @return double que es la velocidad en x
     */
    private double getVx(int posX, int posY) {
        double t = (velY + Math.sqrt(velY * velY - 2 * aceleracion * (yInicial - posY))) / aceleracion;
        return (posX - xInicial) / t;
    }

    /**
     * Metodo para verificar movimiento
     *
     * @return booleano si hay movimiento o no
     */
    public boolean getMovimiento() {
        return movimiento;
    }

    /**
     * Método para asignar posicion en X
     *
     * @param X posicion en X
     */
    public void setPosX(int X) {
        xInicial = X;
        setX(X);
    }

    /**
     * Método para asignar posicion en y
     *
     * @param Y posicion en Y
     */
    public void setPosY(int Y) {
        yInicial = Y;
        setY(Y);
    }

    /**
     * Método para asignar velocidad en X
     *
     * @param v velocidad en X
     */
    public void setVelX(double v) {
        velX = v;
    }

    /**
     * Método para asignar velocidad en Y
     *
     * @param v velocidad en Y
     */
    public void setVelY(double v) {
        velY = v;
    }

    /**
     * Método que regresa velocidad en eje X
     *
     * @return double que es velocidad X
     */
    public double getVelX() {
        return velX;
    }

    /**
     * Método que regresa la velocidad del eje Y
     *
     * @return double = vlocidad Y
     */
    public double getVelY() {
        return velY;
    }

    /**
     * Método que asigna la aceleracion del applet de manera estatica
     *
     * @param a que representa la aceleracion
     */
    public static void setAceleracion(double a) {
        aceleracion = a;
    }

    /**
     * Método que acessa a la variable estatica de la aceleracion
     *
     * @return aceleracion
     */
    public static double getAceleracion() {
        return aceleracion;
    }

}
