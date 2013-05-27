package puyopuyo;

import java.util.Stack;

public class Field {

	static private final byte FIELD_COL = 6; // フィールドの横軸のマス数
	static private final byte FIELD_ROW = 14;// フィールドの縦軸のマス数（見えない上2行も込み）

	/**
	 * フィールドの状況
	 * field_info[x][y] とすると
	 * 左下を原点とし、xは右にいくにつれて増加、yは上にいくにつれて増加
	 * 0 は空白、-1はおじゃまぷよ 1〜はぷよ（数字で色分け）
	 */
	private byte field_info[][];	// フィールドの状況
	
	/**
	 * コンストラクタ
	 */
	public Field(){
		field_info = new byte[FIELD_COL][FIELD_ROW]; 
	}
	
	/**
	 * ぷよを置く
	 * 引数はx座標、回転、ピースの並び
	 * 戻り値は1のくらいがx,rの異常、10のくらいが
	 * @param x
	 * @param r
	 * @param piece_info
	 * @return
	 */
	synchronized public int putPiece(int x, int r, Piece piece){
		
		byte piece_info[] = new byte[2];// 引数を変えたため一時的に書いてある。あとで消すこと。
		
		byte temp_piece[][] = null;
		int retval = 0;
		
		// error回避
		if(x<0){
			retval += 1;
			x=0;
		}else if(FIELD_COL<x){
			retval += 1;
			x=5;
		}
		if(r<0||3<r){
			retval += 2;
			r=0;
		}
		//下の行は後で消す
		
		// 放出口の両隣にぷよがいたら回転はそのままか上下反転かにしぼられる
		// 0,1はそのまま2,3は反転
		if(field_info[1][FIELD_ROW - 1] != 0 && field_info[3][FIELD_ROW - 1] != 0){
			switch(r){
			case 1:
				retval += 10;
			case 0:
				temp_piece = piece.getPieceInfo(0);
				break;
			case 3:
				retval += 10;
			case 2:
				temp_piece = piece.getPieceInfo(2);
				break;
			}
		} else {
			temp_piece = piece.getPieceInfo(r);
			if(x<2){ // 放出口より左側に動く
				for(int i=2; i>x;i--){
					// 軸ぷよの左隣にぷよがある。もしくは付随ぷよが軸ぷよの左側にありフィールドから飛び出すか置く場所にぷよがある場合。
					if((field_info[i-1][FIELD_ROW - 1] != 0) || (temp_piece[0][1] != 0 && ((i-2 < 0)  || field_info[i-2][FIELD_ROW - 1] != 0))){
						x = i;
						retval += 20;
						break;
					}
				}
			}else if(x>2){ // 放出口より右側に動く
				for(int i=2; i<x; i++){
					// 軸ぷよの右隣にぷよがある。もしくは付随ぷよが軸ぷよの右側にありフィールドから飛び出すか置く場所にぷよがある場合。
					if( (field_info[i+1][FIELD_ROW - 1] != 0) || (temp_piece[2][1] != 0 && (i+2 >= FIELD_COL)  ||  field_info[i+2][FIELD_ROW - 1] != 0) ){
						x = i;
						retval += 20;
						break;
					}
				}
			}else{ // 動かない場合
				if(temp_piece[0][0] != 0 && field_info[x-1][FIELD_ROW-1] != 0){
					x++;
				}else if(temp_piece[2][0] != 0 && field_info[x+1][FIELD_ROW-1]!=0){
					x--;
				}
			}
		}

		// フィールド上にピースを置く
		for(int i=0; i<3; i++){
			if(temp_piece[i][0] != 0){
				field_info[i-1 + x][FIELD_ROW-1] = temp_piece[i][0];
			}
		}
		for(int i=0; i<3; i++){
			if(temp_piece[i][1] != 0){
				if(field_info[i-1 + x][FIELD_ROW-2] == 0){
					field_info[i-1 + x][FIELD_ROW-2] = temp_piece[i][1];
				}else{
					field_info[i-1 + x][FIELD_ROW-1] = temp_piece[i][1];
				}
			}
		}

		//連鎖開始
		int rensa = 1;
//					do{
//					fallPuyo();
//					}while(deletePuyo(rensa++));// rensa++は後置なのでインクリメントする前の値が入る
		return retval;
	}
	/**
	 * フィールドのぷよを落とすメソッド
	 */
	synchronized public void fallPuyo(){
		byte temp_field[][] = new byte[FIELD_COL][FIELD_ROW+2];
		for(int x=0; x < FIELD_COL; x++){
			int n=0;
			for(int y=0; y > FIELD_ROW+2; y--){
				if(field_info[x][y] != 0){
					temp_field[x][n++] = field_info[x][y];
					if( n<FIELD_ROW+1){
						break;
					}
				}
			}
			while(n < FIELD_ROW+1){
				temp_field[x][n++] = 0;
			}
		}
		field_info = temp_field;
//		deletePuyo();
	}

