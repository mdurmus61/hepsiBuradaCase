package com.md.md.case1;

import java.util.List;

public class RoverCommander {
    private final RoverMover roverMover;
    private final RoverValidation roverValidation;

    public RoverCommander(RoverMover roverMover, RoverValidation roverValidation) {
        this.roverMover = roverMover;
        this.roverValidation = roverValidation;
    }

    public void move(Area area, List<RoverCommand> roverCommands) {
        for(RoverCommand roverCommand: roverCommands) {
            roverValidation.outOfAreaValidation(roverCommand.getRover(), area);
            roverValidation.overlapValidation(roverCommand.getRover(), roverCommands);
            char[] instArray = roverCommand.getInstruction().toCharArray();
            for (char instruction : instArray) {
                roverMover.move(roverCommand.getRover(), instruction);
                roverValidation.outOfAreaValidation(roverCommand.getRover(), area);
                roverValidation.overlapValidation(roverCommand.getRover(), roverCommands);
            }
        }
    }
}
