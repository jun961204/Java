
import javax.swing.*;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author 3035241897
 * TicTacToeClient.java
 * 
 *
 */

public class TicTacToeClient extends JFrame implements Runnable {
	JFrame window = new JFrame("Tic-Tac-Toe");
	private JTextArea displayArea; // JTextArea to display output
	private JPanel boardPanel; // panel for tic-tac-toe board
	private JPanel panel2; // panel to hold board
	private Square board[][]; // tic-tac-toe board

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
	JDialog dialog = new JDialog();

	private Square currentSquare; // current square
	private Socket connection; // connection to server
	private Scanner input; // input from server
	private Formatter output; // output to server
	private String ticTacToeHost; // host name for server
	private String myMark; // this client's mark
	private boolean myTurn; // determines which client's turn it is
	private final String X_MARK = "X"; // mark for first client
	private final String O_MARK = "O"; // mark for second client

// set up user-interface and board
	public TicTacToeClient(String host) {
		setTitle("Tic-Tac-Toe");
		window.setLocationByPlatform(true);

		ticTacToeHost = host; // set name of server
		displayArea = new JTextArea(5, 30); // set up JTextArea
		displayArea.setEditable(false);

		Lower.add(txt_inputName);
		Lower.add(Submit);

		boardPanel = new JPanel(); // set up panel for squares in board
		boardPanel.setLayout(new GridLayout(3, 3, 0, 0));

		board = new Square[3][3]; // create board

		// loop over the rows in the board
		for (int row = 0; row < board.length; row++) {
			// loop over the columns in the board
			for (int column = 0; column < board[row].length; column++) {
				// create square
				board[row][column] = new Square(" ", row * 3 + column);
				boardPanel.add(board[row][column]); // add square
			} // end inner for
		} // end outer for

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

		setJMenuBar(menuBar);

		panel2 = new JPanel(); // set up panel to contain boardPanel
		panel2.add(boardPanel, BorderLayout.CENTER); // add board panel
		add(panel2, BorderLayout.CENTER); // add container panel
		add(Lower, BorderLayout.SOUTH);
		add(Instruction, BorderLayout.NORTH);

		Submit.addActionListener(new buttonActions());

		setSize(300, 225); // set size of window
		setVisible(true); // show window

		startClient();
	} // end TicTacToeClient constructor

// start the client thread
	public void startClient() {
		try // connect to server, get streams and start outputThread
		{
// make connection to server
			connection = new Socket(InetAddress.getByName(ticTacToeHost), 12345);

// get streams for input and output
			input = new Scanner(connection.getInputStream());
			output = new Formatter(connection.getOutputStream());
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch

// create and start worker thread for this client
		ExecutorService worker = Executors.newFixedThreadPool(1);
		worker.execute(this); // execute client
	} // end method startClient

// control thread that allows continuous update of displayArea
	public void run() {
		myMark = input.nextLine(); // get player's mark (X or O)

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			} // end method run
		} // end anonymous inner class
		); // end call to SwingUtilities.invokeLater

		myTurn = (myMark.equals(X_MARK)); // determine if client's turn

		// receive messages sent to client and output them
		while (true) {
			if (input.hasNextLine())
				processMessage(input.nextLine());
		} // end while
	} // end method run

// process messages received by client
	private void processMessage(String message) {
// valid move occurred
		if (message.equals("Valid move.")) {
			displayMessage("Valid move, please wait.\n");
			Instruction.setText("Valid move, please wait.");
			setMark(currentSquare, myMark); // set mark in square

		} // end if
		else if (message.equals("Invalid move, try again")) {
			displayMessage(message + "\n"); // display invalid move
			Instruction.setText("Invalid move, try again");
			myTurn = true; // still this client's turn
		} // end else if
		else if (message.equals("Opponent moved")) {
			int location = input.nextInt(); // get move location
			input.nextLine(); // skip newline after int location
			int row = location / 3; // calculate row
			int column = location % 3; // calculate column

			setMark(board[row][column], (myMark.equals(X_MARK) ? O_MARK : X_MARK)); // mark move
			displayMessage("Opponent moved. Your turn.\n");
			Instruction.setText("Opponent moved. Your turn.");
			myTurn = true; // now this client's turn
		} // end else if
		else
			displayMessage(message + "\n"); // display the message
	} // end method processMessage

