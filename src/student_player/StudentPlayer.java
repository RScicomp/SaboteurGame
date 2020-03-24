package student_player;

import Saboteur.SaboteurMove;
import boardgame.Move;
import Saboteur.cardClasses.SaboteurCard;
import student_player.MyTools.Path;

import Saboteur.SaboteurPlayer;
import Saboteur.SaboteurBoardState;
import Saboteur.cardClasses.SaboteurTile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
                //System.out.println(moves.get(i).toPrettyString());
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
    public ArrayList<String> getHand(ArrayList<SaboteurMove> moves){
        HashSet<String> hand = new HashSet<String>();
        ArrayList<String> handstr = new ArrayList<String>();
        for (int i =0; i < moves.size();i++){
            String cardname= moves.get(i).getCardPlayed().getName().split("_")[0];
            if (!hand.contains(cardname)){
                handstr.add(cardname);
            }else{
                hand.add(cardname);
            }
        }
        return handstr;
    }
    public int[] nuggetLocation(SaboteurTile[][] tileboard){
        int[] nuggetlocation = {-1,-1,-1};
        if(tileboard[12][7].getIdx().equals("nugget")){
            nuggetlocation[0] = 1;
        }else if (tileboard[12][7].getIdx().equals("hidden2")||tileboard[12][7].getIdx().equals("hidden1")){
            nuggetlocation[0]= 0;
        }
        if(tileboard[12][5].getIdx().equals("nugget")){
            nuggetlocation[1] = 1;
        }else if (tileboard[12][5].getIdx().equals("hidden2")||tileboard[12][5].getIdx().equals("hidden1")){
            nuggetlocation[1]= 0;
        }
        if(tileboard[12][3].getIdx().equals("nugget")){
            nuggetlocation[2] = 1;
        }else if (tileboard[12][3].getIdx().equals("hidden2")||tileboard[12][3].getIdx().equals("hidden1")){
            nuggetlocation[2]= 0;
        }


        return(nuggetlocation);

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
        int[] nuggetlocation = nuggetLocation(tileboard);
        //boardState.printBoard();

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
        Scores scores = heuristic(board,moves,nuggetlocation);
        SaboteurMove best_move = moves.get(scores.min);

        //If a deadend choose it to be the furthest.
        String index=best_move.getCardPlayed().getName();
        boolean deadend=false;
        switch(index){
            case "10":
                break;
            case "9_flip":
                break;
            case "9":
                break;
            case "8":
                break;
            case "7":
                break;
            case "7_flip":
                break;
            case "6":
                break;
            case "6_flip":
                break;
            default:
                //deadend=true;
        }// If deadend put it as far away as possible.
        /*if(scores.connected == false) {
            System.out.println("Not Connected or Deadend");
            best_move = moves.get(scores.max);
        }*/


        //If nothing connects, either drop or destroy
        //If the max is too big drop it.
        if(scores.max > 15-(20/boardState.getTurnNumber())){

        }

        int[] position = best_move.getPosPlayed();
        System.out.println("position X:"+((position[0]*3)+1) + "position Y:" + ((position[1]*3)+1));
        printallMoves(moves);
        //IDEA: Destroy the path closest to goal if you have a bad hand and later in the game.
        System.out.println("Heuristic Move: "+ best_move.toPrettyString());
        System.out.println("Int board width: " + boardState.getHiddenIntBoard().length + " Int board length: "+boardState.getHiddenIntBoard()[0].length);
        System.out.println("Tile board width: "+ tileboard.length + " Tile Board Length: "+tileboard[0].length);
        return best_move;
    }
    public void printallMoves(ArrayList<SaboteurMove> moves){
        for (int i =0;i < moves.size();i++){
            System.out.println("HAND: " + moves.get(i).toPrettyString());
        }

    }
    /*
    public SaboteurMove selectDestroy(ArrayList<SaboteurMove> destroy){
        if (destroy){

        }
    }
    public SaboteurMove selectDrop(ArrayList<SaboteurMove> drop){

    }*/
    /*
    public ArrayList<> populateGoals(int[] position,int[] goals){
        double[] distanceGoals
        for (int i =0; i< goals.length;i++) {
            double[] distanceGoals = {euclideanDistance(position, goals[0]), euclideanDistance(position, goals[1]),
                    euclideanDistance(position, goals[2])};
        }
    }*/
    public Scores heuristic(int[][] board, ArrayList<SaboteurMove> moves, int[] nuggetlocation){
        ArrayList<Double> scores = new ArrayList<Double>();
        int[][] goals = new int[][]{ {12,7},{12,5},{12,3}};
        int[] origin = new int[]{(5*3)+1,(5*3)+1};
        int[] goalbonus = new int[]{0,0,0};
        int goalindex = -1;
        int maxindex = 0;
        int minindex =0;
        double max = -1;
        double bonus = 0;
        double min = Integer.MAX_VALUE;
        boolean connected,maxconnected,minconnected = false;
        boolean deadend=false;
        boolean alldeadend = false;


        for(int i = 0; i <goals.length;i++){
            if(nuggetlocation[i]==1){
                goalindex=i;
            }
        }

        for (int i = 0; i<moves.size();i++){
            SaboteurMove move = moves.get(i);
            boolean above = false;
            boolean side = false;
            //Compare move to other moves
            //Look at the board
            //Give higher weight to the revealed cards
            //Cardpath
            //Path to hidden
            //Drop card with lowest score
            int[] position = move.getPosPlayed();
            if(position[0] < 12 ){
                above = true;
            }
            //System.out.println("position X:"+position[0] + "position Y:" + position[1]);

            //Move to goal states
            SaboteurCard card = move.getCardPlayed();
            double[] alldistanceGoals;
            if(goalindex == -1) {
                ArrayList<Double> distanceGoals2 = new ArrayList<Double>();
                for (int j= 0; j < nuggetlocation.length; j++){
                    if(nuggetlocation[j] == -1){
                        distanceGoals2.add(euclideanDistance(position,goals[j]));
                    }
                }
                double[] distanceGoals = distanceGoals2.stream().mapToDouble(Double::doubleValue).toArray();
                alldistanceGoals= distanceGoals;

            }else{
                double[] distanceGoals = {euclideanDistance(position,goals[goalindex])};
                alldistanceGoals=distanceGoals;
            }
            //System.out.println(alldistanceGoals[0] + " " + alldistanceGoals[1] + " " + alldistanceGoals[2]);
            double mean = mean(alldistanceGoals);

            position[0] = (position[0]*3) ;
            position[1] = (position[1]*3) ;
            connected = pathToMe(board,origin,position);
            if(connected){
                bonus = -5;
                System.out.println("BonusConnect");
            }
            else{
                bonus = 0;
            }
            if(goalindex != -1) {
                if (winningMove(board, origin, move, goals[goalindex])) {
                    bonus += -1000;
                    System.out.println("Winning MOVE!!!");
                }
            }
            //Assign lower scores to cards with dead ends
            if(!card.getName().equals("Map") && !card.getName().equals("Drop") && !card.getName().equals("Destroy") && !card.getName().equals("Bonus") && !card.getName().equals("Malus")) {
                //if pointing towards target. -> If above
                String idx = ((SaboteurTile) card).getIdx();
                switch (idx) {
                    case "10":
                        bonus += -2;
                    case "9_flip":
                        bonus += -5;
                    case "9":
                        bonus += -5;
                    case "8":
                        bonus += -5;
                    case "7":
                        bonus += -5;
                    case "7_flip":
                        bonus += -5;
                    case "6":
                        bonus += -5;
                    case "6_flip":
                        bonus += -5;
                    default:
                        bonus += 50;
                        deadend=true;

                }





            }

            mean = mean+bonus;
            scores.add(mean);
            System.out.println("Bonus: " + bonus);


            if(card.getName().equals("Map")){
                //always use a map card early
                //if opponent uses map card and starts building towards there, build to there as well.
                //or block them.
                minindex=i;
                min = i;
                System.out.println("MAP CARD!");
                break;

            }
            if(mean > max && !card.getName().equals("Drop")){
                max = mean;
                maxindex=i;
                maxconnected=connected;
            }
            if(mean < min && !card.getName().equals("Drop")){
                min = mean;
                minindex=i;
                minconnected=connected;
            }
        }

        //System.out.println(maxindex);
        Scores allscores = new Scores();
        allscores.max=maxindex;
        allscores.min=minindex;
        allscores.scores=scores;
        allscores.connected=minconnected;
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
    public boolean winningMove(int[][] boardog, int[] originPos, SaboteurMove move, int[] nuggetPosition){
        int[][] board =cloneArray(boardog);
        int[] moveposition = move.getPosPlayed();
        SaboteurCard played = move.getCardPlayed();
        int[][] tile =null;
        System.out.println("TILE NAME: " + played.getName());
        if(played.getName().contains("Tile")){
            tile=((SaboteurTile)played).getPath();
        }

        if(tile != null) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (i == moveposition[0] && j == moveposition[1]) {
                        board[i][j] = tile[0][0];
                        board[i + 1][j] = tile[1][0];
                        board[i + 2][j] = tile[2][0];
                        board[i][j + 1] = tile[0][1];
                        board[i + 1][j + 1] = tile[1][1];
                        board[i + 2][j + 1] = tile[2][1];
                        board[i][j + 2] = tile[0][2];
                        board[i + 1][j + 2] = tile[1][2];
                        board[i + 2][j + 2] = tile[2][2];
                    }
                    if (board[i][j] == -1 || board[i][j] == 0) {
                        board[i][j] = 0;
                    }
                    if (board[i][j] == 1) {
                        board[i][j] = 3;
                    }

                }
            }
            board[originPos[0]][originPos[1]] = 1;
            int endX = nuggetPosition[0]*3;
            int endY = nuggetPosition[1]*3;

            board[endX][endY] = 2;
            board[(endX + 1)][endY] = 2;
            board[(endX + 2)][endY] = 2;
            board[endX][endY + 1] = 2;
            board[endX + 1][endY + 1] = 2;
            board[endX + 2][endY + 1] = 2;
            board[endX][endY + 2] = 2;
            board[endX + 1][endY + 2] = 2;
            board[endX + 2][endY + 2] = 2;
            System.out.println("HYPO");
            print2d2(board);

            boolean existPath = Path.isPath(board, board.length);

            return existPath;
        }
        return false;
    }
    public void print2d(int[][] matrix){
        for (int i =0; i < matrix.length;i++){
            for (int j =0;j<matrix[i].length;j++){
                if(matrix[i][j]==0){
                    System.out.print(" "+matrix[i][j]+",");
                }else {
                    System.out.print(matrix[i][j]+",");
                }
            }
            System.out.println("");
        }
    }
    public void print2d2(int[][] matrix){
        for (int i =0; i < matrix.length;i++){
            for (int j =0;j<matrix[i].length;j++){
                System.out.print(matrix[i][j]+",");
            }
            System.out.println("");
        }
    }

    //https://www.geeksforgeeks.org/find-whether-path-two-cells-matrix/

    public static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }
    public boolean pathToMe(int[][] boardog, int[] originPos, int[] targetPos){
        int[][] board =cloneArray(boardog);
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
        board[(endX+1)][endY]=2;
        board[(endX+2)][endY]=2;

        board[endX][endY+1]=2;
        board[endX+1][endY+1]=2;
        board[endX+2][endY+1]=2;
        board[endX][endY+2]=2;
        board[endX+1][endY+2]=2;
        board[endX+2][endY+2]=2;

        //print2d2(board);
        //System.out.println("");
        boolean existPath = Path.isPath(board, board.length);

        return existPath;

    }


//best move is the one closest to an objective tile.
//Learn to drop cards depending on hand and map. Ie. if scores are below average. drop lowest.
//Or learn to sabotage
}