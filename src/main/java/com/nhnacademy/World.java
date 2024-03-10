package com.nhnacademy;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.awt.Font;

public class World extends JPanel implements MouseListener, MouseMotionListener {
    List<Ball> ballList = new LinkedList<>();
    List<Box> boxList = new LinkedList<>();
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private int score;

    public World() {
        super();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @param ball
     * @throw IllegalArgumentException 공간을 벗어나거나, null인 경우, 볼간 충돌된 경우
     */
    public void add(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException();
        }

        if ((ball.getX() - ball.getRadius() < 0)
                || (ball.getX() + ball.getRadius() > getWidth())
                || (ball.getY() - ball.getRadius() < 0)
                || (ball.getY() + ball.getRadius() > getHeight())) {
            throw new IllegalArgumentException();
        }

        for (Ball existBall : ballList) {

            if (ball.getRegion().intersects(existBall.getRegion())) {
                throw new IllegalArgumentException();
            }
        }

        if (ball instanceof BoundedBall) {
            ((BoundedBall) ball).setBounds(getBounds());
        }
        ballList.add(ball);
    }

    public void add(Box box) {
        if (box == null) {
            throw new IllegalArgumentException();
        }
        boxList.add(box);
    }

    public void remove(Ball ball) {
        ballList.remove(ball);
    }

    public void remove(Box box) {
        boxList.remove(box);
    }

    public void removeBall(int index) {
        ballList.remove(index);
    }

    public void removeBox(int index) {
        boxList.remove(index);
    }

    public int getBallCount() {
        return ballList.size();
    }

    public int getBoxCount() {
        return boxList.size();
    }

    public Ball getBall(int index) {
        return ballList.get(index);
    }

    public Box getBox(int index) {
        return boxList.get(index);
    }

    public void handleGameOver() {
        JOptionPane.showMessageDialog(this, "Game Over\nScore : " + score);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Ball ball : ballList) {
            if (ball instanceof Paintable) {
                ((Paintable) ball).paint(g);
            }
        }

        for (Box box : boxList) {
            if (box instanceof Paintable) {
                ((Paintable) box).paint(g);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score : " + getScore(), getWidth() / 2 - 30, 50);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        logger.info("mouseClicked ({},{})", e.getX(), e.getY());

    }

    @Override
    public void mousePressed(MouseEvent e) {
        logger.info("mousePressed ({},{})", e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        logger.info("mouseReleased ({},{})", e.getX(), e.getY());

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        logger.info("mouseEntered ({},{})", e.getX(), e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        logger.info("mouseExited ({},{})", e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        logger.info("mouseDragged ({},{})", e.getX(), e.getY());
        for (Box box : boxList) {
            if (box instanceof MovableBox) {
                ((MovableBox) box).setX(e.getX());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        logger.info("mouseMoved ({},{})", e.getX(), e.getY());
        for (Box box : boxList) {
            if (box instanceof MovableBox) {
                ((MovableBox) box).setX(e.getX());
            }
        }
    }
}
