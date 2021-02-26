package org.jerry.jorm;



import java.io.Serializable;

public class Order implements Serializable {
    public enum Direction {
        asc, desc;

        public static Direction fromString(String value) {
            return valueOf(value.toLowerCase());
        }
    }

    private static final long serialVersionUID = -3078342809727773232L;
    private static final Direction defaultDirection = Direction.desc;
    private String property;
    private Direction direction = defaultDirection;

    public Order() {
    }

    public Order(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    public static Order asc(String property) {
        return new Order(property, Direction.asc);
    }

    public static Order desc(String property) {
        return new Order(property, Direction.desc);
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


}