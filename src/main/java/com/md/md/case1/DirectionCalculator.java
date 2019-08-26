package com.md.md.case1;

public class DirectionCalculator {
    private Direction[] directions = new Direction[]{Direction.N, Direction.E, Direction.S, Direction.W};

    public Direction calculate(Direction currentDirection, char instruction) {
        int currentIndex = -1;
        for(int i = 0 ; i<directions.length; i++){
            if(directions[i].equals(currentDirection)) {
                currentIndex = i;
                break;
            }
        }

        return directions[instruction == 'R' ? ((currentIndex + 5) % directions.length) : ((currentIndex +3)%directions.length)];
    }
}
