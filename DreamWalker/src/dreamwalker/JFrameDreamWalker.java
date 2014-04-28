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
	private int randPosX;
	private int randPosYc;
	private int randPosXc;
	private int dx;
	private int dy;

//	strings
	private String[] arr;
	private String nombre;
	private final String nombreArchivo = "guardar.txt";

//	boleanos
	private boolean pausa;      // bool que checa si se pauso
	private boolean instrucciones;
	private boolean sound;

//	floating
	private long tiempoActual;  // tiempo actual

//	images
	private Image dbImage;	// Imagen a proyectar	
	private Graphics dbg;	// Objeto grafico

//	HighScores
	private HighScore hScore;
	AffineTransform identity = new AffineTransform();
        
//	SoundClips
	private SoundClip backMusic;

//	animaciones
    private Fox fox;
    
//	crea espada
    private BadGuys espada;
    private Animacion espadaNormal;
	
//	animaciones
    private Animacion animSky;
    private Animacion FoxStanding;
    private Animacion FoxJump1;
    private Animacion FoxJump2;
    private Animacion FoxRunning;
    private Animacion foxDeath;
    private Animacion canonNormal;
    private Animacion canonOpen;
    private Animacion canonFire;
    private Animacion canonBall;
    
    private LinkedList<BadGuys> canons;    

    private LinkedList<Floor> floor;

    private Menu menu;
    private Instructions instructions;
    private gameOver gameOver;
    private Image menuBG;
    private Image menuFox;
    private Trophies trophies;
    private FireBall fireball;

    private Image pausaImg;
    private Image imagenAnimaciones;
    private Image imagenPiso;

    private Sky sky;
    private boolean created;
    private boolean nombreIngresado;

    
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

		backMusic = new SoundClip("Images/Background/GameOver.wav");
		backMusic.play();

        hScore = new HighScore();
       
        score = hScore.getActualHighscore(); 
        playing = true;
        pausa = false;
        created = false;
        sound = true;
        nombreIngresado = false;

        menuBG = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/menu.png"));
        menuFox = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Fox/FoxGif.gif"));
        pausaImg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Botones/pause.png"));
        Image skyI = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/sky.png"));

        menu = new Menu(menuBG, menuFox);

        instructions = new Instructions(menuBG);
        gameOver = new gameOver(menuBG);
        trophies = new Trophies(menuBG);
        
                
        FoxStanding  = new Animacion();
        FoxRunning  = new Animacion();
        FoxJump1  = new Animacion();
        FoxJump2  = new Animacion();
        foxDeath = new Animacion();
        canonNormal = new Animacion();
        canonOpen = new Animacion();
        canonFire = new Animacion();
        espadaNormal = new Animacion();
        canonBall = new Animacion(); 
        animSky = new Animacion();
        animSky.sumaCuadro(skyI, 100);

        
        floor = new LinkedList();
        canons = new LinkedList();
        
        for (int x =1; x<= 6; x++ ) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Enemigos/Canon/Canon" + x + ".png"));
			canonNormal.sumaCuadro(imagenAnimaciones, 100);
        }
        
        for (int x =1; x<= 4; x++ ) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Spells/FireBall/FireBall" + x + ".png"));
			canonBall.sumaCuadro(imagenAnimaciones, 100);
        }
        fireball = new FireBall(0,500, canonBall);
        
        for (int x =1; x<= 5; x++ ) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Enemigos/Canon/CanonAbre" + x + ".png"));
			canonOpen.sumaCuadro(imagenAnimaciones, 100);
        }
        
        for (int x =1; x<= 2; x++ ) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Enemigos/Canon/CanonAnticipacionDisparoVolteado-" + x + ".png"));
			canonFire.sumaCuadro(imagenAnimaciones, 200);
        }
        
        randPosY = 414  + (int) (Math.random()*306);
        
        floor.add(new Floor(0, randPosY ));
      
        canons.add(new BadGuys(1150, randPosY - 200, canonNormal));
        
        
		
