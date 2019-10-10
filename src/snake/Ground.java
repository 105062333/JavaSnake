package snake;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ground {

    private static final int rocks[][] = new int[Global.WIDTH][Global.HEIGHT];
    public int rocksCount = 0;
    public Ground() {
        init();
    }
    public void clear() {
        for (int x = 0; x < Global.WIDTH; x++)
            for (int y = 0; y < Global.HEIGHT; y++)
                rocks[x][y] = 0;
    }
    public void init() {
        clear();
        map();
        getScoksCount();
    }

    //隨機地圖，至少產生兩面牆
    public void map() {
        int x = 0,y = 0;
        int enx = 0,eny = 0;
        for(int i=0;i<3;i++){
            x = (int)(Math.random()*(Global.WIDTH-2)+1);
            enx = (int)(Math.random()*(Global.WIDTH-2)+1);
            y = (int)(Math.random()*(Global.HEIGHT-2)+1);
            eny = (int)(Math.random()*(Global.HEIGHT-2)+1);
            if(x>enx){
                for(int n=enx;n<=x;n++){
                    if(n==Global.WIDTH/2-3 || n==Global.WIDTH/2-4 || n==Global.WIDTH/2-5 || n==Global.WIDTH/2-2 || n==Global.WIDTH/2-1 && (y == Global.HEIGHT / 2 || y==Global.HEIGHT/2 + 5))
                        rocks[n][y] = 0;
                    else
                        rocks[n][y] = 1;
                }
            }
            else{
                for(int n=x;n<=enx;n++){
                    if(n==Global.WIDTH/2-3 || n==Global.WIDTH/2-4 || n==Global.WIDTH/2-5 || n==Global.WIDTH/2-2 || n==Global.WIDTH/2-1 && (y == Global.HEIGHT / 2 || y==Global.HEIGHT/2 + 5))
                        rocks[n][y] = 0;
                    else
                        rocks[n][y] = 1;
                }
            }
            if(y>eny){
                for(int m=eny;m<=y;m++){
                    if(x==Global.WIDTH/2-3 || x==Global.WIDTH/2-4 || x==Global.WIDTH/2-5 || x==Global.WIDTH/2-2 || x==Global.WIDTH/2-1 && (m == Global.HEIGHT / 2 || m==Global.HEIGHT/2 + 5))
                        rocks[x][m] = 0;
                    else
                        rocks[x][m] = 1;
                }
            }
            else{
                for(int m=y;m<=eny;m++){
                    if(x==Global.WIDTH/2-3 || x==Global.WIDTH/2-4 || x==Global.WIDTH/2-5 || x==Global.WIDTH/2-2 || x==Global.WIDTH/2-1 && (m == Global.HEIGHT / 2 || m==Global.HEIGHT/2 + 5))
                        rocks[x][m] = 0;
                    else
                        rocks[x][m] = 1;
                }
            }

        }
    }

    //獲得總數
    public void getScoksCount() {
        rocksCount = 0;
        for (int x = 0; x < Global.WIDTH; x++)
            for (int y = 0; y < Global.HEIGHT; y++)
                if (rocks[x][y] == 1) {
                    rocksCount++;
                }
    }
    //判断蛇是否撞到牆
    public boolean isSnakeEatRock(Snake snake) {
        for(int x = 0; x < Global.WIDTH; x++) {
            for (int y = 0; y < Global.HEIGHT; y++) {
                if (rocks[x][y] == 1 && x == snake.getHead().x && y == snake.getHead().y) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isSnake2EatRock(Snake2 snake) {
        for(int x = 0; x < Global.WIDTH; x++) {
            for (int y = 0; y < Global.HEIGHT; y++) {
                if (rocks[x][y] == 1
                        && x == snake.getHead().x
                        && y == snake.getHead().y) {
                    return true;
                }
            }
        }
        return false;
    }
    //獲得不會與牆壁重複的座標
    public Point getPoint() {
        int x = 0, y = 0;
        do{
            x = (int)(Math.random()*Global.WIDTH);
            y = (int)(Math.random()*Global.HEIGHT);
        }while(rocks[x][y] == 1);
        return new Point(x, y);
    }
    public Point getPoint2() {
        int m = 0, n = 0;
        do{
            m = (int)(Math.random()*Global.WIDTH);
            n = (int)(Math.random()*Global.HEIGHT);
        }while(rocks[m][n] == 1);
        return new Point(m, n);
    }
    public void drawMe(Graphics g) {
        drawRocks(g);
    }
    public void drawRocks(Graphics g) {
        for(int x = 0; x < Global.WIDTH; x++) {
            for (int y = 0; y < Global.HEIGHT; y++) {
                if (rocks[x][y] == 1) {
                    Color color = new Color(34,139,34);
                    g.setColor(color);
                    g.fill3DRect(x * Global.CELL_SIZE, y * Global.CELL_SIZE,
                            Global.CELL_SIZE, Global.CELL_SIZE, true);
                }
            }
        }
    }

}