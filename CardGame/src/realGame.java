import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

public class realGame {
	/** 
	 * this part includes the basic elements of GUIs and the default settings for the game to run
	 */
	int cardreplaced = 0;

	ArrayList<Card> dealerHand;
	ArrayList<Card> playerHand;
	Deck deck;
	int playerBet = 0;
	int playerMon = 100;
	JFrame frame = new JFrame();
	JMenuBar menuBar;
	JMenu menu = new JMenu("Control");
	JMenuItem exit = new JMenuItem("Exit");
	JMenu help = new JMenu("Help");
	JMenuItem instruct = new JMenuItem("Instructions");

	JLabel label_Image1 = new JLabel();
	JLabel label_Image2 = new JLabel();
	JLabel label_Image3 = new JLabel();
	JLabel label_Image4 = new JLabel();
	JLabel label_Image5 = new JLabel();
	JLabel label_Image6 = new JLabel();

	JPanel MainPanel = new JPanel();
	JPanel DealerPanel = new JPanel();
	JPanel PlayerPanel = new JPanel();
	JPanel RpCardBtnPanel = new JPanel();
	JPanel ButtonPanel = new JPanel();
	JPanel InfoPanel = new JPanel();

	JButton btn_rpcard1 = new JButton("Replace Card 1");
	JButton btn_rpcard2 = new JButton("Replace Card 2");
	JButton btn_rpcard3 = new JButton("Replace Card 3");
	JButton btn_start = new JButton("Start");
	JButton btn_result = new JButton("Result");
	JTextField txt_inputbet = new JTextField(10);

	JLabel label_bet = new JLabel((" Bet: $"));
	JLabel label_info = new JLabel(" Please place your bet!");
	JLabel label_money = new JLabel("A amount of money you have: $" + playerMon);

	ImageIcon Image00 = new ImageIcon("card_back.gif");

	public realGame() {
		/**
		 * 
		 * This is the initilizer for the game for the start of every new game.
		 */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 1.3+
		createGUI(frame);
		startGame();

		initListeners();

		frame.getContentPane().add(MainPanel);
		frame.setTitle("A Simple Card Game");
		frame.setSize(400, 700);
		frame.setVisible(true);

	}

	void createGUI(JFrame f) {
		/** 
		 * this part adds more detail to the GUI elements, also enabling only the start button on the beginning
		 * 
		 */
		// Create the menu bar.

		menuBar = new JMenuBar();

		// Build the first menu.
		menuBar.add(menu);

		// a group of JMenuItems
		exit.addActionListener(new buttonActions());
		menu.add(exit);

		// Build second menu in the menu bar.

		menuBar.add(help);

		help.add(instruct);

		frame.setJMenuBar(menuBar);

		label_Image1.setIcon(Image00);
		label_Image2.setIcon(Image00);
		label_Image3.setIcon(Image00);
		label_Image4.setIcon(Image00);
		label_Image5.setIcon(Image00);
		label_Image6.setIcon(Image00);

		DealerPanel.add(label_Image1);
		DealerPanel.add(label_Image2);
		DealerPanel.add(label_Image3);

		PlayerPanel.add(label_Image4);
		PlayerPanel.add(label_Image5);
		PlayerPanel.add(label_Image6);

		RpCardBtnPanel.add(btn_rpcard1);
		RpCardBtnPanel.add(btn_rpcard2);
		RpCardBtnPanel.add(btn_rpcard3);

		ButtonPanel.add(label_bet);
		ButtonPanel.add(txt_inputbet);
		ButtonPanel.add(btn_start);
		ButtonPanel.add(btn_result);

		InfoPanel.add(label_info);
		InfoPanel.add(label_money);

		MainPanel.setLayout(new GridLayout(5, 1));
		MainPanel.add(DealerPanel);
		MainPanel.add(PlayerPanel);
		MainPanel.add(RpCardBtnPanel);
		MainPanel.add(ButtonPanel);
		MainPanel.add(InfoPanel);

		DealerPanel.setBackground(Color.green);
		PlayerPanel.setBackground(Color.red);
		RpCardBtnPanel.setBackground(Color.blue);
		
		
		btn_start.setEnabled(true);
		btn_result.setEnabled(false);
		btn_rpcard1.setEnabled(false);
		btn_rpcard2.setEnabled(false);
		btn_rpcard3.setEnabled(false);


	}

	void initListeners() {
		/**
		 * to add actionlisteners to each buttons
		 */
		ActionListener al = new buttonActions();

		btn_rpcard1.addActionListener(al);
		btn_rpcard2.addActionListener(al);
		btn_rpcard3.addActionListener(al);
		btn_start.addActionListener(al);
		btn_result.addActionListener(al);
		instruct.addActionListener(new buttonActions());


	}

	public void startGame() {
		/**
		 * this is to make the new deck, shuffle it and give each person hand of 3
		 */

		deck = new Deck();
		deck.shuffleDeck();
		dealerHand = new ArrayList<Card>();
	    playerHand = new ArrayList<Card>();
		for (int i = 0; i < 3; i++) {
			dealerHand.add(deck.getCard(i));
		}
		for (int i = 3; i < 6; i++) {
			playerHand.add(deck.getCard(i));
		}
	}

	public boolean playerWon(ArrayList<Card> d, ArrayList<Card> p) {
		/**
		 * this part gives the boolean value of determining the winner between 2 according to the 
		 * criteria given. the inputs are 2 arraylists of cards each for  dealer and player.
		 * true if player wins, false if dealer wins
		 */
		int sumD = 0, sumP = 0, sumDR = 0, sumPR = 0;

		for (int i = 0; i < 3; i++) {
			if (d.get(i).getSpecial()) {
				sumD += 1;
			} else {
				sumDR += d.get(i).getValue();
			}
		}
		for (int i = 0; i < 3; i++) {
			if (p.get(i).getSpecial()) {
				sumP += 1;
			} else {
				sumPR += p.get(i).getValue();
			}
		}
		if (sumD != sumP) {
			return sumP > sumD;
		} else {
			return (sumPR % 10) > (sumDR % 10);
		}

	}

