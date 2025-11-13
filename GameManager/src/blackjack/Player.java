package blackjack;

public class Player {
    protected final String name;
    protected int money;
    protected int bet;

    public Player(String name, int initialMoney){
        this.name = name;
        this.money = initialMoney;
    }

    public void add(Card card){

    }

    public void clear(){

    }

    public int getValue(){
        return 0;
    }

    public boolean isBust(){
        return false;
    }

    public String getName(){
        return name;
    }

    public void playBet(int amount){
        this.bet = amount;
        this.money -= amount;
    }

    public int getMoney(){
        return money;
    }


}
