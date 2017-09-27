import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

/**
 * @author Jeeva.K
 */

public class GameFrame extends JFrame {

	private JFrame mainFrame;
	private JMenu game;
	private JMenuBar menuBar;
	private JMenuItem reset;
	private JLabel yourGuess, computerGuess;
	private Color middleColor = new Color(166, 110, 78);
	private Color topColor = new Color(223, 182, 87);
	private JPanel scorePanel;
	private JPanel playerSelectPanel;
	private JPanel runPanel;
	private JLabel playerText, computerText;
	private JLabel playerScore, computerScore;
	private JLabel playerRun;
	private JLabel computerRun;
	private JLabel gameStatus;
	private JButton tossButton;
	private JLabel scorePanelDivider;
	private JLabel runPanelDivider;
	private JButton nextTurn;
	private ArrayList<JButton> playerButtons = new ArrayList<JButton>();
	private boolean playerIsBatting = false, computerIsBatting = false,
			playerIsOut = false, computerIsOut = false;
	private boolean playerWonToss = false, computerWonToss = false;
	private int matchOver = 0;
	private JLabel tossStatus;
	private Integer playerRunsTotal = 0, computerRunsTotal = 0, playerRuns,
			computerRuns;

	private ImageIcon mainIcon = new ImageIcon("icon.jpg");
	private Dimension screenDimension = getScreenDimensions();
	private Font lucidaFax = new Font("Lucida Fax", Font.BOLD, 20);
	private Font bigFont = new Font("Lucida Fax", Font.BOLD, 100);
	private Font mediumFont = new Font("Lucida Fax", Font.BOLD, 26);
	private Font smallFont = new Font("Lucida Fax", Font.BOLD, 15);
	private GridBagConstraints c = new GridBagConstraints();

