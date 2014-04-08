//
//  Created by manolo on Apr 7, 2014.
//  Copyright (c) 2014 manolo. All rights reserved.
//

package dreamwalker;

/**
 *
 * @author manolo
 */
public class Fox extends Base {
	private int jumps, velX, velY;
	
	/**
	 * Metodo constructor default.
	 */
	public Fox() {
		super(0, 0, null);
		jumps = 0;
		velX = 0;
		velY = 0;
	}
	
	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Objeto</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto.
	 * @param posY es el <code>posiscion en y</code> del objeto.
	 * @param a es la <code>animacion</code> del objeto.
	 */
	public Fox(int posX, int posY, Animacion a) {
		super(posX, posY, a);
		jumps = 0;
		velX = 0;
		velY = 0;
	}
	
	/**
	 * Metodo que hace que el personaje principal brinque.
	 */
	void jump() {
		if (jumps > 2) {
			jumps++;
			velY -= 10;
		}
	}
	
	/**
	 * Metodo que hace que se llama cuando el personaje toca el suelo al caer de un brinco.
	 */
	void landed() {
		jumps = 0;
		velY = 0;
	}
	
	/**
	 * Metodo que regresa la velocidad en X del personaje principal.
	 * @return velX
	 */
	int getVelX() {
		return velX;
	}
	
	/**
	 * Metodo que regresa la velocidad en Y del personaje principal.
	 * @return velY
	 */
	int getVelY() {
		return velY;
	}
	
	/**
	 * Metodo que cambia la velocidad en X del personaje principal.
	 * @param v es la velocidad en X
	 */
	void setVelX(int v) {
		velX = v;
	}
	
	/**
	 * Metodo que cambia la velocidad en X del personaje principal.
	 * @param v es el cambio en la velocidad en X
	 */
	void addVelX(int v) {
		velX += v;
	}
	
	/**
	 * Metodo que cambia la velocidad en Y del personaje principal.
	 * @param v es la velocidad en Y
	 */
	void setVelY(int v) {
		velY = v;
	}
	
	/**
	 * Metodo que cambia la velocidad en Y del personaje principal.
	 * @param v es el cambio en la velocidad en Y
	 */
	void addVelY(int v) {
		velY += v;
	}
}