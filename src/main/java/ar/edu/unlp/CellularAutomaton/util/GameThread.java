package ar.edu.unlp.CellularAutomaton.util;

import ar.edu.unlp.CellularAutomaton.view.GridPanel;

/**
 * Thread that calls the next generation of the grid
 * @author mclo
 */
public class GameThread extends Thread {

	private volatile boolean done;
	private int sleepTime;
	private GridPanel gridPanel;

	public GameThread(GridPanel gridPanel, int sleepTime) {
		super();
		this.done = false;
		this.sleepTime = sleepTime;
		this.gridPanel = gridPanel;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void finish() {
		done = true;
	}

	public void run() {
		while (!done) {
			gridPanel.nextGeneration();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
