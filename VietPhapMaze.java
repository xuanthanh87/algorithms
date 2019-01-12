import javafx.util.Pair;

import java.util.*;

public class VietPhapMaze {

    static final String HASH_CHAR = "#";
    static final String DOT_CHAR = ".";
    String[][] maze;
    Set<Point> realVertices;
    Stack<Edge> edges = new Stack<>();

    public VietPhapMaze(String[][] maze) {
        this.maze = maze;

         realVertices = detectPoints();

        //weight
        //for (Point p : realVertices
        //    ) {
        Point start = new Point(0, 1);
        LinkedList<Point> path = new LinkedList<>();
        List<Pair<Point, Point>> parent = new ArrayList<>();
        parent.add(new Pair<>(start, null));
        Stack<Point> paths = new Stack<>();
        paths.push(start);
        Stack<Edge> edges = new Stack<>();
        Set<Point> visited = new HashSet<Point>();
        dfs(start, visited, paths, edges);
        //bfs(start)

        System.out.println("==================");

        while (!paths.isEmpty()){
            System.out.println(paths.pop());
        }



        //}

    }


    private void dfs(Point p, Set<Point> visited,Stack<Point> path,List<Edge> edges) {
        visited.add(p);
        if (realVertices.contains(p)){
            path.push(p);
    }
        List<Point> adjs = getNextMoves(p,visited);

        for (Point temp : adjs
                ) {
            if(!visited.contains(temp)){
                if(realVertices.contains(temp)){
                    //build edge
                    Point toV = temp;
                    edges.add(new Edge(path.peek(), toV, 0));
                    //path.push(toV);
                }

                dfs(temp,visited,path,edges);

            }
        }

        if(realVertices.contains(p)) {
            path.pop();
        }


    }


    private   Set<Edge>  bfs(Point start){
        LinkedList<Point> points = new LinkedList<>();
        Set<Edge> edges = new HashSet<>();
        Set<Point> visited = new HashSet<>();
        visited.add(start);
        points.add(start);
        Stack<Point> stack = new Stack<>();
        stack.push(start);
        while (!points.isEmpty()){
            start = points.poll();

            List<Point> next = getNextMoves(start,visited);
            for (Point p: next
                 ) {
                if(!visited.contains(p)) {

                    if (realVertices.contains(start) && realVertices.contains(p))
                        edges.add(new Edge(stack.peek(), p, 0));
                    }

                    stack.push(p);
                    visited.add(p);
                    points.add(p);
                }
            }


        return edges;
    }

    private Set<Point> detectPoints() {
        Set<Point> points = new HashSet<>();
        int row = maze.length;
        int col = maze[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (maze[i][j].equals(DOT_CHAR)) {
                    Point p = new Point(i, j);
                    int degree = getDegree(p);
                    if(degree % 2 != 0){
                        points.add(p);
                    } else {
                        System.out.println(p);
                    }
                }
            }
        }
        return points;
    }



    private List<Point> getNextMoves(Point curentP, Set<Point> visited) {

        int w = maze.length;
        int h = maze[0].length;

        //just allowed to move by vertical or horizontal
        final List<Point> moves = new ArrayList<>();


        //left
        if (curentP.y - 1 >= 0 && !visited.contains(new Point(curentP.x, curentP.y - 1)) && !maze[curentP.x][curentP.y - 1].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x, curentP.y - 1));
        }

        //up
        if (curentP.x - 1 >= 0 && !visited.contains(new Point(curentP.x - 1, curentP.y)) && !maze[curentP.x - 1][curentP.y].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x - 1, curentP.y));
        }


        //right
        if (curentP.y + 1 < h && !visited.contains(new Point(curentP.x, curentP.y + 1)) && !maze[curentP.x][curentP.y + 1].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x, curentP.y + 1));
        }

        //down
        if (curentP.x + 1 < w && !visited.contains(new Point(curentP.x + 1, curentP.y)) && !maze[curentP.x + 1][curentP.y].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x + 1, curentP.y));
        }

        return moves;
    }

    /**
     * Possible moves : left-up-right-down
     *
     * @param curentP
     * @return
     */
    private int getDegree(Point curentP) {

        int w = maze.length;
        int h = maze[0].length;
        if(curentP.equals(new Point(0,0))){
            System.out.println("df");
        }

        //just allowed to move by vertical or horizontal
        final List<Point> moves = new ArrayList<>();
        //up
        if (curentP.x - 1 >= 0  && !maze[curentP.x - 1][curentP.y].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x - 1, curentP.y));
        }
        //left
        if (curentP.y - 1 >= 0 && !maze[curentP.x][curentP.y - 1].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x, curentP.y - 1));
        }
        //right
        if (curentP.y + 1 < h &&  !maze[curentP.x][curentP.y + 1].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x, curentP.y + 1));
        }
        //down
        if (curentP.x + 1 < w && !maze[curentP.x + 1][curentP.y].equals(HASH_CHAR)) {
            moves.add(new Point(curentP.x + 1, curentP.y));
        }
        return moves.size();
    }


static class Edge {
    Point from;
    Point to;
    int w;


    public Edge(Point from, Point to, int w) {
        this.from = from;
        this.to = to;
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return w == edge.w &&
                Objects.equals(from, edge.from) &&
                Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {

        return Objects.hash(from, to, w);
    }
}




static class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}

    private Set<Point> expected(){
        Set<Point> expected = new HashSet<>();
        expected.add(new Point(0,1));


        return expected;
    }

    public static void main(String... s) {

        String[][] maze = new String[][]{
                {"#", ".", "#", "#", "#", "#", "#", "#"},
                {"#", ".", "#", "#", "#", "#", "#", "#"},
                {"#", ".", ".", ".", ".", ".", ".", "#"},
                {"#", ".", "#", "#", "#", ".", "#", "#"},
                {"#", ".", ".", "#", "#", ".", "#", "#"},
                {"#", ".", "#", "#", "#", ".", "#", "#"},
                {"#", ".", ".", ".", ".", ".", ".", "."},
                {"#", ".", "#", "#", "#", "#", "#", "#"},
                {"#", ".", "#", "#", "#", ".", "#", "#"},
                {"#", ".", ".", ".", ".", ".", ".", "#"},
                {"#", "#", "#", "#", "#", "#", "#", "#"}
        };

        VietPhapMaze vietPhapMaze = new VietPhapMaze(maze);
        Set<Point> actual = vietPhapMaze.realVertices;
        Set<Point> expected = vietPhapMaze.expected();
        System.out.println(expected.containsAll(actual));
    }
}