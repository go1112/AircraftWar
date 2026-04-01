package edu.hitsz.application;

// 用于创建窗口 设置尺寸 获取屏幕信息等
import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {
    // 设置游戏窗口的宽度和高度：公共，静态，只读
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false); // 用户无法调节屏幕大小 已经固定好了
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        // 当用户点击窗口的关闭按钮时，程序会调用 System.exit(0) 结束整个进程，确保游戏完全退出
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        frame.add(game);
        frame.setVisible(true);
        game.action();
    }
}
