package com.w3w.generic.functions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDataProvider {
	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;      
	String filename;
	String SheetName;
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	@SuppressWarnings("unused")
	private static XSSFFormulaEvaluator evaluator;
	public ExcelDataProvider(String path) {

		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}


	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}

	}

	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);

			if(cell==null)
				return "";
			//System.out.println(cell.getCellType());
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){

				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal =Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText =
							(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cal.get(Calendar.MONTH)+1 + "/" + 
							cellText;

					//System.out.println(cellText);

				}



				return cellText;
			}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return ""; 
			else 
				return String.valueOf(cell.getBooleanCellValue());

		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if(index==-1)
				return "";


			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(colNum);
			if(cell==null)
				return "";

			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){

				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal =Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText =
							(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH)+1 + "/" +
							cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cellText;

					// System.out.println(cellText);

				}



				return cellText;
			}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return "";
			else 
				return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data){
		try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;


			sheet = workbook.getSheetAt(index);


			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);

			cell = row.getCell(colNum);	
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			//CellStyle cs = workbook.createCellStyle();
			//cs.setWrapText(true);
			//cell.setCellStyle(cs);
			cell.setCellValue(data);




			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean setCellData(String sheetName,String colName,int rowNum, String data,int numOfLines){
		try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;


			sheet = workbook.getSheetAt(index);


			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);

			cell = row.getCell(colNum);	
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			//CellStyle cs = workbook.createCellStyle();
			//cs.setWrapText(true);
			//cell.setCellStyle(cs);
			cell.setCellValue(data);

			//to enable newlines you need set a cell styles with wrap=true
			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cell.setCellStyle(cs);

			//increase row height to accomodate two lines of text
			row.setHeightInPoints((numOfLines*sheet.getDefaultRowHeightInPoints()));

			//adjust column width to fit the content
			sheet.autoSizeColumn((short)2);


			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}


	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
		//System.out.println("setCellData setCellData******************");
		try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;


			sheet = workbook.getSheetAt(index);
			//System.out.println("A");
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum=i;
			}

			if(colNum==-1)
				return false;
			sheet.autoSizeColumn(colNum); //ashish
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);

			cell = row.getCell(colNum);	
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();

			//cell style for hyperlinks
			//by default hypelrinks are blue and underlined
			CellStyle hlink_style = workbook.createCellStyle();
			XSSFFont hlink_font = workbook.createFont();
			hlink_font.setUnderline(XSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			//hlink_style.setWrapText(true);

			XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

			fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}



	// returns true if sheet is created successfully else false
	public boolean addSheet(String  sheetname){		

		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);	
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if sheet is removed successfully else false if sheet does not exist
	public boolean removeSheet(String sheetName){		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return false;

		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// returns true if column is created successfully
	public boolean addColumn(String sheetName,String colName){
		//System.out.println("**************addColumn*********************");

		try{				
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;

			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			sheet=workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			//cell = row.getCell();	
			//if (cell == null)
				//System.out.println(row.getLastCellNum());
			if(row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;


	}
	// removes a column and all the contents
	public boolean removeColumn(String sheetName, int colNum) {
		try{
			if(!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			sheet=workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			style.setFillPattern(HSSFCellStyle.NO_FILL);



			for(int i =0;i<getRowCount(sheetName);i++){
				row=sheet.getRow(i);	
				if(row!=null){
					cell=row.getCell(colNum);
					if(cell!=null){
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;

	}
	// find whether sheets exists	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
				return false;
			else
				return true;
		}
		else
			return true;
	}

	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if(row==null)
			return -1;

		return row.getLastCellNum();



	}
	//String sheetName, String testCaseName,String keyword ,String URL,String message
	public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){
		//System.out.println("ADDING addHyperLink******************");

		url=url.replace('\\', '/');
		if(!isSheetExist(sheetName))
			return false;

		sheet = workbook.getSheet(sheetName);

		for(int i=2;i<=getRowCount(sheetName);i++){
			if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
				//System.out.println("**caught "+(i+index));
				setCellData(sheetName, screenShotColName, i+index, message,url);
				break;
			}
		}


		return true; 
	}
	public int getCellRowNum(String sheetName,String colName,String cellValue){

		for(int i=2;i<=getRowCount(sheetName);i++){
			if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
				return i;
			}
		}
		return -1;

	}

	/*   public static void main(String args[]) throws Exception{

               // 	String[][] test = getTableArray("C:\\CoreHRTestData\\Test.xlsx","Transfer");            
                	HashMap<String,String>  excelHashMap= getExcelInHashMap(System.getProperty("user.dir") + "\\src\\test\\resources\\hcm\\coreHr\\PromoteDataSheet.xlsx","Promote") ;
                	System.out.println(getExcelColCount(excelHashMap));
                	System.out.println(getExcelRowCount(excelHashMap));



                	//System.out.println("" + test);
                } 
	 */
	/* public static int getExcelColCount(HashMap<String,String> excelHashMap){

                	int colCount =0 ;
                	for ( String key : excelHashMap.keySet() ) {
                	    if(key.contains("_0")){

                	    	colCount++;
                	    }
                	}

                	return colCount;
                }
	 */
	/*  public static int getExcelRowCount(HashMap<String,String> excelHashMap){

                	int colCount =0 ;
                	for ( String key : excelHashMap.keySet() ) {
                	    if(key.contains("_0")){

                	    	colCount++;
                	    }
                	}



                	return excelHashMap.size()/colCount;
                }*/


	/*              public static Object[][] dataProviderSkipFix(String fileName, String SheetName) throws Exception {

                //	HashMap<String,String> excelData = ExcelDataProvider.getExcelInHashMap(System.getProperty("user.dir") + "\\src\\test\\resources\\hcm\\coreHr\\PromoteDataSheet.xlsx" , "Promote");

                	HashMap<String,String> excelData = ExcelDataProvider.getExcelInHashMap(fileName,SheetName);

                	int colCount = ExcelDataProvider.getExcelColCount(excelData);
             	    int rowCount = ExcelDataProvider.getExcelRowCount(excelData);
             	    Object[][] data = new Object[rowCount-1][1];

             	    List<String> colNames= new ArrayList<String>();
             	    int colIndex=1;
             	   colNames.add(0, "Dummy");
             	    for ( String key : excelData.keySet() ) {
                 	    if(key.contains("_0")){

                 	    	colNames.add(colIndex, key.split("_0")[0]);
                 	    	colIndex++;
                 	    }
                 	}


             	    for(int row=2;row<=rowCount;row++){

             	    	Hashtable<String, String> table = new Hashtable<String,String>();
             	    	for(int col=1;col<=colCount;col++) {

             	    		table.put(colNames.get(col), excelData.get(colNames.get(col)+ "_" + (row-1)));
             	    		//System.out.println(colNames.get(col));
             	    		//System.out.println(colNames.get(col)+ "_" + (row-1));

             	    	}

             	    	data[row-2][0]= table;

             	    }


                	return data;
                }
	 */
	public static Object[][] dataProvider(String fileName, String SheetName, String noMentionOfSkip) throws Exception {

		//	HashMap<String,String> excelData = ExcelDataProvider.getExcelInHashMap(System.getProperty("user.dir") + "\\src\\test\\resources\\hcm\\coreHr\\PromoteDataSheet.xlsx" , "Promote");

		//HashMap<String,String> excelData = ExcelDataProvider.getExcelInHashMap(fileName,SheetName);


		ExcelDataProvider xls_file = new ExcelDataProvider(fileName);


		int colCount = xls_file.getColumnCount(SheetName);
		int rowCount = xls_file.getRowCount(SheetName);

		int rowCountActual = xls_file.getRowCount(SheetName);

		int rowReduce =1;

		if(noMentionOfSkip.equalsIgnoreCase("Y")) {

			for(int row=2 ;row<=rowCount;row++){

				if(xls_file.getCellData(SheetName, "execute", row).equalsIgnoreCase("N")){

					rowReduce++;
				}

			}

		}

		rowCountActual = rowCount - rowReduce ;


		Object[][] data = new Object[rowCountActual][1];


		List<String> colNames= new ArrayList<String>();
		int colIndex=0;
		//colNames.add(0, "Dummy");



		for(int col=0;col<colCount;col++) {

			colNames.add(col, xls_file.getCellData(SheetName, col, 1));

		}



		int actualRowCount=2;
		for(int row=2;row<=rowCount;row++){

			if(!xls_file.getCellData(SheetName, "execute", row).equalsIgnoreCase("N") && noMentionOfSkip.equalsIgnoreCase("Y")) {


				Hashtable<String, String> table = new Hashtable<String,String>();
				for(int col=0;col<colCount;col++) {


					table.put(colNames.get(col), xls_file.getCellData(SheetName, col, row));

				}

				data[actualRowCount-2][0]= table;
				actualRowCount++;
			}

			if(noMentionOfSkip.equalsIgnoreCase("N")) {


				Hashtable<String, String> table = new Hashtable<String,String>();
				for(int col=0;col<colCount;col++) {


					table.put(colNames.get(col), xls_file.getCellData(SheetName, col, row));

				}

				data[actualRowCount-2][0]= table;
				actualRowCount++;
			}


		}


		return data;
	}




	public static String[][] getTableArray(String fileName, String SheetName) throws Exception {   

		String[][] tabArray = null;

		try {

			File fileobj = new File(fileName);
			String filepath = fileobj.getAbsolutePath();

			FileInputStream ExcelFile = new FileInputStream(filepath);



			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			evaluator=  new XSSFFormulaEvaluator(ExcelWBook);

			int startRow = 0;

			int startCol =0;

			int ci,cj;

			int totalRows = ExcelWSheet.getLastRowNum();

			int totalCols =ExcelWSheet.getRow(0).getPhysicalNumberOfCells();


			// you can write a function as well to get Column count


			tabArray=new String[totalRows+1][totalCols];

			ci=0;

			for (int i=startRow;i<=totalRows;i++, ci++) {                        

				cj=0;

				for (int j=startCol;j<totalCols;j++, cj++){

					tabArray[ci][cj]=getCellData(i,j);

				

				}

			}

		}

		catch (FileNotFoundException e){

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e){

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return(tabArray);

	}

	public static String getCellData(int RowNum, int ColNum) throws Exception 
	{

		try{

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			int dataType = 0;
			try {
				dataType = Cell.getCellType();

			}catch(NullPointerException e){
				return "";

			}
			if  (dataType == 3) {
				//System.out.println("tesst" +dataType);
				return "";

			}

			else if(HSSFCell.CELL_TYPE_NUMERIC==dataType)
			{

				if(HSSFDateUtil.isCellDateFormatted(Cell))           
				{
					SimpleDateFormat target = new SimpleDateFormat("MM/DD/YY");

					Date date =Cell.getDateCellValue();
					String newDate = target.format(date);

					System.out.println("Test"+newDate);
					return Cell.getDateCellValue().toString();
				}

				return ""+Cell.getNumericCellValue();




			}

			else
			{

				String CellData = Cell.getStringCellValue();

				return CellData;

			}
		}catch (Exception e){

			System.out.println(e.getMessage());

			throw (e);

		}


	}







	public static String convertStringToDate(String dateString) throws ParseException
	{ 




		String[] temp = dateString.split("-");
		String day =temp[0];
		String yy = temp[2];

		SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputFormat.parse(temp[1]));
		SimpleDateFormat outputFormat = new SimpleDateFormat("MM");
		String mon=outputFormat.format(cal.getTime());

		return mon+"/"+day+"/"+yy;
	}



	/*public static HashMap<String,String> getExcelInHashMap(String fileName, String SheetName) throws Exception {   

                                   // String[][] tabArray = null;

                                	HashMap<String,String> excelData = new HashMap<String,String>();

                                	try {



                                				   File fileobj = new File(fileName);
                                                   String filepath = fileobj.getAbsolutePath();
                                                   FileInputStream ExcelFile = new FileInputStream(filepath);


                                                    // Access the required test data sheet

                                                    ExcelWBook = new XSSFWorkbook(ExcelFile);
                                                    ExcelWSheet = ExcelWBook.getSheet(SheetName);
                                                    evaluator=  new XSSFFormulaEvaluator(ExcelWBook);
                                                    int startRow = 0;
                                                    int startCol =0;
                                                    int ci,cj;
                                                    int totalRows = ExcelWSheet.getLastRowNum();
                                                    int totalCols =ExcelWSheet.getRow(0).getPhysicalNumberOfCells();


                                                    // you can write a function as well to get Column count


                                                    //tabArray=new String[totalRows+1][totalCols];

                                                    ci=0;
                                                    for (int i=startRow;i<=totalRows;i++, ci++) {                        
                                                                   cj=0;
                                                                    for (int j=startCol;j<totalCols;j++, cj++){
                                                                    				if(i==0){
                                                                    					excelData.put(getCellData(0,j) + "_0", getCellData(0,j)) ;
                                                                    					//System.out.println(getCellData(0,j) + "_0");
                                                                    					//System.out.println(getCellData(0,j));
                                                                    				}

                                                                    				if(i!=0){

                                                                    					if(getCellData(i,j)==null){
                                                                    						excelData.put(getCellData(0,j) + "_" + i, "") ;
                                                                    					}else

                                                                    						excelData.put(getCellData(0,j) + "_" + i, getCellData(i,j)) ;
                                                                    					//System.out.println(getCellData(0,j) + "_" + i);
                                                                    					//System.out.println(getCellData(i,j));
                                                                    				}

                                                                      }
                                                     }

                                                                 }

                                                 catch (FileNotFoundException e){

                                                                 System.out.println("Could not read the Excel sheet");

                                                                 e.printStackTrace();

                                                                 }

                                                 catch (IOException e){

                                                                 System.out.println("Could not read the Excel sheet");

                                                                 e.printStackTrace();

                                                                 }

                                                 return(excelData);

                                                 }*/


	public static HashMap<String,String> getExcelInHashMap_new(String fileName, String SheetName) throws Exception {   

		// String[][] tabArray = null;

		HashMap<String,String> excelData = new HashMap<String,String>();










		return excelData;




	}


}


