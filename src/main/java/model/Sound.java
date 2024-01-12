package model;

import javafx.animation.AnimationTimer;
import javax.sound.sampled.*;
import java.io.*;

/**
 * Classe qui gère l'ensemble des sons du jeu
 * @author Julien
 */
public final class Sound {

    /**
     * Classe qui réprésente des fichiers audio
     * @author Julien
     */
    private static class AudioFile {

        private String path;
        private Clip clip;

        private AudioFile(String path, Clip clip) {
            this.path = path;
            this.clip = clip;
        }

    }

    private static Clip mainMusicClip, launchMusicClip, eatSoundClip, deathMusicClip,
            fruitClip, sirenClip, energizerSoundClip, retreatingSoundClip;
    private static AudioFile mainMusic =
            new AudioFile("src/main/resources/FichierAudio/MainMusic.wav",
                    Sound.mainMusicClip),
            launchMusic = new AudioFile("src/main/resources/FichierAudio/GameLaunchMusic.wav",
                    Sound.launchMusicClip),
            eatSound = new AudioFile("src/main/resources/FichierAudio/EatSound.wav",
                    Sound.eatSoundClip),
            deathMusic = new AudioFile("src/main/resources/FichierAudio/DeathMusic.wav",
                    Sound.deathMusicClip),
            fruitSound = new AudioFile("src/main/resources/FichierAudio/FruitSound.wav",
                    Sound.fruitClip),
            sirenSound = new AudioFile("src/main/resources/FichierAudio/SirenSound.wav",
                    Sound.sirenClip),
            energizerSound = new AudioFile("src/main/resources/FichierAudio/EnergizerSound.wav",
                    Sound.energizerSoundClip),
            retreatingSound = new AudioFile("src/main/resources/FichierAudio/RetreatingSound.wav",
                    Sound.retreatingSoundClip);


    /**
     * Getter du mainMusicClip
     * @author Julien
     * @return Clip
     */
    public static Clip getMainMusicClip() {
        return mainMusicClip;
    }

    /**
     * Getter du launchMusicClip
     * @author Julien
     * @return Clip
     */
    public static Clip getLaunchMusicClip() {
        return Sound.launchMusic.clip;
    }

    /**
     * Getter du deathMusicClip
     * @author Julien
     * @return Clip
     */
    public static Clip getDeathMusicClip() {
        return Sound.deathMusic.clip;
    }

    /**
     * Getter du sirenClip
     * @author Julien
     * @return Clip
     */
    public static Clip getSirenSoundClip() {
        return Sound.sirenSound.clip;
    }

    /**
     * Getter du energizerClip
     * @author Julien
     * @return Clip
     */
    public static Clip getEnergizerSoundClip() {
        return Sound.energizerSound.clip;
    }

    /**
     * Getter du retreatingSoundClip
     * @author Julien
     * @return Clip
     */
    public static Clip getRetreatingSoundClip() {
        return Sound.retreatingSound.clip;
    }

    /**
     * Joue n'importe quel son
     * @author Julien
     * @return void
     */
    private static void play(AudioFile file) {
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(new File(file.path));
            file.clip = AudioSystem.getClip();
            file.clip.open(audio);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        file.clip.start();
    }

    /**
     * joue n'importe quel son en boucle
     * @author Julien
     * @return void
     */
    private static void playLoop(AudioFile file){
        AudioInputStream audio;
        Clip clip;
        try {
            audio = AudioSystem.getAudioInputStream(new File(file.path));
            file.clip = AudioSystem.getClip();
            file.clip.open(audio);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        file.clip.start();
        file.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Joue une seule fois la musique du menu principale
     * @author Julien
     * @return void
     */
    public static void playMainMusic() {
        play(Sound.mainMusic);
    }

    /**
     * Joue la musique du menu en boucle
     * @author Julien
     * @return void
     */
    public static void playMainMusicLoop() {
        playLoop(Sound.mainMusic);
    }

    /**
     * Stop la musique du menu
     * @author Julien
     * @return void
     */
    public static void stopMainMusicLoop() {
        Sound.mainMusic.clip.stop();
    }

    /**
     * Joue la musique de début de jeu
     * @author Julien
     * @return void
     */
    public static void playLaunchMusic() {
        play(Sound.launchMusic);
    }

    /**
     * Stop la musique de début de jeu
     * @author Julien
     * @return void
     */
    public static void stopLaunchMusic() {
        Sound.launchMusic.clip.stop();
    }

    /**
     * Joue le son de Pacman qui mange une Pacgum
     * @author Julien
     * @return void
     */
    public static void playEatSound() {
        play(Sound.eatSound);
    }

    /**
     * Stop le son de Pacman qui mange une Pacmgum
     * @author Julien
     * @return void
     */
    public static void stopEatSound() {
        Sound.eatSound.clip.stop();
    }

    /**
     * Joue la musique de mort
     * @author Julien
     * @return void
     */
    public static void playDeathMusic() {
        play(Sound.deathMusic);
    }

    /**
     * Stop la musique de de mort
     * @author Julien
     * @return void
     */
    public static void stopDeathMusic() {
        Sound.deathMusic.clip.stop();
    }

    /**
     * Joue le son de Pacman qui mange un fruit
     * @author Julien
     * @return void
     */
    public static void playFruitSound() {
        play(Sound.fruitSound);
    }

    /**
     * Stop le son de Pacman qui mange un fruit
     * @author Julien
     * @return void
     */
    public static void stopFruitSound() {
        Sound.fruitSound.clip.stop();
    }

    /**
     * Joue le son de la sirène des fantômes
     * @author Julien
     * @return void
     */
    public static void playSirenSound() {
        play(Sound.sirenSound);
    }

    /**
     * Stop le son de la sirène des fantômes
     * @author Julien
     * @return void
     */
    public static void stopSirenSound() {
        Sound.sirenSound.clip.stop();
    }

    /**
     * Joue le son de Pacman en mode Energizer
     * @author Julien
     * @return void
     */
    public static void playEnergizerSound() {
        play(Sound.energizerSound);
    }

    /**
     * Stop le son Pacman en mode Energizer
     * @author Julien
     * @return void
     */
    public static void stopEnergizerSound() {
        Sound.energizerSound.clip.stop();
    }

    /**
     * Joue le son des fantômes décédés
     * @author Julien
     * @return void
     */
    public static void playRetreatingSound() {
        play(Sound.retreatingSound);
    }

    /**
     * Stop le son des fantômes décédés
     * @author Julien
     * @return void
     */
    public static void stopRetreatingSound() {
        Sound.retreatingSound.clip.stop();
    }

    /**
     * Stop tout les sons du jeu (pour les développeurs)
     * @author Julien
     * @return void
     */

    public static void stopAllSounds() {
        stopMainMusicLoop();
        stopLaunchMusic();
        stopEatSound();
        stopDeathMusic();
        stopFruitSound();
    }
}
