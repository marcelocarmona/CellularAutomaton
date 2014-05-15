package ar.edu.unlp.CellularAutomaton.util;

import ar.edu.unlp.CellularAutomaton.swing.grid.GameGridModel;

/**
 * Thread that calls the next generation of the grid
 * @author mclo
 */
public class GameThread extends Thread {

	private volatile boolean done;
	private int sleepTime;
	private GameGridModel gameGridModel;

	public GameThread(GameGridModel gameGridModel, int sleepTime) {
		super();
		this.done = false;
		this.sleepTime = sleepTime;
		this.gameGridModel = gameGridModel;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void finish() {
		done = true;
	}

	public void run() {
		while (!done) {
			gameGridModel.nextGeneration();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
