![Logo](src/main/resources/projet_icone.png)

## 📕 Présentation du projet

Nous sommes en deuxième année de Licence d'Informatique à l'Université Paris Cité. Dans le cadre de la matière appelée Pré-Professionnalisation (PP), nous avons repris un projet non abouti. Ce projet est un PacMan. Le but de ce projet est de découvrir et d'apprendre le fonctionnement de Git, mais aussi d'apprendre à travailler davantage en équipe sur des gros projets tels que celui-ci.

Ce Pacman est un jeu d'arcade programmé en Java 17 avec JavaFX. Le projet est configuré avec Gradle utilisant le plugin JavaFX. Ce jeu est largement inspiré du jeu Pacman, un grand classique de 1980.
## 📜 Règles du jeu

- **But du jeu** : Le joueur contrôle Pac-Man à travers un labyrinthe et a pour objectif de manger toutes les pac-gommes.

- **Difficultées** : Quatre fantômes (Blinky, Pinky, Inky et Clyde) se déplacent autour du labyrinthe, essayant de capturer Pac-Man. Si l'un d'eux attrape Pac-Man, PacMan perd une vie.

- **Niveaux** : 

- **Fin de la partie** : Quand toutes les pac-gommes sont mangées, Pac-Man passe au niveau suivant avec des fantômes plus compétent. Si PacMan perd toutes ses vies avant d'avoir mangé toutes les pac-gums, la partie se termine.

- **Super pac-gommes** : Les super pac-gommes rendent Pac-Man plus puissant temporairement, il se déplace plus rapidement et peut manger les fantômes. Quand Pacman mange un fantôme, il retourne dans la maison des fantômes.

- **Bonus** : Manger un fantôme ou un fruit rapporte des points supplémentaires à PacMan. 


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
- 
## Vidéo (présentation du projet)

https://up75-my.sharepoint.com/:v:/g/personal/alexis_dufosse_etu_u-paris_fr/EVhM2WwtLGZBnXHxtdWZlfUBici3c8bvjcDOqmcU3kNYVg?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=oQJKAv