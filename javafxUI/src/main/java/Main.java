import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class Main extends Application implements EventHandler {

    Button button, button2, button3, button4, button5, button6;
    Stage window;
    Scene scene, scene2;


    private void incr(){
//        int j=0;
//        while(j<1000) {
//            i.setValue(i.getValue()+1);
//            j++;
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("AI Assignment 1");

        button = new Button();
        button.setText("Generate Maze");
        button.setOnAction(new ButtonHandler());

        button2 = new Button();
        button2.setText("Run A*");
        button2.setOnAction(this);


        button3 = new Button();
        button3.setText("Run A*...............");
        button3.setOnAction(e ->  {
                System.out.println("Hey Baby!");
        });

        Stage secondStage = new Stage();


        button5 = new Button();
        button5.setText("Like");
        button5.setOnAction(e ->  {
            System.out.println("Liked");
            if( secondStage.isShowing()==false){
                AlertBox.display("Alert box", "This is awesome ");
                secondStage.setScene(new Scene(new HBox(4, new Label("Second window"))));
                secondStage.show();
            }

        });

        Label label = new Label("Welcome to AI assignment ");
        button4 = new Button("Go to Scene2");

        Label label1 = new Label("This will change");

        button4.setOnAction(e -> {
            window.setScene(scene2);
        });

        button6 = new Button("Start Counter");
        button6.setOnAction(e -> { incr();
        });

//        label1.textProperty().bind(i.);
        HBox layout = new HBox();
        layout.getChildren().add(button);
        layout.getChildren().add(button2);
        layout.getChildren().add(button3);
        layout.getChildren().add(button5);

        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(label, button4, label1, button6);
        Scene scene = new Scene(layout2, 300, 250);
        window.setScene(scene);

        scene2 = new Scene(layout, 1000, 500);
        window.show();
    }

    @Override
    public void handle(Event event) {
        if(event.getSource()==button2){
            System.out.println("Running A*");
        }
    }
}
