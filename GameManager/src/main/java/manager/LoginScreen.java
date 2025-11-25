package main.java.manager;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import main.java.manager.models.AccountManager;
import main.java.manager.models.User;

public class LoginScreen {
    private final VBox vbox;

    public LoginScreen(GameManager gameManager, AccountManager accountManager) {
        vbox = new VBox(15); 
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(false); 
        vbox.setStyle("-fx-background-color: #5b86e5;");


        Label title = new Label("WELCOME TO BLACKJACK AND SNAKE");
        title.setFont(Font.font(22));

        TextField usernameText = new TextField();
        usernameText.setPromptText("Username");
        usernameText.setPrefWidth(200);

        PasswordField passwordText = new PasswordField();
        passwordText.setPromptText("Password");
        passwordText.setPrefWidth(200);

        Button login = new Button("Log in");
        Button createAccount = new Button("Create Account");
        Label message = new Label();

        HBox hbox = new HBox(10, login, createAccount);
        hbox.setAlignment(Pos.CENTER);

        Button forgotPasswordBtn = new Button("Forgot Password");
        forgotPasswordBtn.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(title, usernameText, passwordText, hbox, forgotPasswordBtn, message);

        login.setOnAction(e -> {
            String username = usernameText.getText().trim();
            String password = passwordText.getText().trim();

            User loggedIn = accountManager.login(username, password);
            if (loggedIn != null) {
                System.out.println("Login successful! Welcome, " + loggedIn.getProfileName());
                gameManager.setCurrentUser(loggedIn);
                gameManager.showMainMenu();
            } else {
                System.out.println("Invalid username or password");
                message.setText("Invalid username or password");
            }
        });

        createAccount.setOnAction(e -> {
            CreateAccountMenu popup = new CreateAccountMenu(accountManager, message);
            popup.show();
        });

        forgotPasswordBtn.setOnAction(e -> {
            ForgotPasswordMenu popup = new ForgotPasswordMenu(accountManager, message);
            popup.show();
        });

    }

    public VBox getLayout() {
        return vbox;
    }
}