package api.utilities;
import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviderUtil {
	
	@DataProvider(name="PostData")
	public String[][] getAllDataPost() throws IOException
	{
		String path = System.getProperty("user.dir") + "/src/test/resources/UserAPI_RA_TestData.xlsx";
		ExcelUtilities xl=new ExcelUtilities(path);
		
		int rownum=xl.getRowCount("POST");
		int colcount=xl.getCellCount("POST",0);
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]=xl.getCellData("POST", i, j);
			}
		}
		return apidata;
	}
	
	@DataProvider(name="PutData")
	public String[][] getAllDataPut() throws IOException
	{
		String path = System.getProperty("user.dir") + "/src/test/resources/UserAPI_RA_TestData.xlsx";
		ExcelUtilities xl=new ExcelUtilities(path);
		
		int rownum=xl.getRowCount("PUT");
		int colcount=xl.getCellCount("PUT",0);
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]=xl.getCellData("PUT", i, j);
			}
		}
		return apidata;
	}
	
	@DataProvider(name="GetData")
	public String[][] getAllDataGet() throws IOException
	{
		String path = System.getProperty("user.dir") + "/src/test/resources/UserAPI_RA_TestData.xlsx";
		ExcelUtilities xl=new ExcelUtilities(path);
		
		int rownum=xl.getRowCount("GET");
		int colcount=xl.getCellCount("GET",0);
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]=xl.getCellData("GET", i, j);
			}
		}
		return apidata;
	}
	
	@DataProvider(name="DeleteData")
	public String[][] getAllDataDelete() throws IOException
	{
		String path = System.getProperty("user.dir") + "/src/test/resources/UserAPI_RA_TestData.xlsx";
		ExcelUtilities xl=new ExcelUtilities(path);
		
		int rownum=xl.getRowCount("DELETE");
		int colcount=xl.getCellCount("DELETE",0);
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				apidata[i-1][j]=xl.getCellData("DELETE", i, j);
			}
		}
		return apidata;
	}
	
}

