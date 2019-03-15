package com.quinnox.flm.tms.module.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.beans.SpocBean;
import com.quinnox.flm.tms.global.beans.TrackMappingBean;
import com.quinnox.flm.tms.global.beans.UserRoleBean;
import com.quinnox.flm.tms.global.mail.TmsMailSender;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.global.service.TrackService;
import com.quinnox.flm.tms.global.service.UserRoleService;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.ShiftDetailsBean;
import com.quinnox.flm.tms.module.beans.TrackDetailsBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.model.ShiftDetails;
import com.quinnox.flm.tms.module.model.Track;
// import com.quinnox.flm.tms.module.model.Track;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.service.ShiftDetailsService;
import com.quinnox.flm.tms.module.shiftplanner.bean.AllowanceBean;
import com.quinnox.flm.tms.module.shiftplanner.model.ShiftAllowanceDetails;
import com.quinnox.flm.tms.module.shiftplanner.service.ShiftAllowanceService;
import com.quinnox.flm.tms.module.util.JsonConverter;

@Controller
@RequestMapping("tms/project")
public class ProjectController {

	@Autowired
	private ProjectDetailsService projectDetailsService;
	@Autowired
	private ShiftDetailsService shiftDetailsService;

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private TrackService trackserivce;

	private XSSFWorkbook workbook;

	@Autowired
	private ShiftAllowanceService shiftAllowanceService;
	@Autowired
	private TmsMailSender tmsMailSend;

	@Autowired
	private EmployeeService employeeService;
	private static final Logger LOG = Logger.getLogger(FinanceController.class);

	@RequestMapping(value = "/project_details", method = RequestMethod.GET)
	public ModelAndView home(ModelMap modal, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		List<Account> accountList = null;
		Set<EmpProjMapping> spocAcc = null;
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		SecurityContextImpl authenticationToken = (SecurityContextImpl) httpSession
				.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authToken = authenticationToken.getAuthentication();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authToken.getAuthorities();
		for (GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equalsIgnoreCase("ADMIN")) {
				accountList = projectDetailsService.findAllAccounts();
				modelAndView.addObject("accountList", accountList);
				break;
			} else {
				spocAcc = projectDetailsService.findAccountByEmpId(profileBean.getEmployeeId());
				modelAndView.addObject("accountList", spocAcc);
			}
		}
		List<Shift> shiftList = projectDetailsService.findShiftByType(TmsConstant.NORMAL);

		List<ProjectDetailsBean> projectBeanList = projectDetailsService
				.findProjectDetailsByAccountId(profileBean.getAccountId());
		// List<ProjectDetailsBean> projects =
		// projectDetailsService.findProjectsByAccountId(profileBean.getAccountId());
		// modelAndView.addObject("projectList", projects);
		modelAndView.addObject("projectBeanList", projectBeanList);
		System.out.println(projectBeanList);
		modelAndView.addObject("shiftList", shiftList);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(projectBeanList);
			System.out.println(arrayToJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("tms/ProjectShiftDetail");
		return modelAndView;
	}

