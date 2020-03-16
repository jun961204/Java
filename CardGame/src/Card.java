
public class Card {
	private int suit;
	private int rank;
	private int value;
	private boolean special;
	private String imageName;

	public Card() {
		suit = 0;
		rank = 0;
		value = 0;
		special = false;
	}
	/**
	 * 
	 * @param s : is for the suit of card, heart diamond and so on. but not really necessary in our prgram
	 * @param r : is for the rank of card, 0=ace, 1=two, ... 10 for jack and so on
	 * @param v : is for the value of the card. we determine winner by sum of non-special cards so all the non
	 * Special cards have its face value as v and special ones have just 0.
	 * @param sp : boolean value to determine if card is special, J,Q or K
	 */

	public Card(int s, int r, int v, boolean sp) {
		suit = s;
		rank = r;
		value = v;
		special = sp;
		imageName = "card_"+(s+1)+""+(r+1)+".gif";
	}
	/**
	 * this is just to initialize the Card plus adds new parameter imageName so it can be used later
	 * to call the appropriate image with convenience
	 */
	
	public int getSuit() { // this method returns you the suit of the card.
		return suit;
	}

	public int getRank() { // this method returns you the rank of the card.
		return rank;
	}

	public int getValue() { // this method returns you the value of the card.
		return value;
	}
	
	public boolean getSpecial() {
		return special;
	}
	public String getCardNum() {
		return imageName;
	}
	
	/**
	 * just list of functions to access the elements of cards.
	 */
	

}
