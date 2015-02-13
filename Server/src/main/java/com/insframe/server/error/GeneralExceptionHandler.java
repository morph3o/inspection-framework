package com.insframe.server.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class GeneralExceptionHandler {
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	 * Handle exceptions thrown by handlers.
	 */
//	@ExceptionHandler(value = Exception.class)	
//	public ModelAndView exception(Exception exception, WebRequest request) {
//		ModelAndView modelAndView = new ModelAndView("error/general");
//		modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
//		return modelAndView;
//	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value=HttpStatus.OK)
	@ResponseBody
	public JSONErrorMessage exception(Exception exception, HttpServletRequest req) {
		String errorMessage = exception.getMessage(); 
        String errorURL = req.getRequestURL().toString();
         
        return new JSONErrorMessage(errorMessage, errorURL);
	}
	
	@ExceptionHandler({InspectionFrameworkBaseException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ResponseBody
	public JSONErrorMessage objectNotFound(HttpServletRequest req, InspectionFrameworkBaseException ex) {
		String errorMessage = messageSource.getMessage(ex.getMessageId(), ex.getArgs(), LocaleContextHolder.getLocale());
        String errorURL = req.getRequestURL().toString();
         
        return new JSONErrorMessage(errorMessage, errorURL);
    }
}