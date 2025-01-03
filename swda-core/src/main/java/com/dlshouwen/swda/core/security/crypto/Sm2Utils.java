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
 * @version 1.0.0
 */
public class Sm2Utils {
	
	/** public key */
	private final static String PUBLIC_KEY = "3059301306072a8648ce3d020106082a811ccf5501822d0342000472c35d724b22d089b2f23f3d0861b7d98b0eaf5e5b9fd99c6bc3a466258f13445d0dbb7f9521eb763a74627b73706530e5ce95d0140b4c798e98236ea8709fa9";
	
	/** private key */
	private final static String PRIVATE_KEY = "308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420b8c73570490783c03024a61701cfc055c431eaba6ccbfcd0aff9ae8ddf1535aba00a06082a811ccf5501822da1440342000472c35d724b22d089b2f23f3d0861b7d98b0eaf5e5b9fd99c6bc3a466258f13445d0dbb7f9521eb763a74627b73706530e5ce95d0140b4c798e98236ea8709fa9";

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
		
//		privateKey：308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420b8c73570490783c03024a61701cfc055c431eaba6ccbfcd0aff9ae8ddf1535aba00a06082a811ccf5501822da1440342000472c35d724b22d089b2f23f3d0861b7d98b0eaf5e5b9fd99c6bc3a466258f13445d0dbb7f9521eb763a74627b73706530e5ce95d0140b4c798e98236ea8709fa9
//		publicKey：3059301306072a8648ce3d020106082a811ccf5501822d0342000472c35d724b22d089b2f23f3d0861b7d98b0eaf5e5b9fd99c6bc3a466258f13445d0dbb7f9521eb763a74627b73706530e5ce95d0140b4c798e98236ea8709fa9
//		SM2公钥：0472c35d724b22d089b2f23f3d0861b7d98b0eaf5e5b9fd99c6bc3a466258f13445d0dbb7f9521eb763a74627b73706530e5ce95d0140b4c798e98236ea8709fa9
//		SM2私钥：b8c73570490783c03024a61701cfc055c431eaba6ccbfcd0aff9ae8ddf1535ab
//		sm2 加密:BLty0rerhAkM4kr4TVXECrEu7ro+EmS4grG6YdVqMq62euh99vu6+DpxmsuhRu9MvQIHgD1SGDL7iQSkONlXUxBgFuPl+EbMLOkV6meJktdMzoN47XIUeTWztd7T7FwnsaxOfIvn
//		sm2 解密:admin
//		sm3 解密:dc1fd00e3eeeb940ff46f457bf97d66ba7fcc36e0b20802383de142860e76ae6
	}

}
