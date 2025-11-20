package blackjack.model;

import java.util.ArrayList;
import java.util.List;

public class GameSaveHandler {

    //SAVE
    public static String save(GameState state) {
        StringBuilder sb = new StringBuilder();
        sb.append("v1|turn=").append(state.turnIndex)
                .append("|reveal=").append(state.revealDealerHole ? 1 : 0)
                .append("|deck=").append(state.deck.encode())
                .append("|P=");

        List<String> parts = new ArrayList<>();
        parts.add(state.human.encodeType("HUMAN"));
        parts.add(state.bot1.encodeType("BOT"));
        parts.add(state.bot2.encodeType("BOT"));
        parts.add(state.dealer.encodeType("DEALER"));

        for (int i = 0; i < parts.size(); i++) {
            if (i > 0)
                sb.append(";");
            sb.append(parts.get(i));
        }
        return sb.toString();
    }

    //LOAD
    public static GameState load(String save) {
        if (save == null || save.isEmpty())
            throw new IllegalArgumentException("Empty save");
        String[] sections = save.split("\\|");
        if (!sections[0].equals("v1"))
            throw new IllegalArgumentException("Bad version");

        int turn = Integer.parseInt(sections[1].split("=", 2)[1]);
        boolean reveal = Integer.parseInt(sections[2].split("=", 2)[1]) == 1;
        // Combine deck and seq sections, since they belong together
        String deckStr = sections[3].substring(sections[3].indexOf('=') + 1) + "|"
                + sections[4].substring(sections[4].indexOf('=') + 1);

        String playersStr = sections[5].substring(sections[5].indexOf('=') + 1);


        GameState gs = new GameState();
        gs.deck = Deck.decode(deckStr);
        gs.turnIndex = turn;
        gs.revealDealerHole = reveal;

        String[] players = playersStr.split(";");
        if (players.length != 4)
            throw new IllegalArgumentException("Need 4 players");

        // HUMAN
        String[] fH = players[0].split(":");
        Hand hH = Hand.decode(stripBrackets(fH[4]));
        Player human = new Player(fH[1], Integer.parseInt(fH[2]));
        human.bet = Integer.parseInt(fH[3]);
        for (Card c : hH.getCards())
            human.add(c);

        // BOT1
        String[] fB1 = players[1].split(":");
        Hand hB1 = Hand.decode(stripBrackets(fB1[4]));
        int th1 = Bot.parseThresholdOrDefault(fB1, 5, 16);
        Bot b1 = new Bot(fB1[1], Integer.parseInt(fB1[2]), th1);
        b1.bet = Integer.parseInt(fB1[3]);
        for (Card c : hB1.getCards())
            b1.add(c);

        // BOT2
        String[] fB2 = players[2].split(":");
        Hand hB2 = Hand.decode(stripBrackets(fB2[4]));
        int th2 = Bot.parseThresholdOrDefault(fB2, 5, 14);
        Bot b2 = new Bot(fB2[1], Integer.parseInt(fB2[2]), th2);
        b2.bet = Integer.parseInt(fB2[3]);
        for (Card c : hB2.getCards())
            b2.add(c);

        // DEALER
        String[] fD = players[3].split(":");
        Hand hD = Hand.decode(stripBrackets(fD[4]));
        Dealer d = new Dealer();
        d.bet = Integer.parseInt(fD[3]);
        for (Card c : hD.getCards())
            d.add(c);

        gs.human = human;
        gs.bot1 = b1;
        gs.bot2 = b2;
        gs.dealer = d;
        return gs;
    }

    private static String stripBrackets(String s) {
        if (s.startsWith("[") && s.endsWith("]"))
            return s.substring(1, s.length() - 1);
        return s;
    }
}
