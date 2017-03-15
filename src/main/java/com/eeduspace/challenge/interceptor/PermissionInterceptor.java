package com.eeduspace.challenge.interceptor;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.google.gson.Gson;

public class PermissionInterceptor extends HandlerInterceptorAdapter{
	private final Logger log = LoggerFactory.getLogger(PermissionInterceptor.class);  
	private static Gson gson = new Gson();
	private static final String LOGIN_URL = "login.html";
	String requestId;
	@Autowired 
	private RedisClientTemplate redisClientTemplate;
	
	
	/**  
	 * 在业务处理器处理请求之前被调用  
	 * 如果返回false  
	 *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
	 * 如果返回true  
	 *    执行下一个拦截器,直到所有的拦截器都执行完毕  
	 *    再执行被拦截的Controller  
	 *    然后进入拦截器链,  
	 *    从最后一个拦截器往回执行所有的postHandle()  
	 *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		log.debug("开始执行拦截-------------");
		log.debug("方法名："+request.getPathInfo() + "---" + "请求uri：" + request.getRequestURI() + "---" +
				"请求方地址："+ request.getRemoteAddr() + "---" + request.getRequestedSessionId());
		
		log.debug("url-token:{}",request.getParameter("token"));
		BaseResponse baseResponse= new BaseResponse(requestId);
		
		String token = request.getParameter("token");
		if(StringUtils.isBlank(token)||"".equals(token)){
			log.debug("token 参数为空，请传入token！");
//			baseResponse.setResult(LOGIN_URL);//Android端已经处理  不需要返回LOGIN_URL
			baseResponse.setResult(null);
			response.getWriter().print(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_TOKENMISS.toString())));
			return false;
		}
		String result = redisClientTemplate.get(token);
		log.debug("result是："+result);
		if (StringUtils.isBlank(result)||"".equals(result)) {
			baseResponse.setResult(null);
			response.getWriter().print(gson.toJson(BaseResponse.setResponse(baseResponse, ResponseCode.FORBIDDEN_TOKENFAILURE.toString())));
			return false;
		}

		return true;
	}

	/** 
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
	 * 可在modelAndView中加入数据，比如当前时间 
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {

	}

	/**  
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
	 *   
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
	 */ 
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
