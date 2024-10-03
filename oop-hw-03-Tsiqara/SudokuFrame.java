import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	 private JTextArea source, result;
	 Box box;
	 private JButton checkButton;
	 private JCheckBox autoCheckBox;
	
	public SudokuFrame() {
		super("Sudoku Solver");
		setLayout(new BorderLayout(4, 4));

		source = new JTextArea(15, 20);
		result = new JTextArea(15, 20);

		source.setBorder(new TitledBorder("Puzzle"));
		source.setLineWrap(true);
		add(source, BorderLayout.CENTER);
		result.setBorder(new TitledBorder("Solution"));
		result.setEditable(false);
		add(result, BorderLayout.EAST);

		box = Box.createHorizontalBox();

		checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Check();
			}
		});

		autoCheckBox = new JCheckBox("Auto Check");
		autoCheckBox.setSelected(true);
		box.add(checkButton);
		box.add(autoCheckBox);

		add(box, BorderLayout.SOUTH);

		source.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(autoCheckBox.isSelected()){
					Check();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(autoCheckBox.isSelected()){
					Check();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(autoCheckBox.isSelected()){
					Check();
				}
			}
		});
		
		// Could do this:
//		 setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private void Check(){
		StringBuilder solution;
		try {
			Sudoku sudoku = new Sudoku(source.getText());
			int solutions = sudoku.solve();
			if(solutions >= 1){
				solution = new StringBuilder(sudoku.getSolutionText());
				solution.append("solutions:" + solutions + "\n");
				solution.append("elapsed:" + sudoku.getElapsed() + "ms" + "\n");

				result.setText(solution.toString());
			}
		}catch (Exception ex){
			result.setText("Parsing Problem");
		}
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
