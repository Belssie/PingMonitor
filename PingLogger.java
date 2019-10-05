import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PingLogger {
    static final Logger logger = Logger.getLogger("MyLog");
    public static FileWriter fw;
    public static BufferedWriter bw = new BufferedWriter(fw);

    public static void makeLog(){
        try {
            fw = new FileWriter("execute.log");
            FileHandler fh;
            fh = new FileHandler("directory/execute.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void info(String s) {
    }
}