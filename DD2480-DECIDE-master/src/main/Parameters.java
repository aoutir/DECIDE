/**
 * Class holding all parameters used by LICs
 */
public class Parameters {

	double length1; // Length in LICs 0, 7, 12
	double radius1; // Radius in LICs 1, 8, 13
	double epsilon; // Deviation from PI in LICs 2, 9
	double area1; // Area in LICs 3, 10, 14
	int q_pts; // No. of consecutive points in LIC 4
	int quads; // No. of quadrants in LIC 4
	double dist; // Distance in LIC 6
	int n_pts; // No. of consecutive pts . in LIC 6
	int k_pts; // No. of int. pts. in LICs 7, 12
	int a_pts; // No. of int. pts. in LICs 8, 13
	int b_pts; // No. of int. pts. in LICs 8, 13
	int c_pts; // No. of int. pts. in LIC 9
	int d_pts; // No. of int. pts. in LIC 9
	int e_pts; // No. of int. pts. in LICs 10, 14
	int f_pts; // No. of int. pts. in LICs 10, 14
	int g_pts; // No. of int. pts. in LIC 11
	double length2; // Maximum length in LIC 12
	double radius2; // Maximum radius in LIC 13
	double area2; // Maximum area in LIC 14

	Parameters(double l1, double r1, double epsilon, double a1, int q, int quads, double dist, int n, int k, int a,
			int b, int c, int d, int e, int f, int g, double l2, double r2, double a2) {
		this.length1 = l1;
		this.radius1 = r1;
		this.epsilon = epsilon;
		this.area1 = a1;
		this.q_pts = q;
		this.quads = quads;
		this.dist = dist;
		this.n_pts = n;
		this.k_pts = k;
		this.a_pts = a;
		this.b_pts = b;
		this.c_pts = c;
		this.d_pts = d;
		this.e_pts = e;
		this.f_pts = f;
		this.g_pts = g;
		this.length2 = l2;
		this.radius2 = r2;
		this.area2 = a2;
	}
}
