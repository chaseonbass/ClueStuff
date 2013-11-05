package clueGame;
/** Name: ebreikss
 *  Date: Oct 2, 2013
 *  Purpose:
 */

public class BadConfigFormatException extends RuntimeException {
	
	public BadConfigFormatException() {}
	public BadConfigFormatException (String message) {
		super(message);
	}
	
	// All rows and columns being okay
	public BadConfigFormatException (String configFile, int lineNumber) {
		System.out.println(configFile + " has the wrong number of columns starting at line " + lineNumber);
	}
	
	// that the legend is formatted correctly (including having 9 rooms + walkway + closet)
	public BadConfigFormatException (String legendFile, String erredValue, int lineNumber) {
		if (erredValue.equals(",")) 
			System.out.println(legendFile + " has a missing comma at line " + lineNumber);
		else 
			System.out.println(legendFile + " has an invalid value " + erredValue + " at line " + lineNumber);
	}
	
}

