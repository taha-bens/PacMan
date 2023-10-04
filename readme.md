
![Logo](https://cdn.discordapp.com/attachments/741806766014201896/1159127007095959552/Sans_titre.png?ex=651ec0a6&is=651d6f26&hm=b9bdd826d797136d6d3a438e7861799a7c7a1aaf775fc374a40261ccb15e093e&)

## 📕 Présentation du projet

Nous sommes en deuxième année de Licence d'Informatique à l'Université Paris Cité. Dans le cadre de la matière appelée Pré-Professionnalisation (PP), nous avons repris un projet non abouti. Ce projet est un PacMan. Le but de ce projet est de découvrir et d'apprendre le fonctionnement de Git, mais aussi d'apprendre à travailler davantage en équipe sur des gros projets tels que celui-ci.

Ce Pacman est un jeu d'arcade programmé en Java 17 avec JavaFX. Le projet est configuré avec Gradle utilisant le plugin JavaFX. Ce jeu est largement inspiré du jeu Pacman, un grand classique de 1980.
## 📜 Règles du jeu

- **Contrôle de Pac-Man** : Le joueur contrôle Pac-Man à travers un labyrinthe. L'objectif est de manger toutes les pac-gommes et les super pac-gommes.

- **Passage au niveau suivant** : Quand toutes les pac-gommes sont mangées, Pac-Man passe au niveau suivant.

- **Éviter les fantômes** : Quatre fantômes (Blinky, Pinky, Inky et Clyde) se déplacent autour du labyrinthe, essayant de capturer Pac-Man. Si l'un d'eux touche Pac-Man, une vie est perdue.

- **Fin de la partie** : Quand toutes les vies sont perdues, la partie est terminée.

- **Super pac-gommes** : Les super pac-gommes donnent à Pac-Man la capacité temporaire de manger les fantômes. Les fantômes deviennent bleus et se déplacent plus lentement lorsqu'une super pac-gomme est mangée.

- **Manger les fantômes** : Les fantômes mangés retournent à la maison des fantômes et reviennent à leur couleur normale. Mais ils peuvent être mangés à nouveau une fois que Pac-Man mange une autre super pac-gomme.

- **Points supplémentaires** : Manger un fantôme rapporte des points supplémentaires.

- **Fruits bonus** : Il y a aussi des fruits qui apparaissent de temps en temps, et Pac-Man peut les manger pour gagner plus de points.

## 🔧 Installation

- **Téléchargement du projet** : Vous avez deux options pour télécharger le projet :
    - **Cloner le dépôt** : Si vous avez Git installé sur votre machine, vous pouvez cloner le dépôt en utilisant la commande suivante dans votre terminal :
        ```bash
        git clone https://gaufre.informatique.univ-paris-diderot.fr/hem/pacman.git
        ```
    - **Télécharger l'archive ZIP** : Vous pouvez également télécharger le projet sous forme d'archive ZIP en cliquant sur ce lien. Une fois le fichier téléchargé, vous devrez le décompresser.

- **Installation des instances** : Si vous lancez le jeu pour la première fois et que les instances nécessaires ne sont pas déjà installées, elles seront automatiquement téléchargées. Pour ce faire, lancez le fichier `.bat` inclus dans le projet. Vous pouvez le faire en double-cliquant sur le fichier ou en exécutant la commande suivante dans votre terminal (en supposant que vous êtes dans le répertoire du projet) :
    ```bash
    ./gradlew.bat
    ```

- **Lancement du jeu** : Une fois les instances installées, le jeu se lancera automatiquement. Pour les lancements ultérieurs du jeu, vous n'aurez qu'à exécuter à nouveau le fichier `gradlew.bat`.

## 👦 Auteurs

- [@afonsoj](https://gaufre.informatique.univ-paris-diderot.fr/afonsoj)
- [@bensaad](https://gaufre.informatique.univ-paris-diderot.fr/bensaad)
- [@bonnefoy](https://gaufre.informatique.univ-paris-diderot.fr/bonnefoy)
- [@cellier](https://gaufre.informatique.univ-paris-diderot.fr/cellier)
- [@dufosse](https://gaufre.informatique.univ-paris-diderot.fr/dufosse)
- [@hem](https://gaufre.informatique.univ-paris-diderot.fr/hem)