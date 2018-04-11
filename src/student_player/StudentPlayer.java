package student_player;

import java.util.ArrayList;
import boardgame.Move;
import tablut.*;
import coordinates.*;


/** A player file submitted by a student. */
public class StudentPlayer extends TablutPlayer {

 	TablutMove finalMove;
	
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260659960");
    }
    
    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
     
    public Move chooseMove(TablutBoardState boardState) { 
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
     	
    	int me = boardState.getTurnPlayer();
    	System.out.println("me: "+me);
     	int you = boardState.getOpponent();
     	System.out.println("you: "+you);

        int value = minimax( boardState, me, you, 3);          
        return finalMove;
    }
     
    public int minimax( TablutBoardState boardState, int me, int you, int depth){  	
    	TablutMove bestMove = null;
    	int bestValue;
    	int worstValue;
    	
    	if(depth == 0 || boardState.gameOver() || boardState.getTurnNumber()>boardState.MAX_TURNS){  		
    		int val = estimate(boardState, me, you);
    		return val;
    	}
    	
    	if(me == boardState.getTurnPlayer()){
    		bestValue = -1000;
    		ArrayList<TablutMove> options = boardState.getAllLegalMoves(); 
    		for (TablutMove move : options){
    			TablutBoardState clone= (TablutBoardState) boardState.clone();	
    			clone.processMove(move);
    			
    			int val =  minimax(clone, me, you, (depth-1)); 
    				
    			if(val > bestValue){
    				bestValue = val;
    				bestMove = move;    			
    			}    			
    		}				
    		finalMove = bestMove;
    		return bestValue; 
    	}
    	
    	else{
    		worstValue = 1000;
    		ArrayList<TablutMove> options = boardState.getAllLegalMoves(); 
    		for (TablutMove move : options ){
    			TablutBoardState clone= (TablutBoardState) boardState.clone();		
    			clone.processMove(move); 
    			
    			int val =  minimax(clone, me, you, (depth-1)); 
    			if(val < worstValue){
    				worstValue = val;
    				bestMove = move;
    			}     		
    		}	
    		finalMove = bestMove;
    		return worstValue; 	
    	}
    }

    
    
    public int estimate(TablutBoardState boardState, int me, int you){
    	int evaluation = 0;
    	int kingCornerDistance;
    	boolean capture = false;
    	
    	int win = 10000;
    	int lose = -10000;
    	
    	if(boardState.getWinner() == me ){
			evaluation = win;
		}
		if(boardState.getWinner() == you ){
			evaluation = lose;
		}
    	if(!boardState.gameOver() ){
    		if(me==boardState.MUSCOVITE){
    			evaluation = 10*(16-boardState.getNumberPlayerPieces(me))		
    					-10*(9-boardState.getNumberPlayerPieces(you));	
    		}
    		else{
    			Coord kingPos = boardState.getKingPosition();      			    
        		int minDistance = Coordinates.distanceToClosestCorner(kingPos);	 
         	
        		evaluation = 10*(9-boardState.getNumberPlayerPieces(you))		
        					-10*(16-boardState.getNumberPlayerPieces(me)) + minDistance;
    		}
    		
    	}
    	return evaluation;
    }
    
  
}