// manipulate outputArea in event-dispatch thread
	private void displayMessage(final String messageToDisplay) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				displayArea.append(messageToDisplay); // updates output
			} // end method run
		} // end inner class
		); // end call to SwingUtilities.invokeLater
	} // end method displayMessage

	private boolean Winner(Square a[][]) {
		for (int i = 0; i < 3; i++) {
			if (CompareSquare(a[i][0], a[i][1], a[i][2]))
				return true;
			else if (CompareSquare(a[0][i], a[1][i], a[2][i]))
				return true;
			else if (CompareSquare(a[0][0], a[1][1], a[2][2]))
				return true;
			else if (CompareSquare(a[0][2], a[1][1], a[2][0]))
				return true;
		}
		return false;
	}

	private boolean CompareSquare(Square a, Square b, Square c) {
		if (a.getMark().equals(b.getMark()) && b.getMark().equals(c.getMark())
				&& ((a.getMark().equals("X") || a.getMark().equals("O"))))
			return true;
		else
			return false;
	}

	private boolean Finish(Square a[][]) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (a[i][j].getMark() == " ") {
					return false;
				}
			}
		}
		return true;
	}

// utility method to set mark on board in event-dispatch thread
	private void setMark(final Square squareToMark, final String mark) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				squareToMark.setMark(mark);
				int input = 0;
				if (Winner(board)) {
					if (myTurn) {
						JOptionPane.showMessageDialog(TicTacToeClient.this, "You Lost");
						if (input == JOptionPane.OK_OPTION) {
							System.exit(0);
						}
					} else {
						JOptionPane.showMessageDialog(TicTacToeClient.this, "You won");
						if (input == JOptionPane.OK_OPTION) {
							System.exit(0);
						}
					}
				}
				if (Finish(board)) {
					JOptionPane.showMessageDialog(TicTacToeClient.this, "Draw");
					if (input == JOptionPane.OK_OPTION) {
						System.exit(0);
					}
				} // set mark in square
			}
			// end method run
		} // end anonymous inner class
		); // end call to SwingUtilities.invokeLater
	} // end method setMark

// send message to server indicating clicked square
	public void sendClickedSquare(int location) {
// if it is my turn
		if (myTurn) {
			output.format("%d\n", location); // send location to server
			output.flush();
			myTurn = false; // not my turn anymore
		} // end if
	} // end method sendClickedSquare

// set current Square
	public void setCurrentSquare(Square square) {
		currentSquare = square; // set current square to argument
	} // end method setCurrentSquare

// private inner class for the squares on the board
	public class Square extends JPanel {
		private String mark; // mark to be drawn in this square
		private int location; // location of square
		private boolean flag = false;

		public String getMark() {
			return mark;
		}

		public Square(String squareMark, int squareLocation) {
			mark = squareMark; // set mark for this square
			location = squareLocation; // set location of this square

			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (flag == true) {
						setCurrentSquare(Square.this); // set current square

						// send location of this square
						sendClickedSquare(getSquareLocation());
					}
				} // end method mouseReleased
			} // end anonymous inner class
			); // end call to addMouseListener
		} // end Square constructor

// return preferred size of Square
		public Dimension getPreferredSize() {
			return new Dimension(30, 30); // return preferred size
		} // end method getPreferredSize

// return minimum size of Square
		public Dimension getMinimumSize() {
			return getPreferredSize(); // return preferred size
		} // end method getMinimumSize

// set mark for Square
		public void setMark(String newMark) {
			mark = newMark; // set mark of square
			repaint(); // repaint square
		} // end method setMark

// return Square location
		public int getSquareLocation() {
			return location; // return location of square
		} // end method getSquareLocation

// draw Square
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawRect(0, 0, 29, 29); // draw square
			g.drawString(mark, 11, 20); // draw mark
		} // end method paintComponent
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

				JOptionPane.showMessageDialog(window, "Some information about the game:\n"
						+ "Criteria for a valid move:\n" + "-The move is not occupied by any mark.\n"
						+ "-The move is made in the player's turn.\n" + "-The move is made within the 3 x 3 board.\n"
						+ "The game would continue and switch among the opposite player until it reaches either one of the follwing condietion:\n"

						+ "-player 1 or 2 wins\n"

						+ "-draw");
				//
			}
			String playerName = "";

			JButton buttonClicked = (JButton) e.getSource(); // get the particular button that was clicked
			if (e.getSource() == Submit) {
				playerName = txt_inputName.getText();
				setTitle("Tic Tac Toe-Player:" + playerName);
				Instruction.setText("WELCOME " + playerName);
				buttonClicked.setEnabled(false);
				// loop over the rows in the board
				for (int row = 0; row < 3; row++) {
					for (int column = 0; column < 3; column++) {
						board[row][column].flag = true;
						// end method mouseReleased
					} // end anonymous inner class
						// end call to addMouseListener
				} // end outer for

			}
		}
	}// end inner-class Square
} // end class TicTacToeClient
