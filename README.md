Messagerie Instantanée Client-Serveur (Java)
Ce projet implémente une application de messagerie instantanée (tchat) en ligne de commande, basée sur une architecture centralisée Client-Serveur. Il a pour objectif pédagogique la mise en pratique de la programmation réseau (Sockets TCP) et de la gestion de la concurrence (Multi-threading) en Java.

📋 Fonctionnalités
Architecture Multi-clients : Le serveur peut gérer plusieurs connexions simultanées.
Diffusion en temps réel : Les messages envoyés par un utilisateur sont instantanément redistribués à tous les clients connectés.
Gestion des connexions : Notification automatique lorsqu'un utilisateur rejoint ou quitte la conversation.

Interface Console : Interaction textuelle simple et légère.

⚙️ Architecture Technique
L'application repose sur le protocole TCP/IP pour assurer la fiabilité des échanges.

Le Serveur (ServeurSimple & GerantDeClient) :
Écoute sur un port spécifique (6000) en attente de connexions.
Pour chaque nouvelle connexion, il instancie un objet GerantDeClient dédié, exécuté dans son propre Thread. Cela permet de ne pas bloquer la boucle principale d'acceptation des clients.
Gère une liste centralisée des clients (lstGerantCli) pour effectuer la diffusion des messages.

Le Client (Client) :
Se connecte au serveur via un socket.
Utilise deux flux d'exécution parallèles :
Le Thread principal pour capturer la saisie utilisateur et l'envoyer au serveur.
Un Thread secondaire pour écouter en permanence le flux d'entrée (InputStream) et afficher les messages reçus, permettant une communication asynchrone.

🛠️ Prérequis
Java Development Kit (JDK) : Version 8 ou supérieure.

🚀 Guide d'Installation et d'Exécution
1. Compilation
Ouvrez un terminal dans le répertoire racine du projet et compilez l'ensemble des fichiers sources :
Bash
javac *.java

2. Démarrage du Serveur
Lancez d'abord le serveur. Celui-ci écoutera par défaut sur le port 6000.
Bash
java ServeurSimple
Sortie attendue : Serveur démarré sur le port 6000, en attente de clients...

3. Connexion des Clients
Dans de nouveaux terminaux (un par utilisateur), lancez le client :
Bash
java Client
Le programme vous invitera à saisir un nom d'utilisateur. Une fois connecté, vous pourrez échanger avec les autres instances.

📂 Structure du Projet
ServeurSimple.java : Classe principale du serveur. Elle initialise le ServerSocket, accepte les connexions entrantes via une boucle infinie et délègue le traitement de chaque client à un thread GerantDeClient.

GerantDeClient.java : Implémente l'interface Runnable. Cette classe gère le cycle de vie d'un client spécifique : demande du pseudo, réception des messages, et diffusion aux autres utilisateurs via la méthode diffuser().

Client.java : Classe principale du client. Elle établit la connexion socket vers localhost et gère les entrées/sorties de manière concurrente pour permettre l'envoi et la réception simultanés.