	// Main Layout for Frame which adds Buttons,Labels,MenuBar
	private void setupLayout() {

		mainFrame = new JFrame("Hand Cricket Game");
		setMainFrameLayout();

		scorePanel = new JPanel();
		scorePanel.setBackground(topColor);
		mainFrame.add(BorderLayout.NORTH, scorePanel);
		setScorePanelLayout();

		runPanel = new JPanel(new GridBagLayout());
		runPanel.setBackground(middleColor);
		mainFrame.add(BorderLayout.CENTER, runPanel);
		tossButton = new JButton(" Click for Toss !");
		tossButton.setFont(smallFont);

		// ActionListener for Toss Button
		tossButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				getToss();
				generateFlags();
				activateAll();
				disableToss();

			}
		});
		setRunPanelLayout();

		playerSelectPanel = new JPanel(new GridLayout(1, 6));
		mainFrame.add(BorderLayout.SOUTH, playerSelectPanel);
		setPlayerSelectPanelLayout();
		addactionListenersforPlayerRunButton();
		deactivateAll();

		menuBar = new JMenuBar();

		game = new JMenu("Game");
		game.setFont(smallFont);
		reset = new JMenuItem("Reset Game");
		reset.setFont(smallFont);
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				loopOverGameAgain();

			}
		});
		game.add(reset);
		menuBar.add(game);
		mainFrame.setJMenuBar(menuBar);

	}

	// Generates Flags associated
	protected void generateFlags() {

		if (playerWonToss == true) {
			playerIsBatting = true;
			computerIsBatting = false;
			matchOver = 0;
			playerIsOut = false;
			computerIsOut = false;

		} else if (computerWonToss == true) {

			playerIsBatting = false;
			computerIsBatting = true;
			matchOver = 0;
			playerIsOut = false;
			computerIsOut = false;
		}

	}

	// Disables Toss Button
	protected void disableToss() {

		tossButton.setEnabled(false);

	}

	// Enables Toss Button
	protected void enableToss() {

		tossButton.setEnabled(true);

	}

	// Deactivation Logic for all Components
	protected void deactivateAll() {

		for (int i = 0; i < 6; i++) {
			playerButtons.get(i).setEnabled(false);
		}

	}

	// Activation Logic for all Components
	protected void activateAll() {

		for (int i = 0; i < 6; i++) {
			playerButtons.get(i).setEnabled(true);
		}

	}

	// Generate Random Toss Flag
	protected void getToss() {

		int toss = (int) ((Math.random() * 2) + 1);
		if (toss == 1) {
			playerWonToss = true;
			playerRun.setText("0" + " ");
			computerRun.setText("0" + " ");
			gameStatus.setText("You are Batting !");
			computerWonToss = false;
		} else {
			playerWonToss = false;
			playerRun.setText("0" + " ");
			computerRun.setText("0" + " ");
			gameStatus.setText("You are Bowling !");
			computerWonToss = true;
		}

	}

	// Setting up ActionListeners for all Player clickable buttons and passing
	// of associated values
	private void addactionListenersforPlayerRunButton() {

		for (Integer i = 1; i < 7; i++) {
			playerButtons.get(i - 1).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (e.getSource() == playerButtons.get(0)) {
						playerRun.setText("1" + " ");
						playerRuns = 1;
						computerRandom();
						checkScoreandGenerate();

					}
					if (e.getSource() == playerButtons.get(1)) {
						playerRun.setText("2" + " ");
						playerRuns = 2;
						computerRandom();
						checkScoreandGenerate();

					}
					if (e.getSource() == playerButtons.get(2)) {
						playerRun.setText("3" + " ");
						playerRuns = 3;
						computerRandom();
						checkScoreandGenerate();

					}
					if (e.getSource() == playerButtons.get(3)) {
						playerRun.setText("4" + " ");
						playerRuns = 4;
						computerRandom();
						checkScoreandGenerate();

					}
					if (e.getSource() == playerButtons.get(4)) {
						playerRun.setText("5" + " ");
						playerRuns = 5;
						computerRandom();
						checkScoreandGenerate();

					}
					if (e.getSource() == playerButtons.get(5)) {
						playerRun.setText("6" + " ");
						playerRuns = 6;
						computerRandom();
						checkScoreandGenerate();

					}

				}
			});
		}
	}

	// This is the Run compute logic where all user and player run statistics
	// are handled
	protected void checkScoreandGenerate() {

		if ((playerIsBatting == true) && (playerIsOut == false)) {

			yourGuess.setText("You Bat !      ");
			computerGuess.setText("Computer Bowl !");

			if ((playerRuns == computerRuns)) {

				playerIsOut = true;
				playerIsBatting = false;
				computerIsBatting = true;
				computerIsOut = false;
				matchOver++;
				JOptionPane.showMessageDialog(mainFrame, "PLAYER IS OUT !!!!",
						"Play Status", JOptionPane.PLAIN_MESSAGE);
				playerRun.setText("0");
				computerRun.setText("0");
				yourGuess.setText("Your Guess !");
				computerGuess.setText("Computer Guess !");
				gameStatus.setText("You are Bowling !");
				if (matchOver == 2) {

					if ((computerRunsTotal == playerRunsTotal)) {

						JOptionPane.showMessageDialog(mainFrame,
								"MATCH IS DRAW !!!!", "Game Status",
								JOptionPane.PLAIN_MESSAGE);
						loopOverGameAgain();
					} else if (playerRunsTotal > computerRunsTotal) {
						JOptionPane.showMessageDialog(mainFrame,
								"PLAYER WON THE MATCH !!!!", "Game Status",
								JOptionPane.PLAIN_MESSAGE);
						loopOverGameAgain();
					} else {
						JOptionPane.showMessageDialog(mainFrame,
								"COMPUTER WON THE MATCH !!!!", "Game Status",
								JOptionPane.PLAIN_MESSAGE);
						loopOverGameAgain();
					}

				}

			} else {

				playerRunsTotal += playerRuns;
				playerScore.setText(playerRunsTotal.toString());

				if ((computerRunsTotal == playerRunsTotal)) {

					JOptionPane.showMessageDialog(mainFrame,
							"MATCH IS DRAW !!!!", "Game Status",
							JOptionPane.PLAIN_MESSAGE);
					loopOverGameAgain();
				}

				else if ((computerRunsTotal < playerRunsTotal)
						&& (matchOver == 1)) {

					JOptionPane.showMessageDialog(mainFrame,
							"YOU WON THE MATCH !!!!", "Game Status",
							JOptionPane.PLAIN_MESSAGE);
					loopOverGameAgain();
				}
			}

		} else if ((computerIsBatting == true) && (computerIsOut == false)) {

			yourGuess.setText("You Bowl !    ");
			computerGuess.setText("Computer Bat !");

			if (playerRuns == computerRuns) {
				computerIsOut = true;
				computerIsBatting = false;
				playerIsBatting = true;
				computerIsOut = false;
				matchOver++;
				JOptionPane.showMessageDialog(mainFrame,
						"COMPUTER IS OUT !!!!", "Play Status",
						JOptionPane.PLAIN_MESSAGE);
				playerRun.setText("0");
				computerRun.setText("0");
				yourGuess.setText("Your Guess !");
				computerGuess.setText("Computer Guess !");
				gameStatus.setText("You are Batting !");
				if (matchOver == 2) {

					if ((computerRunsTotal == playerRunsTotal)) {

						JOptionPane.showMessageDialog(mainFrame,
								"MATCH IS DRAW !!!!", "Game Status",
								JOptionPane.PLAIN_MESSAGE);
						loopOverGameAgain();
					}

					else if (playerRunsTotal > computerRunsTotal) {
						JOptionPane.showMessageDialog(mainFrame,
								"YOU WON THE MATCH !!!!", "Game Status",
								JOptionPane.PLAIN_MESSAGE);
						loopOverGameAgain();
					} else {
						JOptionPane.showMessageDialog(mainFrame,
								"COMPUTER WON THE MATCH !!!!", "Game Status",
								JOptionPane.PLAIN_MESSAGE);
						loopOverGameAgain();
					}

				}

			} else {

				computerRunsTotal += computerRuns;
				computerScore.setText(computerRunsTotal.toString());

				if ((computerRunsTotal == playerRunsTotal) && (matchOver == 1)) {

					JOptionPane.showMessageDialog(mainFrame,
							"MATCH IS DRAW !!!!", "Game Status",
							JOptionPane.PLAIN_MESSAGE);
					loopOverGameAgain();
				} else if ((computerRunsTotal > playerRunsTotal)
						&& (matchOver == 1)) {

					JOptionPane.showMessageDialog(mainFrame,
							"COMPUTER WON THE MATCH !!!!", "Game Status",
							JOptionPane.PLAIN_MESSAGE);
					loopOverGameAgain();
				}

			}

		}

	}

	// This line of code is to restore flags to default and Play game again
	private void loopOverGameAgain() {

		playerRunsTotal = 0;
		computerRunsTotal = 0;

		playerIsBatting = false;
		computerIsBatting = false;
		playerWonToss = false;
		computerWonToss = false;
		tossButton.setEnabled(true);
		playerRun.setText("0" + " ");
		playerScore.setText("0");
		computerScore.setText("0");
		computerRun.setText("0" + " ");
		gameStatus.setText("Click for Toss !!");
		deactivateAll();

	}

	// Setting up of Player selectable Buttons
	private void setPlayerSelectPanelLayout() {

		JButton button;

		for (Integer i = 1; i < 7; i++) {

			button = new JButton(i.toString());
			button.setFont(mediumFont);
			playerButtons.add(button);
			playerSelectPanel.add(button);
		}

	}

	// Setting up Grid Layout for Run showed for each user click
	private void setRunPanelLayout() {

		playerRun = new JLabel("0");
		playerRun.setFont(bigFont);
		yourGuess = new JLabel("Your Guess");
		yourGuess.setFont(smallFont);
		computerGuess = new JLabel("Computer Guess");
		computerGuess.setFont(smallFont);
		computerRun = new JLabel("0");
		computerRun.setFont(bigFont);
		c.gridx = 0;
		c.gridy = 0;
		runPanel.add(playerRun, c);
		c.gridx = 0;
		c.gridy = 2;
		runPanel.add(yourGuess, c);
		c.gridx = -1;
		c.gridy = -20;
		runPanel.add(tossButton, c);
		c.gridx = 2;
		c.gridy = 0;
		runPanel.add(computerRun, c);
		c.gridx = 2;
		c.gridy = 2;
		runPanel.add(computerGuess, c);
		c.gridx = 1;
		c.gridy = 3;
		gameStatus = new JLabel("Click for Toss !!");
		gameStatus.setFont(mediumFont);
		runPanel.add(gameStatus, c);

	}

	// Generate Random Number for Computer to Play
	private void computerRandom() {

		Integer num = (int) ((Math.random() * 6) + 1);
		computerRun.setText(num.toString());
		computerRuns = num;

	}

	// Compute Layout for Panel which contains to Overall Scores of Player and
	// Computer
	private void setScorePanelLayout() {

		playerText = new JLabel("Your Score :");
		tossStatus = new JLabel("");
		playerScore = new JLabel("0");
		computerText = new JLabel("Computer Score :");
		computerScore = new JLabel("0");
		scorePanelDivider = new JLabel("     |   ");

		scorePanel.add(playerText);
		scorePanel.add(playerScore);
		scorePanel.add(scorePanelDivider);
		scorePanel.add(computerText);
		scorePanel.add(computerScore);
		playerScore.setFont(lucidaFax);
		playerText.setFont(lucidaFax);
		scorePanelDivider.setFont(lucidaFax);
		computerScore.setFont(lucidaFax);
		computerText.setFont(lucidaFax);

	}

	// Sets MainFrame which contains all other Components.The calculation
	// Involves determining dynamic center location
	private void setMainFrameLayout() {

		mainFrame.setBounds(
				(int) ((screenDimension.getWidth() / 2) - (0.5 * 500)),
				(int) ((screenDimension.getHeight() / 2) - (0.5 * 500)), 500,
				500);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setIconImage(getIconImage());
		mainFrame.setVisible(true);

	}

	// Get Screen Dimensions
	private Dimension getScreenDimensions() {

		Dimension screen;
		Toolkit tool = Toolkit.getDefaultToolkit();
		screen = tool.getScreenSize();

		return screen;
	}

	// Main Thread. This is where all starts
	public static void main(String[] args) {

		GameFrame gameframe = new GameFrame();
		gameframe.setupLayout();
	}

}
