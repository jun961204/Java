
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class TicTacToe {
	int counter =0;
	JFrame window = new JFrame("Tic-Tac-Toe");

	JMenuBar menuBar;
	JMenu menu = new JMenu("Control");
	JMenuItem exit = new JMenuItem("Exit");
	JMenu help = new JMenu("Help");
	JMenuItem instruct = new JMenuItem("Instructions");
	JPanel MainPanel = new JPanel();
	JPanel Upper = new JPanel();
	JPanel Lower = new JPanel();
	JPanel Middle = new JPanel();
	JLabel Instruction = new JLabel("Enter your player Name...");
	JButton Submit = new JButton("Submit");
	JTextField txt_inputName = new JTextField(20);

	JButton buttons[] = new JButton[9];
	int alternate = 0;// if this number is a even, then put a X. If it's odd, then put an O

	public TicTacToe() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createGUI(window);
		MainPanel.setLayout(new BorderLayout());
		Submit.addActionListener(new buttonListener());

		Upper.add(Instruction);
		Lower.add(txt_inputName);
		Lower.add(Submit);


		Middle.setLayout(new GridLayout(3, 3));
		Middle.setMaximumSize(new Dimension(300,300));
		initializebuttons();
		
		MainPanel.add(Upper, BorderLayout.NORTH);
		MainPanel.add(Middle, BorderLayout.CENTER);
		MainPanel.add(Lower, BorderLayout.SOUTH);
		window.getContentPane().add(MainPanel);

		window.pack();
		window.setVisible(true);
	}


	void createGUI(JFrame f) {
		menuBar = new JMenuBar();

		// Build the first menu.
		menuBar.add(menu);

		// a group of JMenuItems
		exit.addActionListener(new buttonActions());
		menu.add(exit);

		// Build second menu in the menu bar.

		menuBar.add(help);

		help.add(instruct);
		instruct.addActionListener(new buttonActions());


		window.setJMenuBar(menuBar);
	}

	public void initializebuttons() {
		for (int i = 0; i <= 8; i++) {
			buttons[i] = new JButton();
			buttons[i].setText("");
			buttons[i].setPreferredSize(new Dimension(100,100));
			buttons[i].addActionListener(new buttonListener());
			buttons[i].setEnabled(false);

			Middle.add(buttons[i]); // adds this button to JPanel (note: no need for JPanel.add(...)
								// because this whole class is a JPanel already
		}
	}

	public void resetButtons() {
		for (int i = 0; i <= 8; i++) {
			buttons[i].setText("");
			buttons[i].setEnabled(true);
		}
		counter = 0;
		window.setTitle("Tic-Tac-Toe");
		Instruction.setText("Enter your player Name...");
	}

// when a button is clicked, it generates an ActionEvent. Thus, each button needs an ActionListener. When it is clicked, it goes to this listener class that I have created and goes to the actionPerformed method. There (and in this class), we decide what we want to do.
	private class buttonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String playerName = "";
			

			JButton buttonClicked = (JButton) e.getSource(); // get the particular button that was clicked
			if (e.getSource() == Submit) {
				playerName = txt_inputName.getText();
				window.setTitle("Tic Tac Toe-Player:"+playerName);
				Instruction.setText("WELCOME "+playerName);
				resetButtons();
				buttonClicked.setEnabled(false);
				alternate = 0;
				counter = 0;
			} 
			else
			
			if (alternate % 2 == 0) {
				buttonClicked.setText("X");
				buttonClicked.setEnabled(false);
			}
				else {
				buttonClicked.setText("O");
				buttonClicked.setEnabled(false);
				}

			if (checkForWin() == true) {
				JOptionPane.showConfirmDialog(null, "Game Over.");
				resetButtons();
			}
			if (counter == 9) {
				JOptionPane.showConfirmDialog(null, "Game Over.");
				resetButtons();
			}
			alternate++;
			counter++;

		}

		public boolean checkForWin() {
			/**
			 * Reference: the button array is arranged like this as the board 0 | 1 | 2 3 |
			 * 4 | 5 6 | 7 | 8
			 */
			// horizontal win check
			if (checkAdjacent(0, 1) && checkAdjacent(1, 2)) // no need to put " == true" because the default check is											// for true
				return true;
			else if (checkAdjacent(3, 4) && checkAdjacent(4, 5))
				return true;
			else if (checkAdjacent(6, 7) && checkAdjacent(7, 8))
				return true;

			// vertical win check
			else if (checkAdjacent(0, 3) && checkAdjacent(3, 6))
				return true;
			else if (checkAdjacent(1, 4) && checkAdjacent(4, 7))
				return true;
			else if (checkAdjacent(2, 5) && checkAdjacent(5, 8))
				return true;

			// diagonal win check
			else if (checkAdjacent(0, 4) && checkAdjacent(4, 8))
				return true;
			else if (checkAdjacent(2, 4) && checkAdjacent(4, 6))
				return true;
			else
				return false;

		}

		public boolean checkAdjacent(int a, int b) {
			if (buttons[a].getText().equals(buttons[b].getText()) && !buttons[a].getText().equals(""))
				return true;
			else
				return false;
		}

	}

	public static void main(String[] args) {
		new TicTacToe();

	}

	class buttonActions implements ActionListener {
		/**
		 * the main part of this program list of if loops depending on the which button
		 * has pressed and so on
		 * 
		 */

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == exit) {
				System.exit(0);
			} else if (e.getSource() == instruct) {

				JOptionPane.showMessageDialog(window, 
						"Some information about the game:\n"
						+ "Criteria for a valid move:\n"
						+ "-The move is not occupied by any mark.\n"
						+ "-The move is made in the player's turn.\n"
						+ "-The move is made within the 3 x 3 board.\n"
						+ "The game would continue and switch among the opposite player until it reaches either one of the follwing condietion:\n"

						+ "-player 1 or 2 wins\n"

						+ "-draw");
				//
			}
		}
	}
}
