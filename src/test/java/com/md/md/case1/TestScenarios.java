package com.md.md.case1;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestScenarios {
    private DirectionCalculator directionCalculator = new DirectionCalculator();
    private RoverValidation roverValidation = new RoverValidation();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void direction_calculator_test() {
        Direction result = directionCalculator.calculate(Direction.E, 'R');
        assertEquals(Direction.S, result);

        result = directionCalculator.calculate(Direction.W, 'R');
        assertEquals(Direction.N, result);

        result = directionCalculator.calculate(Direction.N, 'L');
        assertEquals(Direction.W, result);
    }

    @Test
    public void rover_mover_test() {
        RoverMover roverMover = new RoverMover(directionCalculator);
        Rover rover = new Rover(2, 5, Direction.N);
        roverMover.move(rover, 'M');
        assertEquals(2, rover.getX());
        assertEquals(6, rover.getY());
        assertEquals(Direction.N, rover.getDirection());

        roverMover.move(rover, 'L');
        assertEquals(2, rover.getX());
        assertEquals(6, rover.getY());
        assertEquals(Direction.W, rover.getDirection());

        roverMover.move(rover, 'R');
        assertEquals(2, rover.getX());
        assertEquals(6, rover.getY());
        assertEquals(Direction.N, rover.getDirection());
    }

    @Test
    public void success_out_of_area_validation_test() {
        Rover rover = new Rover(1,2, Direction.N);
        Area area = new Area(5,5);

        roverValidation.outOfAreaValidation(rover, area);
    }

    @Test
    public void failed_out_of_area_validation_test() {
        thrown.expect(MarsRoverException.class);
        thrown.expectMessage("OUT_OF_AREA - Out of the specified area for : Rover{x=6, y=2, direction='N'}");
        Rover rover = new Rover(6,2, Direction.N);
        Area area = new Area(5,5);

        roverValidation.outOfAreaValidation(rover, area);
    }

    @Test
    public void success_overlap_validation_test() {
        Rover rover = new Rover(1,2, Direction.N);
        Rover otherRover = new Rover(2, 4, Direction.W);
        RoverCommand roverCommand = new RoverCommand(rover, "");
        RoverCommand otherRoverCommand = new RoverCommand(otherRover, "");

        List<RoverCommand> roverCommands = Arrays.asList(roverCommand, otherRoverCommand);
        roverValidation.overlapValidation(rover, roverCommands);
    }

    @Test
    public void failed_overlap_validation_test() {
        thrown.expect(MarsRoverException.class);
        thrown.expectMessage("OVERLAP - Two rovers overlapRover{x=1, y=2, direction='N'} and Rover{x=1, y=2, direction='W'}");
        Rover rover = new Rover(1,2, Direction.N);
        Rover otherRover = new Rover(1, 2, Direction.W);
        RoverCommand roverCommand = new RoverCommand(rover, "");
        RoverCommand otherRoverCommand = new RoverCommand(otherRover, "");

        List<RoverCommand> roverCommands = Arrays.asList(roverCommand, otherRoverCommand);
        roverValidation.overlapValidation(rover, roverCommands);
    }

    @Test
    public void rover_commander_test() {
        Area area = new Area(5, 5);
        Rover rover1 = new Rover(1, 2, Direction.N);
        RoverCommand roverCommand1 = new RoverCommand(rover1, "LMLMLMLMM");
        Rover rover2 = new Rover(3, 3, Direction.E);
        RoverCommand roverCommand2 = new RoverCommand(rover2, "MMRMMRMRRM");

        List<RoverCommand> roverCommands = Arrays.asList(roverCommand1, roverCommand2);

        RoverMover roverMover = new RoverMover(directionCalculator);
        RoverCommander roverCommander = new RoverCommander(roverMover, roverValidation);

        roverCommander.move(area, roverCommands);

        assertEquals(1, roverCommand1.getRover().getX());
        assertEquals(3, roverCommand1.getRover().getY());
        assertEquals(Direction.N, roverCommand1.getRover().getDirection());
        assertEquals("Rover{x=1, y=3, direction='N'}", roverCommand1.getRover().toString());


        assertEquals(5, roverCommand2.getRover().getX());
        assertEquals(1, roverCommand2.getRover().getY());
        assertEquals(Direction.E, roverCommand2.getRover().getDirection());
        assertEquals("Rover{x=5, y=1, direction='E'}", roverCommand2.getRover().toString());
    }

    @Test
    public void rover_commander_test_failed_initial_values_2() {
        thrown.expect(MarsRoverException.class);
        thrown.expectMessage("OVERLAP - Two rovers overlapRover{x=1, y=2, direction='N'} and Rover{x=1, y=2, direction='E'}");
        Area area = new Area(5, 5);
        Rover rover1 = new Rover(1, 2, Direction.N);
        RoverCommand roverCommand1 = new RoverCommand(rover1, "LMLMLMLMM");
        Rover rover2 = new Rover(1, 2, Direction.E);
        RoverCommand roverCommand2 = new RoverCommand(rover2, "MMRMMRMRRM");

        List<RoverCommand> roverCommands = Arrays.asList(roverCommand1, roverCommand2);

        RoverMover roverMover = new RoverMover(directionCalculator);
        RoverCommander roverCommander = new RoverCommander(roverMover, roverValidation);

        roverCommander.move(area, roverCommands);
    }

    @Test
    public void rover_commander_test_failed_initial_values() {
        thrown.expect(MarsRoverException.class);
        thrown.expectMessage("OUT_OF_AREA - Out of the specified area for : Rover{x=6, y=2, direction='N'}");
        Area area = new Area(5, 5);
        Rover rover1 = new Rover(6, 2, Direction.N);
        RoverCommand roverCommand1 = new RoverCommand(rover1, "LMLMLMLMM");

        List<RoverCommand> roverCommands = Arrays.asList(roverCommand1);

        RoverMover roverMover = new RoverMover(directionCalculator);
        RoverCommander roverCommander = new RoverCommander(roverMover, roverValidation);

        roverCommander.move(area, roverCommands);
    }

    @Test
    public void failed_main_test() throws FileNotFoundException {
        thrown.expect(MarsRoverException.class);
        thrown.expectMessage("WRONG_INPUT - Wrong input values in system.");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("WrongTestInput.txt").getFile());
        System.setIn(new FileInputStream(file.getAbsolutePath()));
        MarsRover.main(new String[0]);
    }

    @Test
    public void failed_main_test_2() throws FileNotFoundException {
        thrown.expect(MarsRoverException.class);
        thrown.expectMessage("WRONG_INPUT - Wrong input values in system.");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("WrongTestInput2.txt").getFile());
        System.setIn(new FileInputStream(file.getAbsolutePath()));
        MarsRover.main(new String[0]);
    }

    @Test
    public void success_main_test() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("TestInput.txt").getFile());
        System.setIn(new FileInputStream(file.getAbsolutePath()));
        MarsRover.main(new String[0]);
    }
}
