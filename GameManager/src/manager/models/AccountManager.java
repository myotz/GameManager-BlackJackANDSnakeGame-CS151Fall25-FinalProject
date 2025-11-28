package manager.models;

import java.io.*;
import java.util.*;

import manager.GameManager;
import utils.CryptoUtils;

public class AccountManager {
    private static final String FILEPATH = "data/accounts.txt";
    private Map<String, User> users = new HashMap<>();
    private GameManager gameManager;


    public AccountManager(GameManager gameManager) {
        this.gameManager = gameManager;
        loadUsers();
    }

    /**
     * Loads all users from user_accounts.txt into memory.
     */
    public void loadUsers() {
        File file = new File(FILEPATH);
        // System.out.println("Looking for file: " + new
        // File(FILEPATH).getAbsolutePath());
        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = CryptoUtils.decrypt(parts[0]);
                    String password = CryptoUtils.decrypt(parts[1]);
                    String profile = CryptoUtils.decrypt(parts[2]);
                    String question = CryptoUtils.decrypt(parts[3]);
                    users.put(username, new User(username, password, profile, question));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean createAccount(String userName, String password, String profileName, String securityQuestion) {
        if (users.containsKey(userName)) {
            System.out.println("Account with this username already exists.");
            return false;
        }
        User user = new User(userName, password, profileName, securityQuestion);
        users.put(userName, user);
        saveUsers();
        gameManager.getHighScoreManager().createDefaultScores(userName);
        return true;
    }

    public User login(String userName, String password) {
        User user = users.get(userName);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    public void updateUser(User updatedUser) {
        users.put(updatedUser.getUserName(), updatedUser);
        saveUsers();
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Saves all users to user_accounts.txt
     */
    private void saveUsers() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILEPATH))) {
            for (User user : users.values()) {
                pw.println(
                        CryptoUtils.encrypt(user.getUserName()) + "," +
                                CryptoUtils.encrypt(user.getPassword()) + "," +
                                CryptoUtils.encrypt(user.getProfileName()) + "," +
                                CryptoUtils.encrypt(user.getSecurityAnswer()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean resetPassword(String username, String newPassword, String answer) {
        User user = users.get(username);
        if (user == null) {
            return false;
        }
        if (!user.getSecurityAnswer().equalsIgnoreCase(answer.trim())) {
            return false; // wrong answer
        }
        user.setPassword(newPassword);
        saveUsers();
        return true;
    }

}
