package AI.Assignment1.UI;

import AI.Assignment1.Algo.GridWorld;
import AI.Assignment1.Entity.XY;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static AI.Assignment1.Utility.Constants.Constant.INFINITY;

@SpringBootApplication
public class MainScreen extends Application{

    Stage window;
    Scene mainScene, secondScene;

    private static final int TILE_SIZE=40;
    private static final int CELLS=10;
    private static final int SIZE=CELLS*TILE_SIZE;

    // Independent Grids for all possible searches
    public static List<List<Tile>> grid, gridForward, gridBackward, gridAdaptive = new ArrayList<>();

    // Default Initial and Goal
    public XY initialCell = new XY(0,0);
    public XY goalCell = new XY(CELLS-1,CELLS-1);
    private BorderPane borderPane = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        window.setTitle("AI Assignment 1");
        window.setResizable(false);
        createMainScene();
        window.setScene(mainScene);
        window.show();
    }

    private void createMainScene(){

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
            label.setText("By, Dhiraj Gandhi and Harsh Bhatt");
            label.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Button button = new Button("Next ->");
            button.setOnAction(e -> {
                createSecondScene();
                window.setScene(secondScene);
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

        mainScene = new Scene(borderPane, 500, 500);
    }

    private void createSecondScene(){

        HBox bottomTitleHBox = addHBox("Repeated AStar Search");
        {
            bottomTitleHBox.setPadding(new Insets(15, 12, 15, 12));
            bottomTitleHBox.setSpacing(10);
            bottomTitleHBox.setStyle("-fx-background-color: #991f23;");
        }

        {
            borderPane.setTop(null);
            borderPane.setCenter(createContent());
            borderPane.setBottom(bottomTitleHBox);
            borderPane.setRight(createSearchOptionsVBox());
        }

        secondScene = new Scene(borderPane, SIZE+300, SIZE+100);
    }

    private VBox createSearchOptionsVBox(){
        VBox vBox = new VBox();
        {
            vBox.setPadding(new Insets(15, 12, 15, 12));
            vBox.setSpacing(10);
            vBox.setStyle("-fx-background-color: #991f23;");
        }

        TextField blockingProbabilityText = new TextField("0.3");
        blockingProbabilityText.setPrefWidth(40);
        Button autoGenerate = new Button("Auto Generate Blocking");
        {
            autoGenerate.setOnAction(e->{
                // Generate Maze with given blocking probability
                double blockingProbabilityValue = Double.parseDouble(blockingProbabilityText.getText());
                autoGenerateGridWorld(blockingProbabilityValue);
            });
        }

        HBox hBox = new HBox();
        {
            hBox.setPadding(new Insets(15, 12, 15, 12));
            hBox.setSpacing(20);
            hBox.setStyle("-fx-background-color: #991f23;");
            hBox.getChildren().addAll( blockingProbabilityText, autoGenerate);
        }

        ComboBox<String> comboBox = new ComboBox<>();
        {
            comboBox.getItems().addAll(
                    "Forward",
                    "Backward",
                    "Adaptive"
            );
            comboBox.getSelectionModel().selectFirst();
        }

        vBox.getChildren().addAll(hBox, comboBox);
        return vBox;
    }

    private void autoGenerateGridWorld(double blockingProbabilityValue){
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
        {
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(10);
            hbox.setStyle("-fx-background-color: #991f23;");
        }

        Button startButton = new Button("Search");
        {
            startButton.setOnAction(e-> {
                gridWorld = new GridWorld(1,grid.size(),grid.size());
                try {

                    IntStream.range(0, grid.size()).forEach(i -> {
                        IntStream.range(0, grid.size()).forEach(j -> {
                            Text text = grid.get(i).get(j).text;
                            int value=1;
                            if(!text.getText().isEmpty()){
                                value= INFINITY;
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
        }

        Button resetButton = new Button("Reset");
        {
            resetButton.setOnAction(e->{
                grid = new ArrayList<>();
                borderPane.setCenter(createContent());
            });
            resetButton.setPrefSize(100, 20);
        }

        Text title = new Text(titleString);
        {
            title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        }

        hbox.getChildren().addAll(title, startButton, resetButton);
        return hbox;
    }



    public BorderPane addBorderPane(String titleString){
        BorderPane border = new BorderPane();
        HBox hbox = addHBox(titleString);
        border.setTop(hbox);

        border.setCenter(addGridPane("hey"));
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

    private Pane root;

    private Pane createContent(){
        root = new Pane();
        root.setPrefSize(SIZE,SIZE);
        initGrid();
        return root;
    }

    private void initGrid(){

        grid = new ArrayList<>();

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
        grid.get(initialCell.getX()).get(initialCell.getY()).border.setFill(Color.BLUE);
        grid.get(goalCell.getX()).get(goalCell.getY()).border.setFill(Color.RED);
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
            if(text.textProperty().getValue().isEmpty()){
                border.setFill(null);
                border.setFill(color);
            }

        }

        public Text getText() {
            return text;
        }

        public void setText(Text text) {
            this.text = text;
        }
    }
}
