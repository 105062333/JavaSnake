package snake;

import java.awt.*;
public class Hole extends Point{
    Point point = null;
    public void newHole(Point p) {
        this.point = p;
        this.setLocation(p);
    }
    //判斷蛇是否進洞
    public boolean isSnakeInHole(Snake snake) {
        return point.equals(snake.getHead());
    }

    public boolean isSnake2InHole(Snake2 snake) {
        return point.equals(snake.getHead());
    }

    public void drawMe(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval(point.x * Global.CELL_SIZE-1, point.y * Global.CELL_SIZE-1, Global.CELL_SIZE+3, Global.CELL_SIZE+3);
        g.setColor(Color.BLACK);
        //g.fill3DRect(x * Global.CELL_SIZE, y * Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE, false);
       // g.fill3DRect(point.x * Global.CELL_SIZE, point.y * Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE, false);
        g.fillOval(point.x * Global.CELL_SIZE+1, point.y * Global.CELL_SIZE+1, Global.CELL_SIZE-2, Global.CELL_SIZE-2);

    }
}