//		Animacion de espada
        for (int x =1; x<= 4; x++ ) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Enemigos/Espada/Espada" + x + ".png"));
			espadaNormal.sumaCuadro(imagenAnimaciones, 100);
        }
        randPosYc = 0  + (int) (Math.random()*6); //randon*rango + minimo
        randPosXc = getWidth() + (int) (Math.random()*200); //randon*rango + minimo
        espada = new BadGuys(randPosXc,randPosYc,espadaNormal);
        espada.setVelX(4);
        
        //
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
			FoxJump2.sumaCuadro(imagenAnimaciones, 25);
        }
        
        for (int x = 1; x <= 50; x++) {
			imagenAnimaciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Fox/FoxDeath" + x + ".gif"));
			foxDeath.sumaCuadro(imagenAnimaciones, 50);
			/*
			if(x>=1&&x<=7)
					foxDeath.sumaCuadro(imagenAnimaciones, 130);
			if(x==8||x==11||x==12)
					foxDeath.sumaCuadro(imagenAnimaciones, 80);
			if(x>=9&&x<=10)
					foxDeath.sumaCuadro(imagenAnimaciones, 40);
			if(x>=13&&x<=14)
					foxDeath.sumaCuadro(imagenAnimaciones, 40);
			if(x>=15&&x<=19)
					foxDeath.sumaCuadro(imagenAnimaciones, 80);
			if(x>=20&&x<=22)
					foxDeath.sumaCuadro(imagenAnimaciones, 130);
			*/          
        }
        
        fox = new Fox(100, floor.get(0).getY()- new ImageIcon (FoxStanding.getImagen()).getIconHeight(), FoxRunning);
		fox.setStand(FoxStanding);
		fox.setAnim(FoxRunning);
		fox.setVelX(3);
		fox.setVelY(0);
		sky = new Sky(0-6480+720, animSky);
		setResizable(false);
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
		playing = true;
        pausa = false;
        created = false;
        sound = true;
        nombreIngresado = false;
        fox = new Fox(100, floor.get(0).getY()- new ImageIcon (FoxStanding.getImagen()).getIconHeight(), FoxRunning);
        fox.setStand(FoxStanding);
        fox.setAnim(FoxRunning);
        fox.setVelX(3);
        fox.setVelY(0);
        score = 0;
        sky = new Sky(0-6480+720, animSky);
        randPosY = 414  + (int) (Math.random()*306);
        floor.clear();
        canons.clear();
        floor.add(new Floor(0, randPosY ));
        canons.add(new BadGuys(1150, randPosY - 200, canonNormal));
        espada = new BadGuys(randPosXc,randPosYc,espadaNormal);
        espada.setVelX(4);
        Floor.cantMalos=1;
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
//						Actualiza la animacion
                        actualiza();
                    } catch (IOException ex) {
                        Logger.getLogger(JFrameDreamWalker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    checaColision();
                }
            }
//			Manda a llamar al metodo paint() para mostrar en pantalla la animación
            repaint();
