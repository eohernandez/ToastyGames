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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ConcurrentModificationException;
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
    public static int score;
    public static int temp;
    public int tempScore;
    public int dificultad;
    private int randPosY;
    private int randPosYc;
    private int randPosXc;
    private int randPosYcr;
    private int randPosXcr;
    private int dx;
    private int dy;
    private int trofeo;

    private String nombre;

//	boleanos
    private boolean pausa;      // bool que checa si se pauso
    private boolean sound;
//	floating
    private long tiempoActual;  // tiempo actual
//	images
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico

//	HighScores
    private HighScore hScore;
//	SoundClips
    public static SoundClip backMusic;
    public static SoundClip playMusic;
    public static SoundClip deathSound;

//	animaciones
    private Animacion espadaNormal;
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
    private BadGuys crawler;
    private Animacion crawlerNormal;
    private Animacion crawlerRapido;
    private URL sonidoURL;
    private URL imagenURL;
    private LinkedList<BadGuys> canons;

    private LinkedList<Floor> floor;

    private Menu menu;
    private Instructions instructions;
    private gameOver gameOver;
    private Image menuBG;
    private Image scoreB;
    private Image[] gameOverBG;
    private Image menuFox;
    private Trophies trophies;
    private FireBall fireball;
    private BadGuys espada;
    private Fox fox;

    private Image pausaImg;
    private Image imagenAnimaciones;

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
     * Se inicializa el tamaño del applet en 1152x720px
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

        backMusic = new SoundClip("Images/Background/MainMusic.wav");
        playMusic = new SoundClip("Images/Background/PlayMusic.wav");
        deathSound = new SoundClip("Images/Background/DeathSound.wav");

        backMusic.play();
        backMusic.setLooping(true);

        hScore = new HighScore();

        playing = true;
        pausa = false;
        sound = true;

        imagenURL = this.getClass().getResource("Images/Background/menu.png");
        menuBG = new ImageIcon(imagenURL).getImage();

        imagenURL = this.getClass().getResource("Images/Background/scoreB.png");
        scoreB = new ImageIcon(imagenURL).getImage();

        imagenURL = this.getClass().getResource("Images/Fox/FoxGif.gif");
        menuFox = new ImageIcon(imagenURL).getImage();

        imagenURL = this.getClass().getResource("Images/Botones/pause.png");
        pausaImg = new ImageIcon(imagenURL).getImage();

        imagenURL = this.getClass().getResource("Images/Background/sky.png");

        Image skyI = new ImageIcon(imagenURL).getImage();

        trofeo = -1;

        tempScore = 0;

        nombre = "";

        menu = new Menu(menuBG, menuFox);
        gameOverBG = new Image[3];

        instructions = new Instructions(menuBG);

        for (int x = 1; x <= 3; x++) {
            imagenURL = this.getClass().getResource("Images/Background/GameOver" + x + ".png");
            gameOverBG[x - 1] = new ImageIcon(imagenURL).getImage();
//			gameOverBG[x-1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Images/Background/GameOver" + x + ".png"));
        }
        gameOver = new gameOver(gameOverBG);
        trophies = new Trophies(menuBG);

        FoxStanding = new Animacion();
        FoxRunning = new Animacion();
        FoxJump1 = new Animacion();
        FoxJump2 = new Animacion();
        foxDeath = new Animacion();
        canonNormal = new Animacion();
        canonOpen = new Animacion();
        canonFire = new Animacion();
        espadaNormal = new Animacion();
        canonBall = new Animacion();
        crawlerNormal = new Animacion();
        crawlerRapido = new Animacion();
        animSky = new Animacion();
        animSky.sumaCuadro(skyI, 100);

        floor = new LinkedList();
        canons = new LinkedList();

        for (int x = 1; x <= 6; x++) {
            imagenURL = this.getClass().getResource("Images/Enemigos/Canon/Canon" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            canonNormal.sumaCuadro(imagenAnimaciones, 100);
        }

        for (int x = 1; x <= 4; x++) {
            imagenURL = this.getClass().getResource("Images/Spells/FireBall/FireBall" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            canonBall.sumaCuadro(imagenAnimaciones, 100);
        }

        fireball = new FireBall(0, 500, canonBall);

        for (int x = 1; x <= 5; x++) {
            imagenURL = this.getClass().getResource("Images/Enemigos/Canon/CanonAbre" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            canonOpen.sumaCuadro(imagenAnimaciones, 100);
        }

        for (int x = 1; x <= 2; x++) {
            imagenURL = this.getClass().getResource("Images/Enemigos/Canon/CanonAnticipacionDisparoVolteado-" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            canonFire.sumaCuadro(imagenAnimaciones, 200);
        }

        randPosY = 414 + (int) (Math.random() * 306);

        floor.add(new Floor(0, randPosY));

        canons.add(new BadGuys(1150, randPosY - 200, canonNormal));

//		Animacion de espada
        for (int x = 1; x <= 4; x++) {
            imagenURL = this.getClass().getResource("Images/Enemigos/Espada/Espada" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            espadaNormal.sumaCuadro(imagenAnimaciones, 100);
        }

        randPosYc = 0 + (int) (Math.random() * 6); //randon*rango + minimo
        randPosXc = getWidth() + (int) (Math.random() * 200); //randon*rango + minimo
        espada = new BadGuys(randPosXc, randPosYc, espadaNormal);
        espada.setVelX(4);

        //		Animacion de crawler
        for (int x = 1; x <= 4; x++) {
            imagenURL = this.getClass().getResource("Images/Enemigos/Crawler/Crawler" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            crawlerNormal.sumaCuadro(imagenAnimaciones, 100);
        }
        for (int x = 1; x <= 4; x++) {
            imagenURL = this.getClass().getResource("Images/Enemigos/Crawler/Crawler" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            crawlerRapido.sumaCuadro(imagenAnimaciones, 25);
        }

        randPosYcr = 125 + (int) (Math.random() * 6); //randon*rango + minimo
        randPosXcr = -1000 - (int) (Math.random() * 200); //randon*rango + minimo
        // crawler = new BadGuys(randPosXcr,randPosYcr,crawlerNormal);
        crawler = new BadGuys(randPosXcr, randPosYcr, crawlerNormal);
        crawler.setVelX(-3);

        //
        for (int x = 1; x <= 8; x++) {
            imagenURL = this.getClass().getResource("Images/Fox/FoxRun" + x + ".gif");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            FoxRunning.sumaCuadro(imagenAnimaciones, 100);
        }

        for (int x = 1; x <= 17; x++) {
            imagenURL = this.getClass().getResource("Images/Fox/FoxStanding" + x + ".png");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            FoxStanding.sumaCuadro(imagenAnimaciones, 100);
        }

        for (int x = 1; x <= 3; x++) {
            imagenURL = this.getClass().getResource("Images/Fox/FoxJump" + x + ".gif");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            FoxJump2.sumaCuadro(imagenAnimaciones, 25);
        }

        for (int x = 1; x <= 50; x++) {
            imagenURL = this.getClass().getResource("Images/Fox/FoxDeath" + x + ".gif");
            imagenAnimaciones = new ImageIcon(imagenURL).getImage();
            foxDeath.sumaCuadro(imagenAnimaciones, 50);
        }

        fox = new Fox(100, floor.get(0).getY() - new ImageIcon(FoxStanding.getImagen()).getIconHeight(), FoxRunning);
        fox.setStand(FoxStanding);
        fox.setAnim(FoxRunning);
        fox.setVelX(3);
        fox.setVelY(0);
        sky = new Sky(animSky);
        crawlerRapido.iniciar();
        setResizable(false);
        dificultad = 0;
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

        fox = new Fox(100, floor.get(0).getY() - new ImageIcon(FoxStanding.getImagen()).getIconHeight(), FoxRunning);
        fox.setStand(FoxStanding);
        fox.setAnim(FoxRunning);
        fox.setVelX(3);
        fox.setVelY(0);
        score = -1;
        sky = new Sky(animSky);
        randPosY = 414 + (int) (Math.random() * 306);
        floor.clear();
        canons.clear();
        floor.add(new Floor(0, randPosY));
        canons.add(new BadGuys(1150, randPosY - 200, canonNormal));
        espada = new BadGuys(randPosXc, randPosYc, espadaNormal);
        espada.setVelX(4);
        crawler = new BadGuys(randPosXcr, randPosYcr, crawlerNormal);
        crawler.setVelX(-3);
        Floor.cantMalos = 1;
        foxDeath.iniciar();
        FoxStanding.iniciar();
        FoxRunning.iniciar();
        crawlerRapido.iniciar();
        tempScore = 0;
        dificultad = 0;

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
                if (bad.getX() + bad.getAncho() >= flo.getX() && bad.getX() + bad.getAncho() <= flo.getX() + flo.getAncho()) {
                    if (bad.getY() + bad.getAlto() < flo.getY()) {
                        bad.cae();
                        break;
                    }
                }
            }
        }

        for (Floor flo : floor) {
            if (flo.intersecta(fox) && fox.getMoveLeft() && fox.getX() >= flo.getX() + flo.getAncho() - 10) {
                fox.setX(flo.getX() + flo.getAncho() + 5);
            }
            if (fox.within(flo.getX() - fox.getAncho() / 2, flo.getAncho() - fox.getAncho(), fox.getX(), fox.getAncho() / 2) && flo.intersecta(fox)) {
                fox.setAterriza(true);
                fox.landed();
                fox.setY(flo.getY() - fox.getAlto());
            }
            if (flo.intersecta(fox) && fox.getMoveRight()) {
                fox.setX(flo.getX() - fox.getAncho());
            }
        }

        for (BadGuys bad : canons) {
            if (bad.intersecta(fox)) {
                fox.setDeath(true);

                playMusic.stop();
                dx = fox.getX();
                dy = fox.getY();
            }
        }

        if (espada.intersectaChico(fox)) {
            fox.setDeath(true);

            playMusic.stop();
            dx = fox.getX();
            dy = fox.getY();
        }
        if (crawler.intersectaChico(fox)) {
            fox.setDeath(true);

            playMusic.stop();
            dx = fox.getX();
            dy = fox.getY();
        }

        for (Floor flo : floor) {
            if (flo.intersecta(fireball)) {
                fireball.volverInicio();
                //fireball = new FireBall(0,0,canonBall);
            }
        }

        if (fireball.intersecta(fox)) {
            fox.setDeath(true);

            playMusic.stop();
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

            if (!fox.getDeath()) {

                if (tempScore == 6) {
                    crawler.setVelX(crawler.getVelX() - dificultad);
                    dificultad++;
                    tempScore = 0;
                }
                for (BadGuys bad : canons) {
                    bad.setX(bad.getX() - dificultad);
                    break;
                }
                sky.move(tiempoTranscurrido);
            }
            tiempoActual += tiempoTranscurrido;

            crawlerRapido.actualiza(tiempoTranscurrido);

            if (fox.getDeath()) {

                foxDeath.actualiza(tiempoTranscurrido);

                fox.actualiza(tiempoTranscurrido);
                if (foxDeath.getCuadroActual() == 33) {
                    fox.setX(-300);
                }
                if (foxDeath.getCuadroActual() >= 49) {
                    gameOver.setTrofeo(trofeo);
                    status = STATUS.GAMEOVER;
                    nombre = "";
                    nombre = JOptionPane.showInputDialog("Cual es tu nombre?");
                    trofeo = hScore.setHighscoreAuto(nombre, score);
                    gameOver.setTrofeo(trofeo);
                    fox.setDeath(false);
                    trofeo = -1;
//					restart();

                }
            } else {
                fox.actualiza(tiempoTranscurrido);
                fox.setX(fox.getX() - fox.getVelX());
                fox.cae();
                fox.setY(fox.getY());
            }

            canonBall.actualiza(tiempoTranscurrido);
            for (BadGuys bad : canons) {
                bad.actualiza(tiempoTranscurrido);
            }
//			Actualiza posiciones de la espada y crawler
            if (!fox.getDeath()) {
                espada.actualiza(tiempoTranscurrido);
                espada.setX(espada.getX() - espada.getVelX());
                crawler.actualiza(tiempoTranscurrido);

            }

            if (Floor.cantMalos == 0) {
                canons.remove();
                canons.add(new BadGuys(1156, randPosY - 111, canonNormal));
            }

            if (espada.getX() + espada.getAncho() < 0) {
                randPosYc = 0 + (int) (Math.random() * 50); //randon*rango + minimo
                randPosXc = getWidth() + (int) (Math.random() * 400); //randon*rango + minimo
                espada.setX(randPosXc);
                espada.setY(randPosYc);
                espada.setCount(0);
                espada.setSigue(false);
            }

            if (espada.getCount() == 1 && espada.getSigue()) {
                if (!fox.getDeath()) {
                    espada.setX(espada.getX() + espada.getVelX() + 8);
                }
                if (espada.getX() + espada.getAncho() >= getWidth()) {
                    espada.setCount(espada.getCount() + 1);
                }
            }

            if (espada.getSigue() && espada.getCount() > 1) {
                if (!fox.getDeath()) {
                    espada.setX(espada.getX() - fox.getVelY() - 1);
                }
            }

            if (espada.getX() > fox.getX() && fox.getX() + fox.getAncho() + 20 > espada.getX() && espada.getCount() == 0) {
                espada.setSigue(true);
                espada.setCount(espada.getCount() + 1);
//			   System.out.println("Verdad");
            }

            //Crawler
            if (crawler.getX() > 1300) {

                randPosYcr = 125 + (int) (Math.random() * 6); //randon*rango + minimo
                randPosXcr = -1000 - (int) (Math.random() * 400); //randon*rango + minimo
                crawler = new BadGuys(randPosXcr, randPosYcr, crawlerNormal);
                crawler.setVelX(-3);
                crawler.setCount(0);
                crawler.setSigue(false);

            }
            if (crawler.getY() > 800) {
                randPosYcr = 125 + (int) (Math.random() * 6); //randon*rango + minimo
                randPosXcr = -1000 - (int) (Math.random() * 400); //randon*rango + minimo
                crawler = new BadGuys(randPosXcr, randPosYcr, crawlerNormal);
                crawler.setVelX(-3);
                crawler.setCount(0);
                crawler.setCount2(0);
                crawler.setSigue(false);

            }
            if (crawler.getCount() == 0) {
                if (!fox.getDeath()) {
                    crawler.setX(crawler.getX() - crawler.getVelX());
                }
            }

            if (crawler.getCount() == 1 && crawler.getSigue()) {

                if (!fox.getDeath()) {
                    crawler.setCount2(crawler.getCount2() + 1);
                    if (crawler.getCount2() >= 50) {
                        crawler.setX(crawler.getX());
                        crawler.setVelY(18);
                        crawler.setY(crawler.getY() + crawler.getVelY());
                    } else {
                        crawler.setX(crawler.getX() + 1);
                    }

                }

            }

            if (crawler.getX() + crawler.getAncho() / 2 > fox.getX() && fox.getX() + fox.getAncho() + 20 < crawler.getX() + crawler.getAncho() && crawler.getCount() == 0) {
                crawler.setSigue(true);
                crawler.setCount(crawler.getCount() + 1);

            }

//			checo si algun malo choco con algun piso por la derecha
//			para que se quede parado si es eel caso
            for (BadGuys bad : canons) {
                for (Floor flo : floor) {
                    if (bad.checaIntersecionDerecha(flo) || fox.getDeath()) {
                        bad.setX(bad.getX());
                    } else {
                        bad.setX(bad.getX() - 5);
                    }
                    break;
                }
            }
//			cambia animacion cuando el zorro esta cerca
            for (BadGuys bad : canons) {
                if (fox.getX() + fox.getAncho() + 200 >= bad.getX() && bad.getCambiaAnim()) {
                    bad.setAnimacion(canonOpen);
                    bad.setY(bad.getY() - 30);
                    bad.setCambiaAnim(false);
                }
            }
//			cambia animacion a disparo
            for (BadGuys bad : canons) {
                if (bad.getX() <= 200) {
                    bad.setAnimacion(canonFire);
                }
            }

            if (!fox.getDeath()) {
                if (fox.getMoveLeft()) {
                    fox.setX(fox.getX() - 6);
                }
                if (fox.getMoveRight()) {
                    fox.setX(fox.getX() + 9);
                }
                if (fox.getBrinca()) {
                    fox.brinca();
                }
                if (fox.getBrincaDoble()) {
                    fox.setAnim(FoxJump2);
                }
                if (fox.getX() <= 0) {
                    fox.setX(0);
                }
                if (fox.getX() + fox.getAncho() >= getWidth()) {
                    fox.setX(getWidth() - fox.getAncho());
                }
                if (!fox.getBrincaDoble()) {
                    fox.setAnim(FoxRunning);
                }
                if (fox.getX() >= getWidth() * .85) {

                    fox.setX((int) (getWidth() * .85));

                }
            }
//   		checa si el piso ya se termino
            for (Floor flo : floor) {
                if (flo.getX() <= 0 && !flo.getPassed()) {
                    randPosY = 414 + (int) (Math.random() * 306);
                    floor.add(new Floor(1152, randPosY));
                    flo.setPassed(true);
                    Floor.cantMalos--;
                    score++;
                    temp = score;
                    tempScore++;
                    break;
                }
            }
//			ya se salio el cañon
            for (BadGuys bad : canons) {
                if (bad.getX() + bad.getAncho() <= 0) {
                    canons.remove(bad);
                    canons.add(new BadGuys(1156, randPosY - 111, canonNormal));
                    break;
                }
            }
//			dispara
            for (BadGuys bad : canons) {
                if (bad.getX() <= 0 && !fireball.getMovimiento()) {

                    //fireball.arroja(bad.getX()+bad.getAncho()/2 -fireball.getAncho()/2,bad.getY()+bad.getAlto()/2 - fireball.getAlto()/2);
                    fireball.arroja(bad.getX() + bad.getAncho() / 2, bad.getY() + bad.getAlto() / 2);

                }
            }

            if (!fox.getDeath()) {
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

    }

    /**
     * Método para grabar archivo que envia todas las variables del juego dentro
     * de un string, el cual es guardado con el nombre <code> NombreArchivo
     * </code>
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("guardar.txt", true)))) {
            out.println(nombre + ", " + score);
        } catch (IOException e) {
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
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia. ph
     *
     * @param g es el elemento grafico usado para dibujar.
     */
    public void paint1(Graphics g) {
        g.drawImage(sky.getImagen(), sky.getX(), sky.getY(), this);
        if (status == STATUS.GAME) {

            g.setFont(new Font("Sylfaen", Font.BOLD, 40));
            g.drawImage(scoreB, 63, 61, this);
            g.drawString("" + score, 100, 100);

            sky.render(g, this);

            if (fox.getDeath()) {
                if (crawler.getCount() == 0) {
                    g.drawImage(crawler.getImagen(0), crawler.getX(), crawler.getY(), this);
                }
                if (crawler.getCount() == 1) {
                    g.drawImage(crawlerRapido.getImagen(0), crawler.getX(), crawler.getY(), this);
                }
                g.drawImage(espada.getImagen(0), espada.getX(), espada.getY(), this);
            } else {
                if (crawler.getCount() == 0) {
                    g.drawImage(crawler.getImagen(), crawler.getX(), crawler.getY(), this);
                }
                if (crawler.getCount() == 1) {
                    g.drawImage(crawlerRapido.getImagen(), crawler.getX(), crawler.getY(), this);
                }
                g.drawImage(espada.getImagen(), espada.getX(), espada.getY(), this);
            }

            try {
                for (Floor flo : floor) {
                    flo.render(g, this);
                }
            } catch (ConcurrentModificationException e) {
            }

            if (fox.getDeath()) {
                g.drawImage(foxDeath.getImagen(), dx - fox.getAncho() / 2 + 5, dy - fox.getAlto() / 2 - 30, this);
            }

            if (fox.getMoveRight() || fox.getMoveLeft() && !fox.getDeath()) {
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
                    if (!fox.getDeath()) {
                        g.drawImage(bad.getImagen(), bad.getX(), bad.getY(), this);
                    } else {
                        g.drawImage(bad.getImagen(0), bad.getX(), bad.getY(), this);
                    }
                }
            }
            if (pausa) {
                g.drawImage(pausaImg, getWidth() / 2 - new ImageIcon(pausaImg).getIconWidth() / 2, getHeight() / 2 - new ImageIcon(pausaImg).getIconHeight() / 2, this);
            }
            if (fireball.getMovimiento()) {
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
    public void keyPressed(KeyEvent e) {
        if (status == STATUS.GAME) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && !pausa) {
                fox.setMoveLeft(true);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !pausa) {
                fox.setMoveRight(true);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !pausa) {
                fox.jump();
                if (fox.getBrinca() || !fox.getBrincaDoble() || fox.getJumps() < 2) {
                    fox.jumpDouble();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_P) {
                pausa = !pausa;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                if (sound) {
                    playMusic.stop();
                } else {
                    playMusic.play();

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
        gameOver.mouseClicked(e, this);
        menu.mouseClicked(e, this);
        trophies.mouseClicked(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
