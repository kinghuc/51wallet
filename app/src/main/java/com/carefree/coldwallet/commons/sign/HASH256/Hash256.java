package com.carefree.coldwallet.commons.sign.HASH256;

import org.apaches.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by wb on 2018/3/23.
 */
public class Hash256 {
	
	public Hash256(){}

	/**
	 * 返回hash256 加密后的字节数组
	 * @param text
	 * @return byte[]
	 */
	public static  byte[] Encrypt(String text){
		String result = null;
		byte [] data =null;
		try {
			//创建加密对象
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			//传入加密数据
			messageDigest.update(text.getBytes());
			//返回加密后的字节数组
			data = messageDigest.digest();
			
			result = Hex.encodeHexString(data);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.getBytes();
	}
	
	/**
	 * 返回hash256 加密后的字节数组
	 * @param text
	 * @return byte[]
	 */
	public static  byte[] Encrypt(byte [] text){
		String result = null;
		byte [] data =null;
		try {
			//创建加密对象
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			//传入加密数据
			messageDigest.update(text);
			//返回加密后的字节数组
			data = messageDigest.digest();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	/**
	 * 返回hash256 加密后的字节数组
	 * @param text
	 * @return byte[]
	 */
	public static  byte[] EncryptHex(String text){
		String result = null;
		byte [] data =null;
		try {
			//创建加密对象
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			//传入加密数据
			messageDigest.update(new BigInteger(text,16).toByteArray());
			//返回加密后的字节数组
			data = messageDigest.digest();
			
			result = Hex.encodeHexString(data);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.getBytes();
	}
	
	//-------------------------------------------------
	
	public static void main(String[] args) {
		Hash256 temp = new Hash256();
//		byte [] bytes = temp.EncryptHex("006C8D7DCB62A9ED705D7ABFBF28730BAA6FFE2A51");
		byte [] bytes2 = temp.Encrypt(new BigInteger("006C8D7DCB62A9ED705D7ABFBF28730BAA6FFE2A51",16).toByteArray());
				
		System.out.println(Hex.encodeHexString(bytes2));
//		System.out.println(new String(bytes));
		
	}
}
