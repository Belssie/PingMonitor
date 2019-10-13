import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PingMonitor {
    private static final int PING_TIMEOUT = 5000; //the time given to try reaching the host;
    private static final long timeInterval = 900000; //the time interval between the pinging;
    private static final int PING_REQUEST_COUNT = 5; //how many times the program will send ping requests;

    private static void sendPingRequest(String ip) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(String.valueOf(ip)); //getting the string value of the input ip;
        if (inetAddress.isReachable(PING_TIMEOUT)) //checking if we can reach the host for the time given (5sec);
        {
            System.out.println("Host is reachable");
            System.out.println("Sending Ping Request to " + ip);
        } else
            System.out.println("Sorry ! We can't reach to this host");
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //url input
        String url;
        System.out.println("Enter URL...");
        Matcher matcher;
        while (true) {
            url = sc.nextLine();
            String regex = "\\b(https://?|ftp://|file://|www.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern pattern = Pattern.compile(regex); //validation with regex;
            matcher = pattern.matcher(url);
            if (!matcher.matches()) {
                System.out.println("Wrong URL, try again...");
                continue;
            }
            break;
        }

        while (matcher.matches()) {
            try {// Fetch IP address by getByName();
                String ip;
                InetAddress ipAddress = InetAddress.getByName(url); //getting the ip address of the input url;
                ip = ipAddress.getHostAddress(); //gets the ip address of the url;
                PingLogger.makeLog(); //makes/opens a log file (execute.log);
                for (int i = 0; i < PING_REQUEST_COUNT; ++i) { //sends 5 times ping requests towards the ip address;
                    sendPingRequest(ip);
                    PingLogger.logger.info("Sent ping request to " + ip); //logs the pings in the log file (execute.log);
                }
            } catch (UnknownHostException u) {
                System.out.println("Unknown host name"); //it says enough;

            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(timeInterval); //suspends the current thread for the specified number of milliseconds;
            } catch (InterruptedException | SecurityException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Invalid URL!");
    }
}

