package blackjack;
import java.util.*;

public class Blackjack implements BlackjackEngine {
	
	/**
	 * Constructor you must provide.  Initializes the player's account 
	 * to 200 and the initial bet to 5.  Feel free to initialize any other
	 * fields. Keep in mind that the constructor does not define the 
	 * deck(s) of cards.
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	public Blackjack(Random randomGenerator, int numberOfDecks) {
	    throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public int getNumberOfDecks() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public void createAndShuffleGameDeck() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public Card[] getGameDeck() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public void deal() {	
		throw new UnsupportedOperationException("You must implement this method.");
	}
		
	public Card[] getDealerCards() {
		throw new UnsupportedOperationException("You must implement this method.");
	}

	public int[] getDealerCardsTotal() {
		throw new UnsupportedOperationException("You must implement this method.");
	}

	public int getDealerCardsEvaluation() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public Card[] getPlayerCards() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public int[] getPlayerCardsTotal() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
		
	public int getPlayerCardsEvaluation() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public void playerHit() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public void playerStand() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public int getGameStatus() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
		
	public void setBetAmount(int amount) {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public int getBetAmount() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public void setAccountAmount(int amount) {	
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	public int getAccountAmount() {
		throw new UnsupportedOperationException("You must implement this method.");
	}
	
	/* Feel Free to add any private methods you might need */
}