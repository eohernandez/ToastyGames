/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dreamwalker;

import java.awt.Image;
import java.awt.Rectangle;

public class Base {
	private int posX;		// posicion en x       
	private int posY;		// posicion en y
	Animacion animacion;	// animacion
	private static int W;
	private static int H;
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param Animacion es la <code>Animacion</code> del objeto.
	 */
	public Base(int posX, int posY, Animacion a) {
		this.posX=posX;
		this.posY=posY;
		this.animacion = a;
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en x del objeto 
	 * @param posX es la <code>posicion en x</code> del objeto.
	 */
	public void setX(int posX) {
		this.posX = posX;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en x del objeto 
	 * @return posX es la <code>posicion en x</code> del objeto.
	 */
        
	public int getX() {
		return posX;
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en y del objeto 
	 * @param posY es la <code>posicion en y</code> del objeto.
	 */
	public void setY(int posY) {
		this.posY = posY;
	}
        
        
		/**
	 * Método que fija una animacion para el objeto
	 *
	 * @param anim
	 */
	public void setAnimacion(Animacion anim) {
		this.animacion = anim;
	}

	/**
	 * Método que regresa la <code> Animacion </code> del objeto
	 *
	 * @return Anim
	 */
	public Animacion getAnimacion() {
		return this.animacion;
	}


	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return posY es la <code>posicion en y</code> del objeto.
	 */
	public int getY() {
		return posY;
	}
	
	/**
	 * Metodo de acceso que regresa la imagen del icono 
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagen() {
		return animacion.getImagen();
	}
        /**
	 * Metodo de acceso que regresa el ancho del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del icono.
	 */
	public int getAncho() {
                 
		return animacion.getWidth();
                
	}
	
	/**
	 * Metodo de acceso que regresa el alto del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el alto del icono.
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
	 * @return un valor boleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */
	public boolean intersecta(Base obj) {
		return getPerimetro().intersects(obj.getPerimetro());
	}
           /**
     * Método que asigna el tamaño estatico del applet
     *
     * @param w
     */
    public static void setW(int w) {
        W = w;
    }

    /**
     * Método que me regresa el tamaño estatico de mi applet
     *
     * @return W que es el width
     */
    public static int getW() {
        return W;
    }

    /**
     * Método que asigna la variable estatica del applet del tamaño
     *
     * @param h
     */
    public static void setH(int h) {
        H = h;
    }

    /**
     * Método que regresa la variable estatica del applet
     *
     * @return H que es el <I> Height </I>
     */
    public static int getH() {
        return H;
    }
	/**
	 * Checa si el objeto <code>Base</code> intersecta con el mouse
	 * @param x posicion x del mouse
	 * @param y posicion y del mouse
	 * @return un valor boleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */ 
	public boolean mouseOver(int x, int y) {
		return getPerimetro().contains(x, y);
	}   
}