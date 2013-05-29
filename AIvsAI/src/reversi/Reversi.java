package reversi;

import java.io.IOException;

import javax.swing.JTextArea;

import mainpackage.Game;

public class Reversi extends Game{
	private Field field;
	private Turn turn;

	public Reversi(String[] com, JTextArea ta) {
		super(2, com, ta);
		// TODO 自動生成されたコンストラクター・スタブ
		field = new Field();	
	}
	
	@Override
	public void gotInput(int num, String str){
		System.out.println("AI[" + num + "] > "+ str);
		if(turn.isBlack()){
			if(num == 0){
				parseCommand(0, str);
			}
		}else{
			if(num == 1){
				parseCommand(1, str);
			}
		}
	}
	
	@Override
	public void start() throws IOException{
		startAI();
		String init_massage = Integer.toString(field.getFieldSize());
		putsAIStream(init_massage);
		putsAIStream(0, "black");
		putsAIStream(1, "white");
		turn = new Turn();
		printTurn();
	}
	
	private void printfield(int num){
		putsAIStream(num, field.getInfo());
	}
	
	private void parseCommand(int num, String str){
		if(str.equals("field")){
			printfield(num);
		}else{
			String[] string = str.split(" ");
			Position pos = new Position(-1,-1);
			try{
				pos = new Position(Integer.parseInt(string[0]), Integer.parseInt(string[1]));
			}catch (Exception e){
				illegalInput();
			}
			
			byte color;
			if(num == 0){
				color = Piece.black;
			}else{
				color = Piece.white;
			}
			try {
				field.putPiece(color, pos);
				putsStatus(pos.x + " " + pos.y);
				putsAIStream(pos.x + " " + pos.y);
			} catch (Exception e) {
				illegalInput();
			}
			nextTurn();
		}
	}
	
	private void nextTurn(){
		if(turn.which().equals("black")){
			if(field.putable(Piece.white)){
				turn.next();
			}else{
				if(!field.putable(Piece.black)){
					gameOver();
				}else{
					//blackのターンのまま
				}
			}
		}else{
			if(field.putable(Piece.black)){
				turn.next();
				if(!field.putable(Piece.white)){
					gameOver();
					return;
				}else{
					//whiteのターンのまま
				}
			}
		}
		printTurn();
	}
	
	private void printTurn(){
			putsStatus(turn.which());
			putsAIStream(turn.which());
	}
	
	private void gameOver(){
		putsAIStream("over");
		putsStatus("over");
		putsStatus(field.getInfo());
		putsStatus("result");
		putsStatus("black : " + field.getBlackNum());
		putsStatus("white : " + field.getWhiteNum());
		super.end();
	}
	
	private void illegalInput(){
		putsStatus("illegal input");
		gameOver();
	}
	
}
