package mainpackage;

import java.io.IOException;
import javax.swing.JTextArea;

import puyopuyo.PuyoPuyo;
import reversi.Reversi;

/**
 * @author KeitaroWakabayashi
 * AIプログラムを実行するクラス
 */
public class Executer {
	private JTextArea textarea;
	private Game game;
	
	// コンストラクタ
	private Executer(){
		
	}
	// コンストラクタ
	public Executer(JTextArea ta){
		this();
		textarea = ta;
	}
	
	/**
	 * AIプログラムを実行するコマンドを引数に渡して対戦開始
	 * @param num AIの数
	 * @param com AIのコマンドを持つ String 配列
	 * @throws IOException
	 */
	public void execute(int num, String com[]) throws IOException{

		/*		
		Runtime r = Runtime.getRuntime();
		InputStream in[] = new InputStream[num];
		OutputStream out[] = new OutputStream[num];
		proc_ai = new Process[num];

		int i = -1;
		try {
			for(i=0; i<num; i++){
				proc_ai[i] = r.exec(com[i]);
				in[i] = proc_ai[i].getInputStream();
				out[i] = proc_ai[i].getOutputStream();
			}
		} catch (IOException e1) {
			throw new IOException(String.format("%d", i+1));
		}
		*/
		
		//game = new Game(in, out, textarea);
		//game = new PuyoPuyo(com,textarea);
		game = new Reversi(com,textarea);
		game.start();
	}
	
	/**
	 * 外部プログラムで作成されたゲームを用いてAIプログラムの対戦開始
	 * @param gamecom	ゲームプログラムを実行するコマンド
	 * @param com1 	AIプログラムを実行するコマンド
	 * @param com2 	AIプログラムを実行するコマンド
	 * @throws IOException 
	 */
	public void execute(String gamecom, int num, String com[]) throws IOException{
		/*
		Runtime r = Runtime.getRuntime();
		InputStream in[] = new InputStream[num];
		OutputStream out[] = new OutputStream[num];
		proc_ai = new Process[num];

		int i = -1;
		try {
			for(i=0; i<num; i++){
				proc_ai[i] = r.exec(com[i]);
				in[i] = proc_ai[i].getInputStream();
				out[i] = proc_ai[i].getOutputStream();
			}
		} catch (IOException e1) {
			throw new IOException(String.format("%d", i+1));
		}
		*/
		game = new Game(gamecom, 2, com, textarea);
	}
}