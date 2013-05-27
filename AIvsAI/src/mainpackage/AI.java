package mainpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//import mainpackage.Game.ReadThread;

/**
 * 今のところ使わない
 * @author Keitaro Wakabayashi
 *
 */
public class AI {
	private String command;
	private InputStream input;
	private OutputStream output;
	
	
	public AI(String command){
		this.command = command;
	}
	
	/**
	 * AI の プログラムを実行する
	 * @throws IOException
	 */
	public void start() throws IOException{
		Runtime r = Runtime.getRuntime();
		Process process;
		try {
			process = r.exec(command);
		} catch (IOException e) {
			throw e;
		}
//		readthread[i] = new ReadThread((byte)i, ai[i].getInputStream());
//		readthread[i].start();
		input = process.getInputStream();
		output = process.getOutputStream();
	}
	
	public void print(String str){
		try {
			output.write(str.getBytes());
			output.flush();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
