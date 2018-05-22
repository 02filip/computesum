package src.computeSum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *  Class which calculates the sum of all numbers 
 *  and determines the error type in case of an error
 */
public class Calculate {

	/*
	 *  Variables are defined here
	 */
	public final String str;
	public int sum = 0;
	public String delimiter = "";
	public int error = 0;

	/*
	 *  Constructor - uses a string and a delimiter as input
	 */
	public Calculate(String str, String inputDelim) {
		this.str = str;		

		/*
		 *  Delimiter is determined by removing all numbers from the input string
		 *  and shortening it to first character 
		 *  in case that more than 1 character is left, "invalid delimiter" error is thrown later in code
		 *  
		 *  This value is later compared to delimiter from input
		 */
		delimiter = str.replaceAll("[\\d]","").replaceAll("(.)\\1+","$1");

		/*
		 *  Checks if the input string contains 2 or more non-numeric characters in a row
		 *  Example "1,2,ab,5"
		 */
		Pattern checkifString = Pattern.compile("[\\D]{2,}");
		Matcher isString = checkifString.matcher(str);

		/*
		 *  Checks if the input string contains a negative number
		 *  by checking for any non-numeric character followed by a -
		 *  Example: "2,-3,5" or "2+-3+5"
		 */
		Pattern checkifNegative = Pattern.compile("[\\D]-");
		Matcher isNegative = checkifNegative.matcher(str);

		/*
		 *  Checks if the input string starts with a negative number
		 *  by checking for '-' character followed by a number 
		 *  at the beginning of the string
		 *  Example: "-22,3,5"
		 */
		Pattern checkifBeginNegative = Pattern.compile("^-[\\d]+");
		Matcher beginsNegative = checkifBeginNegative.matcher(str);

		/*
		 *  Checks if the string is not a single number
		 *  if yes, it should just be returned instead of calculating the sum
		 *  Example: "22" or "1", but numbers higher than 100 throw an error
		 */
		Pattern checkSingle = Pattern.compile("^[\\d]{1,}$");
		Matcher isSingle = checkSingle.matcher(str);
		
		/*
		 *  Error messages:
		 *  1 - number is higher than 100
		 *  2 - number is negative
		 *  3 - invalid input
		 *  4 - invalid delimiter
		 *
		 *  If negative number is found, error 2 is thrown
		 */
		if(isNegative.find() || (beginsNegative.find())) error=2;

		/*
		 *  If input contains 2 or more non-numeric characters in a row, error 3 is thrown
		 */
		else if (isString.find()) error = 3;

		/*  
		 *  If delimiter is "-" and the string contains "--", error 2 is thrown
		 *  Example: "5--5-8"
		 */
		else if (delimiter.contains("-") && str.contains("--")) error = 2;

		/*
		 *  If delimiter from string is equal to delimiter from input,
		 *  or delimiter from string is empty (necessary in cases of using a single number as input), continue
		 */
		else if (inputDelim.equals(delimiter) || delimiter.equals("")) {

			/*
			 *  If the string is a single number lower than 100, this number is the result
			 */
			if(isSingle.find()) {
				sum = Integer.parseInt(str);
				if(sum>=100) error=1;
			}
			else {

				/*
				 *  String is split by the delimiter and the sum is calculated
				 *  If the input string is empty, the sum is 0
				 *  If any number is equal or higher than 100, error 1 is thrown
				 *  In case of exception, error 3 is thrown 
				 */
				String[] numbers = str.split(delimiter);
				for(int i=0;i<numbers.length;i++){
					try{
						if(str== "") {
							sum = 0;
						}
						else if(Integer.parseInt(numbers[i])>=100) {
							error=1;
						}
						else {
							sum+=Integer.parseInt(numbers[i]);
						}
					}
					catch( Exception e ) {
						error=3;
					}
				}
			}
		}

		/*
		 *  If delimiter from string is not equal to the delimiter from input,
		 *  error 4 is thrown
		 *  Example: "http://192.168.1.101:8400/computeSum?input=21+3+4&delimiter=x"
		 */
		else {
			error=4;
		}
	}

	/* 
	 *  Getter for sum
	 */
	public int getResult() {
		return sum;
	}

	/*
	 *  Getter for the error code
	 */
	public int getException() {
		return error;
	}
}
