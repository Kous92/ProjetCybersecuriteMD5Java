/*===============================================================================
  Projet Cybersécurité: stockage de mots de passe
  - Partie 1: Hachage MD5 d'une chaîne de caractères (fichier MD5.java)
  - Partie 2: Génération de 100 mots de passe aléatoires + hachage MD5 de chaque mot de passe 
   (MD5.java + PasswordGenerator.java)
  - Partie 3: Ajout d'un salt aléatoire (je propose par exemple 16 caractères) au mot de passe généré
    + Hachage MD5 (MD5.java + PasswordGenerator.java)
  - Partie 4: Génération d'un token avec H-MAC + MD5 par clé secrète (HMACMD5.java + MD5.java)
*/
package projetmd5java;

import java.util.*;

public class MD5Project 
{ 
    public static void pause()
    {
        Scanner entree = new Scanner(System.in);

        System.out.print("Appuyer sur entrée pour continuer...");
        entree.nextLine();
    }
    
    public static void main(String[] args)
    {
        System.out.print(String.format("\033[2J"));
        System.out.flush();
        
        MD5 md5;
        String chaine;
        Scanner sc = new Scanner(System.in);
        boolean ok = false;
        
        // Partie 1: Hachage MD5
        System.out.println("====================================================================");
        System.out.println("=  Projet Cybersécurité: Stockage de mots de passe et Hachage MD5  =");
        System.out.println("=     Programme Java créé par Koussaïla BEN MAMAR, Janvier 2018    =");
        System.out.println("=         M1 EFREI Promo 2019, majeure Software Engineering        =");
        System.out.println("====================================================================");
        System.out.println();
        
        System.out.println("******************************************");
        System.out.println("**  Partie 1: Hachage MD5 d'une chaîne  **");
        System.out.println("******************************************");
        System.out.println();
        
        do
        {
            System.out.print(">>> Saisissez une chaîne à hacher: ");
            chaine = sc.nextLine();
            
            if (chaine.length() < 1)
            {
                System.out.println(" -> ERREUR: vous n'avez rien saisi.");
                // On vide la ligne avant d'en lire une autre
                sc.nextLine();
                
                ok = false;
            }
            else
            {
                ok = true;
            }
            
        } while (ok == false);
        
        md5 = new MD5(chaine);
        System.out.println("> Message à hacher: " + md5.getMessageInitial());
        md5.hachageMD5();
        
        System.out.println(" * Taille en octets: " + md5.getLongueurMessageOctets());
        System.out.println(" * Taille en bits: " + md5.getLongueurMessageOctets() * 8 + "\n");
        System.out.println("> MD5: " + md5.getMessageMD5());
        
        pause();
        
        // Partie 2: Génération de 100 mots de passe + hachage MD5 (de 8 à 30 caractères)
        System.out.println();
        System.out.println("************************************************************");
        System.out.println("**  Partie 2: Génération de 100 mots de passe aléatoires  **");
        System.out.println("************************************************************");
        System.out.println();
        
        String[] id = new String[100];
        String[] idMD5 = new String[100];
        Random random = new Random();
        PasswordGenerator pg = new PasswordGenerator();
        int min = 8;
        int max = 30;
        int longueurMotDePasse = 0;
        
        System.out.println("\n>>> Génération de 100 mots de passe avec hachage MD5 (mot de passe -> MD5)\n");
        
        // Génération de mots de passe de 8 à 30 caractères
        for (int i = 0; i < 100; i++)
        {
            // Taille aléatoire du mot de passe
            longueurMotDePasse = random.nextInt(max + 1 - min) + min;
            id[i] = pg.generationMotDePasse(longueurMotDePasse);
            
            MD5 hash = new MD5(id[i]);
            hash.hachageMD5();
            idMD5[i] = hash.getMessageMD5();
            
            System.out.println("> Mot de passe " + (i + 1) + ": " + id[i] + " ==> " + idMD5[i]);
        }
        
        pause();
        
        // Partie 3: Ajout du salt dans les mots de passe générés + hachage MD5
        System.out.println();
        System.out.println("***************************************************************");
        System.out.println("**  Partie 3: Ajout des salt dans les mots de passe générés  **");
        System.out.println("***************************************************************");
        System.out.println();
        
        String[] idSalt = new String[100];
        String[] idSaltMD5 = new String[100];
        
        // Génération de mots de passe de 8 à 30 caractères
        for (int i = 0; i < 100; i++)
        {
            String salt = pg.generationSalt();
            System.out.println("> Mot de passe " + (i + 1) + ": " + id[i] + ", salt: " + salt);
            
            idSalt[i] = salt + id[i]; // Concaténation: salt + mot de passe aléatoire
            
            MD5 hash = new MD5(id[i]);
            hash.hachageMD5();
            idSaltMD5[i] = hash.getMessageMD5();
            
            System.out.println("  * Résultat: " + idSalt[i] + " ==> " + idSaltMD5[i] + "\n");
        }
        
        pause();

        // Partie 4: H-MAC + MD5
        System.out.println();
        System.out.println("************************************************************");
        System.out.println("**  Partie 4: Authentification H-MAC avec une clé secrète **");
        System.out.println("************************************************************");
        System.out.println();
        
        // H-MAC MD5
        System.out.println(">>> Exemple: avec les 10 premiers mots de passe générés (Salt + MD5)");
        
        for (int i = 0; i < 10; i++)
        {
            // Génération de la clé secrète
            String cle_secrete = pg.generationCleSecrete();
            String token;
            
            HMACMD5 hmac = new HMACMD5(cle_secrete); // Initialisation clé secrète
            hmac.hachageHMACMD5Base64(idSaltMD5[i]);
            token = hmac.getToken();
            
            System.out.println("> Mot de passe 1: " + idSaltMD5[i]);
            System.out.println("  * Clé secrète générée: " + cle_secrete);
            System.out.println("  * Token H-MAC MD5: " + token);
            System.out.println();
        }
    }
}
