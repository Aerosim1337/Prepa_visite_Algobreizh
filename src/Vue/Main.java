package Vue;

import Function.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Connexion.fxml"));
        primaryStage.setTitle("Algobreizh - Prepa-Visites");
        primaryStage.setScene(new Scene(root, 400, 275));
        primaryStage.show();
    }

    static void launchFullApp(Stage fullStage, Account account) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("App.fxml"));
        fullStage.setTitle("Algobreizh - Prepa-Visites - " + account.getLogin());
        fullStage.setScene(new Scene(loader.load()));
        fullStage.show();
        //Get all information show
        App app = loader.getController();
        app.init(account);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
