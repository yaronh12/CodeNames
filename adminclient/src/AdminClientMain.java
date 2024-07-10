import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

public class AdminClientMain {
    private static final String LOCK_FILE_PATH = "admin.lock";
    private static FileChannel fileChannel;
    private static FileLock lock;

    public static void main(String[] args) {
        try {
            // Try to acquire the lock
            File lockFile = new File(LOCK_FILE_PATH);
            fileChannel = FileChannel.open(lockFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            lock = fileChannel.tryLock();

            if (lock != null) {
                // Proceed with the application logic
                AdminMenus menu = new AdminMenus();
                boolean adminActive = true;
                while (adminActive) {
                    adminActive = menu.displayMainMenu();
                }
            } else {
                System.out.println("Admin client instance already exists! Goodbye!");
            }
        } catch (IOException e) {
            System.out.println("Failed to acquire lock: " + e.getMessage());
        } finally {
            // Release the lock and close the file channel on exit
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (lock != null) {
                        lock.release();
                    }
                    if (fileChannel != null) {
                        fileChannel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
    }
}
