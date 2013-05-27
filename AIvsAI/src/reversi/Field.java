package reversi;

public class Field {
	private byte[][] field_info;
	private final int field_size;
	
	public Field(){
		field_size = 8;
		field_info = new byte[field_size][field_size];
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

		byte[][] temp_field = getTempField();
		
		boolean revarse = false;

		for(int dir = 0; dir < 8; dir++){
			int x = position.x;
			int y = position.y;
			
			while(true){
				x+=dir_x[dir];
				y+=dir_y[dir];
				if(x<0 || field_size<=x || y<0 || field_size<=y){
					temp_field = getTempField();
					// field と同じに戻す
					break;
				}

				if(temp_field[x][y] == rivalcolor){
					temp_field[x][y] = color;
				}else if(temp_field[x][y] == color){
					field_info = temp_field; // 更新する
					revarse = true;
					break;
				}else{
					temp_field = getTempField();
					// field と同じに戻す
					break;
				}
			}
		}
		
		if(!revarse){
			throw new Exception();
		}
	}
	
	private byte[][] getTempField() {
		// TODO 自動生成されたメソッド・スタブ
		byte[][] temp = new byte[field_size][field_size];
		for(int x=0;x<field_size;x++){
			for(int y=0;y<field_size;y++){
				temp[x][y] = field_info[x][y];
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
