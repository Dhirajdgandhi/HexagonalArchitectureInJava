package AI.Assignment1.UI;

import AI.Assignment1.Algo.GridWorld;
import AI.Assignment1.Entity.XY;
import AI.Assignment1.UIApp;
import javafx.application.Application;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static AI.Assignment1.Utility.Constants.Constant.*;

@SpringBootApplication
public class MainScreen extends Application {

    Stage window, forwardWindow, backwardWindow, adaptiveWindow;
    Scene mainScene, secondScene, forwardScene, backwardScene, adaptiveScene;

    private static final int TILE_SIZE = 40;
    private static final int CELLS = 10;
    private static final int SIZE = CELLS * TILE_SIZE;

    // Independent Grids for all possible searches
    private static List<List<Tile>> mainGrid;
    private static List<List<Tile>> gridForward;
    private static List<List<Tile>> gridBackward;
    private static List<List<Tile>> gridAdaptive;

    // Our Search
    private static List<List<Tile>> currentGrid = new ArrayList<>();

    // Default Initial and Goal
    public XY initialCell = new XY(0, 0);
    public XY goalCell = new XY(CELLS - 1, CELLS - 1);
    private BorderPane borderPane = new BorderPane();
    private Pane rootGridPane;
    private Pane forwardGridPane;
    private Pane backwardGridPane;
    private Pane adaptiveGridPane;
    private ComboBox<String> comboBox = new ComboBox<>();
    private Test test;
    private int fCost, bCost, aCost;

    public static void main(String[] args) {
        SpringApplication.run(MainScreen.class);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        test = new Test();
        window = primaryStage;
        window.setTitle("AI Assignment 1");
        window.setResizable(false);
        createMainScene();
        window.setScene(mainScene);
        window.show();
    }

