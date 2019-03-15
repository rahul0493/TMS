package com.quinnox.flm.tms.configuration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@PropertySource("classpath:ldapconfig.properties")
public class LdapConfiguration {
	
	@Value("${ldap.url}")
	private String ldapURL;
	
	@Value("${ldap.searchbase}")
	private String searchBase;
	
	@Value("${ldap.userId}")
	private String userId;
	
	@Value("${ldap.displayName}")
	private String displayName;
	
	@Value("${ldap.userMail}")
	private String userMail;
	
	@Value("${ldap.manager}")
	private String userManager;
	
	@Value("${ldap.empNumber}")
	private String employeeNumber;
	
	@Value("${ldap.empTitle}")
	private String title;

	@Value("${ldap.domain}")
	private String domain;
	
	public Map<String, String> authenticateEmployee(String userName, String passwordString) throws Exception {
		
		Map<String, String> userAttrMap = null;
		LdapContext ldapContext = null;
		try {
			String returnedAtts[] = { userId, displayName, userMail,userManager,employeeNumber,title};
			String searchFilter = "("+ userId + "=" + userName + ")";
	         
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, ldapURL);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, domain + "\\" + userName);
			env.put(Context.SECURITY_CREDENTIALS, passwordString);
			
			SearchControls searchControls = new SearchControls();
			searchControls.setReturningAttributes(returnedAtts);
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			ldapContext = new InitialLdapContext(env, null);
			
			NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchControls);
			
			if (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				
				if (attrs != null) {
					userAttrMap = new HashMap<String, String>();
					NamingEnumeration<?> ne = attrs.getAll();
					while (ne.hasMore()) {
						Attribute attr = (Attribute) ne.next();
						userAttrMap.put(attr.getID(), attr.get().toString());
					}
					ne.close();
				}
				answer.close();
			}
		} catch(CommunicationException ex) {
			throw ex;
		} catch (AuthenticationException aEx) {
			return null;
		} catch (NamingException nEx) {
			return null;
		} catch (Exception ex) {  
			throw ex;
		} finally {
			try {
				if(ldapContext != null) {
					ldapContext.close();
				}
			} catch (Exception e) { }
		}
		return userAttrMap;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	
}
