import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class Light extends JPanel {
    private boolean red;
    private boolean yellow;
    private boolean green;

    public Light() {
        red=false;
        yellow=false;
        green=false;
    }

    //Enciende Luz Roja
    public void red() {
        red=true;
        yellow=false;
        green=false;
        repaint();
    }

    //Enciende Luz Amarilla
    public void yellow() {
        red=false;
        yellow=true;
        green=false;
        repaint();
    }

    //Enciende Luz verde
    public void green() {
        red=false;
        yellow=false;
        green=true;
        repaint();
    }

    //Muestra Luces
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Seleccionamos Rojo
        if(red) {
            g.setColor(Color.red);
            g.fillOval(10,10,20,20);
            g.setColor(Color.black);
            g.drawOval(10,35,20,20);
            g.drawOval(10,60,20,20);
            g.drawRect(5,5,30,80);
        }
        //Seleccionamos Amarillo
        else if(yellow) {
            g.setColor(Color.yellow);
            g.fillOval(10,35,20,20);
            g.setColor(Color.black);
            g.drawRect(5,5,30,80);
            g.drawOval(10,10,20,20);
            g.drawOval(10,60,20,20);
        }
        //Seleccionamos Verde
        else if(green) {
            g.setColor(Color.green);
            g.fillOval(10,60,20,20);
            g.setColor(Color.black);
            g.drawRect(5,5,30,80);
            g.drawOval(10,10,20,20);
            g.drawOval(10,35,20,20);
        }
        //El semaforo apagado
        else {
            g.setColor(Color.black);
            g.drawRect(5,5,30,80);
            g.drawOval(10,10,20,20);
            g.drawOval(10,35,20,20);
            g.drawOval(10,60,20,20);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(40,90);
    }
}