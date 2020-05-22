package sample;

import javafx.scene.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    Stage window = new Stage();
    Scene mainScreen, addParts, modParts, addProds, modProds;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent mainScreenRoot = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Parent addPartsRoot = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));

        window = primaryStage; //refer to primaryStage as window
        window.setTitle("Inventory Management C482");

        mainScreen = new Scene(mainScreenRoot, 720, 405);
        addParts = new Scene(addPartsRoot, 600, 600);

        window.setScene(mainScreen);
        window.show();
    }
}
