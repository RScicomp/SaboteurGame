package Saboteur;

import boardgame.Move;

/**
 * @author mgrenander
 */
public class RandomSaboteurPlayer extends SaboteurPlayer {
    public RandomSaboteurPlayer() {
        super("RandomPlayer");
    }

    public RandomSaboteurPlayer(String name) {
        super(name);
    }

    @Override
    public Move chooseMove(SaboteurBoardState boardState) {
        //System.out.println("random player acting as player number: "+boardState.getTurnPlayer());
        //System.out.println("Random Move:");
        SaboteurMove movep = boardState.getRandomMove();

        //System.out.println(movep.getCardPlayed().getName());
        //System.out.println(movep.getPosPlayed());


        return movep;
    }
}
