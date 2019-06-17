package Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import android.content.Context;
import android.widget.Toast;
import entity.*;

public class ExcelUtil {

	private static WritableFont arial14font = null;

	private static WritableCellFormat arial14format = null;
	private static WritableFont arial10font = null;
	private static WritableCellFormat arial10format = null;
	private static WritableFont arial12font = null;
	private static WritableCellFormat arial12format = null;
	private final static String UTF8_ENCODING = "UTF-8";

	/**
	 * ��Ԫ��ĸ�ʽ���� �����С ��ɫ ���뷽ʽ��������ɫ��...
	 */
	private static void format() {
		try {
			arial14font = new WritableFont(WritableFont.ARIAL, 14,
					WritableFont.BOLD);
			arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
			arial14format = new WritableCellFormat(arial14font);
			arial14format.setAlignment(jxl.format.Alignment.CENTRE);
			arial14format.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

			arial10font = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);
			arial10format = new WritableCellFormat(arial10font);
			arial10format.setAlignment(jxl.format.Alignment.CENTRE);
			arial10format.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			arial10format.setBackground(Colour.GRAY_25);

			arial12font = new WritableFont(WritableFont.ARIAL, 10);
			arial12format = new WritableCellFormat(arial12font);
			// �����ʽ
			arial10format.setAlignment(jxl.format.Alignment.CENTRE);
			// ���ñ߿�
			arial12format.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);

		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʼ��Excel���
	 * 
	 * @param filePath
	 *            ���excel�ļ���·����path/demo.xls��
	 * @param sheetName
	 *            Excel���ı���
	 * @param colName
	 *            excel�а����������������ж����
	 */
	public static void initExcel(String filePath, String sheetName,
			String[] colName) {
		format();
		WritableWorkbook workbook = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				return;
			}
			workbook = Workbook.createWorkbook(file);
			// ���ñ�������
			WritableSheet sheet = workbook.createSheet(sheetName, 0);
			// ����������
			sheet.addCell((WritableCell) new Label(0, 0, filePath,
					arial14format));
			for (int col = 0; col < colName.length; col++) {
				sheet.addCell(new Label(col, 0, colName[col], arial10format));
			}
			// �����и�
			sheet.setRowView(0, 340);
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static <T> void writeObjListToExcel(List<T> objList,
			String fileName, Context c, String str) {
		if (objList != null && objList.size() > 0) {
			WritableWorkbook writebook = null;
			InputStream in = null;
			try {
				WorkbookSettings setEncode = new WorkbookSettings();
				setEncode.setEncoding(UTF8_ENCODING);
				in = new FileInputStream(new File(fileName));
				Workbook workbook = Workbook.getWorkbook(in);
				writebook = Workbook.createWorkbook(new File(fileName),
						workbook);
				WritableSheet sheet = writebook.getSheet(0);

				if (str.equals("����")) {
					AddPayData(objList, sheet);
				}
				if (str.equals("֧��")) {
					AddPayData( objList, sheet) ;

				}
				if (str.equals("Ӧ��") || str.equals("ʵ��")) {

					AddYingShouData( objList, sheet) ;
				}
				if (str.equals("Ӧ��") || str.equals("ʵ��")) {
					AddYingFuData( objList, sheet) ;		
					}
				if (str.equals("����")) {
					AddCountData( objList, sheet) ;
				}

				writebook.write();
				workbook.close();
				Toast.makeText(c, "����Excel�ɹ�", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writebook != null) {
					try {
						writebook.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public <T> void AddIncomeData(List<T> objList, WritableSheet sheet) {
		for (int j = 0; j < objList.size(); j++) {

			Income income = (Income) objList.get(j);
			List<String> list = new ArrayList<String>();
			list.add("");
			list.add(String.valueOf(income.getId()));
			list.add(income.getMenuName());
			list.add(String.valueOf(income.getCount()));
			list.add(income.getIncomeSource());
			list.add(income.getMakePerson());
			list.add(income.getDate());
			list.add(String.valueOf(income.getStatus()));
			list.add(income.getMakeNote());
			for (int i = 0; i < list.size(); i++) {
				try {
					sheet.addCell(new Label(i, j + 1, list.get(i),
							arial12format));
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (list.get(i).length() <= 4) {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 8);
				} else {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 5);
				}
			}
			// �����и�
			try {
				sheet.setRowView(j + 1, 350);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			}
		}

	}

	public static <T> void AddPayData(List<T> objList, WritableSheet sheet) {
		for (int j = 0; j < objList.size(); j++) {

			Pay pay = (Pay) objList.get(j);
			List<String> list = new ArrayList<String>();
			list.add("");
			list.add(String.valueOf(pay.getId()));
			list.add(pay.getMenuName());
			list.add(String.valueOf(pay.getCount()));
			list.add(pay.getPayTo());
			list.add(pay.getMakePerson());
			list.add(pay.getDate());
			list.add(String.valueOf(pay.getStatus()));
			list.add(pay.getMakeNote());
			for (int i = 0; i < list.size(); i++) {
				try {
					sheet.addCell(new Label(i, j + 1, list.get(i),
							arial12format));
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (list.get(i).length() <= 4) {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 8);
				} else {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 5);
				}
			}
			// �����и�
			try {
				sheet.setRowView(j + 1, 350);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			}
		}

	}

	public static <T> void AddYingShouData(List<T> objList, WritableSheet sheet) {
		for (int j = 0; j < objList.size(); j++) {
			YingShou yingshou = (YingShou) objList.get(j);
			List<String> list = new ArrayList<String>();
			list.add("");
			list.add(String.valueOf(yingshou.getId()));
			list.add(String.valueOf(yingshou.getProperty()));
			list.add(yingshou.getMenuName());
			list.add(String.valueOf(yingshou.getCount()));
			list.add(yingshou.getYingShouSource());
			list.add(yingshou.getYingShouObject());
			list.add(yingshou.getTelephone());
			list.add(yingshou.getMakePerson());
			list.add(yingshou.getDate());
			list.add(String.valueOf(yingshou.getStatus()));
			list.add(yingshou.getMakeNote());
			for (int i = 0; i < list.size(); i++) {
				try {
					sheet.addCell(new Label(i, j + 1, list.get(i),
							arial12format));
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (list.get(i).length() <= 4) {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 8);
				} else {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 5);
				}
			}
			// �����и�
			try {
				sheet.setRowView(j + 1, 350);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			}
		}

	}

	public static <T> void AddYingFuData(List<T> objList,
			WritableSheet sheet) {
		for (int j = 0; j < objList.size(); j++) {
						YingFu yingfu = (YingFu) objList.get(j);
						List<String> list = new ArrayList<String>();
						list.add("");
						list.add(String.valueOf(yingfu.getId()));
						list.add(String.valueOf(yingfu.getProperty()));
						list.add(yingfu.getMenuName());
						list.add(String.valueOf(yingfu.getCount()));
						list.add(yingfu.getYingFuTo());
						list.add(yingfu.getYingFuObject());
						list.add(yingfu.getTelephone());
						list.add(yingfu.getMakePerson());
						list.add(yingfu.getDate());
						list.add(String.valueOf(yingfu.getStatus()));
						list.add(yingfu.getMakeNote());
						for (int i = 0; i < list.size(); i++) {
							try {
								sheet.addCell(new Label(i, j + 1, list.get(i),
										arial12format));
							} catch (RowsExceededException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (WriteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (list.get(i).length() <= 4) {
								// �����п�
								sheet.setColumnView(i, list.get(i).length() + 8);
							} else {
								// �����п�
								sheet.setColumnView(i, list.get(i).length() + 5);
							}
						}
						// �����и�
						try {
							sheet.setRowView(j + 1, 350);
						} catch (RowsExceededException e) {
							e.printStackTrace();
						}
					}

	}
	public static <T> void AddCountData(List<T> objList, WritableSheet sheet) {
		for (int j = 0; j < objList.size(); j++) {
			CountEntity count = (CountEntity) objList.get(j);
			List<String> list = new ArrayList<String>();
			list.add("");
			list.add(count.getDate());
			list.add(String.valueOf(count.getShouRuCount()));
			list.add(String.valueOf(count.getZhiChuCount()));
			list.add(String.valueOf(count.getYingShouCount()));
			list.add(String.valueOf(count.getYingFuCount()));
			list.add(String.valueOf(count.getShiShouCount()));
			list.add(String.valueOf(count.getShiFuCount()));
			list.add(String.valueOf(count.getShouRunum()));
			list.add(String.valueOf(count.getZhiChunum()));
			list.add(String.valueOf(count.getYingShounum()));
			list.add(String.valueOf(count.getYingFunum()));
			list.add(String.valueOf(count.getShiShounum()));
			list.add(String.valueOf(count.getShiFunum()));

			for (int i = 0; i < list.size(); i++) {
				try {
					sheet.addCell(new Label(i, j + 1, list.get(i),
							arial12format));
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (list.get(i).length() <= 4) {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 8);
				} else {
					// �����п�
					sheet.setColumnView(i, list.get(i).length() + 5);
				}
			}
			// �����и�
			try {
				sheet.setRowView(j + 1, 350);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			}
		}

	}

}
