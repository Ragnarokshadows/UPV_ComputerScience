package pract4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Year;
import java.util.Scanner;
/**
 * Clase SortFiles. 
 * 
 * @author (PRG) 
 * @version (2017/18)
 */
public class SortFiles {
     
    public static void main(String[] args)  {
        Scanner keyB = new Scanner(System.in);
        String msg = "Año de los datos (hasta 10 años atrás): ";
        int currentY = Year.now().getValue();
        int year = CorrectReading.nextInt(keyB, msg, 
                                          currentY - 10, currentY);     
        
        // Busca en la ubicacion indicada un directorio de nombre 
        // "dataFilesAAAA", siendo AAAA el año leido de teclado
        System.out.print("Ubicación del directorio con los ficheros a ordenar: ");
        String dirParent = keyB.next(); 
        File f = new File(dirParent + "/dataFiles" + year);
        if (f.isDirectory()) {
            // El resultado se deja en el directorio "resultFilesAAAA"
            File fResult = new File(dirParent + "/resultFiles" + year);
            if (!fResult.isDirectory()) { fResult.mkdir(); }
            reportedSortFiles(f.listFiles(), year, fResult);
        } else { 
            System.out.println("Error: " + f 
                + " no es un directorio del sistema.");
            System.exit(-1);
        }
    } 
    
    /** Clasifica los datos leidos de un array de ficheros de texto. 
     *  Los datos corresponden a fechas de un determinado año. Se filtran los 
     *  datos con algun defecto de formato, emitiendo un informe de errores. 
     *  Precondicion: El formato de linea reconocible es
     *        dia mes cantidad
     *  en donde dia y mes deben ser enteros correspondientes a una fecha 
     *  valida del año, y cantidad debe ser un entero > 0.
     *  En un fichero "result.out" se escriben en orden cronologico las 
     *  cantidades acumuladas para cada fecha. En un fichero "result.log" se 
     *  escriben las lineas con datos defectuosos.
     *  Si hay problemas de acceso a alguno de los ficheros, el proceso 
     *  no se completa.
     *     
     *  @param listF array de los ficheros fuente de los datos
     *  @param int year año al que corresponden los datos
     *  @param File place lugar del sistema en donde se guardan 
     *  los ficheros .out y .log
     */
    public static void reportedSortFiles(File[] listF, int year, File place) {
       // A  COMPLETAR 
       
       Scanner in = null;
       PrintWriter out = null, err = null;
       File f = null;
       
       try{
           SortedRegister c = new SortedRegister(year);
           
           f = new File(place + "/result.log");
           err = new PrintWriter(f);
           
           for (int i = 0; i < listF.length;i++){
               f = listF[i];
               in = new Scanner(f);
               err.println(">>>>> File " + listF[i].getName() + " <<<<<");
               c.add(in, err);
               in.close();
           }
           
           f = new File(place + "/result.out");
           out = new PrintWriter(f);
           c.save(out);
           out.close();
       } catch (FileNotFoundException fnfe){
           System.err.println("Incomplete process: Error to open the file: " + f);
       } finally {
           if (err != null){err.close();}
       }
    }
}