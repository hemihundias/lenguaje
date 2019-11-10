/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 *
 * @author Hemihundias
 */
public class Lenguaje {
    private static File fichero;
    private static RandomAccessFile raf;
    private static FileLock bloqueo;
    /**
     * @param args the command line arguments
     * Este es el método principal, donde recogemos los parámetros dados al ejecutar la aplicación.
     * Se verifica que el primer parámetro sea un número, para el total de veces a ejecutar el código.
     * también recogemos el segundo parámetro, que será el nombre del fichero a crear o usar, en caso de que ya exista.
     * Generaremos tantas palabras como indique el primer parámetro, y las escribiremos en el fichero pasado como segundo parámetro.
     */          
    public static void main(String[] args){
        // TODO code application logic here
        String num;
        num = args[0];                
        if(!isNumeric(num)){
            System.out.println("Valor incorrecto.");
            return;
        }   
        int r = Integer.parseInt(num);
      
        try{
            fichero = new File(args[1]);
        
            if(!fichero.exists()){
                fichero.createNewFile();
            }
            raf = new RandomAccessFile(fichero, "rw");
            bloqueo = raf.getChannel().lock();
            raf.seek(raf.length());
            String linea = null;
            for(int i=0;i<r;i++){
                linea = generarPalabra(linea);
                if(raf.getFilePointer() == 0){
                    raf.writeBytes(linea);
                }else{
                    raf.writeBytes("\n" + linea);
                }
            }            
            
            raf.seek(0);
            bloqueo.release();
        }catch (FileNotFoundException e){
            System.err.println(e);
        }catch (IOException e){
            System.err.println(e);
        }finally {
            try {
		if (bloqueo != null) {
                    bloqueo.release(); 
                }else if (raf != null){
                    raf.close();
                }
            }catch (IOException e) {
		System.err.println(e);
		}	
            }             		
        }    
    /**
     * 
     * @param num
     * @return 
     * Este método comprueba que el primer parámetro sea un número. Devuelve true en caso afirmativo.
     */    
    public static boolean isNumeric(String num) {
        boolean resultado;
        try {
            Integer.parseInt(num);
            resultado = true;
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }    
    /**
     * 
     * @param linea
     * @return 
     * Este método genera palabras al azar de 8 carácteres y devuelve la palabra generada
     */    
    public static String generarPalabra(String linea){
        Random ascii = new Random();
        linea = "";
        char letra;
        for(int i=0;i<8;i++){
            letra = (char)(ascii.nextInt(26) + 97);
            linea += letra;
        }        
        return linea;                
    }     
}
