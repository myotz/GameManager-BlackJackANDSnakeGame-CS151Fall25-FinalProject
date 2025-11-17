package manager;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import manager.models.AccountManager;
import manager.models.User;

public class LoginScreen {
    private final VBox vbox;

    public LoginScreen(GameManager gameManager, AccountManager accountManager) {
<<<<<<< HEAD
        vbox = new VBox(15); 
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(false); 
        vbox.setStyle("-fx-background-color: #5b86e5;");
=======
        vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(false); 
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #36d1dc, #5b86e5);");
>>>>>>> chan


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

<<<<<<< HEAD
        Button forgotPasswordBtn = new Button("Forgot Password");
        forgotPasswordBtn.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(title, usernameText, passwordText, hbox, forgotPasswordBtn, message);
=======
        vbox.getChildren().addAll(title, usernameText, passwordText, hbox, message);
>>>>>>> chan

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
<<<<<<< HEAD
                message.setText("Invalid username or password");
=======
>>>>>>> chan
            }
        });

        createAccount.setOnAction(e -> {
            CreateAccountMenu popup = new CreateAccountMenu(accountManager, message);
            popup.show();
        });

<<<<<<< HEAD
        forgotPasswordBtn.setOnAction(e -> {
            ForgotPasswordMenu popup = new ForgotPasswordMenu(accountManager, message);
            popup.show();
        });

=======
>>>>>>> chan
    }

    public VBox getLayout() {
        return vbox;
    }
}
