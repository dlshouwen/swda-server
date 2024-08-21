package com.dlshouwen.swda.core.security.crypto;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * sm2 utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class Sm2Utils {
	
	/** public key */
	private final static String PUBLIC_KEY = "3059301306072a8648ce3d020106082a811ccf5501822d034200041f6567c079d1fca1d934e8170ea5ac471f3576e5a543d3118c27349710448f33120c5dc8ba3dd1cc534661e3895c8f94b94d7ca3e3fa2e2e28ac36aa8fbb6a79";
	
	/** private key */
	private final static String PRIVATE_KEY = "308193020100301306072a8648ce3d020106082a811ccf5501822d04793077020101042083d043f95cafdca03ea3d5fc6adbd22ad387fe427c775463fbc2693813f1016da00a06082a811ccf5501822da144034200041f6567c079d1fca1d934e8170ea5ac471f3576e5a543d3118c27349710448f33120c5dc8ba3dd1cc534661e3895c8f94b94d7ca3e3fa2e2e28ac36aa8fbb6a79";

	/** sm2 */
	private final static SM2 sm2;

	/**
	 * init
	 */
	static {
//		create sm2
		sm2 = SmUtil.sm2(PRIVATE_KEY, PUBLIC_KEY);
	}

	/**
	 * encrypt
	 * @param data
	 * @return
	 */
	public static String encrypt(String data) {
		return sm2.encryptBase64(data, KeyType.PublicKey);
	}

	/**
	 * decrypt
	 * @param data
	 * @return
	 */
	public static String decrypt(String data) {
		return sm2.decryptStr(data, KeyType.PrivateKey);
	}

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		KeyPair keyPair = SecureUtil.generateKeyPair("SM2");
		System.out.println("privateKey：" + HexUtil.encodeHexStr(keyPair.getPrivate().getEncoded()));
		System.out.println("publicKey：" + HexUtil.encodeHexStr(keyPair.getPublic().getEncoded()));
		PublicKey publicKey = keyPair.getPublic();
		if (publicKey instanceof BCECPublicKey) {
			String publicKeyHex = Hex.toHexString(((BCECPublicKey) publicKey).getQ().getEncoded(false));
			System.out.println("SM2公钥：" + publicKeyHex);
		}
		PrivateKey privateKey = keyPair.getPrivate();
		if (privateKey instanceof BCECPrivateKey) {
			String privateKeyHex = ((BCECPrivateKey) privateKey).getD().toString(16);
			System.out.println("SM2私钥：" + privateKeyHex);
		}

		String password = "admin";
		String sm2Password = Sm2Utils.encrypt(password);
		System.out.println("sm2 加密:" + sm2Password);
		System.out.println("sm2 解密:" + Sm2Utils.decrypt(sm2Password));
		System.out.println("sm3 解密:" + SmUtil.sm3("admin"));
	}
}
