package blackjack;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardImage {

    private static final String CARD_PATH = "/blackjack/cards/";

    public static Node createCardImage(String code, boolean hidden) {
        ImageView imgView = new ImageView();
        imgView.setFitWidth(60);
        imgView.setFitHeight(90);

        try {
            String fileName = hidden ? "back.png" : code + ".png";
            System.out.println("Attempting to load: " + CARD_PATH + fileName);
            Image img = new Image(CardImage.class.getResourceAsStream(CARD_PATH + fileName));
            imgView.setImage(img);
            return imgView;
        } catch (Exception e) {
            System.out.println("Error loading image for card: " + code);
            e.printStackTrace();
            Label fallback = new Label(code);
            fallback.setStyle("-fx-background-color:white; -fx-border-color:#333; -fx-padding:6 8;");
            return fallback;
        }
    }
}
