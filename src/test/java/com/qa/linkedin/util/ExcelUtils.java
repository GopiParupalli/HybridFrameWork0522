package com.qa.linkedin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static Logger log = Logger.getLogger(ExcelUtils.class);
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	/**
	 * this Method is used to read the data from excelsheets
	 * @param fpath
	 * @param sheetName
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public Object[][] getTestData(String path,String sheetName) throws InvalidFormatException, IOException {
		
	log.debug("Specify the path of file :"+path);
	File srcFile=new File(path);

	log.debug("read the file");
	 fis=new FileInputStream(srcFile);
	 log.debug("Load workbook");
	 workbook=new XSSFWorkbook(fis);

	//Load sheet- Here we are loading first sheetonly
	 sheet= workbook.getSheet(sheetName);
	log.debug("fetch the row count");	//two d array declaration
	int rowCount=sheet.getLastRowNum();
	log.debug("number of rows in the excel sheet is-->"+rowCount);
	int colCount=sheet.getRow(0).getLastCellNum();
	log.debug("number of columns in the excel sheet is -->"+colCount);
	Object[][] data = new Object[rowCount][colCount];
		for (int i = 0; i < rowCount; i++) {
			for (int k = 0; k < colCount; k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}
}
