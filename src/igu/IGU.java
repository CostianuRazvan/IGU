/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import javafx.collections.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Razvan
 */
public class IGU extends Application {

    Boolean change = false;
    ProgressBar progressBar;
    ProgressIndicator progressIndicator;

    @Override
    public void start(Stage primaryStage) {
        StackPane parent = new StackPane();
        ImageView image = new ImageView();
        image.setFitHeight(200);
        image.setFitWidth(200);

        VBox container = new VBox();
        container.setPadding(new Insets(30, 30, 30, 30));
        container.setAlignment(Pos.TOP_LEFT);
        container.setSpacing(200);
        HBox centerContainer = new HBox();
        centerContainer.setAlignment(Pos.TOP_LEFT);
        centerContainer.setSpacing(400);
        VBox leftCenterContainer = new VBox();
        leftCenterContainer.setSpacing(100);

        progressBar = new ProgressBar();
        progressBar.setVisible(false);
        leftCenterContainer.getChildren().add(progressBar);

        RadioButton radioButton = new RadioButton();
        radioButton.setText("max");
        radioButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if (!change) {
                    changeBrightnes(100, image);
                } else {
                    changeBrightnes(0, image);
                }
                change = !change;
            }
        });
        leftCenterContainer.getChildren().add(radioButton);

        Hyperlink hiperlink = new Hyperlink();
        hiperlink.setText("http://www.aii.pub.ro/");
        hiperlink.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(URI.create(hiperlink.getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        leftCenterContainer.getChildren().add(hiperlink);

        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setItems(FXCollections.observableArrayList("0%", "100% "));
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue.toString().equals("0")) {
                    changeBrightnes(0, image);
                } else {
                    changeBrightnes(100, image);
                }

            }

        });

        leftCenterContainer.getChildren().add(choiceBox);

        ListView listView = new ListView();
        listView.setItems(FXCollections.observableArrayList("0%", "100% "));
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue.toString().equals("0")) {
                    changeBrightnes(0, image);
                } else {
                    changeBrightnes(100, image);
                }

            }

        });

        leftCenterContainer.getChildren().add(listView);

        centerContainer.getChildren().add(leftCenterContainer);

        VBox cContainer = new VBox();
        cContainer.setSpacing(200);

        cContainer.getChildren().add(image);

        Label label = new Label();
        label.setText("Image Brightness modification");
        cContainer.getChildren().add(label);

        Button generateButton = new Button();
        generateButton.setText("Genereaza");
        generateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showImagePre(image);
            }
        });
        cContainer.getChildren().add(generateButton);
        centerContainer.getChildren().add(cContainer);

        VBox rightContainer = new VBox();
        rightContainer.setSpacing(200);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        rightContainer.getChildren().add(progressIndicator);

        CheckBox checkBox = new CheckBox();
        checkBox.setText("Max");
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                if (new_val) {
                    changeBrightnes(100, image);
                } else {
                    changeBrightnes(0, image);
                }
            }
        });

        rightContainer.getChildren().add(checkBox);

        HBox bottomRight = new HBox();
        bottomRight.setSpacing(10);

        TextField textField = new TextField();
        bottomRight.getChildren().add(textField);

        Button exportButton = new Button();
        exportButton.setText("Exporta");

        exportButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    File input = new File("C:\\Users\\Razvan\\Documents\\NetBeansProjects\\IGU\\src\\igu\\sources\\output.jpg");
                    File output = new File("C:\\Users\\Razvan\\Desktop\\" + textField.getText() + ".jpg");
                    FileChannel src = new FileInputStream(input).getChannel();
                    FileChannel dest = new FileOutputStream(output).getChannel();

                    dest.transferFrom(src, 0, src.size());
                } catch (IOException ex) {
                    Logger.getLogger(IGU.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bottomRight.getChildren().add(exportButton);

        rightContainer.getChildren().add(bottomRight);

        centerContainer.getChildren().add(rightContainer);

        container.getChildren().add(centerContainer);

        parent.getChildren().add(container);

        Scene scene = new Scene(parent, 1600, 800);
        primaryStage.setTitle("Image Brightness modification");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public void showImagePre(ImageView iv) {
        File input = new File("C:\\Users\\Razvan\\Documents\\NetBeansProjects\\IGU\\src\\igu\\sources\\input.jpeg");
        Image image = new Image(input.toURI().toString());
        iv.setImage(image);

    }

    public void changeBrightnes(int factor, ImageView iv) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisible(true);
                progressIndicator.setVisible(true);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IGU.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    File input = new File("C:\\Users\\Razvan\\Documents\\NetBeansProjects\\IGU\\src\\igu\\sources\\input.jpeg");
                    File output = new File("C:\\Users\\Razvan\\Documents\\NetBeansProjects\\IGU\\src\\igu\\sources\\output.jpg");
                    BufferedImage picture1 = ImageIO.read(input);   // original
                    BufferedImage picture2 = new BufferedImage(picture1.getWidth(), picture1.getHeight(), BufferedImage.TYPE_INT_RGB);
                    int width = picture1.getWidth();
                    int height = picture1.getHeight();

                    for (int y = 0; y < height; y++) {//loops for image matrix
                        for (int x = 0; x < width; x++) {

                            Color c = new Color(picture1.getRGB(x, y));

                            //adding factor to rgb values
                            int r = c.getRed() + factor;
                            int b = c.getBlue() + factor;
                            int g = c.getGreen() + factor;
                            if (r >= 256) {
                                r = 255;
                            } else if (r < 0) {
                                r = 0;
                            }

                            if (g >= 256) {
                                g = 255;
                            } else if (g < 0) {
                                g = 0;
                            }

                            if (b >= 256) {
                                b = 255;
                            } else if (b < 0) {
                                b = 0;
                            }
                            picture2.setRGB(x, y, new Color(r, g, b).getRGB());

                        }
                    }
                    ImageIO.write(picture2, "jpg", output);
                    Image image = new Image(output.toURI().toString());
                    iv.setImage(image);
                    progressBar.setVisible(false);
                    progressIndicator.setVisible(false);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        });
        t.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
