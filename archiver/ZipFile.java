package main.java.src.archiver;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class to archive data
 */

public class ZipFile {
    private String[] args;

    public ZipFile(String[] args) {
        this.args = args;
    }

    public void archive() {
        //Get current directory and directory in which files will be compressed
        String currentDirectory = System.getProperty("user.dir");
        Path compressionPath = Paths.get(currentDirectory, "filesCompressed.zip");


        try {
            FileOutputStream fos = new FileOutputStream(compressionPath.toString());
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            Path infoPath = Paths.get(currentDirectory, "info.txt");
            File infoFile = new File(infoPath.toString());
            FileWriter fileWriter = new FileWriter(infoFile, true);

            //Proceed all files from args to compression function
            for (int i = 0; i < args.length; i++) {
                File fileToZip = new File(args[i]);
                zipFile(fileToZip, fileToZip.getName(), zipOut);

                //Write log about input files
                fileWriter.write(args[i] + " ");
            }

            //Write final message to log
            fileWriter.write(">archived");
            fileWriter.close();

            zipOut.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }

        //Check if current file is directory
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }

            //Get list all files from current directory
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {

                //Call recursive function to proceed all files from current directory
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);

        //Put next zip entry and write bytes from current file
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
