//package com.quinnox.flm.tms.module.controller;
//
//import java.io.FileOutputStream;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.quinnox.flm.tms.module.beans.AdhocCabRequestBean;
//import com.quinnox.flm.tms.module.beans.UserProfileBean;
//import com.quinnox.flm.tms.module.service.CabRequestService;
//
//@Controller
//@RequestMapping("tms/pdf")
//public class PDFController {
//	@Autowired
//	private CabRequestService cabRequestService;
//	
//	@RequestMapping(value = "/writePDF", method = RequestMethod.GET)
//	public void writePDF(HttpServletRequest request,HttpServletResponse response) {
//		try {
//		
//            Document document = new Document();
//           
//            PdfWriter.getInstance(document, new FileOutputStream("E:\\TestPDF.pdf"));
//            document.open();
//            final UserProfileBean profileBean =  (UserProfileBean)request.getSession().getAttribute("user");
//   		// 	java.util.List<AdhocCabRequestBean> adhocCabRequestList = cabRequestService.findAllOpenRequestByEmpId(profileBean.getEmployeeId());
//   		  	
//   		    PdfPTable table = new PdfPTable(13);
//   		  
//   		    PdfPCell c1 = new PdfPCell(new Phrase("ID"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1); 
//
//	        c1 = new PdfPCell(new Phrase("Request Type"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//
//	        c1 = new PdfPCell(new Phrase("Schedule Date"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("Reason"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("Manager Remark"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("Request Status"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("MobileNumber"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("Adhoc or Monthly"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("Address"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("Landmark"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("Location"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("State"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	        c1 = new PdfPCell(new Phrase("City"));
//	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//	        table.addCell(c1);
//	        
//	   //     table.setHeaderRows(1);
//   		 	
////   		    for(AdhocCabRequestBean obj : adhocCabRequestList)
////   		 	{
////   		    	table.addCell(Integer.toString(obj.getId()));
////   	   	        table.addCell(obj.getRequestType());
////   	   	  //      table.addCell(obj.getScheduleDate());
////   	   	        table.addCell(obj.getReason());
////   	   	        table.addCell(obj.getManagerRemark());
////   	   	        table.addCell(obj.getRequestStatus());
////   	   	        table.addCell(Long.toString(obj.getMobileNumber()));
////	   	        table.addCell(obj.getAdhocMonthly());
//////	   	        table.addCell(obj.getAddress());
//////	   	        table.addCell(obj.getLandmark());
//////	   	        table.addCell(obj.getLocation());
//////	   	        table.addCell(obj.getState());
//////	   	        table.addCell(obj.getCity());
////   		 	}
////   		 	
////   	      
////   	        
////   	        
////   	       
////
////   	        
////   	        try {
////   	        	document.add(table);
////   	        	
////   			} catch (DocumentException e) {
////   				// TODO Auto-generated catch block
////   				e.printStackTrace();
////   			}
////            document.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    
////	}
//	
//
//	
////}