	@RequestMapping(value = "/getProjectShiftDetails", method = RequestMethod.GET)
	@ResponseBody
	public String getProjectShiftDetails(HttpServletRequest request) {

		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		SecurityContextImpl authenticationToken = (SecurityContextImpl) httpSession
				.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authToken = authenticationToken.getAuthentication();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authToken.getAuthorities();
		String projectBeanList = null;

		for (GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equalsIgnoreCase("ADMIN")) {
				projectBeanList = projectDetailsService.findProjectDetailsByAccount(0);
				break;
			} else {
				projectBeanList = projectDetailsService.findProjectDetailsByAccount(profileBean.getEmployeeId());
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(projectBeanList);
			System.out.println(arrayToJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;
	}

	@RequestMapping(value = "/save_project_details", method = RequestMethod.POST)
	@ResponseBody
	public String saveProjectDetails(@RequestBody String projectDetailsJSON, HttpServletRequest request) {

		ProjectDetailsBean projectDetailsBean = JsonConverter.jsonToProjectDetails(projectDetailsJSON);

		Account account = new Account();
		account.setId(projectDetailsBean.getAccountId());
		// account.setId(1);

		Project project = new Project();
		project.setId(projectDetailsBean.getProjectId());
		// project.setId(1);
		project.setAccount(account);

		for (TrackDetailsBean trackBean : projectDetailsBean.getTrackDetails()) {

			// if(trackBean.getShiftDetails().size() <= 0)
			// {
			// continue;
			// }

			Track track = new Track();
			if (trackBean.getTrackDetailsId() == null) {
				track.setTrackName(trackBean.getTrackName());
				track.setProject(project);
				trackBean.setTrackDetailsId(projectDetailsService.saveOrUpdateTrack(track));

			} else {
				track.setTrackId(trackBean.getTrackDetailsId());
			}

			for (ShiftDetailsBean shiftBean : trackBean.getShiftDetails()) {
				Shift shift = new Shift();
				shift.setShiftId(shiftBean.getShiftId());
				// shift.setShiftId(1);

				ShiftDetails shiftDetails = new ShiftDetails();
				shiftDetails.setStartTime(shiftBean.getStartTime());
				shiftDetails.setEndTime(shiftBean.getEndTime());
				shiftDetails.setPickUpEligible(shiftBean.getPickup());
				shiftDetails.setDropEligible(shiftBean.getDrop());
				shiftDetails.setProject(project);
				shiftDetails.setShift(shift);
				shiftDetails.setTrack(track);

				// setProjectShift.add(shiftDetails);

				if (shiftBean.getShiftDetailsId() != null)
					shiftDetails.setId(shiftBean.getShiftDetailsId());

				shiftDetailsService.saveShiftDetails(shiftDetails);
				// shiftDetailsTestService.saveShiftDetails(shiftDetails);
				System.out.println("executed successfully !!!");
			}

		}

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("data", "Saved successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/updateProjectShiftDetails", method = RequestMethod.POST)
	@ResponseBody
	public String updateRequest(HttpServletRequest request) {
		String updateProjectShiftDetails = request.getParameter("updateProjectShiftDetails");
		String getDetails = request.getParameter("getDetails");
		String projectId = request.getParameter("projectId");
		String trackId = request.getParameter("trackId");

		if (getDetails.equals("edit")) {
			List<ShiftDetailsBean> shiftLists = shiftDetailsService.findByTrackId(Integer.parseInt(trackId));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			String arrayToJson = null;
			try {
				arrayToJson = objectMapper.writeValueAsString(shiftLists);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return arrayToJson;
		} else if (getDetails.equals("update")) {
			ProjectDetailsBean projectDetailBean = JsonConverter.jsonToProjectDetails(updateProjectShiftDetails);
			Project project = new Project();
			Track track = new Track();
			Shift shift = new Shift();
			Account account = new Account();
			account.setId(projectDetailBean.getAccountId());
			project.setAccount(account);
			project.setId(projectDetailBean.getProjectId());
			for (TrackDetailsBean trackBean : projectDetailBean.getTrackDetails()) {

				if (trackBean.getTrackDetailsId() != null) {
					projectDetailsService.DeleteShiftDetailsByTrackId(trackBean.getTrackDetailsId());
					track.setTrackId(trackBean.getTrackDetailsId());
				}
				track.setTrackName(trackBean.getTrackName());

				track.setProject(project);
				projectDetailsService.saveOrUpdateTrack(track);

				for (ShiftDetailsBean shiftDetailsBean : trackBean.getShiftDetails()) {

					ShiftDetails shiftModel = new ShiftDetails();
					shiftModel.setEndTime(shiftDetailsBean.getEndTime());
					shiftModel.setStartTime(shiftDetailsBean.getStartTime());
					shift.setShiftId(shiftDetailsBean.getShiftId());
					shiftModel.setPickUpEligible(shiftDetailsBean.getPickup());
					shiftModel.setDropEligible(shiftDetailsBean.getDrop());
					shiftModel.setTrack(track);
					shiftModel.setProject(project);
					shiftModel.setShift(shift);
					shiftDetailsService.saveShiftDetails(shiftModel);
				}
			}
		} else {
			projectDetailsService.deleteProjectById(Integer.parseInt(projectId));
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Saved successfully!");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
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

	@RequestMapping(value = "/createAccountProject", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tms/createAccountProject");
		return modelAndView;
	}

	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> uploadExcel(@RequestParam("file") final MultipartFile multiPart)
			throws IOException {

		XSSFWorkbook workbook;
		String arrayToJson = null;
		Boolean isContinue = false;
		List<Project> eList = new ArrayList<Project>();
		List<ProjectDetailsBean> projectBeanList = new ArrayList<ProjectDetailsBean>();
		String accountName = null;
		String projectName = null;
		try {
			workbook = new XSSFWorkbook(multiPart.getInputStream());
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Project projectObj = new Project();
				Account accountObj = new Account();
				Row currentRow = iterator.next();
				if (currentRow.getRowNum() == 0) {
					System.out.println("sssssss " + currentRow.getPhysicalNumberOfCells());
					for (int col = 0; col < currentRow.getLastCellNum(); col++) {
						Cell cell = currentRow.getCell(col);
						System.out.println("cell value " + cell.getStringCellValue());
						if (cell.getStringCellValue().equals("")) {
							break;
						} else {
							String columnName = cell.getStringCellValue().trim();
							if (columnName.equalsIgnoreCase("Account") || columnName.equalsIgnoreCase("Project"))
								isContinue = true;
						}

					}
					continue;
				}
				if (isContinue) {
					if ((currentRow.getCell(0) != null && currentRow.getCell(1) != null)
							&& (!currentRow.getCell(0).toString().contains(" ")
									&& !currentRow.getCell(1).toString().contains(" "))) {
						accountName = currentRow.getCell(0).toString();
						projectName = currentRow.getCell(1).toString();
					}

					if (accountName != null && projectName != null) {
						List<Account> accountList = projectDetailsService.findAllAccounts();
						List<Project> projectList = projectDetailsService.findAllProjects();

						Boolean isAccountDuplicate = false;
						Boolean isProjectDuplicate = false;
						int accountId = 0;
						for (Account account : accountList) {
							if (account.getAccountName().equalsIgnoreCase(accountName)) {
								for (Project project : projectList) {
									if (project.getProjectName().equalsIgnoreCase(projectName)) {
										isProjectDuplicate = true;
										break;
									}
								}
								isAccountDuplicate = true;
								accountId = account.getId();
								break;
							}
						}
						if (!isAccountDuplicate) {
							accountObj.setAccountName(currentRow.getCell(0).toString());
							accountId = projectDetailsService.saveAccount(accountObj);
						}

						if (!isProjectDuplicate) {
							projectObj.setProjectName(currentRow.getCell(1).toString());
							Account acoount2 = new Account();
							acoount2.setId(accountId);
							projectObj.setAccount(acoount2);
							projectDetailsService.saveProject(projectObj);
						}

					}

				}

			}

			eList = projectDetailsService.findAllProjects();

			for (Project project : eList) {
				ProjectDetailsBean detailsBean = new ProjectDetailsBean();
				detailsBean.setAccountId(project.getAccount().getId());
				detailsBean.setAccountName(project.getAccount().getAccountName());
				detailsBean.setProjectId(project.getId());
				detailsBean.setProjectName(project.getProjectName());
				projectBeanList.add(detailsBean);
			}
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				arrayToJson = objectMapper.writeValueAsString(projectBeanList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(arrayToJson);

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isContinue && (accountName != null && projectName != null))
			return new ResponseEntity<String>(arrayToJson, HttpStatus.OK);
		else
			return new ResponseEntity<String>(arrayToJson, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getShiftListByTrack", method = RequestMethod.POST)
	@ResponseBody
	public String getShiftListByTrack(@RequestBody Integer trackId) {

		final List<ShiftDetailsBean> ShiftDetailsList = shiftDetailsService.findByTrackId(trackId);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(ShiftDetailsList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}

	@RequestMapping(value = "/trackMapping", method = RequestMethod.GET)
	ModelAndView trackMapping(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<Account> adminAcc = null;
		Set<EmpProjMapping> spocAcc = null;
		HttpSession session = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) session.getAttribute("user");
		SecurityContextImpl authenticationToken = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authToken = authenticationToken.getAuthentication();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authToken.getAuthorities();
		for (GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equalsIgnoreCase("ADMIN")) {
				adminAcc = userRoleService.getAllAccount();
				modelAndView.addObject("accountList", adminAcc);
				break;
			} else {
				spocAcc = projectDetailsService.findAccountByEmpId(profileBean.getEmployeeId());
				modelAndView.addObject("accountList", spocAcc);
			}
		}

		modelAndView.setViewName("tms/trackMapping");
		return modelAndView;
	}

	@RequestMapping(value = "/getProject", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectsByAccount(@RequestBody Integer accountId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) session.getAttribute("user");
		SecurityContextImpl authenticationToken = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authToken = authenticationToken.getAuthentication();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authToken.getAuthorities();
		List<ProjectDetailsBean> projectList = new ArrayList<ProjectDetailsBean>();
		for (GrantedAuthority auth : authorities) {
			if (auth.getAuthority().equalsIgnoreCase("ADMIN") || auth.getAuthority().equalsIgnoreCase("FRONT_DESK")) {
				projectList = projectDetailsService.findProjectTracksByAccountId(accountId);
				break;
			} else {
				projectList = projectDetailsService.findProjectsByEmpAcc(accountId, profileBean.getEmployeeId());
			}
		}

		Collections.sort(projectList);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = "";
		try {
			arrayToJson = objectMapper.writeValueAsString(projectList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;
	}

	@RequestMapping(value = "/getEmployeeRoles", method = RequestMethod.POST)
	@ResponseBody
	public String getUserRoleMapping(@RequestBody Integer projectId) {

		final List<UserRoleBean> userRoleList = userRoleService.findEmployeeRolesByProjectId(projectId);
		if (!userRoleList.isEmpty()) {
			ObjectMapper objectMapper = new ObjectMapper();
			String listToJson = TmsConstant.BLANK;
			try {
				listToJson = objectMapper.writeValueAsString(userRoleList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return listToJson;

		} else {
			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("data", "No User Found");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return jsonObject.toString();

		}
	}

	@RequestMapping(value = "/getTrackRoleMapping", method = RequestMethod.POST)
	@ResponseBody
	public String getTrackRoleMapping(@RequestBody Integer projectId) {

		final List<TrackMappingBean> userRoleList = trackserivce.findEmployeeRolesByProjectId(projectId);
		if (!userRoleList.isEmpty()) {
			ObjectMapper objectMapper = new ObjectMapper();
			String listToJson = TmsConstant.BLANK;
			try {
				listToJson = objectMapper.writeValueAsString(userRoleList);
				System.out.println(listToJson);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return listToJson;

		} else {
			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("data", "No User Found");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return jsonObject.toString();

		}
	}

	// need to be in trackController
	@RequestMapping(value = "/updateSpocTrackMapping", method = RequestMethod.POST)
	@ResponseBody

	public String updateSpocTrackMapping(@RequestBody String createJson, HttpServletRequest request) {

		HttpSession httpSession = request.getSession();

		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		String mailFor = "spocTrack";
		List<SpocBean> bean = JsonConverter.jsonToSpocMappingBean(createJson);

		Boolean status = userRoleService.updateSpocTrack(bean);
		userRoleService.sendEmailToUser(bean, profileBean, mailFor);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Saved Successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();

		// return "updated successfully";

	}

	@RequestMapping(value = "/getEmployeeTrackRole", method = RequestMethod.POST)
	@ResponseBody
	public String getUserTrackRoleMapping(@RequestBody Integer projectId) {

		final List<TrackMappingBean> userRoleList = trackserivce.findEmployeeByProjectId(projectId);
		if (!userRoleList.isEmpty()) {
			ObjectMapper objectMapper = new ObjectMapper();
			String listToJson = TmsConstant.BLANK;
			try {
				listToJson = objectMapper.writeValueAsString(userRoleList);
				System.out.println(listToJson);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return listToJson;

		} else {
			JSONObject jsonObject = new JSONObject();

			try {
				jsonObject.put("data", "No User Found");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return jsonObject.toString();

		}
	}

	@RequestMapping(value = "/updateUserTrackMapping", method = RequestMethod.POST)
	@ResponseBody
	public String updateUserTrackMapping(HttpServletRequest request, @RequestBody String createJson) {
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		String mailFor = "userTrack";
		List<SpocBean> bean = JsonConverter.jsonToTrackMappingBean(createJson);
		JSONObject jsonObject = new JSONObject();

		try {
			Boolean status = userRoleService.updateUserTrack(bean);

			userRoleService.sendEmailToUser(bean, profileBean, mailFor);
			jsonObject.put("data", "Saved successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();

		// return "updated successfully";

	}

	@RequestMapping(value = "/updateUserRoleMapping", method = RequestMethod.POST)
	@ResponseBody
	public String updateUserRoleMapping(@RequestBody List<UserRoleBean> userRoleBeanList) {

		List<EmployeeBean> status = userRoleService.updateUserRole(userRoleBeanList);

		for (EmployeeBean bean : status) {
			tmsMailSend.userRoleMapMail(bean);
		}
		JSONObject jsonObject = new JSONObject();

		try {
			if (status.size() > 0)
				jsonObject.put("data", "Saved successfully!!");
			else
				jsonObject.put("data", "Something Went Wrong!!");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}

	@RequestMapping(value = "/writeExcel", method = RequestMethod.GET)
	public void buildExcel(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		String excelType = request.getParameter("excelType");
		String excelFormat = ".xlsx";

		response.setHeader("Content-Disposition", "attachment; filename=" + excelType + excelFormat);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		workbook = new XSSFWorkbook();
		if (excelType.equalsIgnoreCase("AccProjectExcel")) {
			String sheetName = "Account_Project";
			XSSFSheet sheet = workbook.createSheet(sheetName);
			setAccProjHeader(sheet, workbook);
		} else if (excelType.equalsIgnoreCase("EmployeeExcel")) {
			String sheetName = "Employee List";
			XSSFSheet sheet = workbook.createSheet(sheetName);
			setEmpExcelHeader(sheet, workbook);
		}

		workbook.write(response.getOutputStream());
	}

	private void setAccProjHeader(XSSFSheet excelSheet, XSSFWorkbook workbook) throws FileNotFoundException {
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
		cell.setCellValue("Account");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(1, 5000);
		cell = excelHeader.createCell(1);
		cell.setCellValue("Project");
		cell.setCellStyle(style);

	}

	private void setEmpExcelHeader(XSSFSheet excelSheet, XSSFWorkbook workbook) throws FileNotFoundException {
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
		cell.setCellValue("EmployeeCode");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(1, 5000);
		cell = excelHeader.createCell(1);
		cell.setCellValue("EmployeeName");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(2, 5000);
		cell = excelHeader.createCell(2);
		cell.setCellValue("EmpEmailId");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(3, 5000);
		cell = excelHeader.createCell(3);
		cell.setCellValue("DesignationName");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(4, 5000);
		cell = excelHeader.createCell(4);
		cell.setCellValue("MobileNumber");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(5, 6000);
		cell = excelHeader.createCell(5);
		cell.setCellValue("Gender");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(6, 5000);
		cell = excelHeader.createCell(6);
		cell.setCellValue("ProjectName");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(7, 5000);
		cell = excelHeader.createCell(7);
		cell.setCellValue("ResourcePercentage");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(8, 5000);
		cell = excelHeader.createCell(8);
		cell.setCellValue("CustomerName");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(9, 5000);
		cell = excelHeader.createCell(9);
		cell.setCellValue("ManagerEmailID");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(10, 7000);
		cell = excelHeader.createCell(10);
		cell.setCellValue("ProjectManager");
		cell.setCellStyle(style);

		excelSheet.setColumnWidth(11, 5000);
		cell = excelHeader.createCell(11);
		cell.setCellValue("Location");
		cell.setCellStyle(style);

	}

	@RequestMapping(value = "/viewEmployeeDetails", method = RequestMethod.GET)
	public ModelAndView viewEmployeeDetails(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<Employee> empList = employeeService.findAllEmployees();
		modelAndView.addObject("empList", empList);
		modelAndView.setViewName("tms/viewAllEmpDetails");
		return modelAndView;
	}

	@RequestMapping(value = "/getAllShiftAllowance", method = RequestMethod.GET)
	@ResponseBody
	public String getAllShiftAllowance(HttpServletRequest request,
			@RequestParam(value = "fromDate", required = false) String from,
			@RequestParam(value = "toDate", required = false) String to,
			@RequestParam(value = "accountId", required = false) Integer accountId,
			@RequestParam(value = "projectId", required = false) Integer projectId) {
		try {
			Integer fromDate = null;
			Integer toDate = null;
			if (from != null) {
				fromDate = Integer.parseInt(from.substring(0, from.indexOf("-")));
			}
			if (to != null) {
				toDate = Integer.parseInt(to.substring(0, to.indexOf("-")));
			}
			List<AllowanceBean> allwanBean = shiftAllowanceService.getAllowance(fromDate, toDate, accountId, projectId);
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
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/finance", method = RequestMethod.GET)
	public ModelAndView holiday() {
		ModelAndView modelAndView = new ModelAndView();
		List<Account> projectAccount = userRoleService.getAllAccount();
		modelAndView.addObject("accountList", projectAccount);
		modelAndView.setViewName("tms/listOfHoliday");
		return modelAndView;
	}

	@RequestMapping(value = "/CalculateShiftDetails", method = RequestMethod.GET)
	@ResponseBody
	// need to atted approval by project
	public String approveShiftDetails(@RequestParam("monthId") int monthId, @RequestParam("year") int year,
			HttpServletRequest request, @RequestParam("trackId") int trackId, @RequestParam("proj_id") int projectId,
			@RequestParam(value="fromDate",required=false) String from,
			@RequestParam(value="toDate" , required=false) String to) {
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			
			 List<ShiftAllowanceDetails> shiftAllowanceDetailList =	shiftAllowanceService.calculateShiftAllowance(monthId, year, trackId, profileBean.getEmployeeId(),
					projectId);
			 
			  Integer fromDate=null;
	    	  Integer toDate=null;
	    	  if(from!=null){
               fromDate = Integer.parseInt(from.substring(0,from.indexOf("-")));	
	      }
	    	  if(to!=null){
	    	   toDate = Integer.parseInt(to.substring(0,to.indexOf("-")));
	    	  }
	    	  List<AllowanceBean> allwanBean =   shiftAllowanceService.calcAllowance(shiftAllowanceDetailList, fromDate,toDate);
				try {
					arrayToJson = objectMapper.writeValueAsString(allwanBean);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		
		} catch (Exception e) {
			e.printStackTrace();
			arrayToJson = "somthing went wrong";
		}
		return arrayToJson;
	}
}
