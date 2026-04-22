package edu.hitsz.application;

import javax.swing.*;

import edu.hitsz.rank.Difficulty;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

public class MainMenuFrame extends JFrame {
    // 1. 窗口容器组件
    private JPanel mainPanel; // 主面板（对应SimpleCalculator中的MainPanel）

    // 2. 标题区域
    private JPanel titlePanel; // 标题容器
    private JLabel titleLabel; // 标题文字

    // 3. 难度选择区域
    private JPanel difficultyPanel; // 难度选择容器（或使用buttonPanel）
    private JButton beginningButton; // 简单
    private JButton basicButton; // 普通
    private JButton intermediateButton; // 困难
    private JButton advancedButton; // 专家
    private JButton expertButton; // 地狱

    // 4. 功能区域
    private JPanel functionPanel; // 功能按钮容器
    private JButton rankingButton; // 排行榜
    private JButton exitButton; // 退出

    // 5. 信息区域
    private JPanel infoPanel; // 信息容器
    private JLabel infoLabel; // 提示信息

    public MainMenuFrame() {
        // 初始化所有组件
        initComponents();

        // 设置窗口属性
        setTitle("飞机大战 - 主菜单");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 添加事件监听器
        addEventListeners();

        setVisible(true);
    }

    private void addEventListeners() {
        // 难度选择按钮事件
        beginningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.BEGINNER);
            }
        });
        basicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.BASIC);
            }
        });
        intermediateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.INTERMEDIATE);
            }
        });
        advancedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.ADVANCED);
            }
        });
        expertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(Difficulty.EXPERT);
            }
        });

        // 排行榜按钮事件
        rankingButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // showRanking();
            }
        });

        // 退出按钮事件
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void initComponents() {
        // 创建主面板，使用BorderLayout
        mainPanel = new JPanel(new BorderLayout());

        // 创建标题面板
        titlePanel = new JPanel();
        titleLabel = new JLabel("飞机大战");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // 创建难度选择面板
        difficultyPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        beginningButton = new JButton("简单模式");
        basicButton = new JButton("普通模式");
        intermediateButton = new JButton("困难模式");
        advancedButton = new JButton("专家模式");
        expertButton = new JButton("地狱模式");

        difficultyPanel.add(beginningButton);
        difficultyPanel.add(basicButton);
        difficultyPanel.add(intermediateButton);
        difficultyPanel.add(advancedButton);
        difficultyPanel.add(expertButton);

        mainPanel.add(difficultyPanel, BorderLayout.CENTER);

        // 创建功能按钮面板
        functionPanel = new JPanel(new FlowLayout());
        rankingButton = new JButton("排行榜");
        exitButton = new JButton("退出游戏");
        functionPanel.add(rankingButton);
        functionPanel.add(exitButton);

        // 创建信息面板
        infoPanel = new JPanel();
        infoLabel = new JLabel("选择难度开始游戏");
        infoPanel.add(infoLabel);

        // 将所有面板添加到主面板
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(functionPanel, BorderLayout.CENTER);
        southPanel.add(infoPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // 设置主面板为内容面板
        setContentPane(mainPanel);
    }

    /**
     * 开始游戏的方法
     */
    private void startGame(Difficulty difficulty) {
        // 关闭主菜单
        this.dispose();

        // 在事件分发线程上创建游戏窗口
        SwingUtilities.invokeLater(() -> {
            createGameWindow(difficulty);
        });
    }

    /**
     * 创建游戏窗口
     */
    private void createGameWindow(Difficulty difficulty) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame gameFrame = new JFrame("飞机大战 - " + difficulty);
        gameFrame.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        gameFrame.setResizable(false);

        // 居中
        gameFrame.setBounds(
                ((int) screenSize.getWidth() - Main.WINDOW_WIDTH) / 2, 0,
                Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建游戏实例并设置难度
        Game game = new Game();
        game.setDifficulty(difficulty);

        gameFrame.add(game);
        gameFrame.setVisible(true);
        game.action();

        gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // 游戏结束后可以重新显示主菜单
                SwingUtilities.invokeLater(() -> {
                    new MainMenuFrame();
                });
            }
        });
    }
}
