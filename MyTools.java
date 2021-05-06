package student_player;

import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
import pentago_twist.PentagoBoardState.Piece;

import java.lang.Math;
import java.util.ArrayList;

//helper functions list:
//public static PentagoMove StartingMoves (PentagoBoardState bState, int p ): returns starting moves at the beginning of the game
//public static Piece getColour (int player_num): returns coloured piece for the player (white or black)
//

//minimax stuff:
    //heuristics:
    //1: checks for consecutive pieces for a player (ex. _xxxx_), returns a score
    //public static int checkHorizontals(PentagoBoardState bState, int player_num)
    //public static int checkVerticals(PentagoBoardState bState, int player_num) 
    //public static int checkDiagonals(PentagoBoardState bState, int player_num)
    //
    //2: checks if player is winning, returns 5000 if so
    //public static int checkWinning (PentagoBoardState bState)
    //
    //3.Checks for four piece rows / columns / diagonals that are not consecutive
    //public static int checkNumbers (PentagoBoardState bState, int player_num)

//evaluation function that combines all three heuristics to return a score:
//public static int getValue (PentagoBoardState bState)

//actual minimax functions:
//public static PentagoMove RootABMiniMax (PentagoBoardState bState, int depth, int alpha, int beta, boolean maximizingPlayer)
//public static int ChildABMiniMax (PentagoBoardState bState, int depth, int alpha, int beta, boolean maximizingPlayer, long time)




public class MyTools {

