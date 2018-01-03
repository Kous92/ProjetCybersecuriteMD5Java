# Projet Cybersécurité MD5 en Java
- Projet terminé à 100%
  Pour exécuter le programme, clonez le répertoire entier, ouvrez-le avec NetBeans et exécutez-le.

4 parties dans ce projet:
- Partie 1: Implémentation de l'algorithme MD5 (MD5.java)
  -> md5(string)

- Partie 2: Génération de 100 mots de passe aléatoires de longueurs différentes variant de 8 à 30 caractères + hachage MD5 (PasswordGenerator.java + MD5.java)
  -> md5(salt + mot de passe)

- Partie 3: Génération d'un salt aléatoire de 16 caractères par mot de passe généré + hachage MD5 (PasswordGenerator.java + MD5.java)
  -> md5(salt + mot de passe)

- Partie 4: Implémentation de l'algorithme H-MAC avec MD5 (Authentification par clé secrète) (HMACMD5.java)
  -> Génération clé secrète aléatoire par mot de passe
  -> Génération token H-MAC MD5 d'un hachage MD5 d'un mot de passe (md5(salt + mot de passe))

>>> Démonstation des 4 parties dans le fichier MD5Project.java (main)
