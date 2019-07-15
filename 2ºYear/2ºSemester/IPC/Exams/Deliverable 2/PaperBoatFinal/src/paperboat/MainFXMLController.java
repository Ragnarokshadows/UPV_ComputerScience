package paperboat;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.NeedleBehavior;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.LcdDesign;
import eu.hansolo.medusa.tools.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.sf.marineapi.nmea.event.AbstractSentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.HDGSentence;
import net.sf.marineapi.nmea.sentence.MDASentence;
import net.sf.marineapi.nmea.sentence.MWVSentence;
import net.sf.marineapi.nmea.sentence.RMCSentence;
import net.sf.marineapi.nmea.sentence.XDRSentence;
import net.sf.marineapi.nmea.util.Position;

public class MainFXMLController implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button navigationB;
    @FXML
    private Button statusB;
    @FXML
    private Button windB;
    @FXML
    private Button windChartsB;
    @FXML
    private Button dayNightB;
    @FXML
    private ImageView dayNightImage;
    @FXML
    private Pane pane;
    
    @FXML
    private GridPane gridPaneNav;
    @FXML
    private Label lonLabel;
    @FXML
    private Label latLabel;
    private Gauge COG; 
    private Gauge SOG; 
    private Gauge HDG;
    
    @FXML
    private VBox vBoxGraphs;
    @FXML
    private Slider intervalSlider;
    @FXML
    private GridPane gridPaneGraphs;
    private final int MAX_SIZE = 600;
    ObservableList<Data> listTWS = FXCollections.observableList(new ArrayList<>(MAX_SIZE));
    ObservableList<Data> listTWD = FXCollections.observableList(new ArrayList<>(MAX_SIZE));
    int secTrueWindDir = 0;
    int secTrueWindSp = 0;
    private XYChart.Series sTWS, sTWD;
    private NumberAxis GraphAxAxis, GraphBxAxis;  
    
    @FXML
    private GridPane gridPaneWind;
    private Gauge TWD;
    private Gauge TWS; 
    private Gauge AWD;
    private Gauge AWS;
    
    @FXML
    private VBox vBoxStatus;
    @FXML
    private PieChart pitchChart;
    @FXML
    private PieChart rollChart;    
    @FXML
    private Label  pitchLabel;
    @FXML
    private Label rollLabel;
    @FXML
    private GridPane paneTempStatus;
    private Gauge TEMP;
    
    private SentenceReader read;
    private Button selectedB;
    private boolean day;
    
    //Captured Values
    private final ObjectProperty<Position> gps = new SimpleObjectProperty();
    private final DoubleProperty twd = new SimpleDoubleProperty();
    private final DoubleProperty tmp = new SimpleDoubleProperty();
    private final DoubleProperty tws = new SimpleDoubleProperty();
    private final DoubleProperty hdg = new SimpleDoubleProperty();
    private final DoubleProperty awa = new SimpleDoubleProperty();
    private final DoubleProperty aws = new SimpleDoubleProperty();
    private final DoubleProperty roll = new SimpleDoubleProperty();
    private final StringProperty mimi= new SimpleStringProperty();
    private final DoubleProperty lon = new SimpleDoubleProperty();
    private final DoubleProperty cog = new SimpleDoubleProperty();
    private final DoubleProperty lat = new SimpleDoubleProperty();
    private final DoubleProperty sog = new SimpleDoubleProperty();
    private final DoubleProperty pitch = new SimpleDoubleProperty();
    private final StringProperty mimi2= new SimpleStringProperty();
    
    
    //Model Classes
    class MDASentenceListener extends AbstractSentenceListener<MDASentence> {
        @Override
        public void sentenceRead(MDASentence sentence) {
            Platform.runLater( () -> {
                tmp.set(sentence.getAirTemperature());
                twd.set(sentence.getTrueWindDirection());
                tws.set(sentence.getWindSpeedKnots());
            });   
        }
    }
    class HDGSentenceListener extends AbstractSentenceListener<HDGSentence>{
        @Override
        public void sentenceRead(HDGSentence sentence){
            hdg.set(sentence.getHeading());
        }
    }
    class MWVSentenceListener extends AbstractSentenceListener<MWVSentence>{
        @Override
        public void sentenceRead(MWVSentence sentence){
            awa.set(sentence.getAngle());
            Platform.runLater( () -> {
                aws.set(sentence.getSpeed());    
            });
        }
    }       
    
    class XDRSentenceListener extends AbstractSentenceListener<XDRSentence>{
        @Override
        public void sentenceRead(XDRSentence sentence){  
            Platform.runLater(()->{
                sentence.getMeasurements().forEach((me) -> {               
                    if(me.getName().equals("PTCH")){
                        pitch.set(me.getValue());
                        pitchLabel.textProperty().bind(pitch.asString());
                    }else if(me.getName().equals("ROLL")){
                        roll.set(me.getValue());
                       rollLabel.textProperty().bind(roll.asString());
                    }
                });         
            });                
        }
    }
    class RMCSentenceListener extends AbstractSentenceListener<RMCSentence>{
        @Override
        public void sentenceRead(RMCSentence sentence){            
            Platform.runLater(()-> {
                lat.set(sentence.getPosition().getLatitude());
                lon.set(sentence.getPosition().getLongitude());
                cog.set(sentence.getCourse());
                sog.set(sentence.getSpeed());
                
                String mim=sentence.getPosition().getLatitude() + "";
                String mim2=sentence.getPosition().getLongitude() + "";
                mimi.set(mim.substring(0,5) + " " +sentence.getPosition().getLongitudeHemisphere());
                mimi2.set(mim2.substring(0,5)+ " " +sentence.getPosition().getLatitudeHemisphere());
                lon.set(sentence.getPosition().getLongitude());
                latLabel.textProperty().bind(mimi);
                lonLabel.textProperty().bind(mimi2);
            });
        }
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFile();        
        
        MDASentenceListener mda =new MDASentenceListener();
        HDGSentenceListener hdgl = new HDGSentenceListener();
        MWVSentenceListener mwv = new MWVSentenceListener();
        XDRSentenceListener xdr = new XDRSentenceListener();
        RMCSentenceListener rmc = new RMCSentenceListener();
        
        read.addSentenceListener(mda);
        read.addSentenceListener(hdgl);
        read.addSentenceListener(mwv);
        read.addSentenceListener(rmc);
        read.addSentenceListener(xdr);
        read.setExceptionListener(e->{System.out.println(e.getMessage());});

        read.start();
        
        initNav();
        defaultStartup();
        initStatus();
        initWind();
        initGraphs();
    }
    
    private void loadFile() {
        try{
            File file = new File("src/resources/Jul_20_2017_1871339_0183.NMEA");
            InputStream stream = new FileInputStream(file);
            read = new SentenceReader(stream);            
        } catch (FileNotFoundException e) {}
    }
    
    public void defaultStartup() {
        day = true;
        selectedB = navigationB;
        selectedB.setDisable(true);
        gridPaneNav.setVisible(true);
    }
    
    @FXML
    private void changeScene(MouseEvent event) throws IOException {
        Button b = (Button) event.getSource();        
        changeButtonSelected(b);
        
        if (b == navigationB)       {
            vBoxStatus.setVisible(false);
            gridPaneWind.setVisible(false);
            vBoxGraphs.setVisible(false);
            gridPaneNav.setVisible(true);
        }
        else if (b == statusB)      {
            gridPaneWind.setVisible(false);
            vBoxGraphs.setVisible(false);
            gridPaneNav.setVisible(false);
            vBoxStatus.setVisible(true);
        }
        else if (b == windB)        {
            vBoxStatus.setVisible(false);
            vBoxGraphs.setVisible(false);
            gridPaneNav.setVisible(false);
            gridPaneWind.setVisible(true);
        }
        else if (b == windChartsB)  {
            vBoxStatus.setVisible(false);
            gridPaneWind.setVisible(false);
            gridPaneNav.setVisible(false);
            vBoxGraphs.setVisible(true);
        }
    }
    
    private void changeButtonSelected(Button b) {
        selectedB.setDisable(false);
        selectedB = b;
        selectedB.setDisable(true);
    }

    @FXML
    private void changeCSS(MouseEvent event) {        
        anchorPane.getScene().getStylesheets().clear();
        if (day) {
            day = !day;
            dayNightImage.setImage(new Image("resources/Images/night_Blue.png"));
            anchorPane.getScene().getStylesheets().add(getClass().getResource("/resources/StyleSheets/nightCSS.css").toExternalForm());
            
            SOG.minorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            SOG.mediumTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            SOG.majorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            SOG.titleColorProperty().set(Color.web("0xFFFFFA", 1.0));
            SOG.unitColorProperty().set(Color.web("0xFFFFFA", 1.0));
            SOG.valueColorProperty().set(Color.web("0xFFFFFA", 1.0));
            SOG.tickLabelColorProperty().set(Color.web("0xFFFFFA", 1.0));            
            COG.minorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            COG.mediumTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            COG.majorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            COG.titleColorProperty().set(Color.web("0xFFFFFA", 1.0));
            COG.unitColorProperty().set(Color.web("0xFFFFFA", 1.0));
            COG.valueColorProperty().set(Color.web("0xFFFFFA", 1.0));
            COG.tickLabelColorProperty().set(Color.web("0xFFFFFA", 1.0));            
            HDG.minorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            HDG.mediumTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            HDG.majorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            HDG.titleColorProperty().set(Color.web("0xFFFFFA", 1.0));
            HDG.unitColorProperty().set(Color.web("0xFFFFFA", 1.0));
            HDG.valueColorProperty().set(Color.web("0xFFFFFA", 1.0));
            HDG.tickLabelColorProperty().set(Color.web("0xFFFFFA", 1.0));            
            TWS.minorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWS.mediumTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWS.majorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWS.titleColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWS.unitColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWS.valueColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWS.tickLabelColorProperty().set(Color.web("0xFFFFFA", 1.0));            
            TWD.minorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWD.mediumTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWD.majorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWD.titleColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWD.unitColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWD.valueColorProperty().set(Color.web("0xFFFFFA", 1.0));
            TWD.tickLabelColorProperty().set(Color.web("0xFFFFFA", 1.0));            
            AWS.minorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWS.mediumTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWS.majorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWS.titleColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWS.unitColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWS.valueColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWS.tickLabelColorProperty().set(Color.web("0xFFFFFA", 1.0));            
            AWD.minorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWD.mediumTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWD.majorTickMarkColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWD.titleColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWD.unitColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWD.valueColorProperty().set(Color.web("0xFFFFFA", 1.0));
            AWD.tickLabelColorProperty().set(Color.web("0xFFFFFA", 1.0));
        } else {
            day = !day;
            dayNightImage.setImage(new Image("resources/Images/day_Blue.png"));
            anchorPane.getScene().getStylesheets().add(getClass().getResource("/resources/StyleSheets/dayCSS.css").toExternalForm());
            
            SOG.minorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            SOG.mediumTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            SOG.majorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            SOG.titleColorProperty().set(Color.web("0x00021A", 1.0));
            SOG.unitColorProperty().set(Color.web("0x00021A", 1.0));
            SOG.valueColorProperty().set(Color.web("0x00021A", 1.0));
            SOG.tickLabelColorProperty().set(Color.web("0x00021A", 1.0));            
            COG.minorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            COG.mediumTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            COG.majorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            COG.titleColorProperty().set(Color.web("0x00021A", 1.0));
            COG.unitColorProperty().set(Color.web("0x00021A", 1.0));
            COG.valueColorProperty().set(Color.web("0x00021A", 1.0));
            COG.tickLabelColorProperty().set(Color.web("0x00021A", 1.0));            
            HDG.minorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            HDG.mediumTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            HDG.majorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            HDG.titleColorProperty().set(Color.web("0x00021A", 1.0));
            HDG.unitColorProperty().set(Color.web("0x00021A", 1.0));
            HDG.valueColorProperty().set(Color.web("0x00021A", 1.0));
            HDG.tickLabelColorProperty().set(Color.web("0x00021A", 1.0));            
            TWS.minorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            TWS.mediumTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            TWS.majorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            TWS.titleColorProperty().set(Color.web("0x00021A", 1.0));
            TWS.unitColorProperty().set(Color.web("0x00021A", 1.0));
            TWS.valueColorProperty().set(Color.web("0x00021A", 1.0));
            TWS.tickLabelColorProperty().set(Color.web("0x00021A", 1.0));            
            TWD.minorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            TWD.mediumTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            TWD.majorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            TWD.titleColorProperty().set(Color.web("0x00021A", 1.0));
            TWD.unitColorProperty().set(Color.web("0x00021A", 1.0));
            TWD.valueColorProperty().set(Color.web("0x00021A", 1.0));
            TWD.tickLabelColorProperty().set(Color.web("0x00021A", 1.0));            
            AWS.minorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            AWS.mediumTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            AWS.majorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            AWS.titleColorProperty().set(Color.web("0x00021A", 1.0));
            AWS.unitColorProperty().set(Color.web("0x00021A", 1.0));
            AWS.valueColorProperty().set(Color.web("0x00021A", 1.0));
            AWS.tickLabelColorProperty().set(Color.web("0x00021A", 1.0));            
            AWD.minorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            AWD.mediumTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            AWD.majorTickMarkColorProperty().set(Color.web("0x00021A", 1.0));
            AWD.titleColorProperty().set(Color.web("0x00021A", 1.0));
            AWD.unitColorProperty().set(Color.web("0x00021A", 1.0));
            AWD.valueColorProperty().set(Color.web("0x00021A", 1.0));
            AWD.tickLabelColorProperty().set(Color.web("0x00021A", 1.0));
        }        
    }
    
    //Loading Different Windows
    private void initNav() {
        SOG = GaugeBuilder.create()  
            .title("Speed Over Ground")  
            .subTitle("")  
            .unit("Kn")
            .needleType(Gauge.NeedleType.VARIOMETER)
            .needleBehavior(NeedleBehavior.OPTIMIZED)
            .build(); 
        gridPaneNav.add(SOG, 0, 0);
        SOG.setNeedleColor(Color.RED);
        SOG.setMaxValue(360.0);
        SOG.valueProperty().bind(sog);
        
        COG = GaugeBuilder.create()
            .title("Course Over Ground")
            .unit("Degrees")
            .minValue(0)
            .maxValue(359)
            .startAngle(180)
            .angleRange(360)
            .autoScale(false)
            .customTickLabelsEnabled(true)
            .customTickLabels("N")
            .customTickLabelFontSize(72)
            .needleType(Gauge.NeedleType.VARIOMETER)   
            .needleBehavior(NeedleBehavior.OPTIMIZED)
            .build();        
        COG.valueProperty().bind(cog);
        gridPaneNav.add(COG, 0, 1);
        
        HDG = GaugeBuilder.create()
            .title("Heading")
            .unit("Degrees")
            .minValue(0)
            .maxValue(359)
            .startAngle(180)
            .angleRange(360)
            .autoScale(false)
            .customTickLabelsEnabled(true)
            .customTickLabels("N")
            .customTickLabelFontSize(72)
            .needleType(Gauge.NeedleType.VARIOMETER)
            .needleBehavior(NeedleBehavior.OPTIMIZED)
            .build();
        gridPaneNav.add(HDG, 1, 1);
        HDG.valueProperty().bind(hdg);
    }
    
    private void initStatus() {        
        pitchChart.getData().add(new PieChart.Data("",50));        
        pitchChart.getData().add(new PieChart.Data("",50));
        pitchChart.setStartAngle(180);
        pitchChart.setLabelsVisible(true);
        pitchChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFA;");
        pitchChart.getData().get(1).getNode().setStyle("-fx-pie-color: #1870B8");
        pitchChart.startAngleProperty().bind(pitch.negate().add(180));
        
        rollChart.getData().add(new PieChart.Data("",50));        
        rollChart.getData().add(new PieChart.Data("",50));
        rollChart.setStartAngle(180);
        rollChart.setLabelsVisible(true);
        rollChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFA;");
        rollChart.getData().get(1).getNode().setStyle("-fx-pie-color: #1870B8;");
        rollChart.startAngleProperty().bind(roll.negate().add(180));
        
        TEMP = GaugeBuilder.create()
            .skinType(Gauge.SkinType.LCD)
            .lcdDesign(LcdDesign.BLUE_DARKBLUE)
            .title("Temperature")
            .unit("ºC")
            .decimals(1)
            .build();
        paneTempStatus.add(TEMP,0,1);
        GridPane.setColumnSpan(TEMP, 2);
        TEMP.valueProperty().bind(tmp);
    }
    
    private void initWind() {
        TWD = GaugeBuilder.create()  
            .title("True Wind Direction")
            .unit("Degrees")
            .needleType(Gauge.NeedleType.VARIOMETER)
            .minValue(0)
            .maxValue(359)
            .startAngle(180)
            .angleRange(360)
            .autoScale(false)
            .customTickLabelsEnabled(true)
            .customTickLabels("N")
            .customTickLabelFontSize(72)
            .animated(true)
            .animationDuration(500)
            .needleType(Gauge.NeedleType.VARIOMETER)
            .needleBehavior(NeedleBehavior.OPTIMIZED)
            .build(); 
        gridPaneWind.add(TWD, 0, 2);
        TWD.setNeedleColor(Color.RED);
        TWD.setMaxValue(360.0);
        TWD.valueProperty().bind(twd);
        
        TWS = GaugeBuilder.create()  
            .title("True Wind Speed")  
            .subTitle("")  
            .unit("Kn") 
            .needleType(Gauge.NeedleType.VARIOMETER)
            .needleBehavior(NeedleBehavior.OPTIMIZED)
            .build(); 
        gridPaneWind.add(TWS, 0, 1);
        TWS.setNeedleColor(Color.RED);
        TWS.setMaxValue(360.0);
        TWS.valueProperty().bind(tws);        
        
        AWD = GaugeBuilder.create()  
            .title("Aparent Wind Angle")
            .unit("Degrees")
            .needleType(Gauge.NeedleType.VARIOMETER)
            .minValue(0)
            .maxValue(359)
            .startAngle(180)
            .angleRange(360)
            .autoScale(false)
            .customTickLabelsEnabled(true)
            .customTickLabels("N")
            .customTickLabelFontSize(72)
            .needleBehavior(NeedleBehavior.OPTIMIZED)
            .build(); 
        gridPaneWind.add(AWD, 1, 2);
        AWD.setNeedleColor(Color.RED);
        AWD.setMaxValue(360.0);
        AWD.valueProperty().bind(awa);
        
        AWS = GaugeBuilder.create()  
            .title("Aparent Wind Speed")
            .subTitle("")
            .unit("Kn") 
            .needleType(Gauge.NeedleType.VARIOMETER)
            .needleBehavior(NeedleBehavior.OPTIMIZED)
            .build(); 
        gridPaneWind.add(AWS, 1, 1);
        AWS.setNeedleColor(Color.RED);
        AWS.setMaxValue(360.0);
        AWS.valueProperty().bind(aws);
    }
    
    private void initGraphs() {
        GraphAxAxis = new NumberAxis();
        NumberAxis yAxisFirst = new NumberAxis();
        LineChart<Number, Number> twsChart = new LineChart<>( GraphAxAxis, yAxisFirst );
        twsChart.setTitle( "True Wind Speed (Kn/min.)" );
        twsChart.setLegendVisible(false);
        twsChart.setHorizontalGridLinesVisible(false);
        twsChart.setHorizontalZeroLineVisible(false);
        twsChart.setVerticalGridLinesVisible(false);
        twsChart.setVerticalZeroLineVisible(false);
        twsChart.setAnimated( false );
        sTWS = new XYChart.Series();
        twsChart.getData().add( sTWS );
        twsChart.setCreateSymbols( false );  
                
        GraphBxAxis = new NumberAxis();
        NumberAxis yAxisSecond = new NumberAxis();
        LineChart<Number, Number> twdChart = new LineChart<>( GraphBxAxis, yAxisSecond );
        twdChart.setTitle( "True Wind Direction (º/min.)" );
        twdChart.setLegendVisible(false);
        twdChart.setHorizontalGridLinesVisible(false);
        twdChart.setHorizontalZeroLineVisible(false);
        twdChart.setVerticalGridLinesVisible(false);
        twdChart.setVerticalZeroLineVisible(false);
        twdChart.setAnimated( false );
        sTWD = new XYChart.Series();
        twdChart.getData().add( sTWD );        
        twdChart.setCreateSymbols( false );  
        
        gridPaneGraphs.add(twsChart, 0, 0);
        gridPaneGraphs.add(twdChart, 0, 1);
        
        GraphAxAxis.autoRangingProperty().set( false );
       GraphBxAxis.autoRangingProperty().set( false );
        GraphAxAxis.setForceZeroInRange( false );
        GraphBxAxis.setForceZeroInRange( false );               
        GraphAxAxis.setMinorTickCount( 5 );
        GraphAxAxis.setTickUnit( 10 );
        GraphBxAxis.setMinorTickCount( 5 );
        GraphBxAxis.setTickUnit( 10 );
        
        intervalSlider.valueProperty().addListener((observable, oldValue, newValue) -> {           
            double upperBound = secTrueWindSp;
            if(upperBound < ((int) (newValue.doubleValue()*60))) {
                upperBound = (int) ((newValue.doubleValue()*60));
            }
            GraphAxAxis.setUpperBound( upperBound );
           
            double lower = GraphAxAxis.getUpperBound() - (int) (newValue.doubleValue()*60);
            if(lower < 0) {
                lower = 0;
            }
            GraphAxAxis.setLowerBound(lower);           
           
            upperBound = secTrueWindDir;
            if( upperBound < (int) (newValue.doubleValue()*60)) {
                upperBound = (int) (newValue.doubleValue()*60);
            }
            GraphBxAxis.setUpperBound( upperBound );
           
            lower = GraphBxAxis.getUpperBound() - (int) (newValue.doubleValue()*60);
            if(lower < 0) {
                lower = 0;
            }
            GraphBxAxis.setLowerBound(lower);           
        });
        
        twd.addListener((observable, oldValue, newValue) -> {
            if(listTWD.size() == MAX_SIZE) {
                sTWS.getData().remove( 0 );
            }
            if( sTWD.getData().size() >= MAX_SIZE ) {
                sTWD.getData().remove( 0 );
            }
            sTWD.getData().add( new XYChart.Data( secTrueWindDir, newValue ) );
           secTrueWindDir++;
           
            double upper = secTrueWindDir;
            if( upper < ( int )( intervalSlider.getValue() * 60 ) ) {
                upper = ( int )( intervalSlider.getValue() * 60 );
            }
            GraphBxAxis.setUpperBound( upper );
           
           
            double lower = GraphBxAxis.getUpperBound() - ( int )( intervalSlider.getValue() * 60 );
            if( lower< 0 ) {
                lower = 0;
            }
           GraphBxAxis.setLowerBound( lower );
           
        });
        
        tws.addListener((observable, oldValue, newValue)-> {
            if(listTWS.size() == MAX_SIZE) {
                sTWS.getData().remove( 0 );
            }
            if( sTWS.getData().size() >= MAX_SIZE ) {
                sTWS.getData().remove( 0 );
            }
            sTWS.getData().add( new XYChart.Data( secTrueWindSp, newValue ) );
           
            secTrueWindSp++;
            double upper = secTrueWindSp;
            if( upper < ( int )( intervalSlider.getValue() * 60 ) ) {
                upper = ( int )( intervalSlider.getValue() * 60 );
            }
            GraphAxAxis.setUpperBound( upper );
           
           
            double lowerBound = GraphAxAxis.getUpperBound() - ( int )( intervalSlider.getValue() * 60 );
            if( lowerBound < 0 ) {
                lowerBound = 0;
            }
            GraphAxAxis.setLowerBound( lowerBound );
        });
    }
    
}