    //initial value presets
    public static int PLAYER_NUM=-1;
    public static int OPPONENT_NUM=-1;
    public static int TIMELIMIT_MILLIS = 1900;
    private static int TWO_CONSEQ=2;
    private static int THREE_CONSEQ=8;
    private static int FOUR_CONSEQ=30;
    private static int FIVE_CONSEQ=1000;

    
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //to be run at the start of the game
    //tries to take spots in places where they can't be changed
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static PentagoMove StartingMoves (PentagoBoardState bState, int p ){
       
        if (bState.getPieceAt(1,1).compareTo(Piece.EMPTY) == 0) return new PentagoMove(1,1,0,0,p);
        if (bState.getPieceAt(1,4).compareTo(Piece.EMPTY) == 0) return new PentagoMove(1,4,0,0,p);
        if (bState.getPieceAt(4,1).compareTo(Piece.EMPTY) == 0) return new PentagoMove(4,1,0,0,p);
        if (bState.getPieceAt(4,4).compareTo(Piece.EMPTY) == 0) return new PentagoMove(4,4,0,0,p);
        else return null;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //returns coloured piece for the player (white or black)
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static Piece getColour (int player_num){
        Piece p =  player_num==0 ? Piece.WHITE : Piece.BLACK;
        return p;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //checks for consecutive pieces for a player (ex. _xxxx_) in each row, returns a score
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static int checkHorizontals(PentagoBoardState bState, int player_num){
        int score=0;
        if (bState.getWinner()==OPPONENT_NUM){
            return FIVE_CONSEQ*5;
        }
        for (int i=0; i<6;i++){ //for each row
            boolean foundSmth=false;
            
            //check for five
            for (int j=0; j<2;j++){
                if(bState.getPieceAt(i, 0+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 1+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 2+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 3+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 4+j).compareTo(getColour(player_num))==0){
                    score+=FIVE_CONSEQ;
                    foundSmth=true;
                }
            }
            
           

            //check for four
            if (foundSmth){
                continue; //skip  to next row if something was already found before, as we would not want to double count
            }
            for (int j=0; j<3;j++){
                if(bState.getPieceAt(i, 0+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 1+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 2+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 3+j).compareTo(getColour(player_num))==0){
                    score+=FOUR_CONSEQ;
                    foundSmth=true;
                }
            }
            if (foundSmth){
                continue; //skip  to next row
            }

            //check for three
            
            for (int j=0; j<4;j++){
                if(bState.getPieceAt(i, 0+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 1+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 2+j).compareTo(getColour(player_num))==0){
                    score+=THREE_CONSEQ;
                    foundSmth=true;
                }
            }
            if (foundSmth){
                continue; //skip  to next row
            }
            //check for two
            for (int j=0; j<5;j++){
                if(bState.getPieceAt(i, 0+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(i, 1+j).compareTo(getColour(player_num))==0){
                    score+=TWO_CONSEQ;
                   
                }
            }
        }
        return score;
    }
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //checks for consecutive pieces for a player (ex. _xxxx_) in each column, returns a score
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static int checkVerticals(PentagoBoardState bState, int player_num){
        int score=0;
        if (bState.getWinner()==OPPONENT_NUM){
            return FIVE_CONSEQ*5;
        }

        for (int i=0; i<6;i++){ //for each column
            boolean foundSmth=false;
            
            
            //check for five
            for (int j=0; j<2;j++){
                if(bState.getPieceAt(0+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(1+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(2+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(3+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(4+j, i).compareTo(getColour(player_num))==0){
                    score+=FIVE_CONSEQ;
                    foundSmth=true;
                }
            }
            
           

            //check for four
            if (foundSmth){
                continue; //skip  to next row
            }
            for (int j=0; j<3;j++){
                if(bState.getPieceAt(0+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(1+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(2+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(3+j, i).compareTo(getColour(player_num))==0){
                    score+=FOUR_CONSEQ;
                    foundSmth=true;
                }
            }
            if (foundSmth){
                continue; //skip  to next row
            }

            //check for three
            
            for (int j=0; j<4;j++){
                if(bState.getPieceAt(0+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(1+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(2+j, i).compareTo(getColour(player_num))==0){
                    score+=THREE_CONSEQ;
                    foundSmth=true;
                }
            }
            if (foundSmth){
                continue; //skip  to next row
            }
            //check for two
            for (int j=0; j<5;j++){
                if(bState.getPieceAt(0+j, i).compareTo(getColour(player_num))==0
                && bState.getPieceAt(1+j, i).compareTo(getColour(player_num))==0){
                    score+=TWO_CONSEQ;                  
                }
            }
        }
        return score;
    }


    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //checks for consecutive pieces for a player (ex. _xxxx_) in each diagonal, returns a score
    //considering that some of the diagonals have no chance of having winning moves, skips them (ie if they're length 4 and less)
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static int checkDiagonals(PentagoBoardState bState, int player_num){
        int score=0;

        if (bState.getWinner()==OPPONENT_NUM){
            return FIVE_CONSEQ*5;
        }

        boolean foundSmthTopFiveDiag=false;
        boolean foundSmthSixDiag=false;
        boolean foundSmthBottomFiveDiag=false;
        //for ascending   /

        //check five consecutives
        for (int i=0; i<2;i++){
            for (int j=0; j<2;j++){
               if(bState.getPieceAt(4+i, 0+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(3+i, 1+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(2+i, 2+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(1+i, 3+j).compareTo(getColour(player_num))==0
                && bState.getPieceAt(0+i, 4+j).compareTo(getColour(player_num))==0){
                    score+=FIVE_CONSEQ;
                    if(i==0 && j==0) foundSmthTopFiveDiag=true; //add location where found, to avoid double counting
                    else if (i==1 && j==1) foundSmthBottomFiveDiag=true;
                    else foundSmthSixDiag=true;
                } 
            }            
        }

        //check four 
        //from here on, if we had already found a five somewhere, we are not going to double count
        //that's why we're using the booleans
        
        if (!foundSmthSixDiag){
            for (int i=0; i<3;i++){
                if(bState.getPieceAt(5-i, 0+i).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(4-i, 1+i).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(3-i, 2+i).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(2-i, 3+i).compareTo(getColour(player_num))==0){
                        score+=FOUR_CONSEQ;
                        foundSmthSixDiag=true;
                    } 
                }           
            }
            
            if (!foundSmthTopFiveDiag){
                for (int i=0; i<2;i++){
                    if(bState.getPieceAt(4-i, 0+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(3-i, 1+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(2-i, 2+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(1-i, 3+i).compareTo(getColour(player_num))==0){
                            score+=FOUR_CONSEQ;
                            foundSmthTopFiveDiag=true;
                        } 
                    }           
            }
            if (!foundSmthBottomFiveDiag){
                for (int i=0; i<2;i++){
                    if(bState.getPieceAt(5-i, 1+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(4-i, 2+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(3-i, 3+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(2-i, 4+i).compareTo(getColour(player_num))==0){
                            score+=FOUR_CONSEQ;
                            foundSmthBottomFiveDiag=true;
                        } 
                    }           
            }
        

        //check three

        if (!foundSmthSixDiag){
            for (int i=0; i<4;i++){
                if(bState.getPieceAt(5-i, 0+i).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(4-i, 1+i).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(3-i, 2+i).compareTo(getColour(player_num))==0){
                        score+=THREE_CONSEQ;
                        foundSmthSixDiag=true;
                    } 
                }           
            }
            
            if (!foundSmthTopFiveDiag){
                for (int i=0; i<3;i++){
                    if(bState.getPieceAt(4-i, 0+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(3-i, 1+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(2-i, 2+i).compareTo(getColour(player_num))==0){
                            score+=THREE_CONSEQ;
                            foundSmthTopFiveDiag=true;
                        } 
                    }           
            }
            if (!foundSmthBottomFiveDiag){
                for (int i=0; i<3;i++){
                    if(bState.getPieceAt(5-i, 1+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(4-i, 2+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(3-i, 3+i).compareTo(getColour(player_num))==0){
                            score+=THREE_CONSEQ;
                            foundSmthBottomFiveDiag=true;
                        } 
                    }           
            }
        
        //check two

        if (!foundSmthSixDiag){
            for (int i=0; i<5;i++){
                if(bState.getPieceAt(5-i, 0+i).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(4-i, 1+i).compareTo(getColour(player_num))==0){
                        score+=TWO_CONSEQ;
                        foundSmthSixDiag=true;
                    } 
                }           
            }
            
            if (!foundSmthTopFiveDiag){
                for (int i=0; i<4;i++){
                    if(bState.getPieceAt(4-i, 0+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(3-i, 1+i).compareTo(getColour(player_num))==0){
                            score+=TWO_CONSEQ;
                            foundSmthTopFiveDiag=true;
                        } 
                    }           
            }
            if (!foundSmthBottomFiveDiag){
                for (int i=0; i<4;i++){
                    if(bState.getPieceAt(5-i, 1+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(4-i, 2+i).compareTo(getColour(player_num))==0){
                            score+=TWO_CONSEQ;
                            foundSmthBottomFiveDiag=true;
                        } 
                    }           
            }


            //for descending \
            foundSmthTopFiveDiag=false;
            foundSmthSixDiag=false;
            foundSmthBottomFiveDiag=false;
            
    
            //check five
            for (int i=0; i<2;i++){
                for (int j=0; j<2;j++){
                   if(bState.getPieceAt(0+i, 0+j).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(1+i, 1+j).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(2+i, 2+j).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(3+i, 3+j).compareTo(getColour(player_num))==0
                    && bState.getPieceAt(4+i, 4+j).compareTo(getColour(player_num))==0){
                        score+=FIVE_CONSEQ;
                        if(i==0 && j==1) foundSmthTopFiveDiag=true;
                        else if (i==1 && j==0) foundSmthBottomFiveDiag=true;
                        else foundSmthSixDiag=true;
                    } 
                }            
            }
    
            //check four
            
            if (!foundSmthSixDiag){
                for (int i=0; i<3;i++){
                    if(bState.getPieceAt(0+i, 0+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(1+i, 1+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(2+i, 2+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(3+i, 3+i).compareTo(getColour(player_num))==0){
                            score+=FOUR_CONSEQ;
                            foundSmthSixDiag=true;
                        } 
                    }           
                }
                
                if (!foundSmthTopFiveDiag){
                    for (int i=0; i<2;i++){
                        if(bState.getPieceAt(0+i, 1+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(1+i, 2+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(2+i, 3+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(3+i, 4+i).compareTo(getColour(player_num))==0){
                                score+=FOUR_CONSEQ;
                                foundSmthTopFiveDiag=true;
                            } 
                        }           
                }
                if (!foundSmthBottomFiveDiag){
                    for (int i=0; i<2;i++){
                        if(bState.getPieceAt(1+i, 0+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(2+i, 1+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(3+i, 2+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(4+i, 3+i).compareTo(getColour(player_num))==0){
                                score+=FOUR_CONSEQ;
                                foundSmthBottomFiveDiag=true;
                            } 
                        }           
                }
            
    
            //check three
    
            if (!foundSmthSixDiag){
                for (int i=0; i<4;i++){
                    if(bState.getPieceAt(0+i, 0+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(1+i, 1+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(2+i, 2+i).compareTo(getColour(player_num))==0){
                            score+=THREE_CONSEQ;
                            foundSmthSixDiag=true;
                        } 
                    }           
                }
                
                if (!foundSmthTopFiveDiag){
                    for (int i=0; i<3;i++){
                        if(bState.getPieceAt(0+i, 1+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(1+i, 2+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(2-i, 2+i).compareTo(getColour(player_num))==0){
                                score+=THREE_CONSEQ;
                                foundSmthTopFiveDiag=true;
                            } 
                        }           
                }
                if (!foundSmthBottomFiveDiag){
                    for (int i=0; i<3;i++){
                        if(bState.getPieceAt(1+i, 0+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(2+i, 1+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(3+i, 2+i).compareTo(getColour(player_num))==0){
                                score+=THREE_CONSEQ;
                                foundSmthBottomFiveDiag=true;
                            } 
                        }           
                }
            
            //check two
    
            if (!foundSmthSixDiag){
                for (int i=0; i<5;i++){
                    if(bState.getPieceAt(0+i, 0+i).compareTo(getColour(player_num))==0
                        && bState.getPieceAt(1+i, 1+i).compareTo(getColour(player_num))==0){
                            score+=TWO_CONSEQ;
                            foundSmthSixDiag=true;
                        } 
                    }           
                }
                
                if (!foundSmthTopFiveDiag){
                    for (int i=0; i<4;i++){
                        if(bState.getPieceAt(0+i, 1+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(1+i, 2+i).compareTo(getColour(player_num))==0){
                                score+=TWO_CONSEQ;
                                foundSmthTopFiveDiag=true;
                            } 
                        }           
                }
                if (!foundSmthBottomFiveDiag){
                    for (int i=0; i<4;i++){
                        if(bState.getPieceAt(1+i, 0+i).compareTo(getColour(player_num))==0
                            && bState.getPieceAt(2+i, 1+i).compareTo(getColour(player_num))==0){
                                score+=TWO_CONSEQ;
                                foundSmthBottomFiveDiag=true;
                            } 
                        }           
                }
    
        return score;
    }
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //checks if a player is winning, return either 5000 for agent, or -5000 for opponent
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static int checkWinning (PentagoBoardState bState){
        if (bState.getWinner()==OPPONENT_NUM){
            return -FIVE_CONSEQ*10;
        }else if (bState.getWinner()==PLAYER_NUM){
            return FIVE_CONSEQ*10;
        }
        else return 0;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Checks for four piece rows / columns / diagonals that are not consecutive
    // ie they have a space in between (ex. _xx_xx)
    //return the FOUR_CONSEQ score if needed
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static int checkNumbers (PentagoBoardState bState, int player_num){
        int score=0;

        //rows

        for (int i=0; i<6;i++){ //for each row
            int numPlayers=0;
            boolean MiddleOpponentsPresent = false;
            for (int j=0; j<6; j++){
                    if(bState.getPieceAt(i, j).compareTo(getColour(player_num))==0){
                        numPlayers++;
                    }
                    else if(bState.getPieceAt(i, j).compareTo(getColour(1-player_num))==0 
                    && j!=0 && j!=5){
                        MiddleOpponentsPresent=true;
                    }
                }
            // xx_xxo
            // xxoxx_
            // oxxxx_
            // xx__xx
            //if opponent is at the edge, no worries. If they are in the middle, interference 

                if(numPlayers > 3 && !MiddleOpponentsPresent){
                    score += FOUR_CONSEQ;
                }
            }   
            
        //columns

        for (int i=0; i<6;i++){ //for each column
            int numPlayers=0;
            boolean MiddleOpponentsPresent = false;
            for (int j=0; j<6; j++){
                    if(bState.getPieceAt(j, i).compareTo(getColour(player_num))==0){
                        numPlayers++;
                    }
                    else if(bState.getPieceAt(j, i).compareTo(getColour(1-player_num))==0 
                    && j!=0 && j!=5){
                        MiddleOpponentsPresent=true;
                    }
                }
           
            //if opponent is at the edge, no worries. If they are in the middle, interference 

                if(numPlayers > 3 && !MiddleOpponentsPresent){
                    score += FOUR_CONSEQ;
                }
            }   
        
        //ascending diagonal /
        //centre six cells
        int numPlayers=0;
        boolean MiddleOpponentsPresent = false;
        for (int i=0; i<6;i++){
            if(bState.getPieceAt(5-i, 0+i).compareTo(getColour(player_num))==0){
                    numPlayers++;
                } 
            else if(bState.getPieceAt(5-i, 0+i).compareTo(getColour(1-player_num))==0 && i!=0 && i!=5){
                MiddleOpponentsPresent=true;
            } 
            }           
            
            if(numPlayers > 3 && !MiddleOpponentsPresent){
                score += FOUR_CONSEQ;
            }   
            numPlayers=0;
            MiddleOpponentsPresent = false;
            
        //top five piece diagonal
        for (int i=0; i<5;i++){
            if(bState.getPieceAt(4-i, 0+i).compareTo(getColour(player_num))==0){
                    numPlayers++;
                } 
            else if (bState.getPieceAt(4-i, 0+i).compareTo(getColour(1-player_num))==0){
                //don't even need to check position here, even one opponent will ruin winning chances
                MiddleOpponentsPresent=true;
            }       
            }      
            if(numPlayers > 3 && !MiddleOpponentsPresent){
                score += FOUR_CONSEQ;
            }   
            numPlayers=0;
            MiddleOpponentsPresent = false;
           
                for (int i=0; i<5;i++){
                    if(bState.getPieceAt(5-i, 1+i).compareTo(getColour(player_num))==0){
                        numPlayers++;
                    } 
                    else if (bState.getPieceAt(5-i, 1+i).compareTo(getColour(1-player_num))==0){
                        //don't even need to check position here, even one opponent will ruin winning chances
                        MiddleOpponentsPresent=true;
                    }             
                }           
            if(numPlayers > 3 && !MiddleOpponentsPresent){
                        score += FOUR_CONSEQ;
                    }  
        //descending diagonal \

        numPlayers=0;
        MiddleOpponentsPresent = false;

        //six piece center diagonal
        for (int i=0; i<6;i++){
            if(bState.getPieceAt(0+i, 0+i).compareTo(getColour(player_num))==0){
                    numPlayers++;
                } 
            else if(bState.getPieceAt(0+i, 0+i).compareTo(getColour(1-player_num))==0 && i!=0 && i!=5){
                MiddleOpponentsPresent=true;
            } 
            }           
            
            if(numPlayers > 3 && !MiddleOpponentsPresent){
                score += FOUR_CONSEQ;
            }   
            numPlayers=0;
            MiddleOpponentsPresent = false;
            
        //top five piece diagonal
        for (int i=0; i<5;i++){
            if(bState.getPieceAt(0+i, 1+i).compareTo(getColour(player_num))==0){
                    numPlayers++;
                } 
            else if (bState.getPieceAt(0+i, 1+i).compareTo(getColour(1-player_num))==0){
                //don't even need to check position here, even one opponent will ruin winning chances
                MiddleOpponentsPresent=true;
            }       
            }      
            if(numPlayers > 3 && !MiddleOpponentsPresent){
                score += FOUR_CONSEQ;
            }   
            numPlayers=0;
            MiddleOpponentsPresent = false;
           
                for (int i=0; i<5;i++){
                    if(bState.getPieceAt(1+i, 0+i).compareTo(getColour(player_num))==0){
                        numPlayers++;
                    } 
                    else if (bState.getPieceAt(1+i, 0+i).compareTo(getColour(1-player_num))==0){
                        //don't even need to check position here, even one opponent will ruin winning chances
                        MiddleOpponentsPresent=true;
                    }             
                }           
            if(numPlayers > 3 && !MiddleOpponentsPresent){
                        score += FOUR_CONSEQ;
                    }  

        return score;

    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Evaluation function that combines the three above heuristics
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static int getValue (PentagoBoardState bState){
        int totalScore =0;
        //get consecutives
        totalScore +=checkHorizontals(bState, PLAYER_NUM);
        totalScore +=checkVerticals(bState, PLAYER_NUM);
        totalScore +=checkDiagonals(bState, PLAYER_NUM);
        
        //get opponent's consecutives
        totalScore -=checkHorizontals(bState, OPPONENT_NUM);
        totalScore -=checkVerticals(bState, OPPONENT_NUM);
        totalScore -=checkDiagonals(bState, OPPONENT_NUM);

        totalScore+=checkWinning(bState);

        totalScore += checkNumbers(bState, PLAYER_NUM);

        totalScore -= checkNumbers(bState, OPPONENT_NUM);

        return totalScore;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Parent mini max, that returns a pentago move
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //minimax
    public static PentagoMove RootABMiniMax (PentagoBoardState bState, int depth, int alpha, int beta, boolean maximizingPlayer){

        //with time, less branching, can afford more depth
        if (bState.getTurnNumber()>8){
            depth+=3;
        }
        if (bState.getTurnNumber()>12){
            depth+=5;
        }
        long start = System.currentTimeMillis(); // Start timer
        //System.out.println("timer"+start);

        ArrayList<PentagoMove> moves = bState.getAllLegalMoves();
        if(moves.size()==1){
            return moves.get(0); //if there's only one move
        }

        //for agent
        if (maximizingPlayer){
            PentagoMove maxEvalMove = null;
            int maxEval=Integer.MIN_VALUE;
            for (PentagoMove m :moves){
                
                if (System.currentTimeMillis() - start > TIMELIMIT_MILLIS)
            {
                System.out.println("TIME LIMIT EXCEEDED: Playing Best Available Move");
                //if no move saved, return random
                PentagoMove move= (maxEvalMove==null)?(PentagoMove) bState.getRandomMove(): maxEvalMove;
                return move;
                //break;
            }


                PentagoBoardState bStateClone = (PentagoBoardState)bState.clone();
                bStateClone.processMove(m);

                if (bStateClone.getWinner()==PLAYER_NUM){
                    return m;  //if move is a winner, no need to process further
                }
                //here we get into the actual recursive minimax
                int eval=ChildABMiniMax(bStateClone, depth-1, alpha, beta, false, start);
                if (eval>maxEval){
                    maxEval=eval;
                    maxEvalMove=m;
                }
                //alpha beta pruning!
                alpha=Math.max(alpha, eval);
                if (beta <= alpha){
                    break;
                }
            }
            //System.out.println("maxEval" + maxEval);

            return maxEvalMove;
        }
        else{
            //exact same thing, but for opponent
            PentagoMove minEvalMove=null;
            int minEval=Integer.MAX_VALUE;
            for (PentagoMove m :moves){
                if (System.currentTimeMillis() - start > TIMELIMIT_MILLIS)
            {
                System.out.println("TIME LIMIT EXCEEDED: Playing Best Available Move");
                PentagoMove move= (minEvalMove==null)?(PentagoMove) bState.getRandomMove(): minEvalMove;
                return move;
                //break;
            }
                PentagoBoardState bStateClone = (PentagoBoardState)bState.clone();
                bStateClone.processMove(m);

                if (bStateClone.getWinner()==PLAYER_NUM){
                    return m;
                }

                int eval=ChildABMiniMax(bStateClone, depth-1, alpha, beta, true, start);
                //minEval=Math.min(minEval, getValue(mNext));
                if (eval<minEval){
                    minEval=eval;
                    minEvalMove=m;
                }

                beta=Math.min(beta, eval);
                if (beta <= alpha){
                    break;
                }
            }
            //System.out.println("minEval" + minEval);
            return minEvalMove;
            
        }
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Recursive mini max, that returns ints
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        public static int ChildABMiniMax (PentagoBoardState bState, int depth, int alpha, int beta, boolean maximizingPlayer, long time){
            //if we're done recursing
            if(bState.gameOver()==true){
                return getValue(bState); //different winning states hae different scores, that's why we're using the normal evaluation fiunction
            }else if (depth==0){
                //System.out.println("getValue is"+getValue(bState));
                return getValue(bState); //get score
            }

            //else recurse as per the usual minimax flow
            //watch the time limit though!
            if (maximizingPlayer){
                int maxEval=Integer.MIN_VALUE;
                for (PentagoMove m :bState.getAllLegalMoves()){
                    if (System.currentTimeMillis() - time > TIMELIMIT_MILLIS)
                    {
                        System.out.println("TIME LIMIT EXCEEDED: Returning best max: " + maxEval);
                        return maxEval;
                    }
                    PentagoBoardState bStateClone = (PentagoBoardState)bState.clone();
                    bStateClone.processMove(m);
                    int eval=ChildABMiniMax(bStateClone, depth-1, alpha, beta, false, time);
                    maxEval=Math.max(maxEval, eval);
                    alpha=Math.max(alpha, eval);
                    if (beta <= alpha){
                        break;
                    }
                }
                return maxEval;
            }
            else{
                int minEval=Integer.MAX_VALUE;
                for (PentagoMove m :bState.getAllLegalMoves()){
                    if (System.currentTimeMillis() - time > TIMELIMIT_MILLIS)
                    {
                        System.out.println("TIME LIMIT EXCEEDED: Returning best min: " + minEval);
                        return minEval;
                    }
                    PentagoBoardState bStateClone = (PentagoBoardState)bState.clone();
                    bStateClone.processMove(m);
                    int eval=ChildABMiniMax(bStateClone, depth-1, alpha, beta, true, time);
                    minEval=Math.min(minEval, eval);
                    beta=Math.min(beta, eval);
                    if (beta <= alpha){
                        break;
                    }
                }
                return minEval;
            }
        }


    

}