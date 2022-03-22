import java.util.Arrays;

/**
 * All values that can be used inside the Logical Connector Matrix
 */
enum LCMEntries {
	ANDD, ORR, NOTUSED;
}

/**
 * Contains all methods related to DECIDE()
 */
public class Decide {
	private final int numPoints; // Number of datapoints
	private final double[] xPoints; // Arraycontaining the coordinates of data points in format x0,x1,x2,...
	private final double[] yPoints; // Arraycontaining the coordinates of data points in format y0,y1,y2,...
	private final Parameters parameters; // Struct holding parameters for LIC's
	private final LCMEntries[][] lcm; // Logical Connector Matrix
	private final boolean[] puv; // Preliminary Unlocking Vector

	public Decide(int numPoints, double[] xPoints, double[] yPoints, Parameters parameters, LCMEntries[][] lcm,
			boolean[] puv) {
		if ((numPoints < 2) || (numPoints > 100)) {
			throw new IllegalArgumentException("numPoints wrong size");
		}

		this.numPoints = numPoints;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.parameters = parameters;
		this.lcm = lcm;
		this.puv = puv;
	}

	/**
	 * Compiles the CMV from individual LIC calculations
	 *
	 * @return CMV, 15-vector
	 */
	private boolean[] calculateCMV() {
		return new boolean[] { calculateLIC0(), calculateLIC1(), calculateLIC2(), calculateLIC3(), calculateLIC4(),
				calculateLIC5(), calculateLIC6(), calculateLIC7(), calculateLIC8(), calculateLIC9(), calculateLIC10(),
				calculateLIC11(), calculateLIC12(), calculateLIC13(), calculateLIC14() };
	}

