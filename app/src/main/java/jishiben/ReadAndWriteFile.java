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

	// �����ļ���
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

	// �����ļ�
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

	// д�ļ�
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
	// д����
	public void writeData(String content, String filepath, String filename) {
		
		 * String filePath = "/sdcard/LiCaiJSB/"; String fileName = "licai.txt";
		 
		WriteFile(content, filepath, filename);
	}
*/
	// ������
	public String getFileContent(File file) {
		String content = "";
		if (!file.isDirectory()) { // ����·�������ļ��Ƿ���һ��Ŀ¼(�ļ���)
			if (file.getName().endsWith("txt")) {// �ļ���ʽΪ""�ļ�
				try {
					InputStream instream = new FileInputStream(file);
					if (instream != null) {
						InputStreamReader inputreader = new InputStreamReader(
								instream, "UTF-8");
						BufferedReader buffreader = new BufferedReader(
								inputreader);
						String str = "";
						// ���ж�ȡ
						while ((str = buffreader.readLine()) != null) {
							content += str + "\n";
						}
						instream.close();// �ر�������
					}
				} catch (java.io.FileNotFoundException e) {

				} catch (IOException e) {
				}
			}
		}
		return content;
	}

	// �滻�ļ�ĳһ��������

	public void UpdateFileData(String filePath, long pos,long position, String insertContent)
			throws IOException {
		File tmp = File.createTempFile("tmp", null);
		tmp.deleteOnExit(); // ʹ����ʱ�ļ���������������
		RandomAccessFile raf=new  RandomAccessFile(filePath, "rw");	
					
		FileOutputStream out=new FileOutputStream(tmp);
		FileInputStream in=new FileInputStream(tmp);
		 raf.seek(position);
							 //----------������뽫����������ݶ�����ʱ�ļ��б���----------
		byte[] bbuf=new byte[64]; //���ڱ���ʵ�ʶ�ȡ���ֽ���
		 int hasRead =0; //ʹ��ѭ����ʽ��ȡ�����������
		 while((hasRead=raf.read(bbuf))>0){ //����ȡ������д����ʱ�ļ�
		 out.write(bbuf,0,hasRead); }
						 //-----------����������ڲ�������----------
							// //���ļ���¼ָ����д��λ��posλ��
						
		 raf.seek(pos); //׷����Ҫ���������
		 raf.write(insertContent.getBytes()); //׷����ʱ�ļ��е�����
		 firststart=raf.getFilePointer();
			 while((hasRead=in.read(bbuf))>0){
				 raf.write(bbuf,0, hasRead); } 
			 
	}
	
	//��ȡ�ļ�����
		public String GetContent(String filePath,long start,long len) {
			// �޸��ļ�����,д������
			RandomAccessFile raf = null;
			String Content ="";
			try {
				raf = new RandomAccessFile(filePath, "rw");
				String line = null;
				long lastPoint =start; // ��ס��һ�ε�ƫ����
				raf.seek(lastPoint);
				while ((line = raf.readLine()) != null) {
					Content+=new String(line.getBytes("ISO-8859-1"),"utf-8")+"\n";
					final long ponit = raf.getFilePointer();
					lastPoint = ponit; // ��ȡһ�У�ָ��ָ����һ�п�ͷ������д��һ�У�ƫ�����Ŀ�ʼ����
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
	// �����޸��ļ�����
	public boolean UpFileDataByLine(String filePath, String replaceContent,
			String regex,long start,long len) {
		// �޸��ļ�����,д������
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(filePath, "rw");
			String line = null;
			long lastPoint =start; // ��ס��һ�ε�ƫ����
			while ((line = raf.readLine()) != null) {
				final String str = replaceFileContent(line, replaceContent,
						regex); // ��ȡ�ļ�һ�У���ƥ��������ַ����滻��
				final long ponit = raf.getFilePointer();
				raf.seek(lastPoint);
				raf.writeBytes(str);
				lastPoint = ponit; // ��ȡһ�У�ָ��ָ����һ�п�ͷ������д��һ�У�ƫ�����Ŀ�ʼ����
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
	 * �滻ƥ����������ݡ�
	 * </p>
	 * 
	 * @param srcContent
	 *            Դ�ַ���
	 * @param replaceContent
	 *            �滻����
	 * @param regex
	 *            ������ʽ
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
