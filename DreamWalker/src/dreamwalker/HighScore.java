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
    
    private int actualScore, highScore;
    
    public HighScore(){
        
        actualScore = 0;
        
        highScore = 0;
        
        try {
            leeArchivo();
        } catch (IOException ex) {
            try {
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
	 * Metodo que cambia el highscore actual del juego
	 * @param newHighScore es el highscore actual del juego
	 */
    public void setActualHighscore(int newHighscore){
        
        actualScore = newHighscore;
        
    }
    /**
	 * Metodo que regresa el highscore actual del juego.
	 * @return actualScore
	 */
    public int getActualHighscore(){
        
        return actualScore;
        
    }
    
    /**
	 * Metodo que cambia el maximo highscore del juego. 
	 * @param newHighScore es el highscore con el que termino el juego
	 */
    public void setHighscore(int newHighscore){
        
        highScore = newHighscore;
        
    }
    /**
	 * Metodo que regresa el maximo highscore del juego.
	 * @return highScore
	 */
    public int getHighscore(){
        
        return highScore;
        
    }
    
    /**
	 * Metodo <I>leeArchivo</I> lee el archivo previamente guardado
         * 
	 * @throws IOException en caso de ocurrir un error
	 */
    public void leeArchivo() throws IOException{
        
        File archivo1 = new File("Highscore.txt");
        
        DataInputStream archEntrada = new DataInputStream(new FileInputStream(archivo1));
        
        highScore = archEntrada.readInt();
        
        archEntrada.close();
        
    }
    /**
	 * Metodo <I>grabaArchivo</I> graba el juego actual en un archivo de texto en forma binaria
         * 
	 * @throws IOException en caso de ocurrir un error
	 */
    public void grabaArchivo() throws IOException {
        
        File archivo1 = new File("HighScore.txt");
                
        DataOutputStream archSalida = new DataOutputStream(new FileOutputStream(archivo1, false));
        
        archSalida.writeInt(highScore);
        
        archSalida.close();
        
    }
    
}
