
package Excel;

import android.util.SparseIntArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ReadExcel {

    private static ReadExcel creator;
    private WritableWorkbook writableWorkbook;
    private WritableSheet writableSheet;
    private SparseIntArray maxColWidthArray;
    private SparseIntArray maxRowHeightArray;

    private ReadExcel() {
        maxColWidthArray = new SparseIntArray();
        maxRowHeightArray = new SparseIntArray();
    }

    public static ReadExcel getInstance() {
        if (creator == null) {
            synchronized (ReadExcel.class) {
                if (creator == null) {
                    creator = new ReadExcel();
                }
            }
        }
        return creator;
    }


    public ReadExcel createExcel(String pathDir, String name) throws IOException {
        File dir = new File(pathDir);
        if (!dir.exists())
            dir.mkdirs();
        writableWorkbook = Workbook.createWorkbook(new File(pathDir + File.separator + name + ".xls"));
        return this;
    }

    public ReadExcel openExcel(File file) throws IOException, BiffException {
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = Workbook.getWorkbook(fis);
        writableWorkbook = Workbook.createWorkbook(file, wb);
        return this;
    }

    public ReadExcel createSheet(String name) {
        checkNullFirst();
        writableSheet = writableWorkbook.createSheet(name, 0);
        return this;
    }

    public ReadExcel openSheet(int position) {
        checkNullFirst();
        writableSheet = writableWorkbook.getSheet(position);
        checkNullArray();
        maxColWidthArray.clear();
        return this;
    }

 /* @Override
    public ZzExcelCreator insertColumn(int position) {
        checkNullFirst();
        checkNullSecond();
        writableSheet.insertColumn(position);
        return this;
    }

    @Override
    public ZzExcelCreator insertRow(int position) {
        checkNullFirst();
        checkNullSecond();
        writableSheet.insertRow(position);
        return this;
    }

    @Override
    public ZzExcelCreator merge(int col1, int row1, int col2, int row2) throws WriteException {
        checkNullFirst();
        checkNullSecond();
        writableSheet.mergeCells(col1, row1, col2, row2);
        return this;
    }

    @Override
    public ZzExcelCreator mergeColumn(int row, int col1, int col2) throws WriteException {
        checkNullFirst();
        checkNullSecond();
        writableSheet.mergeCells(col1, row, col2, row);
        return this;
    }

    @Override
    public ZzExcelCreator mergeRow(int col, int row1, int row2) throws WriteException {
        checkNullFirst();
        checkNullSecond();
        writableSheet.mergeCells(col, row1, col, row2);
        return this;
    }

    @Override
    public ZzExcelCreator fillNumber(int col, int row, double number, WritableCellFormat format) throws WriteException {
        checkNullFirst();
        checkNullSecond();
        if (format != null && format.getWrap()) {
            setRowHeight(row, getRealRowHeight(row, number + "", format));
            setColumnWidth(col, getRealColWidth(col, number + "", format));
        }
        if (format == null)
            writableSheet.addCell(new Number(col, row, number));
        else
            writableSheet.addCell(new Number(col, row, number, format));
        return this;
    }

    @Override
    public ZzExcelCreator fillContent(int col, int row, String content, WritableCellFormat format) throws WriteException {
        checkNullFirst();
        checkNullSecond();
        if (content == null) {
            content = "";
        }

        if (format != null && format.getWrap()) {
            setRowHeight(row, getRealRowHeight(row, content, format));
            setColumnWidth(col, getRealColWidth(col, content, format));
        }

        if (format == null)
            writableSheet.addCell(new Label(col, row, content));
        else
            writableSheet.addCell(new Label(col, row, content, format));

        return this;
    }

    */
    public void close() throws IOException, WriteException {
        checkNullFirst();
        writableWorkbook.write();
        writableWorkbook.close();
        writableWorkbook = null;
        writableSheet = null;
    }

    private void checkNullFirst() {
        if (writableWorkbook == null) {
            throw new NullPointerException("writableWorkbook is null, please invoke the #createExcel(String, String) method or the #openExcel(File) method first.");
        }
    }

    @SuppressWarnings("unused")
	private void checkNullSecond() {
        if (writableSheet == null) {
            throw new NullPointerException("writableSheet is null, please invoke the #createSheet(String) method or the #openSheet(int) method first.");
        }
    }

    private void checkNullArray() {
        if (maxRowHeightArray == null) {
            maxRowHeightArray = new SparseIntArray();
        }
        if (maxColWidthArray == null) {
            maxColWidthArray = new SparseIntArray();
        }
    }

    protected WritableSheet getWritableSheet() {
        return writableSheet;
    }

    protected WritableWorkbook getWritableWorkbook() {
        return writableWorkbook;
    }
}
