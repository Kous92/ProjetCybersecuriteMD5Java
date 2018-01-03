/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmd5java;

import java.security.SecureRandom;
import java.util.*;

/**
 *
 * @author Kous92
 */
public class PasswordGenerator 
{
    private static SecureRandom random = new SecureRandom();
 
    // Dictionnaires
    private static final String majuscules = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String minuscules = "abcdefghijklmnopqrstuvwxyz";
    private static final String chiffres = "0123456789";
    private static final String special = "!@#$%^&*_=+-/";
    
    private static final String dictionnaire1 = majuscules + minuscules;
    private static final String dictionnaire2 = majuscules + minuscules + chiffres;
    private static final String dictionnaire3 = majuscules + minuscules + chiffres + special;
    
    public String generationMotDePasseV1(int longueur) 
    {
        String resultat = "";
        
        for (int i = 0; i < longueur; i++) 
        {
            int index = random.nextInt(dictionnaire1.length());
            resultat += dictionnaire1.charAt(index);
        }
        
        return resultat;
    }
    
    public String generationMotDePasseV2(int longueur) 
    {
        String resultat = "";
        
        for (int i = 0; i < longueur; i++) 
        {
            int index = random.nextInt(dictionnaire2.length());
            resultat += dictionnaire2.charAt(index);
        }
        
        return resultat;
    }
    
    public String generationMotDePasseV3(int longueur) 
    {
        String resultat = "";
        
        for (int i = 0; i < longueur; i++) 
        {
            int index = random.nextInt(dictionnaire3.length());
            resultat += dictionnaire3.charAt(index);
        }
        
        return resultat;
    }
}
