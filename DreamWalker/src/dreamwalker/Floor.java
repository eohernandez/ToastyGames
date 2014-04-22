package dreamwalker;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author daniel rodriguez
 * @author Emilio Flores
 */
public class Floor  {

    private boolean movement;
    private  int posX;
    private  int posY;
    public static double nivel = 1.0;
    private boolean paso;


    
    private Image floors[];
    private Image bushes[]; 
    private Image trees[]; 
    private Image flowers[]; 
    private Image weeds[]; 
    
    private int floorIndex;
    private int bushIndex;
    private int treeIndex;
    private int flowerIndex;
    private int weedIndex;
    
    private int randPosBush;
    private int randPosTree;
    private int randPosFlower;
    private int randPosWeed;
    
    public Floor(int xPos, int yPos) {
        floors = new Image [3];
        trees = new Image [3];
        bushes = new Image [3];
        flowers = new Image [3];
        weeds = new Image [5];
        
        floorIndex = (int) (Math.random()*3);
        treeIndex = (int) (Math.random()*3);
        bushIndex = (int) (Math.random()*3);
        flowerIndex = (int) (Math.random()*3);
        weedIndex = (int) (Math.random()*5);
        
        
        randPosTree = (int) (Math.random()*1000);
        randPosBush = (int) (Math.random()*1000);
        randPosWeed = (int) (Math.random()*1000);
        randPosFlower = (int) (Math.random()*1000);
        
        posX = xPos;
        posY = yPos;
        
        
        paso = false;
        for (int x = 1; x <= 3; x ++ ) {  
            
            floors[x-1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Colinas/Colina" + x + ".png"));  
        }

        
        for (int x = 1; x <= 3; x ++ ) {  
            trees[x-1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesArbol" + x + ".png"));  
        }
        
        for (int x = 1; x <= 3; x ++ ) {  
            bushes[x-1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesArbusto" + x + ".png"));  
        }
        
        flowers[0] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesFloresAmarillas.png"));  
        flowers[1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesFloresAzules.png"));  
        flowers[2] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesFloresRojas.png"));  
        
        for (int x = 1; x <= 5; x ++ ) {  
            weeds[x-1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesWeed" + x + ".png"));  
        }
        
               
    }
    
    /**
     * Método que resetea la posicion del piso si este ya se paso
     */
    public void addFloor () {
       
        posX = 1156;
        posY = 414  + (int) (Math.random()*280);
       
        floorIndex = (int) (Math.random()*3);
        treeIndex = (int) (Math.random()*3);
        bushIndex = (int) (Math.random()*3);
        flowerIndex = (int) (Math.random()*3);
        weedIndex = (int) (Math.random()*5);
        
        randPosTree = (int) (Math.random()*1000);
        randPosBush = (int) (Math.random()*1000);
        randPosWeed = (int) (Math.random()*1000);
        randPosFlower = (int) (Math.random()*1000);
        
        
    }
    
    /**
     * Método que actualiza la posX del objeto 
     */
    public void actualizaPos () {   
        posX -= 3;

    }
    
    
        /**
     * Dibuja el suelo
     * @param g
     * @param juego
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
        
        g.drawImage(floors[floorIndex], posX, posY, juego);
        g.drawImage(flowers[flowerIndex], posX+randPosFlower, posY-30, juego);
        g.drawImage(weeds[weedIndex], posX+randPosWeed, posY-30, juego);
        g.drawImage(trees[treeIndex], posX+randPosTree, posY-180, juego);
        g.drawImage(bushes[bushIndex], posX+randPosBush, posY-44, juego);
       
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
	 * Metodo de acceso que regresa la posicion en x del objeto 
	 * @return posX es la <code>posicion en x</code> del objeto.
	 */
        
	public int getX() {
		return posX;
	}
        
        /**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return posY es la <code>posicion en y</code> del objeto.
	 */
	public int getY() {
		return posY;
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
	 * Metodo de acceso que regresa un nuevo rectangulo
	 * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
	 * del rectangulo
	 */
	public Rectangle getPerimetro() {
		return new Rectangle(posX, posY, 1152 , 306 );
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

}
