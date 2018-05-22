package src.computeSum;

import java.io.UnsupportedEncodingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 *  Controller - ComputeSum controller is run here, computeSum URL is mapped to compute method
 *  Application uses Thymeleaf Spring MVC implementation
 *  
 *  This implementation works by using a Controller bean with CalcController class
 *  
 *  RequestMapping for /computeSum in URL is used and a computeSum View 
 *  (src/main/resources/templates/computeSum.hmtl) with info about the result is returned
 *  
 *  In case of an error, an error View (src/main/resources/templates/error.html) 
 *  containing detailed information is returned instead
 */
@Controller
public class CalcController  {	
	/*
	 *  Mapping for /computeSum GET request - when this is typed, compute method is run
	 */

	@RequestMapping(path = "/computeSum", method = RequestMethod.GET)

	/*  
	 *  The sum is calculated if there are no errors, if there are errors, error type is determined
	 *  Strings input and delimiter from the request are used as inputs, if no delimiter is defined, "," is used
	 *  
	 *  The method returns computeSum view - depending on the result, this view contains either the resulting sum
	 *  or information about the error (status number and error message)
	 */

	public String compute(@RequestParam(value="input") String input,
			@RequestParam(value="delimiter", defaultValue=",") String delimiter, Model model) throws UnsupportedEncodingException {

		/*
		 *  Calculate class is ran and variable sum filled with the result
		 *  This variable is then passed to the view as "sum" attribute
		 */		
		Calculate calc = new Calculate(input,delimiter); 		
		int sum = calc.getResult();								
		model.addAttribute("sum", sum);

		/*
		 *  Error message depends on error type:
		 *  1 - number higher than 100
		 *  2 - negative number
		 *  3 - invalid input
		 *  4 - invalid delimiter
		 */
		int errCode = calc.getException();
		switch(errCode) {
		case 1: throw new NumberTooHighException();
		case 2: throw new NegativeNumberException();
		case 3: throw new InputException();
		case 4: throw new DelimiterException();
		}

		/*
		 *  computeSum view is returned here		
		 */
		return "computeSum";
	}

	/*
	 *   Responses below depend on the exact error type,
	 *   all are extensions of RuntimeException class
	 *   
	 *   ResponseStatus determines the error type (value) and message (reason)
	 */

	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Number higher than 100 not supported")
	public class NumberTooHighException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Negative number not supported")
	public class NegativeNumberException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid input")
	public class InputException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid delimiter")
	public class DelimiterException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
