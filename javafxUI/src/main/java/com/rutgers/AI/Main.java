package com.rutgers.AI;

import AI.Assignment1.Algo.GridWorld;
import com.sun.deploy.panel.TextFieldProperty;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.rutgers.AI.Test.INFINITY;

public class Main  extends Application implements EventHandler {

    public static void main(String[] args) {
        launch(args);
    }

    Stage window;
    Scene scene, scene2, scene3, scene4;


    private static final int TILE_SIZE=50;
    private static final int CELLS=5;
    private static final int SIZE=CELLS*TILE_SIZE;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("AI Assignment 1");
        window.setResizable(false);
        HBox hBox = new HBox();
        {
            hBox.setStyle("-fx-background-color: #991f23;");

            Text title = new Text("Welcome to AI assignment #1");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            hBox.getChildren().addAll(title);
            hBox.setAlignment(Pos.CENTER);
            hBox.setMinHeight(100);
        }

        VBox vBox = new VBox();
        {
            Label label = new Label();
            label.setText("By, Dhiraj and Harsh");
            label.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Button button = new Button("Next ->");
            button.setOnAction(e -> {
                window.setScene(getSecondScene());
            });
            vBox.getChildren().addAll(label, button);
            vBox.setSpacing(50);
            vBox.setAlignment(Pos.CENTER);
        }

        BorderPane borderPane = new BorderPane();
        {
            borderPane.setTop(hBox);
            borderPane.setCenter(vBox);
        }

        scene = new Scene(borderPane, 500, 500);
        window.setScene(scene);
        window.show();
    }

    public Scene getSecondScene(){
        BorderPane border = new BorderPane();
        HBox hbox = addHBox("Repeated AStar Search");
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #991f23;");

//        border.setTop(hbox);
//        border.setCenter(createMaze());

        border.setTop(createContent());
        border.setBottom(hbox);
        Scene scene = new Scene(border, SIZE+100, SIZE+100);
        return scene;
    }

//    private GridPane createMaze(){
//        int rows = CELLS;
//        int columns = CELLS;
//        int cellSize = TILE_SIZE;
//
//        GridPane grid = new GridPane();
//        for(int i = 0; i < columns; i++) {
//            ColumnConstraints column = new ColumnConstraints(cellSize);
//            grid.getColumnConstraints().add(column);
//        }
//
//        for(int i = 0; i < rows; i++) {
//            RowConstraints row = new RowConstraints(cellSize);
//            grid.getRowConstraints().add(row);
//        }
//
//        grid.setOnMouseReleased(new EventHandler<MouseEvent> () {
//            public void handle(MouseEvent me) {
//                int x = (int)((me.getSceneX() - (me.getSceneX() % cellSize)) / cellSize);
//                int y = (int)((me.getSceneY() - (me.getSceneY() % cellSize)) / cellSize);
//                Pair A = new Pair(x,y);
//                System.out.println(A);
//            }
//        });
//
//        grid.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
//        grid.setAlignment(Pos.CENTER);
//
//        return grid;
////        Scene scene = new Scene(grid, (columns * 40) + 100, (rows * 40) + 100, Color.WHITE);
//    }

    GridWorld gridWorld;

    public HBox addHBox(String titleString) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #991f23;");

        Button startButton = new Button("Start");

        startButton.setOnAction(e-> {
            gridWorld = new GridWorld(1,grid.size(),grid.size());
//            new Test().run();
            try {

                IntStream.range(0, grid.size()).forEach(i -> {
                    IntStream.range(0, grid.size()).forEach(j -> {
                        Text text = grid.get(i).get(j).text;
                        int value=1;
                        if(!text.getText().isEmpty()){
                           value=INFINITY;
                        }
                        ((List)gridWorld.getGridWorld().get(i)).set(j,value);
                    });
                });

                new Test().repeatedForwardAStarSearch(gridWorld);
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        });
        startButton.setPrefSize(100, 20);

        Text title = new Text(titleString);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        hbox.getChildren().addAll(title, startButton);

        return hbox;
    }

    public BorderPane addBorderPane(String titleString){
        BorderPane border = new BorderPane();
        HBox hbox = addHBox(titleString);
        border.setTop(hbox);
//        border.setLeft(addVBox());
//        addStackPane(hbox);         // Add stack to HBox in top region

        border.setCenter(addGridPane("hey"));
//        border.setRight(addFlowPane());
        return border;
    }

    public GridPane addGridPane(String titleString) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // Category in column 2, row 1
        Text title = new Text(titleString);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(title, 0, 0);

        // Title in column 3, row 1
        Text cost = new Text("Cost:");
        cost.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(cost, 1, 1);

        // Title in column 3, row 1
        Text expandedNodes = new Text("Expanded Nodes:");
        expandedNodes.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(expandedNodes, 1, 2);

        // Title in column 3, row 1
        Text runtime = new Text("Run Time:");
        runtime.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(runtime, 1, 3);

        return grid;
    }


    public void showNewWindow(){
        window = new Stage();
        scene2 = new Scene(addBorderPane("Repeated Forward A*"), 1000, 1000);
        window.setScene(scene2);
        window.show();
    }

    @Override
    public void handle(Event event) {

    }

    private List<List<Tile>> grid = new ArrayList<>();

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(SIZE,SIZE);
//        root.setStyle("-fx-background-color: rgba(0,0,0,0.99);");

        grid = new ArrayList<>();

        // Deep Copy GridWorld to VisitedWorld
        IntStream.range(0, CELLS).forEach(i -> {
            grid.add(new ArrayList<>());
            IntStream.range(0, CELLS).forEach(j -> {
                Tile tile = new Tile(j,i);
                grid.get(i).add(tile);
                root.getChildren().add(tile);
            });
        });

        return root;
    }

    private class Tile extends StackPane{
        private int x,y;
        private Rectangle border = new Rectangle(TILE_SIZE-2, TILE_SIZE-2);
        private Text text = new Text();

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
            border.setStroke(Color.BLACK);
            border.setFill(null);
            text.setText(null);

            getChildren().addAll(border,text);
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> block());
        }

        private void block(){
            if(text.getText().isEmpty()){
                text = new Text();
                text.setText("X");
                border.setFill(Color.BLACK);

            }
        }

    }
}
