package reversi;

public class Turn {
	private String turn;
	
	public Turn(){
		turn = "black";
	}
	
	public void next(){
		if(turn.equals("black")){
			turn = "white";
		}else{
			turn = "black";
		}
	}
	
	public String which(){
		return new String(turn);
	}
	
	public boolean isBlack(){
		if(turn.equals("black")){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isWhite(){
		if(turn.equals("white")){
			return true;
		}else{
			return false;
		}
	}
}
