package com.quinnox.flm.tms.module.util;

import org.apache.commons.codec.binary.Base64;

public class EncodeDecodeUtil {

	
	public static String encodeString(String param){
		
		 byte[] encoded = Base64.encodeBase64(param.getBytes());    
		 return new String(encoded);
	}
	public static String decodeString(String param){
		
		 byte[] decoded = Base64.decodeBase64(param);      
	       return new String(decoded);
	}
	
}
