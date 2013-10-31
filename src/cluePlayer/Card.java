package cluePlayer;

public class Card{
	public enum CardType {
		ROOM ("Room"),
		WEAPON ("Weapon"),
		PERSON ("Person");
		
		private String value;

		CardType (String aValue) {
			value = aValue;
		}
		
		public String toString() {
			return value;
		}
	}


	public boolean equals(Object otherCard){  // this is done so we can see if one card equals another card
		boolean result = false;					// the method ends up helping to test that no Duplicates are present when cards are dealt 
		if(otherCard instanceof Card){
			Card thisCard = (Card) otherCard;
			result = (this.getName()== thisCard.getName() && this.getCartype()== thisCard.getCartype());
		}
		return result;
			
	}
		private String name;
		private CardType cardtype;
		
		public Card(String name, CardType cardtype) {
			this.name = name;
			this.cardtype = cardtype;
		}

		public Card(){
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public CardType getCartype() {
			return cardtype;
		}

		public void setCartype(CardType cartype) {
			this.cardtype = cardtype;
		}	
}
