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
	private double pY;
	
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
		pY = posY;
	}
	
	/**
	 * Metodo usado para avanzar la hora del dia.
	 */
	public void move() {
		if (-0.5 < pY && pY > 0.5) {
			pY = -6480+720;
		} else {
			pY += 0.32;
		}
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return posY es la <code>posicion en y</code> del objeto.
	 */
	@Override
	public int getY() {
		return (int) Math.floor(pY);
	}
}
