// CSD feb 2013 Juansa Sendra



import javax.swing.*;
import java.awt.*;

public class StateRenderer extends JLabel implements ListCellRenderer<Object> {
    int type=0;
    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
    public StateRenderer() {setOpaque(true);} 

    public void inic(int type) {this.type=type;}
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        State est=(State)value;
        setText(est.toString()); setFont(new Font("Courier",Font.BOLD,16));
        setBackground(isSelected?HIGHLIGHT_COLOR:Color.white);
        int e=est.test();
        setForeground(e>0 && e<=type?Color.red: isSelected?Color.white: Color.black); 
        return this;
    }
}
