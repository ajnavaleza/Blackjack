package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Blackjack implements BlackjackEngine {

	private int numberOfDecks;
	private Random randomGenerator;
	private ArrayList<Card> gameDeck;
	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private int betAmount;
	private int playerAccountAmount;
	private int gameStatus;

	/**
	 * Constructor you must provide. Initializes the player's account to 200 and
	 * the initial bet to 5. Feel free to initialize any other fields. Keep in
	 * mind that the constructor does not define the deck(s) of cards.
	 * 
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	public Blackjack(Random randomGenerator, int numberOfDecks) {
		playerAccountAmount = 200;
		betAmount = 5;
		this.randomGenerator = randomGenerator;
		this.numberOfDecks = numberOfDecks;
	}

	public int getNumberOfDecks() {
		return numberOfDecks;
	}

	public void createAndShuffleGameDeck() {
		// Creates game deck
		gameDeck = new ArrayList<Card>();

		// Adds a rank and suit for each new card and adds it to the ArrayList
		for (int i = 0; i < numberOfDecks; i++) {
			for (CardSuit suit : CardSuit.values()) {
				for (CardValue rank : CardValue.values()) {
					gameDeck.add(new Card(rank, suit));
				}
			}
		}
		// Shuffles game deck
		Collections.shuffle(gameDeck, randomGenerator);

	}

	public Card[] getGameDeck() {
		// Creates an [] for gameDeck
		Card[] gameDeckArray = new Card[gameDeck.size()];

		// Converts gameDeck ArrayList to []
		gameDeckArray = gameDeck.toArray(gameDeckArray);

		// Returns gameDeck []
		return gameDeckArray;
	}

	public void deal() {
		// Set gameStatus to in progress
		gameStatus = GAME_IN_PROGRESS;

		// Create and shuffle the game deck
		createAndShuffleGameDeck();

		// Create the player and dealer cards
		playerCards = new ArrayList<Card>();
		dealerCards = new ArrayList<Card>();

		// Deal 1st card off top of the deck to the player
		Card playerCard1 = gameDeck.remove(0);
		playerCards.add(playerCard1);

		// Deal 2nd card off to the dealer
		Card dealerCard1 = gameDeck.remove(0);
		dealerCard1.setFaceDown();
		dealerCards.add(dealerCard1);

		// Deal 3rd card off to player
		Card playerCard2 = gameDeck.remove(0);
		playerCards.add(playerCard2);

		// Deal 4th card off to dealer
		Card dealerCard2 = gameDeck.remove(0);
		dealerCards.add(dealerCard2);

		// Set game in progress to true
		getGameStatus();

		// Subtract bet amount from player account
		playerAccountAmount -= betAmount;
	}

	public Card[] getDealerCards() {
		// Creates an [] for dealerCards
		Card[] dealerCardsArray = new Card[dealerCards.size()];

		// Converts dealerCards ArrayList to []
		dealerCardsArray = dealerCards.toArray(dealerCardsArray);

		// Returns dealerCards []
		return dealerCardsArray;
	}

	public int[] getDealerCardsTotal() {
		// Variables
		int n = 0;
		int sum = 0;
		int sumAce = 0;
		int[] dealerCardsTotal = new int[n];
		boolean addedAce = false;
		int aceCount = -1;

		// Checks if the dealerCards ArrayList contains an ace, and gets the two
		// values
		// associated with that
		for (Card cards : dealerCards) {
			if (cards.getValue() == CardValue.Ace && addedAce == false) {
				sum += cards.getValue().getIntValue();
				sumAce = sum + 10;
				addedAce = true;
				aceCount++;
			}

			// Checks if that card is an ace, and if there are more than one
			// aces, treat one
			// as an 11, and one as a 1
			else if (cards.getValue() == CardValue.Ace && aceCount > 0) {
				sum += cards.getValue().getIntValue() + aceCount;
				sumAce = sum + 10;
				aceCount++;
			}

			// Gets the value of hand if there is no ace
			else {
				sum += cards.getValue().getIntValue();
				sumAce += cards.getValue().getIntValue();
			}
		}

		// If dealerCards contains an ace
		if (sum != sumAce) {

			// sumAce is greater than 21, but sum is less than 21
			if (sumAce > 21 && sum < 21) {
				n = 1;
				dealerCardsTotal = new int[] { sum };
			}

			// sumAce and sum are both greater than 21
			else if (sumAce > 21 && sum > 21) {
				dealerCardsTotal = null;
			}

			// sumAce and sum are both less than 21
			else if (sumAce < 21 && sum < 21) {
				n = 2;
				dealerCardsTotal = new int[] { sum, sumAce };
			}

			// sumAce is exactly 21
			else if (sumAce == 21 || sum == 21 || (sum == 11 && sumAce == 21)) {
				n = 2;
				dealerCardsTotal = new int[] { sum, sumAce };
			}

		}

		// If dealerCards do not contain an ace
		else {

			// sum is still less than 21
			if (sum < 21) {
				n = 1;
				dealerCardsTotal = new int[] { sum };
			}

			// sum is exactly 21
			else if (sum == 21) {
				n = 1;
				dealerCardsTotal = new int[] { sum };
			}

			// sum is >21
			else {
				dealerCardsTotal = null;
			}
		}

		// Return the array
		return dealerCardsTotal;

	}

	public int getDealerCardsEvaluation() {
		// Checks if dealerCards are over 21, if so, return bust
		if (getDealerCardsTotal() == null) {
			return BUST;
		}

		int dealerTotal = getDealerCardsTotal()[getDealerCardsTotal().length
				- 1];

		// If that dealerTotal is 21, check for whether its a blackjack or its
		// just 21
		if (dealerTotal == 21) {

			// If the array returned 21 but is of length 2, then that means
			// there was a
			// blackjack.
			if (dealerCards.size() != 2) {
				return HAS_21;
			}

			// If the array returned 21 but is of length anything else, then the
			// dealer has
			// 21
			else if (dealerCards.size() == 2) {
				return BLACKJACK;
			}
		}

		// If the array did not return 21, then it is less than 21.

		return LESS_THAN_21;
	}

	public Card[] getPlayerCards() {
		// Creates an [] for playerCards
		Card[] playerCardsArray = new Card[playerCards.size()];

		// Converts playerCards ArrayList to []
		playerCardsArray = playerCards.toArray(playerCardsArray);

		// Returns playerCards []
		return playerCardsArray;
	}

	public int[] getPlayerCardsTotal() {
		// Variables
		int n = 0;
		int sum = 0;
		int sumAce = 0;
		int[] playerCardsTotal = new int[n];
		boolean addedAce = false;
		int aceCount = -1;

		// Checks if the playerCards ArrayList contains an ace, and gets the two
		// values
		// associated with that
		for (Card cards : playerCards) {
			if (cards.getValue() == CardValue.Ace && addedAce == false) {
				sum += cards.getValue().getIntValue();
				sumAce = sum + 10;
				addedAce = true;
				aceCount++;
			}

			// Checks if that card is an ace, and if there are more than one
			// aces, treat one
			// as an 11, and one as a 1
			else if (cards.getValue() == CardValue.Ace && aceCount > 0) {
				sum += cards.getValue().getIntValue() + aceCount;
				sumAce = sum + 10;
				aceCount++;
			}
			// Gets the value of hand if there is no ace
			else {
				sum += cards.getValue().getIntValue();
				sumAce += cards.getValue().getIntValue();
			}
		}

		// If playerCards contains an ace
		if (sum != sumAce) {

			// sumAce is greater than 21, but sum is less than 21
			if (sumAce > 21 && sum < 21) {
				n = 1;
				playerCardsTotal = new int[] { sum };
			}

			// sumAce and sum are both greater than 21
			else if (sumAce > 21 && sum > 21) {
				playerCardsTotal = null;
			}

			// sumAce and sum are both less than 21
			else if (sumAce < 21 && sum < 21) {
				n = 2;
				playerCardsTotal = new int[] { sum, sumAce };
			}

			// sumAce is exactly 21
			else if (sumAce == 21 || sum == 21) {
				n = 2;
				playerCardsTotal = new int[] { sum, sumAce };
			}

			// RETURN BLACKJACK
			else if (sum == 11 && sumAce == 21) {
				n = 2;
				playerCardsTotal = new int[] { sum, sumAce };
			}

			else if (sumAce == 31) {
				n = 1;
				playerCardsTotal = new int[] { sum };
			}

			else {
				n = 2;
				playerCardsTotal = new int[] { sum, sumAce };
			}
		}

		// If playerCards do not contain an ace
		else {

			// sum is still less than 21
			if (sum < 21) {
				n = 1;
				playerCardsTotal = new int[] { sum };
			}

			// sum is 21, return it
			else if (sum == 21) {
				n = 1;
				playerCardsTotal = new int[] { sum };
			}

			// means that sum is > 21, so return null
			else {
				playerCardsTotal = null;
			}
		}

		// Return the array
		return playerCardsTotal;

	}

	public int getPlayerCardsEvaluation() {
		// Checks if dealerCards are over 21, if so, return bust
		if (getPlayerCardsTotal() == null) {
			return BUST;
		}

		if (getPlayerCardsTotal()[getPlayerCardsTotal().length - 1] == 21) {

			if (playerCards.size() != 2) {
				return HAS_21;
			}

			else if (playerCards.size() == 2) {
				return BLACKJACK;
			}

		}

		return LESS_THAN_21;

	}

	public void playerHit() {
		// Grabs a new card from the top of the stack
		Card nextCard = gameDeck.remove(0);

		// Adds the new card to the playerCards ArrayList and sets it face up
		playerCards.add(nextCard);
		nextCard.setFaceUp();

		// Evaluates playerCards
		int playerHitEval = getPlayerCardsEvaluation();

		// If the player card evaluation is over 21, sets gameStatus to 3, and
		// dealerWon
		// to true
		if (playerHitEval == BUST) {
			gameStatus = DEALER_WON;
			dealerCards.get(0).setFaceUp();
		}

		else {
			gameStatus = GAME_IN_PROGRESS;
		}
	}

	public void playerStand() {
		// Sets first card face up
		Card flip = dealerCards.get(0);
		flip.setFaceUp();

		int finalDealerTotal = getDealerCardsTotal()[getDealerCardsTotal().length
				- 1];
		int finalPlayerTotal = getPlayerCardsTotal()[getPlayerCardsTotal().length
				- 1];

		// Checks the dealer's hand if its less than 21
		while (getDealerCardsEvaluation() == LESS_THAN_21) {

			// If dealer's total is less than 16, keep on adding cards until it
			// is.
			if (finalDealerTotal < 16) {
				while (finalDealerTotal < 16) {
					Card newCard = gameDeck.remove(0);
					dealerCards.add(newCard);

					if (getDealerCardsTotal() == null) {
						finalDealerTotal = 22;
					} else {
						finalDealerTotal = getDealerCardsTotal()[getDealerCardsTotal().length
								- 1];
					}
				}
			}

			// If dealer's total is 16 or greater but also less than 21, the two
			// hands are
			// evaluated. The higher hand will win.
			if ((finalDealerTotal > 16 && finalDealerTotal < 21)
					|| finalDealerTotal == 16) {

				// If dealer has a higher hand, dealer wins.
				if (finalDealerTotal > finalPlayerTotal) {
					gameStatus = DEALER_WON;
					break;
				}

				// If player has a higher hand, player wins.
				else if (finalDealerTotal < finalPlayerTotal) {
					gameStatus = PLAYER_WON;
					setAccountAmount(playerAccountAmount + (betAmount * 2));
					break;
				}

				// If neither have a higher hand, game ends in a draw, bet
				// amount is returned to
				// player account.
				else {
					gameStatus = DRAW;
					setAccountAmount(playerAccountAmount + betAmount);
					break;
				}
			}
		}

		// If dealer's total is over 21, game is over. Player wins their bet
		// times 2
		if (getDealerCardsEvaluation() == BUST) {
			gameStatus = PLAYER_WON;
			setAccountAmount(playerAccountAmount + (betAmount * 2));
		}

		// If dealer gets a blackjack and player has less than 21, dealer wins
		if (getDealerCardsEvaluation() == BLACKJACK
				&& getPlayerCardsEvaluation() == LESS_THAN_21) {
			gameStatus = DEALER_WON;
		}

		// If both players get blackjack
		if (getDealerCardsEvaluation() == BLACKJACK
				&& getPlayerCardsEvaluation() == BLACKJACK) {
			gameStatus = DRAW;
			setAccountAmount(playerAccountAmount + betAmount);
		}

		// If dealer has 21, check if player has 21 too
		if (getDealerCardsEvaluation() == HAS_21) {

			// If player has 21, game ends in a draw, bet amount is returned to
			// player
			// account
			if (finalPlayerTotal == 21) {
				gameStatus = DRAW;
				setAccountAmount(playerAccountAmount + betAmount);
			}

			// If player has less than 21, dealer wins.
			else if (finalPlayerTotal < 21) {
				gameStatus = DEALER_WON;

			}

		}

		// If player has 21, check whether dealer has 21 or blackjack, if so,
		// game ends
		// in a draw, bet amount is returned to player
		if (getPlayerCardsEvaluation() == HAS_21) {

			if (getDealerCardsEvaluation() == HAS_21
					|| getDealerCardsEvaluation() == BLACKJACK) {
				gameStatus = DRAW;
				setAccountAmount(playerAccountAmount + betAmount);
			}
		}

		// If dealer has blackjack AND player has 21 OR dealer has 21 and player
		// has
		// blackjack, draw
		if (getDealerCardsEvaluation() == BLACKJACK
				&& getPlayerCardsEvaluation() == HAS_21
				|| getDealerCardsEvaluation() == HAS_21
						&& getPlayerCardsEvaluation() == BLACKJACK) {
			gameStatus = DRAW;
			setAccountAmount(playerAccountAmount + betAmount);
		}
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setBetAmount(int amount) {
		betAmount = amount;
	}

	public int getBetAmount() {
		return betAmount;
	}

	public void setAccountAmount(int amount) {
		playerAccountAmount = amount;
	}

	public int getAccountAmount() {
		return playerAccountAmount;
	}

	/* Feel Free to add any private methods you might need */
}