package com.joeysoft.kc868.client.packets.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 * DES对称加密算法
 * @author kongqz
 * */
public class DESCoder {
	/**
	 * 密钥算法
	 * java支持56位密钥，bouncycastle支持64位
	 * */
	public static final String KEY_ALGORITHM="DES";
	
	/**
	 * 加密/解密算法/工作模式/填充方式
	 * */
	public static final String CIPHER_ALGORITHM="DES/ECB/NoPadding";
	
	/**
	 * 
	 * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
	 * @return byte[] 二进制密钥
	 * */
	public static byte[] initkey() throws Exception{
		
		//实例化密钥生成器
		KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM);
		//初始化密钥生成器
		kg.init(8);
		//生成密钥
		SecretKey secretKey=kg.generateKey();
		//获取二进制密钥编码形式
		return secretKey.getEncoded();
	}
	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return Key 密钥
	 * */
	public static Key toKey(byte[] key) throws Exception{
		//实例化Des密钥
		DESKeySpec dks=new DESKeySpec(key);
		//实例化密钥工厂
		SecretKeyFactory keyFactory=SecretKeyFactory.getInstance(KEY_ALGORITHM);
		//生成密钥
		SecretKey secretKey=keyFactory.generateSecret(dks);
		return secretKey;
	}
	
	/**
	 * 加密数据
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密后的数据
	 * */
	public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
		//还原密钥
		Key k=toKey(key);
		//实例化
		Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
		//初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data);
	}
	/**
	 * 解密数据
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密后的数据
	 * */
	public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
		//欢迎密钥
		Key k =toKey(key);
		//实例化
		Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
		//初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		//执行操作
		return cipher.doFinal(data);
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String str="12345678";
		System.out.println("原文："+str);
		
		System.out.println(Util.convertByteToHexString(str.getBytes()));
		//初始化密钥
		String keys= "cityclud";
		byte[] key= keys.getBytes();
		System.out.println(Util.convertByteToHexString(key));
		//加密数据
		byte[] data=DESCoder.encrypt(str.getBytes(), key);
		System.out.println("加密后："+Util.convertByteToHexString(data));
		//解密数据
		data=DESCoder.decrypt(data, key);
		System.out.println("解密后："+new String(data));
		//18F7C2045A4EF52A
	}
}
