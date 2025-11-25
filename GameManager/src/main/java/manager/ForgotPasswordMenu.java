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

public class ForgotPasswordMenu {
    private final Stage window;
    private final VBox layout;

    public ForgotPasswordMenu(AccountManager accountManager, Label messageLabel) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // Block other windows
        window.setTitle("Forgot Password");

        layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setFillWidth(false);
        // layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #ac346ae7;");

        Label title = new Label("Reset Your Password");
        title.setFont(Font.font(18));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefWidth(250);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New password");
        newPasswordField.setPrefWidth(250);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm new password");
        confirmPasswordField.setPrefWidth(250);

        TextField securityQuestionField = new TextField();
        securityQuestionField.setPromptText("What is the first name of your first love?");
        securityQuestionField.setPrefWidth(250);

        Button resetBtn = new Button("Reset");
        Button cancelBtn = new Button("Cancel");

        Label popupMessage = new Label();
        popupMessage.setFont(Font.font(18));

        HBox buttonBox = new HBox(10, resetBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                title,
                usernameField,
                newPasswordField,
                confirmPasswordField,
                securityQuestionField,
                buttonBox,
                popupMessage);

        // Reset logic
        resetBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String newPass = newPasswordField.getText().trim();
            String confirmPass = confirmPasswordField.getText().trim();
            String securityQuestion = securityQuestionField.getText().trim();

            if (username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty() || securityQuestion.isEmpty()) {
                popupMessage.setText("All fields are required.");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                popupMessage.setText("Passwords do not match.");
                return;
            }

            boolean success = accountManager.resetPassword(username, newPass, securityQuestion);
            if (success) {
                messageLabel.setText("Password reset successfully!");
                window.close();
            } else {
                popupMessage.setText("Invalid username or security answer");
                messageLabel.setText("Invalid username or security answer.");
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
