package MyActivity;

public class MineSweeper{
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int EASY_MINES = 8;
    public static final int MEDIUM_MINES = 16;
    public static final int HARD_MINES = 32;
    public static final int ARRAY_LENGTH = 8;
    
    private Box [][] table;
    private Ranking [] rankings;
    private int difficulty;
    private int numMines;
    private int seenBoxes;
    
    public MineSweeper(int ndifficulty){
        int i;
        
        rankings = new Ranking[3];
        
        table = new Box[ARRAY_LENGTH][ARRAY_LENGTH];
        
        difficulty = ndifficulty;
        
        for (i = 0;i < 3;i++){
            rankings[i] = new Ranking(); 
        }
        
        switch (ndifficulty){
            case EASY: numMines = EASY_MINES; break;
            case MEDIUM: numMines = MEDIUM_MINES; break;
            case HARD: numMines = HARD_MINES; break;
        }
    }
    
    public Box[][] getTable(){return table;}
    public Ranking getRanking(int ndifficulty){return rankings[difficulty];}
    public int getDifficulty(){return difficulty;}
    public int getNumMines(){return numMines;}
    public int getSeenBoxes(){return seenBoxes;}
    
    public void setSeenBoxes(int s){seenBoxes = s;}
    public void setDifficulty(int ndifficulty){
        difficulty = ndifficulty;
    }
    public void setNumMines(){
        switch (difficulty){
            case EASY: numMines = EASY_MINES; break;
            case MEDIUM: numMines = MEDIUM_MINES; break;
            case HARD: numMines = HARD_MINES; break;
        }
    }
    
    private void clear(){
        int i,j;
        
        seenBoxes = 0;
        
        for (i = 0;i < ARRAY_LENGTH;i++){
            for (j = 0;j < ARRAY_LENGTH;j++){
                table[i][j] = new Box(0, false);
            }
        }
    }
    
    private void setMines(){
        int count = 0;
        int i = 0, j = 0;
        
        while (i < ARRAY_LENGTH && j < ARRAY_LENGTH && count < numMines){
        
            i = (int) Math.round(Math.random() * (ARRAY_LENGTH - 1));
            j = (int) Math.round(Math.random() * (ARRAY_LENGTH - 1));
            
            table[i][j] = new Box(-1, false);;
            count++;
        }
    }
    
    private void oneForMine(int r, int c){
        int i, j;
        
        for (i = r - 1;i <= r + 1;i++){
            for (j = c - 1;j <= c + 1;j++){
                if (i >= 0 && i < ARRAY_LENGTH && j >= 0 && j < ARRAY_LENGTH){
                    if (table[i][j].getValue() != -1){
                        table[i][j].setValue(table[i][j].getValue() + 1);
                    }
                }
            }
        }
    }
    public void setNumbers(){
        clear();
        setMines();
        int i,j;
        
        for (i = 0;i < ARRAY_LENGTH;i++){
            for (j = 0;j < ARRAY_LENGTH;j++){
                if (table[i][j].getValue() == -1){
                    oneForMine(i, j);
                }
            }
        }
    }
    
    public boolean showBox(int r, int c){
        int i,j;
        boolean res;
        
        if (table[r][c].getValue() == -1){
            reveal();
            res = true;
        }
        else{
            table[r][c].setShow(true);
            seenBoxes++;
            res = false;
        }
        
        if(seenBoxes == 64 - numMines){res = true;}
        
        return res;
    }
    
    public void reveal(){
        int i,j;
        
        for (i = 0;i < ARRAY_LENGTH;i++){
            for (j = 0;j < ARRAY_LENGTH;j++){
                table[i][j].setShow(true);
            }
        }
    }
    
    public String toString(){
        String s = "MINESWEEPER" + "\n" + "\n" + "   ";
        int i,j;
        
        for (i = 0;i < ARRAY_LENGTH;i++){
            s = s + i + "   ";
        }
        s = s + "\n";
        for (i = 0;i < ARRAY_LENGTH;i++){
            s = s + i + " ";
            for (j = 0;j < ARRAY_LENGTH;j++){
                if(table[i][j].getValue() != -1 ||
                table[i][j].getShow() == false){
                    s = s + " " + table[i][j] + "  ";
                }
                else{s = s + table[i][j] + "  ";}
            }
            s = s + "\n";
        }
        
        return s;
    }
}