package com.quinnox.flm.tms.global.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.mail.TmsMailSender;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.model.UserRole;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.global.service.UserRoleService;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.ProjectDetailsBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.Account;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmpProjMapping;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.model.LocationMaster;
import com.quinnox.flm.tms.module.model.Project;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.service.HolidayService;
import com.quinnox.flm.tms.module.service.ProjectDetailsService;
import com.quinnox.flm.tms.module.util.JsonConverter;

/**
 * @author AmareshP
 *
 */

@Controller
@RequestMapping("tms/employee")
public class EmployeeController {

	public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	@Qualifier("tmsMailSender")
	public TmsMailSender mailSender;

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private CabRequestService cabRequestService;

	@Autowired
	private ProjectDetailsService projectDetailsService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private TmsMailSender tmsMailSend;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	String userPage(ModelMap modal) {
		return "tms/userPage";
	}

	@RequestMapping(value = "/employeeAddress", method = RequestMethod.GET)
	ModelAndView EmployeeAddress(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tms/addressUpdate");
		logger.info("Showing Address update landing page: {}", "tms/addressUpdate");
		return modelAndView;
	}

	@RequestMapping(value = "/getEmployeeAddress", method = RequestMethod.GET)
	@ResponseBody
	String EmployeeAddressList(HttpServletRequest request) {
		String arrayToJson = null;
		JSONObject jsonObject = new JSONObject();
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		if (profileBean == null) {
			jsonObject.put("data", "Employee Address is Empty");
			return jsonObject.toString();
		}
		List<EmployeeAddressBean> employeeAddressesLists = employeeService
				.findAddressByEmpIdAndGeneralAddress(profileBean.getEmployeeId());
		
		for(EmployeeAddressBean bean : employeeAddressesLists)
		{
			if(bean.getEmployeeId() != null)
			{
				if(bean.getComment() != null && bean.getComment() != "")
				{
					if(bean.getStatus() == null && bean.getComment().contains(TmsConstant.PENDING))
					{
						bean.setStatusValue(TmsConstant.PENDING);
					}
					else if(bean.getStatus() && bean.getComment().contains(TmsConstant.APPROVED))
					{
						bean.setStatusValue(TmsConstant.APPROVED);
					}
					else
					{
						bean.setStatusValue(TmsConstant.REJECTED);
					}
				}
				
			}
			
		}
		if (employeeAddressesLists.size() == 0) {
			
			jsonObject.put("data", "Employee Address is Empty");
			return jsonObject.toString();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			logger.info("Employee address list to Json Array conversion");
			arrayToJson = objectMapper.writeValueAsString(employeeAddressesLists);
			logger.info("Json String value: {}", arrayToJson);
		} catch (JsonProcessingException ex) {
			logger.error("Error:{}", ex.getMessage());
		}
		return arrayToJson;
	}

