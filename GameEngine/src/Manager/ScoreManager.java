package Manager;

import Player.Player;

public class ScoreManager {
	private Player player1;
	private Player player2;
	private int curScoreP1;
	private int curScoreP2;

	
	public ScoreManager(Player p1, Player p2) {
		this.player1 = p1;
		this.player2 = p2;
	}
	public Player getPlayer1() {
		return player1;
	}
	public Player getPlayer2() {
		return player2;
	}

	public int getCurrentP1Score() {
		return curScoreP1;
	}

	public void addToCurrentP1Score(int score) {
		curScoreP1 += score;
	}

	public int getCurrentP2Score() {
		return curScoreP2;
	}

	public void addToCurrentP2Score(int score) {
		curScoreP2 += score;
	}


}
