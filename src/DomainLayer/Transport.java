package DomainLayer;

import java.time.LocalDate;
import java.util.ArrayList;

public class Transport {
    private int TID;
    private int BID;
    private LocalDate date;
    private int weight;
    private int shiftSign;
    private boolean isConfirmed;
    private String driver;


    public Transport(int TID, int BID,  LocalDate date, int weight, int shiftSign){
        this.TID = TID;
        this.BID = BID;
        this.date = date;
        this.weight = weight;
        this.shiftSign = shiftSign;
        this.isConfirmed = false;
        this.driver = "";
    }

    // getters setters
    public int getBID() {
        return BID;
    }
    public void setBID(int bID) {
        BID = bID;
    }
    public int getShiftSign() {
        return shiftSign;
    }
    public void setShiftSign(int shiftSign) {
        this.shiftSign = shiftSign;
    }
    public int getTID() {
        return TID;
    }
    public void setTID(int TID) {
        this.TID = TID;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public boolean isConfirmed() {
        return isConfirmed;
    }
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRequiredLicense(){
        if (weight < 3500){
            return "B";
        }
        if (weight < 12000){
            return "C1";
        }
        return "C";
    }

    public void assignDriver(Employee e){
        if (e.getMaximalWeight() < this.getWeight()){
            throw new RuntimeException("Incapable of carrying this weight");
        }
        this.driver = e.getUserName();
        this.isConfirmed = true;
    }

    public String toString(){
        return "Transport ID: " + TID + ", Branch ID: " + BID + ", Date: " + date.toString() +" Weight: " + getWeight() + ", Driver: " + driver + "\n";
    }


}
