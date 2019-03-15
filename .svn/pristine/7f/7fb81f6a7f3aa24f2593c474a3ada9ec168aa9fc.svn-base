package com.quinnox.flm.tms.module.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quinnox.flm.tms.module.beans.ShiftDetailsBean;
import com.quinnox.flm.tms.module.model.Shift;
import com.quinnox.flm.tms.module.service.ShiftDetailsService;
import com.quinnox.flm.tms.module.util.JsonConverter;

@Controller
@RequestMapping("tms/shift")
public class ShiftController {
	@Autowired
	private ShiftDetailsService shiftDetailsService;
	
	@RequestMapping(value = "/shift_details", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView home(ModelMap modal) {
		ModelAndView modelAndView = new ModelAndView();
		Shift shift= new Shift();
		modelAndView.addObject("shift", shift);
		return modelAndView;
	}
	
	@RequestMapping(value = "/save_shift_details", method = RequestMethod.POST)
	@ResponseBody
	public String saveShiftDetails(@RequestBody String shiftDetailsJSON, HttpServletRequest request) {
	
		ShiftDetailsBean shiftDetailsBean= JsonConverter.jsonToShiftDetails(shiftDetailsJSON);
		Shift shift = new Shift();
		shift.setShiftName(shiftDetailsBean.getShiftName());
		shift.setShiftInitials(shiftDetailsBean.getShiftInitials());
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Saved successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/update_shift_details", method = RequestMethod.POST)
	@ResponseBody
	public String updateShiftDetails(@RequestBody String shiftDetailsJSON, HttpServletRequest request) {
	
		ShiftDetailsBean shiftDetailsBean= JsonConverter.jsonToShiftDetails(shiftDetailsJSON);
		Shift shift = new Shift();
		shift.setShiftName(shiftDetailsBean.getShiftName());
		shift.setShiftInitials(shiftDetailsBean.getShiftInitials());
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Saved successfully.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/view_shift_details", method = RequestMethod.GET)
	@ResponseBody
	private String viewShiftDetails(int shiftID) {
		List<ShiftDetailsBean> shiftBeanList = new ArrayList<ShiftDetailsBean>();
		List<Shift> shiftList=shiftDetailsService.findAllShift() ;
		for(Shift shiftObj:shiftList) {
			ShiftDetailsBean shiftBean= new ShiftDetailsBean();
			shiftBean.setShiftId(shiftObj.getShiftId());
			shiftBean.setShiftName(shiftObj.getShiftName());
			shiftBean.setShiftInitials(shiftObj.getShiftInitials());
			shiftBeanList.add(shiftBean);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = null;
		try {
			arrayToJson = objectMapper.writeValueAsString(shiftBeanList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(arrayToJson);
		System.out.println("executed successfully");
		return arrayToJson;
	}
	
	@RequestMapping(value = "/deleteShiftDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteShiftDetails(@RequestBody String shiftDetailsId, HttpServletRequest request) {
		shiftDetailsService.deleteShiftDetailsById(Integer.parseInt(shiftDetailsId));
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("data", "Deleted successfully.");
		} catch (JSONException e) {
			jsonObject.put("data", "Error Occured please try later");
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

}
