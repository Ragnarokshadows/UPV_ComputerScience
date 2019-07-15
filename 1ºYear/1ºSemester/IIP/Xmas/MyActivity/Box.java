package MyActivity;

public class Box{
    private int value;
    private boolean show;
    
    public Box(int v, boolean s){
        value = v;
        show = s;
    }
    
    public int getValue(){return value;} 
    public boolean getShow(){return show;}
    public void setValue(int v){value = v;}
    public void setShow(boolean s){show = s;}
    
    public String toString(){
        String s = "";
        
        if (show == true){
            s = s + value;
        }
        else{s = s + " ";}
        
        return s;
    }
}