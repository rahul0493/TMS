package com.quinnox.flm.tms.module.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.UserRoleService;
import com.quinnox.flm.tms.module.beans.HolidayBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.ShiftDetailsBean;
import com.quinnox.flm.tms.module.beans.TripSheetDetailsBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.HolidayList;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.TripSheetDetails;
import com.quinnox.flm.tms.module.model.Vendor;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.service.FLMService;
import com.quinnox.flm.tms.module.service.HolidayService;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.service.ShiftDetailsService;
import com.quinnox.flm.tms.module.service.TripSheetDetailsService;
import com.quinnox.flm.tms.module.shiftplanner.bean.AllowanceBean;

import com.quinnox.flm.tms.module.shiftplanner.service.ShiftAllowanceService;
import com.quinnox.flm.tms.module.util.JsonConverter;

@Controller
@RequestMapping("/tms/financePage")
public class FinanceController {
	@Autowired
	private CabRequestService cabRequestService;

	@Autowired
	private TripSheetDetailsService tripSheetDetailsService;
	
	@Autowired
	private FLMService flmService;

	@Autowired
	private ProjectDetailsService projectDetailsService;
	
	@Autowired
	private ShiftDetailsService shiftDetailsService;
	
	@Autowired
	private HolidayService holidayService;
	
	private XSSFWorkbook workbook;

	@Autowired
	private ShiftAllowanceService shiftAllowanceService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	
	private static final Logger LOG = Logger.getLogger(FinanceController.class);
	
