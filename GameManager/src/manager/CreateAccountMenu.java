package manager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.models.AccountManager;

public class CreateAccountMenu {
    private final Stage window;
    private final VBox layout;

    public CreateAccountMenu(AccountManager accountManager, Label messageLabel) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); 
        window.setTitle("Create New Account");

        layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setFillWidth(false); 
        layout.setPadding(new Insets(20));

        Label title = new Label("Create a New Account");
        title.setFont(Font.font(18));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(200);

        TextField profileField = new TextField();
        profileField.setPromptText("Profile Name");
        profileField.setPrefWidth(200);

        Button createBtn = new Button("Create");
        Button cancelBtn = new Button("Cancel");

        HBox buttonBox = new HBox(10, createBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                title,
                usernameField,
                passwordField,
                profileField,
                buttonBox
        );

        createBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String profile = profileField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || profile.isEmpty()) {
                messageLabel.setText("All fields are required.");
                return;
            }

            boolean created = accountManager.createAccount(username, password, profile);
            if (created) {
                messageLabel.setText("Account created for " + profile + "! You can now log in.");
                window.close();
            } else {
                messageLabel.setText("Username already exists.");
            }
        });

        cancelBtn.setOnAction(e -> window.close());

        Scene scene = new Scene(layout, 350, 300);
        window.setScene(scene);
    }

    public void show() {
        window.showAndWait();
    }
}
