package snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class Controller extends KeyAdapter implements SnakeListener {

    private Snake snake;
    private Snake2 snake2;
    private Egg food;
    private Egg2 food2;
    private Ground ground;
    private GamePanel gamePanel;
    private Hole hole;
    private Hole hole2;
    public int score = 0;   //雙方分數
    public int score2 = 0;
    public int maxScore;    //龍虎榜分數
    public int maxScore2;
    public int maxScore3;
    public int maxScore4;
    public int maxScore5;
    public int botheat = 0; //是否兩顆蛋都被吃完
    public long startTime;
    public long endTime;
    public long startTime2;
    public long endTime2;
    public long startTime3;
    public long endTime3;
    public long startTime4;
    public long endTime4;
    public int ran;
    public int speednum;
    public Thread thread;
    public boolean selectmusic = false;
    public Clip clip;

    public Controller(Snake snake,Snake2 snake2,Egg food, Egg2 food2, Ground ground, GamePanel gamePanel, Hole hole, Hole hole2) {
        super();
        this.snake = snake;
        this.snake2 = snake2;
        this.food = food;
        this.food2 = food2;
        this.ground = ground;
        this.gamePanel = gamePanel;
        this.hole = hole;
        this.hole2 = hole2;
        readFile(); //每次開始遊戲讀取文件，目的是讀取最高分
    }
    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP://向上
                snake2.chanceDirection(Snake2.UP);
                break;
            case KeyEvent.VK_DOWN://向下
                snake2.chanceDirection(Snake2.DOWN);
                break;
            case KeyEvent.VK_LEFT://向左
                snake2.chanceDirection(Snake2.LEFT);
                break;
            case KeyEvent.VK_RIGHT://向右
                snake2.chanceDirection(Snake2.RIGHT);
                break;
            case KeyEvent.VK_SPACE://空白鍵來暫停遊戲
                snake.changePause();
                snake2.changePause();
                break;
            case KeyEvent.VK_SHIFT://Shift鍵來開始新遊戲
                newGame();
                break;
            case KeyEvent.VK_W://向上
                snake.chanceDirection(Snake.UP);
                break;
            case KeyEvent.VK_S://向下
                snake.chanceDirection(Snake.DOWN);
                break;
            case KeyEvent.VK_A://向左
                snake.chanceDirection(Snake.LEFT);
                break;
            case KeyEvent.VK_D://向右
                snake.chanceDirection(Snake.RIGHT);
                break;
            case KeyEvent.VK_PAGE_UP:  //加速
                if(speednum<4 && speednum>-4){  //最多加速到40
                    if(speednum!=3){
                        snake.speed -= 20;
                        snake2.speed -= 20;
                        speednum++;
                    }
                }
                break;
            case KeyEvent.VK_PAGE_DOWN: //減速
                if(speednum<4 && speednum>-4) { //最多減速到160
                    if(speednum!=-3){
                        snake.speed += 20;
                        snake2.speed += 20;
                        speednum--;
                    }
                }
                break;
            case KeyEvent.VK_O: //暫停背景音樂
                playmusic();
                break;
            case KeyEvent.VK_P:
                if(selectmusic){
                    if(clip.isActive())
                        clip.stop();
                    else
                        clip.start();
                }
                break;
        }
    }

    @Override
    public void snakeMove(Snake snake){

        if (Global.count - this.snake.body.size() - this.snake2.body.size() - ground.rocksCount < 3) {
            snake.die();
            snake2.die();
            snake.nextisDie = true;
            this.snake2.nextisDie = true;
            writeMaxScore();
            //彈出視窗提示遊戲結束，並顯示勝者及龍虎榜
            JOptionPane.showMessageDialog(gamePanel, "You have gotten the highest Score！\nScore："+ score+ "\nTOP1 Score: " + maxScore
                    + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
        }
        //蛇吃到食物，兩顆都被吃完隔兩秒就顯示新的食物
        if(food.isSnakeEatFood(snake) && !snake.invisible){
            snake.eatFood();
            this.score +=1;
            botheat++;
            if(botheat==2){
                startTime = System.currentTimeMillis();
            }
            food.newFood(snake.getTempFoodPoint());
        }
        if(food2.isSnakeEatFood(snake) && !snake.invisible){
            snake.eatFood();
            this.score +=1;
            botheat++;
            if(botheat==2){
                startTime = System.currentTimeMillis();
            }
            food2.newFood(snake.getTempFoodPoint());
        }
        endTime = System.currentTimeMillis();
        if(endTime-startTime>2000){
            if (botheat==2) {

                food.newFood(snake.getFoodPoint());
                food2.newFood(snake.getFoodPoint2());
                botheat = 0;
            }
        }

        //如果蛇撞到牆壁，有碰撞效果
        if (ground.isSnakeEatRock(snake) && !snake.invisible) {
            snake.die();
            int x[] = new int[snake.body.size()];
            int y[] = new int[snake.body.size()];
            int m[] = new int[snake2.body.size()];
            int n[] = new int[snake2.body.size()];
            for(int i=0;i<snake.body.size();i++){
                x[i] = snake.body.get(i).x;
                y[i] = snake.body.get(i).y;
            }
            for(int j=0;j<snake2.body.size();j++){
                m[j] = snake2.body.get(j).x;
                n[j] = snake2.body.get(j).y;
            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            this.snake2.nextisDie = true;
            //如果遊戲得分大於最高分，最高分就變，然後寫入文件
            writeMaxScore();
            JOptionPane.showMessageDialog(gamePanel, "P1 hit the Wall，So P2 WINS！\nWINNER Score："+ score2+ "\nTOP1 Score: " + maxScore
                    + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
        }
        //如果蛇吃到身體也死亡，並有碰撞效果
        if(snake.isEatBody() && !snake.invisible) {
            snake.die();
            int x[] = new int[snake.body.size()];
            int y[] = new int[snake.body.size()];
            int m[] = new int[snake2.body.size()];
            int n[] = new int[snake2.body.size()];
            for(int i=0;i<snake.body.size();i++){
                x[i] = snake.body.get(i).x;
                y[i] = snake.body.get(i).y;
            }
            for(int j=0;j<snake2.body.size();j++){
                m[j] = snake2.body.get(j).x;
                n[j] = snake2.body.get(j).y;
            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<snake2.body.size();j++){
                    snake2.body.get(j).x = m[j];
                    snake2.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            this.snake2.nextisDie = true;
            //如果遊戲得分大於最高分，最高分就變，然後寫入文件
            writeMaxScore();
            JOptionPane.showMessageDialog(gamePanel, "P1 bit Itself，So P2 WINS！\nWINNER Score："+ score2+ "\nTOP1 Score: " + maxScore
                    + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
        }
        //如果蛇進洞
        if(hole.isSnakeInHole(snake)){
            if(snake.invisible==false){

                for(int i=0;i<snake.body.size();i++){
                    try {
                        Thread.sleep(snake.speed);
                    }catch (Exception e){

                    }
                    snake.move();
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                snake.eatHole();
                startTime2 = System.currentTimeMillis();
            }
            else if(snake.invisible==true){
                snake.outHole();
            }
            else{
                snake.invisible = true;
            }
        }
        if(hole2.isSnakeInHole(snake)){
            if(snake.invisible==false){

                for(int i=0;i<snake.body.size();i++){
                    try {
                        Thread.sleep(snake.speed);
                    }catch (Exception e){

                    }
                    snake.move();
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                snake.eatHole();
                startTime2 = System.currentTimeMillis();
            }
            else if(snake.invisible==true){
                snake.outHole();
            }else{
                snake.invisible = true;
            }
        }
        //進洞時間為兩秒，洞口隨機選擇
        endTime2 = System.currentTimeMillis();
        if(endTime2-startTime2>2000 && snake.invisible){
            snake.invisible = false;
            ran = (int)(Math.random()*100);
            if(ran<50){
                for(int i=0;i<snake.body.size();i++){
                        snake.body.get(i).x = hole.x;
                        snake.body.get(i).y = hole.y;
                }
            } else{
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = hole2.x;
                    snake.body.get(i).y = hole2.y;
                }
            }

        }
        //處理蛇互撞的結果，頭對頭互撞就看蛇的大小
        if(!snake.invisible && !snake2.invisible){
            if(snake2.body.getFirst().equals(snake.body.get(1)) || snake2.body.get(1).equals(snake.body.getFirst()) || snake2.body.getFirst().equals(snake.body.getFirst())){
                if(snake.body.size()<snake2.body.size()){
                    snake.die();
                    snake2.nextisDie = true;
                    int x[] = new int[snake.body.size()];
                    int y[] = new int[snake.body.size()];
                    int m[] = new int[snake2.body.size()];
                    int n[] = new int[snake2.body.size()];
                    for(int i=0;i<snake.body.size();i++){
                        x[i] = snake.body.get(i).x;
                        y[i] = snake.body.get(i).y;
                    }
                    for(int j=0;j<snake2.body.size();j++){
                        m[j] = snake2.body.get(j).x;
                        n[j] = snake2.body.get(j).y;
                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    writeMaxScore();
                    JOptionPane.showMessageDialog(gamePanel, "P2 Bigger than P1，So P2 WINS！\nWINNER Score："+ score2+ "\nTOP1 Score: " + maxScore
                            + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
                }
                else if(snake.body.size()==snake2.body.size()){
                    snake.die();
                    snake2.die();
                    int x[] = new int[snake.body.size()];
                    int y[] = new int[snake.body.size()];
                    int m[] = new int[snake2.body.size()];
                    int n[] = new int[snake2.body.size()];
                    for(int i=0;i<snake.body.size();i++){
                        x[i] = snake.body.get(i).x;
                        y[i] = snake.body.get(i).y;
                    }
                    for(int j=0;j<snake2.body.size();j++){
                        m[j] = snake2.body.get(j).x;
                        n[j] = snake2.body.get(j).y;
                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = Global.WIDTH;
                            snake2.body.get(j).y = Global.HEIGHT;
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = Global.WIDTH;
                            snake2.body.get(j).y = Global.HEIGHT;
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = Global.WIDTH;
                            snake2.body.get(j).y = Global.HEIGHT;
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            snake2.body.get(j).x = m[j];
                            snake2.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    snake.nextisDie = true;
                    snake2.nextisDie = true;
                    writeMaxScore();
                    JOptionPane.showMessageDialog(gamePanel, "Both Snakes Died，No Winner！"+ "\nTOP1 Score: " + maxScore
                            + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
                }
            }
            else{
                for (int a = 1; a < snake2.body.size(); a++) {
                    if (snake2.body.get(a).equals(snake.body.getFirst())) {
                        snake.die();
                        snake2.nextisDie = true;
                        int x[] = new int[snake.body.size()];
                        int y[] = new int[snake.body.size()];
                        int m[] = new int[snake2.body.size()];
                        int n[] = new int[snake2.body.size()];
                        for(int i=0;i<snake.body.size();i++){
                            x[i] = snake.body.get(i).x;
                            y[i] = snake.body.get(i).y;
                        }
                        for(int j=0;j<snake2.body.size();j++){
                            m[j] = snake2.body.get(j).x;
                            n[j] = snake2.body.get(j).y;
                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = Global.WIDTH*2;
                                snake.body.get(i).y = Global.HEIGHT*2;
                            }
                            for(int j=0;j<snake2.body.size();j++){
                                snake2.body.get(j).x = m[j];
                                snake2.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = x[i];
                                snake.body.get(i).y = y[i];
                            }
                            for(int j=0;j<snake2.body.size();j++){
                                snake2.body.get(j).x = m[j];
                                snake2.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = Global.WIDTH*2;
                                snake.body.get(i).y = Global.HEIGHT*2;
                            }
                            for(int j=0;j<snake2.body.size();j++){
                                snake2.body.get(j).x = m[j];
                                snake2.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = x[i];
                                snake.body.get(i).y = y[i];
                            }
                            for(int j=0;j<snake2.body.size();j++){
                                snake2.body.get(j).x = m[j];
                                snake2.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = Global.WIDTH*2;
                                snake.body.get(i).y = Global.HEIGHT*2;
                            }
                            for(int j=0;j<snake2.body.size();j++){
                                snake2.body.get(j).x = m[j];
                                snake2.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = x[i];
                                snake.body.get(i).y = y[i];
                            }
                            for(int j=0;j<snake2.body.size();j++){
                                snake2.body.get(j).x = m[j];
                                snake2.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        writeMaxScore();
                        JOptionPane.showMessageDialog(gamePanel, "P1 Collide with P2，So P2 WINS！\nWINNER Score："+ score2+ "\nTOP1 Score: " + maxScore
                                + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
                        break;
                    }
                }
            }
        }
        //如果蛇死亡就不刷新畫面，且關閉背景音樂
        try {
            Thread.sleep(1);
        }catch (Exception e){

        }
        if (!(snake.isDie) && !(snake.nextisDie)) {
            gamePanel.display(snake,this.snake2 ,food, food2, ground, hole, hole2);
        }
        else{
            if(selectmusic)
                clip.close();
        }
    }

    public void snakeMove2(Snake2 snake){

        if (Global.count - this.snake.body.size() - this.snake2.body.size() - ground.rocksCount < 3) {
            snake.die();
            this.snake.die();
            snake.nextisDie = true;
            this.snake.nextisDie = true;
            writeMaxScore();
            //彈出視窗提示遊戲結束，並顯示勝者及龍虎榜
            JOptionPane.showMessageDialog(gamePanel, "You have gotten the highest Score！\nScore："+ score+ "\nTOP1 Score: " + maxScore
                    + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
        }
        //蛇吃到食物，兩顆都被吃完隔兩秒就顯示新的食物
        if(food.isSnake2EatFood(snake) && !snake.invisible){
            snake.eatFood();
            this.score2 +=1;
            botheat++;
            if(botheat==2){
                startTime = System.currentTimeMillis();
            }
            food.newFood(this.snake.getTempFoodPoint());
        }

        if(food2.isSnake2EatFood(snake) && !snake.invisible){
            snake.eatFood();
            this.score2 +=1;
            botheat++;
            if(botheat==2){
                startTime = System.currentTimeMillis();
            }
            food2.newFood(this.snake.getTempFoodPoint());
        }
        endTime = System.currentTimeMillis();
        if(endTime-startTime>2000){
            if (botheat==2) {

                food.newFood(this.snake.getFoodPoint());
                food2.newFood(this.snake.getFoodPoint2());
                botheat = 0;
            }
        }

        //如果蛇撞到牆壁，有碰撞效果
        if (ground.isSnake2EatRock(snake) && !snake.invisible) {
            snake.die();
            int x[] = new int[snake.body.size()];
            int y[] = new int[snake.body.size()];
            int m[] = new int[this.snake.body.size()];
            int n[] = new int[this.snake.body.size()];
            for(int i=0;i<snake.body.size();i++){
                x[i] = snake.body.get(i).x;
                y[i] = snake.body.get(i).y;
            }
            for(int j=0;j<this.snake.body.size();j++){
                m[j] = this.snake.body.get(j).x;
                n[j] = this.snake.body.get(j).y;
            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            this.snake.nextisDie = true;
            writeMaxScore();
            JOptionPane.showMessageDialog(gamePanel, "P2 hit the Wall，So P1 WINS！\nWINNER Score："+ score+ "\nTOP1 Score: " + maxScore
                    + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
        }
        //如果蛇吃到身體就死亡並有碰撞效果
        if(snake.isEatBody() && !snake.invisible) {
            snake.die();
            int x[] = new int[snake.body.size()];
            int y[] = new int[snake.body.size()];
            int m[] = new int[this.snake.body.size()];
            int n[] = new int[this.snake.body.size()];
            for(int i=0;i<snake.body.size();i++){
                x[i] = snake.body.get(i).x;
                y[i] = snake.body.get(i).y;
            }
            for(int j=0;j<this.snake.body.size();j++){
                m[j] = this.snake.body.get(j).x;
                n[j] = this.snake.body.get(j).y;
            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            try {
                Thread.sleep(100);
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = x[i];
                    snake.body.get(i).y = y[i];
                }
                for(int j=0;j<this.snake.body.size();j++){
                    this.snake.body.get(j).x = m[j];
                    this.snake.body.get(j).y = n[j];
                }
            }catch (Exception e){

            }
            this.snake.nextisDie = true;
            writeMaxScore();
            JOptionPane.showMessageDialog(gamePanel, "P2 bit Itself，So P1 WINS！\nWINNER Score："+ score+ "\nTOP1 Score: " + maxScore
                    + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
         }
        //蛇進洞
        if(hole.isSnake2InHole(snake)){
            if(snake.invisible==false){

                for(int i=0;i<snake.body.size();i++){
                    try {
                        Thread.sleep(snake.speed);
                    }catch (Exception e){

                    }
                    snake.move();
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                snake.eatHole();
                startTime3 = System.currentTimeMillis();
            }
            else if(snake.invisible==true){
                snake.outHole();
            }
            else{
                snake.invisible = true;
            }
        }
        if(hole2.isSnake2InHole(snake)){
            if(snake.invisible==false){

                for(int i=0;i<snake.body.size();i++){
                    try {
                        Thread.sleep(snake.speed);
                    }catch (Exception e){

                    }
                    snake.move();
                    snake.body.get(i).x = Global.WIDTH*2;
                    snake.body.get(i).y = Global.HEIGHT*2;
                }
                snake.eatHole();
                startTime3 = System.currentTimeMillis();
            }
            else if(snake.invisible==true){
                snake.outHole();
            }else{
                snake.invisible = true;
            }
        }
        //進洞時間為兩秒，出洞隨機選擇
        endTime3 = System.currentTimeMillis();
        if(endTime3-startTime3>2000 && snake.invisible){
            snake.invisible = false;
            ran = (int)(Math.random()*100);
            if(ran<50){
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = hole.x;
                    snake.body.get(i).y = hole.y;
                }
            } else{
                for(int i=0;i<snake.body.size();i++){
                    snake.body.get(i).x = hole2.x;
                    snake.body.get(i).y = hole2.y;
                }
            }
        }
        //處理蛇互撞的結果，頭對頭互撞就看蛇的大小
        if(!snake.invisible && !this.snake.invisible){
            if(this.snake.body.getFirst().equals(snake.body.get(1)) || this.snake.body.get(1).equals(snake.body.getFirst()) || this.snake.body.getFirst().equals(snake.body.getFirst())){
                if(snake.body.size()<this.snake.body.size()){
                    snake.die();
                    this.snake.nextisDie = true;
                    int x[] = new int[snake.body.size()];
                    int y[] = new int[snake.body.size()];
                    int m[] = new int[this.snake.body.size()];
                    int n[] = new int[this.snake.body.size()];
                    for(int i=0;i<snake.body.size();i++){
                        x[i] = snake.body.get(i).x;
                        y[i] = snake.body.get(i).y;
                    }
                    for(int j=0;j<this.snake.body.size();j++){
                        m[j] = this.snake.body.get(j).x;
                        n[j] = this.snake.body.get(j).y;
                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<this.snake.body.size();j++){
                            this.snake.body.get(j).x = m[j];
                            this.snake.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<this.snake.body.size();j++){
                            this.snake.body.get(j).x = m[j];
                            this.snake.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<this.snake.body.size();j++){
                            this.snake.body.get(j).x = m[j];
                            this.snake.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<this.snake.body.size();j++){
                            this.snake.body.get(j).x = m[j];
                            this.snake.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = Global.WIDTH*2;
                            snake.body.get(i).y = Global.HEIGHT*2;
                        }
                        for(int j=0;j<this.snake.body.size();j++){
                            this.snake.body.get(j).x = m[j];
                            this.snake.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(100);
                        for(int i=0;i<snake.body.size();i++){
                            snake.body.get(i).x = x[i];
                            snake.body.get(i).y = y[i];
                        }
                        for(int j=0;j<this.snake.body.size();j++){
                            this.snake.body.get(j).x = m[j];
                            this.snake.body.get(j).y = n[j];
                        }
                    }catch (Exception e){

                    }
                    writeMaxScore();
                    JOptionPane.showMessageDialog(gamePanel, "P1 Bigger than P2，So P1 WINS！\nWINNER Score："+ score+ "\nTOP1 Score: " + maxScore
                            + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
                }
            }
            else{
                for (int a = 1; a < this.snake.body.size(); a++) {
                    if (this.snake.body.get(a).equals(snake.body.getFirst())) {
                        snake.die();
                        this.snake.nextisDie = true;
                        int x[] = new int[snake.body.size()];
                        int y[] = new int[snake.body.size()];
                        int m[] = new int[this.snake.body.size()];
                        int n[] = new int[this.snake.body.size()];
                        for(int i=0;i<snake.body.size();i++){
                            x[i] = snake.body.get(i).x;
                            y[i] = snake.body.get(i).y;
                        }
                        for(int j=0;j<this.snake.body.size();j++){
                            m[j] = this.snake.body.get(j).x;
                            n[j] = this.snake.body.get(j).y;
                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = Global.WIDTH*2;
                                snake.body.get(i).y = Global.HEIGHT*2;
                            }
                            for(int j=0;j<this.snake.body.size();j++){
                                this.snake.body.get(j).x = m[j];
                                this.snake.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = x[i];
                                snake.body.get(i).y = y[i];
                            }
                            for(int j=0;j<this.snake.body.size();j++){
                                this.snake.body.get(j).x = m[j];
                                this.snake.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = Global.WIDTH*2;
                                snake.body.get(i).y = Global.HEIGHT*2;
                            }
                            for(int j=0;j<this.snake.body.size();j++){
                                this.snake.body.get(j).x = m[j];
                                this.snake.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = x[i];
                                snake.body.get(i).y = y[i];
                            }
                            for(int j=0;j<this.snake.body.size();j++){
                                this.snake.body.get(j).x = m[j];
                                this.snake.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = Global.WIDTH*2;
                                snake.body.get(i).y = Global.HEIGHT*2;
                            }
                            for(int j=0;j<this.snake.body.size();j++){
                                this.snake.body.get(j).x = m[j];
                                this.snake.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        try {
                            Thread.sleep(100);
                            for(int i=0;i<snake.body.size();i++){
                                snake.body.get(i).x = x[i];
                                snake.body.get(i).y = y[i];
                            }
                            for(int j=0;j<this.snake.body.size();j++){
                                this.snake.body.get(j).x = m[j];
                                this.snake.body.get(j).y = n[j];
                            }
                        }catch (Exception e){

                        }
                        writeMaxScore();
                        JOptionPane.showMessageDialog(gamePanel, "P2 Collide with P1，So P1 WINS！\nWINNER Score："+ score + "\nTOP1 Score: " + maxScore
                                + "\nTOP2 Score: " + maxScore2+ "\nTOP3 Score: " + maxScore3+ "\nTOP4 Score: " + maxScore4+ "\nTOP5 Score: " + maxScore5);
                        break;
                    }
                }
            }
        }
        try {
            Thread.sleep(1);
        }catch (Exception e){

        }
        //如果蛇死亡就不刷新畫面，且關閉背景音樂
        if (!(snake.isDie) && !(snake.nextisDie)) {
            gamePanel.display(this.snake, snake,food, food2, ground, hole, hole2);
        }
        else{
            if(selectmusic)
                clip.close();
        }
    }
    public void beginGame() {
        score = 0;
        score2 = 0;
        botheat = 0;
        startTime = 0;
        endTime = 0;
        startTime2 = 0;
        endTime2 = 0;
        startTime3 = 0;
        endTime3 = 0;
        startTime4 = 0;
        endTime4 = 0;
        speednum = 0;
        //每次開始讀取文件，獲得最高分
        readFile();
        food.newFood(snake.getFoodPoint());
        food2.newFood(snake.getFoodPoint2());
        hole.newHole(snake.getHolePoint());
        hole2.newHole(snake.getHolePoint());
        //用來更新分數
        new Thread(thread).start();
    }

    public void newGame() {
        snake.bodyClear();
        snake2.bodyClear();
        snake.init();
        snake2.init();
        score = 0;
        score2 = 0;
        food.newFood(snake.getFoodPoint());
        food2.newFood(snake.getFoodPoint2());
        hole.newHole(snake.getHolePoint());
        hole2.newHole(snake.getHolePoint());
        botheat = 0;
        ground.init();
        startTime2 = 0;
        endTime2 = 0;
        speednum = 0;
        //如果其中一條蛇死亡就重新監聽，不是則否
        if (snake.isDie || snake2.isDie) {
            snake.start();
            snake2.start();
            beginGame();
            snake.isDie = false;
            snake2.isDie = false;
        }
    }

    //讀取文件，獲得龍虎榜
    public void readFile(){
        File file = new File("src\\MaxScore.txt");
        //如果文件不存在，文件输出流会自动创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader br;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"));
            String str = String.valueOf(maxScore);
            String str5 = String.valueOf(maxScore5);
            String str4 = String.valueOf(maxScore4);
            String str3 = String.valueOf(maxScore3);
            String str2 = String.valueOf(maxScore2);
            str = String.valueOf(br.readLine());
            str2 = String.valueOf(br.readLine());
            str3 = String.valueOf(br.readLine());
            str4 = String.valueOf(br.readLine());
            str5 = String.valueOf(br.readLine());
            maxScore = Integer.parseInt(str);
            maxScore2 = Integer.parseInt(str2);
            maxScore3 = Integer.parseInt(str3);
            maxScore4 = Integer.parseInt(str4);
            maxScore5 = Integer.parseInt(str5);
            br.close();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //重排前五高分
    public void writeMaxScore() {
        if (score > maxScore5) {
            if(score > maxScore4){
                if(score > maxScore3){
                    if(score > maxScore2){
                        if(score > maxScore){
                            maxScore5 = maxScore4;
                            maxScore4 = maxScore3;
                            maxScore3 = maxScore2;
                            maxScore2 = maxScore;
                            maxScore = score;
                        }
                        else{
                            maxScore5 = maxScore4;
                            maxScore4 = maxScore3;
                            maxScore3 = maxScore2;
                            maxScore2 = score;
                        }
                    }
                    else{
                        maxScore5 = maxScore4;
                        maxScore4 = maxScore3;
                        maxScore3 = score;
                    }
                }
                else{
                    maxScore5 = maxScore4;
                    maxScore4 = score;
                }
            }
            else{
                maxScore5 = score;
            }
            writeFile();
        }
        else if(score2 > maxScore5){
            if(score2 > maxScore4){
                if(score2 > maxScore3){
                    if(score2 > maxScore2){
                        if(score2 > maxScore){
                            maxScore5 = maxScore4;
                            maxScore4 = maxScore3;
                            maxScore3 = maxScore2;
                            maxScore2 = maxScore;
                            maxScore = score2;
                        }
                        else{
                            maxScore5 = maxScore4;
                            maxScore4 = maxScore3;
                            maxScore3 = maxScore2;
                            maxScore2 = score2;
                        }
                    }
                    else{
                        maxScore5 = maxScore4;
                        maxScore4 = maxScore3;
                        maxScore3 = score2;
                    }
                }
                else{
                    maxScore5 = maxScore4;
                    maxScore4 = score2;
                }
            }
            else{
                maxScore5 = score2;
            }
            writeFile();
        }
    }
    public void writeFile() {
        File file = new File("src\\MaxScore.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file), "UTF-8"));
            bw.write(String.valueOf(maxScore));
            bw.newLine();
            bw.write(String.valueOf(maxScore2));
            bw.newLine();
            bw.write(String.valueOf(maxScore3));
            bw.newLine();
            bw.write(String.valueOf(maxScore4));
            bw.newLine();
            bw.write(String.valueOf(maxScore5));
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //播放背景音樂
    public void playmusic(){
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            AudioInputStream audioInputStream;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(selectedFile);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                selectmusic = true;
            }catch (Exception e){

            }
        }

    }
    //刷新介面的線程
    public Thread startRefresh(Thread thread) {
        this.thread = thread;
        return this.thread;
    }
}