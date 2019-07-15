/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class CalculatorController implements Initializable {
    @FXML
    private Text text;

    private static final int NULL = 0, ADD = 1, SUB = 2, MUL = 3, DIV = 4;
    private static final int UI_LIMIT = 11;
    private int operation, decimal, length;
    private double value, operand, memory;
    private boolean writeNew;
    DecimalFormat format = new DecimalFormat("#0.######");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        decimal = 1;
        value = operand = memory = 0;
        operation = NULL;
        length = 0;
        writeNew = true;
        updateUI(format);
    }

    public void numberClick(ActionEvent event) {
        if (length < UI_LIMIT) {
            double number = Integer.valueOf(((Button) event.getSource()).getText());
            if (writeNew) {
                value = length = 0;
                decimal = 1;
                writeNew = false;
            } else if (decimal == 1) {
                value *= 10;
            } else {
                number /= decimal;
                decimal *= 10;
            }
            value += number;
            length++;
            updateUI(format);
        }
    }

    public void operationClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        if (operation != NULL) evaluate();
        operand = value;
        switch (clicked.getText()) {
            case "+": operation = ADD; break;
            case "-": operation = SUB; break;
            case "*": operation = MUL; break;
            case "/": operation = DIV; break;
            default: operation = NULL;
        }
        writeNew = true;
    }

    public void functionClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        switch (clicked.getText()) {
            case "=":
                evaluate();
                break;
            case "C":
                decimal = 1;
                value = operand = operation = 0;
                length = 0;
                updateUI(format);
                break;
            case "DEL":
                if (decimal > 10) {
                    decimal /= 10;
                    value -= value % (1.0 / decimal);
                } else if (decimal == 10) {
                    decimal /= 10;
                } else {
                    value = Math.floor(value / 10);
                }
                length--;
                updateUI(format);
                break;
            case "M+":
                memory += value;
                break;
            case "MC":
                value = memory;
                updateUI(format);
                operand = memory;
                value = 0;
                break;
            case ".":
                decimal = 10;
                break;
        }
    }

    private void evaluate() {
        switch (operation) {
            case ADD: operand += value; break;
            case SUB: operand -= value; break;
            case MUL: operand *= value; break;
            case DIV:
                if (value == 0) value = Double.NaN;
                operand /= value; break;
            default: return;
        }
        double aux = value;
        value = operand;
        operand = aux;
        length = String.valueOf((int)Math.floor(value)).length();
        boolean scientific = length >= UI_LIMIT;
        decimal = 1;
        while (value % decimal != 0 && length < UI_LIMIT) {
            decimal *= 10;
            length++;
        }
        writeNew = true;
        updateUI(scientific ? new DecimalFormat("0.0E0") : format);
        operation = NULL;
    }

    private void updateUI(DecimalFormat format) {
        if (value == Double.NaN) text.setText("NaN");
        else
            text.setText(
                    format.format(value)
            );
    }
}
