package jtimer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JTimerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/jtimer_view.fxml")));
        stage.setTitle("jTimer");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gui/assets/ic_timer.png"))));
        stage.setScene(new Scene(root, 320, 480));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}