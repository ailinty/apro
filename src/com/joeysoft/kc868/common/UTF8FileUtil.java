package com.joeysoft.kc868.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class UTF8FileUtil {
	public static void saveFile(String file, String data, boolean append) throws IOException { 

	      BufferedWriter bw = null; 

	      OutputStreamWriter osw = null; 

	      File f = new File(file); 

	      FileOutputStream fos = new FileOutputStream(f, append); 

	      try { 

	         // write UTF8 BOM mark if file is empty 

	         if (f.length() < 1) { 

	           final byte[] bom = new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF }; 

	            fos.write(bom); 

	         } 

	         osw = new OutputStreamWriter(fos, "UTF-8"); 

	         bw = new BufferedWriter(osw); 

	         if (data != null) bw.write(data); 

	      } catch (IOException ex) { 

	         throw ex; 

	      } finally { 

	         try { bw.close(); fos.close(); } catch (Exception ex) { } 

	      } 
	   } 
}
