/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ncrosses;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Stéphane
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField second_score;
    @FXML
    private TextField first_score;
    @FXML
    private TextField information;
    @FXML
    private Button start;
    @FXML
    private Button reset;
    @FXML
    private Button button11;
    @FXML
    private Button button00;
    @FXML
    private Button button01;
    @FXML
    private Button button10;
    @FXML
    private Button button02;
    @FXML
    private Button button20;
    @FXML
    private Button button22;
    @FXML
    private Button button12;
    @FXML
    private Button button21;
    
    private int[][] game_table;
    private int turn;
    private boolean win;
    private int win1;
    private int win2;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        turn=0;
        game_table=new int[3][3];
        second_score.setText("0");
        first_score.setText("0");
        information.setText("1st Player turn");
        win=false;
        win1=0;
        win2=0;
    }    

    @FXML
    private void start(ActionEvent event) {
        turn=0;
        game_table=new int[3][3];
        information.setText("1st Player turn");
        win=false;
        clear();
    }

    @FXML
    private void reset(ActionEvent event) {
        turn=0;
        game_table=new int[3][3];
        second_score.setText("0");
        first_score.setText("0");
        information.setText("1st Player turn");
        win=false;
        clear();
        win1=0;
        win2=0;
    }

    @FXML
    private void mark11(ActionEvent event) {
        if(turn==0){
            if(game_table[1][1]==0){
                button11.setText("O");
                game_table[1][1]=1;
                turn();
            }
        } else {
            if(game_table[1][1]==0){
                button11.setText("X");
                game_table[1][1]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark00(ActionEvent event) {
        if(turn==0){
            if(game_table[0][0]==0){
                button00.setText("O");
                game_table[0][0]=1;
                turn();
            }
        } else {
            if(game_table[0][0]==0){
                button00.setText("X");
                game_table[0][0]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark01(ActionEvent event) {
        if(turn==0){
            if(game_table[0][1]==0){
                button01.setText("O");
                game_table[0][1]=1;
                turn();
            }
        } else {
            if(game_table[0][1]==0){
                button01.setText("X");
                game_table[0][1]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark10(ActionEvent event) {
        if(turn==0){
            if(game_table[1][0]==0){
                button10.setText("O");
                game_table[1][0]=1;
                turn();
            }
        } else {
            if(game_table[1][0]==0){
                button10.setText("X");
                game_table[1][0]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark02(ActionEvent event) {
        if(turn==0){
            if(game_table[0][2]==0){
                button02.setText("O");
                game_table[0][2]=1;
                turn();
            }
        } else {
            if(game_table[0][2]==0){
                button02.setText("X");
                game_table[0][2]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark20(ActionEvent event) {
        if(turn==0){
            if(game_table[2][0]==0){
                button20.setText("O");
                game_table[2][0]=1;
                turn();
            }
        } else {
            if(game_table[2][0]==0){
                button20.setText("X");
                game_table[2][0]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark22(ActionEvent event) {
        if(turn==0){
            if(game_table[2][2]==0){
                button22.setText("O");
                game_table[2][2]=1;
                turn();
            }
        } else {
            if(game_table[2][2]==0){
                button22.setText("X");
                game_table[2][2]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark12(ActionEvent event) {
        if(turn==0){
            if(game_table[1][2]==0){
                button12.setText("O");
                game_table[1][2]=1;
                turn();
            }
        } else {
            if(game_table[1][2]==0){
                button12.setText("X");
                game_table[1][2]=2;
                turn();
            }
        }
        
        winner();
    }

    @FXML
    private void mark21(ActionEvent event) {
        if(turn==0){
            if(game_table[2][1]==0){
                button21.setText("O");
                game_table[2][1]=1;
                turn();
            }
        } else {
            if(game_table[2][1]==0){
                button21.setText("X");
                game_table[2][1]=2;
                turn();
            }
        }
        
        winner();
    }
    
    private void winner(){
        win1 = Integer.parseInt(first_score.getText());
        win2 = Integer.parseInt(second_score.getText());
        if(!win){
            for(int i=0;i<=2;i++){
                if(game_table[i][0]!=0 && game_table[i][0]==game_table[i][1] && game_table[i][0]==game_table[i][2]){
                    if(game_table[i][0]==1){
                        information.setText("Player 1 Wins");
                        win1++;
                        first_score.setText(""+win1);
                    } else {
                        information.setText("Player 2 Wins");
                        win2++;
                        second_score.setText(""+win2);
                    }
                    win=true;
                }
            }
            for(int i=0;i<=2;i++){
                if(game_table[0][i]!=0 && game_table[0][i]==game_table[1][i] && game_table[0][i]==game_table[2][i]){
                    if(game_table[0][i]==1){
                        information.setText("Player 1 Wins");
                        win1++;
                        first_score.setText(""+win1);
                        
                    } else {
                        information.setText("Player 2 Wins");
                        win2++;
                        second_score.setText(""+win2);
                    }
                    win=true;
                }
            }
            if(game_table[0][0]!=0 && game_table[0][0]==game_table[1][1] && game_table[0][0]==game_table[2][2]){
                    if(game_table[0][0]==1){
                        information.setText("Player 1 Wins");
                        win1++;
                        first_score.setText(""+win1);
                    } else {
                        information.setText("Player 2 Wins");
                        win2++;
                        second_score.setText(""+win2);
                    }
                    win=true;
                }
            if(game_table[0][2]!=0 && game_table[0][2]==game_table[1][1] && game_table[0][2]==game_table[2][0]){
                    if(game_table[0][2]==1){
                        information.setText("Player 1 Wins");
                        win1++;
                        first_score.setText(""+win1);
                    } else {
                        information.setText("Player 2 Wins");
                        win2++;
                        second_score.setText(""+win2);
                    }
                    win=true;
                }
        }
    }
    
    private void turn(){
        if(turn==0){
            information.setText("2nd Player Turn");
            turn++;
        } else {
            information.setText("1st Player Turn");
            turn--;
        }
    }
    
    private void clear(){
        button00.setText("");
        button01.setText("");
        button02.setText("");
        button10.setText("");
        button11.setText("");
        button12.setText("");
        button20.setText("");
        button21.setText("");
        button22.setText("");
    }
}
