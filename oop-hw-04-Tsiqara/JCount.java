// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private static final int UPDATE_AFTER_ITERATIONS = 10000;
	private JTextField goal;
	private JLabel current;
	private JButton startButton, stopButton;
	private Worker worker;
	public JCount() {
		super();
		worker = null;
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		goal = new JTextField("100000000", 10);
		add(goal);
		current = new JLabel("0");
		add(current);
		startButton = new JButton("Start");
		add(startButton);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null){
					worker.interrupt();
				}

				worker = new Worker(Integer.parseInt(goal.getText()));
				worker.start();
			}
		});
		stopButton = new JButton("Stop");
		add(stopButton);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null){
					worker.interrupt();
					worker = null;
				}
			}
		});
		
		add(Box.createRigidArea(new Dimension(0,40)));
	}

	private class Worker extends Thread{
		private int goalNumber;
		public Worker(int num){
			goalNumber = num;
		}
		@Override
		public void run(){
			for(int i = 0; i <= goalNumber; i ++){
				if(i % UPDATE_AFTER_ITERATIONS == 0){
					try {
						sleep(100);
					} catch (InterruptedException e) {
						break;
					}
					final String value = "" + i;
					if(isInterrupted()){
						break;
					}
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							current.setText(value);
						}
					});
				}
				if(isInterrupted()){
					break;
				}
			}
		}
	}


	private static void createAndShowGUI() {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void main(String[] args) { SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			createAndShowGUI();
		} });
	}

//	static public void main(String[] args)  {
//		// Creates a frame with 4 JCounts in it.
//		// (provided)
//		JFrame frame = new JFrame("The Count");
//		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
//
//		frame.add(new JCount());
//		frame.add(new JCount());
//		frame.add(new JCount());
//		frame.add(new JCount());
//
//		frame.pack();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//	}
}

