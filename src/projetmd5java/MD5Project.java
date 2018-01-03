package projetmd5java;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kous92
 */
public class MD5Project 
{
    public static void main(String[] args)
    {
        String[] id = new String[100];
        String[] idMD5 = new String[100];
        
        int longueurMotDePasse = 0;
        Random random = new Random();
        PasswordGenerator pg = new PasswordGenerator();
        int min = 8;
        int max = 30;
        
        String output = "";
        String str = "abcdefghijklmnopqrstuvwxyz";
        
        MD5 md5 = new MD5(str);
        
        System.out.println(">>> Hachage MD5\n");
        System.out.println("> Message à hacher: " + md5.getMessageInitial());
        
        md5.hachageMD5();
        
        System.out.println(" * Taille en octets: " + md5.getLongueurMessageOctets());
        System.out.println(" * Taille en bits: " + md5.getLongueurMessageOctets() * 8 + "\n");
        System.out.println("> MD5: " + md5.getMessageMD5());
        
        System.out.println("\n\n>>> Génération de 100 mots de passe avec hachage MD5");
        
        // Génération de mots de passe de 8 à 30 caractères
        for (int i = 0; i < 100; i++)
        {
            longueurMotDePasse = random.nextInt(max + 1 - min) + min;
            id[i] = pg.generationMotDePasseV3(longueurMotDePasse);
            
            MD5 hash = new MD5(id[i]);
            hash.hachageMD5();
            idMD5[i] = hash.getMessageMD5();
            
            System.out.println("> Mot de passe " + (i + 1) + ": " + id[i] + " -> " + idMD5[i]);
        }    
    }
}