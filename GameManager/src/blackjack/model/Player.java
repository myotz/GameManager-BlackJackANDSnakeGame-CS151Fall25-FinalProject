package blackjack.model;

public class Player {
    protected final String name;
    protected int money;
    protected int bet;
    protected final Hand hand = new Hand();

    public Player(String name, int initialMoney) {
        this.name = name;
        this.money = initialMoney;
        // this.hand = new Hand();
    }

    public void add(Card card) {
        hand.add(card);
    }

    public void clear() {
        hand.clear();
        bet = 0;
    }

    public int getValue() {
        return hand.value();
    }

    public boolean isBust() {
        return hand.isBust();
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public int getMoney() {
        return money;
    }

    public int getBet() {
        return bet;
    }

    public void playBet(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Bet must be positive");
        }
        if (amount > money) {
            throw new IllegalArgumentException("Bet exceeds available money");
        }
        this.bet += amount;
        this.money -= amount;
    }

    public int winBet() {
        int winnings = bet * 2;
        this.money += winnings;
        this.bet = 0;
        return winnings;
    }

    public int pushBet() {
        this.money += bet;
        this.bet = 0;
        return bet;
    }

    public void loseBet() {
        this.bet = 0;
    }

    //TYPE:name:money:bet:[cards]
    public String encodeType(String type) {
        return type + ":" + name + ":" + money + ":" + bet + ":[" + hand.encode() + "]";
    }

    @Override
    public String toString() {
        return name + " (Money: $" + money + ", Bet: $" + bet + ", Hand Value: " + hand.value() + ")";
    }

}
