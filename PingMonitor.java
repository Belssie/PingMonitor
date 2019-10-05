
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class PingMonitor {
    static final int PING_TIMEOUT = 5000;
    static final long timeInterval = 900000;
    static final int PING_REQUEST_COUNT = 5;

    public static void sendPingRequest(String ip) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(String.valueOf(ip));
        System.out.println("Sending Ping Request to " + ip);
        if (inetAddress.isReachable(PING_TIMEOUT))
            System.out.println("Host is reachable");
        else
            System.out.println("Sorry ! We can't reach to this host");
    }

    public static void main(String args[]) throws IOException {
        PingLogger logger = new PingLogger();
        PingLogger.makeLog();
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    try {// Fetch IP address by getByName()
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Enter URL...");
                        String url = sc.nextLine();

                        String ip;
                        InetAddress ipAddress = InetAddress.getByName(url);
                        Pattern p = Pattern.compile("\\b(https://?|ftp://|file://|www.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
                        Matcher m;
                        m = p.matcher(url);

                        if (m.matches()) {
                            ip = ipAddress.getHostAddress();

                            for (int i = 0; i < PING_REQUEST_COUNT; ++i) {
                                sendPingRequest(ip);
                                logger.info("Sent ping request to "+ip);
                            }
                        } else {
                            System.out.println("Invalid URL, please enter a valid URL!");
                            run();
                        }
                    } catch (MalformedURLException e) {
                        // It means the URL is invalid
                        System.out.println("Invalid URL");
                        run();
                    } catch (UnknownHostException u) {
                        System.out.println("Unknown host name");
                        run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        PingLogger.bw.close();
    }
}
