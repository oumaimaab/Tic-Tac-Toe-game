package ticaTacToe.socketsProject.arbani.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {
	private static BufferedImage board;
	private static BufferedImage redX;
	private static BufferedImage greenX;
	private static BufferedImage redCircle;
	private static BufferedImage greenCircle;
	
	public void loadImages() {
		try {
			board = ImageIO.read(new File("board.png"));
			redX = ImageIO.read(new File("redX.png"));
			redCircle = ImageIO.read(new File("redCircle.png"));
			greenX = ImageIO.read(new File("greenX.png"));
			greenCircle = ImageIO.read(new File("greenCircle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getBoard() {
		return board;
	}

	public static void setBoard(BufferedImage board) {
		Resources.board = board;
	}

	public static BufferedImage getRedX() {
		return redX;
	}

	public static void setRedX(BufferedImage redX) {
		Resources.redX = redX;
	}

	public static BufferedImage getGreenX() {
		return greenX;
	}

	public static void setGreenX(BufferedImage greenX) {
		Resources.greenX = greenX;
	}

	public static BufferedImage getRedCircle() {
		return redCircle;
	}

	public static void setRedCircle(BufferedImage redCircle) {
		Resources.redCircle = redCircle;
	}

	public static BufferedImage getGreenCircle() {
		return greenCircle;
	}

	public static void setGreenCircle(BufferedImage greenCircle) {
		Resources.greenCircle = greenCircle;
	}
}
