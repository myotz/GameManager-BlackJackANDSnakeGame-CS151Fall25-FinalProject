package blackjack.model;

public final class GameLogic {
    private GameLogic() {}

    public static Outcome compare(Player player, Player dealer) {
        int playerValue = player.getValue(), dealerValue = dealer.getValue();
        boolean playerBust = playerValue > 21, dealerBust = dealerValue > 21;
        if (playerBust && dealerBust) return Outcome.PUSH;
        if (playerBust) return Outcome.LOSE;
        if (dealerBust) return Outcome.WIN;
        if (playerValue > dealerValue) return Outcome.WIN;
        if (playerValue < dealerValue) return Outcome.LOSE;
        return Outcome.PUSH; 
    }

    public enum Outcome { WIN, LOSE, PUSH }
}
