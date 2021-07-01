package demos.spring.boot.flights.errors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(value = NotImplementedYetException.class)
    public ModelAndView handleError(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("theError", ex);
        mav.setViewName("defaultErrorHandler");
        return mav;
    }
}
