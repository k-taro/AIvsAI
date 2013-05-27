package puyopuyo;

import java.util.Random;

public class Piece {
	
	/**
	 * ピースの情報
	 * piece_info[0]が軸ぷよ
	 * piece_info[1]が付随ぷよ 
	 */
	byte piece_info[];
	
	public Piece(){
		piece_info = new byte[2];
		piece_info[0] = (byte) (((Math.random() * 10) % PuyoPuyo.COLOR_MAX) + 1);
	}
	
	/**
	 * ローテーション番号を入れたときのピースの様子を返す
	 * @param r ローテーション番号（右回転の回数）
	 * @return　ピースの並び byte[3][2] 左上が原点
	 */
	public byte[][] getPieceInfo(int r){
		byte temp_piece[][] = new byte[3][2];
		switch (r) {
		case 0:
			temp_piece[1][0] = piece_info[0];
			temp_piece[1][1] = piece_info[1];
			break;
		case 1:
			temp_piece[1][0] = piece_info[0];
			temp_piece[0][0] = piece_info[1];
			break;
		case 2:
			temp_piece[1][1] = piece_info[0];
			temp_piece[1][0] = piece_info[1];
			break;
		case 3:
			temp_piece[1][0] = piece_info[0];
			temp_piece[2][0] = piece_info[1];
			break;
		}
		return temp_piece;
	}
}
