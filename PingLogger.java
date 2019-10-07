import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


class PingLogger {
    static final Logger logger = Logger.getLogger(PingLogger.class.getName()); //creates our logger with the class name;

    private static String dir = System.getProperty("user.dir"); //creates a String with the name(location) of the directory of our project;
    private static String filename = dir + File.separator + "execute.log"; //creates a String with the name of the file where we would be saving our logs;

    private static FileHandler fh; //creates a FileHandler


    private static File directory = new File(dir); //file object of our directory;
    private static File file = new File(filename); //file object of our file execute.log;


    static void makeLog() {
        try {
            directory.mkdirs(); //creates the specified directory path in its entirety
            if (file.createNewFile() || (file.exists() && !file.isDirectory() && file.canRead())) { //checks if the file is created or exists and can be read;
                fh = new FileHandler(filename, 0, 1, true); // initializing our FileHandler;
                System.out.println("Writing in a file." + filename); //Prints out the path of the file we would be writing in;
                logger.addHandler(fh); //adding the FileHandler in our logger;
                SimpleFormatter formatter = new SimpleFormatter(); //initializing a formatter for our log messages;
                fh.setFormatter(formatter); //setting the formatter for our logger handler;
                /* FileWriter fw = new FileWriter(filename, true); nvm that
                BufferedWriter bw = new BufferedWriter(fw); nvm that
                w.close(); nvm that */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}