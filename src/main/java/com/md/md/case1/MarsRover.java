package com.md.md.case1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarsRover {
    public static void main(String[] args){

        List<RoverCommand> roverCommands = new ArrayList<RoverCommand>();
        RoverCommander roverCommander;
        Area area;
        try{
            String space = " ";
            Scanner scanner = new Scanner(System.in);
            System.out.print("how many rovers in system: ");
            int roverNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Upper-right coordinates of the plateau: ");
            String coordinates = scanner.nextLine();

            int maxX = Integer.parseInt(coordinates.split(space)[0]);
            int maxY = Integer.parseInt(coordinates.split(space)[1]);
            area = new Area(maxX, maxY);

            for(int i = 0; i<roverNumber; i++) {
                System.out.print("Rover's position: ");
                String roverPosition = scanner.nextLine();

                int x = Integer.parseInt(roverPosition.split(space)[0]);
                int y = Integer.parseInt(roverPosition.split(space)[1]);
                String dir = roverPosition.split(space)[2];
                Rover rover = new Rover(x, y, Direction.valueOf(dir));

                System.out.print("Series of instructions: ");
                String instruction = scanner.nextLine();
                if(!instruction.matches("[LMR]+"))
                    throw new MarsRoverException(ErrorCode.WRONG_INPUT, "Instruction contains a character other than the L, M, R");

                RoverCommand roverCommand = new RoverCommand(rover, instruction);
                roverCommands.add(roverCommand);
            }

            DirectionCalculator directionCalculator = new DirectionCalculator();
            RoverValidation roverValidation = new RoverValidation();
            RoverMover roverMover = new RoverMover(directionCalculator);
            roverCommander = new RoverCommander(roverMover, roverValidation);
            }
            catch(Exception e) {
                throw new MarsRoverException(ErrorCode.WRONG_INPUT, "Wrong input values in system. " + e.getMessage(), e.getCause());
            }

            System.out.println("\r\n" + "Starting to move robotic rovers " + "\r\n");
            roverCommander.move(area, roverCommands);

            for(RoverCommand roverCommand: roverCommands)
                System.out.println(roverCommand.getRover().toString());

    }
}
