package Saboteur;

import boardgame.Board;
import boardgame.BoardState;
import boardgame.Move;
import boardgame.Player;

/**
 * @author mgrenander
 */
public abstract class SaboteurPlayer extends Player {
    public SaboteurPlayer(String name) { super(name); }
    public SaboteurPlayer() { super("260729805"); }
// At start evaluate shortest path with given cards.
    @Override
    final public Board createBoard() { return new SaboteurBoard(); }

    @Override
    final public Move chooseMove(BoardState boardState) { return chooseMove((SaboteurBoardState) boardState); }

    public abstract Move chooseMove(SaboteurBoardState boardState);
}
