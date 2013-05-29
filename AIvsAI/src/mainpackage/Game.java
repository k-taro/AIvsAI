package mainpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JTextArea;

/**
 * ゲームシステムのクラス。
 * 実際のゲームシステムはこのクラスのサブクラスを用いて実現すること。
 * サブクラスでは String配列, JTextArea を受け取るコンストラクタを実装し、このクラスの適切なコンストラクタを実行すること。
 * AIプログラムからの入力があると、gotInput()メソッドが呼び出されるためこのメソッドをオーバーライドして用いること。
 * gotInput(int num, String str) で num で受け取るAI番号は0から始まることに注意すること。
 * 外部プログラムを用いる場合は直接このクラスを使う。
 * @author Keitaro Wakabayashi
 *
 */
public class Game {
	private final Process ai[];
	private final String command[];
	private final byte AIMAX;
	private final JTextArea textarea;
	private final ReadThread readthread[];
	
	private Process game_proc;
	private ReadThread game_readthread;
	
	/**
	 * コンストラクタ
	 * 参加AIの最大値とコマンドの配列とステータス表示用のJTextArea
	 * @param max
	 * @param com
	 * @param ta
	 */
	public Game(int max, String com[], JTextArea ta){
		AIMAX = (byte)max;
		ai = new Process[AIMAX];
		command = new String[AIMAX];
		for(int i=0; i<AIMAX; i++){
			command[i] = com[i];
		}
		readthread = new ReadThread[AIMAX];
		textarea = ta;
	}
	
	/**
	 * 外部のゲームプログラムを用いる実行するときに用いるコンストラクタ
	 * @param gamecom
	 * @param max
	 * @param aicom
	 * @param ta
	 * @throws IOException
	 */
	public Game(String gamecom,int max, String aicom[], JTextArea ta) throws IOException{
		// 自分のReadThread;
		this(max,aicom,ta);
		Runtime r = Runtime.getRuntime();
		try {
			game_proc = r.exec(command);
		} catch (IOException e) {
			throw new IOException("Game");
		}
		
		game_readthread = new ReadThread((byte)0xff, game_proc.getInputStream());
		game_readthread.start();
	}
	
	/**
	 * i 番目の AI に出力
	 * @param i
	 * @param b
	 */
	protected void putsAIStream(int i, String str){
		PrintStream p = new PrintStream(ai[i].getOutputStream());
		p.println(str);
		p.flush();
		System.out.println("AI["+ i + "] < "+str);


/*		try {
			ai[i].getOutputStream().write((str+"\n").getBytes());
			ai[i].getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	/**
	 * バイト配列を全ての AI に出力
	 * @param b
	 */
	synchronized protected void putsAIStream(String str){
		for(int i=0; i<AIMAX; i++){
			putsAIStream(i,str);
		}
	}
	
	/**
	 * ステータス欄に文字列 str を表示するメソッド　改行しない
	 * @param str
	 */
	synchronized protected void printStatus(String str){
		textarea.append(str);
	}
	
	/**
	 * ステータス欄に文字列 str を表示するメソッド　改行する
	 * @param str
	 */
	synchronized protected void putsStatus(String str){
		textarea.append(str + "\n");
	}
	
	/**
	 * 開始前に呼び出される
	 * オーバーライドするとよい
	 * @throws IOException
	 */
	public void start() throws IOException{
		startAI();
	}
	
	/**
	 * AIのプログラムを開始するメソッド
	 * ユーザがコマンドが間違って入力した場合は IOException が throw される
	 * try, catch でそのままさらにコンストラクタを呼び出したクラスに throw するとウィンドウにダイアログが表示される 
	 * @throws IOException 
	 */
	protected void startAI() throws IOException{
		Runtime r = Runtime.getRuntime();
		for(int i=0; i<AIMAX; i++){
			try {
				ai[i] = r.exec(command[i]);
			} catch (IOException e) {
				throw new IOException(String.format("%d", i));
			}
			readthread[i] = new ReadThread((byte)i, ai[i].getInputStream());
			readthread[i].start();
		}
	}
	
	/**
	 * ゲーム終了時にプロセスを終了させるためのメソッド
	 * 終了時には必ず実行すること
	 */
	protected void end(){
		for(int i=0;i<AIMAX;i++){
			readthread[i].halt();
			try {
				ai[i].getInputStream().close();
				ai[i].getOutputStream().close();
				ai[i].getErrorStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ai[i].destroy();
		}
		if(game_proc != null){
			game_readthread.halt();
			try {
				game_proc.getInputStream().close();
				game_proc.getOutputStream().close();
				game_proc.getErrorStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			game_proc.destroy();
		}
	}
	
	/**
	 * num 番目の AI からの入力を受け付けたときに呼び出されるメソッド
	 * num は 0 から始まる通し番号
	 * オーバーライドすること
	 * @param num
	 * @param str
	 */
	protected void gotInput(int num, String str){
//		System.out.print(str + "\n");
		/*
		try {
			System.out.write((str+"\n").getBytes());
			System.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		try {
			String com;
			if((byte)num == (byte)0xff){
				Scanner scan = new Scanner(str);
				if(scan.next().equals("AI"))	{
					if(scan.hasNext()) {
						String s = scan.next();
						int i = Integer.parseInt(s);
						if(0<=i || i<AIMAX || scan.hasNext()) {
							if(scan.next().equals("<")) {
								com = scan.nextLine();
								putsAIStream(i,com);
							}else {scan.close();return;}
						}else {scan.close();return;}
					}else {scan.close();return;}
				}else {scan.close();return;}
				putsStatus(com);
				scan.close();
			}else{
				com = "AI " + num + " > " + str;
				game_proc.getOutputStream().write(com.getBytes());
				game_proc.getOutputStream().flush();
				putsStatus(com);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * InputStream を監視するスレッド
	 */
	private class ReadThread extends Thread{
		private InputStream input;
		private Scanner scanner;
		private boolean halt_; // スレッドを中止させるための変数
		private byte mynumber;
		
		public ReadThread(byte n, InputStream in){
			super();
			halt_ = false;
			mynumber = n;
			input = in;
			scanner = new Scanner(input);
		}
		public void run(){
			while(!halt_){
				if(scanner.hasNextLine()){
					String str = scanner.nextLine();
					gotInput(mynumber, str);					
				}
			}
		}
		public void halt(){
			halt_ = true;
			interrupt();
		}
	}
	
}