package dreamwalker;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

public class Botones {

    private int posX;
    private int posY;
    private ImageIcon icono;

    /**
     * Constructor para crear un boton
     *
     * @param posX es la posicion X del objeto.
     * @param posY es la posicion Y del objeto.
     * @param image es la Imagen del objeto.
     */
    public Botones(int posX, int posY, Image image) {
        this.posX = posX;
        this.posY = posY;
        icono = new ImageIcon(image);
    }

    /**
     * Constructor para crear un boton
     *
     * @param posX es la posicion X del objeto.
     * @param posY es la posicion Y del objeto.
     * @param urll URL de la imagen
     */
    public Botones(int posX, int posY, String urll) {
        this.posX = posX;
        this.posY = posY;
        URL url = this.getClass().getResource(urll);
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        icono = new ImageIcon(image);
    }

    /**
     * Metodo para modificar la posX
     *
     * @param posX es la Pos X del objeto.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Metodo de acceso que regresa la posicion en x del objeto
     *
     * @return posX es la posicion en x del objeto.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto
     *
     * @param posY es la posicion en y del objeto.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la posicion en y del objeto.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Metodo modificador usado para cambiar el icono del objeto
     *
     * @param icono es el icono del objeto.
     */
    public void setImageIcon(ImageIcon icono) {
        this.icono = icono;
    }

    /**
     * Metodo de acceso que regresa el icono del objeto
     *
     * @return icono es el icono del objeto.
     */
    public ImageIcon getImageIcon() {
        return icono;
    }

    /**
     * Metodo de acceso que regresa el ancho del icono
     *
     * @return el ancho del icono.
     */
    public int getAncho() {
        return icono.getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del icono
     *
     * @return el alto del icono.
     */
    public int getAlto() {
        return icono.getIconHeight();
    }

    /**
     * Metodo que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /**
     * Metodo que indica si un punto está dentro del Boton
     *
     * @param x coordenada X
     * @param y coordenada Y
     * @return si esta o no dentro del boton
     */
    public boolean contiene(int x, int y) {
        return getPerimetro().contains(x, y);
    }

    /**
     * Metodo de acceso que regresa la imagen del icono
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * icono.
     */
    public Image getImagenI() {
        return icono.getImage();
    }
}
