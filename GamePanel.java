package My2D;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48 * 48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth =  tileSize * maxScreenCol; //768 pixel
    final int screenHeight = tileSize * maxScreenRow; //576 pixel

    int FPS = 60;

    KeyHandler KeyH = new KeyHandler();
    Thread gameThread;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS; // 0.1666 s
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void update(){

        if (KeyH.upPressed == true){
            playerY -= playerSpeed;}
        else if (KeyH.downPressed == true) {
            playerY += playerSpeed;
        } else if (KeyH.leftPressed == true) {
            playerX -= playerSpeed;
        } else if (KeyH.rightPressed == true) {
            playerX += playerSpeed;
        }

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.blue);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
