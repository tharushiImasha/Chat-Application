package lk.ijse.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientChatController {
    @FXML
    private Pane imgPane;

    @FXML
    private Label lblName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView selectedImage;

    @FXML
    private TextField txtMsg;

    @FXML
    private VBox vBox;

    private File file;
    private BufferedReader bufferedReader;
    private static PrintWriter writer;
    private String finalName;
    private static String userName;

    public void initialize() {
        lblName.setText(LoginController.name);
        userName = LoginController.name;

        System.out.println(userName);

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",3002);

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(),true);

                writer.println("joi"+userName+"~joining");

                while (true){
                    //reading response
                    String receive = bufferedReader.readLine();
                    String[] split = receive.split("~");
                    String name = split[0];
                    String message = split[1];

                    //find which type of message is came
                    String firstChars = "";
                    if (name.length() > 3) {
                        firstChars = name.substring(0, 3);
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        String[] imgs = name.split("img");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("joi")){
                        String[] imgs = name.split("joi");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("lef")){
                        String[] imgs = name.split("lef");
                        finalName = imgs[1];
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        if (finalName.equalsIgnoreCase(userName)){

                            //adding image to message
                            File receiveFile = new File(message);
                            Image image = new Image(receiveFile.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                selectedImage.setImage(imageView.getImage());
                                imgPane.setVisible(true);
                            });
                            //adding sender to message
                            Text text = new Text("\n");
                            //text.getStyleClass().add("send-text");

                            //add time
                            /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                            LocalDateTime now = LocalDateTime.now();
                            Text time = new Text(dtf.format(now));
                            time.getStyleClass().add("time-text");
                            HBox timeBox = new HBox();
                            timeBox.getChildren().add(time);
                            timeBox.setAlignment(Pos.BASELINE_RIGHT);*/

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("send-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            StackPane stackPane = new StackPane(hBox);
                            stackPane.setAlignment(Pos.CENTER_RIGHT);

                            //adding message to message area
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(stackPane);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setAlignment(Pos.CENTER_LEFT);
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else {
                            //adding image to message
                            File receives = new File(message);
                            Image image = new Image(receives.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                selectedImage.setImage(imageView.getImage());
                                imgPane.setVisible(true);
                            });

                            //adding sender to message
                            Text text = new Text("\n   "+finalName+" - ");
                            text.getStyleClass().add("receive-text");

                            //add time
                            /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                            LocalDateTime now = LocalDateTime.now();
                            Text time = new Text(dtf.format(now));
                            time.getStyleClass().add("time-text");
                            HBox timeBox = new HBox();
                            timeBox.getChildren().add(time);
                            timeBox.setAlignment(Pos.BASELINE_RIGHT);*/

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("receive-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            //adding message to message area
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(hBox);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else if(firstChars.equalsIgnoreCase("joi")) {
                        if (finalName.equalsIgnoreCase(userName)){

                            //adding name of client which join the chat
                            Label text = new Label("You have join the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else{
                            Label text = new Label(finalName+" has join the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else{
                        if(name.equalsIgnoreCase(userName)){

                            Thread receiveThread = new Thread(() -> {

                                Label text = new Label();
                                text.setText("   " + message + "   ");
                                text.setMinWidth(70);
                                text.maxHeight(100);
                                text.setMaxWidth(300);

                                //text.setMaxHeight(300);
                                text.setWrapText(true);

                                text.setStyle("-fx-background-color: #D3D3D3;  -fx-background-radius: 10;");
                                text.prefHeight(43.0);
                                text.prefWidth(98.0);
                                text.setPadding(new Insets(8));

                                final Group root = new Group();

                                final GridPane gridpane = new GridPane();
                                gridpane.setPadding(new Insets(5));
                                gridpane.setHgap(10);
                                gridpane.setVgap(10);
                                gridpane.minHeight(30);
                                gridpane.maxHeight(200);

                                GridPane.setHalignment(text, HPos.CENTER);
                                gridpane.add(text, 0, 0);
                                gridpane.setAlignment(Pos.CENTER_RIGHT);

                                Platform.runLater(() -> {
                                    vBox.getChildren().add(gridpane);
                                });

                            });
                            receiveThread.start();

                        }else {
                            Thread receiveThread = new Thread(() -> {

                            Platform.runLater(() -> {

                                Label text = new Label();
                                text.setText(" "+name +" -  " + message + "   ");
                                text.setMinWidth(70);
                                text.setMaxWidth(300);

                                //text.setMaxHeight(300);
                                text.setWrapText(true);

                                text.setStyle("-fx-background-color: #BAD9EC;  -fx-background-radius: 10;");
                                text.prefHeight(43.0);
                                text.prefWidth(98.0);
                                text.setPadding(new Insets(8));

                                final Group root = new Group();

                                final GridPane gridpane = new GridPane();
                                gridpane.setPadding(new Insets(5));
                                gridpane.setHgap(10);
                                gridpane.setVgap(10);
                                gridpane.minHeight(30);
                                text.maxHeight(100);
                                gridpane.maxHeight(200);

                                GridPane.setHalignment(text, HPos.CENTER);
                                gridpane.add(text, 0, 0);
                                gridpane.setAlignment(Pos.CENTER_LEFT);

                                root.getChildren().add(gridpane);

                                vBox.getChildren().add(gridpane);

                                txtMsg.clear();

                            });
                        });
                            receiveThread.start();
                        }
                    }
                    file = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void backOnAction(MouseEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void emojiOnAction(MouseEvent event) {

    }

    @FXML
    void photosOnAction(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the image");
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(txtMsg.getScene().getWindow());
        if (file != null){
            txtMsg.setText("image selected");
            txtMsg.setEditable(false);
        }
    }

    @FXML
    void sendOnAction(MouseEvent event) {
        if (!txtMsg.getText().isEmpty()){
            if (file != null){
                writer.println("img"+lblName.getText()+"~"+file.getPath());
            }else {
                writer.println(lblName.getText() + "~" + txtMsg.getText());
            }
            txtMsg.setEditable(true);
            txtMsg.clear();
        }
    }

}
