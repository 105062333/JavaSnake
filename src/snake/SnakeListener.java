package snake;

import snake.Snake;
import snake.Snake2;

//蛇移動的監聽器
public interface SnakeListener{

    void snakeMove(Snake snake);
    void snakeMove2(Snake2 snake);
}