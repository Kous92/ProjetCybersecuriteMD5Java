/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmd5java;

import java.util.Base64;

/**
 *
 * @author Kous92
 */
public class HMACMD5 
{
    private final int tailleBloc;
    
    private String key;
    private byte[] iKey;
    private byte[] innerPad;
    private byte[] outerPad;
    private String token;
    
    public HMACMD5(String key)
    {
        this.tailleBloc = 64;
        this.key = key;
        
        initialiserCle();
    }

    public String getKey() 
    {
        return key;
    }

    public void setKey(String key) 
    {
        this.key = key;
    }

    public String getToken() 
    {
        return token;
    }
    
    private void initialiserCle()
    {   
        byte[] cleHash = key.getBytes();
        
        if (cleHash.length > tailleBloc)
        {
            MD5 hash = new MD5(cleHash);
            hash.hachageMD5();
            
            this.iKey = hash.getMd5();
        }
        else
        {
            this.iKey = cleHash;
        }
        
        this.miseAJourBufferIOPad();
    }
    
    private byte[] hachageHMACMD5(byte[] buffer)
    {
        return this.hachage(buffer);
    }
    
    private byte[] hachage(byte[] buffer)
    {
        MD5 hash1 = new MD5(this.combinaisonBlocs(this.innerPad, buffer));
        hash1.hachageMD5();
        
        MD5 hash2 = new MD5(this.combinaisonBlocs(this.outerPad, hash1.getMd5()));
        hash2.hachageMD5();
        
        return hash2.getMd5();
    }
    
    private void miseAJourBufferIOPad()
    {
        if (this.innerPad == null)
        {
            this.innerPad = new byte[tailleBloc];
        }
        
        if (this.outerPad == null)
        {
            this.outerPad = new byte[tailleBloc];
        }
        
        for (int i = 0; i < tailleBloc; i++)
        {
            this.innerPad[i] = 54;
            this.outerPad[i] = 92;
        }
        
        for (int i = 0; i < this.iKey.length; i++)
        {
            byte[] s1 = this.innerPad;
            int s2 = i;
            
            s1[s2] ^= this.iKey[i];
            
            byte[] s3 = this.innerPad;
            int s4 = i;
            
            s3[s4] ^= this.iKey[i];
        }
    }
    
    private byte[] combinaisonBlocs(byte[] a, byte[] b)
    {
        byte[] fusion = new byte[a.length + b.length];
        
        for (int i = 0; i < a.length; i++)
        {
            fusion[i] = a[i];
        }
        
        for (int i = 0; i < b.length; i++)
        {
            fusion[i + a.length] = b[i];
        }
        
        return fusion;
    }
    
    public void hachageEnBase64(String buffer)
    {
        token = Base64.getEncoder().encodeToString(buffer.getBytes());
    }
    
    public void hachageHMACMD5Base64(String buffer)
    {
        byte[] bufferHash = buffer.getBytes();
        
        token = Base64.getEncoder().encodeToString(hachage(bufferHash));
    }
}
