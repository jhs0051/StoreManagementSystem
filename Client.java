public class Client {
    public static String dbms = "Network";
    public static String path = "localhost:12002";

    IDataAdapter dataAdapter;

    private static Client instance = null;

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client(dbms, path);
        }
        return instance;
    }

    private Client(String dbms, String dbfile) {
        dataAdapter = new NetworkDataAdapter();
        dataAdapter.connect(dbfile);
    }

    public IDataAdapter getDataAdapter() {
        return dataAdapter;
    }

    public void run() {
        ClientLoginUI ui = new ClientLoginUI();
        ui.view.setVisible(true);
    }

    public static void main(String[] args) {
        Client.getInstance().run();
    }
}
