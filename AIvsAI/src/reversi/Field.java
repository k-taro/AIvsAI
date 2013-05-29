package reversi;

public class Field {
	private byte[][] field_info;
	private final int field_size;
	
	public Field(){
		field_size = 8;
		field_info = new byte[field_size][field_size];
		for(int x = 0; x<field_size; x++){
			for(int y = 0; y<field_size; y++){
				field_info[x][y] = Piece.brank;
			}
		}
		field_info[(field_size / 2) - 1][(field_size / 2) - 1] = field_info[(field_size / 2)][(field_size / 2)] = Piece.white;
		field_info[(field_size / 2)-1][(field_size / 2)] = field_info[(field_size / 2)][(field_size / 2)-1] = Piece.black;
	}
	
	public void putPiece(byte color, Position position) throws Exception{
		if(field_info[position.x][position.y] != Piece.brank){
			throw new Exception();			
		}
		int[] dir_x = {-1,0,1,-1,1,-1,0,1};
		int[] dir_y = {-1,-1,-1,0,0,1,1,1};
		
		byte rivalcolor;
		
		if(color == Piece.black){
			rivalcolor = Piece.white;
		}else{
			rivalcolor = Piece.black;
		}

		boolean put = false;

		for(int dir = 0; dir < 8; dir++){
			int x = position.x;
			int y = position.y;

			boolean revarse = false;
			byte[][] temp_field = getTempField();
			
			while(true){
				x+=dir_x[dir];
				y+=dir_y[dir];
				if(x<0 || field_size<=x || y<0 || field_size<=y){
					// field と同じに戻す
					break;
				}

				if(temp_field[x][y] == rivalcolor){
					temp_field[x][y] = color;
					revarse = true;
				}else if(temp_field[x][y] == color){
					field_info = temp_field; // 更新する
					if(revarse){
						put = true;
					}
					break;
				}else{
					// field と同じに戻す
					break;
				}
			}
		}
		
		if(put){
			field_info[position.x][position.y] = color; 
		}else{
			throw new Exception();
		}
	}
	
	/**
	 * 諸悪の根源
	 * シャローコピー
	 * @return
	 */
	private byte[][] getTempField() {
		// TODO 自動生成されたメソッド・スタブ
		byte[][] temp = new byte[field_size][field_size];
		for(int x=0;x<field_size;x++){
			for(int y=0;y<field_size;y++){
				//temp[x][y] = new Byte(field_info[x][y]);
				byte b = field_info[x][y];
				temp[x][y] = b;
			}
		}
		return temp;
	}

	public String getInfo(){
		String str = "";
		for(int y = 0; y<field_size; y++){
			for(int x = 0; x<field_size; x++){
				str += field_info[x][y] + " ";
			}
			str = str.substring(0, str.length()-1);
			str += "\n";
		}
		return str;
	}
	
	public int getFieldSize(){
		return field_size;
	}

	public boolean putable(byte color) {
		// TODO 自動生成されたメソッド・スタブ
		for(int y = 0; y<field_size; y++){
			for(int x = 0; x<field_size; x++){
				byte[][] temp = getTempField();
				try {
					putPiece(color, new Position(x,y));
					field_info = temp;
					return true;
				} catch (Exception e) {
					field_info = temp;
				}
			}	
		}
		return false;
	}

	public String getBlackNum() {
		// TODO 自動生成されたメソッド・スタブ
		byte count = 0;
		for(int y = 0; y<field_size; y++){
			for(int x = 0; x<field_size; x++){
				if(field_info[x][y] == Piece.black){
					count++;
				}
			}	
		}
		return String.valueOf(count);
	}

	public String getWhiteNum() {
		// TODO 自動生成されたメソッド・スタブ
		byte count = 0;
		for(int y = 0; y<field_size; y++){
			for(int x = 0; x<field_size; x++){
				if(field_info[x][y] == Piece.white){
					count++;
				}
			}	
		}
		return String.valueOf(count);
	}
}
