package com.carefree.coldwallet.commons.sign.bitaddress;

import com.carefree.coldwallet.commons.sign.HASH256.Hash256;
import com.carefree.coldwallet.commons.sign.RipeMD160.RipeMD160;

import java.math.BigInteger;


/**
 * Created by wb on 2018/3/23.
 */
public class BitAddress {
	
	/**
	 * 参数为16进制非压缩公钥，返回比特币地址
	 * @param publicKey
	 * @return
	 */
	public static String calculateAddress(String publicKey) {
		// 1. 计算非压缩公钥的hash256
		String publicKeyHashStr = new String(Hash256.EncryptHex(publicKey));
		System.out.println("publicKeyHashStr:"+publicKeyHashStr);
		
		// 2. 计算 RIPEMD-160 哈希值
		String publicKeyRIPEMD160Str = RipeMD160.HashRipeMD160(new BigInteger(publicKeyHashStr,16).toByteArray());
		System.out.println("publicKeyRIPEMD160Str:"+publicKeyRIPEMD160Str);
		
		// 3. 取上一步结果，前面加入地址版本号 (比特币主网版本号“0x00”)
		publicKeyRIPEMD160Str = "00"+publicKeyRIPEMD160Str;
		
		// 4. 计算 SHA-256 哈希值
		String hash256Once = new String(Hash256.EncryptHex(publicKeyRIPEMD160Str));
		
		// 5. 再计算一下 SHA-256 哈希值
		String hash256Twice = new String(Hash256.EncryptHex(hash256Once));
		
		// 6. 取上一步结果的前4个字节(8位十六进制)
		String fore4Bite = hash256Once.substring(0, 8);
		System.out.println("fore4Bite:"+fore4Bite);
		
		// 7. 把这4个字节加在第五步的结果后面，作为校验(这就是比特币地址的16进制形态)。
		String verification = publicKeyRIPEMD160Str+fore4Bite;
		System.out.println("verification:"+verification);
		
		// 8. 用base58表示法变换一下地址(这就是最常见的比特币地址形态)
		String bitAddress = Base58.encode(new BigInteger(verification,16).toByteArray());
//		String bitAddress = Base58.encode("00010966776006953D5567439E5E39F86A0D273BEED61967F6".getBytes());
		return bitAddress;
	}

//	public static void main(String[] args) {
//		BitAddress s = new BitAddress();
//		String privateKey = ECDSA.generatePrivateKey();
//		String publicKey = ECDSA.computePublicKeyWithoutCompressed(privateKey);
//		System.out.println(publicKey);
//		String result = s.calculateAddress(publicKey);
//		System.out.println("0x"+result);
//	}
}
