package com.bit2015.multithread;

public class AlphabetRunnableImpl implements Runnable {

	@Override
	public void run() {
		for (char i = 'A'; i < 'Z'; i++) {
			System.out.print(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
