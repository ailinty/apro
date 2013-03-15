package com.joeysoft.kc868.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具
 * 
 * @author joey
 */
public class FileTool {
    /**
     * 得到文件的扩展名
     * 
     * @param filename
     * 		文件名
     * @return
     * 		扩展名，如果没有扩展名，返回null
     */
    public static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if(index == -1)
            return null;
        else
            return filename.substring(index + 1);
    }
    
    /**
     * 删除一个文件
     * 
     * @param path
     * 		文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if(file.exists())
            file.delete();
    }
    
    /**
     * 得到不包括扩展名在内的文件名
     * 
     * @param file
     * 		File对象
     * @return
     * 		文件名
     */
    public static String getNameExcludeExtension(File file) {
        return getNameExcludeExtension(file.getName());
    }
    
    /**
     * 得到不包括扩展名在内的文件名
     * 
     * @param filename
     * 		文件名
     * @return
     * 		文件名
     */
    public static String getNameExcludeExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if(index != -1)
            filename = filename.substring(0, index);
        return filename;
    }
    
    /**
     * 把一段内容存为指定文件
     * 
     * @param b
     * 		字节数组
     * @param offset
     * 		起始偏移
     * @param length
     * 		保存的长度
     * @param dest
     * 		目标文件名
     * @return
     * 		true表示保存成功
     */
    public static boolean saveFile(byte[] b, int offset, int length, String dest) throws IOException{
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dest, true);
            fos.write(b, offset, length);
            fos.flush();
            return true;
        } catch (IOException e) {
        	throw e;
        } finally {
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e1) {
            	throw e1;
            }
        }        
    }
    
    /**
     * 拷贝一个文件
     * 
     * @param src
     * 		源路径
     * @param dest
     * 		目的路径
     * @return
     * 		true表示拷贝成功
     */
    public static boolean copyFile(String src, String dest) {
        FileInputStream fis = null;        
        FileOutputStream fos = null;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            for(int i = 0; i != -1; i = fis.read(buffer, 0, buffer.length))
                fos.write(buffer, 0, i);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if(fis != null)
                    fis.close();
            } catch (IOException e1) {
            }
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e2) {
            }
            buffer = null;
        }
    }
}
