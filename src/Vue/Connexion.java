package Vue;

import Function.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Connexion {
    @FXML
    private Button connexionButton;
    @FXML
    private TextField login;
    @FXML
    private PasswordField pwd;
    @FXML
    private Label message;

    public void getConnexion() throws Exception {
        Account account = new Account();
        if (account.getAccount(login.getText(), pwd.getText())) {
            Main.launchFullApp(new Stage(), account);
            Stage stage = (Stage) connexionButton.getScene().getWindow();
            stage.close();
        } else {
            login.clear();
            pwd.clear();
            message.setText("Connection impossible : login ou mot de passe incorrect, réessayer");
        }
    }

    @FXML
    public void getLogin() {
        //todo : TROUVER UN MOYEN D'EFFACER CETTE FONCTION SANS TOUS FAIRE BUGUé
    }

    @FXML
    public void getPwd() {
        //todo : TROUVER UN MOYEN D'EFFACER CETTE FONCTION SANS TOUS FAIRE BUGUé
    }
}
