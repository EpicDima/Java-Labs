import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainController mainController = new MainController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(mainController);

        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Lab5");
        primaryStage.setScene(scene);
        primaryStage.show();

        mainController.setValues();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