	public static void main(String[] args) {

		new realGame();
	}

	class buttonActions implements ActionListener {
		/** the main part of this program
		 * list of if loops depending on the which button has pressed and so on
		 * 
		 */
		

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == exit) {
				System.exit(0);
			}
			else if(e.getSource() == instruct ) {

				JOptionPane.showMessageDialog(frame, "J, Q, K are regarded as special cards.\n"
						+ "Rule 1: The one with more special cards wins.\n"
						+ "Rule 2: If both have the same number of special cards, add the face values of the other card(s)"
						+ " and take the remainder after dividing the sum by 10. The one with a bigger remainder wins. (Note: Ace = 1). \n"
						+ "Rule 3: The dealer wins if both rule 1 and rule 2 cannot distinguish the winner.");
				// 
			}
			else
				
				if(e.getSource() == btn_start ) {
					String s = txt_inputbet.getText();
				if(txt_inputbet.getText().equals("") ) {
						JOptionPane.showMessageDialog(frame, "WARNING: The bet you place must be a positive integer!");
				}
				else {
					double currentBet2 = Double.parseDouble(s);
					int currentBet = (int)currentBet2;
				if (currentBet<=0||currentBet!=currentBet2) {
					JOptionPane.showMessageDialog(frame, "WARNING: The bet you place must be a positive integer!");
				}
				else if (currentBet>playerMon) {
					JOptionPane.showMessageDialog(frame, "Your bet exceeds your current money");
				}
				else {
					label_Image4.setIcon(new ImageIcon(playerHand.get(0).getCardNum()));
					label_Image5.setIcon(new ImageIcon(playerHand.get(1).getCardNum()));
					label_Image6.setIcon(new ImageIcon(playerHand.get(2).getCardNum()));
					label_info.setText("Your bet is: "+currentBet + "$");
					btn_start.setEnabled(false);
					btn_result.setEnabled(true);
					btn_rpcard1.setEnabled(true);
					btn_rpcard2.setEnabled(true);
					btn_rpcard3.setEnabled(true);
				
				}}
				
			}
			else if(e.getSource() == btn_result ) {
				int currentBet3 = Integer.parseInt(txt_inputbet.getText());
				label_Image1.setIcon(new ImageIcon(dealerHand.get(0).getCardNum()));
				label_Image2.setIcon(new ImageIcon(dealerHand.get(1).getCardNum()));
				label_Image3.setIcon(new ImageIcon(dealerHand.get(2).getCardNum()));
				if(playerWon(dealerHand,playerHand)) {
					JOptionPane.showMessageDialog(frame, "You won!");
					playerMon+=currentBet3;
					label_money.setText("A amount of money you have: $" + playerMon+"$");

					label_info.setText(" Please place your bet!");
					btn_start.setEnabled(true);
					btn_result.setEnabled(false);
					btn_rpcard1.setEnabled(false);
					btn_rpcard2.setEnabled(false);
					btn_rpcard3.setEnabled(false);
					
					startGame();
					createGUI(frame);
					cardreplaced=0;
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "You lost!");
					playerMon-=currentBet3;
					label_money.setText("A amount of money you have: $" + playerMon+"$");
					label_info.setText(" Please place your bet!");
					if(playerMon<=0) {
						JOptionPane.showMessageDialog(frame, "Game Over!\n"+"You no longer have money\n"+"please start new game");

						//int input = JOptionPane.showOptionDialog(null, "You lost! and busted! please exit the game", "The title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

						//if(input == JOptionPane.OK_OPTION)
					//	{		System.exit(0);		}
					
					btn_start.setEnabled(false);
					btn_result.setEnabled(false);
					btn_rpcard1.setEnabled(false);
					btn_rpcard2.setEnabled(false);
					btn_rpcard3.setEnabled(false);
					label_info.setText("");
					label_money.setText("You no longer have money.  "+"please start new game");
					}
					else {
						startGame();
						createGUI(frame);
						cardreplaced=0;
					}

				}
			}
			else if(e.getSource() == btn_rpcard1 ) {
				playerHand.set(0, deck.getCard(6));
				label_Image4.setIcon(new ImageIcon(playerHand.get(0).getCardNum()));
				cardreplaced+=1;
				if(cardreplaced==2) {
					btn_rpcard1.setEnabled(false);
					btn_rpcard2.setEnabled(false);
					btn_rpcard3.setEnabled(false);
				}
				btn_rpcard1.setEnabled(false);

			}
			else if(e.getSource() == btn_rpcard2 ) {
				playerHand.set(1, deck.getCard(7));
				label_Image5.setIcon(new ImageIcon(playerHand.get(1).getCardNum()));
				cardreplaced+=1;
				if(cardreplaced==2) {
					btn_rpcard1.setEnabled(false);
					btn_rpcard2.setEnabled(false);
					btn_rpcard3.setEnabled(false);
				}
				btn_rpcard2.setEnabled(false);

			}
			else if(e.getSource() == btn_rpcard3 ) {
				playerHand.set(2, deck.getCard(8));
				label_Image6.setIcon(new ImageIcon(playerHand.get(2).getCardNum()));
				cardreplaced+=1;
				if(cardreplaced==2) {
					btn_rpcard1.setEnabled(false);
					btn_rpcard2.setEnabled(false);
					btn_rpcard3.setEnabled(false);
				}
				btn_rpcard3.setEnabled(false);

			}

		}
	}

}
