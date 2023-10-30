package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import config.Cell;

public class MazeLoader {



    //lecteur du labyrinthe sous format TXT
    public static String mazeReader(String fichier){
        StringBuilder modele_txt = new StringBuilder();
        try {
            File myObj = new File(fichier);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                modele_txt.append(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return modele_txt.toString();
    }

    //lecteur alternatif, fonctionne aussi
    public static String mazeLecteur(String txt) {
        String modele = "";
        try {
            modele = Files.readString(Path.of(txt), StandardCharsets.UTF_8);
            return modele;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }

    }


    //se gere du contenu de la cellule
    public static Cell.Content contenu(String modele, int chara){
        Cell.Content content = Cell.Content.NOTHING;
        if (modele.charAt(chara+1)=='.'){
            content= Cell.Content.DOT;
        }
        else if (modele.charAt(chara+1)=='*'){
            content= Cell.Content.ENERGIZER;
        }
        return content;
    }


    //crée les cellules adequates depuis le fichier TXT lu
    public static Cell[][] mazeLoader(String fichier){
        String modele = mazeReader(fichier);
        Cell[][] laby;
        laby = new Cell[15][15];
        int longueur=0;
        int hauteur=0;
        for (int chara=0; chara<modele.length(); chara++) {
            //gestion de fin de ligne
            if (modele.charAt(chara)=='/'){
                longueur=0;
                hauteur++;
            }
            //cornercell
            else if (modele.charAt(chara)=='N') {
                laby[hauteur][longueur]=Cell.CornerCell("nw", contenu(modele, chara));
            } else if (modele.charAt(chara)=='E') {
                laby[hauteur][longueur]=Cell.CornerCell("ne", contenu(modele, chara));
            } else if (modele.charAt(chara)=='S') {
                laby[hauteur][longueur] = Cell.CornerCell("se", contenu(modele, chara));
            } else if (modele.charAt(chara)=='W') {
                laby[hauteur][longueur] = Cell.CornerCell("sw", contenu(modele, chara));
            }
            //Tcell
            else if (modele.charAt(chara)=='^') {
                laby[hauteur][longueur] = Cell.TCell('n', contenu(modele, chara));
            } else if (modele.charAt(chara)=='>') {
                laby[hauteur][longueur] = Cell.TCell('e', contenu(modele, chara));
            } else if (modele.charAt(chara)=='v') {
                laby[hauteur][longueur] = Cell.TCell('s', contenu(modele, chara));
            } else if (modele.charAt(chara)=='<') {
                laby[hauteur][longueur] = Cell.TCell('w', contenu(modele, chara));
            }
            //PipeCell
            else if (modele.charAt(chara)=='=') {
                laby[hauteur][longueur] = Cell.PipeCell('h', contenu(modele, chara));
            }
            else if (modele.charAt(chara)=='|') {
                laby[hauteur][longueur] = Cell.PipeCell('v', contenu(modele, chara));
            }
            //UCell
            else if (modele.charAt(chara)=='[') {
                laby[hauteur][longueur] = Cell.UCell('e', contenu(modele, chara));
            } else if (modele.charAt(chara)==']') {
                laby[hauteur][longueur] = Cell.UCell('w', contenu(modele, chara));
            } else if (modele.charAt(chara)=='U'){
                laby[hauteur][longueur] = Cell.UCell('n', contenu(modele, chara));
            }

            //EntierCell
            else if (modele.charAt(chara)=='#'){
                laby[hauteur][longueur] = Cell.EntierCell(false,contenu(modele,chara));
            }

            if (modele.charAt(chara)==' ') {
                longueur++;
            }
        }
        return laby;

    }


}