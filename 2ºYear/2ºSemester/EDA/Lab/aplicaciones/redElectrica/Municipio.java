package aplicaciones.redElectrica;

/** Clase Municipio: representa un municipio del grafo etiquetado
 *  de la aplicacion. 
 *  Al ser la clase de la clave de dos de los Map que TIENE dicho
 *  grafo, debe sobreescribir los metodos equals y hashCode que
 *  hereda de Object
 *  
 * @version (Curso 2017/18)
 */

public class Municipio {
    
    private String nombre;      // Nombre del municipio    
    private int poblacion;      // En numero de habitantes    
    private double extension;   // En km2   
    private int posX, posY;     // Coordenadas en el mapa
    
    /** Crea un municipio de nombre n, poblacion p, extension e y
      * posicion en el mapa (pX, pY)
      * @param  n  Nombre (String)
      * @param  p  Numero (int) de habitantes
      * @param  e  Extension (double) en Km2
      * @param pX, pY, coordenadas (int) en el mapa
     */    
    public Municipio(String n, int p, double e, int pX, int pY) {
        nombre = n;
        poblacion = p;
        extension = e;
        posX = pX; posY = pY;
    }
    
    /** Crea un municipio de nombre n, poblacion 0 habitantes, 
      * extension 0.0 Km2 y posicion en el mapa (0, 0)
      * @param  n   Nombre (String)
     */    
    public Municipio(String n) { 
        this(n, 0, 0.0, 0, 0); 
    }
    
    /** Devuelve el nombre de un municipio
      * @return String Nombre
     */
    public String getNombre() { return nombre; }
    
    /** Devuelve el numero de habitantes de un municipio
      * @return int Poblacion
     */
    public int getPoblacion() { return poblacion; }
    
    /** Devuelve la extension de un municipio
      * @return  double Km2 de extension
     */
    public double getExtension() { return extension; }
    
    /** Devuelve la coordenada horizontal de un municipio en el mapa
      * @return int Pixels como coordenada horizontal
     */
    public int getPosX() { return posX; }
    
    /** Devuelve la coordenada vertical de un municipio en el mapa
      * @return int pixels como coordenada vertical
     */
    public int getPosY() { return posY; }
    
    /** Devuelve un String con las componentes de un municipio en un cierto formato
      **NO modificar** el codigo pues el formato es el mismo que tienen las 
      * componentes del municipio en los ficheros de datos a partir de los que
      * se crea el grafo de la aplicacion
      * @return String que representa al municipio en formato texto
     */
    public String toString() {
        String res = nombre + ";" + poblacion + ";" 
                     + extension + ";" + posX + ";" + posY;
        return res;
    }
    
    /** Comprueba si un municipio (this) es igual a otro, 
      * i.e. si tienen el mismo nombre
      * @param  otro   Municipio del que se quiere comprobar si es igual a this
      * @return boolean true si this y otro son iguales y false en caso contrario
     */
    public boolean equals(Object otro) { 
        return otro instanceof Municipio 
               && this.nombre.equals(((Municipio) otro).nombre); 
    }

    /** Devuelve el valor hash de un municipio, el de su nombre
      * @return int Valor hash
     */
    public int hashCode() { return nombre.hashCode(); }
    
}
