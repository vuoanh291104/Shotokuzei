import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane  layout = new StackPane();
        Button btn = new Button("hello");
        btn.setOnAction(actionEvent -> {
            System.out.print("blabla");
        });
        layout.getChildren().add(btn);
        Scene scene = new Scene(layout,300,300);
        stage.setScene(scene);
        stage.setTitle("heloo JavaFX");
        stage.show();

    }
}