package com.joeysoft.kc868.client.bean;

public class SecretKey {

	private byte[] key;
	
	private byte[] secretKey;
	
	
	public SecretKey(byte[]key, byte[]secretKey){
		this.key = key;
		this.secretKey = secretKey;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public byte[] getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(byte[] secretKey) {
		this.secretKey = secretKey;
	}
}
