package jangkoo.game.screen;

public interface LeaderBoard {
	public void submitScore(String user, long score);
	public void subWinningScore(String user, long score);
	public void toMainMenu();
	public void writeToMemory();
	public void viewScore();
	public void viewWinningScore();
	public void rate();
	public void donate();
	public void buy();
	public void adremove();
	public void unlockAchivement(String id);
	public void viewAchivement();
	public void buyItem(String itemName);
	public void showads();
	public void shareFB();
}
