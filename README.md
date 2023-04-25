# ProjetAgile

Nos classes ce trouve dans le dossier src/main/java/fr/icom/info/m1/balleauprisonnier_mvn/.
Notre configuration Maven est dans Pom.xml.

Afin de jouer il faut avoir une configuration Maven avec Javafx sur Eclipse, avec App comme main.
La version 11.0.12 de javac est requise pour pouvoir lancer le jeu (Nous avons du downgrader les version de nos ordinateurs personnels pour pouvoir tester le projet depuis les ordi de la fac)

## Déroulement du jeu:
  Il y a deux équipes sur le terrain. Une équipe est constituée d'un joueur et 2 bots.
  Une fois le bouton start cliqué, Une balle "rapide" et "lente" spawn aléatoirement dans les camps, mais 1 camp devrait forcemment avoir une des deux balles. Les      joueurs visent et tirent. Les bots peuvent se déplacer de manière aléatoire ou suivre le joueur. A chaque fois qu'un joueur est touché par une balle lancée ou touchent le mur du terrain, la balle est donnée à l'équipe adverse. La partie se termine une fois qu'une équipe a éliminé tous les joueurs de l'autre équipe.

## Modes IA :
  Stratégie "blend" : Les bots se déplacent et visent de manière aléatoire
  Stratégie "follow" : Les bots suivent les mêmes déplacement que le joueur de leurs équipes
  
## Les touches
### Pour l'équipe du bas :
Les flèches gauche et droite pour ce déplacer de manière horizontale, les flèches bas et haut pour viser. le 0 du numPad pour tirer. + et - du Numpad pour changer les modes des IA.

### Pour l'équipe du haut :
Q et D pour ce déplacer, Z et S pour viser. la barre espace pour tirer. E et A pour changer les modes des IA.

## Les boutons de contrôle :
Il y a dans le jeu un bouton "Start" dans la barre de menu en haut à droite qui permet de commencer la partie.
Un autre bouton est présent aussi en haut à droite avec un bouton "Pause" qui permet de pauser la partie, son libellé se transforme en "Resume" quand la partie est pausée.

## Difficultés et bugs présent :
Mettre deux balles différentes sur le terrain au final rend les mécanismes beaucoup plus complexes à gérer et amènent beaucoup de bugs.

  -Les collisions sont un peu "hasardeuses" nos flags indiquant dans quel camp la balle est et quel joueur à quel type de ball sont mal gérés, ce qui amène certaines     balles à être tirées dans la mauvaise direction ou bien que les joueurs ayant tirés la balle disparaissent suite à une vérification de collision dans le mauvais        camps
  -Les spawns de balles sont censé être aléatoires (une balle doit être spawnée dans l'équipe 1 et une autre de l'équipe 2) mais dans certains cas (plus spécifiquement avec le random entre 0 et 1) 2 balles spawns dans les mains du joueur du haut
  - A certains respawn de balles, les deux balles vont arriver dans la main d'un personnage
  - Quand les joueurs du bas se déplacent à droite une fois, même en relachant la touche "RIGHT", le joueur va continuer à avancer jusqu'à toucher le mur

  - Les angles ne sont pas pris en compte pour les tirs même si le fonctionnement semble bon (voir throw_Good)
  - L'implémentation du ProjectileFactory n'est pas la "vraie" mais le vrai script ne marchait pas (peut être lié à notre version de Java) (voir FactoryGood regroupant tous les scripts d'implémentation du design pattern Factory)
  
