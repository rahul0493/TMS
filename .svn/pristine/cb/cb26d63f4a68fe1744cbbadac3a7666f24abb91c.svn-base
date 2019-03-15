	package com.quinnox.flm.tms.global.mail;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.quinnox.flm.tms.constant.TmsConstant;
import com.quinnox.flm.tms.global.beans.EmployeeAddressBean;
import com.quinnox.flm.tms.global.beans.EmployeeBean;
import com.quinnox.flm.tms.global.model.Employee;
import com.quinnox.flm.tms.global.service.EmployeeService;
import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
import com.quinnox.flm.tms.module.beans.CabRequestRemarkBean;
import com.quinnox.flm.tms.module.beans.UserProfileBean;
import com.quinnox.flm.tms.module.model.EmpCabRequestDetails;
import com.quinnox.flm.tms.module.model.EmployeeAddress;
import com.quinnox.flm.tms.module.service.CabRequestService;
import com.quinnox.flm.tms.module.util.EncodeDecodeUtil;

/**
 * @author AmareshP
 *
 */

@Component("tmsMailSender")
public class TmsMailSender {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private CabRequestService requestService;

	@Autowired
	private EmployeeService employeeService;

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Async 
	public void sendMail(UserProfileBean profileBean, Integer cabReqId, String requestFor) {

		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			if (requestFor.equals("employee")) {
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(profileBean.getManagerEmail());
			} else {
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(profileBean.getManagerEmail());
			}

			mail.setSubject("Cab Approval/Rejection Request");

			List<AdhocCabRequestBean> cabRequest = requestService.findByCabRequestId(cabReqId);
			String requestType = null;
			String requestStatus = null;
			String requestTime = null;
			String fromDate = null;
			String toDate = null;

			for (AdhocCabRequestBean bean : cabRequest) {
				fromDate = format.format(bean.getFrom());
				toDate = format.format(bean.getTo());
				requestType = bean.getRequestType();
				requestStatus = bean.getRequestStatus();
				
				if (requestType.contains("both")) {
					requestTime = bean.getPickTime() + " & " + bean.getDropTime();
					requestType = "Round Trip";
				} else if (requestType.contains("pickup")) {
					requestTime = bean.getPickTime();
					requestType = "PickUp";
				} else {
					requestTime = bean.getDropTime();
					requestType = "Drop";
				}
				break;
			}

			if (requestFor.equals("employee")) {
				// request raised for Others
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(profileBean.getManagerEmail());
			} else {
				// request raised for myself
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(profileBean.getManagerEmail());
			}
			mail.setSubject("Action on Cab Request");

			Map model = new HashMap();
			model.put("reqParam", EncodeDecodeUtil.encodeString("cabRequestId=" +cabReqId));
			model.put("empId", profileBean.getEmpCode());
			model.put("fromDate", fromDate);
			model.put("toDate", toDate);
			model.put("empName", profileBean.getEmployeeName());
			model.put("requestType", requestType);
			model.put("approver", profileBean.getManagerEmail());
			model.put("requestStatus", requestStatus);
			model.put("requestTime", requestTime);
			model.put("projectName", profileBean.getProjectName());

			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "managerRemarkForAdhoc.vm", "UTF-8", model);

