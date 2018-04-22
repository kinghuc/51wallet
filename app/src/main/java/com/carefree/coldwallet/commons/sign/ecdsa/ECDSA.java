package com.carefree.coldwallet.commons.sign.ecdsa;

import org.apaches.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * ECC密钥生成和数字签名
 */
public class ECDSA {
	/**
	 * 生成32位16进制私钥（即256位二进制私钥）
	 * @return String
	 */
	public static String generatePrivateKey() {
		byte [] random  = SecureRandom.getSeed(32);
		return new String(Hex.encodeHex(random));
	}
	
	/**
	 * 由私钥生成16进制公钥，压缩格式，33字节的公钥，用16进制表示长度位66位	<br/>
	 * 参数为16进制的私钥
	 * @param privateKey
	 * @return
	 */
	public static String computePublicKey(String privateKey) {
		byte [] publicKey = K256KeyPair.getPublic(new BigInteger(privateKey,16));
		return Hex.encodeHexString(publicKey);
	}
	
	/**
	 * 返回非压缩格式16进制公钥,非压缩格式，65字节的公钥，用16进制表示长度130位	 <br/>
	 * 参数为16进制的私钥
	 * @param privateKey
	 * @return
	 */
	public static String computePublicKeyWithoutCompressed(String privateKey) {
		byte [] publicKey = K256KeyPair.getPublicWithoutCompressed(new BigInteger(privateKey,16));
		String ss = Hex.encodeHexString(publicKey);
		return ss;
	}
	
	/**
	 * 签名函数 sign(带签名数据，16进制私钥)
	 * @param data
	 * @param privateKey
	 * @return
	 */
	public static String sign(byte [] data, String privateKey) {
		BigInteger privateKeyInt = new BigInteger(privateKey,16);
		// 签名
		byte [] sign = K256KeyPair.signHash(data, privateKeyInt);
		return Hex.encodeHexString(sign);
	}
	
	/**
	 * 验证 verify(带签名数据，数字签名，16进制公钥)
	 * @param data
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	public static boolean verify(byte [] data, String sign, String publicKey) {
		BigInteger publicKeyInt = new BigInteger(publicKey,16);
		byte [] signData = new BigInteger(sign,16).toByteArray();
		// 验证֤
		boolean result = K256KeyPair.verify(data, signData, publicKeyInt);
		return result;
	}
	
	/**
	 * 从本地读取私钥
	 * @param path
	 */
	public static String loadPrivateKeyFormLocal(String path) {
		File file = new File(path);
		FileInputStream input = null;
		byte [] priBytes = new byte[(int)file.length()];
		StringBuffer string = new StringBuffer();
		
		try {
			int len = 0;
			input = new FileInputStream(file);
			while(-1 != (len = input.read(priBytes))) {
				string.append(new String(priBytes));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return string.toString();
	}
	
	/**
	 * 将私钥保存到本地的指定位置<br/> 
	 * savePrivateKeyToLocal(指定保存位置，16进制私钥)
	 * 
	 * @param privateKeyPath
	 * @param privateKey
	 */
	public static boolean savePrivateKeyToLocal(String privateKeyPath,String privateKey) {
		File file = new File(privateKeyPath);
		OutputStream out = null;
		try {
			File parentFile = file.getParentFile();
			if (parentFile == null) {
				return false;
			}
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file,false);
			byte []  s = privateKey.getBytes();
			out.write(s);
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除本地私钥地址文件
	 * @param privateKeyPath
	 * @return
	 */
	public static boolean deletePrivateKeyFromLocal(String privateKeyPath) {
		File file = new File(privateKeyPath);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + privateKeyPath + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + privateKeyPath + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + privateKeyPath + "不存在！");
			return false;
		}
	}
	
	public static void main(String[] args) {
		ECDSA test = new ECDSA();
		// 生成随机数私钥
		String privateKey0 = test.generatePrivateKey();
		
		// 将私钥保存到本地
//		test.savePrivateKeyToLocal("E:/privateKey.dat", privateKey);
		
		// 从本地读取私钥
		String privateKey = "5502767ae95def4039ef1ee9f4ce46d25b0b1b240621e8b9d7743afa2a3eb5ae";//test.loadPrivateKeyFormLocal("E:/privateKey.dat");//
		
		// 根据私钥计算公钥
		String publicKey = test.computePublicKey(privateKey);
		System.out.println("长度："+publicKey.length()+":"+publicKey);
		
		// 根据私钥计算非压缩公钥
		String publicKey2 = test.computePublicKeyWithoutCompressed(privateKey);
		System.out.println("长度："+publicKey2.length()+":"+publicKey2);
		
		String data = "Hello ECC"; 
		// 生成数字签名
		String signdata = test.sign(data.getBytes(), privateKey);
		// 验证签名
		boolean result = test.verify(data.getBytes(), signdata, publicKey);

		System.out.println(result);
	}
}
