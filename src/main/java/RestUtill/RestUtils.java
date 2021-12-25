package RestUtill;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class RestUtils {
	static DataFormatter formatter=new DataFormatter();

	public static String getFirstName() {
		String generatedString = RandomStringUtils.randomAlphabetic(2);
		return ("John"+generatedString);
	}
	public static String getShortName() {
		String generatedString = RandomStringUtils.randomAlphabetic(2);
		return ("S"+generatedString);
	}

	public static String getLastName() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		return ("Kenedy"+generatedString);
	}
	
	public static String getUserName() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		return ("John"+generatedString);
	}
	
	public static String TrxId() {
		String generatedString = RandomStringUtils.randomAlphabetic(8);
		return ("TrxId"+generatedString);
	}
	public static String getPassword() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		return ("John"+generatedString);
	}
	
	public static String getEmail() {
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		return (generatedString+"@yopmail.com");
	}
	
	public static String empName() {
		String generatedString = RandomStringUtils.randomAlphabetic(4);
		return ("John"+generatedString);
	}
	public static String partnerCode() {
		String generatedString = RandomStringUtils.randomAlphabetic(2);
		return ("J"+generatedString);
	}
	
	public static String empSal() {
		String generatedString = RandomStringUtils.randomNumeric(5);
		return (generatedString);
	}
	
	public static String empAge() {
		String generatedString = RandomStringUtils.randomNumeric(2);
		return (generatedString);
	}
	public static String mobileNumber() {
		String generatedString = RandomStringUtils.randomNumeric(10);
		return (generatedString);
		
	}

	public static String cifNumber() {
		String generatedString = RandomStringUtils.randomNumeric(10);
		return (generatedString);}
	
	public static String bankAccountNo() {
		String generatedString = RandomStringUtils.randomNumeric(11);
		return (generatedString);
	}
	public static String identityNumber() {
		String generatedString = RandomStringUtils.randomNumeric(16);
		return (generatedString);
	}
	/*
	@DataProvider(name="driveTest")
	public static Object[][] getData() throws IOException
	{
		FileInputStream fis=new FileInputStream("D:\\Automation_Workspace\\RestAssured_PAYLATER\\DataSheets\\DataDrivenTest.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet sheet=wb.getSheetAt(0);
		int rowCount=sheet.getPhysicalNumberOfRows();
		XSSFRow row=sheet.getRow(0);
		int colCount=row.getLastCellNum();
		Object data[][]=new Object[rowCount-1][colCount];
		for(int i=0;i<rowCount-1;i++)
		{
			row=sheet.getRow(i+1);
			for(int j=0;j<colCount;j++)
			{
				XSSFCell cell=row.getCell(j);
				
				data[i][j]=formatter.formatCellValue(cell);
				
			
			}
		}
		return data;
		*/
}
