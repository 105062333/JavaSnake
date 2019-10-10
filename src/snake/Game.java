package snake;
import java.awt.EventQueue;
import java.awt. *;

public class Game {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window frame = new Window();
                    frame.setVisible(true);
                    //設置左上角的mark
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    Image frameImage = tk.createImage("src/snake/Images/title.png");
                    frame.setIconImage(frameImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}