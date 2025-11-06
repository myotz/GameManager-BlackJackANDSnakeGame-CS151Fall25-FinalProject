package manager.models;

public class User {
    private String userName;
    private String password;
    private int blackjackHighScore;
    private int snakeHighScore;
    private String profileName;

    //default constructor
    public User() {
        this.userName = null;
        this.password = null;
        this.blackjackHighScore = 0;
        this.snakeHighScore = 0;
        this.profileName = null;
    }

    //constructor to load the users and game progress from the file
    public User(String userName, String password, 
                int blackjackHighScore, int snakeHighScore, String profileName) {
        this.userName = userName;
        this.password = password;
        this.blackjackHighScore = blackjackHighScore;
        this.snakeHighScore = snakeHighScore;
        this.profileName = profileName;          
    }

    //constructor for account creation
    public User(String userName, String password, String profileName) {
        this.userName = userName;
        this.password = password;
        this.blackjackHighScore = 0;
        this.snakeHighScore = 0;;
        this.profileName = profileName;      
    }

    //Getters and Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setBlackjackHighScore(int highscore) {
        this.blackjackHighScore = highscore;
    }

    public void setSnakeHighScore(int highscore) {
        this.snakeHighScore = highscore;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public int getBlackjackHighScore() {
        return this.blackjackHighScore;
    }

    public int getSnakeHighScore() {
        return this.snakeHighScore;
    }

    //Additional methods
    @Override
    public String toString() {
        return null;
    }
}   
    
