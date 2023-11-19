package Pacman.src.main.java.model;

import javafx.animation.AnimationTimer;
import javax.sound.sampled.*;
import java.io.*;

public final class Sound {
    private final AnimationTimer MainMusicLoop = new AnimationTimer() {
        long l = 0;
        long sound_timer = 0;
        @Override
        public void handle(long n) {
            if (l == 0) {
                l = n;
                return;
            }
            if (sound_timer > 29540000000L){
                playMainMusic();
                sound_timer = 0;
            }
            sound_timer += n - l;
            l = n;
        }
    };
    private Clip MainMusicClip, StartMusicClip;

    private Sound(){}

    public static final Sound SOUND = new Sound();

    public Clip getMainMusicClip() {return MainMusicClip;}

    private void playMainMusic(){
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(new File("src/main/resources/PacmanMainMusic.wav"));
            MainMusicClip = AudioSystem.getClip();
            MainMusicClip.open(audio);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        MainMusicClip.start();
    }

    public void playMainMusicLoop(){
        /* Joue la musique du menu en boucle
        */
        playMainMusic();
        MainMusicLoop.start();
    }

    public void stopMainMusicLoop(){
        /* Stop la musique du menu
         */
        MainMusicLoop.stop();
        MainMusicClip.stop();
    }

    public void playStartMusic(){
        /* Joue la musique de début de jeu
        */
        AudioInputStream StartAudio;
        try {
            StartAudio = AudioSystem.getAudioInputStream(new File("src/main/resources/pacman_beginning.wav"));
            StartMusicClip = AudioSystem.getClip();
            StartMusicClip.open(StartAudio);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        StartMusicClip.start();
    }

    public void stopStartMusic(){
        /* Stop la musique de début de jeu
        */
        StartMusicClip.stop();
    }

    public void stopAllSounds(){
        /* Stop tout les sons du jeu (pour les développeurs)
        */
        stopMainMusicLoop();
        stopStartMusic();
    }

}
