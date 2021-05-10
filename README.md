# DESIGN PATTERN : to_ckonsoru

**Date** : 31/03/2020

**Auteurs** : Anatole de Chauveron, Adrien Ducourthial

**Groupe** : Coffee Time

**Version** : 1.0.0

**Confidentialité** : Tous droits réservés

---

## CONCEPT
### Objectif

L’objectif de ce premier TP est d’implémenter quelques fonctionnalités avec une difficulté qui se situe au niveau de la persistance des données: il faut pouvoir choisir dans la configuration de l’application si le stockage se fait en XML ou en BDD. 
A vous de trouver une implémentation qui permet de faire cohabiter les deux systèmes de stockage de la manière la plus propre possible. Chaque personne du binôme est donc chargée d’un système de stockage différent (XML ou BDD (Postgres)).

### Description

Le cahier des charges concerne un mini gestionnaire de rendez-vous de cabinet vétérinaire en ligne de commande.

### Adresse du Git 

<https://github.com/Real-Coffee-Time/ckonsoru>

## Le Projet
### Lancement

Run le code.

### Fonctionnalités

Au lancement de l'application, un menu apparait ; chaque option nécessite une entrée de paramètres.
- Option 1  
  - paramètres : date  
  - retour : ensemble des crénaux disponibles pour la date choisie  
- Option 2  
  - paramètres : nom du client  
  - retour : liste des rendez-vous du client donné  
- Option 3  
  - paramètres : nom du vétérinaire, nom du client, date et heure du rendez-vous  
  - retour : le rendez-vous est enregistré.   
           l'un des paramètres entréne fonctionne pas : veuillez réessayer avec de nouvelles valeurs.  
- Option 4  
  - paramètres : nom du client, date et heure du rendez-vous  
  - retour : le rendez-vous a bien été supprimer  
- Option 9  
  - retour : fermeture de l'application  

### Classes

#### Class 'App'

Equivalent au main, C'est la classe que l'on éxecute pour lancer le projet.

#### Class 'Menu'

Contient le menu présentant les différents choix d'actions possibles.

#### Class 'DatabaseManager'

Contient l'ensemble des fonctionnalités et des requetes qui concernent la base de données.
  
### Ajouter une fonctionnalité

Si la fonctionnalité manipule des données :

- rajouter une classe dans query_manager pour pgsql, et pour xml selon le modèle suivant :
  - donner une Classe QueryManager
  - Renseigner la requête
  - Appelez la méthode de Query Manager correspondant au mode de persitance ainsi qu'à l'action effectuée :
    - Simple requête => ExecuteQuery
    - Modification de la base de données => ExecuteUpdate
- Dans la méthode ActionManager de la classe Menu appeler cette classe
- Ajouter une entrée au menu dans la class Main de App pour l'affichage pour l'utilisateur

## Contenu du projet

### Répertoire 'src/main'

Contient le code du projet
