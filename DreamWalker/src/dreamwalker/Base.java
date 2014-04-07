/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dreamwalker;

import java.awt.Image;
import java.awt.Rectangle;

public class Base {
	private int posX;				// posicion en x       
	private int posY;				// posicion en y
	private Animacion animacion;	// animacion
	
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
	 * Metodo de acceso que regresa un nuevo rectangulo
	 * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
	 * del rectangulo
	 */
	public Rectangle getPerimetro() {
		return new Rectangle(getX(),getY(),animacion.getWidth(),animacion.getHeight());
	}
	
	/**
	 * Checa si el objeto <code>Base</code> intersecta a otro <code>Base</code>
	 *
	 * @return un valor boleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */
	public boolean intersecta(Base obj) {
		return getPerimetro().intersects(obj.getPerimetro());
	}
     
	/**
	 * Checa si el objeto <code>Base</code> intersecta con el mouse
	 *
	 * @return un valor boleano <code>true</code> si lo intersecta <code>false</code>
         * 
	 * en caso contrario
	 */ 
	public boolean mouseOver(int x, int y) {
		return getPerimetro().contains(x, y);
	}   
}