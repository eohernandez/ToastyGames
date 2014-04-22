/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dreamwalker;


import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author NLCJohn
 */
public class JFrameDreamWalker extends JFrame implements KeyListener, MouseListener, Runnable {
	static boolean playing;        
	private int score;

	private int randPosY;

//	strings
	private String[] arr;
	private String nombre;
	private final String nombreArchivo = "guardar.txt";

//	boleanos
	private boolean pausa;      // bool que checa si se pauso
	private boolean instrucciones;

//	floating
	private long tiempoActual;  // tiempo actual

//	images
	private Image dbImage;	// Imagen a proyectar	
	private Graphics dbg;	// Objeto grafico

//	imagen de Background
	private Image background;

//	HighScores
	private HighScore hScore;

	AffineTransform identity = new AffineTransform();

//	animaciones
    private Fox fox;
    
    // animaciones
    
    private Animacion FoxStanding;
    private Animacion FoxJump1;
    private Animacion FoxJump2;
    private Animacion FoxRunning;

    
   private LinkedList<Floor> floor;

    
    private Menu menu;
    private Instructions instructions;
    private gameOver gameOver;
    private Image menuBG;
    private Trophies trophies;

    private Image pausaImg;
    private Image imagenAnimaciones;
    private Image imagenPiso;
	private Sky sky;
    
     public static enum STATUS {
        MENU,
        INSTRUCTIONS,
        TROPHIES,
        GAME,
        GAMEOVER,
        WIN
    };
     
     public static STATUS status;
     
    /**
     * Se crea un objeto de la misma clase
     */
    public JFrameDreamWalker() {
		init();
		start();
    }

    /**
     * Se inicializan las variables en el metodo <I>Init</>
     * Se inicializa el tamaño del applet en 1000x500
     *
     */
    void init() {

        setTitle("Dream Walker");
        addKeyListener(this);
        addMouseListener(this);
        setSize(1152, 720);
        setBackground(Color.decode("#A0CFCF"));
        status = STATUS.MENU;
        Base.setW(getWidth());
        Base.setH(getHeight());

        

        hScore = new HighScore();
        score = hScore.getActualHighscore();
        
        playing = true;
        pausa = false;
        
        
        menuBG = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/menu.png"));
        pausaImg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Botones/pause.png"));
        Image skyI = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/sky.png"));

        menu = new Menu(menuBG);

        instructions = new Instructions(menuBG);
        gameOver = new gameOver(menuBG);
        trophies = new Trophies(menuBG);
        
        FoxStanding  = new Animacion();
        FoxRunning  = new Animacion();
        FoxJump1  = new Animacion();
        FoxJump2  = new Animacion();

		Animacion animSky = new Animacion();

        
		animSky.sumaCuadro(skyI, 100);


        floor = new LinkedList();
        floor.add(new Floor(0, 414  + (int) (Math.random()*280)));
        
    
        
        for (int x = 1; x <= 8; x++) { 
            imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Fox/FoxRun" + x + ".gif"));
            FoxRunning.sumaCuadro(imagenAnimaciones, 100);
        }
        
		for (int x = 1; x<=17; x++) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Fox/FoxStanding" + x + ".png"));
			FoxStanding.sumaCuadro(imagenAnimaciones, 100);
		}
		
        for (int x = 1; x <= 3; x++) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Fox/FoxJump" + x + ".gif"));
			FoxJump2.sumaCuadro(imagenAnimaciones, 10);
        }
        
        fox = new Fox(100, floor.get(0).getY()- new ImageIcon (FoxStanding.getImagen()).getIconHeight(), FoxRunning);
		fox.setStand(FoxStanding);
		fox.setAnim(FoxRunning);
		fox.setVelX(0);
		fox.setVelY(0);
		sky = new Sky(0-6480+720, animSky);
    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
//		Crea el thread
		Thread hilo = new Thread(this);
