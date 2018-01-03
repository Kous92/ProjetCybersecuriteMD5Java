/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmd5java;

import java.util.*;

/**
 *
 * @author Kous92
 */
public class MD5
{
    // Constantes magiques: registres
    private static final int A = 0x67452301;
    private static final int B = (int) 0xEFCDAB89L;
    private static final int C = (int) 0x98BADCFEL;
    private static final int D = 0x10325476;
    
    // Tableaux de constantes spécifiques
    private static final int[] S = {7, 12, 17, 22, 5, 9, 14, 20, 4, 11, 16, 23, 6, 10, 15, 21};
    private static final int[] K = new int[64];
    
    // Le bloc static permet d'initialiser un tableau qui sera non modifiable ensuite
    static
    {
        for (int i = 0; i < 64; i++)
        {
            K[i] = (int)(long)((1L << 32) * Math.abs(Math.sin(i + 1)));
        }
    }
    
    // Messages 
    private final String messageInitial;
    private String messageMD5;
    private final byte[] message;
    private byte[] md5;  
    
    // Éléments intermédiaires
    private final int longueurMessageOctets;
    private final int nombreBlocs;
    private final int longueurTotale; 
    private long longueurMessageBits;
    private byte[] paddingOctets; 
    private int[] buffer;
    
    // Constructeur: Initialisation du message 
    public MD5(byte[] message)
    {
        this.message = message;
        messageInitial = new String(message);
        
        longueurMessageOctets = message.length;
        nombreBlocs = ((longueurMessageOctets + 8) >>> 6) + 1;
        longueurTotale = nombreBlocs << 6;
    }
    
    // Constructeur: Initialisation du message 
    public MD5(String messageInitial)
    {
        this.messageInitial = messageInitial;
        message = this.messageInitial.getBytes();
        
        longueurMessageOctets = message.length;
        nombreBlocs = ((longueurMessageOctets + 8) >>> 6) + 1;
        longueurTotale = nombreBlocs << 6;
    }
    
    private int F(int b, int c, int d)
    {
        // (b et c) ou (non b et d)
        return (b & c) | (~b & d);
    }
    
    private int G(int b, int c, int d)
    {
        // (b et d) ou (c et non d)
        return (b & d) | (c & ~d);
    }
    
    private int H(int b, int c, int d)
    {
        // b xor c xor d
        return b ^ c ^ d;
    }
    
    private int I(int b, int c, int d)
    {
        // c xor (b ou non d)
        return c ^ (b | ~d);
    }
    
    private void byteToHexString()
    {
        StringBuilder messageHexa = new StringBuilder();
        
        for (int i = 0; i < md5.length; i++)
        {
            // Concaténation: format %02X pour les caractères hexadécimaux
            messageHexa.append(String.format("%02X", md5[i] & 0xFF));
        }
        
        messageMD5 = messageHexa.toString();
    }

    public String getMessageInitial()
    {
        return messageInitial;
    }

    public String getMessageMD5() 
    {
        return messageMD5;
    }

    public int getLongueurMessageOctets() 
    {
        return longueurMessageOctets;
    }

    public long getLongueurMessageBits() 
    {
        return longueurMessageBits;
    }

    public byte[] getMd5() 
    {
        return md5;
    }
    
    public void hachageMD5()
    {
        // Début du hachage
        paddingOctets = new byte[longueurTotale - longueurMessageOctets];
        paddingOctets[0] = (byte) 0x80;
        longueurMessageBits = (long) longueurMessageOctets << 3; // Longueur du message en format binaire
        
        // Padding
        for (int i = 0; i < 8; i++)
        {
            int valeur = paddingOctets.length - 8 + i;
            paddingOctets[paddingOctets.length - 8 + i] = (byte) longueurMessageBits;
            longueurMessageBits >>>= 8;
        }
        
        // Copie des registres
        int a = A; // 0x67452301
        int b = B; // 0xEFCDAB89
        int c = C; // 0x98BADCFE
        int d = D; // 0x10325476
        
        buffer = new int[16];
       
        // Découpage des blocs 
        for (int i = 0; i < nombreBlocs; i++)
        {
            int index = i << 6;
            
            for (int j = 0; j < 64; j++, index++)
            {
                buffer[j >>> 2] = ((int) ((index < longueurMessageOctets) ? message[index] : paddingOctets[index - longueurMessageOctets]) << 24) | (buffer[j >>> 2] >>> 8); 
            }
                
            int registreA = a;
            int registreB = b;
            int registreC = c;
            int registreD = d;
            
            for (int j = 0; j < 64; j++)
            {
                int division16 = j >>> 4;
                int f = 0;
                int g = j;
                
                switch (division16)
                {
                    case 0:
                    f = F(b, c, d);
                    break;
                    
                    case 1:
                    f = G(b, c, d);
                    g = (g * 5 + 1) & 0x0F;
                    break;
                    
                    case 2:
                    f = H(b, c, d);
                    g = (g * 3 + 5) & 0x0F;
                    break;
                    
                    case 3:
                    f = I(b, c, d);
                    g = (g * 7) & 0x0F;
                    break;
                }
                
                int temp = b + Integer.rotateLeft(a + f + buffer[g] + K[j], S[(division16 << 2) | (j & 3)]);
                a = d;
                d = c;
                c = b;
                b = temp;
            }
            
            a = a + registreA;
            b = b + registreB;
            c = c + registreC;
            d = d + registreD;
        }
        
        md5 = new byte[16];
        int compteur = 0;
        
        // Finalisation du hachage: assemblage des 4 blocs
        for (int i = 0; i < 4; i++)
        {
            int n = (i == 0) ? a : ((i == 1) ? b : ((i == 2) ? c : d));
            
            for (int j = 0; j < 4; j++)
            {
                md5[compteur++] = (byte) n;
                n = n >>> 8;
            }
        }
        
        // Conversion du hachage (byte) en chaîne hexadécimale
        byteToHexString();
    }
}