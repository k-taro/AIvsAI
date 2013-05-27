package puyopuyo;

/**
 * おじゃまを管理するのクラス
 * 2人用にしか対応していない
 * @author KeitaroWakabayashi
 *
 */
public class OjamaField {
	int ojama_num;
	int temp_ojama_num;
	
	boolean adding;
	
	/**
	 * コンストラクタ
	 */
	public OjamaField(){
		ojama_num = 0;
		temp_ojama_num = 0;
		adding = false;
	}
	
	/**
	 * 連鎖開始時に呼ぶこと
	 */
	public void startAdd(){
		adding = true;
	}
	
	/**
	 * 連鎖終了後に呼ぶこと
	 */
	public void endAdd(){
		adding = false;
	}
}
