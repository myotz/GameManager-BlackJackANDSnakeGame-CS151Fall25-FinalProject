package blackjack.model;

public enum Suit {
    HEARTS("H"), DIAMONDS("D"), CLUBS("C"), SPADES("S");

    private final String code;

    Suit(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static Suit fromCode(String c) {
        return switch (c) {
            case "H" -> HEARTS;
            case "D" -> DIAMONDS;
            case "C" -> CLUBS;
            case "S" -> SPADES;
            default -> throw new IllegalArgumentException("Invalid suit: " + c);
        };
    }
}
