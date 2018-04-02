# Manuel d'utilisation

Téléchargez python et notre code RetroFire sur https://github.com/kistria/RetroFire


## Configurations

Placez vous sur un terminale et **installez flask et flask-restful** avec ces commandes :
pip install flask
pip install flask-restful

Dans le fichier Retrofire/Server/serverHighScore.py (en bas de page) , **changez host**='192.168.0.18' 
avec **votre propre adresse IP** (ip config -> ipv4).

Dans la classe RetroFire/app/src/main/java/com/example/kevin/retrofire/ScoreRestService.java (en champ),
changez l'url = "http://192.168.0.18:5002/" par "http://**votre_adresse_ip**:5002/"

## Lancement du serveur

Pour lancer le serveur, ouvrez un terminal, placez vous dans RetroFire/Server/ et tapez la commande **"python serverHighScore.py"**
Si vous testez l'application sur votre smartphone, connectez vous avec le même wifi que l'ordinateur qui fait tourner le serveur. Si c'est un émulateur sur cet ordinateur, il n'y a rien à faire.

## Lancement du jeu

Ouvrez le code RetroFire téléchargé sur github avec AndroidStudio. Lancez l'application avec le bouton lecture.

## Comment jouer
Pour jouer, il suffit de cliquer sur le bouton "jouer", choisir sa difficulté et taper son pseudo.
Vous avez aussi la possibilité de voir les meilleurs scores avec le bouton "meilleurs scores"
```
