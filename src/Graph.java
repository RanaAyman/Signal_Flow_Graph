import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Graph extends JFrame {//
	private int width, height;
	private JLabel drawFromL;
	private JLabel drawToL;
	private JLabel gainL;
	private JTextField drawFromT;
	private JTextField drawToT;
	private JTextField gainT;
	private JPanel drawCanv;
	private JButton next_btn, solve_btn, clear_btn;

	Graph() {
		initialize();
	}

	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.setSize(1350, 750);              // make screen size fixed 
		width = (int) screenSize.getWidth() - 120;
		height = (int) screenSize.getHeight() - 120;
		Data.width = width;
		Data.height = height;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2, width, height);
		setTitle("Signal FLow Graph Solver");
		setLayout(null);
		setResizable(false);
		this.getContentPane().setBackground(new Color(0, 16, 40));

		
		drawFromL = new JLabel("From node");
		drawFromL.setBounds(20, 20, 160, 50);
		drawFromL.setForeground(Color.WHITE);
		drawFromT = new JTextField();
		drawFromT.setBounds(140, 30, 40, 40);

		drawToL = new JLabel("To node");
		drawToL.setBounds(20, 90, 160, 50);
		drawToL.setForeground(Color.WHITE);
		drawToT = new JTextField();
		drawToT.setBounds(140, 100, 40, 40);

		gainL = new JLabel("Gain");
		gainL.setBounds(20, 160, 160, 50);
		gainL.setForeground(Color.WHITE);
		gainT = new JTextField();
		gainT.setBounds(140, 170, 40, 40);

		drawCanv = new Drawer();
		drawCanv.setBounds(190, 0, width - 190, height);
		drawCanv.setBackground(Color.WHITE);

		next_btn = new JButton("Next");
		next_btn.setBounds(45, 240, 100, 30);
		solve_btn = new JButton("Solve");
		solve_btn.setBounds(45, height - 150, 100, 40);

		clear_btn = new JButton("Clear");
		clear_btn.setBounds(45, 290, 100, 30);

		Font font = new Font("Tahoma", Font.BOLD, 20);
		Font font2 = new Font("Tahoma", Font.BOLD, 18);
		drawFromL.setFont(font);
		drawFromT.setFont(font);
		drawToL.setFont(font);
		drawToT.setFont(font);
		gainL.setFont(font);
		gainT.setFont(font);
		next_btn.setFont(font2);
		solve_btn.setFont(font);
		clear_btn.setFont(font2);

		getContentPane().add(drawFromL);
		getContentPane().add(drawFromT);
		getContentPane().add(drawToL);
		getContentPane().add(drawToT);
		getContentPane().add(gainL);
		getContentPane().add(gainT);
		getContentPane().add(drawCanv);

		getContentPane().add(next_btn);
		getContentPane().add(solve_btn);
		getContentPane().add(clear_btn);

		next_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!CheckInput.ValidInt(drawFromT.getText()) || !CheckInput.ValidInt(drawToT.getText())) {
					ErrorMSM error = new ErrorMSM("Invalid integer!","Enter the number of the required node.");
					error.setVisible(true);
				} else if (!CheckInput.ValidDouble(gainT.getText())) {
					ErrorMSM error = new ErrorMSM("Invalid gain value!","Enter numbers only");
					error.setVisible(true);
				} else {
					int from = Integer.parseInt(drawFromT.getText());
					int to = Integer.parseInt(drawToT.getText());
					if (from > Data.nodesNum || to > Data.nodesNum || from < 1 || to < 1) {
						ErrorMSM error = new ErrorMSM(
								"Invalid node number!","Enter any number in range [1:" + Data.nodesNum + "]");
						error.setVisible(true);
					} else if (from == Data.nodesNum) {
						ErrorMSM error = new ErrorMSM("Feedback isn't allowed from node "+ from,"");
						error.setVisible(true);
					} else if (to == 1) {
						ErrorMSM error = new ErrorMSM("Feedback isn't allowed to node 1","");
						error.setVisible(true);
					} else {
						double gain = Double.parseDouble(gainT.getText());
						Data.gains[from - 1][to - 1] = gain;
						drawCanv.repaint();
						drawFromT.setText("");
						drawToT.setText("");
						gainT.setText("");
					}
				}
			}
		});

		clear_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!CheckInput.ValidInt(drawFromT.getText()) || !CheckInput.ValidInt(drawToT.getText())) {
					ErrorMSM error = new ErrorMSM("Invalid integer!","Enter the number of the required node.");
					error.setVisible(true);
				} else {
					int from = Integer.parseInt(drawFromT.getText());
					int to = Integer.parseInt(drawToT.getText());
					if (from > Data.nodesNum || to > Data.nodesNum || from < 1 || to < 1) {
						ErrorMSM error = new ErrorMSM(
								"Invalid node number!","Enter any number in range [1:" + Data.nodesNum + "]");
						error.setVisible(true);
					} else if (Data.gains[from - 1][to - 1] == 0) {
						ErrorMSM error = new ErrorMSM("Already Clear!",""); // It hasn't been set yet
						error.setVisible(true);
					} else {
						Data.gains[from - 1][to - 1] = 0;
						drawCanv.repaint();
						drawFromT.setText("");
						drawToT.setText("");
						gainT.setText("");
					}
				}
			}
		});

		solve_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Solver solve = new Solver(Data.gains);
				// TO DO
				Data.forwardPaths = solve.getFPs();
				Data.loops = solve.getLoops();
				Data.nonTouchingloops = solve.getNTLs();
				Data.overAllTF = solve.getResult();
				Data.loopsGain = solve.getLoopGains();
				Data.forwardPathsGain = solve.getFPGains();
				Data.nonTouchingloopsGain = solve.getNTLGains();
				Data.deltas = solve.getDeltas();
				DisplayResults results = new DisplayResults();
				results.setVisible(true);
			}
		});
	}
}
