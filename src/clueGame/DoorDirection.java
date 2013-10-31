package clueGame;
/** Name: ebreikss
 *  Date: Oct 1, 2013
 *  Purpose:
 */

public enum DoorDirection {
	
	RIGHT ("Right"),
	LEFT ("Left"),
	UP ("Up"),
	DOWN ("Down"),
	NONE ("None");
	
	private String value;
	
	DoorDirection (String aValue) {
		value = aValue;
	}
	
	public String toString() {
		return value;
	}
}
