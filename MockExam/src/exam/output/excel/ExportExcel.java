package exam.output.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.DBMServlet;

public class ExportExcel extends DBMServlet {
	private static final long serialVersionUID = 1L;

	public void exportExcel(HttpServletResponse response, CachedRowSetImpl rowSet, String excelname)
			throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String filename = excelname + format.format(new Date()) + ".xls";
		
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename=" + filename);// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		
		String sheetName = "科目一模拟系统";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, sheetName);
		HSSFRow row = sheet.createRow((short) 0);
		HSSFCell cell;
		try {
			ResultSetMetaData md = rowSet.getMetaData();
			int nColumn = md.getColumnCount();

			for (int i = 1; i <= nColumn; i++) {
				cell = row.createCell((short) (i - 1));
				cell.setCellValue(new HSSFRichTextString(md.getColumnLabel(i)));
			}
			int iRow = 1;
			while (rowSet.next()) {
				row = sheet.createRow((short) iRow);
				for (int j = 1; j <= nColumn; j++) {
					cell = row.createCell((short) (j - 1));
					cell.setCellValue(new HSSFRichTextString(rowSet.getString(j)));
				}
				iRow++;
			}
			
			workbook.write(os);
			workbook.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
