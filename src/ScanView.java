import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScanView extends JFrame {
	
	public ScanView(){
		setTitle("Shop Manager");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		getContentPane().add(panel1);

		panel1.add(panel2);
		panel1.add(panel3);
		panel2.add(new JLabel("Product"));
		JTextField productTextField = new JTextField();
		productTextField.setColumns(12);
		
		panel2.add(productTextField);
		panel2.add(new JLabel("Qu"));
		panel2.add(new JTextField("1"));
		panel2.add(new JButton("4sdfdf"));
		
		panel3.add(new JLabel("To Pqy"));
		panel3.add(new JTextField("0.00 "));
		
		
	}
	public void showView(){
		setVisible(true);
		
	}
	
}
