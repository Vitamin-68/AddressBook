import exceptions.MyAddressBookException;
import service.impl.CommandLineServiceImpl;

public class Main {
    public static void main(String[] args) throws MyAddressBookException {
        CommandLineServiceImpl.start();
    }
}
