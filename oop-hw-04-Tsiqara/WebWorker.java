import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class WebWorker extends Thread {
    private String urlString;
    private int row;
    private WebFrame frame;
    private String status;
    public WebWorker(String url, int rowNum, WebFrame frame){
        urlString = url;
        row = rowNum;
        this.frame = frame;
    }

    public void run(){
        frame.runningCount.incrementAndGet();
        frame.launcher.updateRunningLabel();
        download();
        frame.workerDone(status, row);
    }

//  This is the core web/download i/o code...
 	private void download() {
        InputStream input = null;
        StringBuilder contents = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            long startTime = System.currentTimeMillis();
            int size = 0;
            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                contents.append(array, 0, len);
                size += len;
                if(isInterrupted()){
                    status = "interrupted";
                    break;
                }
                Thread.sleep(100);
            }

            // Successful download if we get here
            long endTime = System.currentTimeMillis();
            status = new SimpleDateFormat("HH:mm:ss").format(new Date(endTime)) + " " + (endTime - startTime) + "ms "
                    + size + " bytes";

        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            status = "err";
        } catch (InterruptedException exception) {
            status = "interrupted";
        } catch (IOException ignored) {
            status = "err";
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
            }
        }

    }
}
