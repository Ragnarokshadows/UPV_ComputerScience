package aplicaciones.editorPredictivo;

 
 
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Paint;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.CaretListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener; 
import java.awt.geom.RoundRectangle2D;
import librerias.estructurasDeDatos.modelos.ListaConPI;

/** Clase TestEditorPredictivo: programa grafico que hace <br>
 * uso del EditorPredictivo para la redaccion de mensajes <br>
 * de texto. <br>
 *  
 * @version Octubre 2017
 */
public class GUIEditorPredictivo extends JFrame implements 
        java.awt.event.ActionListener, javax.swing.event.CaretListener, 
        java.awt.event.MouseListener, java.awt.event.MouseMotionListener {
            
    private static final String DICCIONARIO_POR_DEFECTO = "aplicaciones" 
        + java.io.File.separator + "editorPredictivo" 
        + java.io.File.separator + "castellano.txt";
    private static final String ANYADIR_PALABRA = "\u00BFA\u00F1adir palabra?";
    private static final int MAX_SUCESORES = 20;
    
    // Atributos para el Editor Predictivo
    private String palabraActual;
    private EditorPredictivo editor;
    // Componentes graficos
    private Font fSmall, fBig, fMed;
    private JButton btnClose, btnLoad, btnSave, btnDel;
    private JLabel lblTitle;
    private JList<String> listSucesores;
    private JTextArea textArea;
    private JScrollPane scrPane;
    private LimitedStyledDocument styleDoc = new LimitedStyledDocument();
    private int mousePosX, mousePosY;
    private boolean moviendo;
        
    /**
     * Constructor de la ventana grafica
     */
    private GUIEditorPredictivo() {
        super("EDITOR PREDICTIVO");
        editor = new EditorPredictivo(DICCIONARIO_POR_DEFECTO);
        palabraActual = "";
        setUndecorated(true);
        setContentPane(new JPanelRound());
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        inicializarInterfaz();
        setVisible(true);
        textArea.requestFocus();
        moviendo = false;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Inicializacion de los componentes de la interfaz grafica
     */
    @SuppressWarnings("unchecked")
    private void inicializarInterfaz() {
        // Fuentes y boton de cerrar
        fSmall = new Font("arial", Font.PLAIN, 8);
        fMed = new Font("arial", Font.PLAIN, 10);
        fBig = new Font("arial", Font.BOLD, 24);
        btnClose = new JButtonRound("X", 13);
        btnClose.setBounds(375, 5, 20, 20);
        btnClose.setFont(fSmall);
        getContentPane().add(btnClose);
        btnClose.addActionListener(this);
        // Etiqueta de titulo
        lblTitle = new JLabel("EDA MOVIL");
        lblTitle.setFont(new Font("arial", Font.BOLD + Font.ITALIC, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(140, 20, 150, 19);
        getContentPane().add(lblTitle);
        // Area de texto para el mensaje
        textArea = new JTextArea();
        textArea.setDocument(styleDoc);
        textArea.setFont(fBig);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(new Color(113, 3, 3));
        textArea.addCaretListener(this);
        scrPane = new JScrollPane(textArea);
        scrPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPane.setBounds(10, 40, 380, 260);        
        getContentPane().add(scrPane);
        // Lista con los sucesores obtenidos para la palabra actual
        JLabel lblSug = new JLabel("SUGERENCIAS:");
        lblSug.setFont(fMed);
        lblSug.setForeground(Color.WHITE);
        lblSug.setBounds(10, 308, 100, 10);
        getContentPane().add(lblSug);
        listSucesores = new JList<String>() {
            public int getScrollableUnitIncrement(Rectangle visibleRect, 
                int orientation, int direction) {
                int row = getFirstVisibleIndex();
                if (orientation == SwingConstants.VERTICAL 
                    && direction < 0 && row != -1) {
                    Rectangle r = getCellBounds(row, row);
                    if ((r.y == visibleRect.y) && (row != 0))  {
                        Point loc = r.getLocation();
                        loc.y--;
                        int prevIndex = locationToIndex(loc);
                        Rectangle prevR = getCellBounds(prevIndex, prevIndex);
                        if (prevR == null || prevR.y >= r.y) {
                            return 0;
                        }
                        return prevR.height;
                    }
                }
                return super.getScrollableUnitIncrement(visibleRect, 
                    orientation, direction);
            }
        };
        listSucesores.setBackground(new Color(0x262d3d));
        listSucesores.setForeground(Color.WHITE);
        listSucesores.setFont(fMed);
        listSucesores.setModel(new DefaultListModel());
        listSucesores.setSelectionMode(
            ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listSucesores.setPrototypeCellValue(ANYADIR_PALABRA + "_");
        listSucesores.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listSucesores.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(listSucesores);
        listScroller.setBounds(10, 320, 380, 80);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        getContentPane().add(listScroller);        
        listSucesores.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { seleccionarSugerencia(); }
            }
        });
        // Botones del teclado
        crearTeclas("qwertyuiop", 10, 415);
        crearTeclas("asdfghjkl\u00f1", 10, 445);
        crearTeclas("zxcvbnm,.", 26, 475);
        crearTeclas("\u00e1\u00e9\u00ed\u00f3\u00fa", 26, 505);
        JButton b = new JButton("spc");
        b.setFont(fMed);
        b.setBounds(216, 505, 74, 28);
        b.setFocusPainted(false);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.addActionListener(this);
        getContentPane().add(b);
        btnDel = new JButton("del");
        btnDel.setFont(fMed);
        btnDel.setBounds(292, 505, 74, 28);
        btnDel.setFocusPainted(false);
        btnDel.setMargin(new Insets(0, 0, 0, 0));
        btnDel.addActionListener(this);
        getContentPane().add(btnDel);
        // Botones de abrir/guardar el diccionario
        btnLoad = new JButton("abrir diccionario");
        btnLoad.setFont(fMed);
        btnLoad.setBounds(10, 550, 188, 20);
        btnLoad.setFocusPainted(false);
        btnLoad.setMargin(new Insets(0, 0, 0, 0));
        btnLoad.addActionListener(this);
        getContentPane().add(btnLoad);
        btnSave = new JButton("guardar diccionario");
        btnSave.setFont(fMed);
        btnSave.setBounds(200, 550, 188, 20);
        btnSave.setFocusPainted(false);
        btnSave.setMargin(new Insets(0, 0, 0, 0));
        btnSave.addActionListener(this);
        getContentPane().add(btnSave);
    }
    
    /**
     * Creacion de los botones del teclado
     * @param teclas    Secuencia de letras
     * @param x         Coordenada horizontal
     * @param y         Coordenada vertical
     */
    private void crearTeclas(String teclas, int x, int y) {
        for (int i = 0; i < teclas.length(); i++) {
            JButton b = new JButton(teclas.substring(i, i + 1));
            b.setFont(fMed);
            b.setBounds(x + i * 38, y, 36, 28);
            b.setFocusPainted(false);
            b.setMargin(new Insets(0, 0, 0, 0));
            b.addActionListener(this);
            getContentPane().add(b);
        }
    }
    
    /**
     * Evento que ocurre cuando el usuario selecciona un sucesor:
     * completa la palabra actual con el sucesor seleccionado
     */    
    public void seleccionarSugerencia() {
        if (listSucesores.getSelectedValue() == null) { return; }
        String sel = (String) (listSucesores.getSelectedValue());
        if (sel.equals(ANYADIR_PALABRA)) {
            if (JOptionPane.showConfirmDialog(this, 
                 "\u00BFQuieres a\u00F1adir la palabra '" + palabraActual 
                 + "' al diccionario?", "Confirmar", 
                 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                editor.incluir(palabraActual);
                try {
                    styleDoc.insertString(styleDoc.getLength(), " ", null);
                } catch (javax.swing.text.BadLocationException e) { ; }
            }
        } else {
            sel = sel.substring(palabraActual.length());
            try {
                styleDoc.insertString(styleDoc.getLength(), sel + " ", null);
            } catch (javax.swing.text.BadLocationException e) { ; }
        }
        textArea.requestFocus();
    }
    
    /**
     * Gestion de la pulsacion de los botones de la interfaz
     * @param   e   Evento a manejar
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClose) {
            setVisible(false);
            return;
        }
        if (e.getSource() == btnLoad) {
            JFileChooser fileChooser = new JFileChooser("aplicaciones" 
                + java.io.File.separator + "editorPredictivo");
            if (fileChooser.showOpenDialog(this) 
                == JFileChooser.APPROVE_OPTION) {
                java.io.File fichero = fileChooser.getSelectedFile();
                String filename = fichero.getName();
                String ext = filename.substring(filename.lastIndexOf('.') + 1, 
                    filename.length());
                if (!ext.toLowerCase().equals("txt")) {
                    JOptionPane.showMessageDialog(this, 
                        "Debes seleccionar archivos de texto: *.txt",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    editor = new EditorPredictivo(fichero.toString());
                    JOptionPane.showMessageDialog(this, 
                        "Nuevo diccionario cargado", "Informaci\u00F3n", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else if (e.getSource() == btnSave) {
            JFileChooser fileChooser = new JFileChooser("aplicaciones" 
                + java.io.File.separator + "editorPredictivo");
            if (fileChooser.showSaveDialog(this) 
                == JFileChooser.APPROVE_OPTION) {
                java.io.File fichero = fileChooser.getSelectedFile();
                String filename = fichero.getName();
                String ext = filename.substring(filename.lastIndexOf('.') + 1, 
                    filename.length());
                if (!ext.toLowerCase().equals("txt")) { 
                    JOptionPane.showMessageDialog(this, 
                        "Debes seleccionar archivos de texto: *.txt",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } else if (filename.toLowerCase().equals("castellano.txt")) {
                    JOptionPane.showMessageDialog(this, 
                        "No se puede sobreescribir el diccionario "
                        + "est\u00E1ndar: castellano.txt", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    editor.guardar(fichero.toString());
                    JOptionPane.showMessageDialog(this, 
                        "Nuevo diccionario guardado", "Informaci\u00F3n", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else if (e.getSource() == btnDel) {
            if (styleDoc.getLength() > 0) {
                try {
                    styleDoc.remove(styleDoc.getLength() - 1, 1);
                } catch (javax.swing.text.BadLocationException ex) { ; }
            }
        } else if (e.getSource() instanceof JButton) {
            String s = ((JButton) e.getSource()).getText().toLowerCase();
            if (s.equals("spc")) { s = " "; }
            try {
                styleDoc.insertString(styleDoc.getLength(), s, null);
            } catch (javax.swing.text.BadLocationException ex) { ; }
        }
        textArea.requestFocus();
    }
    
    /**
     * Impide el movimiento del cursor de texto 
     * (siempre queda al final del texto)
     * @param e Evento del cursor
     */
    public void caretUpdate(javax.swing.event.CaretEvent e) {
        if (e.getDot() != textArea.getDocument().getLength()) {
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    /**
     * Al pulsar el raton 
     * @param e Evento del raton
     */
    public void mouseClicked(MouseEvent e) { }
    
    /**
     * Al entrar en la ventana el puntero del raton 
     * @param e Evento del raton
     */    
    public void mouseEntered(MouseEvent e) { }
    
    /**
     * Al salir de la ventana el puntero del raton 
     * @param e Evento del raton
     */
    public void mouseExited(MouseEvent e) { }
    
    /**
     * Inicio del desplazamiento de la ventana 
     * @param e Evento del raton
     */
    public void mousePressed(MouseEvent e) {
        if (!moviendo && e.getPoint().y < 16) {
            mousePosX = e.getLocationOnScreen().x;
            mousePosY = e.getLocationOnScreen().y;
            moviendo = true;
        }
    }
    
    /**
     * Fin del desplazamiento de la ventana 
     * @param e Evento del raton
     */
    public void mouseReleased(MouseEvent e) {
        moviendo = false;
    }
    
    /**
     * Desplazamiento de la ventana 
     * @param e Evento del raton
     */
    public void mouseDragged(MouseEvent e) {
        if (moviendo) {
            int incX = e.getLocationOnScreen().x - mousePosX;
            int incY = e.getLocationOnScreen().y - mousePosY;
            mousePosX = e.getLocationOnScreen().x;
            mousePosY = e.getLocationOnScreen().y;
            Point pos = getLocation();
            pos.x += incX;
            pos.y += incY;
            setLocation(pos);
        }
    }

    /**
     * Movimiento del puntero del raton 
     * @param e Evento del raton
     */
    public void mouseMoved(MouseEvent e) { }

    /**
     * Programa principal
     * @param args Argumentos de la linea de comandos (no se utiliza)
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        GUIEditorPredictivo app = new GUIEditorPredictivo();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Clase para crear un panel de bordes redondeados
     */
    private class JPanelRound extends JPanel {
        private Color colorPrimario = new Color(0x666f7f);
        private Color colorSecundario = new Color(0x262d3d);
        private Color colorContorno = new Color(0x262d3d);
        private int arcw = 20;
        private int arch = 20;   
        
        /**
         * Constructor de un panel de bordes redondeados
         */
        public JPanelRound() {
            super();
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            Paint oldPaint = g2.getPaint();
            RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(
                    0, 0, getWidth(), getHeight() - 1, getArcw(), getArch());
            g2.clip(r2d);
            g2.setPaint(new GradientPaint(0.0f, 0.0f,
                getColorPrimario().darker(), 0.0f, getHeight(),
                getColorSecundario().darker()));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setStroke(new BasicStroke(4f));
            g2.setPaint(new GradientPaint(0.0f, 0.0f, getColorContorno(),
                    0.0f, getHeight(), getColorContorno()));
            g2.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 18, 18);
            g2.setPaint(oldPaint);
            super.paintComponent(g);
        }
        
        /**
         * Devuelve el color primario del panel
         * @return Color primario del panel
         */
        public Color getColorPrimario() { return colorPrimario; }
        
        /**
         * Modifica el color primario del panel
         * @param colorPrimario Nuevo color primario del panel
         */        
        public void setColorPrimario(Color colorPrim) { 
            colorPrimario = colorPrim; 
        }
        
        /**
         * Devuelve el color secundaro del panel
         * @return Color secundario del panel
         */        
        public Color getColorSecundario() { 
            return colorSecundario; 
        }
        
        /**
         * Modifica el color secundario del panel
         * @param colorSecundario Nuevo color secundario del panel
         */
        public void setColorSecundario(Color colorSec) { 
            colorSecundario = colorSec; 
        }
        
        /**
         * Devuelve el color de contorno del panel
         * @return Color de contorno del panel
         */
        public Color getColorContorno() { 
            return colorContorno; 
        }
        
        /**
         * Modifica el color de contorno del panel
         * @param colorContorno Nuevo color de contorno del panel
         */
        public void setColorContorno(Color colorCont) { 
            colorContorno = colorCont; 
        }
        
        /**
         * Devuelve la anchura del arco
         * @return Anchura del arco
         */
        public int getArcw() { return arcw; }
        
        /**
         * Modifica la anchura del arco
         * @param arcw  Nueva anchura del arco
         */
        public void setArcw(int a) { this.arcw = a; }
        
        /**
         * Devuelve la altura del arco
         * @return Altura del arco
         */
        public int getArch() { return arch; }
        
        /**
         * Modifica la altura del arco
         * @param arch  Nueva altura del arco
         */
        public void setArch(int a) { this.arch = a; }
    }
    
    /**
     * Clase para crear un boton redondo
     */    
    private class JButtonRound extends JButton {
        private int posY;
        
        /**
         * Constructor de un boton redondo
         * @param rotulo    Etiqueta del boton
         * @param posY      Posici??n vertical del boton
         */
        public JButtonRound(String rotulo, int pos) { 
            super(rotulo);
            this.posY = pos;
            setFocusable(false);
            Dimension tamano = getPreferredSize();
            tamano.width = Math.max(tamano.width, tamano.height);
            tamano.height = tamano.width; 
            setPreferredSize(tamano);
            setContentAreaFilled(false);            
        }
        
        /**
         * Dibujo del componente
         * @param g Area de dibujo
         */
        protected void paintComponent(Graphics g) {            
            if (getModel().isArmed()) { 
                g.setColor(Color.lightGray);
            } else {
                g.setColor(getBackground());
            }
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
            Graphics2D g2 = (Graphics2D) g;
            FontMetrics metrics = g2.getFontMetrics(getFont());
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int w = metrics.stringWidth(getText()), h = metrics.getHeight();
            g2.setColor(Color.BLACK);
            g2.drawString(getText(), (getSize().width - w) / 2, posY);
        }
        
        /**
         * Dibujo del borde del componente
         * @param g Area de dibujo
         */
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }
    }
    
    /**
     * Clase para impedir la introduccion de caracteres no deseados 
     * en el cuerpo del mensaje
     */    
    @SuppressWarnings("unchecked")
    private class LimitedStyledDocument 
        extends javax.swing.text.DefaultStyledDocument {
        private boolean activo;
        
        /**
         * Constructor
         */
        public LimitedStyledDocument() {
            super();
            activo = true;
        }
        
        /**
         * Habilita/deshabilita la escritura
         * @param   a   Estado del modo escritura
         */
        public void setActivo(boolean a) { activo = a; }
        
        /**
         * Inserta un texto en el documento
         * @param offs  Posicion para la insercion
         * @param str   Cadena de texto a insertar
         * @param a     Estilo del texto a insertar
         * @throws javax.swing.text.BadLocationException 
         *         si la posicion no es valida
         */
        public void insertString(int offs, String str, 
            javax.swing.text.AttributeSet a) 
            throws javax.swing.text.BadLocationException {
            if (activo) {
                String s = "";
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
//                     if ((c >= 'a' && c <= 'z') || c == '\u00F1' || c == ' ' 
//                         || c == ',' || c == '.' || c == '\u00E1' 
//                         || c == '\u00E9' || c == '\u00ED' || c == '\u00F3'
//                         || c == '\u00FA') {
//                             s += c;
//                         }
                    if (Character.isLetter(c) 
                            || c == ' ' || c == ',' || c == '.') {
                        s += c;
                    }
                }
                if (s.length() > 0) {
                    super.insertString(getLength(), s, a);
                    DefaultListModel dlm = (DefaultListModel) 
                        listSucesores.getModel();
                    dlm.clear();
                    char c = s.charAt(s.length() - 1);
                    if (c == ' ' || c == ',' || c == '.') { 
                        palabraActual = ""; 
                    } else {
                        palabraActual += c;
                        ListaConPI<String> suc = 
                            editor.recuperarSucesores(palabraActual, 
                                MAX_SUCESORES - 1);
                        for (suc.inicio(); !suc.esFin(); suc.siguiente()) {
                            dlm.addElement(suc.recuperar());
                        }
                        dlm.addElement(ANYADIR_PALABRA);
                    }
                }
            }
        }
        
        /**
         * Elimina un texto del documento
         * @param offs  Inicio del texto a eliminar
         * @param len   Longitud del texto a eliminar
         * @throws javax.swing.text.BadLocationException
         *         si la posicion no es valida
         */
        public void remove(int offs, int len) 
            throws javax.swing.text.BadLocationException {
            if (activo && offs == getLength() - 1 && len == 1) {
                DefaultListModel dlm = (DefaultListModel)
                    listSucesores.getModel();
                dlm.clear();
                palabraActual = "";
                int pos = offs - 1;
                while (pos >= 0) {
                    char c = getText(pos, 1).charAt(0);
                    if (c == ' ' || c == ',' || c == '.') { break; }
                    palabraActual = c + palabraActual;
                    pos--;
                }
                if (!palabraActual.equals("")) {
                    ListaConPI<String> suc = 
                        editor.recuperarSucesores(palabraActual, 
                            MAX_SUCESORES - 1);
                    for (suc.inicio(); !suc.esFin(); suc.siguiente()) {
                        dlm.addElement(suc.recuperar());
                    }
                    dlm.addElement(ANYADIR_PALABRA);
                }
                super.remove(offs, len);
            }
        }
    }
}