//			Hace una pausa de 20 milisegundos
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
			fox.setAterriza(false);
            
            for (Floor flo : floor) {    
                for (BadGuys bad : canons) {
                    if ( bad.getX()+bad.getAncho() >= flo.getX() && bad.getX()+bad.getAncho() <= flo.getX()+flo.getAncho()) {
                        if (bad.getY()+bad.getAlto() < flo.getY()) {  
                            bad.cae();
                            break;
                        }    
                    }
                }
            }
            
            for (Floor flo : floor) {
				if(flo.intersecta(fox)&&fox.getMoveLeft()&& fox.getX()>=flo.getX()+flo.getAncho()-10 ){
					 fox.setX(flo.getX()+ flo.getAncho()+5);
				}
				if(fox.within(flo.getX()-fox.getAncho()/2, flo.getAncho()-fox.getAncho(), fox.getX(), fox.getAncho()/2)&& flo.intersecta(fox)){
					fox.setAterriza(true);
					fox.landed();
					fox.setY(flo.getY()-fox.getAlto());
				}
				if( flo.intersecta(fox) && fox.getMoveRight()){
					 fox.setX(flo.getX()- fox.getAncho());
				}
            }
            
            for (BadGuys bad : canons) {
				if (bad.intersecta(fox)) {
					fox.setDeath(true);
					dx = fox.getX();
					dy = fox.getY();
				}
            }

            if (espada.intersecta(fox)) {
                    fox.setDeath(true);
                    dx = fox.getX();
                    dy = fox.getY();
            }
            
            for (Floor flo : floor) {
                if (flo.intersecta(fireball)) {
                    fireball.volverInicio(0,0);
                }
            }
            
            if (fireball.intersecta(fox)) {
                fox.setDeath(true);
                 dx = fox.getX();
                 dy = fox.getY();
            }

	}

    /**
     * El método actualiza() del <code>Applet</code> que actualiza las
     * posiciones de el objeto bueno, los objetos malos y da los tiempos para
     * cada segmento de animacion.
     */
    public void actualiza() throws IOException {
		long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
		if (status == STATUS.GAME) {
			sky.move(tiempoTranscurrido);
			tiempoActual += tiempoTranscurrido;

			if(fox.getDeath()) {
				foxDeath.actualiza(tiempoTranscurrido);
				fox.actualiza(tiempoTranscurrido);
				if(foxDeath.getCuadroActual()==33) {
					fox.setX(-300);
				}
				if(foxDeath.getCuadroActual()>=49){
					status = STATUS.GAMEOVER;
					restart();
					nombre = JOptionPane.showInputDialog("Cual es tu nombre?");
					nombreIngresado = true;
					grabaArchivo();
					fox.setDeath(false);
				}
			} else {
				fox.actualiza(tiempoTranscurrido);
				fox.setX(fox.getX()-fox.getVelX());
				fox.cae();
				fox.setY(fox.getY());
			}

			canonBall.actualiza(tiempoTranscurrido);
			for (BadGuys bad : canons) {
				bad.actualiza(tiempoTranscurrido);
			}
//			Actualiza posiciones de la espada
			espada.actualiza(tiempoTranscurrido);
			espada.setX(espada.getX() - espada.getVelX());

			if (Floor.cantMalos == 0) {
				canons.remove();
				canons.add( new BadGuys (1156, randPosY -111, canonNormal));
			}

			if(espada.getX() + espada.getAncho() < 0) {
				randPosYc = 0  + (int) (Math.random()*50); //randon*rango + minimo
				randPosXc = getWidth() + (int) (Math.random()*400); //randon*rango + minimo
				espada.setX(randPosXc);
				espada.setY(randPosYc);
				espada.setCount(0);
				espada.setSigue(false);
		   }

		   if(espada.getCount()==1&&espada.getSigue()){
			   espada.setX(espada.getX()+espada.getVelX()+8);
			   if(espada.getX()+espada.getAncho()>=getWidth()){
				   espada.setCount(espada.getCount()+1);
			   }
		   }

		   if(espada.getSigue()&&espada.getCount()>1){
			   espada.setX(espada.getX()-fox.getVelY()-1) ;
		   }

		   if(espada.getX()>fox.getX()&&fox.getX()+fox.getAncho()+20>espada.getX()&&espada.getCount()==0){
			   espada.setSigue(true);
			   espada.setCount(espada.getCount()+1);
//			   System.out.println("Verdad");
			}
//			checo si algun malo choco con algun piso por la derecha
//			para que se quede parado si es eel caso
			for (BadGuys bad : canons) {
				for (Floor flo : floor) {
					if (bad.checaIntersecionDerecha(flo)) {
						bad.setX(bad.getX());
					} else {
						bad.setX( bad.getX() - 5);
					}
					break;
				}
			}
//			cambia animacion cuando el zorro esta cerca
			for (BadGuys bad : canons ) {
				if ( fox.getX() + fox.getAncho() + 200 >= bad.getX()&& bad.getCambiaAnim()) {
					bad.setAnimacion(canonOpen);
					bad.setY(bad.getY()-30);
					bad.setCambiaAnim(false);        
				}
			}
//			cambia animacion a disparo
			for (BadGuys bad: canons) {
				if ( bad.getX() <= 200 ) {
					bad.setAnimacion(canonFire);
				}
			}

			if(!fox.getDeath()){
				 if (fox.getMoveLeft()) {
				 fox.setX(fox.getX() - 6);
				 }
				 if (fox.getMoveRight()) {
					 fox.setX(fox.getX() + 9);
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
		   }
//   		checa si el piso ya se termino
			for (Floor flo : floor) {
				if (flo.getX()  <= 0 && !flo.getPassed()) {
					randPosY = 414  + (int) (Math.random()*306);
					floor.add(new Floor(1152, randPosY ));
					flo.setPassed(true);
					Floor.cantMalos--;
					score++;
					break;
				}
			}
//			ya se salio el cañon
			for (BadGuys bad : canons) {
				if (bad.getX() + bad.getAncho() <= 0) {
					canons.remove(bad); 
					canons.add( new BadGuys (1156, randPosY -111, canonNormal));
					break;
				}
			}
//			dispara
			for (BadGuys bad : canons) {
				if (bad.getX() <= 0 && !fireball.getMovimiento()) {
					fireball.arroja(bad.getX()+bad.getAncho()/2 -fireball.getAncho()/2,bad.getY()+bad.getAlto()/2 - fireball.getAlto()/2);
				}
			}
			if (fireball.getMovimiento()) {
				fireball.avanza();
			}
//			actualiza el piso
			for (Floor flo : floor) {
				flo.actualizaPos();
				if (flo.getX() <= -1156) {
					floor.remove(flo);
					break;
				}
			}
		}
	}
    
    /**
     * Método para grabar archivo que envia todas las variables del juego dentro
     * de un string, el cual es guardado con el nombre <code> NombreArchivo
     * </code>
     *
     * @throws IOException
     */
     public void grabaArchivo() throws IOException {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("guardar.txt", true)))) {
        out.println(nombre + ", " + score);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
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
        g.setFont(new Font("Sans-Serif", Font.BOLD, 34));
        g.drawString("" + score, 100, 80);
       
        if (status == STATUS.GAME ) {

                        	sky.render(g, this);

				for (Floor flo : floor) {
					flo.render(g, this);
				}
				g.drawImage(espada.getImagen(), espada.getX(), espada.getY(), this);
				

				if(fox.getDeath()){
					g.drawImage(foxDeath.getImagen(), dx - fox.getAncho()/2+5, dy - fox.getAlto()/2-30, this);
				}
				
				if (fox.getMoveRight() || fox.getMoveLeft()) {
					g.drawImage(fox.getImagenA(), fox.getX(), fox.getY(), this);
				} else {
					if (fox.getBrincaDoble()) {
						g.drawImage(fox.getImagenA(), fox.getX(), fox.getY(), this);
					} else {
						g.drawImage(fox.getImagenS(), fox.getX(), fox.getY(), this);
					}
				}
				for (BadGuys bad : canons) {
					if (Floor.cantMalos < 0) {
						g.drawImage(bad.getImagen(), bad.getX(), bad.getY(), this);
					}
				}
			} else {
				g.drawImage(pausaImg, getWidth() / 2 - new ImageIcon(pausaImg).getIconWidth() / 2, getHeight() / 2 - new ImageIcon(pausaImg).getIconHeight() / 2, this);
			}
			if (fireball.getMovimiento() ) {
				g.drawImage(fireball.getImagen(), fireball.getX(), fireball.getY(), this);
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
            if (e.getKeyCode() == KeyEvent.VK_LEFT && !pausa) {
                fox.setMoveLeft(true);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !pausa) {
                fox.setMoveRight(true);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !pausa) {
                fox.jump();
				if (fox.getBrinca()||!fox.getBrincaDoble()||fox.getJumps()<2) {
					fox.jumpDouble();
				}
            } else if (e.getKeyCode() == KeyEvent.VK_P) {
                pausa = !pausa;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
				if (sound) {
					backMusic.stop();
				} else {
					backMusic.play();
				}
				sound = !sound;
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
