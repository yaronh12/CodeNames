import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerClientMain {
    public static void main(String[] args) {
        System.out.println("Welcome to Code Names!");
        while(!SignUpToSystem.signUpToSystem()){}
        PlayerMenus mainMenu = new PlayerMenus();
        while(mainMenu.playerMainMenu()){}
        ExitSystem.exitSystem(SignUpToSystem.getUsername());
    }


}