	@RequestMapping(value = "/finance", method = RequestMethod.GET)
	public ModelAndView holiday() {
		ModelAndView modelAndView = new ModelAndView();
		List<LocationMaster> location = holidayService.getAllLocation();
		List<Account> projectAccount = userRoleService.getAllAccount();
		modelAndView.addObject("accountList", projectAccount);
		modelAndView.addObject("location", location);
		modelAndView.setViewName("tms/listOfHoliday");
		return modelAndView;
	}
	
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public ModelAndView getLocation() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tms/listOfHoliday");
		return modelAndView;
	}
	
	@RequestMapping(value = "/getProjectList", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectTracksByAccount(@RequestBody Integer accountId) {

		List<ProjectDetailsBean> projectList = projectDetailsService.findProjectTracksByAccountId(accountId);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(projectList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}
	@RequestMapping(value = "/updateAllowance", method = RequestMethod.POST)
	@ResponseBody
	public String updateAllowance(@RequestBody String allowanceJson, HttpServletRequest request) {
		List<ShiftDetailsBean> shiftBeanList = JsonConverter.jsonToAllowanceList(allowanceJson);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		
		for(ShiftDetailsBean bean : shiftBeanList)
		{
			Shift shift = new Shift();
			if(bean.getShiftId() != null)
			{
				shift.setShiftId(bean.getShiftId());
				shift.setEnumValues(bean.getEnumValues());
				shift.setShiftInitials(bean.getShiftInitials());
				shift.setShiftName(bean.getShiftName());
				shift.setAllowance(bean.getAllowance());
			}
			
			shiftDetailsService.updateAllowance(shift);
			try {
				arrayToJson = objectMapper.writeValueAsString("Data was successfully updated");
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					arrayToJson = objectMapper.writeValueAsString("Data was not updated");
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
		return arrayToJson;
	}
	
	@RequestMapping(value = "/getShifts", method = RequestMethod.GET)
	@ResponseBody
	private String getShifts() {
		List<ShiftDetailsBean> shiftBeanList = new ArrayList<ShiftDetailsBean>();
		List<Shift> normalShift=projectDetailsService.findShiftByType(TmsConstant.NORMAL) ;
		List<Shift> holidayShift=projectDetailsService.findShiftByType(TmsConstant.HOLIDAYS) ;
	
		
		for(Shift shiftObj:holidayShift) {
			
			normalShift.add(shiftObj);
		}		
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(normalShift);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}

	@RequestMapping(value = "/saveOrUpdateHolidays", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdateHolidays(@RequestBody String holidayJson, HttpServletRequest request) {
		List<HolidayBean> holidayBeanList = JsonConverter.jsonToHolidayList(holidayJson);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		
		for(HolidayBean bean : holidayBeanList)
		{
			HolidayList model = new HolidayList();
			LocationMaster location =  new LocationMaster();
			model.setHolidayDate(bean.getHolidayDate());
			model.setHolidayName(bean.getHolidayName());
			model.setIsNationalHoliday(bean.getIsNationalHoliday());
			model.setIsPublicHoliday(bean.getIsPublicHoliday());
			location.setLocationId(bean.getLocation().getLocationId());
			model.setLocation(location);
			model.setYear(bean.getYear());
		
			
			if(bean.getHolidayId() != null)
			{
				model.setHolidayId(bean.getHolidayId());
			}
			
			holidayService.saveOrUpdateHolidayList(model);
			try {
				arrayToJson = objectMapper.writeValueAsString("Saved successfully.");
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					arrayToJson = objectMapper.writeValueAsString("Data is not saved");
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
		return arrayToJson;
	}
	@RequestMapping(value = "/getHolidays", method = RequestMethod.GET)
	@ResponseBody
	public String getHolidayList(HttpServletRequest request) {
		Integer location = Integer.parseInt(request.getParameter("location"));
		Integer year = Integer.parseInt(request.getParameter("year"));
		List<HolidayList> holidayList = null;
		
		if(location !=null && year !=null) {
			holidayList = holidayService.getHolidayList(location,year);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			if(holidayList.size()!=0) {
			arrayToJson = objectMapper.writeValueAsString(holidayList);
			}
			else {
				arrayToJson = objectMapper.writeValueAsString("No Data");
				//arrayToJson = "No Record Found";
			}
			System.out.println(arrayToJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
		
		return arrayToJson;
	}
	
	@RequestMapping(value = "/deleteHolidays", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHolidays(HttpServletRequest request) {
		Integer holidayId = Integer.parseInt(request.getParameter("holidayId"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		if(holidayId!= null && holidayId!=0) {
			holidayService.deleteHoliday(holidayId);
			try {
				arrayToJson = objectMapper.writeValueAsString("Deleted successfully.");
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			try {
				arrayToJson = objectMapper.writeValueAsString("Data is not deleted");
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return arrayToJson;
	}

	@RequestMapping(value = "/writeExcel", method = RequestMethod.GET)
	@ResponseBody
	public String buildExcel(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		
		String excelType =  request.getParameter("excelType");
		Integer month = Integer.parseInt(request.getParameter("month"));
		Integer year = Integer.parseInt(request.getParameter("year"));
		String excelFormat = ".xlsx";
		
		
		response.setHeader("Content-Disposition", "attachment; filename=" + excelType+excelFormat);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		workbook = new XSSFWorkbook();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String message = null;
		String returnValue = null;
		if(excelType.equalsIgnoreCase("FinalExcelReport")) {
			String sheetName = "Final Excel Report";
			XSSFSheet sheet = workbook.createSheet(sheetName);
			 setFinalExcelReportHeader(sheet,workbook);
			 message = writeFinalExcelReportHeader(request,sheet,month,year);		
			 
		}
		else if(excelType.equalsIgnoreCase("SAPExcelReport")){
			String sheetName = "SAP Excel Report";
			XSSFSheet sheet = workbook.createSheet(sheetName);
			 setFinanceExcelHeaderSAPPurpose(sheet, workbook);
			 message =  writeFinanceExcelSAPPurpose(request, sheet,month, year);
		}

		if(message.contains("NoData")) {
			returnValue = objectMapper.writeValueAsString("NoData");
			
		}
		 else {
			 workbook.write(response.getOutputStream());
		 }
		
		 return returnValue;
	}
	
	private String writeFinanceExcelSAPPurpose(HttpServletRequest request, XSSFSheet excelSheet,Integer month,Integer year) {
		Row excelRow = null;
		int record = 1;
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		Cell cell = null;
		String returnString = null;
		List<EmpCabRequestDetails> cabList = cabRequestService.findAllRequestsByMonth(month,year); //To Do: take month from front end
		if(cabList.size()==0) {
			returnString = "NoData";
		}else {
			Map<Employee, Map<Integer, List<EmpCabRequestDetails>>> grpByEmp = cabList.stream()
					.collect(Collectors.groupingBy(p -> p.getEmpCabRequest().getUser(),
							Collectors.groupingBy(p -> p.getProjectId(), Collectors.toList())));
			for (Entry<Employee, Map<Integer, List<EmpCabRequestDetails>>> entry : grpByEmp.entrySet()) {
				
				for (Entry<Integer, List<EmpCabRequestDetails>> childMap : entry.getValue().entrySet()) {
					
					double escortTotal = 0;
					double noEscortTotal = 0;
					excelRow = excelSheet.createRow(record++);
					int tripCount= 0;
					for (EmpCabRequestDetails details : childMap.getValue()) {
						
						if (details.getTripSheetId() != null) {
							List<EmpCabRequestDetails> requestDetails = cabRequestService.findCabRequestByTripId(details.getTripSheetId());
							TripSheetDetails sheetDetails = tripSheetDetailsService
									.findByTripSheetId(details.getTripSheetId());
							Vendor vendor = flmService.getVendorById(sheetDetails.getVendorDetails().getVendorId());
							Double escortCost = vendor.getRateWithEscort();
							Double noEscortCost = vendor.getRateWithoutEscort();
							tripCount++;
							if (sheetDetails.getIsEscort()) {
								escortTotal +=(double) escortCost/requestDetails.size();
								
							} else {
								noEscortTotal += (double)noEscortCost/requestDetails.size();
							}
						}

					}
					cell = excelRow.createCell(0);
					excelRow.createCell(0).setCellValue(entry.getKey().getEmpcode());
					cell.setCellStyle(style);

					cell = excelRow.createCell(1);
					excelRow.createCell(1).setCellValue(entry.getKey().getName());
					cell.setCellStyle(style);

					Project project = projectDetailsService.findProjectById(childMap.getKey());

					cell = excelRow.createCell(2);
					excelRow.createCell(2).setCellValue(project.getProjectName());
					cell.setCellStyle(style);

					cell = excelRow.createCell(3);
					excelRow.createCell(3).setCellValue(tripCount);
					cell.setCellStyle(style);
					
					
					cell = excelRow.createCell(4);
					excelRow.createCell(4).setCellValue(escortTotal + noEscortTotal );
					cell.setCellStyle(style);
				}
			}
		
			returnString = "Excel Downloaded Succesfully";
		}
		
		return returnString;
	}

	private void setFinanceExcelHeaderSAPPurpose(XSSFSheet excelSheet, XSSFWorkbook workbook) {
		Row excelHeader = excelSheet.createRow(0);
		XSSFFont font = workbook.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		Cell cell = null;

		excelSheet.setColumnWidth(0, 5000);
		cell = excelHeader.createCell(0);
		cell.setCellValue("Emp ID");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(1, 5000);
		cell = excelHeader.createCell(1);
		cell.setCellValue("Emp Name");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(2, 5000);
		cell = excelHeader.createCell(2);
		cell.setCellValue("Projects");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(3, 5000);
		cell = excelHeader.createCell(3);
		cell.setCellValue("No of Trips / Month");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(4, 5000);
		cell = excelHeader.createCell(4);
		cell.setCellValue("Total");
		cell.setCellStyle(style);

	}

	private void setFinalExcelReportHeader(XSSFSheet excelSheet, XSSFWorkbook workbook) throws FileNotFoundException {
		Row excelHeader = excelSheet.createRow(0);
		XSSFFont font = workbook.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		Cell cell = null;

		excelSheet.setColumnWidth(0, 5000);
		cell = excelHeader.createCell(0);
		cell.setCellValue("Date");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(1, 5000);
		cell = excelHeader.createCell(1);
		cell.setCellValue("Day");
		cell.setCellStyle(style);

	
		
		excelSheet.setColumnWidth(2, 6000);
		cell = excelHeader.createCell(2);
		cell.setCellValue("Cabs No");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(3, 5000);
		cell = excelHeader.createCell(3);
		cell.setCellValue("TripSheet No");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(4, 5000);
		cell = excelHeader.createCell(4);
		cell.setCellValue("EmpID");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(5, 5000);
		cell = excelHeader.createCell(5);
		cell.setCellValue("Employee Name");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(6, 5000);
		cell = excelHeader.createCell(6);
		cell.setCellValue("Project Name");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(7, 5000);
		cell = excelHeader.createCell(7);
		cell.setCellValue("Cost");
		cell.setCellStyle(style);

	
	}

	public String writeFinalExcelReportHeader(HttpServletRequest request, XSSFSheet excelSheet,Integer month,Integer year)
			throws IOException, ParseException {
		int record = 1;
		List<TripSheetDetailsBean> tripDetailsBeanList = tripSheetDetailsService.findAllTripSheetsByMonth(month,year); // change
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Row excelRow = null;
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		Cell cell = null;
		String returnString = null;
			if(tripDetailsBeanList.size()==0) {
				returnString ="NoData";
			}else {
				Map<Date, List<TripSheetDetailsBean>> tripDate = tripDetailsBeanList.stream()
						.collect(Collectors.groupingBy(p -> p.getTripScheduleDate(), Collectors.toList()));

				for (Entry<Date, List<TripSheetDetailsBean>> entry : tripDate.entrySet()) {
					Double costForTrip=0.0;
						for (TripSheetDetailsBean bean : entry.getValue()) {

						List<EmpCabRequestDetails> cabRequestDetailsList = cabRequestService
								.findCabRequestByTripId(bean.getTripId());
						Vendor vendor = flmService.getVendorById(bean.getVendorId());
						Double escortCost = vendor.getRateWithEscort();
						Double noEscortCost = vendor.getRateWithoutEscort();
						if(cabRequestDetailsList.size()!=0) {
						if(bean.getIsEscort()) {
							costForTrip = ( escortCost /  (double)cabRequestDetailsList.size());
						}
						else {	
							costForTrip =  (noEscortCost /(double) cabRequestDetailsList.size());
						}
						}
						for (EmpCabRequestDetails cabRequestDetails : cabRequestDetailsList) {
							excelRow = excelSheet.createRow(record++);
								cell = excelRow.createCell((short) 0);
								excelRow.createCell(0).setCellValue(dateFormat.format(entry.getKey()));
								cell.setCellStyle(style);

								cell = excelRow.createCell(1);
								SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
								excelRow.createCell(1).setCellValue(simpleDateformat.format(entry.getKey()));
								cell.setCellStyle(style);

								cell = excelRow.createCell(2);
								excelRow.createCell(2).setCellValue(bean.getVehicleNumber());
								cell.setCellStyle(style);

								cell = excelRow.createCell(3);
								excelRow.createCell(3).setCellValue(bean.getTripSheetNumber());
								cell.setCellStyle(style);

								cell = excelRow.createCell(4);
							excelRow.createCell(4).setCellValue(cabRequestDetails.getEmpCabRequest().getUser().getEmpcode());
							cell.setCellStyle(style);

							cell = excelRow.createCell(5);
							excelRow.createCell(5).setCellValue(cabRequestDetails.getEmpCabRequest().getUser().getName());
							cell.setCellStyle(style);

							cell = excelRow.createCell(6);
							excelRow.createCell(6).setCellValue(
									projectDetailsService.findProjectById(cabRequestDetails.getProjectId()).getProjectName());
							cell.setCellStyle(style);
							
							cell = excelRow.createCell(7);
							excelRow.createCell(7).setCellValue(costForTrip);
							cell.setCellStyle(style);
							
						}
					}

				}
				returnString = "Excel Downloaded Succesfully";
			}
			return returnString;
	}
	@RequestMapping(value = "/getAllAccount", method = RequestMethod.GET)
	ModelAndView userRoleMapping() {
		ModelAndView modelAndView = new ModelAndView();
		List<Account> projectAccount = userRoleService.getAllAccount();
		modelAndView.addObject("accountList", projectAccount);
		modelAndView.setViewName("tms/allowanceView");
		return modelAndView;
	} 	
	
	

	
	@RequestMapping(value = "/getAllShiftAllowance", method = RequestMethod.GET)
	@ResponseBody
		public String getAllShiftAllowance(HttpServletRequest request,@RequestParam(value="fromDate",required=false) String from,
			@RequestParam(value="toDate" , required=false) String to,@RequestParam(value ="accountId", required=false) Integer accountId,@RequestParam(value="projectId", required=false) Integer projectId) {
	      try{
	    	  Integer fromDate=null;
	    	  Integer toDate=null;
	    	  if(from!=null){
               fromDate = Integer.parseInt(from.substring(0,from.indexOf("-")));	
	      }
	    	  if(to!=null){
	    	   toDate = Integer.parseInt(to.substring(0,to.indexOf("-")));
	    	  }
	    	  List<AllowanceBean> allwanBean = shiftAllowanceService.getAllowance(fromDate,toDate,accountId,projectId);
	    	  ObjectMapper objectMapper = new ObjectMapper();
	  		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	  		String arrayToJson = null;
	  		try {
	  			arrayToJson = objectMapper.writeValueAsString(allwanBean);
	  		} catch (JsonProcessingException e) {
	  			e.printStackTrace();
	  		}

	  		LOG.debug(arrayToJson);
	  		LOG.debug("executed successfully");
	  		return arrayToJson;
			      }
	      catch(Exception e){
	    	  e.printStackTrace();
	    	  return "error";
	      }
	      }
	

}
