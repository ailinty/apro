package com.joeysoft.kc868.client.packets.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.StringTokenizer;

import com.joeysoft.kc868.client.bean.SecretKey;
import com.joeysoft.kc868.client.packets.ErrorPacket;
import com.joeysoft.kc868.client.support.Protocol;

public class Util {
	// 随机类
    private static Random random;
    
    // string buffer
    private static StringBuilder sb = new StringBuilder();
    
    // 16进制字符数组
    private static char[] hex = new char[] { 
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
    
    public static byte[] MAC = new byte[]{
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    
	/**
	 * 得到错误类型
	 * 
	 * @param error
	 * @return
	 */
	public static String getErrorString(int error) {
		switch(error) {
			case ErrorPacket.ERROR_TIMEOUT:
				return "发送超时";
			case ErrorPacket.ERROR_REMOTE_CLOSED:
				return "远程连接已关闭";
			default:
				return "";
		}
	}
	
	/**
	 * @return Random对象
	 */
	public static Random random() {
		if (random == null)
			random = new Random();
		return random;
	}
	
	/**
	 * @return
	 * 		一个随机产生的密钥字节数组
	 */
	public static byte[] randomKey() {
	    byte[] key = new byte[Protocol.LENGTH_KEY];
	    random().nextBytes(key);
	    return key;
	}
	
	 /**
     * 这是个随机因子产生器，用来填充头部的，如果为了调试，可以用一个固定值
     * 随机因子可以使相同的明文每次加密出来的密文都不一样
     * 
     * @return
     * 		随机因子
     */
    public static int rand() {
        return random().nextInt();
    }
	
	/**
	 * 把字节数组从offset开始的len个字节转换成一个unsigned int， 因为java里面没有unsigned，所以unsigned
	 * int使用long表示的， 如果len大于8，则认为len等于8。如果len小于8，则高位填0 <br>
	 * (edited by notxx) 改变了算法, 性能稍微好一点. 在我的机器上测试10000次, 原始算法花费18s, 这个算法花费12s.
	 * 
	 * @param in
	 *                   字节数组.
	 * @param offset
	 *                   从哪里开始转换.
	 * @param len
	 *                   转换长度, 如果len超过8则忽略后面的
	 * @return
	 */
	public static long getUnsignedInt(byte[] in, int offset, int len) {
		long ret = 0;
		int end = 0;
		if (len > 8)
			end = offset + 8;
		else
			end = offset + len;
		for (int i = offset; i < end; i++) {
			ret <<= 8;
			ret |= in[i] & 0xff;
		}
		return (ret & 0xffffffffl) | (ret >>> 32);
	}
	
	/**
     * 把字节数组转换成16进制字符串
     * 
     * @param b
     * 			字节数组
     * @return
     * 			16进制字符串，每个字节之间空格分隔，头尾无空格
     */
    public static String convertByteToHexString(byte[] b) {
        return convertByteToHexString(b, 0, b.length);
    }
    
    /**
     * 把字节数组转换成16进制字符串
     * 
     * @param b
     * 			字节数组
     * @param offset
     * 			从哪里开始转换
     * @param len
     * 			转换的长度
     * @return 16进制字符串，每个字节之间空格分隔，头尾无空格
     */
    public static String convertByteToHexString(byte[] b, int offset, int len) {
        // 检查索引范围
        int end = offset + len;
        if(end > b.length)
            end = b.length;
        
	    sb.delete(0, sb.length());
        for(int i = offset; i < end; i++) {
            sb.append(hex[(b[i] & 0xF0) >>> 4])
            	.append(hex[b[i] & 0xF])
            	.append(' ');
        }
        if(sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    /**
     * 把字节数组转换成16进制字符串
     * 
     * @param b
     * 			字节数组
     * @return
     * 			16进制字符串，每个字节没有空格分隔
     */
    public static String convertByteToHexStringWithoutSpace(byte[] b) {
        return convertByteToHexStringWithoutSpace(b, 0, b.length);
    }
    
    /**
     * 把字节数组转换成16进制字符串
     * 
     * @param b
     * 			字节数组
     * @param offset
     * 			从哪里开始转换
     * @param len
     * 			转换的长度
     * @return 16进制字符串，每个字节没有空格分隔
     */
    public static String convertByteToHexStringWithoutSpace(byte[] b, int offset, int len) {
        // 检查索引范围
        int end = offset + len;
        if(end > b.length)
            end = b.length;
        
	    sb.delete(0, sb.length());
        for(int i = offset; i < end; i++) {
            sb.append(hex[(b[i] & 0xF0) >>> 4])
            	.append(hex[b[i] & 0xF]);
        }
        return sb.toString();
    }
    
    /**
     * 转换16进制字符串为字节数组
     * 
     * @param s
     * 			16进制字符串，每个字节由空格分隔
     * @return 字节数组，如果出错，返回null，如果是空字符串，也返回null
     */
    public static byte[] convertHexStringToByte(String s) {
        try {
            s = s.trim();
            StringTokenizer st = new StringTokenizer(s, " ");
            if(st.countTokens() == 0) return null;
            byte[] ret = new byte[st.countTokens()];
            for(int i = 0; st.hasMoreTokens(); i++) {
                String byteString = st.nextToken();
                
                // 一个字节是2个16进制数，如果不对，返回null
                if(byteString.length() > 2)
                    return null;
                
                ret[i] = (byte)(Integer.parseInt(byteString, 16) & 0xFF);     
            }
            return ret;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 把一个16进制字符串转换为字节数组，字符串没有空格，所以每两个字符
     * 一个字节
     * 
     * @param s
     * @return
     */
    public static byte[] convertHexStringToByteNoSpace(String s) {
        int len = s.length();
        byte[] ret = new byte[len >>> 1];
        for(int i = 0; i <= len - 2; i += 2) {
            ret[i >>> 1] = (byte)(Integer.parseInt(s.substring(i, i + 2).trim(), 16) & 0xFF);
        }
        return ret;
    }
    
    /**
     * 把ip的字节数组形式转换成字符串形式
     * 
     * @param ip
     * 			ip地址字节数组，big-endian
     * @return ip字符串
     */
    public static String convertIpToString(byte[] ip) {
	    sb.delete(0, sb.length());
        for(int i = 0; i < ip.length; i++) {
            sb.append(ip[i] & 0xFF)
            	.append('.');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    /**
     * 比较两个字节数组的内容是否相等
     * 
     * @param b1
     * 		字节数组1
     * @param b2
     * 		字节数组2
     * @return
     * 		true表示相等
     */
    public static boolean isByteArrayEqual(byte[] b1, byte[] b2) {
        if(b1.length != b2.length)
            return false;
        
        for(int i = 0; i < b1.length; i++) {
            if(b1[i] != b2[i])
                return false;
        }
        return true;
    }
    
    /**
     * 前后8字节异或, 不足8字节，补足0x00
     * @param in
     * @return
     */
    public static byte[] XOR(final byte[] in, int offset, int length) {
    	int len = length;
    	byte[] out = new byte[8];
    	int m = 8 - (len % 8);
    	
    	byte[] data = new byte[len + m];
    	System.arraycopy(in, offset, data, 0, len);
    	
    	for(int i=len;i<len+m;i++){
    		data[i] = 0x0;
    	}
    	
    	for(int i = 0; i < 8; i++)
    		out[i] = 0x0;
    	
    	for(int pos=0; pos<len + m;pos+=8){
    		for(int i = 0; i < 8; i++) {
                out[i] ^= data[pos + i];
            }
    	}
    	return out;
    }
    
    /**
     * 得到随机的SecretKey
     * @return
     */
    public static SecretKey getRandomSecretKey(){
    	SecretKey secretKey = null;
    	byte[] key = new byte[8];
		for(int i = 1; i < 8; i++)
			key[i] = (byte)(rand() & 0xFF);
		try {
			byte[] sctKey = DESCoder.encrypt(key, Protocol.MK.getBytes());
			secretKey = new SecretKey(key, sctKey);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return secretKey;
    }
    
    /**
     * 根据某种编码方式得到字符串的字节数组形式
     * @param s 字符串
     * @param encoding 编码方式
     * @return 特定编码方式的字节数组，如果encoding不支持，返回一个缺省编码的字节数组
     */
    public static byte[] getBytes(String s, String encoding) {
        try {
            return s.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return s.getBytes();
        }
    }
    
    /**
     * 根据缺省编码得到字符串的字节数组形式
     * 
     * @param s
     * @return
     */
    public static byte[] getBytes(String s) {
        return getBytes(s, Protocol.CHARSET_DEFAULT);
    }
    
    /**
     * 对原始字符串进行编码转换，如果失败，返回原始的字符串
     * @param s 原始字符串
     * @param srcEncoding 源编码方式
     * @param destEncoding 目标编码方式
     * @return 转换编码后的字符串，失败返回原始字符串
     */
    public static String getString(String s, String srcEncoding, String destEncoding) {
        try {
            return new String(s.getBytes(srcEncoding), destEncoding);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
}
