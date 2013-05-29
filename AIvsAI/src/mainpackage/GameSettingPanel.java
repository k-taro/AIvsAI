package mainpackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public abstract class GameSettingPanel extends JPanel {

	/**
	 * よくわからんけど一応
	 */
	private static final long serialVersionUID = 1L;
	
	private int ai_n;
	
	private JTextField[] command;
	
	/**
	 * Create the panel.
	 */
	public GameSettingPanel(int ai_num) {
		this.ai_n = ai_num;

        BoxLayout boxlayout = new BoxLayout(this,BoxLayout.Y_AXIS);// this.getRootRane？
		this.setLayout(boxlayout);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		JLabel lblSelectAnExecutable = new JLabel("Select an executable file or enter the command");
		panel_2.add(lblSelectAnExecutable);
		int height = panel_2.getHeight();
		panel_2.setMaximumSize(new Dimension(Short.MAX_VALUE, height));
		this.add(panel_2);
		
		command = new JTextField[ai_n];
		for(int i = 0; i<ai_n; i++){
			JPanel panel = new JPanel();
			height = panel.getHeight();
			panel.setMaximumSize(new Dimension(Short.MAX_VALUE, height));

			panel.setLayout(new BorderLayout(0, 0));
			
			JLabel lblAi0 = new JLabel("AI " + i);
			panel.add(lblAi0, BorderLayout.WEST);
			
			command[i] = new JTextField();
			panel.add(command[i], BorderLayout.CENTER);
			command[i].setColumns(10);
			
			JButton btnOpenFile = new JButton("Open File");
			btnOpenFile.setActionCommand(String.valueOf(i));
			btnOpenFile.addActionListener(new OpenFileButtonActionListener());
			panel.add(btnOpenFile, BorderLayout.EAST);
			
			this.add(panel);
		}
		
		JPanel panel_5 = new JPanel();
		this.add(panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 76, 0, 85, 0, 0};
		gbl_panel_5.rowHeights = new int[]{29, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);

		JButton btnFight = new JButton("Fight");
		btnFight.addActionListener(new FightButtonAction());
		GridBagConstraints gbc_btnFight = new GridBagConstraints();
		gbc_btnFight.insets = new Insets(0, 0, 0, 5);
		gbc_btnFight.gridx = 1;
		gbc_btnFight.gridy = 0;
		panel_5.add(btnFight, gbc_btnFight);
		
		JButton btnReplay = new JButton("Replay");
		btnReplay.addActionListener(new ReplayButtonActionListener());
		GridBagConstraints gbc_btnReplay = new GridBagConstraints();
		gbc_btnReplay.insets = new Insets(0, 0, 0, 5);
		gbc_btnReplay.gridx = 3;
		gbc_btnReplay.gridy = 0;
		panel_5.add(btnReplay, gbc_btnReplay);
	}
	
	protected abstract void startGame(String[] com) throws IOException;
	
	class FightButtonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] com = new String[ai_n];
			
			for(int i = 0; i<ai_n; i++){
				com[i] = command[i].getText();
				if(com[i].length() == 0){
					JOptionPane.showMessageDialog(GameSettingPanel.this,//frmAiVsAi,
							"Please select your programs",
							null, JOptionPane.WARNING_MESSAGE);
				}
			}
			try{
				startGame(com);
			}catch(IOException e1){
				JOptionPane.showMessageDialog(GameSettingPanel.this,
						"AI "+ e1.getMessage() + " is wrong command or file name",
						null, JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	class ReplayButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class OpenFileButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		    JFileChooser filechooser = new JFileChooser();
		    int index = Integer.parseInt(e.getActionCommand()); 
		    int selected = filechooser.showOpenDialog(GameSettingPanel.this);
		    if (selected == JFileChooser.APPROVE_OPTION){
		      File file = filechooser.getSelectedFile();
		      command[index].setText(file.getAbsolutePath());
		    }
		}
	}
}
