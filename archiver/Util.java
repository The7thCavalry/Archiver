package main.java.src.archiver;;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Util class for starting archive/unarchive modes
 */

public class Util {
    private String[] args;

    public Util(String[] args) {
        this.args = args;
    }

    public String proceedArchiver() {
        //Incorrect execution
        if (args.length < 1) {
            return "Enter target file names as a program arguments";
        }

        //Unzip mode
        else if (args.length == 1 && args[0].endsWith("zip")) {
            if (Files.exists(Paths.get(args[0]))) {
                UnzipFile unzipFile = new UnzipFile(args[0]);
                unzipFile.unarchive();
                return "Successfully unarchived";
            }
            return "Filename is incorrect";
        }

        //Zip mode
        else {
            for (int i = 0; i < args.length; i++) {
                if (Files.notExists(Paths.get(args[i]))) {
                    return String.format("Filename number %d is incorrect", i + 1);
                }
            }
            ZipFile zipFile = new ZipFile(args);
            zipFile.archive();
            return "Successfully archived";
        }
    }
}
