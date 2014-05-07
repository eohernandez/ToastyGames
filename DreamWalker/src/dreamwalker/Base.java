/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dreamwalker;

import java.awt.Image;
import java.awt.Rectangle;

public class Base {
	int posX;		// posicion en x       
	int posY;		// posicion en y
	Animacion animacion;	// animacion
	private static int W;
	private static int H;
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la posicion en x del objeto.
	 * @param posY es la posicion en y del objeto.
	 * @param a es la Animacion del objeto.
	 */
	public Base(int posX, int posY, Animacion a) {
		this.posX=posX;
		this.posY=posY;
		this.animacion = a;
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en x del objeto 
	 * @param posX es la posicion en x del objeto.
	 */
	public void setX(int posX) {
		this.posX = posX;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en x del objeto 
	 * @return posX es la posicion en x del objeto.
	 */
        
	public int getX() {
		return posX;
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en y del objeto 
	 * @param posY es la posicion en y del objeto.
	 */
	public void setY(int posY) {
		this.posY = posY;
	}
	
	/**
	 * Método que fija una animacion para el objeto
	 * @param anim animacion del objeto
	 */
	public void setAnimacion(Animacion anim) {
		this.animacion = anim;
	}

	/**
	 * Método que regresa la Animacion del objeto
	 * @return la animacion del objeto
	 */
	public Animacion getAnimacion() {
		return this.animacion;
	}

	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return posY es la posicion en y del objeto.
	 */
	public int getY() {
		return posY;
	}
	
	/**
	 * Metodo de acceso que regresa la imagen del icono 
	 * @return un objeto de la clase Image que es la imagen del icono.
	 */
	public Image getImagen() {
		return animacion.getImagen();
	}
	
	/**
	 * Metodo de acceso que regresa la imagen del icono 
	 * @param i el indice de la imagen a regresar
	 * @return un objeto de la clase Image que es la imagen del icono en el indice i.
	 */
	public Image getImagen(int i) {
		return animacion.getImagen(i);
	}
	
	/**
	 * Metodo de acceso que regresa el ancho del icono 
	 * @return el ancho del icono.
	 */
	public int getAncho() {
		return animacion.getWidth();
	}
	
	/**
	 * Metodo de acceso que regresa el alto del icono 
	 * @return el alto del icono.
	 */
	public int getAlto() {
		return animacion.getHeight();
	}
	
	/**
	 * Metodo de acceso que regresa un nuevo rectangulo
	 * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
	 * del rectangulo
	 */
	public Rectangle getPerimetro() {
		return new Rectangle(getX(),getY(),animacion.getWidth(),animacion.getHeight());
	}
	
	/**
	 * Checa si el objeto <code>Base</code> intersecta a otro <code>Base</code>
	 * @param obj con el objeto que se checa la interseccion
	 * @return un booleano que dice si lo intersecta o no
	 */
	public boolean intersecta(Base obj) {
		return getPerimetro().intersects(obj.getPerimetro());
	}
	
	/**
     * Metodo que asigna el tamaño estatico del applet
     * @param w el ancho del applet
     */
    public static void setW(int w) {
        W = w;
    }

    /**
     * Método que regresa el tamaño estatico del applet
     * @return W que es el ancho del applet
     */
    public static int getW() {
        return W;
    }

    /**
     * Metodo que asigna la variable estatica del applet del tamaño
     * @param h el alto del applet
     */
    public static void setH(int h) {
        H = h;
    }

    /**
     * Metodo que regresa la variable estatica del applet
     * @return H que es el alto del applet
     */
    public static int getH() {
        return H;
    }
	
	/**
	 * Checa si el objeto <code>Base</code> intersecta con el mouse
	 * @param x posicion x del mouse
	 * @param y posicion y del mouse
	 * @return un booleano que dice si se intersectan o no
	 */ 
	public boolean mouseOver(int x, int y) {
		return getPerimetro().contains(x, y);
	}
	
	/**
	 * Checa si hay interseccion con el lado derecho del piso
	 * @param obj el piso con el que se checa interseccion
	 * @return un booleano que dice si hay interseccion o no
	 */
	public boolean checaIntersecionDerecha(Floor obj) {
		return getPerimetro().intersects(obj.cuadroDerecha());
	}
	
	/**
	 * Checa si hay interseccion con el lado de arriba del piso
	 * @param obj el piso con el que se checa interseccion
	 * @return un booleano que dice si hay interseccion o no
	 */
	public boolean checaIntersecionArriba(Floor obj) {
		return getPerimetro().intersects(obj.cuadroArriba());
	}
}