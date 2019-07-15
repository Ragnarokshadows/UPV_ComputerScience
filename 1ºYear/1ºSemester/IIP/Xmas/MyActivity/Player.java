package MyActivity;

public class Player{
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    
    private int score;
    private String name;
    private int difficulty;
    
    public Player(String nname, int difficulty, int nscore){
        score = nscore;
        
        nname = nname.substring(0,3);
        nname = nname.toUpperCase();
        name = nname;
        
        switch (difficulty){
            case EASY: difficulty = EASY; break;
            case MEDIUM: difficulty = MEDIUM; break;
            case HARD: difficulty = HARD; break;
        }
    }
    
    public int getScore(){
        return score;
    }
    public String getName(){return name;}
    
    public String toString(){
        String s = "NAME: " + name + "------------->SCORE: " +
        score;
        
        return s;   
    }
}