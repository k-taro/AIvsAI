package mainpackage;

import javax.swing.JTextArea;

public class Display {
	private JTextArea textarea;
	
	public Display(JTextArea ta){
		textarea = ta;
	}
	
	public void print(String str){
		textarea.append(str);
	}
	public void puts(String str){
		textarea.append(str + "\n");
	}
}
