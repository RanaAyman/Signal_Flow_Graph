import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class DisplayResults extends JFrame {

	private static final long serialVersionUID = 1L;

	DisplayResults() {
		initialize();
	}

	private void initialize() {
		JLabel loopsLabel, FPLabel,DeltasLabel, tfLabel, nontouchLabel, loopsGainLabel, FPGainLabel, nonTouchGainLabel,tfLabel_body;

		JTextPane loopsLabel_body,DeltasLabel_body, FPLabel_body, nontouchLabel_body, loopsGainLabel_body, FPGainLabel_body, nonTouchGainLabel_body;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.setSize(1350, 750);           // make screen size fixed 
		int width = (int) screenSize.getWidth() - 120;
		int height = (int) screenSize.getHeight() - 120;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2, width, height);
		setTitle("Signal FLow Graph - Results");
		setLayout(null);
		setResizable(false);
		this.getContentPane().setBackground(new Color(0, 16, 40));

		FPLabel = new JLabel("Forward Paths");
		FPGainLabel = new JLabel("FPs Gains");
		FPGainLabel_body = new JTextPane();
		FPLabel_body = new JTextPane();
		FPGainLabel_body.setBackground(new Color(211, 229, 246));

		DeltasLabel = new JLabel("Deltas");
		DeltasLabel_body = new JTextPane();
		DeltasLabel_body.setBackground(new Color(247, 247, 247));
		
		loopsLabel = new JLabel("Loops");
		loopsGainLabel = new JLabel("Loops Gains");
		loopsGainLabel_body = new JTextPane();
		loopsLabel_body = new JTextPane();
		loopsGainLabel_body.setBackground(new Color(211, 229, 246));
		
		nontouchLabel = new JLabel("Non Touching Loops");
		nonTouchGainLabel = new JLabel("Non TLs Gains");
		nonTouchGainLabel_body = new JTextPane();
		nontouchLabel_body = new JTextPane();
		nonTouchGainLabel_body.setBackground(new Color(211, 229, 246));

		tfLabel = new JLabel("Transfer Function = ");
		tfLabel_body = new JLabel();

		FPLabel.setBounds(10, 0, 200, 40);
		FPLabel_body.setBounds(10, 40, 200, height - 150);
		FPGainLabel.setBounds(210, 0, 150, 40);
		FPGainLabel_body.setBounds(210, 40, 150, height - 150);

		DeltasLabel.setBounds(360, 0, 140, 40);
		DeltasLabel_body.setBounds(360, 40, 140, height - 150);
		
		loopsLabel.setBounds(500, 0, 200, 40);
		loopsLabel_body.setBounds(500, 40, 200, height - 150);
		loopsGainLabel.setBounds(700, 0, 150, 40);
		loopsGainLabel_body.setBounds(700, 40, 150, height - 150);

		nontouchLabel.setBounds(850, 0, 200, 40);
		nontouchLabel_body.setBounds(850, 40, 200, height - 150);
		nonTouchGainLabel.setBounds(1050, 0, 150, 40);
		nonTouchGainLabel_body.setBounds(1050, 40, 150, height - 150);

		tfLabel.setBounds(20, height - 100, 250, 40);
		tfLabel_body.setBounds(270, height - 100, 400, 40);
		Font font = new Font("Serif", Font.BOLD, 28);
		tfLabel.setFont(font);
		tfLabel.setForeground(Color.WHITE);
		tfLabel_body.setFont(font);
		tfLabel_body.setForeground(Color.WHITE);

		font = new Font("Serif", Font.BOLD, 18);

		loopsLabel.setFont(font);
		loopsLabel.setForeground(Color.WHITE);
		FPLabel.setFont(font);
		FPLabel.setForeground(Color.WHITE);
		DeltasLabel.setFont(font);
		DeltasLabel.setForeground(Color.WHITE);
		nontouchLabel.setFont(font);
		nontouchLabel.setForeground(Color.WHITE);
		loopsGainLabel.setFont(font);
		loopsGainLabel.setForeground(Color.WHITE);
		FPGainLabel.setFont(font);
		FPGainLabel.setForeground(Color.WHITE);
		nonTouchGainLabel.setFont(font);
		nonTouchGainLabel.setForeground(Color.WHITE);
		

		getContentPane().add(loopsLabel);
		getContentPane().add(FPLabel);
		getContentPane().add(DeltasLabel);
		getContentPane().add(tfLabel);
		getContentPane().add(nontouchLabel);
		getContentPane().add(loopsGainLabel);
		getContentPane().add(FPGainLabel);
		getContentPane().add(nonTouchGainLabel);

		getContentPane().add(loopsLabel_body);
		getContentPane().add(FPLabel_body);
		getContentPane().add(DeltasLabel_body);
		getContentPane().add(tfLabel_body);
		getContentPane().add(nontouchLabel_body);
		getContentPane().add(loopsGainLabel_body);
		getContentPane().add(FPGainLabel_body);
		getContentPane().add(nonTouchGainLabel_body);


		StringBuilder sb = new StringBuilder();
		sb.append("<font size=\"5\">");
		String[] tempArr = Data.forwardPaths;
		for (int i = 0; i < tempArr.length; i++) 
			sb.append((i + 1) + ") " + tempArr[i] + "<br>");
		sb.append("</font>");

		FPLabel_body.setContentType("text/html");
		FPLabel_body.setEditable(false);
		FPLabel_body.setText(sb.toString());
		FPLabel_body.setForeground(Color.blue);
		
		sb = new StringBuilder();
		sb.append("<font size=\"5\">");
		tempArr = Data.loops;
		for (int i = 0; i < tempArr.length; i++) 
			sb.append((i + 1) + ") " + tempArr[i] + "<br>");
		sb.append("</font>");
		loopsLabel_body.setContentType("text/html");
		loopsLabel_body.setEditable(false);
		loopsLabel_body.setText(sb.toString());

		sb = new StringBuilder();
		sb.append("<font size=\"5\">");
		tempArr = Data.nonTouchingloops;
		for (int i = 0; i < tempArr.length; i++) 
			sb.append((i + 1) + ") " + tempArr[i] + "<br>");
		sb.append("</font>");
		
		nontouchLabel_body.setContentType("text/html");
		nontouchLabel_body.setEditable(false);
		nontouchLabel_body.setText(sb.toString());
		
		sb = new StringBuilder();
		sb.append("<font size=\"5\">");
		Double [] tempDou = Data.forwardPathsGain;
		for (int i = 0; i < tempDou.length; i++) 
			sb.append((i + 1) + ") " + tempDou[i].doubleValue() + "<br>");
		sb.append("</font>");

		FPGainLabel_body.setContentType("text/html");
		FPGainLabel_body.setEditable(false);
		FPGainLabel_body.setText(sb.toString());
		
		//////////////////////////////////////
		sb = new StringBuilder();
		sb.append("<font size=\"5\">");
		Double [] tempDelta = Data.deltas;
		for (int i = 0; i < tempDelta.length; i++) 
			sb.append((i + 1) + ") " + tempDelta[i].doubleValue() + "<br>");
		sb.append("</font>");

		DeltasLabel_body.setContentType("text/html");
		DeltasLabel_body.setEditable(false);
		DeltasLabel_body.setText(sb.toString());
		//////////////////////////////////////
		
		sb = new StringBuilder();
		sb.append("<font size=\"5\">");
		tempDou = Data.loopsGain;
		for (int i = 0; i < tempDou.length; i++) 
			sb.append((i + 1) + ") " + tempDou[i].doubleValue() + "<br>");
		sb.append("</font>");

		loopsGainLabel_body.setContentType("text/html");
		loopsGainLabel_body.setEditable(false);
		loopsGainLabel_body.setText(sb.toString());
		
		sb = new StringBuilder();
		sb.append("<font size=\"5\">");
		tempDou = Data.nonTouchingloopsGain;
		for (int i = 0; i < tempDou.length; i++) 
			sb.append((i + 1) + ") " + tempDou[i].doubleValue() + "<br>");
		sb.append("</font>");
	
		nonTouchGainLabel_body.setContentType("text/html");
		nonTouchGainLabel_body.setEditable(false);
		nonTouchGainLabel_body.setText(sb.toString());
		
		tfLabel_body.setText(Data.overAllTF + "");

	}
}
