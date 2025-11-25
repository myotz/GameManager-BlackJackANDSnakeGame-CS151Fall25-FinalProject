package main.java.snake;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {

    private final MediaPlayer bgPlayer;
    private final MediaPlayer eatPlayer;
    private final MediaPlayer crashPlayer;
    private double currentMusicVolume;
    private double currentSfxVolume;

    public AudioManager(double initialMusicVolume, double initialSfxVolume) {
        bgPlayer   = createPlayer("/assets/snakemusic.mp3", true);
        eatPlayer  = createPlayer("/assets/eat.wav", false);
        crashPlayer = createPlayer("/assets/crash.wav", false);

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
        //max allowed volume is 1.0
        this.currentMusicVolume = Math.max(0.0, Math.min(1.0, volume));
        if (bgPlayer != null) 
            bgPlayer.setVolume(this.currentMusicVolume);
    }

    public void setSfxVolume(double volume) {
        //max allowed volume is 1.0
        this.currentSfxVolume = Math.max(0.0, Math.min(1.0, volume));
        if (eatPlayer != null) eatPlayer.setVolume(this.currentSfxVolume);
        if (crashPlayer != null) crashPlayer.setVolume(this.currentSfxVolume);
    }

    public void playBackground() {
        if (bgPlayer != null) bgPlayer.play();
    }

    public void stopBackground() {
        if (bgPlayer != null) bgPlayer.stop();
    }

    public void playEat() {
        playOnce(eatPlayer);
    }

    public void playCrash() {
        playOnce(crashPlayer);
    }

    private void playOnce(MediaPlayer p) {
        if (p == null) return;
        p.stop();          
        p.play();          
    }
}

