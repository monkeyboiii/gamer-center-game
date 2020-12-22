import model.*;
import util.*;

public class LoginTest {

    private static final String email = "calvin@gmail.com";
    private static final String password = "calvin123";

    public static void main(String[] args) throws Exception {
        DeveloperSDK sdk = new DeveloperSDK(email, password, "10.21.20.191");

        Game game = sdk.getGameById(9L);
        System.out.println(game.getName());
    }
}
