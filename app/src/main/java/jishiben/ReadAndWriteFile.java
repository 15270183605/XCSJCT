package jishiben;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadAndWriteFile {
	public long firststart;
	public long len;
	public ReadAndWriteFile() {
		super();

	}

	// 生成文件夹
	public void MakeFileRoot(String filepath) {
		File file = null;
		try {
			file = new File(filepath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}

	// 生成文件
	public File MakeFile(String filepath, String filename) {
		File file = null;
		MakeFileRoot(filepath);
		try {
			file = new File(filepath + filename);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	// 写文件
	public void WriteFile(String content, String filepath, String filename) {
		MakeFile(filepath, filename);
		String Filepath = filepath + filename;
		String Content = content + "\r\n";
		try {
			File file = new File(Filepath);
			if (!file.exists()) {
				file.getParentFile().mkdir();
				file.createNewFile();
			}
			RandomAccessFile ref = new RandomAccessFile(file, "rw");
			ref.seek(file.length());
			firststart = file.length();
			ref.write(Content.getBytes());
			len = file.length() - firststart;
			ref.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
	// 写数据
	public void writeData(String content, String filepath, String filename) {
		
		 * String filePath = "/sdcard/LiCaiJSB/"; String fileName = "licai.txt";
		 
		WriteFile(content, filepath, filename);
	}
*/
	// 读数据
	public String getFileContent(File file) {
		String content = "";
		if (!file.isDirectory()) { // 检查此路径名的文件是否是一个目录(文件夹)
			if (file.getName().endsWith("txt")) {// 文件格式为""文件
				try {
					InputStream instream = new FileInputStream(file);
					if (instream != null) {
						InputStreamReader inputreader = new InputStreamReader(
								instream, "UTF-8");
						BufferedReader buffreader = new BufferedReader(
								inputreader);
						String str = "";
						// 分行读取
						while ((str = buffreader.readLine()) != null) {
							content += str + "\n";
						}
						instream.close();// 关闭输入流
					}
				} catch (java.io.FileNotFoundException e) {

				} catch (IOException e) {
				}
			}
		}
		return content;
	}

	// 替换文件某一部分内容

	public void UpdateFileData(String filePath, long pos,long position, String insertContent)
			throws IOException {
		File tmp = File.createTempFile("tmp", null);
		tmp.deleteOnExit(); // 使用临时文件保存插入点后的数据
		RandomAccessFile raf=new  RandomAccessFile(filePath, "rw");	
					
		FileOutputStream out=new FileOutputStream(tmp);
		FileInputStream in=new FileInputStream(tmp);
		 raf.seek(position);
							 //----------下面代码将插入点后的内容读入临时文件中保存----------
		byte[] bbuf=new byte[64]; //用于保存实际读取的字节数
		 int hasRead =0; //使用循环方式读取插入点后的数据
		 while((hasRead=raf.read(bbuf))>0){ //将读取的数据写入临时文件
		 out.write(bbuf,0,hasRead); }
						 //-----------下面代码用于插入内容----------
							// //把文件记录指针重写定位到pos位置
						
		 raf.seek(pos); //追加需要插入的内容
		 raf.write(insertContent.getBytes()); //追加临时文件中的内容
		 firststart=raf.getFilePointer();
			 while((hasRead=in.read(bbuf))>0){
				 raf.write(bbuf,0, hasRead); } 
			 
	}
	
	//获取文件内容
		public String GetContent(String filePath,long start,long len) {
			// 修改文件内容,写入日期
			RandomAccessFile raf = null;
			String Content ="";
			try {
				raf = new RandomAccessFile(filePath, "rw");
				String line = null;
				long lastPoint =start; // 记住上一次的偏移量
				raf.seek(lastPoint);
				while ((line = raf.readLine()) != null) {
					Content+=new String(line.getBytes("ISO-8859-1"),"utf-8")+"\n";
					final long ponit = raf.getFilePointer();
					lastPoint = ponit; // 读取一行，指针指到下一行开头。用作写下一行，偏移量的开始，。
					if(lastPoint>=start+len){
						break;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return Content;
		}
	// 按行修改文件内容
	public boolean UpFileDataByLine(String filePath, String replaceContent,
			String regex,long start,long len) {
		// 修改文件内容,写入日期
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(filePath, "rw");
			String line = null;
			long lastPoint =start; // 记住上一次的偏移量
			while ((line = raf.readLine()) != null) {
				final String str = replaceFileContent(line, replaceContent,
						regex); // 读取文件一行，将匹配正则的字符串替换。
				final long ponit = raf.getFilePointer();
				raf.seek(lastPoint);
				raf.writeBytes(str);
				lastPoint = ponit; // 读取一行，指针指到下一行开头。用作写下一行，偏移量的开始，。
				if(lastPoint>start+len){
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * 替换匹配正则的内容。
	 * </p>
	 * 
	 * @param srcContent
	 *            源字符串
	 * @param replaceContent
	 *            替换内容
	 * @param regex
	 *            正则表达式
	 * @return boolean
	 */
	private static String replaceFileContent(String srcContent,
			String replaceContent, String regex) {
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(srcContent);
		final StringBuffer sb = new StringBuffer();
		if (matcher.find()) {
			matcher.appendReplacement(sb, replaceContent);
		}
		return sb.toString();
	}

}
