package blackjack;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

//Utility class for creating JavaFX Nodes that display playing card images
public class CardImage {

    private static final String CARD_PATH = "GameManager/cards/";

    /**
     * Creates an image node for a card code (like "K-S" or "10-H").
     * 
     * @param code   The card code (Rank-Suit), e.g., "K-S"
     * @param hidden If true, shows the back of the card instead.
     * @return An image node
     */
    public static Node createCardImage(String code, boolean hidden) {
        ImageView imgView = new ImageView();
        imgView.setFitWidth(60);
        imgView.setFitHeight(90);

        try {
            String path = hidden ? CARD_PATH + "back.png" : CARD_PATH + code + ".png";
            File file = new File(path);
            Image img = new Image(file.toURI().toString());
            imgView.setImage(img);
            return imgView;
        } catch (Exception e) {
            Label fallback = new Label(code);
            fallback.setStyle("-fx-background-color:white; -fx-border-color:#333; -fx-padding:6 8;");
            return fallback;
        }
    }
}
