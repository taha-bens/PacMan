package model;

import javafx.animation.AnimationTimer;
import javax.sound.sampled.*;
import java.io.*;

/**
 * Classe qui gère l'ensemble des sons du jeu
 * @author Julien
 * @version 2.0
 */
public final class Sound {

    private final AnimationTimer mainMusicLoop = new AnimationTimer() {
        long last = 0;
        long soundTimer = 0;
        @Override
        public void handle(long now) {
            if (last == 0) {
                last = now;
                return;
            }

            if (soundTimer > 29540000000L) {
                playMainMusic();
                soundTimer = 0;
            }
            soundTimer += now - last;
            last = now;
        }
    };

    private Clip mainMusicClip, startMusicClip, eatSoundClip, deathMusicClip;

    private Sound() {

    }

    public static final Sound SOUND = new Sound();

    /**
     * Getter du mainMusicClip
     * @author Julien
     * @return Clip
     */
    public Clip getMainMusicClip() {
        return mainMusicClip;
    }

    /**
     * Getter du startMusicClip
     * @return Clip
     */
    public Clip getStartMusicClip() {

        return startMusicClip;
    }

    /**
     * Getter du deathMusicClip
     */
    public Clip getDeathMusicClip() {
        return deathMusicClip;
    }

    /**
     * Joue une seule fois la musique du menu principale
     * @return void
     */
    private void playMainMusic() {
        AudioInputStream audio;
        try {
            File file = new File("src/main/resources/PacmanMainMusic.wav");
            audio = AudioSystem.getAudioInputStream(file);
            mainMusicClip = AudioSystem.getClip();
            mainMusicClip.open(audio);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        mainMusicClip.start();
    }

    /**
     * Joue la musique du menu en boucle
     * @return void
     */
    public void playMainMusicLoop() {
        playMainMusic();
        mainMusicLoop.start();
    }

    /**
     * Stop la musique du menu
     * @return void
     */
    public void stopMainMusicLoop() {
        mainMusicLoop.stop();
        mainMusicClip.stop();
    }

    /**
     * Joue la musique de début de jeu
     * @return void
     */
    public void playStartMusic() {
        AudioInputStream StartAudio;
        try {
            File file = new File("src/main/resources/pacman_beginning.wav");
            StartAudio = AudioSystem.getAudioInputStream(file);
            startMusicClip = AudioSystem.getClip();
            startMusicClip.open(StartAudio);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        startMusicClip.start();
    }

    /**
     * Stop la musique de début de jeu
     * @return void
     */
    public void stopStartMusic() {
        startMusicClip.stop();
    }

    /**
     * Joue le son de Pacman qui mange une Pacgum
     * @return void
     */
    public void playEatSound() {
        AudioInputStream eatSoundAudio;
        try {
            File file = new File("src/main/resources/credit.wav");
            eatSoundAudio = AudioSystem.getAudioInputStream(file);
            eatSoundClip = AudioSystem.getClip();
            eatSoundClip.open(eatSoundAudio);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        eatSoundClip.start();
    }

    /**
     * Stop la musique de début de jeu
     * @return void
     */
    public void stopEatSound() {
        eatSoundClip.stop();
    }

    /**
     * Joue le son de Pacman qui mange une Pacgum
     * @return void
     */
    public void playDeathMusic() {
        AudioInputStream deathAudio;
        try {
            File file = new File("src/main/resources/DeathMusic.wav");
            deathAudio = AudioSystem.getAudioInputStream(file);
            deathMusicClip = AudioSystem.getClip();
            deathMusicClip.open(deathAudio);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        deathMusicClip.start();
    }

    /**
     * Stop la musique de de mort
     * @return void
     */
    public void stopDeathMusic() {
        deathMusicClip.stop();
    }

    /**
     * Stop tout les sons du jeu (pour les développeurs)
     * @return void
     */
    public void stopAllSounds() {
        stopMainMusicLoop();
        stopStartMusic();
        stopEatSound();
        stopDeathMusic();
    }
}
