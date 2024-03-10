package com.nhnacademy;

import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TestWorld {
    static final int FRAME_WIDTH = 400;
    static final int FRAME_HEIGHT = 600;
    static final int MIN_RADIUS = 5;
    static final int MAX_RADIUS = 10;
    static final int BALL_RADIUS = 10;
    static final int FIXED_BALL_COUNT = 0;
    static final int BOUNDED_BALL_COUNT = 1;
    static final int MIN_DELTA = 5;
    static final int MAX_DELTA = 7;
    static final int MAX_MOVE_COUNT = 0;
    static final int DT = 20;
    static final Color[] COLOR_TABLE = {
            new Color(39, 55, 77),
            new Color(82, 109, 130),
            new Color(157, 178, 191),
            new Color(221, 230, 237),
            new Color(238, 245, 255)
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        MovableWorld world = new MovableWorld();
        world.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        world.setBackground(Color.BLACK);

        frame.add(world);

        Random random = new Random();

        int gapX = 3; 
        int gapY = 3; 

        for (int i = 0; i < 5; i++) { 
            for (int j = 0; j < 10; j++) { 
                int x = j * (FRAME_WIDTH / 10 + gapX); 
                int y = 100 + i * (40 + gapY); 
                PaintableBox box = new PaintableBox(x, y, FRAME_WIDTH / 10, 40, COLOR_TABLE[i]);
                world.add(box);
            }
        }

        // 플레이볼 추가
        BoundedBall ball = new BoundedBall(FRAME_WIDTH / 2, FRAME_HEIGHT / 2 + 100, BALL_RADIUS, Color.WHITE);
        int dx = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);
        int dy = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);
        ball.setDX(dx);
        ball.setDY(dy);
        world.add(ball);
        // 플레이볼 끝

        MovableBox bar = new MovableBox(FRAME_WIDTH / 2, (FRAME_HEIGHT / 10) * 9, 100, 10, Color.WHITE);
        world.add(bar);

        frame.setVisible(true);

        world.setMaxMoveCount(MAX_MOVE_COUNT);
        world.setDT(DT);
        world.run();
    }
}