	/**
	 * Calculates the PUM from the CMV and the LCM
	 *
	 * @param cmv CMV, 15-vector
	 * @return PUM, 15*15-matrix
	 */
	public boolean[][] calculatePUM(boolean[] cmv) {
		boolean[][] pum = new boolean[15][15];

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				switch (lcm[i][j]) {
					case ANDD:
						pum[i][j] = cmv[i] && cmv[j];
						break;
					case ORR:
						pum[i][j] = cmv[i] || cmv[j];
						break;
					case NOTUSED:
						pum[i][j] = true;
						break;
				}
			}
		}
		return pum;
	}

	/**
	 * Calculates the FUV from the PUM and the FUV
	 *
	 * @param pum PUM, 15*15-matrix
	 * @return FUV, 15-vector
	 */
	public boolean[] calculateFUV(boolean[][] pum) {
		boolean[] fuv = new boolean[15];
		for (int i = 0; i < 15; i++) {
			if (!puv[i]) {
				fuv[i] = true;
			} else {
				boolean temp = true;
				for (int j = 0; j < 15; j++) {
					if (j != i) {
						temp = temp && pum[i][j];
					}
				}
				fuv[i] = temp;
			}
		}
		return fuv;
	}

	/**
	 * Calculates LAUNCH from the FUV
	 *
	 * @param fuv FUV, 15-vector
	 */
	public boolean calculateLaunch(boolean[] fuv) {
		for (boolean b : fuv) {
			if (!b) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Runs all necessary functions in order and prints the result to the standard output
	 *
	 * @return Whether or not to launch
	 */
	public boolean calculateEverythingAndPrint() {
		boolean[] cmv = this.calculateCMV();
		boolean[][] pum = this.calculatePUM(cmv);
		boolean[] fuv = this.calculateFUV(pum);
		boolean launch = this.calculateLaunch(fuv);
		System.out.println(launch ? "TRUE" : "FALSE");
		return launch;
	}

	/**
	 * LIC0 returns true if there is at least one set of two consecutive data points that are a
	 * distance greater than LENGTH1 apart.
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC0() {
		for (int i = 0; i <= xPoints.length - 2; i += 1) {
			if (eucl_dist(i, i + 1) > parameters.length1) {  //this.parameters.length1
				return true;
			}
		}
		return false;
	}

	/**
	 * LIC1 returns true if there is at least one set of three consecutive data points that cannot
		 * all be contained within or on a circle of radius RADIUS1.
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC1() {
		for (int i = 0; i <= xPoints.length - 3; i += 1) {
			if (!(circle_can_contain(i, i + 1, i + 2, parameters.radius1))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * LIC2 returns whether there exists at least one set of three consecutive data points which form an
	 * angle such that: angle < (PI−EPSILON) or angle > (PI+EPSILON) The second of
	 * the three consecutive points is always the vertex of the angle. If either the
	 * first point or the last point (or both) coincides with the vertex, the angle
	 * is undefined and the LIC is not satisfied by those three points.
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC2() {
		/**
		 * T
		 **/
		for (int i = 0; i <= xPoints.length - 3; i += 1) {
			if (angle(i, i + 1, i + 2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * LIC3 returns whether there exists at least one set of three consecutive data points that are the
	 * vertices of a triangle with area greater than AREA1
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC3() {
		if (0 > parameters.area1) {
			throw new IllegalArgumentException("LIC3: Area1 not > 0 ");
		}

		for (int i = 0; i <= xPoints.length - 3; i += 1) {
			double area = areaCalculation(i, i+1, i+2);
			if (area > parameters.area1){
				return true;
			}
			area = 0;
		}
		return false;
	}

	/**
	 * LIC4 returns whether there exists at least one set of Q PTS consecutive data points that lie in
	 * more than QUADS quadrants. Where there is ambiguity as to which quadrant
	 * contains a given point, priority of decision will be by quadrant number,
	 * i.e., I, II, III, IV. For example, the data point (0,0) is in quadrant I, the
	 * point (-l,0) is in quadrant II, the point (0,-l) is in quadrant III, the
	 * point (0,1) is in quadrant I and the point (1,0) is in quadrant I.
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC4() {
		Boolean[] quadrants_checked = new Boolean[4];
		Arrays.fill(quadrants_checked, false);

		int quadPoints = parameters.q_pts;
		int quads = parameters.quads;

		if (!(2 <= quadPoints && quadPoints <= numPoints)) {
			throw new IllegalArgumentException("LIC4: illegal q_pts value");
		}
		if (!(1 <= quads && quads <= 3)) {
			throw new IllegalArgumentException("LIC4: illegal quads value");
		}
		//Numpoints is too low
			for (int i = 0; (i+quadPoints-1) <= numPoints; i += 1) {
				// Maybe we need -1 if the condition for the first for loop
				for (int j = 0; j < (quadPoints+i-1); j += 1) {
					if ((xPoints[j] >= 0) && (yPoints[j] >= 0)) { // quadrant 1 has highest priority
						quadrants_checked[0] = true;
					} else if ((xPoints[j] < 0) && (yPoints[j] >= 0)) { // quadrant 2 is strong against 3 on the y axis
						quadrants_checked[1] = true;
					} else if ((xPoints[j] <= 0) && (yPoints[j] < 0)) { // quadrant 3 is strong against 4 on the ex axis
						quadrants_checked[2] = true;
					} else if ((xPoints[j] > 0) && (yPoints[j] < 0)) { // quadrant 4 is weak against all
						quadrants_checked[3] = true;
					}
				}
				// j is out, time to check if this consecutive made it
				int counter = 0;
				for (boolean b : quadrants_checked) {
					if (b) {
						counter++;
					}
				}
				if (counter > quads) { // yupp
					return true;
				}
				// nope
				Arrays.fill(quadrants_checked, Boolean.FALSE);
			}

			return false;


	}

	/**
	 * LIC5 returns whether there exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), such
	 * that X[j] - X[i] < 0. (where i = j-1)
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC5() {
		for (int i = 0; i <= xPoints.length - 2; i += 1) {
			if (xPoints[i+1] - xPoints[i] < 0) {
				return true;
			}
		}
		return false;

	}

	/**
	 * LIC6 determines whether there exists at least one set of N PTS consecutive data points such that at least one of the
	 * points lies a distance greater than DIST from the line joining the first and last of these N PTS
	 * points. If the first and last points of these N PTS are identical, then the calculated distance
	 * to compare with DIST will be the distance from the coincident point to all other points of
	 * the N PTS consecutive points. The condition is not met when NUMPOINTS < 3.
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC6() {
		if(numPoints < 3){
			return false;
		}
		for(int B = 0 ; B <= numPoints - parameters.n_pts ; B++) { // B: first point
			int C = B + parameters.n_pts - 1; // last point
			double BC = eucl_dist(B,C);

			for(int A = B + 1 ; A < C; A++){ // A: middle point
				double AB = eucl_dist(A, B);
				double AC = eucl_dist(A, C);

				if(BC == 0){ //check if the first point and last are identical
					if(AC > parameters.dist){
						return true ;
					}else{
						continue ; //next point
					}
				}
				if(AB == 0 || AC ==0){
					//next point because the current point is at a distance 0 from the line
					continue;
				}

				double angleB = Math.acos((Math.pow(BC,2) + Math.pow(AB,2) - Math.pow(AC,2))/2*AB*BC);
				double angleC = Math.acos((Math.pow(BC,2) + Math.pow(AC,2) - Math.pow(AB,2))/2*BC*AC);

				if(angleC >= Math.PI/2){
					double newAngle = Math.PI - angleC;
					double dist = Math.sin(newAngle)*AC;
					if(dist > parameters.dist){
						return true;
					}else{
						continue; //next point
					}
				}else if( angleB >= Math.PI/2){
					double newAngle = Math.PI - angleB;
					double dist = Math.sin(newAngle)*AB;
					if(dist > parameters.dist){
						return true;
					}else{
						continue;
					}
				}
				double height = AB*Math.sin(angleB);
				if(height > parameters.dist){
					return true;
				}


			}
		}
		return false;
	}

	/**
	 * LIC7 determines whether there exists at least one set of two data points separated by exactly K PTS consecutive intervening
	 * points that are a distance greater than the length, LENGTH1, apart. The condition
	 * is not met when NUMPOINTS < 3.
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public  boolean calculateLIC7() {
		if(numPoints < 3){
			return false;

		}
		int last = numPoints - parameters.k_pts - 1;
		for(int i = 0 ; i < last ; i++ ){
			if(eucl_dist(i,i+1+parameters.k_pts) > parameters.length1){
				return true;
			}
		}
		return false;
	}

	/**
	 * LIC8 determines whether there exists at least one set of three data points separated by exactly A PTS and B PTS
	 * consecutive intervening points, respectively, that cannot be contained within or on a circle of
	 * radius RADIUS1. The condition is not met when NUMPOINTS < 5
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC8() {
		if(numPoints < 5 ){
			return false;
		}
		int last = numPoints - parameters.a_pts - parameters.b_pts -2 ;

		for(int i = 0 ; i < last ; i++){
			int P1 = i ;
			int P2 = i + parameters.a_pts + 1;
			int P3 = i + parameters.a_pts + parameters.b_pts + 2 ;
			if(!circle_can_contain(P1,P2,P3,parameters.radius1)){
				return true ;
			}

		}
		return false;

	}

	/**
	 * LIC9 determines whether there exists at least one set of three data points separated by exactly C PTS and D PTS
	 * consecutive intervening points, respectively, that form an angle such that: angle < (PI-EPSILON) or angle > (PI+EPSILON)
	 * The second point of the set of three points is always the vertex of the angle. If either the first
	 * point or the last point (or both) coincide with the vertex, the angle is undefined and the LIC
	 * is not satisfied by those three points. When NUMPOINTS < 5, the condition is not met.
	 *
	 * @return true if the conditions are met, false otherwise
	 */
	public boolean calculateLIC9() {
		if(numPoints < 5){
			return false;
		}

		int last = numPoints - parameters.a_pts - parameters.b_pts -2 ;

		for(int i = 0 ; i < last ; i++){
			int A = i ;
			int B = i + parameters.a_pts + 1;
			int C = i + parameters.a_pts + parameters.b_pts + 2 ;

			if(angle(A,B,C)){
				return true ;
			}

		}
		return false;
	}

	/**
	 * Angle determines if the points A, B and C form an angle such that angle < PI-EPSILON or angle > PI+EPSILON
	 * @param A point A
	 * @param B point B
	 * @param C point C
	 * @return true if the condition is met, false otherwise
	 */
	public boolean angle(int A, int B, int C){
			double AB = eucl_dist(A,B);
			double BC = eucl_dist(B,C);
			double AC = eucl_dist(A,C);

			if(AB == 0 || BC == 0){
				return false ; //AC can be 0
			}
			double a = Math.acos((BC* BC + AB* AB - AC * AC) / (2 * BC * AB));
			if(a < Math.PI - parameters.epsilon || a > Math.PI+ parameters.epsilon){
				return true;
			}
			return false;


	}

	/**
	 * LIC10 will return true if the is a set of 3 datapoints separated by e_pts and
	 * f_pts whos spanning triangle will have an area greater than area1.
	 *
	 * @return true if above, false otherwise
	 */
	public boolean calculateLIC10() {
		if (numPoints < 5 || parameters.e_pts < 1 || parameters.f_pts < 1
				|| parameters.e_pts + parameters.f_pts + 3 > numPoints) {

			return false;
		}
		int point1, point2, point3;
		for (int i = 0; i < (numPoints - parameters.e_pts - parameters.f_pts - 2); i++) {
			point1 = i;
			point2 = i + parameters.e_pts + 1;
			point3 = i + parameters.e_pts + parameters.f_pts + 2;
			if (areaCalculation(point1, point2, point3) > parameters.area1)
				return true;
		}

		return false;
	}

	/**
	 * LIC11 returns true if there excists atleast one set of 2 data points
	 * (X[i],Y[i]) and (X[j],Y[j]) separated by exactly G-PTS.
	 *
	 * @return boolean true if above is satisfied, false otherwise
	 */
	public boolean calculateLIC11() {
		if (parameters.g_pts < 1 || parameters.g_pts > numPoints - 2) {
			return false;
		}
		for (int i = 0; i < xPoints.length - parameters.g_pts - 1; i++) {
			if (xPoints[i + parameters.g_pts + 1] - xPoints[i] < 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * LIC12 returns true if there excists atleast one set of 2 data points,
	 * separated by k-pts, that are further than lenght1 apart and if there excists
	 * atleast one set of 2 data points ,separated by k-pts, that are closer than
	 * length2 apart.
	 *
	 * @return true if above, false otherwise
	 */
	public boolean calculateLIC12() {
		if (numPoints < 3 || parameters.length2 < 0) {
			return false;
		}
		boolean condition1 = false;
		boolean condition2 = false;

		for (int i = 0; i < (numPoints - parameters.k_pts - 1); i++) {
			if (eucl_dist(i, i + parameters.k_pts + 1) > parameters.length1) {
				condition1 = true;
			}
			if (eucl_dist(i, i + parameters.k_pts + 1) < parameters.length2) {
				condition2 = true;
			}
			if (condition1 && condition2) {
				return true;
			}
		}
		return false;
	}

	/**
	 * LIC13 returns true if there excists atleast one set of 3 data points that are
	 * separeted by atleast a_pts and b_pts respectively that can be contained in a
	 * circle with randius2 and that cannot be contained within a circle of radius1.
	 * Can be the same or different sets of 3 points for both.
	 *
	 * @return true if above, otherwise false
	 */
	public boolean calculateLIC13() {
		if (numPoints < 5 || parameters.radius2 < 0) {

			return false;
		}
		boolean condition1 = false;
		boolean condition2 = false;
		int point1, point2, point3;

		for (int i = 0; i < (numPoints - parameters.a_pts - parameters.b_pts - 2); i++) {
			point1 = i;
			point2 = i + parameters.a_pts + 1;
			point3 = i + parameters.a_pts + parameters.b_pts + 2;

			if (!circle_can_contain(point1, point2, point3, parameters.radius1)) {
				condition1 = true;
			}
			if (circle_can_contain(point1, point2, point3, parameters.radius2)) {
				condition2 = true;
			}
			if (condition1 && condition2)
				return true;
		}

		return false;
	}

	/**
	 * LIC14 returns true if there excists atleast one set of 3 data points that are
	 * separeted by atleast e_pts and f_pts respectively can span a triangle with an
	 * area of area1 between the points and that a set of 3 data points cannot span
	 * a triangle of area2 between them. Can be the same or different sets of 3
	 * points for both.
	 *
	 * @return true if above, otherwise false
	 */

	public boolean calculateLIC14() {
		if (numPoints < 5 || parameters.area2 < 0) {
			return false;
		}
		boolean condition1 = false;
		boolean condition2 = false;
		int point1, point2, point3;

		for (int i = 0; i < (numPoints - parameters.e_pts - parameters.f_pts - 2); i++) {
			point1 = i;
			point2 = i + parameters.e_pts + 1;
			point3 = i + parameters.e_pts + parameters.f_pts + 2;

			if (areaCalculation(point1, point2, point3) > parameters.area1) {
				condition1 = true;
			}
			if (areaCalculation(point1, point2, point3) < parameters.area2) {
				condition2 = true;
			}
			if (condition1 && condition2)
				return true;
		}

		return false;
	}

	/**
	 * Calculates the distance between to points in the points arrays
	 *
	 * @param p0 Index of point 0
	 * @param p1 Index of point 1
	 * @return The distance between point 0 and point 1
	 */
	public double eucl_dist(int p0, int p1) {
		return Math.sqrt(Math.pow((xPoints[p1] - xPoints[p0]), 2) + Math.pow((yPoints[p1] - yPoints[p0]), 2));
	}

	/**
	 * Calculates whether a circle of certain radius can contain three specified points.
	 *
	 * @param x0 Index of point 0
	 * @param x1 Index of point 1
	 * @param x2 Index of point 2
	 * @param radius Radius of the circle
	 * @return Whether the circle can contain the three points
	 */
	public boolean circle_can_contain(int x0, int x1, int x2, double radius) {
		// for theory check
		// https://matthewhr.wordpress.com/2013/03/23/circumscribed-circle-vs-minimal-enclosing-circle-ii/
		// we have 4 cases: acute-, obtuse-, rightside- circumscribed circle or no
		// triangle.
		// we get the triangle-walls first.

		double A = eucl_dist(x0, x1);
		double B = eucl_dist(x1, x2);
		double C = eucl_dist(x2, x0);
		if (A == 0 || B == 0 || C == 0) { // The triangle is in fact, a line, so check only if line < radius*2
			return Math.max(A, Math.max(B, C)) * 0.5 <= radius; //added <=
		}
		// we get the corners of the sides through law of cosines
		double a = Math.acos((B * B + C * C - A * A) / (2 * B * C));
		double b = Math.acos((A * A + C * C - B * B) / (2 * C * A));
		double c = Math.acos((A * A + B * B - C * C) / (2 * A * B));


		// We test for obtuse triangle, that is if any corner is larger than 90 degrees
		// or PI/2 (rad)
		if ((a > (Math.PI / 2)) || (b > (Math.PI / 2)) || (c > (Math.PI / 2))) { //also here
			// Triangle obtuse, we set the center of the circle in the middle of the longest
			// side
			return Math.max(A, Math.max(B, C)) * 0.5 <= radius; //added <=
		} else {
			// triangle acute
			// formula for radius necessitated is found at
			// https://en.wikibooks.org/wiki/Trigonometry/Circles_and_Triangles/The_Circumcircle
			double circumradius = (A / (2 * Math.sin(a)));
			return circumradius < radius;
		}

	}

	/**
	 * Calculates the area for a spanning triangle between three data points.
	 *
	 * @param p1 index for first datapoint
	 * @param p2 index for second datapoint
	 * @param p3 index for third datapoint
	 * @return area of the spanning triangle
	 */
	public double areaCalculation(int p1, int p2, int p3) {
		return Math.abs((xPoints[p1] * (yPoints[p2] - yPoints[p3]) + xPoints[p2] * (yPoints[p3] - yPoints[p1])
				+ xPoints[p3] * (yPoints[p1] - yPoints[p2])) / 2.0);
	}

	public static void main(String[] args) {
		// Decide decide = new Decide();

	}

}
