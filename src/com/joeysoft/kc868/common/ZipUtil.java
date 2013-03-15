package com.joeysoft.kc868.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {

	/**
	 * 不准实例对象
	 */
	private ZipUtil() {

	}

	/**
	 * 压缩
	 * 
	 * @param lists
	 *            添加的文件列表
	 * @param fileDest
	 *            目标zip文件的路径
	 * @throws IOException
	 */
	public static void zip(List<String> lists, String fileDest)
			throws Exception {
		if (lists == null || lists.size() <= 0) {
			throw new Exception("file list is empty!");

		} else {
			File destFile = new File(fileDest);
			OutputStream output = null;
			ZipOutputStream zipOut = null;
			if (destFile.exists()) {
				throw new Exception("target file Already  exists!");

			} else {

				try {
					output = new FileOutputStream(destFile, false);
				} catch (Exception e) {
					throw new Exception("target directory must exists!");
				}

				zipOut = new ZipOutputStream(output);
				for (String filePath : lists) {
					File file = new File(filePath);

					if (file.exists()) {
						List<String> temps = new ArrayList<String>();
						recursionFiles(file.getPath(), temps);
						
						for (String p : temps) {

							recursionZipFile(p, zipOut);
						}
						//recursionZipFile(file.getPath(), zipOut);

					} else {
						if (zipOut != null) {

							zipOut.closeEntry();
							try {
								zipOut.close();
							} catch (Exception e1) {
								destFile.delete(); // delete already zipfile
								e1.printStackTrace();
							}
							zipOut = null;
						}
						if (output != null) {

							output.flush();
							try {
								output.close();
							} catch (Exception e1) {
								destFile.delete(); // delete already zipfile
								e1.printStackTrace();
							}
							output = null;
						}
						destFile.delete();

						throw new Exception("target file not exists!");
					}

				}

				// close stream object
				closeStream(zipOut, output, destFile);

			}

		}

	}

	/**
	 * @param file
	 *            添加的单个文件
	 * @param fileDest
	 *            目标zip文件的路径
	 * @throws Exception
	 */
	public static void zip(String file, String fileDest) throws Exception {
		File srcFile = new File(file);
		if (srcFile.exists()) {

			File destFile = new File(fileDest);
			OutputStream output = null;
			ZipOutputStream zipOut = null;
			/*if (destFile.exists()) {
				throw new Exception("target file Already  exists!");

			} else {*/

				try {
					output = new FileOutputStream(destFile, false);
				} catch (Exception e) {
					throw new Exception("target directory must exists!");
				}

				zipOut = new ZipOutputStream(output);
				try {
					recursionZipFile(srcFile.getPath(), zipOut);
				} catch (Exception e) {
					closeStream(zipOut, output, destFile);
					e.printStackTrace();
				}
				// close stream object
				closeStream(zipOut, output, destFile);
			//}

		} else {

			throw new Exception("target file not exists!");
		}

	}

	/**
	 * @param zipSrc
	 *            zip源文件路径
	 * @param fileDest
	 *            解压的文件路径
	 * @throws Exception
	 */
	public static void unzip(String zipSrc, String fileDest) throws Exception {

		File f = new File(zipSrc);
		if (f.exists()) {

			File file = new File(fileDest);

			if (file.exists() && file.isDirectory()) {

				ZipFile zipFile = new ZipFile(f);

				for (@SuppressWarnings("rawtypes")
				Enumeration entries = zipFile.getEntries(); entries
						.hasMoreElements();) {
					try {
						ZipEntry zipEntry = (ZipEntry) entries.nextElement();
						InputStream ins = zipFile.getInputStream(zipEntry);

						int index = zipEntry.getName().lastIndexOf("/") + 1;
						if (index <= 0) {

							index = zipEntry.getName().lastIndexOf("\\") + 1;
						}
						file = new File(fileDest
								+ "/"
								+ zipEntry.getName().substring(0, index)
								+ "/"
								+ zipEntry.getName().substring(index,
										zipEntry.getName().length()));
						
						File parentPath = new File(file.getParent());
						if (!parentPath.isDirectory()) {
							parentPath.mkdirs();
						}

						String absPath = file.getPath().replaceAll("\\\\", "/");
						OutputStream output = new FileOutputStream(absPath);

					
						int len=-1;
						byte [] b=new byte[1024];
						while((len=ins.read(b))!=-1){
							
							output.write(b, 0, len);
						}
						output.flush();
						output.close();
						ins.close();
					} catch (Exception e) {
						delete(fileDest, false); // delete already unzip file
						e.printStackTrace();
					}

				}
				zipFile.close();

			} else {

				throw new Exception("target directory  not exists!");
			}

		} else {

			throw new Exception("src file  not exists!");
		}

		
	}

	/**
	 * 
	 * @param lists
	 *            追加的文件列表
	 * @param zipDest
	 *            要追加的zip文件
	 */
	@SuppressWarnings("unchecked")
	public static void zipAppend(List<String> lists, String zipDest)
			throws Exception {

		File zipRoot = new File(zipDest);
		String suffix = zipRoot.getPath().substring(
				zipRoot.getPath().lastIndexOf("zip"));
		boolean isException = true;
		if (zipRoot.isFile() && "zip".equals(suffix)) {
			if (lists != null && lists.size() > 0) {
				File destFile = new File(zipDest);

				ZipFile zipFile = new ZipFile(zipRoot);
				OutputStream output = null;
				ZipOutputStream zipOut = null;
				for (int i = 0; i < lists.size(); i++) {

					isException = exists(lists.get(i), false);
				}
				try {
					@SuppressWarnings("rawtypes")
					List list = new ArrayList();
					List<String> existsFile = new ArrayList<String>();
					if (isException) {

						// 先添加原来的

						for (@SuppressWarnings("rawtypes")
						Enumeration entries = zipFile.getEntries(); entries
								.hasMoreElements();) {
							try {
								List<Object> oldFile = new ArrayList<Object>();
								ZipEntry zipEntry = (ZipEntry) entries
										.nextElement();
								InputStream ins = zipFile
										.getInputStream(zipEntry);
								long size = zipEntry.getSize();

								String name = zipEntry.getName();
								/**
								 * assets/picture/1-1.png
								 * assets/mousePic/1uparrow2.png
								 */

								for (int i = 0; i < lists.size(); i++) {

									String newsName = lists.get(i).toString();

									List<String> temps = new ArrayList<String>();
									recursionFiles(newsName, temps);
									for (int j = 0; j < temps.size(); j++) {

										String tempName = name;

										if (tempName.indexOf("/") != -1) {

											tempName = tempName.replaceAll("/",
													"\\\\");
										}
										String tp = (zipRoot.getParent() + tempName)
												.replaceAll("\\\\", "/")
												.toLowerCase();
										String sp = temps.get(j)
												.replaceAll("\\\\", "/")
												.toLowerCase();

										if (tp.equals(sp)) {

											// 用最后新的文件路径替换前面己有路径，这是为了，用后面的覆盖前面的文件
											ins = new FileInputStream(new File(
													temps.get(j)));
											size = ins.available();
											existsFile.add(temps.get(j)); // 添加存在的住合里面去

										}
									}

								}

								byte[] b = new byte[(int) size];
								// long begin=System.currentTimeMillis();
								for (int i = 0; i < size; i++) {
									byte c = (byte) ins.read();
									// 存入临时缓存
									b[i] = c;
								}

								ins.close();
								oldFile.add(name);
								oldFile.add(b);
								list.add(oldFile);

							} catch (Exception e) {
								isException = false;
								e.printStackTrace();
							}
						}
					} else {
						throw new Exception("target file format error");

					}
					if (isException) {
						try {
							output = new FileOutputStream(destFile);

						} catch (Exception e) {
							throw new Exception(e);
						}

						zipOut = new ZipOutputStream(output);
						// 原来的

						for (int i = 0; i < list.size(); i++) {

							List<Object> oldFile = (List<Object>) list.get(i);
							String name = oldFile.get(0).toString();
							byte[] b = (byte[]) oldFile.get(1);
							ZipEntry zipEntry = new ZipEntry(name);
							zipOut.putNextEntry(zipEntry);
							zipOut.write(b, 0, b.length);
						}

						// 再添加后面的
						for (int i = 0; i < lists.size(); i++) {
							String path = lists.get(i).replaceAll("\\\\", "/");
							List<String> temps = new ArrayList<String>();
							recursionFiles(path, temps);

							// 选出不存在的
							for (int j = 0; j < existsFile.size(); j++) {

								if (temps.contains(existsFile.get(j))) {

									temps.remove(existsFile.get(j)); // 这一步是为了移除旧的文件

								}

							}

							for (String p : temps) {

								recursionZipFile(p, zipOut);
							}

						}

					} else {
						throw new Exception("target file format error");

					}

				} catch (Exception e) {
					closeStream(zipOut, output, destFile);
					e.printStackTrace();
				}
				// close stream object
				closeStream(zipOut, output, destFile);

			} else {

				throw new Exception("lists empty!");
			}

		} else {

			throw new Exception("target file format error");
		}
	}

	/**
	 * 关闭流
	 * 
	 * @param zipOut
	 * @param output
	 * @param destFile
	 */
	private static void closeStream(ZipOutputStream zipOut,
			OutputStream output, File destFile) {

		if (zipOut != null) {

			try {
				zipOut.closeEntry();
				zipOut.flush();
				zipOut.close();
			} catch (Exception e) {
				destFile.delete();
				e.printStackTrace();
			}
			zipOut = null;
		}
		if (output != null) {
			try {
				output.flush();
				output.close();
			} catch (Exception e) {
				destFile.delete();
				e.printStackTrace();
			}
			output = null;

		}
	}

	/**
	 * 这是递归删除,当前目录下所有文件都将删除
	 * 
	 * @param p
	 *            路径
	 * @param dp
	 *            是否删除当前目录
	 * @throws Exception
	 */
	private static void delete(String p, boolean dp) throws Exception {

		File rootFile = new File(p);
		if (rootFile.exists()) {

			String[] files = rootFile.list();
			for (int i = 0; i < files.length; i++) {

				String path = (rootFile.getPath() + "\\" + files[i])
						.replaceAll("\\\\", "/");
				File elementFile = new File(path);
				if (elementFile.isDirectory()) {
					delete(elementFile.getPath(), true);

				} else {

					if (!elementFile.delete()) {

						throw new Exception("delete Failed!");
					}
				}

			}

		} else {

			throw new Exception("target directory  not exists!");
		}
		if (dp) {

			if (!rootFile.delete()) {

				throw new Exception("delete Failed!");
			}
		}

	}

	/**
	 * @param base
	 * @param path
	 * @param entry
	 * @param zipOut
	 */
	private static void recursionZipFile(String path, ZipOutputStream zipOut) {
		try {
			File f = new File(path);
			FileInputStream input = new FileInputStream(f.getPath());

			String base = "";
			String temppath = f.getPath().replaceAll("\\\\", "/");

			String xg[] = temppath.split("/");

			/**
			 * 下面这个主要是为了确定父文件夹
			 */
			/*if (xg.length == 2) {

				base = "";
			} else if (xg.length > 2) {

				base = temppath.substring(temppath.indexOf("/") + 1,
						temppath.lastIndexOf("/") + 1);
			}*/

			String savepath = base + f.getName();
			ZipEntry entry = new ZipEntry(savepath);
			zipOut.putNextEntry(entry);
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = input.read(b)) != -1) {

				zipOut.write(b, 0, len);
			}

			input.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 递归一个文件夹下所有文件
	 * 
	 * @param path
	 * @param returnList
	 * @return
	 */
	private static List<String> recursionFiles(String path,
			List<String> returnList) {

		File filepath = new File(path.replaceAll("\\\\", "/"));

		if (filepath.exists()) {

			if (filepath.isDirectory()) {
				String[] files = filepath.list();
				for (int i = 0; i < files.length; i++) {

					String newsPath = filepath.getPath() + "/" + files[i];
					newsPath = newsPath.replaceAll("\\\\", "/");
					File elementFile = new File(newsPath);
					if (elementFile.exists()) {

						if (elementFile.isFile()) {

							returnList.add(elementFile.getPath());
						} else if (elementFile.isDirectory()) {

							recursionFiles(newsPath, returnList);// 再遍历
						}

					} else {
						returnList = new ArrayList<String>();
						break;

					}

				}

			} else {
				returnList.add(filepath.getPath());

			}

		} else {

			returnList = new ArrayList<String>();
		}
		return returnList;

	}

	/**
	 * 判断一个文件夹下所有文件是否确实存在
	 * 
	 * @param path
	 * @param exists
	 * @return
	 */
	private static boolean exists(String path, boolean exists) {

		File filepath = new File(path.replaceAll("\\\\", "/"));

		if (filepath.exists()) {

			if (filepath.isDirectory()) {
				String[] files = filepath.list();
				for (int i = 0; i < files.length; i++) {

					String newsPath = filepath.getPath() + "/" + files[i];
					newsPath = newsPath.replaceAll("\\\\", "/");
					File elementFile = new File(newsPath);
					if (elementFile.exists()) {

						if (elementFile.isFile()) {

							exists = true;
						} else if (elementFile.isDirectory()) {

							exists(newsPath, false);// 再遍历
						}

					} else {
						exists = false;

					}

				}

			} else {
				exists = true;

			}

		} else {

			exists = false;
		}
		return exists;
	}

	public static void main(String[] args) throws Exception {

		// delete("D:\\aa", true);
		// testzipAppend();
		// List<String> fileList = new ArrayList<String>();
		// fileList.add("D:\\图片.png");
		// fileList.add("D:\\assets");
		// fileList.add("D:\\projectsw.xml");
		// fileList.add("D:\\assets\\aa.png");
		// for(int i=0;i<fileList.size();i++){
		//
		// System.out.println(exists(fileList.get(i), false));
		// }
		testunZIP();

	}

	private static void testZIP() {

		List<String> fileList = new ArrayList<String>();
		fileList.add("D:\\图片.png");
		fileList.add("D:\\assets");
		fileList.add("D:\\My Documents\\F5\\projects.xml");
		String srcFile = "D:\\My Documents\\F5\\projects.xml";
		String fileDest = "D:\\zip-2.zip";

		try {
			zip(fileList, fileDest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testunZIP() {

		String srcFile = "D:\\dddd.zip";
		String destFile = "D:\\aa";
		try {
			unzip(srcFile, destFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testzipAppend() throws Exception {
		List<String> fileList = new ArrayList<String>();
		fileList.add("D:\\图片.png");
		fileList.add("D:\\assets");
		fileList.add("D:\\projects.xml");

		String fileDest = "D:\\dddd.zip";
		zipAppend(fileList, fileDest);

	}

	private static void buffer() {

		File file = new File("D:\\waterMarkImage.jpg");
		InputStream in;
		try {
			in = new FileInputStream(file);
			BufferedInputStream bs = new BufferedInputStream(in);
			Thread.sleep(10000);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(new File("D:\\aa.jpg")));
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = bs.read(b)) != -1) {

				bos.write(b, 0, len);
			}
			bos.flush();
			bos.close();
			bs.close();
			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}