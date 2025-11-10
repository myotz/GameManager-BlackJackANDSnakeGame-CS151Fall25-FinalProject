package manager.models;

import java.io.*;
import java.util.*;

public class AccountManager {
    private static final String FILEPATH = "data/accounts.txt";
    private Map<String, User> users = new HashMap<>();

    public AccountManager() {
        loadUsers();
    }

    /**
     * Loads all users from user_accounts.txt into memory.
     */
    public void loadUsers() {
        File file = new File(FILEPATH);
        System.out.println("Looking for file: " + new File(FILEPATH).getAbsolutePath());
        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String profile = parts[2];
                    users.put(username, new User(username, password, profile));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean createAccount(String userName, String password, String profileName) {
        if (users.containsKey(userName)) {
            System.out.println("Account with this username already exists.");
            return false;
        }
        User user = new User(userName, password, profileName);
        users.put(userName, user);
        saveUsers();
        return true;
    }

    public User login(String userName, String password) {
        User user = users.get(userName);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        else {
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
                pw.println(user.getUserName() + "," + user.getPassword() + "," + user.getProfileName());
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
