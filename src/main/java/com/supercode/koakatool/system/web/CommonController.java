package com.supercode.koakatool.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;





/**   
*    
* 项目名称：
* 类名称：CommonController   
* 类描述：   
* 创建人：林曌   
* 创建时间：
* 修改人：   
* 修改时间：
* 修改备注：   
* @version    
*    
*/
@Controller
//@RequestMapping("/manager")
public class CommonController {
	
	
	
	@RequestMapping("/main")
	public String findMainPage(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		Locale locale = null;
//		locale = Locale.CHINA;
		Locale sessionlocale = (Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		if(sessionlocale != null && sessionlocale !=locale)  {
			locale = sessionlocale;
		}else {
			locale = Locale.getDefault();
		}
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		return "common/main/main";
	}
	
	
	@RequestMapping("/findHomePage")
	public String findHomePage(){
		return "common/home/home";	
	}
	

	

	
}
