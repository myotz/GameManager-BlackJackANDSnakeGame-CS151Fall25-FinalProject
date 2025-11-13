package snake;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {

    private final MediaPlayer bgPlayer;
    private final MediaPlayer eatPlayer;
    private final MediaPlayer crashPlayer;

    public AudioManager() {
        bgPlayer   = createPlayer("/assets/snakemusic.mp3", true, 0.3);
        eatPlayer  = createPlayer("/assets/eat.wav", false, 0.9);
        crashPlayer = createPlayer("/assets/crash.wav", false, 0.9);
    }

    private MediaPlayer createPlayer(String path, boolean loop, double vol) {
        try {
            Media m = new Media(getClass().getResource(path).toExternalForm());
            MediaPlayer p = new MediaPlayer(m);
            p.setVolume(vol);
            if (loop) p.setCycleCount(MediaPlayer.INDEFINITE);
            return p;
        } catch (Exception e) {
            System.out.println("Couldn't load sound " + path + ": " + e.getMessage());
            return null;
        }
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