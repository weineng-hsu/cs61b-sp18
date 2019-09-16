/** this is a java tell year is a leap year or not
*    @author WNSHU
*/
public class LeapYear {

	public static void checkLeapYear (int year) {

		if (isLeapYear(year) == true ) {
			System.out.printf("%d is a leap year.\n", year);
		}
		else if (isLeapYear(year) == false) {
			System.out.printf("%d is not a leap year.\n", year);
		}
	}

	public static boolean isLeapYear (int year) {

		if (year % 400 ==0) {
			return true;
		}else if (year % 4 ==0 & year % 100 !=0){
			return true;
		}else return false;
	}


	public static void main (String[] args) {
		if (args.length<1) {
			System.out.println("Enter the integer to check leap year.");
			System.out.println("e.g. java LeapYear 2004");
		}

		for (int i = 0; i < args.length; i++ ) {

			try {
				int year = Integer.parseInt(args[i]);
				checkLeapYear(year);
			}
			catch (NumberFormatException e) {
				System.out.printf("%s is not a valid number.\n", args[i]);
			}

		}


	}












}