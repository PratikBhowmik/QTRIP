package qtriptest;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DP {

	// String filepath = System.getProperty("user.dir");

	@DataProvider(name = "my data provider")
	public static Object[][] dpMethod(Method m) throws IOException {


		Object[][] dataTest1 = readCompleteExcel("DatasetsforQTrip.xlsx", "TestCase01");

		Object[][] dataTest2 = readCompleteExcel("DatasetsforQTrip.xlsx", "TestCase02");

		Object[][] dataTest3 = readCompleteExcel("DatasetsforQTrip.xlsx", "TestCase03");

		Object[][] dataTest4 = readCompleteExcel("DatasetsforQTrip.xlsx", "TestCase04");

		switch (m.getName()) {
			case "TestCase01":
				return dataTest1;

			case "TestCase02":
				return dataTest2;

			case "TestCase03":
				return dataTest3;

			case "TestCase04":
				return dataTest4;

		}

		return null;
	}

	public static Object[][] readCompleteExcel(String testDataExcelFileName, String sheetName)
			throws IOException {

		String testDataExcelPath = System.getProperty("user.dir") + "/src/test/resources/";
		String filename = testDataExcelPath + testDataExcelFileName;

		// File fileName = new File(filePath);
		FileInputStream file = new FileInputStream(filename);

		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		// Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum();
		int colsCount = sheet.getRow(1).getLastCellNum();

		Object[][] datas = new Object[rowCount][colsCount - 1];

		for (int outer = 1; outer <= rowCount; outer++) { // outer for loop to iterate each row
			XSSFRow rows = sheet.getRow(outer);
			// List<String> rowValues = new ArrayList<String>();
			for (int inner = 1; inner < colsCount; inner++) { // inner for loop to iterate each cell

				XSSFCell cell = rows.getCell(inner);
				switch (cell.getCellType()) {
					case STRING:
						// System.out.print(cell.getStringCellValue());
						// rowString += cell.getStringCellValue();
						// rowValues.add(cell.getStringCellValue());
						datas[outer - 1][inner - 1] = cell.getStringCellValue();
						break;
					case NUMERIC:
						// System.out.print(cell.getNumericCellValue());
						// rowString += cell.getNumericCellValue();
						// rowValues.add(cell.getNumericCellValue());
						datas[outer - 1][inner - 1] = cell.getNumericCellValue();
						break;
					case BOOLEAN:
						// System.out.print(cell.getBooleanCellValue());
						// rowValues.add(cell.getBooleanCellValue());
						datas[outer - 1][inner - 1] = cell.getBooleanCellValue();
						break;
					default:
						break;
				}
				// rowString += " | ";
				// System.out.print(rowString);
			}
		}
		workbook.close();
		return datas;
	}

}
