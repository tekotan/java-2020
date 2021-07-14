import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Assignment5 {
    public static void main(String[] args){
        MazeMaker k = new MazeMaker(15, 15);
        k.carvePassages();
        k.display();
    }
}

class Maze {
    public static final int N = 1;
    public static final int S = 2;
    public static final int E = 4;
    public static final int W = 8;

    protected Random random = null;
    protected int width = 0;
    protected int height = 0;
    protected int[][] grid = null;

    public static int dx(int direction) {
        switch (direction) {
            case Maze.E:
                return +1;
            case Maze.W:
                return -1;
            case Maze.N:
            case Maze.S:
                return 0;
        }
        return -1;
    }

    public static int dy(int direction) {
        switch (direction) {
            case Maze.E:
            case Maze.W:
                return 0;
            case Maze.N:
                return -1;
            case Maze.S:
                return 1;
        }
        return -1;
    }

    public static int opposite(int direction) {
        switch (direction) {
            case Maze.E:
                return Maze.W;
            case Maze.W:
                return Maze.E;
            case Maze.N:
                return Maze.S;
            case Maze.S:
                return Maze.N;
        }
        return -1;
    }

    public Maze(int w, int h) {
        initialize(w, h);
        random = new Random();
    }

    private void initialize(int w, int h) {
        this.width = w;
        this.height = h;
        grid = new int[h][w];
        for (int j = 0; j < h; ++j) {
            for (int i = 0; i < w; ++i) {
                grid[j][i] = 0;
            }
        }
    }


    public void draw() {
        System.out.print(" ");
        for (int i = 0; i < (width * 2 - 1); ++i) {
            System.out.print("_");
        }
        System.out.println("");

        for (int j = 0; j < height; ++j) {
            System.out.print("|");
            for (int i = 0; i < width; ++i) {
                System.out.print((grid[j][i] & Maze.S) != 0 ? " " : "_");

                if ((grid[j][i] & Maze.E) != 0) {
                    System.out.print(((grid[j][i] | grid[j][i + 1]) & Maze.S) != 0 ? " " : "_");
                } else {
                    System.out.print("|");
                }
            }
            System.out.println("");
        }
    }
}

class MazeMaker extends Maze {
    private List<List<Tree>> sets;
    private Stack<Edge> edges;

    public MazeMaker(int w, int h) {
        super(w, h);
        initialize();
    }
    private void initialize() {

        sets = new ArrayList<>();
        for (int y = 0; y < height; ++y) {
            List<Tree> tmp = new ArrayList<>();
            for (int x = 0; x < width; ++x) {
                tmp.add(new Tree());
            }
            sets.add(tmp);
        }

        edges = new Stack<>();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                if (y > 0) {
                    edges.add(new Edge(x, y, Maze.N));
                }
                if (x > 0) {
                    edges.add(new Edge(x, y, Maze.W));
                }
            }
        }
        shuffle(edges);
    }

    public void display() {
        System.out.print(" ");
        for (int i = 0; i < (width * 2) - 1; ++i) {
            System.out.print("_");
        }
        System.out.println("");

        for (int y = 0; y < grid.length; ++y) {
            System.out.print("|");
            for (int x = 0; x < grid[0].length; ++x) {
                System.out.print(((grid[y][x] & Maze.S) != 0) ? " " : "_");
                if ((grid[y][x] & Maze.E) != 0) {
                    System.out.print((((grid[y][x] | grid[y][x + 1]) & Maze.S) != 0) ? " " : "_");
                } else {
                    System.out.print("|");
                }
            }
            System.out.println("");
        }
    }

    public void carvePassages() {
        while (!edges.isEmpty()) {
            Edge tmp = edges.pop();
            int x = tmp.getX();
            int y = tmp.getY();
            int direction = tmp.getDirection();
            int dx = x + Maze.dx(direction), dy = y + Maze.dy(direction);

            Tree set1 = (sets.get(y)).get(x);
            Tree set2 = (sets.get(dy)).get(dx);

            if (!set1.connected(set2)) {
                set1.connect(set2);
                grid[y][x] |= direction;
                grid[dy][dx] |= Maze.opposite(direction);
            }
        }
    }

    private void shuffle(List<Edge> args) {
        for (int i = 0; i < args.size(); ++i) {
            int pos = random.nextInt(args.size());
            Edge tmp1 = args.get(i);
            Edge tmp2 = args.get(pos);
            args.set(i, tmp2);
            args.set(pos, tmp1);
        }
    }
}

class Tree {

    private Tree parent = null;
    public Tree root() {
        return parent != null ? parent.root() : this;
    }

    public boolean connected(Tree tree) {
        return this.root() == tree.root();
    }

    public void connect(Tree tree) {
        tree.root().setParent(this);
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }
}

class Edge {
    private int x;
    private int y;
    private int direction;

    public Edge(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }
}
