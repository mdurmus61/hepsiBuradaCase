package com.md.md.case1;

public class RoverMover {
    private final DirectionCalculator directionCalculator;

    public RoverMover(DirectionCalculator directionCalculator) {
        this.directionCalculator = directionCalculator;
    }

    public void move(Rover rover, char instruction) {
        if(instruction == 'M') {
            if(rover.getDirection().equals(Direction.N))
                rover.setY(rover.getY() + 1);

            else if(rover.getDirection().equals(Direction.E))
                rover.setX(rover.getX() + 1);

            else if(rover.getDirection().equals(Direction.S))
                rover.setY(rover.getY() - 1);

            else if (rover.getDirection().equals(Direction.W))
                rover.setX(rover.getX() - 1);


        }
        else
            rover.setDirection(directionCalculator.calculate(rover.getDirection(), instruction));
    }
}
