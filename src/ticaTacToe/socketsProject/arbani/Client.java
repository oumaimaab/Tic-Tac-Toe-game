package ticaTacToe.socketsProject.arbani;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ticaTacToe.socketsProject.arbani.Window.Square;
import ticaTacToe.socketsProject.arbani.resources.Resources;

public class Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Tic-Tac-Toe");
	private JLabel messageLabel = new JLabel("");
	private ImageIcon icon;
	private ImageIcon opponentIcon;

	private Square[] board = new Square[9];
	private Square currentSquare = new Square();

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Resources res = new Resources();

	public Client(String ip, int port) throws Exception {

		socket = new Socket(ip, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		messageLabel.setBackground(Color.lightGray);
		frame.getContentPane().add(messageLabel, "South");

		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.black);
		boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
		for (int i = 0; i < board.length; i++) {
			final int j = i;
			board[i] = new Square();
			board[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					currentSquare = board[j];
					out.println("MOVE " + j);
				}
			});
			boardPanel.add(board[i]);
		}
		frame.getContentPane().add(boardPanel, "Center");
	}

	public void play() throws Exception {
		String response;
		res.loadImages();
		try {
			response = in.readLine();
			if (response.startsWith("WELCOME")) {
				char mark = response.charAt(8);
				icon = new ImageIcon(mark == 'X' ? Resources.getGreenX() : Resources.getGreenCircle());
				opponentIcon = new ImageIcon(mark == 'X' ? Resources.getRedCircle() : Resources.getRedX());
				frame.setTitle("Tic Tac Toe - Player " + mark);
			}
			while (true) {
				response = in.readLine();
				if (response.startsWith("VALID_MOVE")) {
					messageLabel.setText("Valid move, please wait");
					currentSquare.setIcon(icon);
					currentSquare.repaint();
				} else if (response.startsWith("OPPONENT_MOVED")) {
					int loc = Integer.parseInt(response.substring(15));
					board[loc].setIcon(opponentIcon);
					board[loc].repaint();
					messageLabel.setText("Opponent moved, your turn");
				} else if (response.startsWith("VICTORY")) {
					messageLabel.setText("You win");
					break;
				} else if (response.startsWith("DEFEAT")) {
					messageLabel.setText("You lose");
					break;
				} else if (response.startsWith("TIE")) {
					messageLabel.setText("You tied");
					break;
				} else if (response.startsWith("MESSAGE")) {
					messageLabel.setText(response.substring(8));
				}
			}
			out.println("QUIT");
		} finally {
			socket.close();
		}
	}

	private boolean wantsToPlayAgain() {
		int response = JOptionPane.showConfirmDialog(frame, "Play again?", "Click OK", JOptionPane.YES_NO_OPTION);
		frame.dispose();
		return response == JOptionPane.YES_OPTION;
	}

	public static void main(String[] args) throws Exception {
		while (true) {
			String ip = "localhost";
			int port = 22222;
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please input the IP: ");
			ip = scanner.nextLine();
			System.out.println("Please input the port: ");
			port = scanner.nextInt();
			while (port < 1 || port > 65535) {
				System.out.println("The port you entered was invalid, please input another port: ");
				port = scanner.nextInt();
			}
			Client client = new Client(ip, port);
			client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.frame.setSize(506, 527);
			client.frame.setVisible(true);
			client.frame.setResizable(false);
			client.play();
			if (!client.wantsToPlayAgain()) {
				break;
			}
		}
	}
}