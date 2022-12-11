package com.javarush.task.task23.task2312_Snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public Snake(int x, int y) {
        SnakeSection head = new SnakeSection(x, y);
        this.sections = new ArrayList<SnakeSection>();
        this.sections.add(head);
        this.isAlive = true;
    }

    public int getX() {
        return this.sections.get(0).getX();
    }

    public int getY() {
        return this.sections.get(0).getY();
    }

    public void move() {
        if (!isAlive) return;
        switch (direction) {
            case UP:
                move(0, - 1);
                break;
            case DOWN:
                move(0, 1);
                break;
            case RIGHT:
                move(1, 0);
                break;
            case LEFT:
                move(-1, 0);
                break;
        }
    }
    public void move (int x, int y) {
        SnakeSection currentHead = sections.get(0);
        SnakeSection newHead = new SnakeSection(currentHead.getX() + x, currentHead.getY() + y);

        checkBorders(newHead);
        checkBody(newHead);
        if (isAlive)  {
            if ((newHead.getX() == Room.game.getMouse().getX()) && (newHead.getY() == Room.game.getMouse().getY())) {
                Room.game.eatMouse();
                sections.add(0, newHead);
            } else {
                sections.add(0, newHead);
                sections.remove(sections.size() - 1);
            }
        }
    }

    public void checkBorders (SnakeSection head) {
        if ((head.getX() < 0) || (head.getY() < 0) || (head.getX() >= Room.game.getWidth()) ||(head.getY() >= Room.game.getHeight())) {
            isAlive = false;
        }
    }

    public void checkBody (SnakeSection head) {
        if (sections.contains(head)) isAlive = false;
    }
}
