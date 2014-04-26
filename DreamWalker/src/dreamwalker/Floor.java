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
public class Floor {
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
    private int tree1Index;
    private int tree2Index;
    private int bush1Index;
    private int bush2Index;
    private int bush3Index;
    private int weed1Index;
    private int weed2Index;
    private int weed3Index;
    private int weed4Index;
    private int weed5Index;
    private int flower1Index;
    private int flower2Index;
    private int flower3Index;
    private int flower4Index;
    private int flower5Index;
    
    private int randPosBush;
    private int randPosTree;
    private int randPosFlower;
    private int randPosWeed;
    
    private int ancho;
    private int alto; 
    
    public Floor(int xPos, int yPos) {
        floors = new Image [3];
        trees = new Image [3];
        bushes = new Image [3];
        flowers = new Image [3];
        weeds = new Image [5];
        
        ancho = 1152;
        alto  = 306;
        
        floorIndex = (int) (Math.random()*3);
        tree1Index = (int) (Math.random()*3);
        tree2Index = (int) (Math.random()*3);
        bush1Index = (int) (Math.random()*3);
        bush2Index = (int) (Math.random()*3);
        bush3Index = (int) (Math.random()*3);
        weed1Index = (int) (Math.random()*5);
        weed2Index = (int) (Math.random()*5);
        weed3Index = (int) (Math.random()*5);
        weed4Index = (int) (Math.random()*5);
        weed5Index = (int) (Math.random()*5);
        flower1Index = (int) (Math.random()*3);
        flower2Index = (int) (Math.random()*3);
        flower3Index = (int) (Math.random()*3);
        flower4Index = (int) (Math.random()*3);
        flower5Index = (int) (Math.random()*3);
        
        randPosTree = (int) (Math.random()*464);
        randPosBush = (int) (Math.random()*328);
        randPosWeed = (int) (Math.random()*217);
        randPosFlower = (int) (Math.random()*215);
        
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
        
		for (int x = 1; x <= 5; x ++ ) {  
            weeds[x-1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesWeed" + x + ".png"));  
        }
        
        flowers[0] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesFloresAmarillas.png"));  
        flowers[1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesFloresAzules.png"));  
        flowers[2] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/Detalles/DetallesFloresRojas.png"));  
    }
    
    /**
     * Método que resetea la posicion del piso si este ya se paso
     */
    public void addFloor () {
        posX = 1156;
        posY = 414  + (int) (Math.random()*280);
       
        floorIndex = (int) (Math.random()*3);
        tree1Index = (int) (Math.random()*3);
        tree2Index = (int) (Math.random()*3);
        bush1Index = (int) (Math.random()*3);
        bush2Index = (int) (Math.random()*3);
        bush3Index = (int) (Math.random()*3);
        weed1Index = (int) (Math.random()*5);
        weed2Index = (int) (Math.random()*5);
        weed3Index = (int) (Math.random()*5);
        weed4Index = (int) (Math.random()*5);
        weed5Index = (int) (Math.random()*5);
        flower1Index = (int) (Math.random()*3);
        flower2Index = (int) (Math.random()*3);
        flower3Index = (int) (Math.random()*3);
        flower4Index = (int) (Math.random()*3);
        flower5Index = (int) (Math.random()*3);
        
        randPosTree = (int) (Math.random()*464);
        randPosBush = (int) (Math.random()*328);
        randPosWeed = (int) (Math.random()*217);
        randPosFlower = (int) (Math.random()*1000);
	}
    
    /**
     * Método que actualiza la posX del objeto 
     */
	public void actualizaPos () {
		posX -= 3;
	}
	
    /**
	 * Metodo de acceso que regresa el alto de la base
	 * @return alto
	 */
	public int getAlto() {
		return alto;
	}
	
    /**
	 * Metodo de acceso que regresa el ancho de la base
	 * @return alto
	 */
	public int getAncho() {
		return ancho;
	}
    
	/**
     * Dibuja el suelo
     * @param g
     * @param juego
     */
    public void render(Graphics g, JFrameDreamWalker juego) {
		g.drawImage(floors[floorIndex], posX, posY, juego);
		g.drawImage(trees[tree1Index], posX+randPosTree, posY-trees[tree1Index].getHeight(juego), juego);
		g.drawImage(trees[tree2Index], posX+randPosTree+464, posY-trees[tree2Index].getHeight(juego), juego);
		g.drawImage(bushes[bush1Index], posX+randPosBush, posY-bushes[bush1Index].getHeight(juego), juego);
		g.drawImage(bushes[bush2Index], posX+randPosBush+328, posY-bushes[bush2Index].getHeight(juego), juego);
		g.drawImage(bushes[bush3Index], posX+randPosBush+656, posY-bushes[bush3Index].getHeight(juego), juego);
        g.drawImage(weeds[weed1Index], posX+randPosWeed, posY-weeds[weed1Index].getHeight(juego), juego);
        g.drawImage(weeds[weed2Index], posX+randPosWeed+217, posY-weeds[weed2Index].getHeight(juego), juego);
        g.drawImage(weeds[weed3Index], posX+randPosWeed+434, posY-weeds[weed3Index].getHeight(juego), juego);
        g.drawImage(weeds[weed4Index], posX+randPosWeed+651, posY-weeds[weed4Index].getHeight(juego), juego);
        g.drawImage(weeds[weed5Index], posX+randPosWeed+868, posY-weeds[weed5Index].getHeight(juego), juego);
        g.drawImage(flowers[flower1Index], posX+randPosFlower, posY-flowers[flower1Index].getHeight(juego), juego);
        g.drawImage(flowers[flower2Index], posX+randPosFlower+215, posY-flowers[flower2Index].getHeight(juego), juego);
        g.drawImage(flowers[flower3Index], posX+randPosFlower+430, posY-flowers[flower3Index].getHeight(juego), juego);
        g.drawImage(flowers[flower4Index], posX+randPosFlower+645, posY-flowers[flower4Index].getHeight(juego), juego);
        g.drawImage(flowers[flower5Index], posX+randPosFlower+860, posY-flowers[flower5Index].getHeight(juego), juego);
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
     * Método que me regresa un boleano para saber si el objecto ya paso el piso
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
