/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class TicTacToeController implements Initializable {

    @FXML
    private Button topLeft;
    @FXML
    private Button topCenter;
    @FXML
    private Button centerLeft;
    @FXML
    private Button center;
    @FXML
    private Button bottomLeft;
    @FXML
    private Button bottomCenter;
    @FXML
    private Button topRight;
    @FXML
    private Button centerRight;
    @FXML
    private Button bottomRight;
    @FXML
    private Label winText;

    /*  board structure: row * 3 + column (both [0, 2])
           0 1 2
          -------
       0 | 0 1 2
       1 | 3 4 5
       2 | 6 7 8
    */
    private int[] board;
    private int turn;
    private Button[] buttons;
    private boolean win;
    private static final String RED_BUTTON = "-fx-background-image: url(\"http://d30y9cdsu7xlg0.cloudfront.net/png/8752-200.png\");" +
            "-fx-background-repeat: stretch;" +
            "-fx-background-size: 90 90;" +
            "-fx-background-position: center center;",
            GREEN_BUTTON = "-fx-background-image: url(\"http://stratanews.net/wp-content/uploads/2013/02/At-Symbol.png\");" +
                    "-fx-background-repeat: stretch;" +
                    "-fx-background-size: 90 90;" +
                    "-fx-background-position: center center;",
            DEFAULT_BUTTON = "";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        win = false;
        board = new int[9];
        turn = 0;
        buttons = new Button[]{
            topLeft,        topCenter,      topRight,
            centerLeft,     center,         centerRight,
            bottomLeft,     bottomCenter,   bottomRight
        };
    }

    @FXML
    public void buttonClick(ActionEvent event) {
        if (win) {
            for (Button button : buttons) button.setStyle(DEFAULT_BUTTON);
            win = false;
            board = new int[9];
            winText.setText("");
            turn = 0;
        } else {
            Button button = (Button) event.getTarget();
            
            int x = 1, y = 1;
            if (button.getId().contains("top")) x = 0;
            else if (button.getId().contains("bottom")) x = 2;
            if (button.getId().contains("Left")) y = 0;
            else if (button.getId().contains("Right")) y = 2;
            
            if (board[3 * x + y] == 0) {
                //Player valid input
                button.setStyle(GREEN_BUTTON);
                turn++;
                board[x * 3 + y] = 1;
                checkWin(x, y, true);

                if (win) return;
                //Enemy turn
                //TODO: implement here AI
                Random r = new Random();
                int i = r.nextInt(9);
                System.err.println("Searching good tile");
                while (board[i] != 0) i = r.nextInt(9);
                System.err.println("Found tile: " + i / 3 + ", " + i % 3);
                //Space selected
                buttons[i].setStyle(RED_BUTTON);
                turn++;
                board[i] = 2;
                checkWin(i / 3, i % 3, false);
                
            }
        }
    }
    
    private void checkWin(int x, int y, boolean playerTurn) {        
        //Horizontal lines
        int h1 = (y + 1) % 3, h2 = (y + 2) % 3;
        win |= board[3 * x + y] == board[3 * x + h1] && board[3 * x + y] == board[3 * x + h2];
        //Vertical lines
        int v1 = (x + 1) % 3, v2 = (x + 2) % 3;
        win |= board[3 * x + y] == board[3 * v1 + y] && board[3 * x + y] == board[3 * v2 + y];
        //Descending diagonal
        if (x == y)
            win |= board[0] == board[8] && board[0] == board[4];
        //Ascending diagonal
        if (x + y == 2)
            win |= board[2] == board[4] && board[6] == board[4];
        
        if (win) {
            winText.setText((playerTurn ? "You win!" : "You lose!") + 
                " To play again, click any button");
        } else if (turn == board.length) {
            winText.setText("It's a draw! To play again, click any button");
            win = true;
        }
    }
}
