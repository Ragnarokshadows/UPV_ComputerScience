//CSD 2016 Ana Garcia
package theantsproblem;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TheAntsProblem extends Application {
    public static final int CELDA_SIZE = 60;
    int eventindex = 0;
    int nwaits = 0;
    String[] arg;
    Text[] textAnt;
    Polygon[] arrowAnt;

    private static int integer(String[] v, int i, int def, int a, int b) {
        int n = (i >= v.length) ? def : Integer.parseInt(v[i]);
        return (n < a || n > b) ? def : n;
    }

    @Override
    public void start(Stage primaryStage) {
        // 1st arg: size of Territory (interval [2..12], default 10)
        int tsize = integer(arg, 0, 10, 2, 12);
        // 2nd arg: number of ants (interval [2..30], default 15)
        int Nants = integer(arg, 1, 15, 2, 30); 
        int steps = 3; // number of iterations for ants

        Random rnd = new Random();
        // Creating the trace
        Log log = new Log(Nants * steps * 3);

        CyclicBarrier barrier = new CyclicBarrier(Nants, new Runnable() {
            public void run() {
                log.writeLog(LogItem.END, -1, -1, -1, -1, "done");
            }
        });

        // Creating Territory
        Territory t = new Territory(tsize, log);

        // Creating ants and placing them in the territory
        Ant[] ants = new Ant[Nants];
        for (int i = 0; i < Nants; i++) {
            int x = (int) (rnd.nextDouble() * tsize); // X initial position (random)
            int y = (int) (rnd.nextDouble() * tsize); // Y initial position (random)
            ants[i] = new Ant(i, x, y, t, steps, barrier);
            ants[i].start();
        }

        // Creating and managing the interface
        ObservableList<String> events = FXCollections.observableArrayList();
        ListView<String> EventList = new ListView<String>(events);
        EventList.setItems(events);

        GridPane board = new GridPane();
        board.setGridLinesVisible(true);
        for (int i = 0; i < tsize; i++) {
            for (int j = 0; j < tsize; j++) {
                if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 == 1) && (j % 2 == 1))) {
                    Rectangle rec = new Rectangle(CELDA_SIZE, CELDA_SIZE, Color.IVORY);
                    rec.setStroke(Color.DIMGREY);
                    board.add(rec, i, j);
                } else {
                    Rectangle rec = new Rectangle(CELDA_SIZE, CELDA_SIZE, Color.WHITESMOKE);
                    rec.setStroke(Color.DIMGREY);
                    board.add(rec, i, j);
                }
                Label pos = new Label("[" + j + "," + i + "]");
                pos.setTextFill(Color.GREY);
                board.add(pos, i, j);
                GridPane.setHalignment(pos, HPos.CENTER);
                GridPane.setValignment(pos, VPos.TOP);
            }
        }

        textAnt = new Text[Nants];
        arrowAnt = new Polygon[Nants];
        for (int i = 0; i < Nants; i++) {
            textAnt[i] = new Text();
            textAnt[i].setFont(Font.font("Verdana", FontWeight.BOLD, 16));
            board.add(textAnt[i], 0, 0);

            arrowAnt[i] = new Polygon(0.0, 3.0, 3.0, 3.0, 3.0, 0.0, 12.0, 5.0, 3.0, 10.0, 3.0, 7.0, 0.0, 7.0);
            arrowAnt[i].setStroke(Color.DARKRED);
            arrowAnt[i].setFill(Color.RED);
            GridPane.setHalignment(arrowAnt[i], HPos.CENTER);
            GridPane.setValignment(arrowAnt[i], VPos.BOTTOM);
            arrowAnt[i].setTranslateY(-(double) CELDA_SIZE / 7);
            arrowAnt[i].setSmooth(true);
            arrowAnt[i].setVisible(false);
            board.add(arrowAnt[i], 0, 0);
        }
        Image image = new Image(getClass().getResourceAsStream("redant.gif"));
        Label waitcount = new Label("Total waitings: " + nwaits);
        waitcount.setFont(Font.font("Courier", FontWeight.BOLD, 14));
        waitcount.setTextFill(Color.RED);
        waitcount.setGraphic(new ImageView(image));

        Button btnplay = new Button("->");
        Button btnadv5 = new Button("->> x5");
        Button btnadv10 = new Button("->> x10");
        Button btnstart = new Button("<|");
        Button btnend = new Button("|>");
        Button btnrew = new Button("<-");

        btnstart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventindex = 0;
                nwaits = 0;
                for (int i = 0; i < Nants; i++)
                    takeAnt(i);
                CurrentEventList(log, events, eventindex);
                waitcount.setText("Total waitings: " + nwaits);

            }
        });

        btnrew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LogItem item;
                int newevent = eventindex - 2;
                if (newevent >= 0) {
                    eventindex = 0;
                    nwaits = 0;
                    for (int i = 0; i < Nants; i++)
                        takeAnt(i);
                    while (eventindex <= newevent) {
                        item = log.readLog(eventindex);
                        switch (item.cod_op) {
                        case LogItem.PUT:
                            putAnt(board, item.idH, item.PosX, item.PosY, item.State);
                            break;
                        case LogItem.MOVE:
                            moveAnt(board, item.idH, item.PosX, item.PosY, item.State);
                            break;
                        case LogItem.TAKE:
                            takeAnt(item.idH);
                            break;
                        case LogItem.END:
                            break;
                        }
                        eventindex++;
                    }
                    CurrentEventList(log, events, eventindex);
                    waitcount.setText("Total waitings: " + nwaits);
                }
            }
        });

        btnplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LogItem item;
                int last = log.getsize();
                if (eventindex < last) {
                    item = log.readLog(eventindex);
                    switch (item.cod_op) {
                    case LogItem.PUT:
                        putAnt(board, item.idH, item.PosX, item.PosY, item.State);
                        break;
                    case LogItem.MOVE:
                        moveAnt(board, item.idH, item.PosX, item.PosY, item.State);
                        break;
                    case LogItem.TAKE:
                        takeAnt(item.idH);
                        break;
                    case LogItem.END:
                        break;
                    }
                    eventindex++;
                    CurrentEventList(log, events, eventindex);
                    waitcount.setText("Total waitings: " + nwaits);
                }
            }
        });

        btnadv5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LogItem item;
                int last = log.getsize();
                for (int i = 0; i < 5; i++) {
                    if (eventindex < last) {
                        item = log.readLog(eventindex);
                        switch (item.cod_op) {
                        case LogItem.PUT:
                            putAnt(board, item.idH, item.PosX, item.PosY, item.State);
                            break;
                        case LogItem.MOVE:
                            moveAnt(board, item.idH, item.PosX, item.PosY, item.State);
                            break;
                        case LogItem.TAKE:
                            takeAnt(item.idH);
                            break;
                        case LogItem.END:
                            break;
                        }
                        eventindex++;
                    }
                }
                CurrentEventList(log, events, eventindex);
                waitcount.setText("Total waitings: " + nwaits);
            }
        });

        btnadv10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LogItem item;
                int last = log.getsize();
                for (int i = 0; i < 10; i++) {
                    if (eventindex < last) {
                        item = log.readLog(eventindex);
                        switch (item.cod_op) {
                        case LogItem.PUT:
                            putAnt(board, item.idH, item.PosX, item.PosY, item.State);
                            break;
                        case LogItem.MOVE:
                            moveAnt(board, item.idH, item.PosX, item.PosY, item.State);
                            break;
                        case LogItem.TAKE:
                            takeAnt(item.idH);
                            break;
                        case LogItem.END:
                            break;
                        }
                        eventindex++;
                    }
                }
                CurrentEventList(log, events, eventindex);
                waitcount.setText("Total waitings: " + nwaits);

            }
        });

        btnend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LogItem item;
                int last = log.getsize();
                while (eventindex < last) {
                    item = log.readLog(eventindex);
                    switch (item.cod_op) {
                    case LogItem.PUT:
                        putAnt(board, item.idH, item.PosX, item.PosY, item.State);
                        break;
                    case LogItem.MOVE:
                        moveAnt(board, item.idH, item.PosX, item.PosY, item.State);
                        break;
                    case LogItem.TAKE:
                        takeAnt(item.idH);
                        break;
                    case LogItem.END:
                        break;
                    }
                    eventindex++;
                }
                CurrentEventList(log, events, eventindex);
                waitcount.setText("Total waitings: " + nwaits);
            }
        });

        HBox hbox2 = new HBox(20);
        hbox2.getChildren().addAll(btnstart, btnrew, btnplay, btnadv5, btnadv10, btnend);

        HBox hbox3 = new HBox(60);
        hbox3.getChildren().addAll(hbox2, waitcount);
        hbox3.setAlignment(Pos.BOTTOM_CENTER);

        VBox vbox1 = new VBox(10);
        vbox1.getChildren().addAll(board, hbox3);

        HBox mainpanel = new HBox(20);
        mainpanel.setPadding(new Insets(5, 5, 5, 5));
        mainpanel.getChildren().addAll(vbox1, EventList);

        Scene scene = new Scene(mainpanel, 1000, 800);

        primaryStage.setTitle("Ants Problem-version: " + t.getDesc() + ". Ants= " + Nants);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            // Application exit
            Platform.exit();
            System.exit(0);
        });
    }

    private void putAnt(GridPane tab, int hid, int posX, int posY, int state) {
        Text t1 = textAnt[hid];
        t1.setText("Ant" + hid);
        if (state == LogItem.OK) {
            t1.setFill(Color.GREEN);
            t1.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
            GridPane.setValignment(t1, VPos.CENTER);
        } else if (state == LogItem.WAITINS) {
            t1.setFill(Color.BLUEVIOLET);
            t1.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
            GridPane.setValignment(t1, VPos.BOTTOM);
            nwaits++;
        }
        GridPane.setColumnIndex(t1, posY);
        GridPane.setRowIndex(t1, posX);
        GridPane.setHalignment(t1, HPos.CENTER);
        arrowAnt[hid].setVisible(false);
    }

    private void moveAnt(GridPane tab, int hid, int toX, int toY, int state) {
        Text t1 = textAnt[hid];
        Polygon arrow = arrowAnt[hid];
        if (state == LogItem.OK) {
            t1.setFill(Color.GREEN);
            GridPane.setColumnIndex(t1, toY);
            GridPane.setRowIndex(t1, toX);
            arrow.setVisible(false);
        } else if (state == LogItem.WAIT) {
            int x = GridPane.getRowIndex(t1), y = GridPane.getColumnIndex(t1);
            t1.setFill(Color.RED);
            if (toY > y)
                arrow.setRotate(0); // right
            else if (toY < y)
                arrow.setRotate(180); // left
            else if (toX > x)
                arrow.setRotate(90); // down
            else
                arrow.setRotate(-90); // up
            GridPane.setColumnIndex(arrow, y);
            GridPane.setRowIndex(arrow, x);
            arrow.setVisible(true);
            nwaits++;
        }
    }

    private void takeAnt(int hid) {
        textAnt[hid].setText("");
        arrowAnt[hid].setVisible(false);
    }

    private void CurrentEventList(Log t1, ObservableList<String> events, int index) {
        LogItem item;
        int ult = t1.getsize();
        int next = 0;
        String s = new String();
        events.remove(0, events.size());
        while ((next < ult) && (next < index)) {
            item = t1.readLog(next);
            s = next + ".- " + item.Evento + "\n";
            events.add(s);
            next++;
        }
    }

    @Override
    public void init() {
        arg = new String[this.getParameters().getRaw().size()];
        for (int i = 0; i < this.getParameters().getRaw().size(); i++) {
            arg[i] = this.getParameters().getRaw().get(i);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
