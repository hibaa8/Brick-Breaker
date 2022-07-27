
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.File;

public class Game extends JPanel implements KeyListener, ActionListener {
  private boolean play;
  private int score;
  private int numBricks = 24;
  private Timer timer;
  private int delay = 2;
  private int player = 310;
  private int ballX = 250;
  private int ballY = 350;
  private double ballDirX = -4;
  private double ballDirY = -5;
  private Map grid;
  private int lives = 3;
  private int level = 1;
  private boolean finish;
  private int paddle_width = 180;
  private ArrayList<Integer> playerScores = new ArrayList<>();
  private LoginPage login;
  private boolean winGame;
  private String title;

  public Game() {
    super();
  }

  public Game(LoginPage loginPage) {
    super(false);
    title = "Brick Breaker";
    login = loginPage;
    grid = new Map(3, 8, 1);
    super.addKeyListener(this);
    super.setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
    playNoise("bgm.wav");
  }

  public static void playNoise(String musicLocation) {
    try {
      File musicPath = new File(musicLocation);
      if (musicPath.exists()) {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        if (musicLocation.equals("bgm.wav")) {
          clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
      } else {
        System.out.println("can't find file");
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void paint(Graphics g) {
    g.setColor(new Color(204, 229, 255)); // bg color
    g.fillRect(1, 1, 692, 592);
    grid.draw((Graphics2D) g);
    g.setColor(Color.black); // border color
    g.fillRect(0, 0, 3, 592);
    g.fillRect(0, 0, 692, 3);
    g.fillRect(691, 0, 3, 592);
    g.setFont(new Font("serif", Font.BOLD, 25));

    g.setColor(Color.black); // real time score
    g.drawString("Score: " + score, 560, 30);

    g.setColor(Color.black); // lives tracker
    g.drawString("Lives: " + lives, 20, 30);

    g.setColor(Color.black); // level
    g.drawString("Level: " + level, 300, 30);

    g.setColor(Color.black); // paddle
    g.fillRect(player, 550, paddle_width, 8);

    g.setColor(Color.white); // ball
    g.fillOval(ballX, ballY, 20, 20);

    if (ballY > 570) {
      play = false;
      ballDirX = 0;
      ballDirY = 0;
      playerScores.add(score);

      g.setColor(new Color(51, 153, 255));
      g.setFont(new Font("american typewriter", Font.BOLD, 30));
      g.drawString("Game Over", 270, 300);
      if (lives == 0) {
        g.drawString("You lose", 290, 340);
        g.setFont(new Font("american typewriter", Font.ITALIC, 25));
        if (winGame) {
          g.drawString("Press 1 to Play Level 1", 240, 290);
          g.drawString("Press 2 to Play Level 2", 240, 320);
          g.drawString("Press 3 to Play Level 3", 240, 350);
        } else {
          int hs = highestScore(playerScores);
          int ls = lowestScore(playerScores);
          g.drawString("Highest Score: " + hs, 276, 380);
          g.drawString("Lowest Score: " + ls, 278, 400);
          for (int i = 0; i < playerScores.size(); i++) {
            int score = playerScores.get(i);
            if (score != hs && score != ls) {
              playerScores.remove(score);
              i--;
            }
          }
          playerScores.set(0, 0);
        }
      } else {
        g.drawString("Score: " + score, 275, 340);
        g.setFont(new Font("american typewriter", Font.ITALIC, 20));
        g.drawString("Press Enter to Restart", 250, 370);
      }

    }
    if (numBricks == 0) {
      play = false;
      finish = true;
      ballDirX = -1;
      ballDirY = -2;

      g.setColor(new Color(1, 153, 255));
      if (level == 3) {
        winGame = true;
        g.setFont(new Font("american typewriter", Font.BOLD, 40));
        g.drawString("You Win", 265, 250);
        g.setFont(new Font("american typewriter", Font.ITALIC, 20));
        g.drawString("Press 1 to Play Level 1", 240, 290);
        g.drawString("Press 2 to Play Level 2", 240, 320);
        g.drawString("Press 3 to Play Level 3", 240, 350);
      } else {
        g.setFont(new Font("american typewriter", Font.BOLD, 30));
        g.drawString("Level Completed", 240, 300);
        g.drawString("Score: " + score, 275, 340);
        if (level <= 2) {
          g.setFont(new Font("american typewriter", Font.ITALIC, 20));
          g.drawString("Press Enter to Continue", 250, 370);
        }
      }
    }
    g.dispose();
  }

  public void actionPerformed(ActionEvent e) {
    timer.start();
    if (play) {
      if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(player, 550, paddle_width, 8))) {
        ballDirY = -1 * ballDirY;
      }
      for (int i = 0; i < grid.getGrid().length; i++) {
        for (int j = 0; j < grid.getGrid(0).length; j++) {
          Brick brick = grid.getGrid(i, j);
          if (grid.getGrid(i, j).getNumHits() > 0) {
            int brickX = j * brick.getWidth() + 30;
            int brickY = i * brick.getHeight() + 50;
            int brickWidth = brick.getWidth();
            int brickHeight = brick.getHeight();

            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
            Rectangle ball = new Rectangle(ballX, ballY, 20, 20);
            Rectangle brickRect = rect;
            if (ball.intersects(brickRect)) {
              playNoise("pop.wav");
              brick.setNumHits(brick.getNumHits() - 1);
              numBricks--;
              score += 5;
              if (ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickWidth) {
                ballDirX = -1 * ballDirX;
              } else {
                ballDirY = -1 * ballDirY;
              }
            }
          }
        }
      }
      ballX += ballDirX;
      ballY += ballDirY;
      if (ballX < 0) {
        ballDirX = -1 * ballDirX;
      }
      if (ballY < 0) {
        ballDirY = -1 * ballDirY;
      }
      if (ballX > 670) {
        ballDirX = -1 * ballDirX;
      }
    }
    repaint();
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
      if (player + paddle_width >= 700) {
        player = 700 - paddle_width;
      } else {
        right();
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
      if (player <= 0) {
        player = 0;
      } else {
        left();
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (finish) {
        newLevel();
      } else if (!play && lives > 0) {
        restart(level);
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_1 && winGame) {
      lives = 4;
      restart(1);
    }
    if (e.getKeyCode() == KeyEvent.VK_2 && winGame) {
      lives = 4;
      restart(2);
    }
    if (e.getKeyCode() == KeyEvent.VK_3 && winGame) {
      lives = 4;
      restart(3);
    }
  }

  public void restart(int level) {
    finish = false;
    lives--;
    ballX = 250;
    ballY = 350;
    score = 0;
    player = 310;
    if (level == 1) {
      ballDirX = -4;
      ballDirY = -5;
      numBricks = 24;
      paddle_width = 180;
      grid = new Map(3, 8, 1);
    } else if (level == 2) {
      ballDirX = -4.5;
      ballDirY = -5.5;
      numBricks = 72;
      paddle_width = 170;
      grid = new Map(4, 9, 2);
    } else if (level == 3) {
      ballDirX = -5;
      ballDirY = -6;
      numBricks = 100;
      paddle_width = 150;
      grid = new Map(5, 10, 2);
    }
    repaint();
  }

  public void newLevel() {
    finish = false;
    level++;
    lives = 3;
    ballX = 250;
    ballY = 350;
    score = 0;
    player = 310;
    if (level == 2) {
      grid = new Map(4, 9, 2);
      ballDirX = -4.5;
      ballDirY = -5.5;
      numBricks = 72;
      paddle_width = 170;
    } else if (level == 3) {
      grid = new Map(5, 10, 2);
      ballDirX = -5;
      ballDirY = -6;
      numBricks = 100;
      paddle_width = 150;
    }
    repaint();
  }

  static int selectionCount = 0;
  static int insertionCount = 0;

  // selection sort
  public static int highestScore(ArrayList<Integer> list) {

    for (int i = 0; i < list.size() - 1; i++) {
      int min = i;
      for (int j = i + 1; j < list.size(); j++) {
        if (list.get(j) < list.get(min)) {
          min = j;
        }
      }
      if (i != min) {
        int temp = list.get(min);
        list.set(min, list.get(i));
        list.set(i, temp);
        selectionCount++;
      }
    }
    return list.get(list.size() - 1);
  }

  // insertion sort
  public static int lowestScore(ArrayList<Integer> list) {
    for (int i = 1; i < list.size(); i++) {
      int key = list.get(i);
      int j = i - 1;
      while (j >= 0 && list.get(j) > key) {
        list.set(j + 1, list.get(j));
        j--;
        insertionCount++;
      }
      list.set(j + 1, key);
    }
    return list.get(0);
  }

  public void keyTyped(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

  public void right() {
    play = true;
    if (level == 1) {
      player += 20;
    } else if (level == 2) {
      player += 40;
    } else {
      player += 50;
    }
  }

  public void left() {
    play = true;
    if (level == 1) {
      player -= 20;
    } else if (level == 2) {
      player -= 40;
    } else {
      player -= 50;
    }
  }

  public String toString() {
    return "Level: " + level + "\nLives: " + lives + "\nScore: " + score + "Number of bricks: " + numBricks
        + "Ball position: " + ballX + ", " + ballY;
  }

  public boolean equals(Object other) {
    Game game2 = (Game) other;
    return game2.getTitle().equals(title);
  }

  public String getTitle() {
    return title;
  }
}