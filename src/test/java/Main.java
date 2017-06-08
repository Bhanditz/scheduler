import org.bibalex.eol.scheduler.content_partner.ContentPartnerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by sara.mustafa on 4/12/17.
 */
public class Main {

    /**
     * Read resume of a candidate and write it into a file
     *

     */
    public static void readBlob(int id) {
        // update sql
        String selectSQL = "SELECT * FROM eol.content_partner WHERE id=?";
        ResultSet rs = null;

        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/eol?rewriteBatchedStatements=true","root","root");
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            // set parameter;
            pstmt.setInt(1,id);
            System.out.println("call  " );
            rs = pstmt.executeQuery();


            while (rs.next()) {
                InputStream input = rs.getBinaryStream("logo");
                // write binary stream into file
                File file = new File("imagepost50."+rs.getString("logo_type"));
                FileOutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
                output.close();
                System.out.println("Writing to file " + file.getAbsolutePath());
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    static class Task implements Runnable
    {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run()
        {
            try {
                System.out.println("Doing a task during : " + name + " - Time - " + new Date());
                Thread.sleep(6000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        Callable<Integer> task = () -> {
//            try {
//                System.out.println("name "+ Thread.currentThread().getName() + new Date());
//                TimeUnit.SECONDS.sleep(5);
//                return 123;
//            }
//            catch (InterruptedException e) {
//                throw new IllegalStateException("task interrupted", e);
//            }
//        };
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
//        Runnable task1 = () -> {
//            System.out.println("execute task " + new Date());
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };

        // Task task2 = new Task ("Demo Task 2");

       // System.out.println("The time is : " + new Date());
//
     //   executor.scheduleAtFixedRate(task1, 0, 2, TimeUnit.SECONDS);
       // executor.schedule(task2, 4 , TimeUnit.SECONDS);

//        try {
//            executor.awaitTermination(1, TimeUnit.DAYS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

  //      executor.shutdown();

   //     task.run();


//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        Future<Integer> future1 = executor.submit(task);
//        Future<Integer> future2 = executor.submit(task);
//
//        System.out.println("future done? " + future1.isDone());
//        System.out.println("future done? " + future2.isDone());
//
//        Integer result = future1.get();
//        Integer result2 = future2.get();
//
//        System.out.println("future 1 done? " + future1.isDone());
//        System.out.println("future 2 done? " + future2.isDone());
//        System.out.print("result: " + result);
//executor.shutdown();
        Logger logger = LoggerFactory.getLogger(Main.class);
        System.out.println("cores "+Runtime.getRuntime().availableProcessors());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        final Date midnight = new Date();
        midnight.setHours(12);
        midnight.setMinutes(55);
        midnight.setSeconds(10);
        long initialDelay = new Date(midnight.getTime()-System.currentTimeMillis()).getTime();
        System.out.println("delAY  "+initialDelay);
       executor.scheduleAtFixedRate(()->{

            //The repetitive task, say to update Database


            logger.debug("----------> hi there at: "+ new Date() );
            try {
                Thread.sleep(1000);
              // return 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, initialDelay , 2000L, TimeUnit.MILLISECONDS);



    }
}