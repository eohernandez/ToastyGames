//
//  Created by manolo on Apr 7, 2014.
//  Copyright (c) 2014 manolo. All rights reserved.
//

package dreamwalker;

import static dreamwalker.JFrameDreamWalker.toBufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author manolo
 */
public class BadGuys extends Base {
	private int jumps, velX, velY, count;
	private Animacion stand, anim;
	private boolean moveRight;
	private boolean moveLeft;
        private boolean cambiaAnim;
	private static boolean brinco = false, brincoDoble = false;
	public static int acceleracion = 1;
<<<<<<< HEAD
        private boolean aterriza;
=======
        private boolean sigue;
>>>>>>> 24f97d0749ec66cc9bdf0803e9d9de2e0f8b4cf3
	
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
                sigue = false;
	}
	
	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Objeto</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto.
	 * @param posY es el <code>posiscion en y</code> del objeto.
	 * @param a es la <code>animacion</code> del objeto.
	 */
	public BadGuys(int posX, int posY, Animacion a) {
		super(posX, posY, a);
		moveRight = false;
		moveLeft = true;
		jumps = 0;
		velX = 0;
		velY = 0;
                count = 0;
                cambiaAnim = true;
                sigue = false;
	}
        
	
	/**
	 * Metodo que hace que el personaje principal brinque.
	 */
	void jump() {
        brinco = true;
		if (jumps < 2) {
			jumps++;
			velY = 30;
		}
	}
	
	/**
	 * Metodo que hace que el personaje principal brinque doble.
	 */
	void jumpDouble() {
		if (brinco&&jumps < 3&&jumps>1) {
			brincoDoble= true;
			jumps++;
			velY = 30;
		}       
	}
    
	/**
	 * Metodo que regresa si el personaje se encuentra en el aire
	 */
	public boolean getBrinca(){
		return brinco;
	}
        
        /**
	 * Metodo que regresa si el personaje se encuentra en el aire
	 */
	public boolean getBrincaDoble(){
		return brincoDoble;
	}
        /**
	 * Metodo que regresa si el personaje ya aterrizo
	 */
	public boolean getCambiaAnim(){
		return cambiaAnim;
	}
         /**
	* Metodo cambia el estatus de aterriza
	*
	* @param b booleano
	*/
	public void setCambiaAnim(boolean b) {
	   cambiaAnim = b;
	}
        
            /**
         * Método que cambia si esta sigueiengo
         *
         * @param b booleano
         */
        public void setSigue(boolean b) {
            sigue = b;
        }
        /**
	 * Metodo que regresa si esta siguendo
	 */
	public boolean getSigue(){
		return sigue;
	}
            /**
         * Método que cambia la cuenta
         *
         * @param b int
         */
        public void setCount(int b) {
            count = b;
        }
        /**
	 * Metodo que regresa la cuenta
	 */
	public int getCount(){
		return count;
	}
	/**
	 * Metodo que hace que se llama cuando el personaje toca el suelo al caer de un brinco.
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
        public void cae(){
            
            setY(getY() - acceleracion*velY);
            velY-=1;
                       
        }
       
        
         /**
	 * Metodo que regresa si el personaje ya aterrizo
	 */
	public boolean getAterriza(){
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
	 * @return jumps
	 */
	 public int getJumps(){
		return jumps;
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
	
        /**
	 * Metodo que cambia la animacion usada cuando el personaje principal esta animado.
	 * @param a es la animacion
	 */
	void setAnim(Animacion a) {
		anim = a;
	}
	
	/**
	 * Metodo de acceso que regresa la imagen de animacion
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenA() {
		return anim.getImagen();
	}
		
	/**
	 * Metodo que cambia la animacion usada cuando el personaje principal no camina.
	 * @param a es la animacion
	 */
	void setStand(Animacion a) {
		stand = a;
	}
	
	/**
	 * Metodo de acceso que regresa la imagen de standing
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenS() {
		return stand.getImagen();
	}
	
	 /**
	* Método que activa la direccion donde se movio el objeto
	*
	* @param b booleano
	*/
	public void setMoveRight(boolean b) {
	   moveRight = b;
	}

	/**
	* Método que me regresa un booleano si se movio hacia la derecha la canasta
	*
	* @return boolean
	*/
	public boolean getMoveRight() {
	   return moveRight;
	}

	/**
     * Método que activa la direccion donde se movio el objeto
     *
     * @param b booleano
     */
    public void setMoveLeft(boolean b) {
        moveLeft = b;
    }

    /**
     * Método que regresa un booleano si se movio hacia la izquierda la canasta
     * @return boolean
     */
    public boolean getMoveLeft() {
        return moveLeft;
    }
	
	/**
	 * Actualiza la imagen (cuadro) actual de la animación,si es necesario.
	*/
	public void actualiza(long t) {
		animacion.actualiza(t);
		
	}
            /**
         * Creates an empty image with transparency
         * 
         * @param width The width of required image
         * @param height The height of required image
         * @return The created image
         */
        public static Image getEmptyImage(int width, int height){
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            return toImage(img);
        }
            /**
        * Converts a given BufferedImage into an Image
        * 
        * @param bimage The BufferedImage to be converted
        * @return The converted Image
        */
       public static Image toImage(BufferedImage bimage){
           // Casting is enough to convert from BufferedImage to Image
           Image img = (Image) bimage;
           return img;
       }
                /**
         * Rotates an image. Actually rotates a new copy of the image.
         * 
         * @param img The image to be rotated
         * @param angle The angle in degrees
         * @return The rotated image
         */
        public static Image rotate(Image img, double angle)
        {
            double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                   cos = Math.abs(Math.cos(Math.toRadians(angle)));

            int w = img.getWidth(null), h = img.getHeight(null);

            int neww = (int) Math.floor(w*cos + h*sin),
                newh = (int) Math.floor(h*cos + w*sin);

            BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
            Graphics2D g = bimg.createGraphics();

            g.translate((neww-w)/2, (newh-h)/2);
            g.rotate(Math.toRadians(angle), w/2, h/2);
            g.drawRenderedImage(toBufferedImage(img), null);
            g.dispose();

            return toImage(bimg);
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
}