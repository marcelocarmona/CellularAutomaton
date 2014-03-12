package ar.edu.unlp.CellularAutomaton.util;

import ar.edu.unlp.CellularAutomaton.view.Window;

public class StartThread extends Thread {

	private volatile boolean done;
	private int sleepTime;
	private Window window;

	public StartThread(Window window, int sleepTime) {
		super();
		this.done = false;
		this.sleepTime = sleepTime;
		this.window = window;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void finish() {
		done = true;
	}

	@Override
	public void run() {
		while (!done) {
			window.nextGeneration();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
