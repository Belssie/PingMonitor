import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PingMonitor {
    static final int PING_TIMEOUT = 5000; //the time given to try reaching the host;
    static final long timeInterval = 900000; //the time interval between the pinging;
    static final int PING_REQUEST_COUNT = 5; //how many times the program will send ping requests;

    private static void sendPingRequest(String ip) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(String.valueOf(ip)); //getting the string value of the input ip;
        if (inetAddress.isReachable(PING_TIMEOUT)) //checking if we can reach the host for the time given (5sec);
        {
            System.out.println("Host is reachable");
            System.out.println("Sending Ping Request to " + ip);
        } else
            System.out.println("Sorry ! We can't reach to this host");
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in); //url input
        System.out.println("Enter URL...");
        String url = sc.nextLine();
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    try {// Fetch IP address by getByName();

                        String ip;
                        InetAddress ipAddress = InetAddress.getByName(url); //getting the ip address of the input url;

                        Pattern p = Pattern.compile("\\b(https://?|ftp://|file://|www.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"); //validation with regex;
                        Matcher m; //declaring matcher;
                        m = p.matcher(url);

                        if (m.matches()) { //this works if the url matches the pattern of the regex;
                            ip = ipAddress.getHostAddress(); //gets the ip address of the url;
                            PingLogger.makeLog(); //makes/opens a log file (execute.log);
                            for (int i = 0; i < PING_REQUEST_COUNT; ++i) { //sends 5 times ping requests towards the ip address;
                                sendPingRequest(ip);
                                PingLogger.logger.info("Sent ping request to " + ip); //logs the pings in the log file (execute.log);
                            }
                        } else {
                            System.out.println("Invalid URL, please enter a valid URL!"); //prints if the URL is written wrong and doesn't suffice the regex validation;
                            run();
                        }
                    } catch (UnknownHostException u) {
                        System.out.println("Unknown host name"); //it says enough;
                        run();
                    } catch (NullPointerException | IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(timeInterval); //suspends the current thread for the specified number of milliseconds;
                    } catch (InterruptedException | SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
