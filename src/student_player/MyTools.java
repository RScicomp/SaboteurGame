package student_player;

public class MyTools {
    /*
    public static class Maze{
        int height;
        int width;
        int[][] maze;// = new int[width][height]; // The maze
        boolean[][] wasHere; //= new boolean[width][height];
        boolean[][] correctPath; // = new boolean[width][height]; // The solution to the maze
        int startX, startY; // Starting X and Y values of maze
        int endX, endY;     // Ending X and Y values of maze
        public Maze(int[][] maze,int endX, int endY){
            for(int i =0 ;i < maze.length;i++){
                for (int j=0;j<maze[i].length;j++){
                    if(maze[i][j] == -1) {
                        maze[i][j] = 2;
                    }
                    if(maze[i][j]==0){
                        maze[i][j] = 2;
                    }
                }
            }
            this.maze = maze;
            this.maze[endX][endY]=1;
            this.maze[endX+1][endY]=1;
            this.maze[endX+2][endY]=1;
            this.maze[endX][endY+1]=1;
            this.maze[endX+1][endY+1]=1;
            this.maze[endX+2][endY+1]=1;
            this.maze[endX][endY+2]=1;
            this.maze[endX+1][endY+2]=1;
            this.maze[endX+2][endY+2]=1;

            this.width = maze.length;
            this.height = maze[0].length;
            this.wasHere = new boolean[width][height];
            this.correctPath= new boolean[width][height];
            //printMaze();
        }
        public void printMaze(){
            for (int i = 0; i < maze.length;i++){
                for(int j = 0; j < maze[i].length;j++){
                    System.out.print(maze[i][j]);
                }
                System.out.println("");
            }

        }
        public void solveMaze() {
            //maze = generateMaze(); // Create Maze (1 = path, 2 = wall)
            for (int row = 0; row < maze.length; row++)
                // Sets boolean Arrays to default values
                for (int col = 0; col < maze[row].length; col++){
                    wasHere[row][col] = false;
                    correctPath[row][col] = false;
                }
            boolean b = recursiveSolve(startX, startY);
            // Will leave you with a boolean array (correctPath)
            // with the path indicated by true values.
            // If b is false, there is no solution to the maze
        }
        public boolean recursiveSolve(int x, int y) {
            if (x == endX && y == endY) return true; // If you reached the end
            if (maze[x][y] == 2 || wasHere[x][y]) return false;
            // If you are on a wall or already were here
            wasHere[x][y] = true;
            if (x != 0) // Checks if not on left edge
                if (recursiveSolve(x-1, y)) { // Recalls method one to the left
                    correctPath[x][y] = true; // Sets that path value to true;
                    return true;
                }
            if (x != width - 1) // Checks if not on right edge
                if (recursiveSolve(x+1, y)) { // Recalls method one to the right
                    correctPath[x][y] = true;
                    return true;
                }
            if (y != 0)  // Checks if not on top edge
                if (recursiveSolve(x, y-1)) { // Recalls method one up
                    correctPath[x][y] = true;
                    return true;
                }
            if (y != height - 1) // Checks if not on bottom edge
                if (recursiveSolve(x, y+1)) { // Recalls method one down
                    correctPath[x][y] = true;
                    return true;
                }
            return false;
        }
    }
    public static double getSomething() {

        return 1;
    }*/
    static class Path {

        // method for finding and printing
        // whether the path exists or not

        public static boolean isPath(int matrix[][], int n)
        {
            // defining visited array to keep
            // track of already visited indexes
            boolean visited[][] = new boolean[n][n];

            // flag to indicate whether the path exists or not
            boolean flag=false;
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    // if matrix[i][j] is source
                    // and it is not visited
                    if(matrix[i][j]==1 && !visited[i][j])

                        // starting from i, j and then finding the path
                        if(isPath(matrix, i, j, visited))
                        {
                            flag=true; // if path exists
                            break;
                        }
                }
            }
            if(flag)
                return true;
            else
                return false;
        }


        // method for checking boundries
        public static boolean isSafe(int i, int j, int matrix[][])
        {

            if(i>=0 && i<matrix.length && j>=0
                    && j<matrix[0].length)
                return true;
            return false;
        }

        // Returns true if there is a path from a source (a
        // cell with value 1) to a destination (a cell with
        // value 2)
        public static boolean isPath(int matrix[][],
                                     int i, int j, boolean visited[][]){

            // checking the boundries, walls and
            // whether the cell is unvisited
            if(isSafe(i, j, matrix) && matrix[i][j]!=0
                    && !visited[i][j])
            {
                // make the cell visited
                visited[i][j]=true;

                // if the cell is the required
                // destination then return true
                if(matrix[i][j]==2)
                    return true;

                // traverse up
                boolean up = isPath(matrix, i-1, j, visited);

                // if path is found in up direction return true
                if(up)
                    return true;

                // traverse left
                boolean left = isPath(matrix, i, j-1, visited);

                // if path is found in left direction return true
                if(left)
                    return true;

                //traverse down
                boolean down = isPath(matrix, i+1, j, visited);

                // if path is found in down direction return true
                if(down)
                    return true;

                // traverse right
                boolean right = isPath(matrix, i, j+1, visited);

                // if path is found in right direction return true
                if(right)
                    return true;
            }
            return false; // no path has been found
        }

        // driver program to check above function


    }
}