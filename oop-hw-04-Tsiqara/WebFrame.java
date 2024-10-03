import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class WebFrame extends JFrame {
    private static final String FILENAME = System.getProperty("user.dir") + "/links.txt";
    private Semaphore semaphore;
    protected Launcher launcher;
    private ArrayList<String> urls;
    private ArrayList<WebWorker> workers;
    private DefaultTableModel model;
    private JTable table;
    private JPanel panel;
    private JButton singleFetchButton, concurrentFetchButton, stopButton;
    private JTextField numberOfConcurent;
    private JLabel runningLabel, completedLabel, timeLabel;
    private JProgressBar progressBar;
    private long startTime;
    protected AtomicInteger runningCount, completedCount;
    public WebFrame(){
        super("WebLoader");
        urls = new ArrayList<>();
        model = new DefaultTableModel(new String[] { "url", "status"}, 0);
        readFile();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(600,300));
        panel = (JPanel) getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollpane);

        singleFetchButton = new JButton("Single Thread Fetch");
        add(singleFetchButton);
        singleFetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launcher = new Launcher(1);
                fetch();
            }
        });
        concurrentFetchButton = new JButton("Concurrent Fetch");
        add(concurrentFetchButton);
        concurrentFetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launcher = new Launcher(Integer.parseInt(numberOfConcurent.getText()));
                fetch();
            }
        });

        numberOfConcurent = new JTextField("4",4);
        numberOfConcurent.setMaximumSize(numberOfConcurent.getPreferredSize());
        add(numberOfConcurent);

        runningLabel = new JLabel("Running:0");
        add(runningLabel);
        completedLabel = new JLabel("Completed:0");
        add(completedLabel);
        timeLabel = new JLabel("Elapsed:");
        add(timeLabel);

        progressBar = new JProgressBar();
        add(progressBar);

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launcher.interrupt();
                for(WebWorker worker: workers){
                    worker.interrupt();
                }
                done();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void fetch(){
        singleFetchButton.setEnabled(false);
        concurrentFetchButton.setEnabled(false);
        runningLabel.setText("Running:");
        completedLabel.setText("Completed:");
        timeLabel.setText("Elapsed:");
        progressBar.setMaximum(urls.size());
        startTime = System.currentTimeMillis();
        launcher.start();
        stopButton.setEnabled(true);
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt("", i, 1);
        }
    }

    private void done(){
        singleFetchButton.setEnabled(true);
        concurrentFetchButton.setEnabled(true);
        stopButton.setEnabled(false);
        progressBar.setValue(0);
    }

    private void checkDone(){
        if(runningCount.get() == 0){
            long time = System.currentTimeMillis() - startTime;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    done();
                    timeLabel.setText("Elapsed:"+ time);
                }
            });
        }
    }

    protected void workerDone(String status, int rowIndex){
        runningCount.decrementAndGet();
        completedCount.incrementAndGet();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runningLabel.setText("Running:" + runningCount);
                completedLabel.setText("Completed:" + completedCount);
                progressBar.setValue(completedCount.get());
                model.setValueAt(status, rowIndex, 1);
            }
        });
        semaphore.release();
        checkDone();
    }

    protected class Launcher extends Thread{
        public Launcher(int limit){
            workers = new ArrayList<>();
            semaphore = new Semaphore(limit);
            runningCount = new AtomicInteger(0);
            completedCount = new AtomicInteger(0);
        }
        @Override
        public void run(){
            runningCount.incrementAndGet();
            updateRunningLabel();
            for (int i = 0; i < urls.size(); i ++){
                try {
                    if(isInterrupted()){
                        break;
                    }
                    semaphore.acquire();
                    WebWorker worker = new WebWorker(urls.get(i), i, WebFrame.this);
                    workers.add(worker);
                    if(isInterrupted()){
                        break;
                    }
                    worker.start();
                } catch (InterruptedException e) {
                    break;
                }
                if(isInterrupted()){
                    break;
                }
            }
            runningCount.decrementAndGet();
            updateRunningLabel();
            checkDone();
        }

        protected void updateRunningLabel(){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    runningLabel.setText("Running:" + runningCount);
                }
            });
        }
    }

    private void readFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILENAME));

            while (true) {
                String line = reader.readLine();
                if (line == null) break;  // detect EOF
                model.addRow(new String[]{line, ""});
                urls.add(line);
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WebFrame();
            }
        });
    }
}
