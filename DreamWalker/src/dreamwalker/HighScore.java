/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamwalker;

import java.io.File;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NLCJohn
 */
public class HighScore {

    private int highScore[];
    private String nombre[];
    private final int MAX = 5;
    private int numTrofeo = -1;

    /**
     * Metodo constructor de highscores, si no hay un archivo, se crea uno nuevo
     */
    public HighScore() {
        nombre = new String[MAX];
        highScore = new int[MAX];

        try {
            leeArchivo();
        } catch (IOException ex) {
            try {
                initHighScore();
                grabaArchivo();
            } catch (IOException ex1) {
                try {
                    leeArchivo();
                } catch (IOException ex2) {
                    System.out.println("Error al leer y escribir el archivo");
                }

            }
        }
    }

    /**
     * Metodo que inicializa los highscores
     */
    public void initHighScore() {
        for (int i = 0; i < MAX; i++) {
            highScore[i] = 0;
            nombre[i] = "";
        }
    }

    /**
     * Metodo que cambia el highscore actual del juego
     *
     * @param newHighscore es el highscore actual del juego
     * @param i numero de highscore a guardar [nth]
     */
    public void setHighscore(int newHighscore, int i) {
        highScore[i] = newHighscore;
    }

    /**
     * Metodo que regresa el highscore actual del juego.
     *
     * @param i numero de posicion de highscore [nth]
     * @return actualScore
     */
    public int getHighscore(int i) {
        return highScore[i];
    }

    /**
     * Metodo que cambia el highscore actual del juego
     *
     * @param nombre1 es el nombre de quien hizo el highscore
     * @param i numero de posicion de highscore [nth]
     */
    public void setHighscoreName(String nombre1, int i) {
        nombre[i] = nombre1;
    }

    /**
     * Metodo que regresa el nombre de quien hizo el nth highscore.
     *
     * @param i es el nth highscore
     * @return nombre[i] nombre de quien hizo el nth highscore
     */
    public String getHighscoreName(int i) {
        return nombre[i];
    }

    /**
     * Metodo que regresa el Trofeo .
     *
     * @return numTrofeo el numero de trofeo
     */
    public int getTrofeo() {
        return numTrofeo;
    }

    /**
     * Metodo que resetea el trofeo
     */
    public void resetTrofeo() {
        numTrofeo = -1;
    }

    /**
     * Metodo que regresa en que lugar queda el highscore
     *
     * @param nombre1 nombre de quien hizo el highscore
     * @param newHighscore numero de puntos a guardar
     * @return que lugar queda el highscore
     */
    public int setHighscoreAuto(String nombre1, int newHighScore) {
        try {
            leeArchivo();
        } catch (IOException ex) {
            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
        }
        sort();
        insertScore(newHighScore, nombre1);
        try {
            grabaArchivo();
        } catch (IOException ex) {
            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (nombre[0] == nombre1 && highScore[0] == newHighScore) {
            return 0;
        } else if (nombre[1] == nombre1 && highScore[1] == newHighScore) {
            return 1;
        } else if (nombre[2] == nombre1 && highScore[2] == newHighScore) {
            return 2;
        }
        return -1;
    }

    /**
     * Metodo que guarda el nombre y score
     *
     * @param mscore el score a guardar
     * @param nombre1 nombre de quien hizo el score
     */
    public void insertScore(int mscore, String nombre1) {
        //find insert point
        int i = 0;
        while (i < MAX && highScore[i] > mscore) {
            i++;
        }
        if (i < MAX) {
            //you found a place to insert the score
            for (int j = MAX - 1; j > i; j--) {
                highScore[j] = highScore[j - 1];
                nombre[j] = nombre[j - 1];
            }
            highScore[i] = mscore;
            nombre[i] = nombre1;
        }
    }

    /**
     * Arregla el orden de los highscores de mayor a menor
     */
    void sort() {
        for (int i = 0; i < MAX - 1; i++) {
            for (int j = 0; j < MAX - 1 - i; j++) {
                if (highScore[j] < highScore[j + 1]) {
                    /* 
                     Swap revenue with lowest revenue 
                     */
                    int revenueTemp = highScore[j];
                    highScore[j] = highScore[j + 1];
                    highScore[j + 1] = revenueTemp;

                    /* 
                     Swap name using the same index 
                     of the revenue 
                     */
                    String nameTemp = nombre[j];
                    nombre[j] = nombre[j + 1];
                    nombre[j + 1] = nameTemp;
                }
            }
        }
    }

    /**
     * Metodo que lee el archivo previamente guardado
     *
     * @throws IOException en caso de ocurrir un error
     */
    public void leeArchivo() throws IOException {
        File archivo1 = new File("Highscore.txt");

        DataInputStream archEntrada = new DataInputStream(new FileInputStream(archivo1));

        for (int i = 0; i < MAX; i++) {
            nombre[i] = archEntrada.readUTF();
            highScore[i] = archEntrada.readInt();
        }

        archEntrada.close();
    }

    /**
     * Metodo que graba el juego actual en un archivo de texto en forma binaria
     *
     * @throws IOException en caso de ocurrir un error
     */
    public void grabaArchivo() throws IOException {
        File archivo1 = new File("HighScore.txt");

        DataOutputStream archSalida = new DataOutputStream(new FileOutputStream(archivo1, false));
        for (int i = 0; i < MAX; i++) {
            archSalida.writeUTF(nombre[i]);
            archSalida.writeInt(highScore[i]);
        }

        archSalida.close();
    }
}
