package com.quinnox.flm.tms.module.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinnox.flm.tms.module.beans.CabRequestBean;


public class ReadExcelSheet {


	
	public static String readExcel(final InputStream inputStream){
		XSSFWorkbook workbook;
		 String jsonList = null;
		try {
			workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheet("Asset-Sheet");
			//int num = sheet.getPhysicalNumberOfRows();
		
			Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            List<CabRequestBean> listRows = new ArrayList<CabRequestBean> ();
           
            String monthYear = null;
            while (iterator.hasNext()) {
            	   CabRequestBean cabRequestBean = new CabRequestBean();
                Row currentRow = iterator.next();
                              
              
                // Skip read heading 
                if (currentRow.getRowNum() == 1 || currentRow.getRowNum() == 2) {
               	 continue;
               }
                    if (currentRow.getRowNum() == 0 ) {
                    	monthYear = currentRow.getCell(0).toString();
                    	 continue;
                    }
                   cabRequestBean.setMonthYear(monthYear);
                   cabRequestBean.setProjectName((currentRow.getCell(0).toString()));
                   cabRequestBean.setEmployeeId(Integer.parseInt(String.valueOf((int) currentRow.getCell(1).getNumericCellValue())));
                   cabRequestBean.setEmployeeName((currentRow.getCell(2).toString()));
                   cabRequestBean.setMobileNumber(Long.parseLong((String.valueOf((long) currentRow.getCell(3).getNumericCellValue()))));
                 
                   String [] days = new String[currentRow.getPhysicalNumberOfCells()-4];
                   
                   for(int i = 4; i < currentRow.getPhysicalNumberOfCells(); i++)
                   {
                	   days[i-4] = currentRow.getCell(i).toString();
                   }
                   
                   cabRequestBean.setDays(days);
                   listRows.add(cabRequestBean);
                  // cabRequestBean.setProjectName(currentRow.getCell(0).toString());
            }
          
            ObjectMapper objectMapper = new ObjectMapper();
            jsonList = objectMapper.writeValueAsString(listRows);
           

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return jsonList;
	}
	
}
