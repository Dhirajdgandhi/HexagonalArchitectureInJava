package AI.Assignment1;

import AI.Assignment1.UI.MainScreen;
import AI.Assignment1.config.Config;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

//@SpringBootApplication
public class UIApp extends Application {
    private AbstractApplicationContext context;

    @Override
    public void start(Stage primaryStage) {
        context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean(MainScreen.class).start(primaryStage);
    }

    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        SpringApplication.run(UIApp.class);
        launch(args);
    }

}
