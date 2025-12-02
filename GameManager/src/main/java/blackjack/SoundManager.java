package blackjack;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private final MediaPlayer bgPlayer;
    private final MediaPlayer dealerWinPlayer;
    private final MediaPlayer drawCardPlayer;
    private final MediaPlayer playerWinPlayer;
    private double currentMusicVolume;
    private double currentSfxVolume;

    public SoundManager(double initialMusicVolume, double initialSfxVolume) {
        bgPlayer        = createPlayer("/assets/blackJack_background.mp3", true);
        dealerWinPlayer = createPlayer("/assets/blackJack_dealerwin.wav", false);
        drawCardPlayer  = createPlayer("/assets/blackJack_drawcard.wav", false);
        playerWinPlayer = createPlayer("/assets/blackJack_playerwin.mp3", false);

        setMusicVolume(initialMusicVolume);
        setSfxVolume(initialSfxVolume);
    }

    private MediaPlayer createPlayer(String path, boolean loop) {
        try {
            Media m = new Media(getClass().getResource(path).toExternalForm());
            MediaPlayer p = new MediaPlayer(m);
            if (loop) p.setCycleCount(MediaPlayer.INDEFINITE);
            return p;
        } catch (Exception e) {
            System.out.println("Couldn't load sound " + path + ": " + e.getMessage());
            return null;
        }
    }

    public void setMusicVolume(double volume) {
        this.currentMusicVolume = Math.max(0.0, Math.min(1.0, volume));
        if (bgPlayer != null)
            bgPlayer.setVolume(this.currentMusicVolume);
    }

    public void setSfxVolume(double volume) {
        this.currentSfxVolume = Math.max(0.0, Math.min(1.0, volume));
        if (dealerWinPlayer != null) dealerWinPlayer.setVolume(this.currentSfxVolume);
        if (drawCardPlayer != null) drawCardPlayer.setVolume(this.currentSfxVolume);
        if (playerWinPlayer != null) playerWinPlayer.setVolume(this.currentSfxVolume);
    }

    public void playBackground() {
        if (bgPlayer != null) bgPlayer.play();
    }

    public void stopBackground() {
        if (bgPlayer != null) bgPlayer.stop();
    }

    public void playDealerWin() {
        playOnce(dealerWinPlayer);
    }

    public void playDrawCard() {
        playOnce(drawCardPlayer);
    }

    public void playPlayerWin() {
        playOnce(playerWinPlayer);
    }

    private void playOnce(MediaPlayer p) {
        if (p == null) return;
        p.stop(); 
        p.play();
    }
}