# IMT Arena

### *CI1 FISA ITR — projet*
* **Date :** 29 octobre 2026
* **Auteurs :**
  * Quentin DOUILLY
  * Arthur BEIGNON
  * Lilou CHATELAIN

## 1. Comment exécuter le code
A la racine du projet, dans le dossier où se trouve ```pom.xml```, lancer :
```
mvn compile
mvn exec:java "-Dexec.mainClass=tournoi.Main"
```

## 2. Comment lancer les tests
Exécuter dans un terminal à la racine du projet la commande suivante :
```mvn test```.
Cette commande compile le projet et exécute les tests automatisés avec Maven. Le résultat des tests est affiché dans le terminal.

## Pour tester notre BDD
(À utiliser uniquement lorsque le programme est arrêté.)  
Exécuter dans un terminal dans la racine du projet cette commande(windows) : 
```java -cp "$env:USERPROFILE\.m2\repository\com\h2database\h2\2.2.224\h2-2.2.224.jar" org.h2.tools.Console```  

Une page web s’ouvre dans le navigateur, y remplir :
* **JDBC URL :** jdbc:h2:./data/arena
* **User Name :** sa
* **Password :** (vide)

Puis effectuer cette requête !
```SELECT * FROM RapportTournoi;```
