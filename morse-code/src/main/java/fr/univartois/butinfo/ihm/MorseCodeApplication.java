package fr.univartois.butinfo.ihm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Axel Kleszewski
 *
 * @version 0.1.0
 */
public class MorseCodeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("morse-code-view.fxml"));
        Parent viewContent = fxmlLoader.load();
		Scene scene = new Scene(viewContent, 960, 540);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Morse Code Game");
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
