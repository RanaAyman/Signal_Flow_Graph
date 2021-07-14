import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Start extends JFrame {

	private JPanel contentPane;
	private JTextField numNodesTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start frame = new Start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Start() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
		Dimension frameSize = new Dimension ( 390, 169 );
		setBounds(d.width / 2 - frameSize.width / 2, d.height / 2 - frameSize.height / 2,
				390, 169);
		setTitle("Signal Flow Graph");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 384, 150);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel errorMSM = new JLabel("");
		errorMSM.setForeground(Color.yellow);
		errorMSM.setBounds(245, 85, 100, 30);
		panel.add(errorMSM);
		
		JButton submit = new JButton("Next");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (CheckInput.ValidInt(numNodesTF.getText())) {
					int input = Integer.parseInt(numNodesTF.getText());
					if(input<2) {
						ErrorMSM error = new ErrorMSM("Minimum number of nodes is 2","");
						error.setVisible(true);
					}else {
					Data.nodesNum = input;
					Data.gains = new double[input][input];
					Graph g = new Graph();
					g.setVisible(true);
					dispose();
					}
				} else {
					errorMSM.setText("Integers only");
				}
				
			}
		});
		submit.setForeground(new Color(0, 16, 40));
		submit.setOpaque(false);
		submit.setFont(new Font("Swis721 BlkCn BT", Font.PLAIN, 16));
		submit.setBounds(148, 88, 68, 22);
		panel.add(submit);
		
		numNodesTF = new JTextField();
		numNodesTF.setForeground(new Color(0, 16, 40));
		numNodesTF.setFont(new Font("Tahoma", Font.BOLD, 14));
		numNodesTF.setBounds(238, 34, 55, 20);
		panel.add(numNodesTF);
		numNodesTF.setColumns(10);
		
		JLabel image = new JLabel("image");
		image.setBounds(0, -11, 388, 161);
		image.setIcon(new ImageIcon(Start.class.getResource("start3.jpeg")));
		panel.add(image);
	}

}
