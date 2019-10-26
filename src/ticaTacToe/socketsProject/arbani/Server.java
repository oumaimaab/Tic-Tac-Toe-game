package ticaTacToe.socketsProject.arbani;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;

import ticaTacToe.socketsProject.arbani.Window.Game;

public class Server implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String ip = "localhost";
	private static int port = 22222;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		System.out.println("Please input the IP: ");
		ip = scanner.nextLine();
		System.out.println("Please input the port: ");
		port = scanner.nextInt();
		while (port < 1 || port > 65535) {
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}
		ServerSocket serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		System.out.println("Tic Tac Toe Server is Running");
		try {
			while (true) {
				Game game = new Game();
				Game.Player playerX = game.new Player(serverSocket.accept(), 'X');
				Game.Player playerO = game.new Player(serverSocket.accept(), 'O');
				playerX.setOpponent(playerO);
				playerO.setOpponent(playerX);
				game.setCurrentPlayer(playerX);
				playerX.start();
				playerO.start();
			}
		} finally {
			serverSocket.close();
		}
	}
}