			mail.setText(text, true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Sending...");
		mailSender.send(message);
		logger.info("Done!");

	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Async  
	public void empAdhocAcknowledgeMail(UserProfileBean profileBean, Integer cabReqId, String requestFor) {

		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);

			List<AdhocCabRequestBean> cabRequest = requestService.findByCabRequestId(cabReqId);

			String requestType = null;
			String requestStatus = null;
			String requestTime = null;
			String fromDate = null;
			String toDate = null;

			for (AdhocCabRequestBean bean : cabRequest) {
				fromDate = format.format(bean.getFrom());
				toDate = format.format(bean.getTo());
				requestType = bean.getRequestType();
				requestStatus = bean.getRequestStatus();
				if (requestType.contains("both")) {
					requestTime = bean.getPickTime() + " & " + bean.getDropTime();
					requestType = "Round Trip";
				} else if (requestType.contains("pickup")) {
					requestTime = bean.getPickTime();
					requestType = "PickUp";
				} else {
					requestTime = bean.getDropTime();
					requestType = "Drop";
				}

				break;
			}

			if (requestFor.equals("employee")) {
				// request raised for Others
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(profileBean.getReqEmpEmail());
			} else {
				// request raised for myself
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(profileBean.getEmail());
			}
			mail.setSubject("Cab Request Raised");

			Map model = new HashMap();
			model.put("docId", cabReqId);
			model.put("empId", profileBean.getEmpCode());
			model.put("fromDate", fromDate);
			model.put("toDate", toDate);
			model.put("empName", profileBean.getEmployeeName());
			model.put("requestType", requestType);
			model.put("approver", profileBean.getManagerEmail());
			model.put("requestStatus", requestStatus);
			model.put("requestTime", requestTime);
			model.put("projectName", profileBean.getProjectName());

			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "empAdhocAcknowledgeMail.vm",
					"UTF-8", model);
			mail.setText(text, true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Sending...");
		mailSender.send(message);
		logger.info("Done!");
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Async 
	public void managerRemarkMail(EmployeeBean employee, Integer cabReqId, String requestFor,
			CabRequestRemarkBean cabRemarkBean) {

		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			List<AdhocCabRequestBean> cabRequest = requestService.findByCabRequestId(cabReqId);
			Date from = null;
			Date to = null;
			
			String requestType = null;
			String requestStatus = null;
			String requestTime = null;
			String fromDate = null;
			String toDate = null;
			String comment = null;
			for (AdhocCabRequestBean bean : cabRequest) {
				fromDate = format.format(bean.getFrom());
				toDate = format.format(bean.getTo());
				requestType = bean.getRequestType();
				requestStatus = bean.getRequestStatus();
				if(bean.getManagerRemark()!=null || bean.getManagerRemark().equals("")) {
					comment = bean.getManagerRemark();
				}
				else {
					comment = TmsConstant.COMMENT_NOT_ADDED_BY_MGR;
				}
			
				if (requestType.contains("both")) {
					requestTime = bean.getPickTime() + " & " + bean.getDropTime();
					requestType = "Round Trip";
				} else if (requestType.contains("pickup")) {
					requestTime = bean.getPickTime();
					requestType = "PickUp";
				} else {
					requestTime = bean.getDropTime();
					requestType = "Drop";
				}
				break;
			}
			if (requestFor.equals("employee")) {
				// request raised for Others
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(employee.getEmail());
				// mail.setCc(employee.getEmail());
			} else {
				// request raised for myself
				mail.setFrom("qtvt@quinnox.com");
				mail.setTo(employee.getEmail());
				// mail.setCc(employee.getEmail());
			}
			mail.setSubject("Cab Request Remark");

			// If Approved by manager then put CC to FLM 
			 if(cabRemarkBean.getAction().equals("Approve")){
				 // To Do : add FLM MAIL ID BELOW
				 mail.setCc(employee.getEmail());
         	}
			 
			Map model = new HashMap();
			model.put("docId", cabReqId);
			model.put("empId", employee.getEmpcode());
			model.put("fromDate", fromDate);
			model.put("toDate", toDate);
			model.put("empName", employee.getName());
			model.put("requestType", requestType);
			model.put("approver", employee.getManagerEmail());
			model.put("requestStatus", requestStatus);
			model.put("requestTime", requestTime);
			model.put("projectName", employee.getProjectName());
			model.put("comment", comment);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "empAcknowlegdeFromManagerRemark.vm", "UTF-8",
					model);
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
	}
	
	@Async 
	public void sendMailToFlmforAddressRemark(List<EmployeeAddress> addList,EmployeeBean employeeDetails) {

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			mail.setFrom("qtvt@quinnox.com");
			
			// TO DO : CHANGE EMAIL ID TO MANAGER EMAIL ID DURING PRODUCION PHASE ONLY !!!!!!!!!!!!!!!!!!!
			mail.setTo(employeeDetails.getEmail());
			
			mail.setSubject("Address Approval/Rejection Request");
			Map model = new HashMap();
			Integer oldId = null;
			Integer newId = null;
			for(int i = 0 ; i < addList.size() ; i++)
			{
//				if(addList.get(i).getStatus() && addList.get(i).getAlias() != null)
//				{
//					model.put("OldAddress", addList.get(i));
//					oldId = addList.get(i).getId();
//				}
				//else
			//	{
				if(addList.get(i).getAlias() != null)
				{
					model.put("NewAddress", addList.get(i));
					newId = addList.get(i).getId();
					model.put("Status", "Pending");
				}
					
				//}
			}
			model.put("EmployeeDetails", employeeDetails);
			//model.put("reqParam", EncodeDecodeUtil.encodeString(oldId + "," + newId));
			model.put("reqParam", EncodeDecodeUtil.encodeString(employeeDetails.getId() + "," + newId)); // ","+oldId+
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "MgrRemarkForUpdateEmpAddress.vm",
					"UTF-8", model);
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Async 
	public void achknowlegdeToEmpForAddressChange(List<EmployeeAddress> addList, EmployeeBean employeeDetails , String status) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(employeeDetails.getEmail());
			
