package MyActivity;

public class Ranking{
    private Player[] top10;
    private int highscore;
    
    public Ranking(){
        top10 = new Player[10];
        highscore = 0;
    }
    
    public int searchPlayer(String s){
        int i= 0;
        int res = -1;
        boolean end = false;
        
        while (i < top10.length && end == false){
            if (top10[i] != null && top10[i].getName().equals(s)){
                res = i;
                end = true;
            }
            i++;
        }
        
        return res;
    }   
    
    public void addPlayer(Player p){
        int x = searchPlayer(p.getName());
        int i = 0;
        boolean end = false;
        Player aux = null;
        
        if (x >= 0){top10[x] = p;}
        else{
            while (i < top10.length && end == false){
                if (top10[i] != null && top10[i].getScore() >= p.getScore()){
                    i++;
                }
                else{
                    end = true;
                    x = i;
                    while (i < top10.length -1){
                        aux = top10[i];
                        top10[x] = p;
                        top10[i + 1] = aux;
                        i++;
                    }
                }
            }
        }
        
        highscore = top10[0].getScore();
    }
    
    public String toString(){
        String s = "TOP 10" + "\n" + "\n" + "HIGHSCORE: " + highscore + "\n" +
        "\n";
        
        int i;
        
        for (i = 0;i < top10.length;i++){
            if (top10[i] != null){
                s = s + (i + 1) + ".-" + top10[i] + "\n";
            }
        }
        
        return s;
    }
}