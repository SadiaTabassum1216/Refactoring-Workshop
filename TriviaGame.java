package workshop;

import java.util.ArrayList;
import java.util.LinkedList;

public class TriviaGame {
	ArrayList players = new ArrayList();
	int[] places = new int[6];
	int[] purses = new int[6];
	boolean[] inPenaltyBox = new boolean[6];

	LinkedList popQuestions = new LinkedList();
	LinkedList scienceQuestions = new LinkedList();
	LinkedList sportsQuestions = new LinkedList();
	LinkedList rockQuestions = new LinkedList();

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

	public TriviaGame() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast("Rock Question " + i);
		}
	}

	public boolean add(String playerName) {
		players.add(playerName);
		places[players.size()] = 0;
		purses[players.size()] = 0;
		inPenaltyBox[players.size()] = false;

		announce(playerName + " was added");
		announce("They are player number " + players.size());
		return true;
	}

	public void roll(int roll) {
		announce(players.get(currentPlayer) + " is the current player");
		announce("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				announce(players.get(currentPlayer) + " is getting out of the penalty box");
				positioning(roll);
				askQuestion();
			} else {
				announce(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {
			positioning(roll);
			askQuestion();
		}

	}

	private void positioning(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11)
			places[currentPlayer] = places[currentPlayer] - 12;

		announce(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
		announce("The category is " + currentCategory());

	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			announce(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			announce(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			announce(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			announce(rockQuestions.removeFirst());
	}

	private String currentCategory() {
		if (places[currentPlayer] % 4 == 0 && places[currentPlayer] <= 10)
			return "Pop";

		else if (places[currentPlayer] % 4 == 1 && places[currentPlayer] <= 10)
			return "Science";

		else if (places[currentPlayer] % 4 == 2 && places[currentPlayer] <= 10)
			return "Sports";

		else
			return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				return addPointAndReturnWinner();
			} 
			else {
				currentPlayer++;
				if (currentPlayer == players.size())
					currentPlayer = 0;
				return true;
			}

		} 
		else 
			return addPointAndReturnWinner();

	}

	public boolean addPointAndReturnWinner() {
		announce("Answer was correct!!!!");
		purses[currentPlayer]++;
		announce(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;

		return winner;
	}

	public boolean wrongAnswer() {
		announce("Question was incorrectly answered");
		announce(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}

	protected void announce(Object message) {
		System.out.println(message);
	}
}