	/**
	 * フィールドのぷよを消すメソッド
	 * 消すことによって得られた得点を返す
	 * @param rensa
	 * @return
	 */
	public int deletePuyo(int rensa){
		boolean del = false;
		int puyo_count[][] = new int[FIELD_COL][FIELD_ROW];
		boolean check_puyo[][] = new boolean[FIELD_COL][FIELD_ROW];
		
		int chain_bonus = 0;
		int douji_puyo = 0;
		boolean color[] = new boolean[PuyoPuyo.COLOR_MAX];
		
		for(int x=0; x<FIELD_COL; x++){
			for(int y=0; y<FIELD_ROW; y++){
				check_puyo[x][y] = false;
			}
		}

		for(int i=0; i<FIELD_COL; i++){
			for(int j=0; j<FIELD_ROW; j++){
				if(puyo_count[i][j] != 0){
					break;
				}
				int num = count(puyo_count, check_puyo, i, j);
				if(num > 3){
					color[field_info[i][j]] = true;
				}
				for(int x=0; x<FIELD_COL; x++){
					for(int y=0; y<FIELD_ROW; y++){
						if(check_puyo[x][y] == true){
							check_puyo[x][y] = false;
							puyo_count[x][y] = num;
							if(num > 3){
								field_info[x][y] = 0;
								del = true;
								// おじゃまの処理
								if(field_info[x-1][y] == PuyoPuyo.OJAMA_PUYO){
									field_info[x-1][y] = 0;
								}
								if(field_info[x+1][y] == PuyoPuyo.OJAMA_PUYO){
									field_info[x+1][y] = 0;
								}
								if(field_info[x][y-1] == PuyoPuyo.OJAMA_PUYO){
									field_info[x][y-1] = 0;
								}
								if(field_info[x][y+1] == PuyoPuyo.OJAMA_PUYO){
									field_info[x][y+1] = 0;
								}
							}
						}
					}
				}
				switch(num){
				default:
					chain_bonus += 10;
					douji_puyo += num;
					break;
				case 10:
					chain_bonus += 7;
					douji_puyo += 10;
					break;
				case 9:
					chain_bonus += 6;
					douji_puyo += 9;
					break;
				case 8:
					chain_bonus += 5;
					douji_puyo += 8;
					break;
				case 7:
					chain_bonus += 4;
					douji_puyo += 7;
					break;
				case 6:
					chain_bonus += 3;
					douji_puyo += 6;
					break;
				case 5:
					chain_bonus += 2;
					douji_puyo += 5;
					break;
				case 4:
					douji_puyo += 4;
				case 3:
				case 2:
				case 1:
				case 0:
					break;
				}
			}
		}

		int iro = 0;
		for(int i = 0; i<PuyoPuyo.COLOR_MAX; i++){
			if(color[i])
				iro++;
		}
		
		if(del){
			addPoint(douji_puyo, rensa, chain_bonus, iro);
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 得点を計算するメソッド
	 * 消したぷよが無くて呼び出された場合は得点なしになる
	 * @param douji_puyo　同時消しされたぷよの数
	 * @param rensa　連鎖数
	 * @param chain_bonus　繋がった数
	 * @param iro　色の数
	 */
	private void addPoint(int douji_puyo, int rensa, int chain_bonus, int iro) {
		int iro_bonus;
		int rensa_bonus;
		
		if(iro == 1){
			iro_bonus = 0;
		} else {
			iro_bonus = 3 * 2^(iro-2);
		}

		switch(rensa){
		case 1:	rensa_bonus = 0; break;
		case 2:	rensa_bonus = 8; break;
		case 3:	rensa_bonus = 16; break;
		default:rensa_bonus = 32 + 32*(rensa-4); break;
		}
		int x;
		int point = douji_puyo * 10 * (((x = chain_bonus + iro_bonus + rensa_bonus) != 0) ? x:1);
		
//		score += point;
		
//		addOjama(temp_score+point);
	}

	/**
	 * ぷよがどれだけ繋がっているか数えるメソッド
	 * @param puyo_count
	 * @param check_puyo
	 * @param i
	 * @param j
	 * @return
	 */
	public int count(int[][] puyo_count, boolean[][] check_puyo, int i, int j){

		return 0;
	}
	
}
