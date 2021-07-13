import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class ErrorMSM extends JFrame {

	private JPanel contentPane;
	
	ErrorMSM(String MSM,String MSM2) {
		initialize(MSM,MSM2);
	}

	/**
	 * Create the frame.
	 */
	private void initialize(String MSM ,String MSM2) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
		Dimension frameSize = new Dimension ( 370, 149 );
		setBounds(d.width / 2 - frameSize.width / 2, d.height / 2 - frameSize.height / 2, 370, 149);
		setTitle("Error Message!");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 384, 161);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label2 = new JLabel("");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label2.setForeground(Color.WHITE);
		label2.setBounds(31, 59, 318, 30);
		panel.add(label2);
		
		JLabel label1 = new JLabel(MSM);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label1.setForeground(Color.WHITE);
		label1.setBounds(31, 40, 318, 23);
		panel.add(label1);
		
		JLabel labelTitle = new JLabel("Error");
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		labelTitle.setForeground(Color.YELLOW);
		labelTitle.setBounds(10, 0, 74, 41);
		panel.add(labelTitle);
		
		JLabel imageError = new JLabel("imageError");
		imageError.setIcon(new ImageIcon(ErrorMSM.class.getResource("1.jpg")));
		imageError.setBounds(0, 0, 384, 161);
		panel.add(imageError);
		
		if(!MSM.isEmpty()) {
			label2 = new JLabel(MSM2);
			label2.setBounds(10,60, 400, 30);
			getContentPane().add(label2);
		}
	}

}
