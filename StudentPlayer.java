package student_player;

import boardgame.Move;

import pentago_twist.PentagoPlayer;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260770497");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
       
        //set up the variables
        int myTurn =boardState.getTurnPlayer(); //0 first, 1 second
        boolean maximizingPlayer= myTurn==0 ? true : false;
        MyTools.PLAYER_NUM=myTurn;
        MyTools.OPPONENT_NUM= 1-myTurn;
        int depth=3;

        //start with predetermined moves for as long as possible to cut down the tree     
        PentagoMove move=MyTools.StartingMoves(boardState, myTurn);

        //else run minimax
        if(move==null){
            move=MyTools.RootABMiniMax(boardState, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, maximizingPlayer);
        }
       
        // Return your move to be processed by the server.
        return move;
    }
}