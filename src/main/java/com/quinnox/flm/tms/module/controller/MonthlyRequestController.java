package com.quinnox.flm.tms.module.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.module.beans.CabRequestBean;
import com.quinnox.flm.tms.module.model.EmpCabRequest;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.util.ReadExcelSheet;

/**
 * @author AmareshP
 *
 */
@Controller
@RequestMapping("tms/monthlyRequest")
public class MonthlyRequestController {

	@Autowired
	private CabRequestService cabRequestService;

	@RequestMapping(value = "/monthly", method = RequestMethod.GET)
	String home(ModelMap modal) {
		modal.put("title", "Monthly Request");
		return "tms/monthlyRequest";
	}

	// TODO Attach as parameter => @Valid EmpCabRequest empCabRequest, BindingResult
	// bindingResult
	// TODO Remove this method after development complete
	@RequestMapping(value = "/savemonthlydata", method = RequestMethod.POST)
	public ModelAndView saveMonthlyData(@RequestBody List<CabRequestBean> requestBeans) {

		Date dateObj = new Date();
		ModelAndView modelAndView = new ModelAndView();
		Set<EmpCabRequestDetails> empCabRequestDetails = new HashSet<EmpCabRequestDetails>();
		
		for (CabRequestBean objCabRequestBean : requestBeans) {
			EmpCabRequest empCabRequest = new EmpCabRequest();
			Employee empObj = new Employee();
		//	empObj.setEmpcode(Integer.toString(objCabRequestBean.getEmployeeId()));
			empObj.setActive(true);
		  //empObj.setLastName("lastname");
			empObj.setEmail("email@gmail.com"); // should be removed
			//empObj.setPassword("123456"); // should be removed
			empObj.setName(objCabRequestBean.getEmployeeName());
			empObj.setRoles(null);
			empCabRequest.setUser(empObj);
		//	empCabRequest.setReason(objCabRequestBean.getReason());
		//	empCabRequest.setRemark(objCabRequestBean.getRemark());
			//empCabRequest.setReqDate(dateObj);
			for (String day : objCabRequestBean.getDays()) {
				EmpCabRequestDetails objEmpCabRequestDetails = new EmpCabRequestDetails();
				//objEmpCabRequestDetails.setDropLocation(day);
				objEmpCabRequestDetails.setEmpCabRequest(empCabRequest);
				empCabRequestDetails.add(objEmpCabRequestDetails);
			}
			empCabRequest.setEmpCabRequestDetails(empCabRequestDetails);
			cabRequestService.saveEmpCabRequest(empCabRequest);

		}
		System.out.println("saved data successfully");
		modelAndView.addObject("successMessage", "Monthly data saved successfully");
		modelAndView.addObject("empCabRequest", new EmpCabRequest());
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	/*@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> uploadExcel(@RequestParam("file") final MultipartFile multiPart)
			throws IOException {

		//ReadExcelSheet.readExcel(multiPart.getInputStream());
		// final boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		// Iterator<String> itrator = request.getFileNames();
		// MultiValueMap<String, MultipartFile> multiValueMap=
		// request.getMultiFileMap();

			String jsonList = ReadExcelSheet.readExcel(multiPart.getInputStream());
		return new ResponseEntity<String>(jsonList, HttpStatus.OK);
	}*/

}
