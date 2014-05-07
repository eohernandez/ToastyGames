//
//  Created by manolo on Apr 7, 2014.
//  Copyright (c) 2014 manolo. All rights reserved.
//

package dreamwalker;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author manolo
 */
public class BadGuys extends Base {
	private int jumps, velX, velY, count, count2;
	private Animacion stand, anim;
	private boolean moveRight;
	private boolean moveLeft;
	private boolean cambiaAnim;
	private static boolean brinco = false, brincoDoble = false;
	public static int acceleracion = 1;
	private boolean aterriza;
	private boolean sigue;
	
	/**
	 * Metodo constructor default.
	 */
	public BadGuys() {
		super(0, 0, null);
		moveRight = false;
		moveLeft = false;

		jumps = 0;
		velX = 0;
		velY = 0;
                count =0;
                count2 = 0;
                sigue = false;
	}
	
	/**
	 * Metodo constructor que hereda los atributos de la clase Objeto.
	 * @param posX es la posiscion en x del objeto.
	 * @param posY es el posiscion en y del objeto.
	 * @param a es la animacion del objeto.
	 */
	public BadGuys(int posX, int posY, Animacion a) {
		super(posX, posY, a);
		moveRight = false;
		moveLeft = true;
		jumps = 0;
		velX = 0;
		velY = 0;
                count = 0;
                count2 = 0;
                cambiaAnim = true;
                sigue = false;
	}
        
	/**
	 * Metodo que regresa si se cambio la animacion
	 * @return si se cambio o no la animacion
	 */
	public boolean getCambiaAnim(){
		return cambiaAnim;
	}
	
	/**
	 * Metodo que cambia el estatus de si se cambio la animacion
	 * @param b booleano
	 */
	public void setCambiaAnim(boolean b) {
	   cambiaAnim = b;
	}
	
	/**
	 * Método que cambia si esta siguiendo
	 * @param b booleano
	 */
	public void setSigue(boolean b) {
		sigue = b;
	}
	
	/**
	 * Metodo que regresa si esta siguendo
	 * @return si esta siguiendo o no
	 */
	public boolean getSigue(){
		return sigue;
	}
	
	/**
	 * Método que cambia la cuenta
	 * @param b la cuenta nueva
	 */
	public void setCount(int b) {
		count = b;
	}
               /**
         * Método que cambia la segunda cuenta
         *
         * @param b int
         */
        public void setCount2(int b) {
            count2 = b;
        }
        /**
	 * Metodo que regresa la segunda cuenta
	 */
	public int getCount2(){
            return count2;
	}
	
	/**
	 * Metodo que regresa la cuenta
	 * @return la cuenta
	 */
	public int getCount(){
		return count;
	}
	
	/**
	 * Metodo que se llama cuando el personaje toca el suelo al caer de un brinco.
	 */
	void landed() {
		jumps = 0;
		velY = 0;
		brinco = false;
		brincoDoble = false;
		cambiaAnim = true;
	}
	
	/**
	 * Metodo actualiza la velocidad en y si el objeto esta callendo 
	 */
	public void cae() {
		setY(getY() - acceleracion*velY);
		velY-=1;
	}
	
	/**
	 * Metodo que regresa si el personaje ya aterrizo
	 * @return si aterrizo o no el personaje
	 */
	public boolean getAterriza(){
		return aterriza;
	}
	
	/**
	 * Metodo cambia el estatus de aterriza
	 * @param b booleano
	 */
	public void setAterriza(boolean b) {
	   aterriza = b;
	}
        
	/**
	 * Metodo actualiza la velocidad en y si es que brinco el objeto
	 */ 
	public void brinca() {
		if (brinco) {
			setY(getY() - acceleracion*velY);
			velY-=2;
		}
	}
	
	/**
	 * Metodo que regresa el numero de brincos del personaje
	 * @return jumps numero de brincos
	 */
	 public int getJumps(){
		return jumps;
	}
	 
	/**
	 * Metodo que regresa la velocidad en X
	 * @return velX velocidad en X
	 */
	int getVelX() {
		return velX;
	}
	
	/**
	 * Metodo que regresa la velocidad en Y
	 * @return velY velocidad en Y
	 */
	int getVelY() {
		return velY;
	}
	
	/**
	 * Metodo que cambia la velocidad en X
	 * @param v es la velocidad en X
	 */
	void setVelX(int v) {
		velX = v;
	}
	
	/**
	 * Metodo que cambia la velocidad en X
	 * @param v es el cambio en la velocidad en X
	 */
	void addVelX(int v) {
		velX += v;
	}
	
	/**
	 * Metodo que cambia la velocidad en Y
	 * @param v es la velocidad en Y
	 */
	void setVelY(int v) {
		velY = v;
	}
	
	/**
	 * Metodo que cambia la velocidad en Y
	 * @param v es el cambio en la velocidad en Y
	 */
	void addVelY(int v) {
		velY += v;
	}
	
	/**
	 * Metodo de acceso que regresa la imagen de la animacion
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenA() {
		return anim.getImagen();
	}
	
	/**
	 * Metodo de acceso que regresa la imagen de standing
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenS() {
		return stand.getImagen();
	}
	
	/**
	 * Actualiza la imagen actual de la animación si es necesario.
	 */
	public void actualiza(long t) {
		animacion.actualiza(t);
		
	}
	
	/**
	 * Creates an empty image with transparency
	 * @param width The width of required image
	 * @param height The height of required image
	 * @return The created image
	 */
	public static Image getEmptyImage(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		return toImage(img);
	}
	
	/**
	 * Converts a given BufferedImage into an Image
	 * @param bimage The BufferedImage to be converted
	 * @return The converted Image
	 */
	public static Image toImage(BufferedImage bimage) {
//		Casting is enough to convert from BufferedImage to Image
		Image img = (Image) bimage;
		return img;
	}
	

           /**
         * within. regresa si la imagen se encuentra dentro del rango
         * 
         * @param x posicion en x
         * @param xf posicion en x final
         * @param posX posicion en x
         * @param ancho ancho de la imagen posX
         * 
         * @return si es que se encuentra dentro del rango
         */
        public boolean within(int x, int xf,int posX, int ancho){
            
            if(posX>x&&posX+ancho<xf)
                return true;
            else 
                return false;
            
        }
        
        /**
	 * Metodo de acceso que regresa un nuevo rectangulo chico
	 * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
	 * del rectangulo
	 */
        
	public Rectangle getPerimetroChico(){
            
		return new Rectangle(super.getX(),super.getY(), (int) ((int) getAncho() * .75) ,(int) ((int) getAlto() * .75));
                
	}
	
        
        /**
	 * Checa si el objeto <code>Base</code> intersecta a otro <code>Base</code>
	 *
	 * @return un valor boleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */
        
          
        
	public boolean intersectaChico(Base obj){
            
		return getPerimetroChico().intersects(obj.getPerimetro());
                
	}
}