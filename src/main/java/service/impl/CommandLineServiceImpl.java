package service.impl;

import dao.impl.ContactDaoImpl;
import service.CommandLineService;
import service.ContactService;
import util.ConnectionDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineServiceImpl implements CommandLineService {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final ContactService service = new ContactServiceImpl(new ContactDaoImpl());

    public static void start() throws IOException {
        ConnectionDB.connectAndCreateDataBase();
        CommandLineService.run(bufferedReader, service);
//        ConnectionDB.closeConnection();
    }

}
