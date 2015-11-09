package kr.co.killers.deployutil.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.tmatesoft.svn.core.SVNException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    public static final String DEFAULT_ERROR_VIEW = "error/error";

    @ExceptionHandler(value = {
            Exception.class,
            SVNException.class
    })
    public ModelAndView handleError(HttpServletRequest request, Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
