package thesis;

import java.util.Iterator;

public abstract class UAV {

    public final static int MAX_HEIGHT = 100;
    public final static double MOVE_MOMENT = 0.5;
    public final static double TRANSMIT_POWER = 46;
    public final static double SIR_NOT_DEFINED = -1;

    private static int ID; //for automatically increment id
    
    private int id;
    private double x;
    private double y;
    private double z;
    private double cachedSIR;
    private boolean isOpen;

    
    public UAV(double x, double y, double z, boolean isOpen) {
        this.x = x;
        this.y = y;
        this.z = z;
        cachedSIR = UAV.SIR_NOT_DEFINED;
        this.isOpen = isOpen;
        id = ID++;
    }
    
    public void move(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
    
    public void open(boolean isOpen) {
        this.isOpen = isOpen; 
    }
    
    public int getID() {
        return id;
    }
    
    public double getCachedSIR() {
        return cachedSIR;
    }
    
    public void setCachedSIR(double val) {
        cachedSIR = val;
    }
    
    public double x() {
        return x;
    }
    
    public double y() {
        return y;
    }
    
    public double z() {
        return z;
    }
    
    public String toString() {
        return "x: " + x + " y:" + y + " z:" + z;
    }
    
    public abstract void run();
}