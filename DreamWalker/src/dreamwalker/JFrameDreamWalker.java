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

        
         private int score;

    private int randPosY;

// strings
    private String[] arr;
    private String nombre;
    private final String nombreArchivo = "guardar.txt";

// boleanos
    private boolean pausa;      // bool que checa si se pauso
    private boolean instrucciones;
    private boolean sonido;
    private boolean inicio;

    //floating
    private long tiempoActual;  // tiempo actual

    // images
    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico

    private Image fotoBarraAbajo;
    private Image fotoBarraArriba;

    private Image gameOver;
    private Image fotoAvion;
    private Image tableroInstrucciones;
    private Image pausaImagen;
    private Image background;

    AffineTransform identity = new AffineTransform();

// animaciones
    private Animacion animAvion;
    private Animacion animArriba;
    private Animacion animAbajo;

// sounds
    private SoundClip jump; // sonido cuando saltas
    private SoundClip backMusic; //musica de fondo del juego
    private SoundClip loseSound; //musica de perder el juego
    private SoundClip crashSound; //musica cuando chocas
    private SoundClip coin; //sonido cuando ganas punto

    
    private boolean nombreIngresado;
    
     public static enum STATUS {
        MENU,
        PAUSE,
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
        setBackground(Color.decode("#C6FFFF"));
        status = STATUS.MENU;
        
        score = 0;
       

        instrucciones = false;
        sonido = true;
        inicio = false;
   
        nombreIngresado = false;

////        loseSound = new SoundClip("Resources/lostSound.wav");
////        crashSound = new SoundClip("Resources/crashSound.wav");
////        backMusic = new SoundClip("Resources/gameMusic.wav");
////        jump = new SoundClip("Resources/jumpSound.wav");
////        coin = new SoundClip("Resources/coin.wav");
////
////        gameOver = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resources/Gameover.png"));
////        pausaImagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resources/pause.png"));
////        fotoAvion = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resour    ces/avion/ah.png"));
////        background = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resources/background.png"));
////        tableroInstrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resourses/instruccionesTiroParabolico.jpg"));
////        fotoBarraAbajo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resources/tube1.png"));
////        fotoBarraArriba = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Resources/tube2.png"));
//
////        animAvion = new Animacion();
////        animArriba = new Animacion();
////        animAbajo = new Animacion();
////
////        animArriba.sumaCuadro(fotoBarraArriba, tiempoActual);
////        animAbajo.sumaCuadro(fotoBarraAbajo, tiempoActual);
////        animAvion.sumaCuadro(fotoAvion, 700);
//
//        backMusic.play();
    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
        //Crea el thread
        Thread hilo = new Thread(this);
        //Inicializa el thread
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

        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

        //Ciclo principal del Applet. Actualiza y despliega en pantalla la animación hasta que el Applet sea cerrado
        while (true) {
            if (!pausa && !instrucciones) {
                try {
                    actualiza();
                } catch (IOException ex) {
                    
                }
             
                checaColision();
                

            }

            repaint();

            //Hace una pausa de 200 milisegundos
            try {
                Thread.sleep(60);
            } catch (InterruptedException ex) {
                // no hace nada
            }
        }

    }

    /**
     * Metodo usado para checar la colision del objeto bueno con algún objeto
     * malo de la lista de malos, checa si algun malo choco con el <code>Applet
     * </code> por la parte inferior.
     */
    public void checaColision() {

        
        
        
        
        

    }

    /**
     * El método actualiza() del <code>Applet</code> que actualiza las
     * posiciones de el objeto bueno, los objetos malos y da los tiempos para
     * cada segmento de animacion.
     */
    public void actualiza() throws IOException {

        
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

        g.drawImage(background, 8, 30, this);
        g.setFont(new Font("Serif", Font.BOLD, 34));
        g.drawString("" + score, 100, 80);


    }

    @Override
    public void keyTyped(KeyEvent e) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