//		Inicializa el thread
		hilo.start();
    }

    /**
     * Metodo que reinicia todas las variables en caso de que se quiera volver a
     * jugar
     */
    public void restart() {

       
    }

    /**
     * Metodo stop sobrescrito de la clase Applet. En este metodo se pueden
     * tomar acciones para cuando se termina de usar el Applet. Usualmente
     * cuando el usuario sale de la pagina en donde esta este Applet.
     */
    public void stop() {

    }

    /**
     * Metodo destroy sobrescrito de la clase Applet. En este metodo se toman
     * las acciones necesarias para cuando el Applet ya no va a ser usado.
     * Usualmente cuando el usuario cierra el navegador.
     */
    public void destroy() {

    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, checa si pauso el juego, actualiza
     * llama al metodo checaColision, finalmente se repinta el
     * <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
	public void run() {
//		Guarda el tiempo actual del sistema
		tiempoActual = System.currentTimeMillis();
		
//		Ciclo principal del Applet. Actualiza y despliega en pantalla la animación hasta que el Applet sea cerrado
        while (true) {
            if (!playing) {
                setVisible(false);
                System.exit(0);
            }
            
            if (status == STATUS.GAME) {
                if (!pausa) {
                    try {
                        //Actualiza la animacion
                        actualiza();
                    } catch (IOException ex) {
                        Logger.getLogger(JFrameDreamWalker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    checaColision();
                }
            }
            //Manda a llamar al metodo paint() para mostrar en pantalla la animación
            repaint();
            //Hace una pausa de 20 milisegundos
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
  
        }

    }

    /**
     * Metodo usado para checar la colision del objeto bueno con algún objeto
     * malo de la lista de malos, checa si algun malo choco con el <code>Applet
     * </code> por la parte inferior.
     */
    public void checaColision() {

            
            for (Floor flo : floor) {
                if ( flo.intersecta(fox) && fox.getBrinca()) {

                    fox.setX(fox.getX());
                    fox.landed();
                    fox.setY(flo.getY()-fox.getAlto());

                }
            }

	}

    /**
     * El método actualiza() del <code>Applet</code> que actualiza las
     * posiciones de el objeto bueno, los objetos malos y da los tiempos para
     * cada segmento de animacion.
     */
    public void actualiza() throws IOException {

               long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
              sky.move();
               tiempoActual+= tiempoTranscurrido;
               fox.actualiza(tiempoTranscurrido);
               fox.setX(fox.getX()-3);
               
               if (fox.getMoveLeft()) {
                fox.setX(fox.getX() - 6);
                }
                if (fox.getMoveRight()) {
                    fox.setX(fox.getX() + 6);
                }
                
                if (fox.getBrinca()) {
                     fox.brinca();
                }
                if(fox.getBrincaDoble()){
                    fox.setAnim(FoxJump2);
                }
                if (fox.getX() <= 0) {
                    fox.setX(0);
                }
                if (fox.getX()+fox.getAncho()>= getWidth()) {
                    fox.setX(getWidth()-fox.getAncho());
                }
                if(!fox.getBrincaDoble()){
                    fox.setAnim(FoxRunning);
                    
                }
                
                // checa si el piso ya se termino
                for (Floor flo : floor) {
                   
                    if (flo.getX()  <= 0 && !flo.getPassed()) {
                        
                        floor.add(new Floor(1152, 414  + (int) (Math.random()*306)));
                        flo.setPassed(true);
                        
                         break;
                    }
                    
                
                }
                
                // actualiza el piso
                for (Floor flo : floor) {
                    flo.actualizaPos();
                    if (flo.getX() <= -1156) {
 
                        floor.remove(flo);
                        break;
                    }
                }
              
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer

        dbImage = createImage(this.getSize().width, this.getSize().height);
        dbg = dbImage.getGraphics();

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia. ph
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
		g.drawImage(sky.getImagen(), sky.getX(), sky.getY(), this);
        g.setFont(new Font("Serif", Font.BOLD, 34));
        g.drawString("" + score, 100, 80);
        if (status == STATUS.GAME ) {

			if (!pausa) { 
                                // si no esta pausado el juego, pinta todo                    
                                for (Floor flo : floor) 
                                     flo.render(g, this);
                                  
                                if (fox.getMoveRight() || fox.getMoveLeft()) {
                                    
                                    g.drawImage(fox.getImagenA(), fox.getX(), fox.getY(), this);
                                } else {
                                   
                                    if(fox.getBrincaDoble()){
                                        g.drawImage(fox.getImagenA(), fox.getX(), fox.getY(), this);
                                    }
                                    else{
                                        g.drawImage(fox.getImagenS(), fox.getX(), fox.getY(), this);
                                    }   
                                    
				}
			} else {         
				g.drawImage(pausaImg, getWidth() / 2 - new ImageIcon(pausaImg).getIconWidth() / 2, getHeight() / 2 - new ImageIcon(pausaImg).getIconHeight() / 2, this);
			}
        } else if (status == STATUS.MENU) {
            menu.render(g, this);
        } else if (status == STATUS.INSTRUCTIONS) {
            instructions.render(g, this);
        } else if (status == STATUS.GAMEOVER) {
            gameOver.render(g, this);
        } else if (status == STATUS.TROPHIES) {
            trophies.render(g, this);
        }
	}

    @Override
    public void keyTyped(KeyEvent e) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (status == STATUS.GAME) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                fox.setMoveLeft(true);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                fox.setMoveRight(true);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                fox.jump();
                if(fox.getBrinca()||!fox.getBrincaDoble()||fox.getJumps()<2){
                    fox.jumpDouble();
                    
                }
            } else if (e.getKeyCode() == KeyEvent.VK_P) {
                pausa = !pausa;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            fox.setMoveLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            fox.setMoveRight(false);
        }
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        instructions.mouseClicked(e);
        gameOver.mouseClicked(e);
        menu.mouseClicked(e);
        trophies.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}