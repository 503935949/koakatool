package com.supercode.koakatool.platform.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.catalina.connector.ResponseFacade;
//import org.apache.catalina.connector.RequestFacade;
//import org.apache.catalina.connector.ResponseFacade;

//import com.yueqingRMS.platform.util.SysUtil;

@Order(0) 
@Component
@Aspect
public class ProjectAspect {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private HttpSession session;

//	@Before：前置通知，在方法执行之前执行
//	@After：后置通知，在方法执行之后执行 
//	@AfterReturning：返回通知，在方法返回结果之后执行
//	@AfterThrowing：异常通知，在方法抛出异常之后
//	@Around：环绕通知，围绕着方法执行
	
	/** 
	 *  
	 * ************重用切点表达式******************************************************************************** 
	 *  
	 * 定义一个方法，用于声明切入点表达式，一般的，该方法中不再不要填土其他的代码。 
	 * 使用@Pointcut来声明切入点表达式。 
	 * 同一个类中其他通知直接使用方法名来引用当前的切入点表达式，如：@Before("method()") 
	 * 同一个报下其他类中的通知需要在方法名前加类名，如：@Before("class.method()") 
	 * 其他包下面类中的通知需要在方法名前加类的全额限定名,如：@AfterReturning(value="package.class.method()",returning="result") 
	 *  
	 * 第一个星号代表匹配任意修饰符及任意返回值, 第二个星号表示任意方法名称，参数列表中的两个点号表示任意数量和类型的参数 
	 * 
	 * 例如定义切入点表达式 execution(* com.sample.service.impl..*.*(..))
		execution()是最常用的切点函数，其语法如下所示：
		 整个表达式可以分为五个部分：
		 1、execution(): 表达式主体。
		 2、第一个*号：表示返回类型，*号表示所有的类型。
		 3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
		 4、第二个*号：表示类名，*号表示所有的类。
		 5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
 
	 */  
	
	@Pointcut("execution(* com..web..*.*(..))")
	// @Pointcut("@annotation(com.ycy.annotation.Action)")//3 自定义注解
	public void allJoinPoint(){}  
	
	
	/**
	 * @param joinPoint
	 */
	@Before("allJoinPoint()")
	public void Before(JoinPoint joinPoint)  //这个方法需要一个特定的参数
    {
		System.out.println("Before 前:");
		System.out.println("ProjectAspect-+"+request.getRequestURL());
		System.out.println("ProjectAspect-+"+request.getRequestURI());
		System.out.println("ProjectAspect-+"+request.getContextPath());
		System.out.println("ProjectAspect-+"+request.getServletPath());
		System.out.println("ProjectAspect-+"+request.getQueryString());
		String url = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		System.out.println("ProjectAspect-+url:"+url);
        System.out.println("Before 后:");
    }
	
	/**
	 * @param joinPoint
	 * @return
	 * @throws Throwable 
	 */
	@Around("allJoinPoint()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable  //这个方法需要一个特定的参数
    {
		System.out.println("Around 前:");
		Object returnValue = null;
		 //访问目标方法的参数：
        Object[] args = joinPoint.getArgs();
//        if (args != null && args.length > 0 && args[0].getClass() == String.class) {
//            args[0] = "改变后的参数1";
//        }
        //访问目标方法的类：
        Class<?> clazz = joinPoint.getTarget().getClass();
        //访问目标方法名称
        String methodNames = joinPoint.getSignature().getName();
        //方法参数中获取request
        //RequestFacade
//        HttpServletRequest request = null;
//        for(Object arg : args) {//RequestFacade
//        	if(RequestFacade.class.equals(arg.getClass())||HttpServletRequest.class.equals(arg.getClass())) {
//        		request = (HttpServletRequest) arg;
//        	}
//        }

        String url = null;
//        HttpSession session = null;
//        if(SysUtil.isNotNull(request)) {
//        	//获取请求的URL RequestFacade问题
//        	url = request.getRequestURI();
//            //获取Session  
//            session = request.getSession();  
//        }
		url = request.getRequestURI();
        //冲方法参数中获取response
        //ResponseFacade 
          HttpServletResponse response = null;
//        for(Object arg : args) {//RequestFacade
//        	if(ResponseFacade.class.equals(arg.getClass())||HttpServletResponse.class.equals(arg.getClass())) {
//        		response = (HttpServletResponse) arg;
//        	}
//        }
        
        
        
       
        try {
        	//用改变后的参数执行目标方法
        	returnValue = joinPoint.proceed();
            
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Throwable(e);
        }
        System.out.println(clazz+methodNames+response+url+session);
        System.out.println("Around 后:");
        return returnValue;
        
    }
	
	
	/**
	 * @param joinPoint
	 * @param returnValue
	 */
	@AfterReturning(pointcut="allJoinPoint()",returning = "returnValue")
	public void AfterRunning(JoinPoint joinPoint, Object returnValue)  
    {
		System.out.println("AfterRunning 前:");
		
		joinPoint.getTarget();
		
        System.out.println("AfterRunning 后:");
    }
	
	
	 
	/**
	 * @param joinPoint
	 * @param ex
	 */
	@AfterThrowing(pointcut="allJoinPoint()",throwing="ex" )
	public void AfterThrowing(JoinPoint joinPoint,Throwable ex)  
    {
		System.out.println("AfterThrowing 前:");
		//return throw new RuntimeException(ex);
        System.out.println("AfterThrowing 后:");
    }
	
	
}
