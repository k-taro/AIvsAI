package reversi;

import java.io.IOException;

import javax.swing.JTextArea;

import mainpackage.GameSettingPanel;

public class ReversiSettingPanel extends GameSettingPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextArea textArea;
	
	public ReversiSettingPanel(JTextArea ta){
		super(2);
		textArea = ta;
	}
	@Override
	protected void startGame(String[] com) throws IOException {
		new Reversi(com, textArea).start();		
	}
}
