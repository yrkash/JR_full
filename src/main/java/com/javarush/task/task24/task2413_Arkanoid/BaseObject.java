package com.javarush.task.task24.task2413_Arkanoid;

public abstract class BaseObject {
    private double x;
    private double y;
    private double radius;

    public BaseObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public abstract void draw(Canvas canvas);

    public abstract void move();

    public boolean intersects (BaseObject o) {
        double maxRad = Math.max(this.radius, o.radius);

        return (Math.hypot(Math.abs(this.getX() - o.getX()),Math.abs(this.getY() - o.getY())) <= maxRad) ? true : false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
