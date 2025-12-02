package utils;

import java.util.Base64;

/**
 * Simple text encoder/decoder.
 * It encodes the important imformation to String that human can't understnad
 */
public class CryptoUtils {

    private static final String SECRET_KEY = "SimpleKey123";

    //Scrambles the text so it cannot be read
    public static String encrypt(String text) {
        if (text == null)
            return null;

        StringBuilder scrambled = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = SECRET_KEY.charAt(i % SECRET_KEY.length());

            // Mix letters to produce a scrambled value
            int mixed = textChar + keyChar; 
            scrambled.append((char) mixed);
        }

        return Base64.getEncoder().encodeToString(scrambled.toString().getBytes());
    }

    //Restores original text that was previously scrambled.
    public static String decrypt(String encrypted) {
        if (encrypted == null)
            return null;

        try {
            // Try to treat it as Base64 (new encrypted format)
            String decoded = new String(Base64.getDecoder().decode(encrypted));
            StringBuilder original = new StringBuilder();

            for (int i = 0; i < decoded.length(); i++) {
                char scrambledChar = decoded.charAt(i);
                char keyChar = SECRET_KEY.charAt(i % SECRET_KEY.length());

                int unmixed = scrambledChar - keyChar;  // reverse the add
                original.append((char) unmixed);
            }

            return original.toString();
        } catch (IllegalArgumentException e) {
            // If it is NOT valid Base64, assume it's old plain text and just return it
            return encrypted;
        }
    }
}
