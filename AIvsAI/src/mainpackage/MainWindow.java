package mainpackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class MainWindow {

	protected JFrame frmAiVsAi;
	private JTextField ai0command;
	private JTextField ai1command;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	public void frameSetVisible(boolean b) {
		frmAiVsAi.setVisible(b);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAiVsAi = new JFrame();
		frmAiVsAi.setTitle("AI vs AI");
		frmAiVsAi.setBounds(100, 100, 500, 400);
		frmAiVsAi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblAivsai = new JLabel("AI vs AI");
		lblAivsai.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblAivsai.setHorizontalAlignment(SwingConstants.CENTER);
		frmAiVsAi.getContentPane().add(lblAivsai, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frmAiVsAi.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{15, 0, 15, 0};
		gbl_panel.rowHeights = new int[]{10, 130, 15, 130, 10, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{189, 0};
		gbl_panel_1.rowHeights = new int[]{25, 35, 30, 30, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblSelectAnExecutable = new JLabel("Select an executable file or enter the command");
		GridBagConstraints gbc_lblSelectAnExecutable = new GridBagConstraints();
		gbc_lblSelectAnExecutable.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSelectAnExecutable.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectAnExecutable.gridx = 0;
		gbc_lblSelectAnExecutable.gridy = 0;
		panel_1.add(lblSelectAnExecutable, gbc_lblSelectAnExecutable);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		panel_1.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAi0 = new JLabel("AI 0");
		panel_3.add(lblAi0, BorderLayout.WEST);
		
		ai0command = new JTextField();
		panel_3.add(ai0command, BorderLayout.CENTER);
		ai0command.setColumns(10);
		
		JButton btnOpenFile0 = new JButton("Open File");
		btnOpenFile0.addActionListener(new OpenFileButton1ActionListener());
		panel_3.add(btnOpenFile0, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 2;
		panel_1.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JButton btnOpenFile1 = new JButton("Open File");
		btnOpenFile1.addActionListener(new OpenFileButton2ActionListener());
		panel_4.add(btnOpenFile1, BorderLayout.EAST);
		
		JLabel lblAi1 = new JLabel("AI 1");
		panel_4.add(lblAi1, BorderLayout.WEST);
		
		ai1command = new JTextField();
		panel_4.add(ai1command);
		ai1command.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 3;
		panel_1.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 76, 0, 85, 0, 0};
		gbl_panel_5.rowHeights = new int[]{29, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);

		//
		
		JButton btnReplay = new JButton("Replay");
		btnReplay.addActionListener(new ReplayButtonActionListener());
		GridBagConstraints gbc_btnReplay = new GridBagConstraints();
		gbc_btnReplay.insets = new Insets(0, 0, 0, 5);
		gbc_btnReplay.gridx = 3;
		gbc_btnReplay.gridy = 0;
		panel_5.add(btnReplay, gbc_btnReplay);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 3;
		panel.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{442, 0};
		gbl_panel_2.rowHeights = new int[]{25, 16, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblExecutionStatus = new JLabel("Execution Status");
		GridBagConstraints gbc_lblExecutionStatus = new GridBagConstraints();
		gbc_lblExecutionStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblExecutionStatus.insets = new Insets(0, 0, 5, 0);
		gbc_lblExecutionStatus.gridx = 0;
		gbc_lblExecutionStatus.gridy = 0;
		panel_2.add(lblExecutionStatus, gbc_lblExecutionStatus);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_2.add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(1);
		textArea.setTabSize(4);
		textArea.setColumns(1);
		scrollPane.setViewportView(textArea);
		
		JButton btnFight = new JButton("Fight");
		btnFight.addActionListener(new FightButtonAction(textArea));
		GridBagConstraints gbc_btnFight = new GridBagConstraints();
		gbc_btnFight.insets = new Insets(0, 0, 0, 5);
		gbc_btnFight.gridx = 1;
		gbc_btnFight.gridy = 0;
		panel_5.add(btnFight, gbc_btnFight);
	}
	
	class FightButtonAction implements ActionListener{
		JTextArea textarea;
		public FightButtonAction(JTextArea ta){
			textarea = ta;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] com = new String[2];
			com[0] = ai0command.getText();
			com[1] = ai1command.getText();
			if(com[0].length() == 0 || com[1].length() == 0){
				JOptionPane.showMessageDialog(frmAiVsAi,
						"Please select your programs",
						null, JOptionPane.WARNING_MESSAGE);
			}else{
				try{
					//new Executer(textarea).execute("/Users/can8anyone4hear8me1/Documents/program/game", 2, com);
					new Executer(textarea).execute(2, com);
				}catch(IOException e1){
					JOptionPane.showMessageDialog(frmAiVsAi,
							"AI "+ e1.getMessage() +	" is wrong command or file name",
							null, JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	class ReplayButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class OpenFileButton1ActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		    JFileChooser filechooser = new JFileChooser();

		    int selected = filechooser.showOpenDialog(frmAiVsAi);
		    if (selected == JFileChooser.APPROVE_OPTION){
		      File file = filechooser.getSelectedFile();
		      ai0command.setText(file.getAbsolutePath());
		    }
		}
	}
	class OpenFileButton2ActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		    JFileChooser filechooser = new JFileChooser();

		    int selected = filechooser.showOpenDialog(frmAiVsAi);
		    if (selected == JFileChooser.APPROVE_OPTION){
		      File file = filechooser.getSelectedFile();
		      ai1command.setText(file.getAbsolutePath());
		    }
		}
	}
	
}
