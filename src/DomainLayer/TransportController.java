package DomainLayer;

import DataAccessLayer.TransportDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransportController {
    private ArrayList<Transport> transports;
    private static TransportController instance;
    private InitSystem initSystem;
    private TransportDAO transportDAO;

    private TransportController() throws SQLException {
        initSystem = InitSystem.getInstance();
        this.transports = initSystem.loadTransports();
        transportDAO = new TransportDAO();
    }

    public static TransportController getInstance() throws SQLException {
        if (instance == null){
            instance = new TransportController();
        }
        return instance;
    }

    public void addTransport(Transport t) throws SQLException {
        transports.add(t);
        transportDAO.addT(t);
    }

    public ArrayList<Transport> getTransportsByShift(int BID, LocalDate date, int shiftSign) {
        ArrayList<Transport> transports = new ArrayList<>();
        for (Transport t : this.transports) {
            if (t.getBID() == BID && t.getDate().equals(date) && t.getShiftSign() == shiftSign) {
                transports.add(t);
            }
        }
        return transports;
    }
    public ArrayList<Transport> getTransports(){
        return this.transports;
    }
    public ArrayList<Transport> getTransportsByBID(int BID) {
        ArrayList<Transport> transports = new ArrayList<>();
        for (Transport t : this.transports) {
            if (t.getBID() == BID) {
                transports.add(t);
            }
        }
        return transports;
    }
}
