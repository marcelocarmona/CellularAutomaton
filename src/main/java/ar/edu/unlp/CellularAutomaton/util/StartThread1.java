package ar.edu.unlp.CellularAutomaton.util;

import java.util.concurrent.CyclicBarrier;

import ar.edu.unlp.CellularAutomaton.view.Window;

public class StartThread1 extends Thread {

	private volatile boolean done;
	private int sleepTime;
	private Window window;

	public StartThread1(Window window, int sleepTime) {
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
		final CyclicBarrier countAliveNeighborsBarrier = new CyclicBarrier(2);
		final CyclicBarrier transitionFunctionBarrier = new CyclicBarrier(2);
		
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