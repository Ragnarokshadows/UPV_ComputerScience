package aplicaciones.biblioteca;

/**
*Text genereted by Simple GUI Extension for BlueJ
*/
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;
import java.io.FileNotFoundException;
import librerias.estructurasDeDatos.modelos.ListaConPI;

public class GUIBiblioteca extends JFrame {

    private JMenuBar menuBar;
    private JButton cargar;
    private JButton buscar;
    private JTextArea areaRes; // listado índices
    private JScrollPane scroll;
    private JTextField textoP; // palabra a buscar
    private JTextField textoL; // listado de libros
    private JTextField textfield1;
        
    // Atributos para el Indexador
    private Indexador biblioteca;    
    private String palabra;
    private boolean librosCargados;
    private int numCub, numCubTemp;

    //Constructor 
    public GUIBiblioteca() {

        this.setTitle("Indices Biblioteca");
        this.setSize(500, 600);

        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 400));
        contentPane.setBackground(new Color(192, 192, 192));

        areaRes = new JTextArea();        
        areaRes.setEditable(false);
        areaRes.setWrapStyleWord(true);  
        scroll = new JScrollPane(areaRes, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        scroll.setBounds(5, 12, 300, 350); 
        
        cargar = new JButton();
        cargar.setBounds(320, 20, 120, 30);
        cargar.setBackground(new Color(204, 204, 204));
        cargar.setForeground(new Color(153, 0, 0));
        cargar.setEnabled(true);
        cargar.setFont(new Font("sansserif", 0, 12));
        cargar.setText("Cargar libros");
        cargar.setVisible(true);
        
        cargar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
		        cargarLibros(evt);
	        }
	    });

        textoL = new JTextField();
        textoL.setBounds(320, 50, 170, 35);
        textoL.setBackground(new Color(255, 255, 255));
        textoL.setForeground(new Color(0, 0, 0));
        textoL.setEnabled(true);
        textoL.setFont(new Font("sansserif", 0, 12));
        textoL.setText("Fichero con los libros...");
        textoL.setBorder(BorderFactory.createBevelBorder(1));
        textoL.setVisible(true);
        
        buscar = new JButton();
        buscar.setBounds(320, 94, 120, 35);
        buscar.setBackground(new Color(204, 204, 204));
        buscar.setForeground(new Color(153, 0, 0));
        buscar.setEnabled(false); 
        buscar.setFont(new Font("sansserif", 0, 12));
        buscar.setText("Buscar");
        buscar.setVisible(true);
        
        buscar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
		        buscar(evt);
	        }
	    });
        
        textoP = new JTextField();
        textoP.setBounds(320, 130, 170, 35);
        textoP.setBackground(new Color(204, 204, 204));
        textoP.setForeground(new Color(0, 0, 0));
        textoP.setEnabled(true);
        textoP.setFont(new Font("sansserif", 0, 12));
        textoP.setText("Palabra a buscar");
        textoP.setBorder(BorderFactory.createBevelBorder(1));
        textoP.setVisible(true);

        //adding components to contentPane panel        
        //contentPane.add(areaRes);
        contentPane.add(scroll);
        contentPane.add(cargar);
        contentPane.add(textoL);
        contentPane.add(textoP);
        contentPane.add(buscar);

        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    //Method actionPerformed for cargar
    private void cargarLibros(ActionEvent evt) {
        areaRes.setText("");
        librosCargados = false;
	    String nomFile = textoL.getText();
	    System.out.println("Leyendo los libros de ... " + nomFile);
	    try {
	        biblioteca = new Indexador(nomFile, Indexador.TALLA_VOCABULARIO);
	        librosCargados = true;
	        buscar.setEnabled(true);
	    } catch (FileNotFoundException er) {
	        System.out.println("Error en la lectura de los libros");
	    }
    }
    
    //Method actionPerformed for buscar
    private void buscar(ActionEvent evt) {
        areaRes.setText("");
	    String nomPal = textoP.getText();
	    ListaConPI<String> res = biblioteca.indiceDe(nomPal);
	    System.out.println("Buscando ... " + nomPal + " (" + res.talla() + " apariciones)");
	    for (res.inicio(); !res.esFin(); res.siguiente()) {
	        areaRes.append(res.recuperar().toString());
	    }
    }

    public static void main(String[] args) {        
        System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIBiblioteca();
            }
        });
    }

}