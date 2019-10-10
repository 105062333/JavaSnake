package snake;

import java.awt.*;

public class Egg extends Point{
    Point point = null;
    public void newFood(Point p) {
        this.point = p;
        this.setLocation(p);
    }

    //判斷蛇是否吃到食物
    public boolean isSnakeEatFood(Snake snake) {
        return point.equals(snake.getHead());
    }
    public boolean isSnake2EatFood(Snake2 snake) {
        return point.equals(snake.getHead());
    }

    public void drawMe(Graphics g) {

        Color color = new Color(139,105,20);
        g.setColor(color);
        g.fillRect(point.x * Global.CELL_SIZE+7, point.y * Global.CELL_SIZE-5, Global.CELL_SIZE/8, Global.CELL_SIZE-2);
        g.setColor(Color.RED);
        g.fillOval(point.x * Global.CELL_SIZE, point.y * Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE-3);

    }

}
