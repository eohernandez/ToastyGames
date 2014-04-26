//
//  Created by manolo on Apr 21, 2014.
//  Copyright (c) 2014 manolo. All rights reserved.
//

package dreamwalker;

/**
 *
 * @author manolo
 */
public class Sky extends Base {
	private double skyY, puebloX;
	
	/**
	 * Metodo constructor default.
	 */
	public Sky() {
		super(0, 0, null);
	}
	
	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Objeto</code>.
	 * @param posY es el <code>posiscion en y</code> del objeto.
	 * @param a es la <code>animacion</code> del objeto.
	 */
	public Sky(int posY, Animacion a) {
		super(0, 0, a);
		skyY = posY;
		puebloX = 1152;
	}
	
	/**
	 * Metodo usado para avanzar la hora del dia.
	 */
	public void move() {
		if (Math.floor(skyY) == 0) {
			skyY = -6480+720;
		} else {
			skyY += 0.32;
		}
		
		if (Math.floor(puebloX) == -1500) {
			puebloX = 0;
		} else {
			puebloX -= 0.2;
		}
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return posY es la <code>posicion en y</code> del objeto.
	 */
	@Override
	public int getY() {
		return (int) Math.floor(skyY);
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en x del pueblo
	 * @return posY es la <code>posicion en x</code> del pueblo.
	 */
	public int getPuebloX() {
		return (int) Math.floor(puebloX);
	}
}