    private void createMainScene() {

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

    private void createSecondScene() {

        HBox bottomTitleHBox = createHBoxForSecondScreen("Repeated AStar Search");
        {
            bottomTitleHBox.setPadding(new Insets(15, 12, 15, 12));
            bottomTitleHBox.setSpacing(10);
            bottomTitleHBox.setStyle("-fx-background-color: #991f23;");
        }

        {
            rootGridPane = new Pane();
            rootGridPane.setPrefSize(SIZE, SIZE);

            forwardGridPane = new Pane();
            forwardGridPane.setPrefSize(SIZE, SIZE);

            backwardGridPane = new Pane();
            backwardGridPane.setPrefSize(SIZE, SIZE);

            adaptiveGridPane = new Pane();
            adaptiveGridPane.setPrefSize(SIZE, SIZE);

            initGrid();
        }

        {
            borderPane.setTop(null);
            borderPane.setCenter(rootGridPane);
            borderPane.setBottom(bottomTitleHBox);
            borderPane.setRight(createSearchOptionsVBox());
        }

        secondScene = new Scene(borderPane, SIZE + 300, SIZE + 100);
    }

    private VBox createSearchOptionsVBox() {
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
            autoGenerate.setOnAction(e -> {
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
            hBox.getChildren().addAll(blockingProbabilityText, autoGenerate);
        }

        {
            comboBox.getItems().addAll(
                    FORWARD, BACKWARD, ADAPTIVE
            );
            comboBox.getSelectionModel().selectFirst();
        }

        vBox.getChildren().addAll(hBox, comboBox);
        return vBox;
    }

    private void autoGenerateGridWorld(double blockingProbabilityValue) {
        IntStream.range(0, CELLS).forEach(i -> {
            IntStream.range(0, CELLS).forEach(j -> {
                Random r = new Random();
                int randomResult = r.nextInt(100);
                if (randomResult <= (int) (blockingProbabilityValue * 100)) {
                    mainGrid.get(i).get(j).block();
                }
            });
        });
        setInitAndGoalCell();
    }

    public HBox createHBoxForSecondScreen(String titleString) {
        HBox hbox = new HBox();
        {
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(10);
            hbox.setStyle("-fx-background-color: #991f23;");
        }

        Button startButton = new Button("Start");
        {
            startButton.setOnAction(e -> {
                // Before search save the gridWorld
//                GridWorld gridWorld = createGridWorldUsingTileGrid();
                //create the dropdown selected screen and open up
                setupScreenForSelectedDropDownValue(comboBox.getValue());
            });
            startButton.setPrefSize(100, 20);
        }

        Button resetButton = new Button("Reset");
        {
            resetButton.setOnAction(e -> {
                reinitializeUnBlockedGrid();
                // Close All search windows if open
                borderPane.setCenter(rootGridPane);
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


    public HBox createHBoxForSearchScreen(String titleString) {
        HBox hbox = new HBox();
        {
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(10);
            hbox.setStyle("-fx-background-color: #991f23;");
        }

        Button startButton = new Button("Search");
        {
            startButton.setOnAction(e -> {
                // Before search save the gridWorld
                GridWorld gridWorld = createGridWorldUsingTileGrid();

                // On Search Button -
                try {
                    fCost = test.repeatedForwardAStarSearch(gridWorld);
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }

//                setupScreenForSelectedDropDownValue(gridWorld, titleString);
            });
            startButton.setPrefSize(100, 20);
        }

        Text title = new Text(titleString);
        {
            title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        }

        hbox.getChildren().addAll(title, startButton);
        return hbox;
    }

    private void setupScreenForSelectedDropDownValue(String type) {
        switch (type) {
            case FORWARD: {
                gridForward = new ArrayList<>();

                IntStream.range(0, CELLS).forEach(i -> {
                    gridForward.add(new ArrayList<>());
                    IntStream.range(0, CELLS).forEach(j -> {
                        Tile tile = null;
                        try {
                            tile = (Tile)mainGrid.get(i).get(j).clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        gridForward.get(i).add(tile);
                        forwardGridPane.getChildren().add(tile);
                    });
                });
                currentGrid = gridForward;

                forwardWindow = new Stage();
                forwardScene = createSearchScreen(FORWARD, forwardGridPane);
                forwardWindow.setScene(forwardScene);
                forwardWindow.show();
                break;
            }
            case BACKWARD: {
                gridBackward = new ArrayList<>();

                IntStream.range(0, CELLS).forEach(i -> {
                    gridBackward.add(new ArrayList<>());
                    IntStream.range(0, CELLS).forEach(j -> {
                        Tile tile = null;
                        try {
                            tile = (Tile)mainGrid.get(i).get(j).clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        gridBackward.get(i).add(tile);
                        backwardGridPane.getChildren().add(tile);
                    });
                });
                currentGrid = gridBackward;

                backwardWindow = new Stage();
                backwardScene = createSearchScreen(BACKWARD, backwardGridPane);
                backwardWindow.setScene(forwardScene);
                backwardWindow.show();
                break;
            }
            case ADAPTIVE: {
                gridAdaptive = new ArrayList<>();

                IntStream.range(0, CELLS).forEach(i -> {
                    gridAdaptive.add(new ArrayList<>());
                    IntStream.range(0, CELLS).forEach(j -> {
                        Tile tile = null;
                        try {
                            tile = (Tile)mainGrid.get(i).get(j).clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        gridAdaptive.get(i).add(tile);
                        adaptiveGridPane.getChildren().add(tile);
                    });
                });
                currentGrid = gridAdaptive;

                adaptiveWindow = new Stage();
                adaptiveScene = createSearchScreen(BACKWARD, adaptiveGridPane);
                adaptiveWindow.setScene(adaptiveScene);
                adaptiveWindow.show();
                break;
            }
            default:
        }
    }

    public Scene createSearchScreen(String titleString, Pane gridPane) {

        BorderPane borderPane = new BorderPane();

        HBox bottomTitleHBox = null;
        {
            bottomTitleHBox = createHBoxForSearchScreen(titleString);
            bottomTitleHBox.setPadding(new Insets(15, 12, 15, 12));
            bottomTitleHBox.setSpacing(10);
            bottomTitleHBox.setStyle("-fx-background-color: #991f23;");
        }

        VBox vBox = new VBox();
        {
            vBox.setPadding(new Insets(15, 12, 15, 12));
            vBox.setSpacing(10);
            vBox.setStyle("-fx-background-color: #991f23;");

            Text cost = new Text("Cost:" + "0");
            cost.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Text expandedNodes = new Text("Expanded Nodes:" + "0");
            expandedNodes.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            Text runtime = new Text("Run Time: " + "0");
            runtime.setFont(Font.font("Arial", FontWeight.BOLD, 20));

            vBox.getChildren().addAll(cost, expandedNodes, runtime);
        }

        {
            borderPane.setTop(null);
            borderPane.setCenter(gridPane);
            borderPane.setBottom(bottomTitleHBox);
            borderPane.setRight(vBox);
        }

        Scene scene = new Scene(borderPane, SIZE + 300, SIZE + 100);
        return scene;
    }

    private GridWorld createGridWorldUsingTileGrid() {
        GridWorld gridWorld = new GridWorld(1, mainGrid.size(), mainGrid.size());

        IntStream.range(0, mainGrid.size()).forEach(i -> {
            IntStream.range(0, mainGrid.size()).forEach(j -> {
                Text text = mainGrid.get(i).get(j).getText();
                int value = 1;
                if (!text.getText().isEmpty()) {
                    value = INFINITY;
                }
                ((List) gridWorld.getGridWorld().get(i)).set(j, value);
            });
        });
        return gridWorld;
    }

    private void reinitializeUnBlockedGrid() {
        initGrid();
    }

    private void initGrid() {

        mainGrid = new ArrayList<>();

        IntStream.range(0, CELLS).forEach(i -> {
            mainGrid.add(new ArrayList<>());

            IntStream.range(0, CELLS).forEach(j -> {
                Tile tile1 = new Tile(j, i);
                mainGrid.get(i).add(tile1);
                rootGridPane.getChildren().add(tile1);
            });
        });
        setInitAndGoalCell();
    }

    private void setInitAndGoalCell() {
        mainGrid.get(initialCell.getX()).get(initialCell.getY()).border.setFill(Color.AQUA);
//        gridForward.get(initialCell.getX()).get(initialCell.getY()).border.setFill(Color.AQUA);
//        gridBackward.get(initialCell.getX()).get(initialCell.getY()).border.setFill(Color.AQUA);
//        gridAdaptive.get(initialCell.getX()).get(initialCell.getY()).border.setFill(Color.AQUA);

        mainGrid.get(goalCell.getX()).get(goalCell.getY()).border.setFill(Color.RED);
//        gridForward.get(goalCell.getX()).get(goalCell.getY()).border.setFill(Color.RED);
//        gridBackward.get(goalCell.getX()).get(goalCell.getY()).border.setFill(Color.RED);
//        gridAdaptive.get(goalCell.getX()).get(goalCell.getY()).border.setFill(Color.RED);
    }

    public static List<List<Tile>> getCurrentGrid() {
        return currentGrid;
    }

    public static void setCurrentGrid(List<List<Tile>> currentGrid) {
        MainScreen.currentGrid = currentGrid;
    }

    public class Tile extends StackPane implements Cloneable {
        private int x, y;
        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
            border.setStroke(Color.BLACK);
            border.setFill(null);
            text.setText(null);

            getChildren().addAll(border, text);
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> flip());
        }

        public void block() {
            if (text.getText().isEmpty()) {
                text = new Text();
                text.setText("X");
                border.setFill(Color.BLACK);
            }
        }

        public void flip() {
            if (text.getText().isEmpty()) {
                block();
            } else {
                text.setText(null);
                border.setFill(null);
            }
        }

        public void changeColor(Paint color) {
            if (text.textProperty().getValue().isEmpty()) {
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

        public Object clone() throws
                CloneNotSupportedException
        {
            return super.clone();
        }
    }
}
