package snake;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Snake2 {
    //定義方向變量
    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = -2;

    //定義一個舊的方向及新的方向，在改變方向時判斷新方向與舊方向是否相同，不同才改變
    private int oldDirection, newDirection;

    Ground ground = new Ground();
    public int snakeBodyCount; //蛇身總長度
    private Point oldTail; //存放尾巴的坐标
    private boolean life; //判断蛇是否活着
    private boolean pause; //蛇是否暂停
    private boolean isPause; //開局時為暫停狀態
    public boolean isDie; //判斷蛇是否死亡
    public boolean nextisDie; //對方的蛇是否死亡
    public boolean invisible; //是否在黑洞裡
    public int speed = 0; //蛇速度
    //存放蛇身體座標
    public LinkedList<Point> body = new LinkedList<Point>();
    //監聽蛇
    private Set<SnakeListener> listener = new HashSet<SnakeListener>();

    //構造方法，初始化
    public Snake2() {
        init();
    }

    public void init() {
        int x = Global.WIDTH/ 2 - 3;
        int y = Global.HEIGHT / 2 + 5;
        //初始化蛇，長度為二
        for(int i = 0; i < 3; i++) {
            body.addLast(new Point(x--, y));
        }
        oldDirection = newDirection = RIGHT;//初始化方向，向右
        life = true;
        pause = false;
        isPause = true;
        speed = 100;
        nextisDie = false;
        invisible = false;
    }

    public void move() {
        if (!(oldDirection + newDirection == 0)) {
            oldDirection = newDirection;
        }

        oldTail = body.removeLast(); //去尾，達到移動的目的
        int x = body.getFirst().x;
        int y = body.getFirst().y;
        switch(oldDirection) {
            case UP: //向上
                y--;
                //到邊上可以從另一邊出現
                if (y < 0) {
                    y = Global.HEIGHT - 1;
                }
                break;
            case DOWN: //向下
                y++;
                //到邊上可以從另一邊出現
                if (y >= Global.HEIGHT) {
                    y = 0;
                }
                break;
            case LEFT: //向左
                x--;
                //到邊上可以從另一邊出現
                if (x < 0) {
                    x = Global.WIDTH - 1;
                }
                break;
            case RIGHT: //向右
                x++;
                //到邊上可以從另一邊出現
                if (x >= Global.WIDTH) {
                    x = 0;
                }
                break;

        }

        Point newHead = new Point(x, y); //紀錄蛇頭座標
        body.addFirst(newHead); //加頭
    }
    //蛇改變方向
    public void chanceDirection(int direction) {
        newDirection = direction;
    }
    //蛇吃食物
    public void eatFood() {
        //添加刪去的最後尾巴節點，所以吃到食物會加長一格
        body.addLast(oldTail);
    }
    //判斷蛇是否吃到身體
    public boolean isEatBody() {
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(body.getFirst())) {
                return true;
            }
        }
        return false;
    }
    //判斷蛇是否進洞
    public void eatHole(){
        invisible = true;
    }
    //判斷蛇是否出洞
    public void outHole(){
        invisible = false;
    }

    //畫蛇
    public void drawMe(Graphics g) {
        if(!invisible){
            for(Point p : body) {
                g.setColor(Color.yellow);
                g.fillRect(p.x * Global.CELL_SIZE, p.y * Global.CELL_SIZE,
                        Global.CELL_SIZE, Global.CELL_SIZE);
            }
            if(oldDirection==RIGHT) {
                g.setColor(Color.WHITE);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10, body.getFirst().y * Global.CELL_SIZE,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10, body.getFirst().y * Global.CELL_SIZE+10,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.setColor(Color.BLACK);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10+2, body.getFirst().y * Global.CELL_SIZE+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10+2, body.getFirst().y * Global.CELL_SIZE+10+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
            }
            else if(oldDirection==LEFT){
                g.setColor(Color.WHITE);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE, body.getFirst().y * Global.CELL_SIZE,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE, body.getFirst().y * Global.CELL_SIZE+10,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.setColor(Color.BLACK);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+2, body.getFirst().y * Global.CELL_SIZE+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+2, body.getFirst().y * Global.CELL_SIZE+10+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
            }
            else if(oldDirection==UP){
                g.setColor(Color.WHITE);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE, body.getFirst().y * Global.CELL_SIZE,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10, body.getFirst().y * Global.CELL_SIZE,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.setColor(Color.BLACK);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+2, body.getFirst().y * Global.CELL_SIZE+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10+2, body.getFirst().y * Global.CELL_SIZE+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
            }
            else if(oldDirection==DOWN){
                g.setColor(Color.WHITE);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE, body.getFirst().y * Global.CELL_SIZE+10,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10, body.getFirst().y * Global.CELL_SIZE+10,
                        Global.CELL_SIZE/2, Global.CELL_SIZE/2);
                g.setColor(Color.BLACK);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+2, body.getFirst().y * Global.CELL_SIZE+10+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
                g.fillOval(body.getFirst().x * Global.CELL_SIZE+10+2, body.getFirst().y * Global.CELL_SIZE+10+2,
                        Global.CELL_SIZE/2-5, Global.CELL_SIZE/2-5);
            }
        }
        else{   //蛇進洞後設為背景顏色
            for(Point p : body) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(p.x * Global.CELL_SIZE, p.y * Global.CELL_SIZE,
                        Global.CELL_SIZE, Global.CELL_SIZE);
            }
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(body.getFirst().x * Global.CELL_SIZE, body.getFirst().y * Global.CELL_SIZE,
                    Global.CELL_SIZE, Global.CELL_SIZE);
        }
    }
    //獲得蛇頭座標
    public Point getHead() {
        return body.getFirst();
    }
    //蛇死亡
    public void die() {
        life = false;
        isDie = true;
    }
    //讓蛇移動
    public class SnakerDriver implements Runnable{
        public void run() {
            while(life && !nextisDie) {
                if (!pause) {
                    move();
                    for(SnakeListener l : listener) {
                        l.snakeMove2(Snake2.this);
                    }
                    if (isPause) {
                        pause = true;
                        isPause = false;
                    }
                }
                try {
                    //定時移動
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //讓蛇開始移動
    public void start() {
        new Thread(new SnakerDriver()).start();
    }

    //添加監聽器
    public void addSnakeListener(SnakeListener l) {
        if(l != null) {
            this.listener.add(l);
        }
    }
    public void getSnakeBodyCount() {
        snakeBodyCount = body.size();
    }
    //改變蛇暫停狀態
    public void changePause() {
        pause = !pause;
    }
    //清除所有節點
    public void bodyClear() {
        body.clear();
    }
}