package com.nhnacademy;

public class MovableWorld extends World {
	static final int DEFAULT_DT = 10;
	int moveCount;
	int maxMoveCount = 0;
	int dt = DEFAULT_DT;

	public void setDT(int dt) {
		if (dt < 0) {
			throw new IllegalArgumentException();
		}
		this.dt = dt;
	}

	public int getDT() {
		return dt;
	}

	public void reset() {
		moveCount = 0;
	}

	public void move() {
		if ((getMaxMoveCount() == 0) || (getMoveCount() < getMaxMoveCount())) {
			for (int i = 0; i < getBallCount(); i++) {
				Ball ball = getBall(i);
				if (ball instanceof MovableBall) {
					((MovableBall) ball).move();
					int ballY = ball.getY();
					if(ballY > TestWorld.FRAME_HEIGHT - TestWorld.BALL_RADIUS) {
						remove(ball);
						handleGameOver();
					}

					if (ball instanceof BoundedBall) {
						for (int j = 0; j < getBallCount(); j++) {
							Ball otherBall = getBall(j);

							if ((ball != otherBall) && (ball.getRegion().intersects(otherBall.getRegion()))) {
								((BoundedBall) ball).bounce(otherBall);
								logger.info("ball({})와 ball({})이 충돌하였습니다.", ball.getId(), otherBall.getId());
							}
						}

						for (int j = 0; j < getBoxCount(); j++) {
							Box box = getBox(j);

							if ((ball.getRegion().intersects(box.getRegion()))) {
								((BoundedBall) ball).bounce(box);
								logger.info("ball({})와 box({})이 충돌하였습니다.", ball.getId(), box.getId());
								if (!(box instanceof MovableBox)) {
									setScore(getScore() + 10);
									remove(box);
								}

							}

						}
					}
				}
			}

			moveCount++;
			repaint();
		}
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			move();
			try {
				Thread.sleep(getDT());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public int getMoveCount() {
		return moveCount;
	}

	public int getMaxMoveCount() {
		return maxMoveCount;
	}

	public void setMaxMoveCount(int count) {
		if (count < 0) {
			throw new IllegalArgumentException();
		}

		maxMoveCount = count;
	}

}
