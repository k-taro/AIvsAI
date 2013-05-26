package mainpackage;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * Game クラスを継承したぷよぷよ専用のクラス
 * AIの入力によってぷよぷよの操作を行ったり情報を返したり出力する
 * @author KeitaroWakabayashi
 *
 */
public class PuyoPuyo extends Game{
/*	private final byte UP = 1;
	#define DOWN		2
	#define RIGHT		3
	#define LEFT		4

	#define ROT_RIGHT		13
	#define ROT_LEFT		14

	#define BLUE		9
	#define GREEN		10
	#define RED			12
	#define YELLOW		14
	#define OJAMA		8
	#define DEL			7
*/
	static public final int AINUM = 2;		// AI の数
	static public final int COLOR_MAX = 4;	// 色の数 
	static public final int OJAMA_PUYO = 9;	// おじゃまぷよの番号
	static private final int RATE = 70;
	
	private Player[] player;
	
	public PuyoPuyo(String com[], JTextArea ta){
		super(AINUM, com, ta);

		player = new Player[AINUM];
		for(int i=0;i<2; i++){
			player[i] = new Player();			
		}
	}
	
	
	/* (非 Javadoc)
	 * @see mainpackage.Game#gotInput(int, java.lang.String)
	 * AIからの命令を解析する部分
	 */
	@Override
	public void gotInput(int num, String str){
		//ピースをセット
		putPiece(num);
		
		
		player[num].getField();
	}
	
	private void putPiece(int num){
		int x=0;
		int r=0;
		byte piece_info[] = new byte[2];
		
		Field field = player[num].getField();
		int score = 0;
		int rensa = 1;
		player[num].putPiece(x, r);
		do{
			synchronized(field){
				field.fallPuyo();
				score = field.deletePuyo(rensa);
			}
			int temp_score = player[num].addScore(score);
			rensa++;
		}while(score == 0);
	}
	
}
