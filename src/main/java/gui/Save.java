package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Save {

    private String fichier;
    private String[][] contenu;

    public Save(String fichier) {
        this.fichier = fichier;
        this.contenu = readFichier();
    }

    /**
     * Lire un fichier
     * @return
     */
    private String[][] readFichier() {
        try {
            FileReader reader = new FileReader(fichier);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            List<String[]> lignes = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] ligneSplit = line.split(";");
                lignes.add(ligneSplit);
            }

            String[][] contenu = new String[lignes.size()][2];

            for (int i = 0; i < lignes.size(); i++) {
                String[] ligne = lignes.get(i);
                contenu[i][0] = ligne[0];
                contenu[i][1] = ligne[1];
            }
            bufferedReader.close();
            return contenu;

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Ecrire dans un fichier
     */
    public void writeFichier() {
        try {
            FileWriter writer = new FileWriter(fichier);
            for (String[] ligne : contenu) {
                writer.write(ligne[0] + ";" + ligne[1] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            // Handle IOException here
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * Avoir le score du joueur par son pseudo
     * @param pseudo
     * @return
     */
    public int getScore(String pseudo) {
        for (int i = 0; i < contenu.length; i++) {
            if (contenu[i][0].equals(pseudo)) {
                return Integer.parseInt(contenu[i][1]);
            }
        }
        return -1;
    }

    /**
     * Actualiser le score d'un joueur par son pseudo
     * @param pseudo
     * @param score
     */
    public void updateScore(String pseudo, int score) {
        for (int i = 0; i < contenu.length; i++) {
            if (contenu[i][0].equals(pseudo) && Integer.valueOf(contenu[i][1]) < score) {
                contenu[i][1] = String.valueOf(score);
                return;
            }
        }
    }

    /**
     * Retourner vrai si l'utilisateur existe
     * @param pseudo
     * @return
     */
    public boolean userExist(String pseudo) {
        for (int i = 0; i < contenu.length; i++) {
            if (contenu[i][0].equals(pseudo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * DÃ©finir un joueur et son score
     * @param pseudo
     * @param score
     */
    public void setUser(String pseudo, int score) {
        if (userExist(pseudo)) {
            updateScore(pseudo, score);
        } else {
            contenu = addUser(pseudo, score);
        }
    }

    /**
     * Ajouter un joueur
     * @param pseudo
     * @param score
     * @return
     */
    private String[][] addUser(String pseudo, int score) {
        String[][] newContenu = new String[contenu.length + 1][2];
        for (int i = 0; i < contenu.length; i++) {
            newContenu[i] = contenu[i];
        }
        newContenu[newContenu.length - 1][0] = pseudo;
        newContenu[newContenu.length - 1][1] = String.valueOf(score);
        return newContenu;
    }

    public void trier() {
        Arrays.sort(contenu, (a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
    }

    public String[][] getContenu() {
        return contenu;
    }
}