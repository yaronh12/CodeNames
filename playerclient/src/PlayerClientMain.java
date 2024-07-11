public class PlayerClientMain {
    public static void main(String[] args) {
        System.out.println("Welcome to Code Names!");
        while(!SignUpToSystem.signUpToSystem()){}
        PlayerMenus mainMenu = new PlayerMenus();
        while(mainMenu.playerMainMenu()){}

    }
}