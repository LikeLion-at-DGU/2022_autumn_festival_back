package likelion.festival.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {
    public String getSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[20];

        sr.nextBytes(salt);

        StringBuffer sb = new StringBuffer();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public String getEncrypt(String pwd, String salt){
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((pwd+salt).getBytes());
            byte[] pwsalt = md.digest();

            for (byte b : pwsalt) {
                sb.append(String.format("%02x",b));
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
