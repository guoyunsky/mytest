package com.guoyun.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyFileUtil {
	/**
	 * Add context to the file
	 * 
	 * @param textName
	 * @param date
	 * @return
	 */
	public synchronized static boolean appendText(File file, 
			String data, boolean create) {

		boolean flag = false;
        BufferedWriter bw=null;
		FileWriter fw = null;
		try {
			if (!file.exists()) {
				if (create) {
					file.createNewFile();
				} else {
					return false;
				}
			}

			fw = new FileWriter(file,true);
            bw=new BufferedWriter(fw);
			bw.write(data);
            bw.newLine();
            bw.flush();
			flag = true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
                if(bw!=null){
                    bw.close();
                }
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return flag;
	}
    /**
     * 获得BufferedWriter,该Writer可以用于写入数据,不需要的时候请记得关闭
     * @param file
     * @param createNewFile
     * @return
     * @throws IOException
     */
    public static BufferedWriter getBufferedWriter(File file,
    		boolean createNewFile) throws IOException{
        BufferedWriter bw=null;
        FileWriter fw = null;
        
        if (!file.exists()) {
            if (createNewFile) {
                file.createNewFile();
            } else {
                return null;
            }
        }
            
        fw = new FileWriter(file,true);
        bw=new BufferedWriter(fw);
            
        return bw;
    }
    /**
     * 写入String
     * @param br
     * @param data
     * @param close
     * @throws IOException
     */
    public static void writeStringData(BufferedWriter bw,
    		String data,boolean close) throws IOException{
        bw.write(data);
        bw.newLine();
        bw.flush();
        
        if(close){
            if(bw!=null){
                bw.close();
            }
        }
    }

	public static int cutFile(String filePath, int maxRow) {
		File file = new File(filePath);
		String fileName = file.getName();
		int index = fileName.indexOf(".");
		String preFileName = fileName.substring(0, index);
		String sufFileName = fileName.substring(index + 1);
		String parentPath = file.getParentFile().getAbsolutePath()
				+ File.separator + preFileName + "Cut" + File.separator;

		Iterator<String> it = MyFileUtil.getFileToIterator(file);
		StringBuilder sb = new StringBuilder();
		int row = 0;
		int result = 0;
		while (it.hasNext()) {
			String str = it.next();
			sb.append(str);
			row++;

			if (row % maxRow == 0) {
				result++;
				String newFilePath = parentPath + preFileName + result + "."
						+ sufFileName;
				MyFileUtil.writeStringToFile(newFilePath, sb.toString());
				sb = null;
				sb = new StringBuilder();
			}
		}

		result++;
		String newFilePath = parentPath + preFileName + result + sufFileName;
		MyFileUtil.writeStringToFile(newFilePath, sb.toString());
		sb = null;
		sb = new StringBuilder();

		return result;
	}

	public static void deleteFile(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}

		} else {
			file.delete();
		}
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		deleteFile(file);
	}

	public static String getFileContent(File file) {
		return getFileContent(file, "UTF-8", false);
	}

	public static String getFileContent(File file, String encoding) {
		return getFileContent(file, encoding, false);
	}

	public static String getFileContent(File file, String encoding,
			boolean newLine) {
		StringBuilder sb = new StringBuilder();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String line = null;
		try {
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, encoding);
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				sb.append(line);
				if (newLine) {
					sb.append("\n");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * 返回文件内容，以Iterator形式返回，每一行为一条数据
	 * 
	 * @param file
	 * @return
	 */
	public static Iterator<String> getFileToIterator(File file) {
		List list = new ArrayList();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String line = null;
		
		try {
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				list.add(line.trim());
				// System.out.println(line.trim());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list.iterator();
	}

	/**
	 * Read file contents
	 * 
	 * @param file
	 * @param isDeleteFile
	 * @return
	 */
	public static String readFileToString(File file, 
			boolean isDeleteFile) {
		if (!file.exists())
			return null;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isDeleteFile) {
				file.delete();
			}
		}
		return sb.toString();
	}

	/**
	 * Write contents to File
	 * 
	 * @param filePath
	 * @param data
	 * @return
	 */
	public static boolean writeStringToFile(String filePath, String data) {
		boolean flag = false;
		FileWriter fw = null;
		File newFile = null;

		try {
			newFile = new File(filePath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			fw = new FileWriter(newFile);
			fw.write(data);
			flag = true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return flag;
	}

	/**
	 * Write contents to File
	 * 
	 * @param fileDir
	 * @param fileName
	 * @param data
	 * @return
	 */
	public static boolean writeStringToFile(String fileDir, String fileName,
			String data) {
		String filePath;
		if (fileDir.endsWith("/") || fileDir.endsWith("\\")) {
			filePath = fileDir.concat(fileName);
		} else {
			filePath = fileDir.concat(File.separator).concat(fileName);
		}

		return writeStringToFile(filePath, data);
	}

	public static void main(String[] args) {
		/*
		 * int begain = 1; int end = 44903760; StringBuilder sb = new
		 * StringBuilder();
		 * 
		 * for (int i = begain; i <= end; i++) { sb.append("CALL S(" + i +
		 * ");\r"); if (i % 1000000 == 0) {
		 * writeStringToFile("/home/www/sql/sql_" + i + ".txt", sb .toString());
		 * // writeStringToFile("D:/sql/sql_"+i+".txt",sb.toString()); sb =
		 * null; sb = new StringBuilder(); } if (i == end) {
		 * writeStringToFile("/home/www/sql/sql_" + i + ".txt", sb .toString());
		 * sb = null; } }
		 */
		/*
		 * File file=newFile(
		 * "/home/www/smsbuy_crawl/crawl_jobs/smsbuy_crawl_house_info-20100427024502711/seeds.txt"
		 * ); getFileToIterator(file);
		 */

		/*File file = new File("D:/mobileFrom/exception.sql");
		Iterator<String> it = getFileToIterator(file);
		Connection con = null;
		Statement stmt = null;

		try {
			con = DatabaseUtil.getConnection_Mysql_CrawlServer_Local();
			stmt = con.createStatement();

			while (it.hasNext()) {
				String sql = it.next();
				boolean result = stmt.execute(sql);
				if (!result) {
					System.out.println(sql);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeConnection(con, stmt, null);
		}
*/
	}
}
