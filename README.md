# IMT Arena

### *CI1 FISA ITR — projet*
* **Date :** 29 octobre 2026
* **Auteurs :**
  * Quentin DOUILLY
  * Arthur BEIGNON
  * Lilou CHATELAIN

## 1. Comment exécuter le code
#Todo

## 2. Comment lancer les tests
#Todo

## Diagramme des classes
A METTRE DANS LE DOSSIER DE CONCEPTION
[Lien ici](https://lucid.app/lucidchart/94eb4ef8-a7c6-4dc7-894b-70434a4ce9ae/edit?viewport_loc=-2291%2C1444%2C4926%2C2239%2CHWEp-vi-RSFO&invitationId=inv_6cf00406-66c3-43ed-a5d5-971ae7c57c9c)

## Pour tester notre BDD
A METTRE DANS LE DOSSIER DE CONCEPTION ???
Exécuter dans un terminal dans la racine du projet cette commande(windows) : 
```java -cp "$env:USERPROFILE\.m2\repository\com\h2database\h2\2.2.224\h2-2.2.224.jar" org.h2.tools.Console```  

Une page web s’ouvre dans le navigateur, y remplir :
* **JDBC URL :** jdbc:h2:./data/arena
* **User Name :** sa
* **Password :** (vide)

Puis effectuer cette requête !
```SELECT * FROM RapportTournoi;```
