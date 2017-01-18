package model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import controller.DB;

public class Encrypt {
	
	public static String encrypt(String unencryptedText) {
		String key = DB.getInstance().getAesKey();
		if(key != null){
			SecretKey myDesKey;
			
			try {
				myDesKey = new SecretKeySpec(new String(key).getBytes(), "AES");
	
		        Cipher desCipher;
		        desCipher = Cipher.getInstance("AES");
	
	
		        byte[] text = unencryptedText.getBytes();
	
	
		        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
		        byte[] textEncrypted = desCipher.doFinal(text);
	
		        String s = new String(new Base64().encode(textEncrypted));
		        return s;
				
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}else{
			return null;
		}
        
	}
	
	public static String unencrypt(String encryptedText) {
		String key = DB.getInstance().getAesKey();
		if( key != null){
			SecretKey myDesKey;
			
			try {
				myDesKey = new SecretKeySpec(new String(key).getBytes(), "AES");
				
				Cipher desCipher = Cipher.getInstance("AES");
				desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
		        byte[] textDecrypted = desCipher.doFinal(new Base64().decode(encryptedText.getBytes()));
	
		        String s = new String(textDecrypted);
		        return s;
		        
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
			return null;
		}
		else{
			return null;
		}
	}
	
}