			Map model = new HashMap();
			for(int i = 0 ; i < addList.size() ; i++)
			{
				if(addList.get(i).getStatus())
				model.put("OldAddress", addList.get(i));
				else
				{
					model.put("NewAddress", addList.get(i));
					status = addList.get(i).getStatus() == true ? "Approved" : "Pending";
					//model.put("Status", addList.get(i).getStatus() == true ? "Approved" : "Rejected");
					
				}
			}
			
			//model.put("Status", status);
			
			mail.setSubject("Address Request Status : " + status );
			model.put("EmployeeDetails", employeeDetails);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "addressAknowledgement.vm",
					"UTF-8", model);
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Async 
	public void flmRemarkMail(UserProfileBean profileBean, EmployeeAddressBean address) {

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			EmployeeAddressBean addressBean = employeeService.findAddressByAddressId(address.getEmpAddressBeanId());
			addressBean.setStatusValue("Rejected");
			Employee employee = employeeService.findById(addressBean.getEmployeeId());
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(employee.getEmail());
			mail.setSubject("Address Remark by FLM");
			Map model = new HashMap();
			model.put("addressBean", addressBean);
			model.put("name", employee.getName());
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "flmRemarkMail.vm", "UTF-8",
					model);
			mail.setText(text,true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Async 
	public void fLMRejectedCabRequest(EmployeeBean employee, Integer cabReqId
			) {

		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			
			EmpCabRequestDetails cabRequest = requestService.findByRequestId(cabReqId);
			// List<AdhocCabRequestBean> cabRequest = requestService.findByCabRequestId(cabReqId);
			String requestType = null;
			String requestStatus = null;
			String ScheduledDate = null;
		
			ScheduledDate = format.format(cabRequest.getScheduleDate());
			requestType = cabRequest.getEmpCabRequest().getRequestType();
			requestStatus = cabRequest.getAction();
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(employee.getEmail());
			mail.setSubject("FLM Remark");
			Map model = new HashMap();
			model.put("docId", cabReqId);
			model.put("empId", employee.getEmpcode());
			model.put("fromDate", ScheduledDate);
			model.put("toDate", ScheduledDate);
			model.put("empName", employee.getName());
			model.put("requestType", requestType);
			model.put("approver", employee.getManagerEmail());
			model.put("requestStatus", requestStatus);
			model.put("requestTime", cabRequest.getRequestime());
			model.put("projectName", employee.getProjectName());

			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "empAdhocAcknowledgeMail.vm", "UTF-8",
					model);
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
	}


	

	@Async 
	public void testEMail(Integer addressId) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper mail;
		try {
			mail = new MimeMessageHelper(message, true);
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo("amareshp@quinnox.com");
			mail.setSubject("Address Approval/Rejection Request");
			Map model = new HashMap();
			model.put("addressId", addressId);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "addressAknowledgement.vm",
					"UTF-8", model);
			mail.setText(text, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		logger.info("Sending...");
		mailSender.send(message);
		logger.info("Done!");
	}
	
	@Async 
	public void achknowlegdementForDefaultAddress(EmployeeAddress addr, EmployeeBean employeeDetails) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(employeeDetails.getEmail());
			mail.setTo(employeeDetails.getManagerEmail());
			
			Map model = new HashMap();
			model.put("NewAddress", addr);
			mail.setSubject("Employee Default Work Location");
			model.put("EmployeeDetails", employeeDetails);
			model.put("Status", addr.getStatus() == true ? "Approved" : "Rejected");
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "defaultWorkLocation.vm",
					"UTF-8", model);
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Async 
	public void achknowlegdementForCabRequired(EmployeeBean employeeDetails) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(employeeDetails.getEmail());
			mail.setCc(employeeDetails.getManagerEmail());
			
			Map model = new HashMap();
			mail.setSubject("Cab Requirement Confirmation");
			model.put("EmployeeDetails", employeeDetails);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "cabRequiredAchkowledgement.vm",
					"UTF-8", model);
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	
//	@Async 
//	public void userRoleMapMail(EmployeeBean bean) {
//
//		MimeMessage message = mailSender.createMimeMessage();
//		try {
//			MimeMessageHelper mail = new MimeMessageHelper(message, true);	
//			mail.setFrom("qtvt@quinnox.com");
//			mail.setTo(bean.getEmail());
//			mail.setSubject("Active Roles");
//			Map model = new HashMap();
//			model.put("employeeDetails", bean);
//			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "UserRoleMapAchkwldge.vm", "UTF-8",
//					model);
//			mail.setText(text);
//			logger.info("Sending...");
//			mailSender.send(message);
//			logger.info("Done!");
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	@Async 
	public void userRoleMapMail(EmployeeBean bean) {

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(bean.getEmail());
			mail.setSubject("Active Roles");
			Map model = new HashMap();
			model.put("employeeDetails", bean);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "UserRoleMapAchkwldge.vm", "UTF-8",
					model);
			mail.setText(text,true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Async 
	public void empMonthlyShiftPlanMail(UserProfileBean profileBean, String trackName, int monthId, int year, String accountName, String projectName, int trackId, int projectId,int spocId) {
	
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
	        @SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
	             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	             message.setTo(profileBean.getReqEmpEmail());
	             message.setFrom(new InternetAddress("qtvt@quinnox.com") );
	             System.out.println("email sent to"+profileBean.getReqEmpEmail()+"email sent from"+profileBean.getEmail() );
	             message.setSubject("Shift Plan for"+"  "+new DateFormatSymbols().getMonths()[monthId-1]+" "+year); 
	             message.setSentDate(new Date());
	             Map model = new HashMap();
	             model.put("trackName", trackName);
	             model.put("monthId", monthId);
	             model.put("year", year);
	             model.put("accountName", accountName);
	             model.put("projectName", projectName);
	             model.put("trackId", trackId);
	             model.put("type", "email");
	             model.put("projectId", projectId);
	             model.put("spocId", spocId);
	             String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "empShiftPlanMail.vm", "UTF-8", model);
	             message.setText(text, true);
	          }
	       };
	       mailSender.send(preparator);
}
	
	@Async
	public void sendMailToUser(Employee employee, String spocMail, String trackName, String mailFor) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			String text=null;
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			System.out.println("trackName"+trackName);
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(employee.getEmail());
			Map model = new HashMap();
			if(mailFor!=null && mailFor.equalsIgnoreCase("spocTrack")){
			mail.setSubject("SPOC Assignment Confirmation");
			}
			if(mailFor!=null && mailFor.equalsIgnoreCase("userTrack")){
				mail.setSubject("Employee Track Assignment Confirmation");
				}
			model.put("employeeName",employee.getName());
			model.put("trackName", trackName);
	model.put("employeeId",employee.getEmpcode());
			if(mailFor!=null && mailFor.equalsIgnoreCase("spocTrack")){
				text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "UserAssignedasSpocMail.vm",
						"UTF-8", model);
				}
				if(mailFor!=null && mailFor.equalsIgnoreCase("userTrack")){
					text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "userAssigendToTrack.vm",
							"UTF-8", model);
					}
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	@Async 
	public void mgrRemarkForAddrRejection(List<EmployeeAddress> addList, EmployeeBean employeeDetails , String status) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mail = new MimeMessageHelper(message, true);
			mail.setFrom("qtvt@quinnox.com");
			mail.setTo(employeeDetails.getEmail());
			
			Map model = new HashMap();
			for(int i = 0 ; i < addList.size() ; i++)
			{
				if(addList.get(i).getStatus())
				model.put("OldAddress", addList.get(i));
				else
				{
					model.put("NewAddress", addList.get(i));
					status = addList.get(i).getStatus() == true ? "Approved" : "Rejected";
					//model.put("Status", addList.get(i).getStatus() == true ? "Approved" : "Rejected");
					
				}
			}
			
			//model.put("Status", status);
			
			mail.setSubject("Address Request Status : " + status );
			model.put("EmployeeDetails", employeeDetails);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "addressRejectionAckMail.vm",
					"UTF-8", model);
			mail.setText(text, true);
			logger.info("Sending...");
			mailSender.send(message);
			logger.info("Done!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
