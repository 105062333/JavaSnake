package snake;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;


public class GamePanel extends JPanel{

    private Snake snake;
    private Snake2 snake2;
    private Egg food;
    private Egg2 food2;
    private Ground ground;
    private Hole hole;
    private Hole hole2;

    public void display(Snake snake,Snake2 snake2 ,Egg food,Egg2 food2 ,Ground ground, Hole hole, Hole hole2) {
        this.snake = snake;
        this.snake2 = snake2;
        this.food = food;
        this.food2 = food2;
        this.ground = ground;
        this.hole = hole;
        this.hole2 = hole2;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
       g.setColor(Color.lightGray);
        g.fillRect(0, 0, Global.WIDTH * Global.CELL_SIZE,Global.HEIGHT * Global.CELL_SIZE);
        if(ground != null && snake != null && food != null && food2 != null && snake2 != null && hole != null && hole2 != null) {
            this.ground.drawMe(g);
            this.hole.drawMe(g);
            this.hole2.drawMe(g);
            this.snake.drawMe(g);
            this.snake2.drawMe(g);
            this.food.drawMe(g);
            this.food2.drawMe(g);
        }
    }
}