package student_player;

import Saboteur.SaboteurMove;
import boardgame.Move;
import Saboteur.cardClasses.SaboteurCard;
import student_player.MyTools.Path;

import Saboteur.SaboteurPlayer;
import Saboteur.SaboteurBoardState;
import Saboteur.cardClasses.SaboteurTile;

import java.util.ArrayList;

/** A player file submitted by a student. */
public class StudentPlayer extends SaboteurPlayer {
    private static class Scores {
        int max;
        int min;
        ArrayList<Double> scores;
        boolean connected=false;
    }
//IDEA: Tree. Probability of cards.
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("xxxxxxxxx");
    }
    private ArrayList<SaboteurMove> chosenMoves;
    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public ArrayList<SaboteurMove> getDrops(ArrayList<SaboteurMove> moves){
        System.out.println("Drops");
        ArrayList<SaboteurMove> drops = new ArrayList<SaboteurMove>();
        for (int i =0;i < moves.size();i++){
            if (moves.get(i).getCardPlayed().getName().equals("Drop")){
                drops.add(moves.get(i));
                System.out.println(moves.get(i).toPrettyString());
            }
        }
        return drops;
    }
    public ArrayList<SaboteurMove> getDestroy(ArrayList<SaboteurMove> moves){
        ArrayList<SaboteurMove> drops = new ArrayList<SaboteurMove>();
        for (int i =0;i < moves.size();i++){
            if (moves.get(i).getCardPlayed().getName().equals("Destroy")){
                drops.add(moves.get(i));

            }
        }
        return drops;
    }

    public Move chooseMove(SaboteurBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        //MyTools.getSomething();
        System.out.println("Student player:");
        ArrayList<SaboteurMove> moves =  boardState.getAllLegalMoves();
        ArrayList<SaboteurMove> drops = getDrops(moves);
        ArrayList<SaboteurMove> destroys = getDestroy(moves);

        int[][] board = boardState.getHiddenIntBoard();
        SaboteurTile[][] tileboard = boardState.getHiddenBoard();
        boardState.printBoard();

        //print tiles
        for (int i =0 ;i < tileboard.length;i++){
            for (int j =0;j<tileboard[i].length;j++) {
                if(tileboard[i][j]!=null) {
                    System.out.print(tileboard[i][j].getIdx());
                }else{
                    System.out.print("-");
                }
            }
            System.out.println("");
        }

        // Is random the best you can do?
        Move myMove = boardState.getRandomMove();
        System.out.println("Random Move: " + myMove.toPrettyString());

        //Analyze moves
        //ArrayList<Double> scores = heuristic(board,moves);
        Scores scores = heuristic(board,moves);
        SaboteurMove best_move = moves.get(scores.min);

        //If nothing connects, either drop or destroy
        //If the max is too big drop it.
        if(scores.max > 15-(20/boardState.getTurnNumber())){

        }

        int[] position = best_move.getPosPlayed();
        System.out.println("position X:"+((position[0]*3)+1) + "position Y:" + ((position[1]*3)+1));

        //IDEA: Destroy the path closest to goal if you have a bad hand and later in the game.
        System.out.println("Heuristic Move: "+ best_move.toPrettyString());
        System.out.println("Int board width: " + boardState.getHiddenIntBoard().length + " Int board length: "+boardState.getHiddenIntBoard()[0].length);
        System.out.println("Tile board width: "+ tileboard.length + " Tile Board Length: "+tileboard[0].length);
        return best_move;
    }
    /*
    public SaboteurMove selectDestroy(ArrayList<SaboteurMove> destroy){
        if (destroy){

        }
    }
    public SaboteurMove selectDrop(ArrayList<SaboteurMove> drop){

    }*/
    public Scores heuristic(int[][] board, ArrayList<SaboteurMove> moves){
        ArrayList<Double> scores = new ArrayList<Double>();
        int[][] goals = new int[][]{ {12,7},{12,5},{12,3}};
        int[] origin = new int[]{(5*3)+1,(5*3)+1};
        int maxindex = 0;
        int minindex =0;
        double max = -1;
        double bonus = 0;
        double min = Integer.MAX_VALUE;
        boolean connected = false;

        for (int i = 0; i<moves.size();i++){
            SaboteurMove move = moves.get(i);
            //Compare move to other moves
            //Look at the board
            //Give higher weight to the revealed cards
            //Cardpath
            //Path to hidden
            //Drop card with lowest score
            int[] position = move.getPosPlayed();

            //System.out.println("position X:"+position[0] + "position Y:" + position[1]);
            SaboteurCard card = move.getCardPlayed();
            double[] distanceGoals = {euclideanDistance(position,goals[0]),euclideanDistance(position,goals[1]),
                    euclideanDistance(position,goals[2])};
            double mean = mean(distanceGoals);
            position[0] = (position[0]*3) + 1;
            position[1] = (position[1]*3) +1;

            if(pathToMe(board,origin,position)){
                bonus = -5;
                System.out.println("BonusConnect");
            }
            else{
                bonus = 0;
            }
            //Assign lower scores to cards with dead ends
            String idx = ((SaboteurTile)card).getIdx();
            switch(idx){
                case "10":
                    bonus = -5;
                case "9_flip":
                    bonus = -5;
                case "9":
                    bonus = -5;
                case "8":
                    bonus = -5;
                case "7":
                    bonus =-5;
                case "7_flip":
                    bonus=-5;
                case "6":
                    bonus =-5;
                case "6_flip":
                    bonus =-5;
                default:
                    bonus= 0;
            }


            scores.add(mean+bonus);
            System.out.println("Bonus: " + bonus);


            if(card.getName().equals("Map")){
                //always use a map card early
                //if opponent uses map card and starts building towards there, build to there as well.
                //or block them.
                minindex=i;
                min = -1;
                break;
            }
            if(mean > max && !card.getName().equals("Drop")){
                max = mean;
                maxindex=i;
            }
            if(mean < min && !card.getName().equals("Drop")){
                min = mean;
                minindex=i;
            }
        }
        //System.out.println(maxindex);
        Scores allscores = new Scores();
        allscores.max=maxindex;
        allscores.min=minindex;
        allscores.scores=scores;
        allscores.connected=true;
        return allscores;
    }
    public double euclideanDistance(int[] source, int[] destination){
        return Math.sqrt((source[1] - destination[1]) * (source[1] - destination[1]) + (source[0] - destination[0]) * (source[0] - destination[0]));
    }
    public double mean(double[] values){
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum / values.length;
    }
    //process move. If winning move take it.
    public boolean winningMove(Move move){
        return false;
    }

    //https://www.geeksforgeeks.org/find-whether-path-two-cells-matrix/



    public boolean pathToMe(int[][] board, int[] originPos, int[] targetPos){
        for (int i =0 ;i < board.length;i++){
            for (int j =0;j <board[i].length;j++){
                if(board[i][j] == -1 || board[i][j]==0){
                    board[i][j] = 0;
                }
                if(board[i][j]==1){
                    board[i][j]=3;
                }
            }
        }
        board[originPos[0]][originPos[1]] = 1;

        int endX = targetPos[0];
        int endY = targetPos[1];

        board[endX][endY]=2;
        board[endX+1][endY]=2;
        board[endX+2][endY]=2;
        board[endX][endY+1]=2;
        board[endX+1][endY+1]=2;
        board[endX+2][endY+1]=2;
        board[endX][endY+2]=2;
        board[endX+1][endY+2]=2;
        board[endX+2][endY+2]=2;

        boolean existPath = Path.isPath(board, board.length);

        return existPath;

    }


//best move is the one closest to an objective tile.
//Learn to drop cards depending on hand and map. Ie. if scores are below average. drop lowest.
//Or learn to sabotage
}