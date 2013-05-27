package puyopuyo;

public class Player {

	private Field field;
	private Piece piece;
	private Piece next;
	private Piece next2;
	
	private int	score;
	private int	temp_score;

	private int	ojama;

	/**
	 * コンストラクタ
	 */
	public Player(){
		field = new Field();
	}
	
	/**
	 * ピースを置く
	 * x,y 位置
	 * r 回転
	 * @param x
	 * @param r
	 */
	public void putPiece(int x, int r){
		field.putPiece(x, r, piece);
	}
	
	/**
	 * フィールド状況を返す
	 */
	public Field getField(){
		return field;
	}
	
	/**
	 * おじゃまの様子を返す
	 */
	public void getOjama(){
		
	}
	
	public synchronized int addScore(int s){
		score += s;
		return temp_score;
	}
	
	
}
