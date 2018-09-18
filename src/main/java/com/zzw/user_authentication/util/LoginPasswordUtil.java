package com.zzw.user_authentication.util;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class LoginPasswordUtil {

	private static Cache<String, KeyPair> userKeyPair;

	public static Cache<String, KeyPair> getUserKeyPair() {
		if (Objects.isNull(userKeyPair)) {
			userKeyPair = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10000, TimeUnit.MINUTES).build();
		}
		return userKeyPair;
	}

	/**
	 * 公钥密码解密
	 * 
	 * @param loginUsername
	 * @param encryptedPassword
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decryptedPassword(String loginUsername, String encryptedPassword) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		KeyPair keyPair = getUserKeyPair().getIfPresent(loginUsername);
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] encrypted = Base64.getDecoder().decode(encryptedPassword);
		byte[] secret = RsaCypherUtil.decrypt(privateKey, encrypted);
		String decryptedPassword = new String(secret);
		return decryptedPassword;
	}

	/**
	 * 原密码MD5加密
	 * 
	 * @param orginPassword
	 * @return
	 */
	public static String encoderPassword(String orginPassword) {
		String md5HashOldPassword = DigestUtils.md5Hex(orginPassword).toUpperCase();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(md5HashOldPassword);
	}

	/**
	 * 输入密码与用户密码是否匹配
	 * 
	 * @param orginPassword
	 * @param loginPassword
	 * @return
	 */
	public static boolean verifyPassword(String orginPassword, String loginPassword) {
		String md5HashOldPassword = DigestUtils.md5Hex(orginPassword).toUpperCase();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (passwordEncoder.matches(md5HashOldPassword, loginPassword)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			KeyPair keyPair = RsaCypherUtil.buildKeyPair();
			getUserKeyPair().put("123", keyPair);
			PublicKey publicKey = keyPair.getPublic();
			byte[] bytes = "admin".getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] str = cipher.doFinal(bytes);
			String encoded = Base64.getEncoder().encodeToString(str);
			System.out.println(encoded);
			
			byte[] de = Base64.getDecoder().decode(encoded);
			PrivateKey privateKey = userKeyPair.getIfPresent("123").getPrivate();
			Cipher ciphe = Cipher.getInstance("RSA");
			ciphe.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] b = ciphe.doFinal(de);
			String deString = new String(b);
			
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String pwdEn = passwordEncoder.encode(deString);
			System.out.println(pwdEn);
			
			System.out.println(new String(b));
			System.out.println(passwordEncoder.matches(new String(b),pwdEn));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}