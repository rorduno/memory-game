import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class MemoryGame extends JFrame {
	private static int n;
	private static int k;
	private JButton[][] board;
	private JButton jbtGo;
	private boolean gridMarked[][];
	private Timer timer;
	int winner = 0;

	// constructor
	public MemoryGame() {

		String sizeString = JOptionPane
				.showInputDialog("Enter a value for size");
		n = Integer.parseInt(sizeString);

		String needString = JOptionPane
				.showInputDialog("Enter a value for need");
		k = Integer.parseInt(needString);

		while (k > (n * n)) {
			needString = JOptionPane
					.showInputDialog("Enter a value less or equal to size * size");
			k = Integer.parseInt(needString);
		}

		JPanel panel = new JPanel(new GridLayout(n, n));
		board = new JButton[n][n];
	
		gridMarked = new boolean[n][n];
		BtnListener btnListener = new BtnListener();
		// create buttons
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				
				board[i][j] = new JButton(i + "," + j);
				board[i][j].setEnabled(false);
				board[i][j].setBackground(Color.LIGHT_GRAY);
				board[i][j].setPreferredSize(new Dimension(200, 100));
				board[i][j].addActionListener(btnListener);
				gridMarked[i][j] = false;

				panel.add(board[i][j]);
			}
		}
		jbtGo = new JButton("GO");
		jbtGo.setPreferredSize(new Dimension(200, 100));

		GoBtnListener goBtnListener = new GoBtnListener();
		jbtGo.addActionListener(goBtnListener);

		JPanel panel2 = new JPanel();
		panel2.add(jbtGo);

		add(panel);
		add(panel2, BorderLayout.SOUTH);

		pack();

		timer = new Timer(2000, new TimerListener());// will show for two
														// seconds

	}// end of constructor

	class BtnListener implements ActionListener {
		@Override
		// once you hit wrong button game is over
		public void actionPerformed(ActionEvent e) {

			// Color.RED; if not green
			// Color.CYAN; after game is over
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					if (e.getSource() == board[i][j])
						if (gridMarked[i][j])// if it has been marked
						{
							board[i][j].setBackground(Color.GREEN);
							board[i][j].removeActionListener(this);// removes
																	// listener
																	// from jbt
							gridMarked[i][j] = false;
							winner++;
							if (winner == k) {
								JOptionPane.showMessageDialog(null, "Winner!!");
								System.exit(0);
							}
						}

						else if (!gridMarked[i][j])// if its not green
						{
							board[i][j].setBackground(Color.RED);
							showCyan(); // call showCyan
							JOptionPane.showMessageDialog(null,
									"You lose!! Game Over!!");
							System.exit(0);
						}

				}

		}
	}

	public void showCyan() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (gridMarked[i][j])
					board[i][j].setBackground(Color.CYAN);

	}

	class GoBtnListener implements ActionListener {// this will generate the
													// random green buttons
		@Override
		public void actionPerformed(ActionEvent e) {
			// pause for 1 second, turn green for 2 seconds then turn gray again
			
			//Enable the buttons
			for(int i=0;i<board.length;i++)
				for(int j=0;j<board.length;j++){
					board[i][j].setEnabled(true);
				}
			

			timer.start();
			jbtGo.setEnabled(false);//Disable go btn
			int counter = 0;

			Random rnd = new Random();
			// timer goes in here
			int x = rnd.nextInt(n);// generates numbers
			int y = rnd.nextInt(n);
			while (counter < k) {
				x = rnd.nextInt(n);// generates numbers
				y = rnd.nextInt(n);

				// search for duplicates

				while (board[x][y].getBackground() != Color.GREEN) {
					board[x][y].setBackground(Color.GREEN);// creates random
															// coordinates of
															// green jbt
					gridMarked[x][y] = true;// mark it
					counter++;
				}
			}

		}
	}

	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// show the green buttons for 2 seconds
			// redarkens
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {

					board[i][j].setBackground(Color.LIGHT_GRAY);
				}
			timer.stop();

		}
	}

	// enter size:
	// enter how many buttons to remember:
	// prints true if you hit right one
	// uses btnlistener, gobtnlistener,timerlistener
	// use exceptions
	public static void main(String[] args) {

		JFrame frame = new MemoryGame();
		frame.setTitle("MemoryGame");
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}