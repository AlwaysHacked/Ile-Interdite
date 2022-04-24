# Forbidden Island, Projet L2 Info

Ce projet a été réalisé dans le cadre du cours **PO&GL** par Pierre Derathe et Seryozha Hakobyan.

## Description du projet

Nous avons réussi à implémenter le jeu correctement. Au cours de la réalisation, nous avons été obligé de faire certains choix, les voici ;

- Un joueur = une case ! Il n'y a qu'un seul joueur par case.
- Le joueur doit être sur la même case que l'artefact, pour pouvoir le récupérer.
- Une fois l'un des joueurs est entouré par des cases `Submergée`s, le jeu est perdu.
- Une fois tous les artefacts sont collectés, les joueurs peuvent quitter l'île un par un, en se déplaçant sur la case `Heliport`.
- Pour gagner tous les joueurs doivent avoir quitté l'île avec l'`Heliport`.
- Pour effectuer une action, l'on clique sur la case où l'action se déroule. 
  - Pour *bouger* à la case voisine on clique non pas sur le joueur, mais sur la case
  - Pour *prendre un artefacte*, on se place sur la case, on clique sur la case, puis choisit `Fouille`, dans le menu ouvrant
  - Pour *sécher* une case, on clique sur la case (voisine ou elle-même)

## Organisation du projet

Le projet est organisé de manière suivante :

- `Main` : l'exécutable.

- **`Controlleur`** : Ce dossier gère les actions effectuées sur la fenêtre, contient les fichiers suivants :
  - `Controlleur` : Lie les commandes de fenêtre par un pointeur au code
  - `ControlleurCase` : Lie un controlleur à chaque **Case**
  - `ControlleurChoix` : Lie les commandes **Bouger**, **Sécher** et **Fouille** au code 

- **`Modele`** : Ce dossier-ci contient les fichiers suivants :
  - `Artefact` : Gestion des artefacts, hérite de `Item`
  - `Case` : *Hérite d'`Observable`*. Gestion des cases, ainsi que de l'énumération State : **SEC**, **INONDE**, **SUBMERGE**
  - `Cle` : Gestion des clés, hérite de `Item` également
  - `Ile` : *Hérite d'`Observable`*. La grille du jeu, le fichier le plus chargé
  - `Item` : Classe mère de `Artefact` et de `Cle`, contient également la classe énumerée **Type**
  - `Joueur` : S'occupe de chaque joueur de jeu

- **`Obs`** : Le code contenu dans ce dossier "veille attentivement" au changement à effectuer sur la fenêtre. 
  - `Observable` : **Abstract**
  - `Observser` : **Interface**

- **`Vue`**  : Ce dossier contient toutes les images que l'on voit s'afficher (joueurs, cases, artefact...) ainsi que le code :
    - `CVue` : Gestion de la fenêtre
    - `VueCommande` : Gestion d'affichage de bouton **Tour Suivant**
    - `VueGrille` : Gestion d'affichage de la grille avec ses composantes (joueurs, case avec ses états, ...)
