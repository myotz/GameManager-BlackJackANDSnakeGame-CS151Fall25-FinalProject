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
<<<<<<< HEAD
        //System.out.println("Looking for file: " + new File(FILEPATH).getAbsolutePath());
=======
        System.out.println("Looking for file: " + new File(FILEPATH).getAbsolutePath());
>>>>>>> chan
        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
<<<<<<< HEAD
                if (line.isEmpty())
                    continue;

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String password = parts[1];
                    String profile = parts[2];
                    String securityQuestion = parts[3];
                    users.put(username, new User(username, password, profile, securityQuestion));
=======
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String profile = parts[2];
                    users.put(username, new User(username, password, profile));
>>>>>>> chan
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public boolean createAccount(String userName, String password, String profileName, String securityQuestion) {
=======
    public boolean createAccount(String userName, String password, String profileName) {
>>>>>>> chan
        if (users.containsKey(userName)) {
            System.out.println("Account with this username already exists.");
            return false;
        }
<<<<<<< HEAD
        User user = new User(userName, password, profileName, securityQuestion);
=======
        User user = new User(userName, password, profileName);
>>>>>>> chan
        users.put(userName, user);
        saveUsers();
        return true;
    }

    public User login(String userName, String password) {
        User user = users.get(userName);
        if (user != null && user.getPassword().equals(password)) {
            return user;
<<<<<<< HEAD
        } else {
=======
        }
        else {
>>>>>>> chan
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
<<<<<<< HEAD
                pw.println(user.getUserName() + "," + user.getPassword()
                        + "," + user.getProfileName() + "," + user.getSecurityAnswer());
            }
        } catch (IOException e) {
=======
                pw.println(user.getUserName() + "," + user.getPassword() + "," + user.getProfileName());
            }
        } 
        catch (IOException e) {
>>>>>>> chan
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
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

=======
>>>>>>> chan
}
