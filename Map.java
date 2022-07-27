import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

public class Map extends Brick {
    private Brick[][] grid;
    private int width = 630;
    private int height = 150;
    private int rows;
    private int cols;
    // light, darker, darkest
    private int[][] colors = { { 229, 204, 255 }, { 204, 153, 255 }, { 194, 174, 235 } };

    public Map(int row, int col, int numHits) {
        super();
        this.rows = row;
        this.cols = col;
        grid = new Brick[row][col];
        int brickWidth = width / col;
        int brickHeight = height / row;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Brick b = new Brick(brickWidth, brickHeight, numHits);
                grid[i][j] = b;
            }
        }
    }

    public Map() {
        Brick b = new Map();
        showPolymorphism(this);
    }

    public void draw(Graphics2D g) {
        int randInt = (int) (Math.random() * 2 + 1);
        if (randInt == 1) {
            // row-major traversal
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    Brick brick = grid[i][j];
                    if (brick.getNumHits() >= 2) {
                        g.setColor(new Color(colors[1][0], colors[1][1], colors[1][2]));
                        g.fillRect(j * brick.getWidth() + 30, i * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                        g.setStroke(new BasicStroke(3));
                        g.setColor(new Color(colors[2][0], colors[2][1], colors[2][2]));
                        g.drawRect(j * brick.getWidth() + 30, i * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                    } else if (brick.getNumHits() == 1) {
                        g.setColor(new Color(colors[0][0], colors[0][1], colors[0][2]));
                        g.fillRect(j * brick.getWidth() + 30, i * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                        g.setStroke(new BasicStroke(3));
                        g.setColor(new Color(colors[2][0], colors[2][1], colors[2][2]));
                        g.drawRect(j * brick.getWidth() + 30, i * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                    }
                }
            }
        } else if (randInt == 2) {
            // column-major traversal
            for (int i = 0; i < grid[0].length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    Brick brick = grid[j][i];
                    if (brick.getNumHits() >= 2) {
                        g.setColor(new Color(colors[1][0], colors[1][1], colors[1][2]));
                        g.fillRect(i * brick.getWidth() + 30, j * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                        g.setStroke(new BasicStroke(3));
                        g.setColor(new Color(colors[2][0], colors[2][1], colors[2][2]));
                        g.drawRect(i * brick.getWidth() + 30, j * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                    } else if (brick.getNumHits() == 1) {
                        g.setColor(new Color(colors[0][0], colors[0][1], colors[0][2]));
                        g.fillRect(i * brick.getWidth() + 30, j * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                        g.setStroke(new BasicStroke(3));
                        g.setColor(new Color(colors[2][0], colors[2][1], colors[2][2]));
                        g.drawRect(i * brick.getWidth() + 30, j * brick.getHeight() + 40, brick.getWidth(),
                                brick.getHeight());
                    }
                }
            }
        }
    }

    public void showPolymorphism(Brick b) {
        System.out.println(b);
        Brick[] bricks = { new Map(), new Map() };
        ArrayList<Brick> moreBricks = new ArrayList<Brick>(Arrays.asList(new Map(), new Map()));
    }

    public String toString() {
        return "Width: " + width + "\nHeight: " + height + "\nRows: " + rows + "\nColumns: " + cols;
    }

    public boolean equals(Object other) {
        Map map2 = (Map) other;
        return width == map2.getWidth() && height == map2.getHeight() && rows == map2.getRows()
                && cols == map2.getCols();
    }

    public Brick[][] getGrid() {
        return grid;
    }

    public Brick[] getGrid(int n) {
        return grid[n];
    }

    public Brick getGrid(int a, int b) {
        return grid[a][b];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
