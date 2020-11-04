package main.java.src.tests;

import main.java.src.archiver.Util;
import org.junit.Assert;
import org.junit.Test;


public class InputTests {
    private final String LESS_THAN_ONE_ARGUMENT = "Enter target file names as a program arguments";
    private final String WRONG_SINGLE_ARGUMENT = "Filename is incorrect";
    private final String WRONG_SECOND_ARGUMENT = "Filename number 2 is incorrect";
    private final String WRONG_THIRD_ARGUMENT = "Filename number 3 is incorrect";

    @Test
    public void lessThenOneArg() {
        String[] args = new String[0];
        Util util = new Util(args);
        Assert.assertEquals(LESS_THAN_ONE_ARGUMENT, util.proceedArchiver());
    }

    @Test
    public void wrongSingleArgument() {
        String[] args = new String[1];
        args[0] = "/wron/g\\pat\\h.zip";
        Util util = new Util(args);
        Assert.assertEquals(WRONG_SINGLE_ARGUMENT, util.proceedArchiver());
    }

    @Test
    public void wrongSecondArgument() {
        String[] args = new String[2];
        args[0] = System.getProperty("user.home");
        args[1] = "/wron/g\\pat\\h";
        Util util = new Util(args);
        Assert.assertEquals(WRONG_SECOND_ARGUMENT, util.proceedArchiver());
    }

    @Test
    public void wrongThirdArgument() {
        String[] args = new String[3];
        args[0] = System.getProperty("user.home");
        args[1] = System.getProperty("user.home");
        args[2] = "/wron/g\\pat\\h";
        Util util = new Util(args);
        Assert.assertEquals(WRONG_THIRD_ARGUMENT, util.proceedArchiver());
    }
}
