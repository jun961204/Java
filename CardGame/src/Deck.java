  
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
  
  private ArrayList<Card> deck; //this is arraylist to store 52 cards. 
  
  public Deck() {   
    deck = new ArrayList<Card>(); //we initialize this arraylist of cards.
    
    for (int i = 0; i < 4; i++) { // we go through the 52 cards thorugh nested loop
      for (int j = 0; j < 13; j++) {                                                  
        if (j >= 10) { //if j is bigger or equal than 10, this means that the card will be either Jack, Queen, or King.
          Card card = new Card(i, j, 0, true); //we create card with the ith suit and jth rank which has the value of 10(J, Q, K)
          deck.add(card); //we add the card to our deck.
        }
        else { //we do this for any other cards other than J,Q,K, and Ace.
          Card card = new Card(i, j, j+1, false); //when j is 1 (for example), we have a two of a suit and this has a value of two. So for the value, we increment j by one.
          deck.add(card); //we add the card to our deck.
        } 
      }
    }
  }
  ///This will form Deck of 52 cards and set their face, rank, value and boolean of if they are special card or not)
  
  public void shuffleDeck() { //This method shuffles the deck to make cards random.
    Collections.shuffle(deck); 
  }
  public Card getCard(int i) { //This method returns the ith card of the deck.
    return deck.get(i); 
  }
  public Card removeCard(int i) { //This method removes the ithcard of the deck.
    return deck.remove(i); 
  }
  

}