package AI.Assignment1.UI;

import AI.Assignment1.Algo.GridWorld;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main  extends Application implements EventHandler {

    public static void main(String[] args) {
        launch(args);
    }

    Stage window;
    Scene scene, scene2, scene3, scene4;

    private static final int TILE_SIZE=10;
    private static final int CELLS=50;
    private static final int SIZE=CELLS*TILE_SIZE;
    public static List<List<Tile>> grid = new ArrayList<>();

    Pair<Integer, Integer> initialCell = Pair.of(0,0);
    Pair<Integer, Integer> goalCell = Pair.of(CELLS-1,CELLS-1);

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("AI Assignment 1");
        window.setResizable(true);
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
            vBox.setSpacing(5);
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

    private BorderPane borderPane = new BorderPane();

    public Scene getSecondScene(){
        HBox hbox = addHBox("Repeated AStar Search");
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #991f23;");

//        border.setTop(hbox);
//        border.setCenter(createMaze());

        borderPane.setCenter(createContent());
        borderPane.setBottom(hbox);
        borderPane.setRight(createVBox());
        Scene scene = new Scene(borderPane, SIZE+300, SIZE+300);
        return scene;
    }

    private VBox createVBox(){
        VBox vBox = new VBox();
        {
            vBox.setPadding(new Insets(15, 12, 15, 12));
            vBox.setSpacing(10);
            vBox.setStyle("-fx-background-color: #991f23;");
        }

        Button resetButton = new Button("Reset");
        {
            resetButton.setOnAction(e->{
                grid = new ArrayList<>();
                borderPane.setCenter(createContent());
            });
        }

        TextField blockingProbabilityText = new TextField("0.3");
        blockingProbabilityText.setPrefWidth(40);
        Button autoGenerate = new Button(" Auto Generate");
        {
            autoGenerate.setOnAction(e->{
                // Generate Maze with given blocking probability
                double blockingProbabilityValue = Double.valueOf(blockingProbabilityText.getText());
                autoGenerate(blockingProbabilityValue);
            });
        }

        HBox hBox = new HBox();
        {
            hBox.setPadding(new Insets(15, 12, 15, 12));
            hBox.setSpacing(20);
            hBox.setStyle("-fx-background-color: #991f23;");
            hBox.getChildren().addAll( blockingProbabilityText, autoGenerate);
        }

        vBox.getChildren().addAll(hBox, resetButton);

        return vBox;
    }

    private void autoGenerate(double blockingProbabilityValue){
        IntStream.range(0, CELLS).forEach(i -> {
            IntStream.range(0, CELLS).forEach(j -> {
                Random r = new Random();
                int randomResult = r.nextInt(100);
                if (randomResult <= (int)(blockingProbabilityValue*100)) {
                    grid.get(i).get(j).block();
                }
            });
        });
        setInitAndGoalCell();
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
            try {

                IntStream.range(0, grid.size()).forEach(i -> {
                    IntStream.range(0, grid.size()).forEach(j -> {
                        Text text = grid.get(i).get(j).text;
                        int value=1;
                        if(!text.getText().isEmpty()){
                           value= Test.INFINITY;
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

    private Pane root;

    private Pane createContent(){
        root = new Pane();
        root.setPrefSize(SIZE,SIZE);
        initGrid();
        return root;
    }

    private void initGrid(){
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
        setInitAndGoalCell();
    }

    private void setInitAndGoalCell(){
        grid.get(initialCell.getFirst()).get(initialCell.getSecond()).border.setFill(Color.BLUE);
        grid.get(goalCell.getFirst()).get(goalCell.getSecond()).border.setFill(Color.RED);
    }

    public class Tile extends StackPane{
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

            setOnMouseClicked(e -> flip());
        }

        public void block(){
            if(text.getText().isEmpty()){
                text = new Text();
                text.setText("X");
                border.setFill(Color.BLACK);
            }
        }

        public void flip(){
            if(text.getText().isEmpty()){
                block();
            } else{
                text.setText(null);
                border.setFill(null);
            }
        }

        public void changeColor(Paint color){
            if(text.textProperty().getValue().isEmpty())
                border.setFill(color);
        }
    }
}
