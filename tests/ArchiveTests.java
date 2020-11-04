package main.java.src.tests;

import main.java.src.archiver.Util;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArchiveTests {
    private final int ARGS_TO_ARCHIVE_COUNT = 2;
    private final String TEST_STRING = "test string";

    private final String HOME_DIRECTORY = System.getProperty("user.home");
    private final String USER_DIRECTORY = System.getProperty("user.dir");
    private final String COMPRESSION_DIRECTORY = "/filesCompressed/";

    private final String ZIP_FILE_NAME = "filesCompressed.zip";

    private final String TEST_DIRECTORY_ADDITIONAL_PATH_1 = "/testFolder1";
    private final String TEST_DIRECTORY_ADDITIONAL_PATH_2 = "/testFolder2";
    private final String TEST_DIRECTORY_ADDITIONAL_PATH_3 = "/testFolder2/testFolder3";

    private final String TEST_FILE_ADDITIONAL_PATH_1 = "/testFolder1/test1.txt";
    private final String TEST_FILE_ADDITIONAL_PATH_2 = "/testFolder1/test2.txt";
    private final String TEST_FILE_ADDITIONAL_PATH_3 = "/testFolder2/test3.txt";
    private final String TEST_FILE_ADDITIONAL_PATH_4 = "/testFolder2/testFolder3/test4.txt";
    private final String TEST_FILE_ADDITIONAL_PATH_5 = "/testFolder2/testFolder3/test5.txt";

    @Test
    public void archiveAndCheckSingleFile() {
        //Declare actual files, directories and subdirectories
        File actualFileFolder1 = new File(HOME_DIRECTORY + TEST_DIRECTORY_ADDITIONAL_PATH_1);
        File actualFileFolder2 = new File(HOME_DIRECTORY + TEST_DIRECTORY_ADDITIONAL_PATH_2);
        File actualFileFolder3 = new File(HOME_DIRECTORY + TEST_DIRECTORY_ADDITIONAL_PATH_3);

        File actualFile1 = new File(HOME_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_1);
        File actualFile2 = new File(HOME_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_2);

        File actualFile3 = new File(HOME_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_3);

        File actualFile4 = new File(HOME_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_4);
        File actualFile5 = new File(HOME_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_5);

        try {

            //Create actual files, directories and subdirectories
            Files.createDirectory(actualFileFolder1.toPath());
            Files.createDirectory(actualFileFolder2.toPath());
            Files.createDirectory(actualFileFolder3.toPath());

            actualFile1.createNewFile();
            actualFile2.createNewFile();
            actualFile3.createNewFile();
            actualFile4.createNewFile();
            actualFile5.createNewFile();

            List<File> listOfFiles = new ArrayList<>();
            listOfFiles.add(actualFile1);
            listOfFiles.add(actualFile2);
            listOfFiles.add(actualFile3);
            listOfFiles.add(actualFile4);
            listOfFiles.add(actualFile5);

            //Add text to created actual files
            for (File file : listOfFiles) {
                FileWriter fileWriter = new FileWriter(file.toString());
                fileWriter.write(TEST_STRING);
                fileWriter.close();
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Check if creating has been succeeded
            isActualFile1EntryOkBeforeZipping();
            isActualFile2EntryOkBeforeZipping();
            isActualFile3EntryOkBeforeZipping();
            isActualFile4EntryOkBeforeZipping();
            isActualFile5EntryOkBeforeZipping();


            //Archive distinguished directories
            String[] argsToArchive = new String[ARGS_TO_ARCHIVE_COUNT];
            argsToArchive[0] = HOME_DIRECTORY + TEST_DIRECTORY_ADDITIONAL_PATH_1;
            argsToArchive[1] = HOME_DIRECTORY + TEST_DIRECTORY_ADDITIONAL_PATH_2;

            Util utilToArchive = new Util(argsToArchive);
            utilToArchive.proceedArchiver();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Check if archived file have been created
            haveArchiveFileBeenCreated();

            //Delete unarchived directories and their entries from previous step
            deleteDirectoryStream(Paths.get(argsToArchive[0]));
            deleteDirectoryStream(Paths.get(argsToArchive[1]));

            //Unarchive created archive
            /*String zipFileLocation = HOME_DIRECTORY + "/" + ZIP_FILE_NAME;*/
            String zipFileLocation = USER_DIRECTORY + "/" + ZIP_FILE_NAME;
            String[] argsToUnarchive = new String[1];
            argsToUnarchive[0] = zipFileLocation;
            Util utilToUnarchive = new Util(argsToUnarchive);
            utilToUnarchive.proceedArchiver();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //Check if files were unarchived
            isActualFile1EntryOkAfterZipping();
            isActualFile2EntryOkAfterZipping();
            isActualFile3EntryOkAfterZipping();
            isActualFile4EntryOkAfterZipping();
            isActualFile5EntryOkAfterZipping();

            //Delete archive
            File archiveFile = new File(zipFileLocation);
            archiveFile.delete();


            //Delete unarchived directories and their entries
            deleteDirectoryStream(Paths.get(USER_DIRECTORY + COMPRESSION_DIRECTORY));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
    }



    /**
     * Tests to check if folders creation has been succeeded
     */

    @Test
    public void isActualFile1EntryOkBeforeZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(HOME_DIRECTORY
                + TEST_FILE_ADDITIONAL_PATH_1));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile2EntryOkBeforeZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(HOME_DIRECTORY
                + TEST_FILE_ADDITIONAL_PATH_2));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile3EntryOkBeforeZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(HOME_DIRECTORY
                + TEST_FILE_ADDITIONAL_PATH_3));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile4EntryOkBeforeZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(HOME_DIRECTORY
                + TEST_FILE_ADDITIONAL_PATH_4));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile5EntryOkBeforeZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(HOME_DIRECTORY
                + TEST_FILE_ADDITIONAL_PATH_5));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }


    /**
     * Test to check if folders were archived
     */

    @Test
    public void haveArchiveFileBeenCreated() {
        File archivedFile = new File(USER_DIRECTORY + "/" + ZIP_FILE_NAME);
        boolean isFileExists = archivedFile.exists();
        Assert.assertTrue(isFileExists);
    }

    /**
     * Tests to check if folders unarchive has been succeeded
     */

    @Test
    public void isActualFile1EntryOkAfterZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_DIRECTORY +
                COMPRESSION_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_1));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile2EntryOkAfterZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_DIRECTORY +
                COMPRESSION_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_2));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile3EntryOkAfterZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_DIRECTORY +
                COMPRESSION_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_3));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile4EntryOkAfterZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_DIRECTORY +
                COMPRESSION_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_4));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }

    @Test
    public void isActualFile5EntryOkAfterZipping() {
        String entryString = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USER_DIRECTORY +
                COMPRESSION_DIRECTORY + TEST_FILE_ADDITIONAL_PATH_5));
            entryString = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(TEST_STRING, entryString);
    }
}
