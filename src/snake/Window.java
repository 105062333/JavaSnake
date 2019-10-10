package snake;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class Window extends JFrame{
    protected static final Object SnakeListener = null;
    private JPanel contentPane;
    Snake snake = new Snake();
    Snake2 snake2 = new Snake2();
    Egg food = new Egg();
    Egg2 food2 = new Egg2();
    Ground ground = new Ground();
    Hole hole = new Hole();
    Hole hole2 = new Hole();
    public JTextField txt_score;
    private JTextField txt_speed;
    private JTextField txt_maxScore;
    public JTextField txt_score2;
    private JTextField txt_speed2;
    private JTextField txt_maxScore2;
    public JLabel label_play;
    public JLabel label_play2;
    public JButton button_pause;
    public JButton button_music;
    public JButton button_select;

    GamePanel gamePanel = new GamePanel();
    Controller controller = new Controller(snake, snake2, food, food2 ,ground, gamePanel, hole, hole2);

    public Window() {
        setResizable(false);
        setTitle("貪吃蛇");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocation(getToolkit().getScreenSize().width / 2 - Global.CELL_SIZE * Global.WIDTH / 2,
                getToolkit().getScreenSize().height / 2 - Global.CELL_SIZE * Global.WIDTH / 2);

        setSize(823, 760);
        addKeyListener(controller);
        contentPane = new JPanel();
        contentPane.setFocusCycleRoot(true);
        contentPane.setFocusTraversalPolicyProvider(true);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setFocusCycleRoot(true);
        panel.setFocusTraversalPolicyProvider(true);
        gamePanel.setFocusTraversalPolicyProvider(true);
        gamePanel.setFocusCycleRoot(true);


        gamePanel.setSize(Global.CELL_SIZE * Global.WIDTH, Global.CELL_SIZE * Global.HEIGHT);
        gamePanel.setLayout(new BorderLayout(0, 0));
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addComponent(gamePanel, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addComponent(gamePanel, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );
        panel.setLayout(gl_panel);

        JPanel panel_1 = new JPanel();
        panel_1.setFocusable(false);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 801, Short.MAX_VALUE)
                                .addGap(10))
                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 795, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 505, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 205, Short.MAX_VALUE)
                                .addContainerGap())
        );

        JPanel lable = new JPanel();
        lable.setFocusable(false);
        lable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        JPanel panel_control = new JPanel();
        panel_control.setFocusable(false);
        panel_control.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        JPanel panel_setSpeed = new JPanel();
        panel_setSpeed.setFocusable(false);
        panel_setSpeed.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        JPanel panel_set = new JPanel();
        panel_set.setFocusable(false);
        panel_set.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        JPanel panel_display = new JPanel();
        panel_display.setFocusable(false);
        panel_display.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(panel_set, GroupLayout.PREFERRED_SIZE,
                                                216, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(panel_setSpeed, 0, 0, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)

                                .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(panel_display, GroupLayout.PREFERRED_SIZE,
                                                216, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(panel_control, 0, 0, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGap(29)
                                .addComponent(lable, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(29))
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(panel_set,
                                                        GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(panel_setSpeed,
                                                        GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(panel_display,
                                                        GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(panel_control,
                                                        GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE))
                                        .addComponent(lable, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                                .addContainerGap())
        );

        JLabel lable_score = new JLabel("Now Scores");
        lable_score.setFocusable(false);
        lable_score.setHorizontalAlignment(SwingConstants.LEFT);
        lable_score.setHorizontalTextPosition(SwingConstants.CENTER);
        lable_score.setAlignmentX(Component.CENTER_ALIGNMENT);



        txt_score = new JTextField();
        txt_score.setText("0 Score");
        txt_score.setEditable(false);
        txt_score.setFocusable(false);
        txt_score.setColumns(10);

        JLabel label_maxScore = new JLabel("Highest");
        label_maxScore.setFocusable(false);

        txt_maxScore = new JTextField();
        txt_maxScore.setText(controller.maxScore + " Score");
        txt_maxScore.setEditable(false);
        txt_maxScore.setFocusable(false);
        txt_maxScore.setColumns(10);

        JLabel label_speed = new JLabel("Now Speed");
        label_speed.setFocusable(false);

        txt_speed = new JTextField();
        txt_speed.setText(snake.speed + " ms / cell");
        txt_speed.setEditable(false);
        txt_speed.setFocusable(false);
        lable_score.setLabelFor(txt_speed);
        txt_speed.setColumns(10);

        label_play = new JLabel("                        Player 2");
        label_play.setFocusable(false);

        GroupLayout gl_panel_display = new GroupLayout(panel_display);
        gl_panel_display.setHorizontalGroup(
                gl_panel_display.createParallelGroup(Alignment.LEADING)
                        .addComponent(label_play, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel_display.createSequentialGroup()
                                .addGap(6)
                                .addGroup(gl_panel_display.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lable_score,
                                                GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_maxScore,
                                                GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_speed,
                                                GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panel_display.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(txt_maxScore,
                                                GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                        .addComponent(txt_speed,
                                                GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                        .addComponent(txt_score))
                                .addContainerGap(26, Short.MAX_VALUE))
        );
        gl_panel_display.setVerticalGroup(
                gl_panel_display.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_display.createSequentialGroup()
                                .addComponent(label_play, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE)
                                .addGroup(gl_panel_display.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel_display.createSequentialGroup()
                                                .addComponent(txt_score, GroupLayout.PREFERRED_SIZE,
                                                        21, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(txt_maxScore, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(txt_speed, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(16))
                                        .addGroup(gl_panel_display.createSequentialGroup()
                                                .addComponent(lable_score, GroupLayout.PREFERRED_SIZE,
                                                        18, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(label_maxScore, GroupLayout.PREFERRED_SIZE,
                                                        25, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(label_speed)
                                                .addGap(21))))
        );
        panel_display.setLayout(gl_panel_display);


        JLabel lable_score2 = new JLabel("Now Scores");
        lable_score2.setFocusable(false);
        lable_score2.setHorizontalAlignment(SwingConstants.LEFT);
        lable_score2.setHorizontalTextPosition(SwingConstants.CENTER);
        lable_score2.setAlignmentX(Component.CENTER_ALIGNMENT);



        txt_score2 = new JTextField();
        txt_score2.setText("0 Score");
        txt_score2.setEditable(false);
        txt_score2.setFocusable(false);
        txt_score2.setColumns(10);

        JLabel label_maxScore2 = new JLabel("Highest");
        label_maxScore2.setFocusable(false);

        txt_maxScore2 = new JTextField();
        txt_maxScore2.setText(controller.maxScore + " Score");
        txt_maxScore2.setEditable(false);
        txt_maxScore2.setFocusable(false);
        txt_maxScore2.setColumns(10);

        JLabel label_speed2 = new JLabel("Now Speed");
        label_speed2.setFocusable(false);

        txt_speed2 = new JTextField();
        txt_speed2.setText(snake.speed + " ms / cell");
        txt_speed2.setEditable(false);
        txt_speed2.setFocusable(false);
        lable_score2.setLabelFor(txt_speed2);
        txt_speed2.setColumns(10);

        label_play2 = new JLabel("                        Player 1");
        label_play2.setFocusable(false);
        GroupLayout gl_panel_set = new GroupLayout(panel_set);
        gl_panel_set.setHorizontalGroup(
                gl_panel_set.createParallelGroup(Alignment.LEADING)
                        .addComponent(label_play2, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel_set.createSequentialGroup()
                                .addGap(6)
                                .addGroup(gl_panel_set.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lable_score2,
                                                GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_maxScore2,
                                                GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label_speed2,
                                                GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panel_set.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(txt_score2,
                                                GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                        .addComponent(txt_maxScore2,
                                                GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                        .addComponent(txt_speed2))
                                .addContainerGap(26, Short.MAX_VALUE))
        );
        gl_panel_set.setVerticalGroup(
                gl_panel_set.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_set.createSequentialGroup()
                                .addComponent(label_play2, GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE)
                                .addGroup(gl_panel_set.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel_set.createSequentialGroup()
                                                .addComponent(txt_score2, GroupLayout.PREFERRED_SIZE,
                                                        21, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(txt_maxScore2, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(txt_speed2, GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(16))
                                        .addGroup(gl_panel_set.createSequentialGroup()
                                                .addComponent(lable_score2, GroupLayout.PREFERRED_SIZE,
                                                        18, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(label_maxScore2, GroupLayout.PREFERRED_SIZE,
                                                        25, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(label_speed2)
                                                .addGap(21))))
        );
        panel_set.setLayout(gl_panel_set);

        button_pause = new JButton("Start / Pause");
        button_pause.setBackground(Color.WHITE);
        button_pause.setFocusable(false);
        button_pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                snake.changePause();
                snake2.changePause();
            }
        });
        button_pause.setFocusPainted(false);
        panel_setSpeed.add(button_pause);

        JButton button_newGame = new JButton("Start New Game");
        button_newGame.setBackground(Color.WHITE);
        button_newGame.setFocusable(false);
        button_newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.newGame();

            }
        });
        button_newGame.setFocusPainted(false);
        panel_setSpeed.add(button_newGame);

        JButton button_speed = new JButton("Speed ⇧");
        button_speed.setBackground(Color.WHITE);
        button_speed.setFocusable(false);
        button_speed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(controller.speednum<4 && controller.speednum>-4){  //最多加速到40
                    if(controller.speednum!=3){
                        snake.speed -= 20;
                        snake2.speed -= 20;
                        controller.speednum++;
                    }
                }
            }
        });
        button_speed.setFocusPainted(false);
        //panel_setSpeed.add(button_speed);

        JButton button_speed2 = new JButton("Speed ⇩");
        button_speed2.setBackground(Color.WHITE);
        button_speed2.setFocusable(false);
        button_speed2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(controller.speednum<4 && controller.speednum>-4) { //最多減速到160
                    if(controller.speednum!=-3){
                        snake.speed += 20;
                        snake2.speed += 20;
                        controller.speednum--;
                    }
                }
            }
        });
        button_speed2.setFocusPainted(false);
        //panel_setSpeed.add(button_speed2);

        button_select = new JButton("Select Music");
        button_select.setBackground(Color.WHITE);
        button_select.setFocusable(false);
        button_select.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.playmusic();
            }
        });
        button_select.setFocusPainted(false);

        button_music = new JButton("Pause Music");
        button_music.setBackground(Color.WHITE);
        button_music.setFocusable(false);
        button_music.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(controller.selectmusic){
                    if(controller.clip.isActive())
                        controller.clip.stop();
                    else
                        controller.clip.start();
                }
            }
        });
        button_music.setFocusPainted(false);



        GroupLayout gl_panel_control = new GroupLayout(panel_control);
        panel_control.add(button_speed);
        panel_control.add(button_speed2);
        panel_control.add(button_select);
        panel_control.add(button_music);

        JLabel lblNewLabel = new JLabel("                            Information");
        lblNewLabel.setFocusable(false);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        JLabel label = new JLabel("W A S D 操控P1      ↑↓←→ 操控P2 ");
        label.setFocusable(false);

        JLabel label_1 = new JLabel("Shift鍵可重新開始遊戲");
        label_1.setFocusable(false);

        JLabel lblShift = new JLabel("空白鍵可暫停or繼續遊戲");
        lblShift.setFocusable(false);

        JLabel label_2 = new JLabel("PageUp,PageDown可增加or減緩速度");
        label_2.setHorizontalAlignment(SwingConstants.LEFT);
        label_2.setInheritsPopupMenu(false);
        label_2.setFocusable(false);
        label_2.setFocusTraversalKeysEnabled(false);
        label_2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label_m = new JLabel("O鍵可選擇wav檔音樂，P鍵可暫停播放");
        label_m.setFocusable(false);

        GroupLayout gl_lable = new GroupLayout(lable);
        gl_lable.setHorizontalGroup(
                gl_lable.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_lable.createSequentialGroup()
                                .addGroup(gl_lable.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_lable.createSequentialGroup()
                                                .addGap(26)
                                                .addGroup(gl_lable.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(label_1,
                                                                GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                                        .addComponent(label, GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblShift)
                                                        .addComponent(label_2,
                                                                GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                                        .addComponent(label_m))))
                                .addContainerGap())
        );
        gl_lable.setVerticalGroup(
                gl_lable.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_lable.createSequentialGroup()
                                //.addGap(8)
                                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE,
                                        30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(label, GroupLayout.PREFERRED_SIZE,
                                        26, GroupLayout.PREFERRED_SIZE)
                                .addGap(2)
                                .addComponent(label_1, GroupLayout.PREFERRED_SIZE,
                                        26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblShift, GroupLayout.PREFERRED_SIZE,
                                        25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(label_2)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                //.addContainerGap(39, Short.MAX_VALUE)
                                .addComponent(label_m))
        );
        lable.setLayout(gl_lable);
        panel_1.setLayout(gl_panel_1);
        contentPane.setLayout(gl_contentPane);

        gamePanel.addKeyListener(controller);
        snake.addSnakeListener(controller);
        snake2.addSnakeListener(controller);
        //用來更新分數
        controller.startRefresh(new Thread(new refresh()));
        //開始遊戲
        controller.beginGame();
        snake.start();
        snake2.start();
    }
    //刷新分數
    public class refresh implements Runnable{
        @Override
        public void run() {
            //活著時才要計算分數
            while(!snake.isDie && !snake2.isDie) {
                txt_score.setText(controller.score2 + " score");
                txt_maxScore.setText(controller.maxScore + " score");
                txt_score2.setText(controller.score + " score");
                txt_maxScore2.setText(controller.maxScore + " score");
                txt_speed.setText(snake2.speed + " ms / cell");
                txt_speed2.setText(snake.speed + " ms / cell");
                if(snake.invisible) {
                    label_play2.setText("               Snake 1 is in Hole");
                    label_play2.setForeground(Color.RED);
                }
                else{
                    label_play2.setText("                        Player 1");
                    label_play2.setForeground(Color.BLACK);
                }
                if(snake2.invisible){
                    label_play.setText("                Snake 2 is in Hole");
                    label_play.setForeground(Color.RED);
                }
                else{
                    label_play.setText("                        Player 2");
                    label_play.setForeground(Color.BLACK);
                }
                if(snake.pause){
                    button_pause.setForeground(Color.RED);
                }
                else{
                    button_pause.setForeground(Color.black);
                }
                if(controller.selectmusic){
                    if(!controller.clip.isActive()){
                        button_music.setForeground(Color.RED);
                    }
                    else{
                        button_music.setForeground(Color.BLACK);
                    }
                }
                else{
                    button_music.setForeground(Color.BLACK);
                }
                try {
                    Thread.sleep(snake.speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}