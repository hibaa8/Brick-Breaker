
public class Brick {
  private int width;
  private int height;
  private int numHits;

  public Brick(int width, int height, int numHits) {
    this.width = width;
    this.height = height;
    this.numHits = numHits;
  }

  public Brick() {
  }

  public int getNumHits() {
    return numHits;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setNumHits(int val) {
    numHits = val;
  }
}