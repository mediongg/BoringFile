package thesis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 * @author Medion-PC.
 * 
 * The UAV that randomly moves one unit choosing from 3 dimensions.  
 */
public class ReferenceUAV extends UAV {

	private double last_profit;
	private Point last_move;

	public static final SequenceGenerator SEQUENCE_GENERATOR = new SequenceGenerator() {

		@Override
		public int[] sequence(int uavNum) {
			int[] a = new int[uavNum];
			int n = a.length;

			for (int i = 0; i < uavNum; i++)
				a[i] = i;
			Random random = new Random();
			for (int i = 0; i < n; i++) {
				int r = i + random.nextInt(n - i); // between i and n-1
				int temp = a[i];
				a[i] = a[r];
				a[r] = temp;
			}
			return a;
		}

	};

	public ReferenceUAV(double x, double y, double z, boolean isOpen) {
		super(x, y, z, isOpen);
		last_profit = 0;
		last_move = new Point(0.0, 0.0, 0.0);
	}

	@Override
	public void run(Grid[][] grid) {
		int grid_size = grid.length;
		int si_deno = 0;
		double si_no = 0.0d;

		for (int i = 0; i < grid_size; i++) {
			for (int j = 0; j < grid_size; j++) {
				double sir = 0.0d;
				int termNum = grid[i][j].getTermNum();
				if (termNum == 0)
					continue;

				Terminal t = grid[i][j].getTerminal();
				Point p = last_move;
				sir = t.peekSIR(getID(), p.x, p.y, p.z);

				double tmp = UAV.ld(sir);
				if (tmp > 0) {
					si_no += tmp * termNum;
					si_deno += termNum;
				} 
			}
		}

		if (si_deno > 0) {
			double si = si_no / si_deno;
			if (last_profit > si)
				last_move = randomPoint(grid_size);

			last_profit = si;
		} else {
			last_move = randomPoint(grid_size);
		}

		if (last_move.z != 0) moveByPoint(last_move, Environment.MAX_HEIGHT);
		else moveByPoint(last_move, grid_size);
	}
	
	private Point randomPoint(int grid_size) {
		Point pt = new Point(0, 0, 0);
		while (true) {
			pt.set(Util.randomPoint());
			if (checkBoundary(pt, grid_size, Environment.MAX_HEIGHT)) return pt;
		}
	}
	
    /**
     * Shall not call this method.
     */
    @Override
    public void setPartnetUAV(UAV[] uav) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Operation is not supported for ReferenceUAV");
    }

	@Override
	public double[] getSpectrumAndTerms(Grid[][] grid) {
		int grid_size = grid.length;
		int si_deno = 0;
		double si_no = 0.0d;
		double[] res = new double[2];

		for (int i = 0; i < grid_size; i++) {
			for (int j = 0; j < grid_size; j++) {
				double sir = 0.0d;
				int termNum = grid[i][j].getTermNum();
				if (termNum == 0)
					continue;

				Terminal t = grid[i][j].getTerminal();
				sir = t.getSIR(getID());

				double tmp = UAV.ld(sir);
				if (tmp > 0) {
					si_no += tmp * termNum;
					si_deno += termNum;
				}
			}
		}
		System.out.println("Terms: " + si_deno);
		if (si_deno == 0)
			si_deno = 1;

		res[0] = si_no;
		res[1] = si_deno;

		return res;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("id:" + getID() + " x: " + String.format("%.2f", x()) + " y:" + String.format("%.2f", y()) + " z:"
				+ String.format("%.2f", z()));

		return sb.toString();
	}

	public static void main(String[] args) {

	}
}