	@RequestMapping(value = "/cabRequest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createCabRequest(@RequestBody EmpCabRequestDetails cabRequest) {
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/employeeRegistraion", method = RequestMethod.GET)
	public ModelAndView employeeRegistraion(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<Account> projectAccount = userRoleService.getAllAccount();
		List<LocationMaster> location = holidayService.getAllLocation();
		modelAndView.addObject("location", location);
		modelAndView.addObject("accountList", projectAccount);
		modelAndView.setViewName("tms/addEmployee");
		return modelAndView;
	}

	
	// Changed here
	@RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
	public ModelAndView addEmployee(ModelMap modal, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		List<ProjectDetailsBean> projectList = null;
		if (profileBean != null) {

			EmployeeBean empBean = employeeService.addEmployee(profileBean.getEmpCode());
			modelAndView.addObject("employee", empBean);

			if (empBean.getAccountId() != null) {
				projectList = projectDetailsService.findProjectsByAccountId(profileBean.getAccountId());
			}
		}
		modelAndView.addObject("projectList", projectList);
		final List<Account> projectAccount = userRoleService.getAllAccount();
		List<LocationMaster> location = holidayService.getAllLocation();
		modelAndView.addObject("location", location);
		modelAndView.addObject("accountList", projectAccount);
		modelAndView.setViewName("tms/addEmployee");
		return modelAndView;
	}

	@RequestMapping(value = "/saveEmployeeDetails", method = RequestMethod.POST)
	public ModelAndView saveEmployeeDetails(@Valid EmployeeBean employeeBean, BindingResult bindingResult,
			final HttpServletRequest request) {
		Integer userId = null;
		if (employeeBean.getId() != null)
			userId = employeeBean.getId();
		try {
			employeeService.saveUser(employeeBean);
		} catch (Exception ex) {
			return new ModelAndView("redirect:/tms/employee/employeeRegistraion");
		}
		if (userId == null && request.getSession().getAttribute("user") == null) {
			return new ModelAndView("redirect:/tms/security/updateUserAuthority");
		} else {
			return new ModelAndView("redirect:/tms/home");
		}
	}

	@RequestMapping(value = "/setDefaultAddress", method = RequestMethod.POST)
	@ResponseBody
	public String setDefaultAddress(HttpServletRequest request) { // @RequestBody String employeeDetailsJSON,
																	// HttpServletRequest request

		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		EmployeeBean empBean = new EmployeeBean();
		EmployeeAddress employeeAddress = new EmployeeAddress();
		Employee employee = new Employee();
		EmployeeAddressBean bean = new EmployeeAddressBean();
		Boolean value = Boolean.parseBoolean(request.getParameter("value"));
		Integer addressId = Integer.parseInt(request.getParameter("addressId"));
		
		empBean.setId(profileBean.getEmployeeId());
		empBean.setName(profileBean.getEmployeeName());
		empBean.setManagerEmail(profileBean.getManagerEmail());
		empBean.setEmail(profileBean.getEmail());
		empBean.setEmpcode(profileBean.getEmpCode());

		employeeService.setDefaultAddress(addressId, value);
		if(value) 	{
			bean = employeeService.findAddressByAddressId(addressId);
			employeeAddress.setEmpcode(bean.getEmpCode());
			employee.setId(bean.getEmployeeId());
			employeeAddress.setEmployee(employee);
		}
		else
		{
			bean = employeeService.getOfficeAddress();
			
		}
		employeeAddress.setAlias(bean.getAlias());
		employeeAddress.setDefaultAddress(bean.getDefaultAddress());
		employeeAddress.setLandmark(bean.getLandmark());
		employeeAddress.setLocation(bean.getLocation());
		employeeAddress.setPincode(bean.getPincode());
		employeeAddress.setCity(bean.getCity());
		employeeAddress.setAddress(bean.getAddress());
		employeeAddress.setStatus(false);
		
		mailSender.achknowlegdementForDefaultAddress(employeeAddress, empBean);
		
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("data", "Successfully updated !!");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject.toString();
	}

	// Changed here
	@RequestMapping(value = "/getProjectList", method = RequestMethod.POST)
	@ResponseBody
	public String getProjectsByAccount(@RequestBody Integer accountId, HttpServletRequest request) {
		String arrayToJson = null;
		if (accountId == 0) {
			HttpSession httpSession = request.getSession();
			UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
			if (profileBean == null) {
				return arrayToJson;
			}
			accountId = profileBean.getAccountId();
		
		}
		List<ProjectDetailsBean> projectList = null;
		if (accountId != null) {
			projectList = projectDetailsService.findProjectsByAccountId(accountId);
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			arrayToJson = objectMapper.writeValueAsString(projectList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return arrayToJson;

	}

	@RequestMapping(value = "/createEmployeeList", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap modal, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tms/uploadEmployeeList");
		return modelAndView;
	}

	@RequestMapping(value = "/uploadEmployeeList", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> uploadExcel(@RequestParam("file") final MultipartFile multiPart)
			throws IOException {
		String arrayToJson = null;
		try {
			XSSFWorkbook workbook;
			
			List<Employee> eList = new ArrayList<Employee>();
			List<EmployeeBean> empBeanList = new ArrayList<EmployeeBean>();
			workbook = new XSSFWorkbook(multiPart.getInputStream());
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int rowCount = datatypeSheet.getPhysicalNumberOfRows();
			System.out.println("row count " + rowCount);
			List<EmployeeBean> empList = readExcelData(iterator);
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				arrayToJson = objectMapper.writeValueAsString(empList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			System.out.println(arrayToJson);
			if(empList == null || empList.isEmpty())
				return new ResponseEntity<String>(arrayToJson, HttpStatus.BAD_REQUEST);
			else
				return new ResponseEntity<String>(arrayToJson, HttpStatus.OK);
			
				
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<String>(arrayToJson, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/saveEmployeeList", method = RequestMethod.POST)
	@ResponseBody
	private String saveExcelData(@RequestBody String empDataJson) {

		List<EmployeeBean> beanList = JsonConverter.jsonToEmpData(empDataJson);
		for(EmployeeBean b1 : beanList) {
			for(ProjectDetailsBean p1 : b1.getProjectList())
			System.out.println(" ss" + p1.getProjectName());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		for (EmployeeBean bean : beanList) {
			Employee employee = employeeService.findUserByEmail(bean.getEmail());
			LocationMaster locMaster =  holidayService.getLocationByName(bean.getLocation());
			Employee empObj = new Employee();
			Set<EmpProjMapping> projMappingSet = new HashSet<EmpProjMapping>();
			Project project = null;
			Account account = null;
			EmpProjMapping projMapping = new EmpProjMapping();
			// to find project duplicacy and to add if project doesnt exist
			if(employee!=null) {
			for(EmpProjMapping empProj: employee.getEmpProjMapping()) {
		//	for(ProjectDetailsBean projectBean :  bean.getProjectList()) {
				
				
				boolean found = false;	 
				EmpProjMapping newEmpProj = new EmpProjMapping();
				
				for(ProjectDetailsBean projectBean :  bean.getProjectList()) {
				//for(EmpProjMapping empProj: employee.getEmpProjMapping()) {
				//	found = false;
					//projectBean.getProjectName()
				
					if(projectBean.getProjectName().equalsIgnoreCase
							(empProj.getProject().getProjectName())) {
						
						found = true;
						break;
			
					}
					newEmpProj.setId(empProj.getId());

				}
				if(!found)
				{
					newEmpProj.setProjectStatus(false);
					employeeService.updateProjStatus(newEmpProj);

				}
				
			}
			
			
			}
			
			project = projectDetailsService.findProjectByName(bean.getProjectName());
			if (project != null) {
				projMapping.setProject(project);

			} else {

				project = new Project();
				account = projectDetailsService.findAccountByAccountName(bean.getAccountName());
				if (account != null) {
					project.setAccount(account);
					project.setProjectName(bean.getProjectName());
					projectDetailsService.saveProject(project);
				} else {
					account = new Account();
					account.setAccountName(bean.getAccountName());
					projectDetailsService.saveAccount(account);
					project.setAccount(account);
					project.setProjectName(bean.getProjectName());
					projectDetailsService.saveProject(project);
				}
				projMapping.setProject(project);
			}
			projMapping.setProjectAllocation(bean.getProjectAllocation());

			// for excel data update
			if (employee != null) {
				
				EmpProjMapping mapping = employeeService.findEmpProjById(employee.getId(), project.getId());
				
				
				if (mapping == null) {
					projMapping.setEmployee(employee);
					projMapping.setProjectStatus(true);
					employeeService.saveOrUpdateProjectMapping(projMapping);
				} else {
					mapping.setEmployee(employee);
					mapping.setProjectStatus(true);
				}

				
				empObj.setId(employee.getId());
				empObj.setEmpcode(bean.getEmpcode());
				empObj.setName(bean.getName());
				empObj.setEmail(bean.getEmail());
				empObj.setEmpDesignation(bean.getDesignationName());
				empObj.setPhoneNumber(bean.getPhoneNumber());
				empObj.setGender(bean.getGender());
				empObj.setRoles(employee.getRoles());
				empObj.setTracksEmpMapping(employee.getTracksEmpMapping());
				empObj.setTracks(employee.getTracks());
				projMappingSet.add(mapping);
				empObj.setEmpProjMapping(projMappingSet);
				empObj.setManagerEmail(bean.getManagerEmail());
				empObj.setActive(true);
				if(locMaster != null) {
					empObj.setLocation(locMaster);
				}
				
				
				employeeService.updateUser(empObj);
			} else {
				
				empObj.setEmpcode(bean.getEmpcode());
				empObj.setName(bean.getName());
				empObj.setEmail(bean.getEmail());
				empObj.setEmpDesignation(bean.getDesignationName());
				empObj.setPhoneNumber(bean.getPhoneNumber());
				empObj.setGender(bean.getGender());
				projMapping.setEmployee(empObj);
				projMapping.setProjectStatus(true);
				projMappingSet.add(projMapping);
				empObj.setEmpProjMapping(projMappingSet);
				empObj.setManagerEmail(bean.getManagerEmail());
				if(locMaster != null) {
					empObj.setLocation(locMaster);
				}
				empObj.setActive(true);
				if(bean.getDesignationName().equalsIgnoreCase("Senior Manager")) {
					Set<UserRole> userRoles= new HashSet<UserRole>();
					UserRole mgrRole = userRoleService.findByRole(TmsConstant.SENIORMANAGER);
					userRoles.add(mgrRole);
					UserRole userRole = userRoleService.findByRole(TmsConstant.USER);
					userRoles.add(userRole);
					empObj.setRoles(userRoles);
					
				}else {
					UserRole userRole = userRoleService.findByRole(TmsConstant.USER);
					empObj.setRoles(new HashSet<UserRole>(Arrays.asList(userRole)));
				}
				try{
					employeeService.saveOrUpdate(empObj);
					arrayToJson = objectMapper.writeValueAsString("Data was successfully updated");
				}
				catch(Exception e) {
					try {
						arrayToJson = objectMapper.writeValueAsString("Error");
					} catch (JsonProcessingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		return arrayToJson; 
	}

	private List<EmployeeBean> readExcelData(Iterator<Row> iterator) {
		Boolean isContinue = false;
		List<EmployeeBean> employeeBeans = new ArrayList<EmployeeBean>();
		ArrayList<String> columns = new ArrayList<String>();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			if (currentRow.getRowNum() == 0) {
				for (int col = 0; col < currentRow.getLastCellNum(); col++) {
					System.out.println("last cell num " + currentRow.getLastCellNum());
					Cell cell = currentRow.getCell(col);
					if (cell.getStringCellValue().equals("")) {
						break;
					} else {
						String columnName = cell.getStringCellValue().trim();
						columns.add(columnName);
				}
					
					
			}
				
				for(int i=0;i<columns.size();i++) {
					if (("EmployeeCode").equalsIgnoreCase(columns.get(i)) && ("EmployeeName").equalsIgnoreCase(columns.get(i+1))
							&& ("EmpEmailId").equalsIgnoreCase(columns.get(i+2))
							&& ("DesignationName").equalsIgnoreCase(columns.get(i+3))
							&& ("MobileNumber").equalsIgnoreCase(columns.get(i+4)) 
							&& ("Gender").equalsIgnoreCase(columns.get(i+5))
							&& ("ProjectName").equalsIgnoreCase(columns.get(i+6))
							&& ("ResourcePercentage").equalsIgnoreCase(columns.get(i+7))
							&& ("CustomerName").equalsIgnoreCase(columns.get(i+8))
							&& ("ManagerEmailID").equalsIgnoreCase(columns.get(i+9))
							&& ("ProjectManager").equalsIgnoreCase(columns.get(i+10))
							&& ("Location").equalsIgnoreCase(columns.get(i+11)))
							isContinue = true;
					

				}
				continue;
			}
		
			
			if (isContinue) {
				if((currentRow.getCell(0) != null && currentRow.getCell(1)!= null && currentRow.getCell(2)!= null && currentRow.getCell(3)!= null
						&& currentRow.getCell(4)!= null && currentRow.getCell(5)!= null && currentRow.getCell(6)!= null && currentRow.getCell(7)!= null 
						&& currentRow.getCell(8)!= null && currentRow.getCell(9)!= null && currentRow.getCell(11)!= null)) {
				Long mobile = 0L;
				String empCode = NumberToTextConverter.toText(currentRow.getCell(0).getNumericCellValue()).trim();
				String empName = currentRow.getCell(1).toString().trim();
				String empEmail = currentRow.getCell(2).toString().trim();
				String designation = currentRow.getCell(3).toString().trim();
				if (!currentRow.getCell(4).toString().contains("NULL")) {
					mobile = (long) currentRow.getCell(4).getNumericCellValue();
				}

				String gender = currentRow.getCell(5).toString().trim();
				String projectName = currentRow.getCell(6).toString().trim();
				Integer projectAllocation = (int) currentRow.getCell(7).getNumericCellValue();
				String accountName = currentRow.getCell(8).toString().trim();
				String managerEmail = currentRow.getCell(9).toString().trim();
				String location = currentRow.getCell(11).toString().trim();
				EmployeeBean employeeBean = new EmployeeBean();
				employeeBean.setEmpcode(empCode);
				employeeBean.setName(empName);
				employeeBean.setEmail(empEmail);
				employeeBean.setDesignationName(designation);
				employeeBean.setPhoneNumber(mobile);
				employeeBean.setGender(gender);
				employeeBean.setProjectName(projectName);
				employeeBean.setProjectAllocation(projectAllocation);
				employeeBean.setAccountName(accountName);
				employeeBean.setManagerEmail(managerEmail);
				employeeBean.setLocation(location);
				employeeBeans.add(employeeBean);

				}
				else {
					continue;
				}
			}
			else {
				return null;
			}
		}
		Collections.sort(employeeBeans);
		return employeeBeans;
	}

	@RequestMapping(value = "/getSecondaryApproverMail", method = RequestMethod.GET)
	@ResponseBody
	public String getSecondaryApproverMail(HttpServletRequest request) {

		// Integer userId = 7 ;
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String approverEmail = employeeService.findEmpByEmpId(userId).getManagerEmail();
		String secondaryApprover = employeeService.findUserByEmail(approverEmail).getManagerEmail();
	
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", secondaryApprover);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();

	}
	
	@RequestMapping(value = "/sendMailSecondaryAppr", method = RequestMethod.POST)
	@ResponseBody
	public String sendMailSecondaryAppr(HttpServletRequest request, @RequestBody String employeeDetailsJSON) { 
		List<EmployeeAddress> addrList = new ArrayList<EmployeeAddress>(); 
		
		EmployeeAddressBean bean = JsonConverter.jsonToEmployeeAddressDetails(employeeDetailsJSON);
		EmployeeBean  empBean = employeeService.findEmployeeByEmpId(bean.getEmployeeId());
		Employee emp = new Employee();
		emp.setId(empBean.getId());
		EmployeeAddress  employeeAddress = new EmployeeAddress();
		employeeAddress.setAlias(bean.getAlias());
		employeeAddress.setDefaultAddress(bean.getDefaultAddress());
		employeeAddress.setLandmark(bean.getLandmark());
		employeeAddress.setLocation(bean.getLocation());
		employeeAddress.setPincode(bean.getPincode());
		employeeAddress.setEmpcode(bean.getEmpCode());
		employeeAddress.setCity(bean.getCity());
		employeeAddress.setAddress(bean.getAddress());
		employeeAddress.setEmployee(emp);
		employeeAddress.setId((bean.getEmpAddressBeanId()));
		employeeAddress.setEffectiveDate(bean.getEffectiveDate());
		addrList.add(employeeAddress);
		
		if (bean.getEmpAddressBeanId() != null) {
			mailSender.sendMailToFlmforAddressRemark(addrList,empBean);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Successfully Saved Address !!");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/setCabRequiredForEmp", method = RequestMethod.POST)
	@ResponseBody
	public String setCabRequiredForEmp(HttpServletRequest request) {
		
		EmpCabRequest masterReq =  new EmpCabRequest();
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		Boolean isCabRequired = Boolean.parseBoolean(request.getParameter("isCabRequired"));
		
		HttpSession httpSession = request.getSession();
		UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
		EmployeeBean empBean = new EmployeeBean();
		empBean.setEmail(profileBean.getEmail());
		empBean.setEmpcode(profileBean.getEmpCode());
		empBean.setManagerEmail(profileBean.getManagerEmail());
		if(isCabRequired)
		empBean.setIsCabRequiredMsg("Required");
		else
		{
			empBean.setIsCabRequiredMsg("Not Required");
			// In active all unused requests
			
			
			List<EmpCabRequestDetails> details =  cabRequestService.findAllOpenRequestByEmpId(userId);
			Set<EmpCabRequestDetails> detailsSet = new HashSet<EmpCabRequestDetails>();
		    for(EmpCabRequestDetails childReq : details)
		    {
		    	masterReq = childReq.getEmpCabRequest();
		    	if(masterReq.getAdhocOrMonthly().equalsIgnoreCase("Monthly")) {
		    		//masterReq.setActiveRequest(false);
			    	childReq.setActiveRequest(false);
			    	childReq.setTravelStatus(TmsConstant.NOT_CAB_REQUIRED);
		    	}
		    	
		    	detailsSet.add(childReq);
		    	masterReq.setEmpCabRequestDetails(detailsSet);
		    	cabRequestService.updateCabRequest(masterReq);
		    }
		 cabRequestService.findAllRequestByEmpId(request.getParameter("userId"));
		 
		  
		}
		empBean.setIsCabRequired(isCabRequired);
		empBean.setName(profileBean.getEmployeeName());
		
		// Set Cab Required or Not
		employeeService.setCabRequiredForEmp(userId,isCabRequired);
		
		mailSender.achknowlegdementForCabRequired(empBean);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data","updated successfully !!!!");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();

	}
	
	// During Excel upload only for address details
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/save_employee_address", method = RequestMethod.POST)
		@ResponseBody
		public String saveEmployeeAddress(HttpServletRequest request, @RequestBody String employeeDetailsJSON) { // EmployeeBean
			HttpSession httpSession = request.getSession();
			UserProfileBean profileBean = (UserProfileBean) httpSession.getAttribute("user");
			EmployeeAddressBean addressBean = JsonConverter.jsonToEmployeeAddressDetails(employeeDetailsJSON);
			addressBean.setEmployeeId(profileBean.getEmployeeId());
			addressBean.setCity(addressBean.getCity());
			addressBean.setEmpCode(profileBean.getEmpCode());
			addressBean.setProjectId(profileBean.getProjectId());
			addressBean.setProjectName(profileBean.getProjectName());
			Map<String, Object> map = employeeService.saveOrUpdateEmployeeAddress(addressBean);
			if (addressBean.getEmpAddressBeanId() != null) {
				tmsMailSend.achknowlegdeToEmpForAddressChange((List<EmployeeAddress>) map.get("addList"),
						(EmployeeBean) map.get("empBean"), (String) map.get(TmsConstant.PENDING));
				tmsMailSend.sendMailToFlmforAddressRemark((List<EmployeeAddress>) map.get("addList"),
						(EmployeeBean) map.get("empBean"));
			}
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("data", "Successfully Saved Address !!");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
		@RequestMapping(value = "/getAddressByAddressId", method = RequestMethod.POST)
		@ResponseBody
		private EmployeeAddressBean getAddressByAddressId(@RequestBody int addressId) {
			EmployeeAddressBean employeeAddressBean = employeeService.findAddressByAddressId(addressId);
			return employeeAddressBean;
		}

}
