package cluePlayer;

public class Suggestion {
	String person, weapon, room;

	public Suggestion(String person, String weapon, String room) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Suggestion other = (Suggestion) obj;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}
	
	public String getRoom(){
		return room;
	}

	@Override
	public String toString() {
		return "Is it " + person + " with the " + weapon
				+ " in the " + room + "?";
	}

	public String getPerson() {
		return person;
	}

	public String getWeapon() {
		return weapon;
	}
	
	

}
