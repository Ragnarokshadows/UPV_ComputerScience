package MyActivity;

import java.util.*;

public class Game{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        boolean res;
        boolean end = false;
        int x,y,difficulty;
        String name;
        String ans,ans2;
        Player p;
        TimeInstant start, endT;
        MineSweeper game = new MineSweeper(0);
        
        do{
            System.out.println("Do you want to play MineSweeper?");
            ans = kbd.nextLine();
            
            if (ans.equals("YES")){
                System.out.println("What difficulty do you choose?");
                difficulty = kbd.nextInt();
                
                start = new TimeInstant();
                
                game.setDifficulty(difficulty);
                game.setNumMines();
                game.setNumbers();
                
                System.out.println(game);
                System.out.println("");
                
                do{
                    System.out.println("Give me the x: ");
                    x = kbd.nextInt();
                    System.out.println("Give me the y: ");
                    y = kbd.nextInt();
                    
                    end = game.showBox(x, y);
                    System.out.println(game);
                    
                }while (end == false);
                
                endT = new TimeInstant();
                
                if (game.getSeenBoxes() < 64 - game.getNumMines()){
                    System.out.println("YOU LOOSE");
                }
                
                else{
                    System.out.println("YOU WIN");
                }
                
                
                kbd.nextLine();
                System.out.println("Give me your name: ");
                name = kbd.nextLine();
               
                
                p = new Player(name, difficulty, opScore(start, endT, game));
                
                game.getRanking(difficulty).addPlayer(p);
                
                System.out.println("Do you want to see the top10 ranking?");
                ans2 = kbd.nextLine();
            
                if (ans2.equals("YES")){
                    System.out.println("From what difficulty?");
                    difficulty = kbd.nextInt();
                    System.out.println(game.getRanking(difficulty));
                    kbd.nextLine();
                }
            }
        }while(ans.equals("YES"));
    }
    
    public static int opScore(TimeInstant start, TimeInstant end, 
    MineSweeper game){
        int score = 0;
        
        if(game.getSeenBoxes() == 64 - game.getNumMines()){
            score = 10000 / (end.toMinutes() - start.toMinutes());
            score = score * (game.getDifficulty() + 1);
        }
        
        return score;
    } 
}