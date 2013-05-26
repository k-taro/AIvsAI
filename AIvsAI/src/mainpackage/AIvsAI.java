package mainpackage;

import java.awt.EventQueue;

public class AIvsAI {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frameSetVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
