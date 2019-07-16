package aplicaciones.redElectrica;

import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.BorderLayout;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import librerias.estructurasDeDatos.modelos.Map;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.deDispersion.TablaHash;
import librerias.estructurasDeDatos.grafos.Adyacente;
import librerias.estructurasDeDatos.grafos.Arista;

/** Aplicacion de prueba, en modo grafico, de un GrafoRedMunicipios
 * 
 * @version (Curso 2017/18)
 */

public class GUIKruskal extends JFrame 
    implements ActionListener, MouseMotionListener, MouseListener  {

    // Nombre del fichero con la imagen del mapa
    private static final String MAP_FILE =   "aplicaciones" + File.separator
                                           + "redElectrica" + File.separator
                                           + "spain.jpg";

    private static final String NO_MAP_MSG =   "No se pudo cargar "
                                             + "la imagen del mapa";
    private static final String INTRO_MSG = ""; 

    // Grafos de municipios
    private GrafoRedMunicipios gg;

    // Municipios principales
    private ArrayList<Municipio> municipiosPrincipales;

    private boolean problemaCargado = false;

    // Elementos de la interfaz grafica
    // Paneles
    private Panel panelMunicipios;
    // Campos de texto
    private TextField tfNomFichero;
    private JTextArea taMsgs;
    // Botones
    private Button btCargaProblema, btCalcular;
    // Componente para la gestion del mapa    
    private MapComponent map;
    // Fuentes para el texto    
    private Font fuenteTitulos, fuenteNormal;
    // Coordenadas del raton sobre el mapa
    private int mouseX, mouseY;

    /** Inicializa el gestor de municipios y la interfaz grafica */            
    public GUIKruskal() {
        super("Red El\u00E9ctrica Interurbana");

        // Configuracion de la ventana
        setLayout(null);
        setBackground(Color.lightGray);
        setSize(1010, 618);
        setResizable(false);
        setLocationRelativeTo(null);

        // Fuentes
        fuenteTitulos = new Font("ARIAL", Font.BOLD, 12);
        fuenteNormal = new Font("ARIAL", Font.PLAIN, 11);

        // Paneles, definicion de tamanyos, posiciones y caracteristicas
        panelMunicipios = new Panel(); 
        panelMunicipios.setBounds(4, 4, 192, 582);
        panelMunicipios.setLayout(null);
        panelMunicipios.setBackground(Color.lightGray);
        add(panelMunicipios);
        inicializarPanelMunicipios();   

        // Cargar la imagen del mapa
        try {
            BufferedImage mapImage = ImageIO.read(new File(MAP_FILE));
            map = new MapComponent(mapImage);
            map.setBounds(200, 4, 999, 603);            
            map.addMouseMotionListener(this);
            map.addMouseListener(this);            
            add(map);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, NO_MAP_MSG, "Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }        
    }

    //Inicializa el panel donde se muestra la informacion sobre el camino minimo
    private void inicializarPanelMunicipios() {
        // Etiquetas y campos de texto
        crearEtiqueta("Red El\u00E9ctrica a optimizar", 4, 20, 160, 16, 
                      panelMunicipios, true);
        crearEtiqueta("Problema:", 4, 60, 60, 40, panelMunicipios, true);
        tfNomFichero = crearCampoTexto(60, 60, 80, 40, panelMunicipios);
        crearEtiqueta("Resultado:", 4, 260, 120, 16, panelMunicipios, true);

        btCargaProblema = crearBoton("Cargar Problema", 60, 150, 120, 40, 
                                     panelMunicipios);
        // Botones
        btCalcular = crearBoton("Calcular \u00D3ptima", 60, 200, 120, 40, 
                                panelMunicipios);
        btCalcular.disable();
        // Panel de resultados
        taMsgs = new JTextArea(INTRO_MSG);
        taMsgs.setFont(fuenteNormal);
        taMsgs.setEditable(false);
        // taMsgs.setLineWrap(true);
        // taMsgs.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(taMsgs);
        scrollPane.setBounds(4, 300, 185, 50); //(4, 155, 185, 300);
        panelMunicipios.add(scrollPane);
    }

    // Crea una etiqueta de texto
    // text    Texto a mostrar en la etiqueta
    // @param   x       Coordenada horizontal de la etiqueta
    // @param   y       Coordenada vertical de la etiqueta
    // @param   width   Anchura en pixels de la etiqueta
    // @param   height  Altura en pixels de la etiqueta
    // @param   p       Panel que contendra la etiqueta
    // @param   title   True si es una etiqueta de titulo (fuente mas grande)
    private JLabel crearEtiqueta(String text, int x, int y, int width,  
    int height, Panel p, boolean title) {
        JLabel label = new JLabel(text);
        if (title) { label.setFont(fuenteTitulos); }
        else { label.setFont(fuenteNormal); }
        label.setBounds(x, y, width, height);
        p.add(label);
        return label;
    }

    //Crea un campo de texto
    // @param   x       Coordenada horizontal del campo de texto
    // @param   y       Coordenada vertical del campo de texto
    // @param   width   Anchura en pixels del campo de texto
    // @param   height  Altura en pixels del campo de texto
    // @param   p       Panel que contendra el campo de texto        
    private TextField crearCampoTexto(int x, int y, int width, 
    int height, Panel p) { 
        TextField text = new TextField();
        text.setBounds(x, y, width, height);
        p.add(text);
        return text;
    }

    // Crea un boton
    // @param   texto   Texto a mostrar en el boton
    // @param   x       Coordenada horizontal del boton
    // @param   y       Coordenada vertical del boton
    // @param   width   Anchura en pixels del boton
    // @param   height  Altura en pixels del boton
    // @param   p       Panel que contendra el boton   
    private Button crearBoton(String texto, int x, int y, int width, 
    int height, Panel p) { 
        Button b = new Button(texto);        
        b.setBounds(x, y, width, height);
        b.setBackground(Color.LIGHT_GRAY);  
        b.addActionListener(this);
        p.add(b);
        return b;
    }

    // Muestra un mensaje en el area de resultados de las acciones realizadas
    // @param   msg     Mensaje a mostrar       
    private void mensaje(String msg) {
        taMsgs.append("\n" + msg);
        taMsgs.setCaretPosition(taMsgs.getDocument().getLength());
    }

    /** Evento que ocurre al pulsar un boton: se invoca al metodo asociado
     *  al boton pulsado
     * @param   a   Informacion relativa al evento
     */            
    public void actionPerformed(ActionEvent a) {

        if (a.getSource() == btCargaProblema) {

            try {
                gg = new GrafoRedMunicipios(tfNomFichero.getText());
                problemaCargado = true;
                String text = "Problema cargado correctamente.";
                text += "\n N\u00BA de Municipios = " + gg.numVertices();
                text += "\n N\u00FAmero de L\u00EDneas del Tendido = " 
                        + gg.numAristas();
                taMsgs.setText(text);
                btCalcular.enable();
                municipiosPrincipales = new ArrayList<Municipio>();
                for (int i = 0; i < gg.numVertices(); i++) {
                    municipiosPrincipales.add(gg.getMunicipio(i));
                }
                map.dibujarGrafo(gg);

            } catch (Exception eChecked) {
                //Ha ocurrido algun problema con el fichero
                System.err.println(eChecked);
                btCalcular.disable();
                gg = null; map.dibujarGrafo(null);
            }
        }
        if (a.getSource() == btCalcular) {
            if (problemaCargado) {
                taMsgs.setText(arbolExpansionMinima());
            } else {
                taMsgs.setText("Primero debes cargar un problema (P1 o P2)");
            }
        }
    }

    private String arbolExpansionMinima() {
        double coste = gg.crearAdyKruskal();
        Map<Municipio, ListaConPI<Municipio>> mST = gg.adyKruskal;
        String texto = "No se ha podido obtener el \u00C1rbol de "
                       + "Recubrimiento M\u00EDnimo";
        if (mST != null) { 
            map.dibujarArbol(mST);
            texto = String.format("Coste total = %6.2f mill. \u20ac\n", 
                                  coste);           
        } 
        return texto;
    }

    /**Evento que ocurre al hacer click con el raton
     * @param   e   Informacion relativa al evento
     */        
    public void mouseClicked(MouseEvent e) {   }

    /**Evento que ocurre cuando el cursor del raton entra en el marco 
     * de la aplicacion
     * @param   e   Informacion relativa al evento
     */    
    public void mouseEntered(MouseEvent e) {   }

    /** Evento que ocurre cuando el cursor del raton sale del marco 
     * de la aplicacion
     * @param   e   Informacion relativa al evento
     */        
    public void mouseExited(MouseEvent e) {    }

    /**Evento que ocurre al pulsar un boton del raton
     * Boton izquierdo: hacemos zoom sobre el mapa si el raton esta sobre los 
     *                  botones de zoom
     * Boton derecho: mostramos la informacion del municipio sobre el que se 
     *                encuentra el raton
     * @param   e   Informacion relativa al evento
     */        
    public void mousePressed(MouseEvent e) {        
        if (e.getButton() == MouseEvent.BUTTON1) {            
            mouseX = e.getX();
            mouseY = e.getY();
            if (mouseX > 8 && mouseX < 28 && mouseY > 553 && mouseY < 573) { 
                map.zoomOut(); 
            } 
            else if (mouseX > 32 && mouseX < 52 
                     && mouseY > 553 && mouseY < 573) {
                map.zoomIn(); 
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            Municipio m = null;
            int x = e.getX(), y = e.getY();
            for (int i = 1; i <= gg.numVertices() && m == null; i++) {
                Municipio aux = gg.getMunicipio(i);
                if (map.municipioSeleccionado(aux, x, y)) { m = aux; }
            }
            if (m != null) {
                JOptionPane.showMessageDialog(this, m.getNombre() 
                    + "\nPob.: " + m.getPoblacion() + " hab.\nExt.: "
                    + m.getExtension() 
                    + " km2", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**Evento que ocurre al soltar un boton del raton
     * @param   e   Informacion relativa al evento
     */        
    public void mouseReleased(MouseEvent e) {   }

    /** Evento que ocurre al mover el raton
     * @param   e   Informacion relativa al evento
     */
    public void mouseMoved(MouseEvent e) {     }

    /** Evento que ocurre al mover el raton manteniendo un boton 
     * del raton pulsado
     * Nos desplazamos por el mapa
     * @param   e   Informacion relativa al evento
     */
    public void mouseDragged(MouseEvent e) {
        int incX = mouseX - e.getX();
        int incY = mouseY - e.getY();
        mouseX = e.getX();
        mouseY = e.getY();
        map.moveZoom(incX, incY);        
    }

    /**Metodo principal: crea la interfaz grafica de la aplicacion
     * @param  args   Argumentos de la linea de comandos (no se utiliza)
     */        
    public static void main(String[] args) {
        GUIKruskal guiMunicipios = new GUIKruskal();
        guiMunicipios.setVisible(true);        
    }

    /** Componente para la gestion del mapa */
    class MapComponent extends JComponent {

        // Arbol a mostrar, otra representacion del grafo con tabla hash
        Map<Municipio, ListaConPI<Municipio>> municDict;
        // Imagen que contiene el mapa
        private BufferedImage map;
        // Muestra todas las aristas del grafo si esta a true
        private boolean drawAll;
        // Nivel de zoom
        private double zoom;
        // Coordenadas origen actuales del mapa
        private int zoomX, zoomY;

        /**Constructor: inicializa el mapa
         * @param  map   Imagen que contiene el mapa
         */        
        MapComponent(BufferedImage m) {            
            this.map = m;
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            drawAll = true;
            zoomX = 0; zoomY = 0;
            zoom = 1.0;

        }

        /**Devuelve las dimensiones del mapa
         * @return      Dimensiones preferidas del componente
         */
        public Dimension getPreferredSize() {
            Insets insets = getInsets();
            int w = insets.left + insets.right + map.getWidth();
            int h = insets.top + insets.bottom + map.getHeight();
            return new Dimension(w, h);
        }

        /** Incrementa el nivel de zoom del mapa */        
        public void zoomOut() {
            if (zoom > 1.0) {
                zoom = zoom / 2.0;
                if (zoom < 1.0) { zoom = 1.0; }
                int w = (int) (map.getWidth() / zoom);
                int h = (int) (map.getHeight() / zoom);
                if (zoomX + w > map.getWidth()) {
                    zoomX = map.getWidth() - w - 1;
                } else if (zoomX < 0) { zoomX = 0; }
                if (zoomY + h > map.getHeight()) {
                    zoomY = map.getHeight() - h - 1;
                } else if (zoomY < 0) { zoomY = 0; }
                repaint();
            }
        }        

        /** Reduce el nivel de zoom del mapa */
        public void zoomIn() {
            if (zoom < 5.0) {
                zoom = zoom + 1.0;
                if (zoom > 5.0) { zoom = 5.0; }
                int w = (int) (map.getWidth() / zoom);
                int h = (int) (map.getHeight() / zoom);
                if (zoomX + w > map.getWidth()) {
                    zoomX = map.getWidth() - w - 1;
                } else if (zoomX < 0) { zoomX = 0; }
                if (zoomY + h > map.getHeight()) {
                    zoomY = map.getHeight() - h - 1;
                } else if (zoomY < 0) { zoomY = 0; }
                repaint();
            }
        }

        /** Desplaza el mapa */
        public void moveZoom(int incX, int incY) {
            if (zoom != 1.0) {
                zoomX += incX;
                zoomY += incY;
                int w = (int) (map.getWidth() / zoom);
                int h = (int) (map.getHeight() / zoom);
                if (zoomX + w > map.getWidth()) {
                    zoomX = map.getWidth() - w - 1;
                } else if (zoomX < 0) { zoomX = 0; }
                if (zoomY + h > map.getHeight()) {
                    zoomY = map.getHeight() - h - 1;
                } else if (zoomY < 0) { zoomY = 0; }
                repaint();
            }
        }

        public void dibujarGrafo(GrafoRedMunicipios gg) {  
            if (gg == null) { 
                zoomX = 0; zoomY = 0;
                zoom = 1.0;
                repaint(); 
            }
            else {
                int numV = gg.numVertices();
                Map<Municipio, ListaConPI<Municipio>> munic = 
                    new TablaHash<Municipio, ListaConPI<Municipio>>(numV);
                for (int i = 0; i < numV; i++) {
                    Municipio m1 = gg.getMunicipio(i);
                    ListaConPI<Adyacente> ady = gg.adyacentesDe(i);
                    ListaConPI<Municipio> mAdy = new LEGListaConPI<Municipio>();
                    for (ady.inicio(); !ady.esFin(); ady.siguiente()) {
                        int j = ady.recuperar().getDestino();  
                        Municipio m2 = gg.getMunicipio(j);
                        mAdy.insertar(m2);
                    }
                    munic.insertar(m1, mAdy);
                }

                drawAll = true;

                int minX, minY, maxX, maxY, w, h;
                ListaConPI<Municipio> c = munic.claves();
                c.inicio();
                Municipio m0 = c.recuperar();
                minX = m0.getPosX(); maxX = minX;
                minY = m0.getPosY(); maxY = minY;
                for (; !c.esFin(); c.siguiente()) {
                    Municipio mi = c.recuperar();
                    int px = mi.getPosX(), py = mi.getPosY();
                    if (px < minX) { minX = px; }
                    else if (px > maxX) { maxX = px; }
                    if (py < minY) { minY = py; }
                    else if (py > maxY) { maxY = py; }
                }
                double ar = map.getWidth() / (double) map.getHeight();    
                if (maxX - minX < ar * (maxY - minY)) {
                    h = maxY - minY + 20;
                    w = (int) (h * ar);
                } else {
                    w = maxX - minX + 30;
                    h = (int) (w / ar);
                }
                zoom = map.getWidth() / w;
                if (zoom <= 1.2) {
                    zoom = 1.0;
                    zoomX = 0; zoomY = 0;
                } 
                else {
                    if (zoom > 5.0) {
                        w = (int) ((w / 5.0) * zoom);
                        h = (int) ((h / 5.0) * zoom);
                        zoom = 5.0;
                    }
                    zoomY = (int) ((maxY + minY) / 2.0 - h / 2.0);
                    zoomX = (int) ((maxX + minX) / 2.0 - w / 2.0);
                    if (zoomX < 0) { zoomX = 0; }               
                    if (zoomY < 0) { zoomY = 0; }
                }

                repaint();
            }
        }

        public void dibujarArbol(Map<Municipio, 
                                 ListaConPI<Municipio>> municDict) {
            this.municDict = municDict;
            if (municDict.talla() == 0) {                
                //zoomX = 0; zoomY = 0;
                //zoom = 1.0;
                drawAll = true;
            } else {               
                drawAll = false;
            }

            int minX, minY, maxX, maxY, w, h;
            ListaConPI<Municipio> c = municDict.claves();
            c.inicio();
            Municipio m0 = c.recuperar();
            minX = m0.getPosX(); maxX = minX;
            minY = m0.getPosY(); maxY = minY;
            for (; !c.esFin(); c.siguiente()) {
                Municipio mi = c.recuperar();
                int px = mi.getPosX(), py = mi.getPosY();
                if (px < minX) { minX = px; }
                else if (px > maxX) { maxX = px; }
                if (py < minY) { minY = py; }
                else if (py > maxY) { maxY = py; }
            }
            double ar = map.getWidth() / (double) map.getHeight();
            if (maxX - minX < ar * (maxY - minY)) {
                h = maxY - minY + 20;
                w = (int) (h * ar);
            } else {
                w = maxX - minX + 30;
                h = (int) (w / ar);
            }
            zoom = map.getWidth() / w;
            if (zoom <= 1.2) {
                zoom = 1.0;
                zoomX = 0; zoomY = 0;
            } else {
                if (zoom > 5.0) {
                    w = (int) ((w / 5.0) * zoom);
                    h = (int) ((h / 5.0) * zoom);
                    zoom = 5.0;
                }
                zoomY = (int) ((maxY + minY) / 2.0 - h / 2.0);
                zoomX = (int) ((maxX + minX) / 2.0 - w / 2.0);
                if (zoomX < 0) { zoomX = 0; }               
                if (zoomY < 0) { zoomY = 0; }
            }
            repaint();
        }

        // Transforma la coordenada horizontal del mapa en coordenada  
        // horizontal de la pantalla
        // @param  x   Coordenada horizontal del mapa
        // @return     Coordenada horizontal de la pantalla       
        private int posX(int x) { return (int) ((x - zoomX) * zoom); }

        // Transforma la coordenada vertical del mapa en coordenada  
        // vertical de la pantalla
        // @param  x   Coordenada vertical del mapa
        // @return     Coordenada vertical de la pantalla
        private int posY(int y) { return (int) ((y - zoomY) * zoom); }

        // Dibuja los botones de zoom sobre el mapa
        // @param  g   Superficie de dibujo
        private void paintZoomButtons(Graphics g) {
            g.setColor(Color.blue);
            g.fillRoundRect(8, 553, 20, 20, 6, 6);
            g.fillRoundRect(32, 553, 20, 20, 6, 6);
            g.setColor(Color.white);
            g.drawString("-", 17, 567);
            g.drawString("+", 38, 567);
        }

        /** Comprueba si un municipio esta en las coordenadas especificadas
         * @param  m        Municipio a comprobar
         * @param  mouseX   Coordenada horizontal
         * @param  mouseY   Coordenada vertical
         * @return True si el municipio esta en dichas coordenadas
         */
        public boolean municipioSeleccionado(Municipio m, int mX, int mY) {
            boolean sel = false;
            int posX = posX(m.getPosX());
            if (Math.abs(posX - mX) < 6) { 
                int posY = posY(m.getPosY());
                sel = Math.abs(posY - mY) < 6;
            }            
            return sel;
        }

        // Dibuja un municipio sobre el mapa
        // @param   g   Superficie de dibujo
        // @param   m   Municipio a mostrar
        private void dibujarMunicipio(Graphics g, Municipio m) {
            int posX = posX(m.getPosX());  
            int posY = posY(m.getPosY());
            g.setColor(Color.BLACK);
            g.drawOval(posX - 7, posY - 7, 13, 13); 
            g.setColor(Color.RED);
            g.fillOval(posX - 4, posY - 4, 8, 8);
            g.setColor(Color.BLACK);
            g.drawString(m.getNombre(), posX + 10, posY + 10);
        }

        protected void drawEdge(Graphics g, Municipio m1, Municipio m2) {
            g.drawLine(posX(m1.getPosX()), 
                       posY(m1.getPosY()), 
                       posX(m2.getPosX()), 
                       posY(m2.getPosY()));         
            dibujarMunicipio(g, m1);
            dibujarMunicipio(g, m2);
        }

        /**Procedimiento de dibujado del mapa
         * @param   g   Superficie de dibujo
         */ 
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int w = (int) (map.getWidth() / zoom);
            int h = (int) (map.getHeight() / zoom);            
            g.drawImage(map, 0, 0, map.getWidth() - 1, map.getHeight() - 1, 
                zoomX, zoomY, zoomX + w, zoomY + h, null);
            float red;

            if (gg != null) {
                if (drawAll) {
                    for (int i = 0; i < gg.numVertices(); i++) {
                        Municipio m1 = gg.getMunicipio(i);
                        ListaConPI<Adyacente> ady = gg.adyacentesDe(i);
                        for (ady.inicio(); !ady.esFin(); ady.siguiente()) {
                            int j = ady.recuperar().getDestino();
                            Municipio m2 = gg.getMunicipio(j);
                            int totalP = m1.getPoblacion() + m2.getPoblacion();
                            red = (totalP) / 10000.0f;
                            if (red > 1.0f) { red = 1.0f; }
                            red /= 4.0f;
                            g.setColor(new Color(0.6f - red, 0.5f - red, 0.6f));
                            g.drawLine(posX(m1.getPosX()), 
                                       posY(m1.getPosY()), 
                                       posX(m2.getPosX()), 
                                       posY(m2.getPosY()));
                        }
                    }
                    for (int i = 0; i < municipiosPrincipales.size(); i++) {
                        //System.out.println(municipiosPrincipales.get(i));
                        dibujarMunicipio(g, municipiosPrincipales.get(i));
                    }
                }
                else {
                    for (int i = 0; i < gg.numVertices(); i++) {
                        Municipio m1 = gg.getMunicipio(i);
                        ListaConPI<Adyacente> ady = gg.adyacentesDe(i);
                        for (ady.inicio(); !ady.esFin(); ady.siguiente()) {
                            int j = ady.recuperar().getDestino();
                            Municipio m2 = gg.getMunicipio(j);
                            int totalP = m1.getPoblacion() + m2.getPoblacion();
                            red = (totalP) / 10000.0f;
                            if (red > 1.0f) { red = 1.0f; }
                            red /= 4.0f;
                            g.setColor(new Color(0.6f - red, 0.5f - red, 0.6f));
                            g.drawLine(posX(m1.getPosX()), 
                                       posY(m1.getPosY()), 
                                       posX(m2.getPosX()), 
                                       posY(m2.getPosY()));
                        }
                    }
                    for (int i = 0; i < municipiosPrincipales.size(); i++) {
                        //System.out.println(municipiosPrincipales.get(i));
                        dibujarMunicipio(g, municipiosPrincipales.get(i));
                    }

                    ListaConPI<Municipio> lM = this.municDict.claves();
                    for (lM.inicio(); !lM.esFin(); lM.siguiente()) {
                        Municipio ori = lM.recuperar();
                        dibujarMunicipio(g, ori);
                        ListaConPI<Municipio> lMAdy = 
                            this.municDict.recuperar(lM.recuperar());
                        lMAdy.inicio();
                        for (; !lMAdy.esFin(); lMAdy.siguiente()) {
                            Municipio ady = lMAdy.recuperar();
                            dibujarMunicipio(g, ady);
                            ListaConPI<Adyacente> lAdy = 
                                gg.adyacentesDe(gg.getVertice(ori));
                            double coste = 0.0;
                            lAdy.inicio();
                            for (; !lAdy.esFin(); lAdy.siguiente()) {
                                if (lAdy.recuperar().getDestino() 
                                    == gg.getVertice(ady)) {
                                    coste = lAdy.recuperar().getPeso();
                                }
                            }                            
                            g2d.setStroke(new BasicStroke(3,
                                                     BasicStroke.CAP_ROUND,
                                                     BasicStroke.JOIN_ROUND));
                            String costeSt = String.format("%6.2f", coste);
                            g.drawString(costeSt,
                                    posX((ori.getPosX() + ady.getPosX()) / 2),
                                    posY((ori.getPosY() + ady.getPosY()) / 2));
                            g.drawLine(posX(ori.getPosX()), 
                                       posY(ori.getPosY()), 
                                       posX(ady.getPosX()), 
                                       posY(ady.getPosY()));
                        }
                    }

                }
            }
            paintZoomButtons(g);
        }

    }
}
