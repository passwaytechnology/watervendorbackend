package passway.technology.paaspay.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import passway.technology.paaspay.model.WaterBill;

public class ExcelHelper {
	  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "Invoice No.", "Consumer", "Meter No.", "Previous Reading", "Current Reading" };
	  static String SHEET = "BILLS";
	  
	  public static List<WaterBill> excelToData(InputStream is) {
		    try {
		      Workbook workbook = new XSSFWorkbook(is);

		      Sheet sheet = workbook.getSheet(SHEET);
		      Iterator<Row> rows = sheet.iterator();

		      List<WaterBill> waterbills = new ArrayList<WaterBill>();

		      //	DataFormatter objDefaultFormat = new DataFormatter();
		      int rowNumber = 0;
		      while (rows.hasNext()) {
		        Row currentRow = rows.next();

		        // skip header
		        if (rowNumber == 0) {
		          rowNumber++;
		          continue;
		        }

		        Iterator<Cell> cellsInRow = currentRow.iterator();

		        WaterBill waterbill = new WaterBill();
		    //    DataFormatter dataFormatter = new DataFormatter();
		        int cellIdx = 0;
		        while (cellsInRow.hasNext()) {
		          Cell currentCell = cellsInRow.next();

		          switch (cellIdx) {
		          case 0:
		        	  CellType type = currentCell.getCellType();
		        	  if (type == CellType.STRING) {
		        		  waterbill.setId( currentCell.getStringCellValue());
		        	  }else {
		        		
		        	 	  String strValue = String.valueOf(currentCell.getNumericCellValue());
		        		  waterbill.setId(strValue);
		        	  }
		            break;

		          case 1:
		        	
		        	  CellType type1 = currentCell.getCellType();
		        	  if (type1 == CellType.STRING) {
		        		  waterbill.setConsumerid(currentCell.getStringCellValue());
		        	  }else {
		        	 	  String strValue = String.valueOf(currentCell.getNumericCellValue());
		        		  waterbill.setConsumerid(strValue);
		        	  }
		            break;

		          case 2:

//		        	  String strValue = String.valueOf(currentCell.getNumericCellValue());
		        	//  String strValue = String.valueOf(NumberToTextConverter.toText(currentCell.getNumericCellValue()));
		        	 // String strValue2 = currentCell.getStringCellValue();
		        	//  waterbill.setMeternumber(currentCell.getStringCellValue());
		        	
		        	  CellType type2 = currentCell.getCellType();
		        	  if (type2 == CellType.STRING) {
		        		  waterbill.setMeternumber(currentCell.getStringCellValue());
		        	  }else {
		        	 	  String strValue = String.valueOf(currentCell.getNumericCellValue());
		        		  waterbill.setMeternumber(strValue);
		        	  }
		            break;

		          case 3:
		        	//  String data0 = String.valueOf(NumberToTextConverter.toText(currentCell.getNumericCellValue()));
		        	//  String strValue3 = currentCell.getStringCellValue();
//		        	  Cell strValue =currentCell.getStringCellValue();
//		        	  DataFormatter formatter = new DataFormatter();
//		        	  String strValue1 = formatter.formatCellValue(strValue);
		        	//  waterbill.setPrevious_reading(currentCell.getStringCellValue());
		        	  CellType type3 = currentCell.getCellType();
		        	  if (type3 == CellType.STRING) {
		        		  waterbill.setPrevious_reading(currentCell.getStringCellValue());
		        	  }else {
		        	 	  String strValue = String.valueOf(currentCell.getNumericCellValue());
		        		  waterbill.setPrevious_reading(strValue);
		        	  }
		            break;
		            
		          case 4:

		        	//  String strValue2 = String.valueOf(NumberToTextConverter.toText(currentCell.getNumericCellValue()));
		        	 // waterbill.setCurrent_reading(currentCell.getStringCellValue());
		        	  CellType type4 = currentCell.getCellType();
		        	  if (type4 == CellType.STRING) {
		        		  waterbill.setCurrent_reading(currentCell.getStringCellValue());
		        	  }else {
		        	 	  String strValue = String.valueOf(currentCell.getNumericCellValue());
		        		  waterbill.setCurrent_reading(strValue);
		        	  }
		            break;

		          default:
		            break;
		          }

		          cellIdx++;
		        }

		        waterbills.add(waterbill);
		      }

		      workbook.close();

		      return waterbills;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		    }
		  }
	  

	  public static ByteArrayInputStream DataToExcel(List<WaterBill> waterbills) {

	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);

	      // Header
	      Row headerRow = sheet.createRow(0);

	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }

	      int rowIdx = 1;
	      for (WaterBill waterbill : waterbills) {
	        Row row = sheet.createRow(rowIdx++);
	     
	        row.createCell(0).setCellValue(waterbill.getId());
	        row.createCell(1).setCellValue(waterbill.getConsumerid());
	        String meternum=waterbill.getMeternumber();
	        row.createCell(2).setCellValue(meternum);
	        String previous_reading=waterbill.getPrevious_reading();
	        row.createCell(3).setCellValue(previous_reading);
	        row.createCell(4).setCellValue("");
	      }

	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	  }
	  

}
