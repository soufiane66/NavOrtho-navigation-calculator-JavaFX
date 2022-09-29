package Interface.Main;


import OrthoClasses.*;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import pdfViewer.PDFFXMLController;
import zoomAndDragge.PanAndZoomPane;
import zoomAndDragge.SceneGestures;
import zunayedhassan.AnimateFX.FlashAnimation;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller implements Initializable {

    DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    ImageView triangle_view;


    @FXML
    DatePicker datePicker;
    // heure de depart
    @FXML
    TextField depart_hour;
    @FXML
    TextField depart_min;


    //le point de depart
    @FXML
    TextField depart_lat_dgr;
    @FXML
    TextField depart_lat_min;

    @FXML
    TextField depart_lon_dgr;
    @FXML
    TextField depart_lon_min;

    //le point d'arrivee
    @FXML
    TextField arrive_lat_dgr;

    @FXML
    TextField arrive_lat_min;

    @FXML
    TextField arrive_lon_dgr;
    @FXML
    TextField arrive_lon_min;

    //Distance Orthodromique
    @FXML
    TextField distanceOrho;

    @FXML
    TextField distanceLoxo;
    @FXML
    TextField gain_tf;

    @FXML
    TextField initialRoute;

    @FXML
    private TextField finalRoute;

    @FXML
    TextArea coordinateVertx;

    @FXML
    TextField routeLoxo;

    @FXML
    TextArea jalonnement;

    @FXML
    JFXSlider vitesse_slider;

    @FXML
    ChoiceBox<Character> depart_Lat_Sign;


    @FXML
    ChoiceBox<Character> depart_Lon_Sign;

    @FXML
    ChoiceBox<Character> arrive_Lat_Sign;

    @FXML
    ChoiceBox<Character> arrive_Lon_Sign;

    @FXML
    JFXSlider interval_slider;
    @FXML
    JFXSlider vitesseLoxo_slider;


    @FXML
    Button calcul_btn;

    @FXML
    TextField arrivalDateOrtho;
    @FXML
    TextField arrivalDateLoxo;

    @FXML
    TextArea result_text_area;

    @FXML
    Label labelHourWarning;
    @FXML
    Label labelMinutesWarning;
    @FXML
    Label labelLatStaDegWaring;
    @FXML
    Label labelLatStaMinWaring;
    @FXML
    Label labelLongStaDegWaring;
    @FXML
    Label labelLongStaMinWaring;

    @FXML
    Label labelLatArrDegWaring;
    @FXML
    Label labelLatArrMinWaring;
    @FXML
    Label labelLongArrDegWaring;
    @FXML
    Label labelLongArrMinWaring;

    @FXML
    ComboBox<String> coursesCB;

    @FXML
    SplitPane main_splitPane;

    @FXML
    ImageView minimize_iv;

    @FXML
    ImageView maximize_iv;

    @FXML
    Label about_label;
    @FXML
    TextField interval_label;
    @FXML
    TextField  V_Loxo_label;
    @FXML
    TextField Vs_label;

    @FXML
    Circle arrive_circle;
    @FXML
    Circle start_circle;
    @FXML
    Circle vertex_circle;

    //make map pannable
    AnchorPane anchorPane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    Group group;
    @FXML
    Pane draggablePane;
    @FXML
    StackPane stackPane;

    @FXML
    Canvas canvas;

    @FXML
    Button resetScale;

    @FXML
    TextField interval_M1_tv,vitesse_M1_tv;

    @FXML
    Slider interval_M1_slider,vitesse_M1_slider;


    public int getDepartLatSinge() {
        int latSing = 0;
        if (depart_Lat_Sign.getValue() == 'N') {
            latSing = 1;
        } else if (depart_Lat_Sign.getValue() == 'S') {
            latSing = -1;
        }

        return latSing;

    }

    public int getArriveLatSinge() {
        int latSing = 0;
        if (arrive_Lat_Sign.getValue() == 'N') {
            latSing = 1;
        } else if (arrive_Lat_Sign.getValue() == 'S') {
            latSing = -1;
        }

        return latSing;

    }

    public int getDepartLonSinge() {
        int latSing = 0;
        if (depart_Lon_Sign.getValue() == 'E') {
            latSing = 1;
        } else if (depart_Lon_Sign.getValue() == 'W') {
            latSing = -1;
        }

        return latSing;

    }

    public int getArriveLonSinge() {
        int latSing = 0;
        if (arrive_Lon_Sign.getValue() == 'E') {
            latSing = 1;
        } else if (arrive_Lon_Sign.getValue() == 'W') {
            latSing = -1;
        }

        return latSing;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        draggablePane.setOnMouseClicked(event -> {
            System.out.println("X : "+event.getX());
            System.out.println("Y : "+event.getY());
        });


        //Make Pane Zoomable and Draggable
        makePanePannable();

        // Initialize and Reformatting datePicker;
        initializeAndReformatDatePicker();

        //initializeSliders
        initSliders();

        //initialize choice box of coordinates Signs
        initializeChoiceBox();

        //Create Documents with choiceBox
        createDocument();

        //Initialize a Vertical SplitPane
        initializeSplitPane();

        //Create About Application
        createAbout();


        //initialize draggable circles
        makeCircleDraggable();





    }

    public void initializeStartCoordinates(LATITUDE lat,LONGITUDE lon){
        depart_lat_dgr.setText(""+lat.getAngle().getDegree());
        depart_lat_min.setText(""+lat.getAngle().getMinute());
        depart_lon_dgr.setText(""+lon.getAngle().getDegree());
        depart_lon_min.setText(""+lon.getAngle().getMinute());
        depart_Lat_Sign.setValue(lat.getLat_char());
        depart_Lon_Sign.setValue(lon.getLon_char());
    }

    public void initializeArriveCoordinates(LATITUDE lat,LONGITUDE lon){
        arrive_lat_dgr.setText(""+lat.getAngle().getDegree());
        arrive_lat_min.setText(""+lat.getAngle().getMinute());
        arrive_lon_dgr.setText(""+lon.getAngle().getDegree());
        arrive_lon_min.setText(""+lon.getAngle().getMinute());
        arrive_Lat_Sign.setValue(lat.getLat_char());
        arrive_Lon_Sign.setValue(lon.getLon_char());
    }

    public void initializeAndReformatDatePicker(){

         Locale myLocale = Locale.getDefault(Locale.Category.FORMAT);
        datePicker.setValue(LocalDate.now());
        datePicker.setOnShowing(event -> Locale.setDefault(Locale.Category.FORMAT, Locale.FRANCE));
        datePicker.setOnShown(event -> Locale.setDefault(Locale.Category.FORMAT, myLocale));


        datePicker.setConverter(new StringConverter<LocalDate>()
        {
            private final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });

    }

    public void initSliders(){
        vitesse_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Vs_label.setText(df.format(newValue));
        });

        interval_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            interval_label.setText(df.format(newValue));
        });

        vitesseLoxo_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            V_Loxo_label.setText(df.format(newValue));
        });

        interval_M1_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            interval_M1_tv.setText(df.format(newValue));
        });

        vitesse_M1_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            vitesse_M1_tv.setText(df.format(newValue));
        });


    }

    public void initializeChoiceBox(){
        ObservableList<Character> lat_Sign_list = FXCollections.observableArrayList('N', 'S');
        ObservableList<Character> lon_Sign_list = FXCollections.observableArrayList('E', 'W');

        depart_Lat_Sign.setItems(lat_Sign_list);
        depart_Lat_Sign.setValue('N');

        arrive_Lat_Sign.setItems(lat_Sign_list);
        arrive_Lat_Sign.setValue('N');

        depart_Lon_Sign.setItems(lon_Sign_list);
        depart_Lon_Sign.setValue('W');

        arrive_Lon_Sign.setItems(lon_Sign_list);
        arrive_Lon_Sign.setValue('W');
    }

    public void initializeSplitPane(){

        minimize_iv.setOpacity(0.5);
        minimize_iv.setDisable(true);

        main_splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            if (oldValue.doubleValue()>=0 && oldValue.doubleValue()<=0.97){
                minimize_iv.setOpacity(1);
                minimize_iv.setDisable(false);
            }else if(oldValue.doubleValue()<=0.99)
            {
                minimize_iv.setOpacity(0.5);
                minimize_iv.setDisable(true);
            }

            if(oldValue.doubleValue()<=0.005){
                maximize_iv.setOpacity(0.5);
                maximize_iv.setDisable(true);
            }
        });

        minimize_iv.setOnMouseClicked(event -> {
            main_splitPane.setDividerPosition(0,1);

            minimize_iv.setOpacity(0.5);
            minimize_iv.setDisable(true);
            maximize_iv.setOpacity(1);
            maximize_iv.setDisable(false);
        });

        maximize_iv.setOnMouseClicked(event -> {
            main_splitPane.setDividerPosition(0,0);

            maximize_iv.setOpacity(0.5);
            maximize_iv.setDisable(true);
            minimize_iv.setOpacity(1);
            minimize_iv.setDisable(false);
        });
    }

    public void createDocument(){

        //Configuration of Choice box
        ObservableList<String> courses_list = FXCollections.observableArrayList("Orthodromie","Trigonométrie Sphérique","Distance Orthodomie",
                "Gain","Angel Intiale","Coordonies Du Vertex");
        coursesCB.setItems(courses_list);
        coursesCB.setValue("Orthodromie");

        coursesCB.valueProperty().addListener((obs, oldItem, newItem) -> {

            System.out.println("new Item : "+newItem);
            System.out.println("old Item : "+oldItem);
            System.out.println("obs Item : "+obs);


            switch (newItem) {
                case "Trigonométrie Sphérique":

                    try {
                        openPdfViewer("src\\pdfViewer\\pdfFiles\\Orthodromie.pdf");
                    } catch (IOException e) {
                        System.out.println("error openPdfViewer");
                    }

                    break;
                case "Distance Orthodomie":
                    try {
                        openPdfViewer("src/pdfViewer/pdfFiles/distance_ortho.pdf");
                    } catch (IOException e) {
                        System.out.println("error openPdfViewer");
                    }

                    break;
                case "Angel Intiale":
                    try {
                        openPdfViewer("src/pdfViewer/pdfFiles/angle_intiale.pdf");
                    } catch (IOException e) {
                        System.out.println("error openPdfViewer");
                    }

                    break;
                case "Gain":
                    try {
                        openPdfViewer("src/pdfViewer/pdfFiles/gain.pdf");
                    } catch (IOException e) {
                        System.out.println("error openPdfViewer");
                    }

                    break;
                case "Coordonies Du Vertex":

                    try {
                        openPdfViewer("src/pdfViewer/pdfFiles/vertex.pdf");
                    } catch (IOException e) {
                        System.out.println("error openPdfViewer");
                    }

                    break;
            }
        });

        coursesCB.addEventHandler(MouseEvent.MOUSE_RELEASED,event -> coursesCB.setValue("Orthodromie"));

    }

    public void createAbout(){
        about_label.setOnMouseClicked(event -> {

            try {
                AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("aboutView.fxml")));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initOwner(MainActivity.stage);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void makeCircleDraggable(){
        DraggableNode circle_start = new DraggableNode(start_circle);
        DraggableNode circle_arrive = new DraggableNode(arrive_circle);

        arrive_circle.addEventHandler(MouseEvent.MOUSE_DRAGGED,event -> {

            Position arrivePosition = circle_arrive.getNodePosition(arrive_circle);
            Position startPosition = circle_start.getNodePosition(start_circle);
            DrawPath drawPath = new DrawPath(canvas, circle_start.getNodePosition(start_circle), arrivePosition,circle_arrive.getNodePosition(vertex_circle));
            drawPath.draw();
            initializeArriveCoordinates(arrivePosition.getLati(),arrivePosition.getLongi());
            initializeStartCoordinates(startPosition.getLati(),startPosition.getLongi());
            event.consume();

        });

        start_circle.addEventHandler(MouseEvent.MOUSE_DRAGGED,event -> {
            Position arrivePosition = circle_arrive.getNodePosition(arrive_circle);
            Position startPosition = circle_start.getNodePosition(start_circle);
            DrawPath drawPath = new DrawPath(canvas, circle_start.getNodePosition(start_circle), circle_arrive.getNodePosition(arrive_circle),circle_arrive.getNodePosition(vertex_circle));
            drawPath.draw();
            initializeStartCoordinates(startPosition.getLati(),startPosition.getLongi());
            initializeArriveCoordinates(arrivePosition.getLati(),arrivePosition.getLongi());
            event.consume();
        });
    }

    public void makePanePannable(){

        final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
        final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);
        PanAndZoomPane panAndZoomPane;

        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);



        panAndZoomPane = new PanAndZoomPane();
        zoomProperty.bind(panAndZoomPane.myScale);
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(group);

        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

        scrollPane.setContent(panAndZoomPane);
        panAndZoomPane.toBack();

        scrollPane.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrollPane.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrollPane.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrollPane.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        AnchorPane.setLeftAnchor(scrollPane, 0d);
        AnchorPane.setTopAnchor(scrollPane,0d);
        AnchorPane.setRightAnchor(scrollPane,0d);

        resetScale.setOnAction(event -> {

        });

    }

    public void openPdfViewer(String pathPdf) throws IOException {

        PDFFXMLController.pdfPath = pathPdf;
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PdfViewer.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void setCalculate_btn() {


        try {


            LATITUDE fiD = new LATITUDE(new Angle(Integer.parseInt(depart_lat_dgr.getText()), Double.parseDouble(depart_lat_min.getText())), getDepartLatSinge());
            LONGITUDE GD = new LONGITUDE(new Angle(Integer.parseInt(depart_lon_dgr.getText()), Double.parseDouble(depart_lon_min.getText())), getDepartLonSinge());

            LATITUDE fiA = new LATITUDE(new Angle(Integer.parseInt(arrive_lat_dgr.getText()), Double.parseDouble(arrive_lat_min.getText())), getArriveLatSinge());
            LONGITUDE GA = new LONGITUDE(new Angle(Integer.parseInt(arrive_lon_dgr.getText()), Double.parseDouble(arrive_lon_min.getText())), getArriveLonSinge());

            Position D = new Position(fiD, GD);
            Position A = new Position(fiA, GA);


            Time dateDepart = new Time(datePicker.getValue().getYear(), datePicker.getValue().getMonthValue(),
                    datePicker.getValue().getDayOfMonth(), Integer.parseInt(depart_hour.getText()), Integer.parseInt(depart_min.getText()));
            Orthodromie orthodromie = new Orthodromie(dateDepart,Double.parseDouble(Vs_label.getText()), D, A);




            //check if the distance is > 300 miles
            if(orthodromie.distanceLoxo() > 300){
                showResults(orthodromie,D,A);
            }else{

                try {
                    AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("orthWarning.fxml")));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.initOwner(MainActivity.stage);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

       } catch (NumberFormatException | NullPointerException exception) {

            checkWarning();
            System.out.println(exception);

        }


    }

    //show up the results


    public  void showResults(Orthodromie orthodromie,Position D,Position A) {

        distanceOrho.setText("" + orthodromie.distanceOrtho() + " milles");
        distanceLoxo.setText("" + orthodromie.distanceLoxo() + " milles");
        gain_tf.setText(orthodromie.getGain() + " milles");
        initialRoute.setText(orthodromie.angleInitial());
        finalRoute.setText(orthodromie.angleFinale());
        coordinateVertx.setText(orthodromie.coordonnesDeVertex());
        routeLoxo.setText(orthodromie.CalculeRloxo(Double.parseDouble(interval_label.getText()), Double.parseDouble(V_Loxo_label.getText())));

        jalonnement.setText(orthodromie.pointDeJalonnement(Double.parseDouble(interval_M1_tv.getText()), Double.parseDouble(vitesse_M1_tv.getText())));

        arrivalDateOrtho.setText(orthodromie.getArrivalTimeOrtho());
        arrivalDateLoxo.setText(orthodromie.getArrivalTimeLoxo());
        result_text_area.setText(orthodromie.finalResult());

        //Setting up map
        DrawPath drawPath = new DrawPath(canvas, D, A,orthodromie.vertex);
        drawPath.draw();



        start_circle.relocate(longitudeToPixel(D.getLongi()),latitudeToPixel(D.getLati()));
        arrive_circle.relocate(longitudeToPixel(A.getLongi()),latitudeToPixel(A.getLati()));
        //arrive_circle.relocate(-10, -10);





    }

    public double longitudeToPixel(LONGITUDE longitude){
        double lon = longitude.getAngle().toAngle();
        if (longitude.getSign() > 0){
            return (lon * 2.535625) - start_circle.getRadius();

        }
        return (lon * -2.535625) - start_circle.getRadius() ;

    }

    public double latitudeToPixel(LATITUDE latitude){
        double lat = latitude.getAngle().toAngle();
        if (latitude.getSign() > 0){
            return (lat * -2.5383) - start_circle.getRadius();
        }
        return (lat * 2.5383) - start_circle.getRadius();
    }

    //cheek if the value of hour is between 1 and 24
    @FXML
    public void checkHour(KeyEvent event) {


        labelHourWarning.setVisible(false);
        TextField hourField = (TextField) event.getSource();
        hourField.setStyle(null);
        String hour = hourField.getText();

        try {
            int hours = Integer.parseInt(hour);
            if (hours < 0 || hours > 23) {
                hourField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelHourWarning.setVisible(true);
            }
        } catch (NumberFormatException |
                NullPointerException exception) {
            hourField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(hourField);
            flashAnimation.Play();
            labelHourWarning.setVisible(true);
        }

    }

    @FXML
    public void checkMinutes(KeyEvent event) {

        labelMinutesWarning.setVisible(false);
        TextField minuteField = (TextField) event.getSource();
        minuteField.setStyle(null);
        String minute = minuteField.getText();
        try {
            int minutes = Integer.parseInt(minute);
            if (minutes < 0 || minutes > 59) {
                minuteField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelMinutesWarning.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            minuteField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(minuteField);
            flashAnimation.Play();
            labelMinutesWarning.setVisible(true);
        }
    }

    @FXML
    public void checkLatStarDegree(KeyEvent event) {
        labelLatStaDegWaring.setVisible(false);
        TextField degreeField = (TextField) event.getSource();
        degreeField.setStyle(null);
        String degree = degreeField.getText();
        try {
            int degrees = Integer.parseInt(degree);
            if (degrees < 0 || degrees >90) {
                degreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLatStaDegWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            degreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(degreeField);
            flashAnimation.Play();
            labelLatStaDegWaring.setVisible(true);
        }

    }

    @FXML
    public void checkLatStaMinutes(KeyEvent event) {

        labelLatStaMinWaring.setVisible(false);

        TextField latMinute = (TextField) event.getSource();
        latMinute.setStyle(null);
        String minute = latMinute.getText();
        try {
            double minutes = Double.parseDouble(minute);
            if (minutes < 0 || minutes > 59.99) {
                latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLatStaMinWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(latMinute);
            flashAnimation.Play();
            labelLatStaMinWaring.setVisible(true);
        }
    }

    @FXML
    public void checkLonStarDegree(KeyEvent event) {
        labelLongStaDegWaring.setVisible(false);
        TextField latDegreeField = (TextField) event.getSource();
        latDegreeField.setStyle(null);
        String degree = latDegreeField.getText();
        try {
            int degrees = Integer.parseInt(degree);
            if (degrees < 0 || degrees > 180) {
                latDegreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLongStaDegWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            latDegreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(latDegreeField);
            flashAnimation.Play();
            labelLongStaDegWaring.setVisible(true);
        }

    }

    @FXML
    public void checkLonStaMinutes(KeyEvent event) {

        labelLongStaMinWaring.setVisible(false);

        TextField latMinute = (TextField) event.getSource();
        latMinute.setStyle(null);
        String minute = latMinute.getText();
        try {
            double minutes = Double.parseDouble(minute);
            if (minutes < 0 || minutes > 59.99) {
                latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLongStaMinWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(latMinute);
            flashAnimation.Play();
            labelLongStaMinWaring.setVisible(true);
        }
    }


    @FXML
    public void checkLatArrDegree(KeyEvent event)
    {
        labelLatArrDegWaring.setVisible(false);
        TextField degreeField = (TextField) event.getSource();
        degreeField.setStyle(null);
        String degree = degreeField.getText();
        try {
            int degrees = Integer.parseInt(degree);
            if (degrees < 0 || degrees >90) {
                degreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLatArrDegWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            degreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(degreeField);
            flashAnimation.Play();
            labelLatArrDegWaring.setVisible(true);
        }

    }

    @FXML
    public void checkLatArrMinutes(KeyEvent event) {

        labelLatArrMinWaring.setVisible(false);

        TextField latMinute = (TextField) event.getSource();
        latMinute.setStyle(null);
        String minute = latMinute.getText();
        try {
            double minutes = Double.parseDouble(minute);
            if (minutes < 0 || minutes > 59.99) {
                latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLatArrMinWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(latMinute);
            flashAnimation.Play();
            labelLatArrMinWaring.setVisible(true);
        }
    }

    @FXML
    public void checkLonArrDegree(KeyEvent event)
    {
        labelLongArrDegWaring.setVisible(false);
        TextField latDegreeField = (TextField) event.getSource();
        latDegreeField.setStyle(null);
        String degree = latDegreeField.getText();
        try {
            int degrees = Integer.parseInt(degree);
            if (degrees < 0 || degrees > 180) {
                latDegreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLongArrDegWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            latDegreeField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(latDegreeField);
            flashAnimation.Play();
            labelLongArrDegWaring.setVisible(true);
        }

    }

    @FXML
    public void checkLonArrMinutes(KeyEvent event) {

        labelLongArrMinWaring.setVisible(false);

        TextField latMinute = (TextField) event.getSource();
        latMinute.setStyle(null);
        String minute = latMinute.getText();
        try {
            double minutes = Double.parseDouble(minute);
            if (minutes < 0 || minutes > 59.99) {
                latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
                labelLongArrMinWaring.setVisible(true);
            }
        } catch (NumberFormatException | NullPointerException exception) {
            latMinute.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-text-fill: red;");
            FlashAnimation flashAnimation = new FlashAnimation(latMinute);
            flashAnimation.Play();
            labelLongArrMinWaring.setVisible(true);
        }
    }



    public void sliderINpute(KeyEvent keyEvent) {
        TextField textField = ((TextField) keyEvent.getSource());
//        boolean isDepart_hour = ((TextField) keyEvent.getSource()).getId().equals("depart_hour");
//        boolean isDepart_minutes = ((TextField) keyEvent.getSource()).getId().equals("depart_min");

        try {
            textField.setStyle(null);
            double password = Double.parseDouble(textField.getText());
            if(password < 0 )
            {
                textField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                FlashAnimation flashAnimation = new FlashAnimation(textField);
                flashAnimation.Play();
            }


        } catch (NumberFormatException | NullPointerException exception) {
            textField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            FlashAnimation flashAnimation = new FlashAnimation(textField);
            flashAnimation.Play();
        }


    }

    public void checkWarning() {

        for (int i = 0; i < getTextFields().size(); i++) {
            if (getTextFields().get(i).getText().equals("")) {
                getTextFields().get(i).setStyle("-fx-border-color: red; -fx-border-width: 2;");
                FlashAnimation flashAnimation = new FlashAnimation(getTextFields().get(i));
                flashAnimation.Play();
            }
        }


    }

    public ArrayList<TextField> getTextFields() {
        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(depart_hour);
        textFields.add(depart_min);

        textFields.add(depart_lat_dgr);
        textFields.add(depart_lat_min);

        textFields.add(depart_lon_dgr);
        textFields.add(depart_lon_min);

        textFields.add(arrive_lat_dgr);
        textFields.add(arrive_lat_min);

        textFields.add(arrive_lon_dgr);
        textFields.add(arrive_lon_min);

        textFields.add(Vs_label);
        textFields.add(V_Loxo_label);
        textFields.add(interval_label);


        return textFields;
    }


    @FXML
    public void clearAll() {

        for (int i = 0; i < getTextFields().size(); i++) {
            getTextFields().get(i).clear();
        }


    }


}
