/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Enrique O Hernandez A01185423
 * @author Enrique Martinez Guerrero A00919747
 *
 * La clase Animacion maneja una serie de imágenes (cuadros) y la cantidad de
 * tiempo que se muestra cada cuadro.
 */
package dreamwalker;

import java.awt.Image;
import java.util.ArrayList;

public class Animacion {

    private ArrayList cuadros;
    private int indiceCuadroActual;
    private long tiempoDeAnimacion;
    private long duracionTotal;

    /**
     * Crea una nueva Animacion vacía
     */
    public Animacion() {
        cuadros = new ArrayList();
        duracionTotal = 0;

        iniciar();
    }

    /**
     * Añade una cuadro a la animación con la duración indicada.
     *
     * @param imagen imagen a agregar
     * @param duracion tiempo que dura la imagen
     */
    public synchronized void sumaCuadro(Image imagen, long duracion) {
        duracionTotal += duracion;
        cuadros.add(new cuadroDeAnimacion(imagen, duracionTotal));
    }

    /**
     * Inicializa la animación desde el principio.
     */
    public synchronized void iniciar() {
        tiempoDeAnimacion = 0;
        indiceCuadroActual = 0;
    }

    /**
     * Actualiza la imagen actual de la animación si es necesario.
     *
     * @param tiempoTranscurrido tiempo a actualizar
     */
    public synchronized void actualiza(long tiempoTranscurrido) {
        if (cuadros.size() > 1) {
            tiempoDeAnimacion += tiempoTranscurrido;
            if (tiempoDeAnimacion >= duracionTotal) {
                tiempoDeAnimacion = tiempoDeAnimacion % duracionTotal;
                indiceCuadroActual = 0;
            }
            while (tiempoDeAnimacion > getCuadro(indiceCuadroActual).tiempoFinal) {
                indiceCuadroActual++;
            }
        }
    }

    /**
     * Captura la imagen actual de la animación.
     *
     * @return la imagen actual o null si no tiene imágenes.
     */
    public synchronized Image getImagen() {
        if (cuadros.size() == 0) {
            return null;
        } else {
            return getCuadro(indiceCuadroActual).imagen;
        }
    }

    /**
     * Metodo para obtener el indice de la imagen actual
     *
     * @return indice actual de la animacion
     */
    public int getCuadroActual() {
        return indiceCuadroActual;
    }

    /**
     * Regresa la imagen en el indice que se pide
     *
     * @param i indice a regresar
     * @return imagen en el indice i
     */
    public synchronized Image getImagen(int i) {
        return getCuadro(i).imagen;
    }

    /**
     * Metodo que regresa el cuadro en el indice indicado
     *
     * @param i indice
     * @return cuadro en el indice i
     */
    private cuadroDeAnimacion getCuadro(int i) {
        return (cuadroDeAnimacion) cuadros.get(i);
    }

    /**
     * Metodo para saber el numero de cuadros
     *
     * @return numero de cuadros
     */
    public int getCuadros() {
        return cuadros.size();
    }

    public class cuadroDeAnimacion {

        Image imagen;
        long tiempoFinal;

        /**
         * Metodo constructor
         */
        public cuadroDeAnimacion() {
            this.imagen = null;
            this.tiempoFinal = 0;
        }

        /**
         * Metodo para agregar una imagen
         *
         * @param imagen imagen a agregar
         * @param tiempoFinal el tiempo final de la imagen
         */
        public cuadroDeAnimacion(Image imagen, long tiempoFinal) {
            this.imagen = imagen;
            this.tiempoFinal = tiempoFinal;
        }

        /**
         * Metodo para obtener la imagen
         *
         * @return imagen
         */
        public Image getImagen() {
            return imagen;
        }

        /**
         * Metodo para obtener el tiempo final
         *
         * @return tiempo final
         */
        public long getTiempoFinal() {
            return tiempoFinal;
        }

        /**
         * Metodo para cambiar la imagen
         *
         * @param imagen
         */
        public void setImagen(Image imagen) {
            this.imagen = imagen;
        }

        /**
         * Metodo para cambiar el tiempo final
         *
         * @param tiempoFinal
         */
        public void setTiempoFinal(long tiempoFinal) {
            this.tiempoFinal = tiempoFinal;
        }
    }

    /**
     * Metodo de acceso que regresa el ancho de la imagen
     *
     * @return width, -1 si no existe una imagen
     */
    public int getWidth() {
        if (cuadros.isEmpty()) {
            return -1;
        } else {
            return getCuadro(indiceCuadroActual).imagen.getWidth(null);
        }
    }

    /**
     * Metodo de acceso que regresa el alto de la imagen
     *
     * @return height, -1 si no existe una imagen
     */
    public int getHeight() {
        if (cuadros.isEmpty()) {
            return -1;
        } else {
            return getCuadro(indiceCuadroActual).imagen.getHeight(null);
        }
    }
}
