package thesis;

public class Point {

    public double x;
    public double y;
    public double z;
    
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
}
