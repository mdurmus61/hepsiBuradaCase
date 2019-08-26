package com.md.md.case1;

import java.util.List;

public class RoverValidation {

    public void overlapValidation(Rover rover, List<RoverCommand> roverCommands) {
        for(RoverCommand roverCommand: roverCommands) {
            if(rover == roverCommand.getRover())
                continue;

            if (rover.getX() == roverCommand.getRover().getX() && rover.getY() == roverCommand.getRover().getY()) {
                throw new MarsRoverException(ErrorCode.OVERLAP, "Two rovers overlap" + rover.toString() + " and " + roverCommand.getRover().toString());
            }
        }
    }

    public void outOfAreaValidation(Rover rover, Area area) {
        if(rover.getX()<0 || rover.getY()<0 || rover.getY()>area.getY() || rover.getX()>area.getX()) {
            throw new MarsRoverException(ErrorCode.OUT_OF_AREA, "Out of the specified area for : " + rover.toString());
        }
    }

}
