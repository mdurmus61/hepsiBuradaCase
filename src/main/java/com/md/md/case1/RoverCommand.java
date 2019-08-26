package com.md.md.case1;

public class RoverCommand {
    private Rover rover;
    private String instruction;

    public RoverCommand(Rover rover, String instruction) {
        this.rover = rover;
        this.instruction = instruction;
    }

    public Rover getRover() { return rover; }

    public String getInstruction() { return instruction; }
}
