import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

public class DecideUnitTests {

    /**
     * Test LIC0 with an input that contains two points further apart than LENGTH1, expecting true
     */
    @DisplayName("LIC0 True test")
    @Test
    void testLIC0True() {
        Parameters testParameters= new Parameters(5, 0, 0, 0, 0, 0, 0, 0, 0, 0,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
            double[] xpoints = {0.0, 5.1};
            double[] ypoints = {0.0, 0.0};

        Decide testDecide = new Decide(2, xpoints, ypoints, testParameters, null, null);
        assertTrue(testDecide.calculateLIC0());
    }

    /**
     * Test LIC0 with an input that contains two points not further apart than LENGTH1, expecting true
     */
    @DisplayName("LIC0 False test")
    @Test
    void testLIC0False() {
        Parameters testParameters= new Parameters(5, 0, 0, 0, 0, 0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
            double[] xpoints = {0.0, 5.0};
            double[] ypoints = {0.0, 0.0};

        Decide testDecide = new Decide(2, xpoints, ypoints, testParameters, null, null);
        assertFalse(testDecide.calculateLIC0());
    }

    /**
     * Test LIC1 with no three points that cannot be contained in a circle with RADIUS, expecting false
     */

    @DisplayName("LIC1 False test")
    @Test
    void calculateLIC1FalseTest(){
        int numpoints = 3;
        double [] Xpoints = new double []{0.0, 1.0, 0.0};
        double [] Ypoints = new double []{0.0, 0.0, 1.0};
        Parameters parameters = new Parameters( 0.0,0.71,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
        Decide decide = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
        assertFalse(decide.calculateLIC1());
    }

    /**
     * Test LIC1 with three consecutive points that cannot be contained in a circle with RADIUS, expecting true
     */
    @DisplayName("LIC1 True test")
    @Test
    void calculateLIC1TrueTest(){
        int numpoints = 3;
        double [] Xpoints = new double []{0.0, 1.0, 0.0};
        double [] Ypoints = new double []{0.0, 0.0, 1.0};
        Parameters parameters = new Parameters( 0.0,0.7,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
        Decide decide = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
        assertTrue(decide.calculateLIC1());
    }

    /**
     * Test LIC2 with no three consecutive points that form an angle larger than ANGLE, expecting false
     */
    @DisplayName("LIC2 False test")
    @Test
    void testLIC2False() {
        Parameters testParameters= new Parameters(0, 0, Math.PI/2, 0, 0, 0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
        double[] xpoints = {0.0, 0.0, 1.0};
        double[] ypoints = {1.0, 0.0, 0.0};
        Decide testDecide = new Decide(3, xpoints, ypoints, testParameters, null, null);
        assertFalse(testDecide.calculateLIC2());
    }

    /**
     * Test LIC2 with three consecutive points that form an angle larger than ANGLE, expecting true
     */
    @DisplayName("LIC2 True test")
    @Test
    void testLIC2True() {
        Parameters testParameters= new Parameters(0, 0, (Math.PI/2-0.0000001), 0, 0, 0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
        double[] xpoints = {0.0, 0.0, 1.0};
        double[] ypoints = {1.0, 0.0, 0.0};
        Decide testDecide = new Decide(3, xpoints, ypoints, testParameters, null, null);
        assertTrue(testDecide.calculateLIC2());
    }

    /**
     * Test LIC3 with three points that form an area of exactly AREA1, expecting false
     */
    @DisplayName("LIC3 False test")
    @Test
    void testLIC3False() {
        Parameters testParameters= new Parameters(0, 0, 0, 0.50, 0, 0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
        double[] xpoints = {0.0, 0.0, 1.0};
        double[] ypoints = {1.0, 0.0, 0.0};

        Decide testDecide = new Decide(3, xpoints, ypoints, testParameters, null, null);
        assertFalse(testDecide.calculateLIC3());
    }

    /**
     * Test LIC3 with three points that form an area larger than AREA1, expecting true
     */
    @DisplayName("LIC3 True test")
    @Test
    void testLIC3True() {
        //// There are two points apart exactly the length length1
        Parameters testParameters= new Parameters(0, 0, 0, 0.49, 0, 0,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
        double[] xpoints = {0.0, 0.0, 1.0};
        double[] ypoints = {1.0, 0.0, 0.0};

        Decide testDecide = new Decide(3, xpoints, ypoints, testParameters, null, null);
        assertTrue(testDecide.calculateLIC3());
    }

    /**
     * Test LIC4 such that 3 consecutive points fill 3 quads, expecting true.
     */
    @DisplayName("LIC4 True test")
    @Test
    void testLIC4True() {
        Parameters testParameters= new Parameters(0, 0, 0, 0, 3, 3, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
            double[] xpoints = {0.0, -1.0, -1.0, 1.0};
            double[] ypoints = {0.0, 1.0, -1.0, -1.0};
        Decide testDecide = new Decide(4, xpoints, ypoints, testParameters, null, null);
        assertTrue(testDecide.calculateLIC4());
    }

    /**
     * Test LIC 4 such that no 3 points fill 3 quads, expecting false
     */
    @DisplayName("LIC4 False test")
    @Test
    void testLIC4False() {
        Parameters testParameters= new Parameters(0, 0, 0, 0, 3, 3, 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0, 0, 0, 0);
            double[] xpoints = {1.0, 0.0, -1.0, -1.0};
            double[] ypoints = {1.0, 0.0, 0.0, -1.0};
        Decide testDecide = new Decide(4, xpoints, ypoints, testParameters, null, null);
        assertFalse(testDecide.calculateLIC4());
    }

    /**
     * Test LIC5 such that there are no two consecutive points for which the condition is true, expecting false
     */
    @DisplayName("LIC5 False test")
    @Test
    void calculateLIC5FalseTest(){
        int numpoints = 2;

        double [] Xpoints = new double []{0.0,1.0};
        double [] Ypoints = new double []{0.0,0.0};
        Decide d = new Decide(numpoints,Xpoints,Ypoints,null,null,null);
        assertFalse(d.calculateLIC5());

        Xpoints = new double []{1.0,1.0};
        d = new Decide(numpoints,Xpoints,Ypoints,null,null,null);
        assertFalse(d.calculateLIC5());

    }

    /**
     * Test LIC5 such that there are two consecutive points for which the condition is true, expecting true
     */
    @DisplayName("LIC5 true test")
    @Test
    void calculateLIC5TrueTest(){
        int numpoints = 2;
        double []Xpoints = new double []{1.0,0.0};
        double []Ypoints =  new double []{0.0,0.0};
        Decide d = new Decide(numpoints,Xpoints,Ypoints,null,null,null);
        assertTrue(d.calculateLIC5());
    }

    /**
     * Test LIC6 with points such that no N_PTS consecutive points exist that satisfy the requirements, expecting false
     */
    @DisplayName("LIC6 false test")
    @Test
    void calculateLIC6FalseTest(){
        int numpoints = 3 ;
        Parameters parameters = new Parameters(0.0, 0.0, 0.0, 0.0, 0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0);
        parameters.dist = 3;
        parameters.n_pts = 3;
        double [] Xpoints = new double[]{0.0,1.0,3.0};
        double [] Ypoints = new double []{0.0,2.0,0.0};
        Decide d = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
        assertFalse(d.calculateLIC6());
    }

    /**
     * Test LIC6 with points such that N_PTS consecutive points satisfy the requirements, expecting true
     */
    @DisplayName("LIC6 true test")
    @Test
    void calculateLIC6TrueTest(){
        int numpoints = 3;
        Parameters parameters = new Parameters(0.0, 0.0, 0.0, 0.0, 0, 0, 0.0, 3, 0, 0,
        0, 0, 0, 0, 0, 0, 0.0, 0.0, 0.0);
        double [] Xpoints = new double[]{0.0,0.0,2.0};
        double [] Ypoints = new double []{0.0,3.0,0.0};
        Decide d = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
        assertTrue(d.calculateLIC6());
        parameters.dist = 2.0;
        assertTrue(d.calculateLIC6());
    }

    /**
     * Test LIC7 such that there are no 2 points seperated by K_PTS that are at least LENGTH1 apart, expecting false
     */
    @DisplayName("LIC7 False test")
    @Test
    void calculateLIC7FalseTest(){
        int numpoints = 3 ;
        double[] XPoints = new double []{0.0,1.0,0.0};
        double[] YPoints = new double []{0.0, 0.0,1.0};
        Parameters parameters = new Parameters( 0.0,0.0,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
        parameters.k_pts = 1;
        parameters.length1 = 3.0;
        Decide d = new Decide(numpoints, XPoints, YPoints ,parameters,null,null);
        assertFalse(d.calculateLIC7());

        numpoints++;
        XPoints = new double []{0.0,1.0,0.0,3.0};
        YPoints = new double []{0.0, 0.0,1.0,0.0};
        d = new Decide(numpoints, XPoints, YPoints ,parameters,null,null);
        assertFalse(d.calculateLIC7());
    }

    /**
     * Test LIC7 such that there are 2 points seperated by K_PTS that are at least LENGTH1 apart, expecting true
     */
    @DisplayName("LIC7 True test")
    @Test
    void calculateLIC7TrueTest(){
        int numpoints = 3;
        Parameters parameters = new Parameters( 0.0,0.0,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
        parameters.k_pts = 1;
        parameters.length1 = 3.0;
        double [] XPoints = new double []{0.0,5.0,0.0};
        double [] YPoints = new double []{0.0,0.0,4.0};
        Decide d = new Decide(numpoints, XPoints, YPoints ,parameters,null,null);
        assertTrue(d.calculateLIC7());
    }

    /**
     * Test LIC8 such that there are no 3 points with A_PTS and B_PTS intervening, that cannot be contained in a RADIUS1 circle, expecting false
     */
    @DisplayName("LIC8 False test")
    @Test
    void calculateLIC8FalseTest(){
       int numpoints = 5 ;
       double [] Xpoints = new double []{0.0,1.0,0.0,-1.0,0.0};
       double [] Ypoints = new double []{0.0,0.0,1.0,0.0,-1.0};
       Parameters parameters = new Parameters( 0.0,0.0,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
       Decide d = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
       parameters.a_pts = 1 ;
       parameters.b_pts = 1 ;
       parameters.radius1= 1;
       assertFalse(d.calculateLIC8());

       numpoints++;
       Xpoints = new double []{-1,1.5,1.5,0.0,-1.5,1.0};
       Ypoints = new double []{0.0,0.5,-0.5,1.0,0.5,0.0};
       parameters.a_pts = 2;
       parameters.b_pts = 1 ;
       parameters.radius1= 2;
       d = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
       assertFalse(d.calculateLIC8());
    }

    /**
     * Test LIC8 such that there are 3 points with A_PTS and B_PTS intervening, that cannot be contained in a RADIUS1 circle, expecting true
     */
    @DisplayName("LIC8 TRUE test")
    @Test
    void calculateLIC8TrueTest(){
        int numpoints = 6;
        double [] Xpoints = new double []{-1,1.5,1.5,0.0,-1.5,1.0};
        double []Ypoints = new double []{0.0,0.5,-0.5,1.0,0.5,0.0};
        Parameters parameters = new Parameters( 0.0,0.0,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
        parameters.a_pts = 2;
        parameters.b_pts = 1 ;
        parameters.radius1= 0.5;
        Decide d = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
        assertTrue(d.calculateLIC8());
    }

    /**
     * Test LIC9 such that there exist no three points with C_PTS and D_PTS intervening such that a correct angle is formed, expecting false
     */
    @DisplayName("LIC9 False test")
    @Test
    void calculateLIC9FalseTest(){
        int numpoints = 3;
        double [] Xpoints = new double []{0.0,0.0,0.0};
        double [] Ypoints = new double []{0.0,1.0,-1.0};
        Parameters parameters = new Parameters( 0.0,0.0,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
        Decide d = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
        assertFalse(d.calculateLIC9());

        numpoints += 2 ;
        Xpoints = new double[]{-1.0,24.534,0.0,1.0,1.0};
        Ypoints = new double[]{-1.0,232.4,0.0,0.0,-1.0};
        parameters.c_pts = 1;
        parameters.d_pts = 1;
        parameters.epsilon = Math.PI/2;

        assertFalse(d.calculateLIC9());

    }

    /**
     * Test LIC9 such that there are three points with C_PTS and D_PTS intervening such that a correct angle is formed, expecting true
     */
    @DisplayName("LIC9 True test")
    @Test
    void calculateLIC9TrueTest(){
        int numpoints = 5;
        double [] Xpoints = new double[]{-1.0,24.534,0.0,1.0,1.0};
        double [] Ypoints = new double[]{-1.0,232.4,0.0,0.0,-1.0};
        Parameters parameters = new Parameters( 0.0,0.0,0.0,0.0,0,0,0.0,0,0,0,0,0,0,0,0,0,0.0,0.0,0.0);
        parameters.c_pts = 1;
        parameters.d_pts = 1;
        parameters.epsilon = Math.PI/2 - 0.001;
        Decide d = new Decide(numpoints,Xpoints,Ypoints,parameters,null,null);
        assertTrue(d.calculateLIC9());
    }

    /**
     * Test LIC10 such that there are 3 points with E_PTS and F_PTS intervening, that cover more than AREA1, expecting true
     */
    @DisplayName("LIC10 True test")
    @Test
    void calculateLIC10TrueTest(){
        Parameters p = new Parameters(0, 0, 0, 5.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0);
        double[] xPoints = {0.0,0.0,0.0,0.0,4.0};
        double[] yPoints = {0.0,0.0,3.0,0.0,0.0};
        Decide d = new Decide(5, xPoints, yPoints, p, null, null);
        assertTrue(d.calculateLIC10());
    }

    /**
     * Test LIC10 such that there are no 3 points with E_PTS and F_PTS intervening, that cover more than AREA1, expecting false
     */
    @DisplayName("LIC10 False test")
    @Test
    void calculateLIC10FalseTest(){
        Parameters p = new Parameters(0, 0, 0, 10.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0);
        double[] xPoints = {0.0,0.0,0.0,0.0,4.0};
        double[] yPoints = {0.0,0.0,3.0,0.0,0.0};
        Decide d = new Decide(5, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC10());
    }

    /**
     * Test LIC11 such that there are 2 points with G_PTS intervening that satisfy the condition, expecting true
     */
    @DisplayName("LIC11 True test")
    @Test
    void calculateLIC11TrueTest(){
        Parameters p = new Parameters(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0);
        double[] xPoints = {2.0,1.0,1.0,1.3};
        double[] yPoints = {1.0,1.0,1.0,1.0};
        Decide d = new Decide(4, xPoints, yPoints, p, null, null);
        assertTrue(d.calculateLIC11());
    }

    /**
     * Test LIC11 such that there are 2 points with G_PTS intervening that satisfy the condition, expecting false
     */
    @DisplayName("LIC11 False test")
    @Test
    void calculateLIC11FalseTest(){
        Parameters p = new Parameters(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0);
        double[] xPoints = {2.0,1.0,1.0,3.0};
        double[] yPoints = {1.0,1.0,1.0,1.0};
        Decide d = new Decide(4, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC11());
    }

    /**
     * Test LIC12 such that there are 2 points with K_PTS intervening, that are further than LENGTH1 apart (satisfied), and
     * 2 points with K_PTS intervening, that are less than LENGTH2 apart (satisfied), expecting true.
     */
    @DisplayName("LIC12 True test")
    @Test
    void calculateLIC12TrueTest(){
        Parameters p = new Parameters(4, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0);
        double[] xPoints = {1.0,3.0,2.0,4.0};
        double[] yPoints = {1.0,3.0,2.0,5.0};
        Decide d = new Decide(4, xPoints, yPoints, p, null, null);
        assertTrue(d.calculateLIC12());
    }

    /**
     * Test LIC12 such that there are no 2 points with K_PTS intervening, that are further than LENGTH1 apart (unsatisfied), and
     * 2 points with K_PTS intervening, that are less than LENGTH2 apart (satisfied), expecting false
     */
    @DisplayName("LIC12 False (cond1 false & cond2 true) test")
    @Test
    void calculateLIC12False1Test(){
        Parameters p = new Parameters(6, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0);
        double[] xPoints = {1.0,3.0,2.0,4.0};
        double[] yPoints = {1.0,3.0,2.0,5.0};
        Decide d = new Decide(4, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC12());
    }

    /**
     * Test LIC12 such that there are 2 points with K_PTS intervening, that are further than LENGTH1 apart (satisfied), and
     * no 2 points with K_PTS intervening, that are less than LENGTH2 apart (unsatisfied), expecting false.
     */
    @DisplayName("LIC12 False (cond1 true & cond2 false) test")
    @Test
    void calculateLIC12False2Test(){
        Parameters p = new Parameters(4, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0);
        double[] xPoints = {1.0,3.0,2.0,4.0};
        double[] yPoints = {1.0,3.0,2.0,5.0};
        Decide d = new Decide(4, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC12());
    }

    /**
     * Test LIC13 such that there are 3 points with A_PTS and B_PTS intervening, that cannot fit in a circle of RADIUS1 (satisfied),
     * and 3 points with A_PTS and B_PTS intervening that can fit in a circle of RADIUS2 (satisfied), expecting true
     */
    @DisplayName("LIC13 True test")
    @Test
    void calculateLIC13TrueTest(){
        Parameters p = new Parameters(0, 3.0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1.0, 0);
        double[] xPoints = {0.0,0.0,3.5,1.0,0.0,1.0,0.0};
        double[] yPoints = {0.0,0.0,0.0,0.0,0.0,0.0,5.0};
        Decide d = new Decide(7, xPoints, yPoints, p, null, null);
        assertTrue(d.calculateLIC13());
    }

    /**
     * Test LIC13 such that there are no 3 points with A_PTS and B_PTS intervening, that cannot fit in a circle of RADIUS1 (unsatisfied),
     * and 3 points with A_PTS and B_PTS intervening that can fit in a circle of RADIUS2 (satisfied), expecting false
     */
    @DisplayName("LIC13 False (cond1 false & cond2 true) test")
    @Test
    void calculateLIC13False1Test(){
        Parameters p = new Parameters(0, 7.0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1.0, 0);
        double[] xPoints = {0.0,0.0,3.5,1.0,0.0,1.0,0.0};
        double[] yPoints = {0.0,0.0,0.0,0.0,0.0,0.0,5.0};
        Decide d = new Decide(7, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC13());
    }

    /**
     * Test LIC13 such that there are 3 points with A_PTS and B_PTS intervening, that cannot fit in a circle of RADIUS1 (satisfied),
     * and no 3 points with A_PTS and B_PTS intervening that can fit in a circle of RADIUS2 (unsatisfied), expecting false
     */
    @DisplayName("LIC13 False (cond1 true & cond2 false) test")
    @Test
    void calculateLIC13False2Test(){
        Parameters p = new Parameters(0, 3.0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0.3, 0);
        double[] xPoints = {0.0,0.0,3.5,1.0,0.0,1.0,0.0};
        double[] yPoints = {0.0,0.0,0.0,0.0,0.0,0.0,5.0};
        Decide d = new Decide(7, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC13());
    }

     /**
     * Test LIC14 such that there are 3 points with E_PTS and F_PTS intervening, that cover more than AREA1 (satisfied),
     * and 3 points with E_PTS and F_PTS intervening that cover less than AREA2 (satisfied), expecting true
     */
    @DisplayName("LIC14 True test")
    @Test
    void calculateLIC14TrueTest(){
        Parameters p = new Parameters(0, 0, 0, 1.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 7.0);
        double[] xPoints = {0.0,0.0,0.0,0.0,4.0};
        double[] yPoints = {0.0,0.0,3.0,0.0,0.0};
        Decide d = new Decide(5, xPoints, yPoints, p, null, null);
        assertTrue(d.calculateLIC14());
    }

    /**
     * Test LIC14 such that there are no 3 points with E_PTS and F_PTS intervening, that cover more than AREA1 (unsatisfied),
     * and 3 points with E_PTS and F_PTS intervening that cover less than AREA2 (satisfied), expecting false
     */
    @DisplayName("LIC14 False (cond1 false & cond2 true) test")
    @Test
    void calculateLIC14False1Test(){
        Parameters p = new Parameters(0, 0, 0, 7.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 7.0);
        double[] xPoints = {0.0,0.0,0.0,0.0,4.0};
        double[] yPoints = {0.0,0.0,3.0,0.0,0.0};
        Decide d = new Decide(5, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC14());
    }

    /**
     * Test LIC14 such that there are 3 points with E_PTS and F_PTS intervening, that cover more than AREA1 (satisfied),
     * and no 3 points with E_PTS and F_PTS intervening that cover less than AREA2 (unsatisfied), expecting false
     */
    @DisplayName("LIC14 False (cond1 true & cond2 false) test")
    @Test
    void calculateLIC14False2Test(){
        Parameters p = new Parameters(0, 0, 0, 1.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 5.0);
        double[] xPoints = {0.0,0.0,0.0,0.0,4.0};
        double[] yPoints = {0.0,0.0,3.0,0.0,0.0};
        Decide d = new Decide(5, xPoints, yPoints, p, null, null);
        assertFalse(d.calculateLIC14());
    }

    /**
     * Test euclidian distance calculation between two points
     *
     * @result the euclidean distance between (-7, -4) and (-17, 6.5) should be 14.5
     */
    @DisplayName("Test euclidiane distance")
    @Test
    void eucl_distTest(){
        double xPoints[] = {-7, -17};
        double yPoints[] = {-4, 6.5};
        Decide d = new Decide(2, xPoints, yPoints, null, new LCMEntries[1][1], new boolean[1]);
        assertEquals(14.5, d.eucl_dist(0, 1));
    }

    /**
     * Test the function circle_can_contain with three points that can be contained in the circle
     *
     * @result (0, 0), (1.3, 0.2) and (1.6, 0) should fit in a circle with radius 1.0
     */
    @DisplayName("Test circle can contain inside circle (true)")
    @Test
    void circle_can_containInTest() {
        double xPoints[] = {0.0, 1.3, 1.6};
        double yPoints[] = {0.0, 0.2, 0.0};
        Decide d = new Decide(3, xPoints, yPoints, null, null, null);
        double radius = 1.0;
        assertTrue(d.circle_can_contain(0, 1, 2, radius));
    }

    /**
     * Test the function circle_can_contain with three points can be contained on the circle
     *
     * @result (-1, 0), (0, 1) and (1, 0) should fit on a circle with radius 1.0
     */
    @DisplayName("Test circle can contain on circle (true)")
    @Test
    void circle_can_containOnTest() {
        double xPoints[] = {-1.0, 0.0, 1.0};
        double yPoints[] = {0.0, 1.0, 0.0};
        Decide d = new Decide(3, xPoints, yPoints, null, null, null);
        double radius = 1.0;
        assertTrue(d.circle_can_contain(0, 1, 2, radius));
    }

    /**
     * Test the function circle_can_contain with three points that cannot be contained in or on the circle
     *
     * @result (0, 5), (3, 1) and (1, 1) should not fit in or on a circle with radius 1.0
     */
    @DisplayName("Test circle can contain outside circle (false)")
    @Test
    void circle_can_containOutTest() {
        double xPoints[] = {0.0, 3.0, 1.0};
        double yPoints[] = {5.0, 1.0, 1.0};
        Decide d = new Decide(3, xPoints, yPoints, null, null, null);
        double radius = 1.0;
        assertFalse(d.circle_can_contain(0, 1, 2, radius));
    }

    /**
     * Test the angle function with an expected true result
     *
     * @result Points (-1, .1), (0, 0) and (1, 0) should form an angle > pi + .05 (true)
     */
    @DisplayName("Test angle with true result")
    @Test
    void angleTrueTest() {
        double xPoints[] = {-1.0, 0.0, 1.0};
        double yPoints[] = {.1, 0.0, 0.0};
        double epsilon = .05;
        Parameters p = new Parameters(0, 0, epsilon, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Decide d = new Decide(3, xPoints, yPoints, p, null, null);
        assertTrue(d.angle(0, 1, 2));
    }

    /**
     * Test the angle function with an expected false result
     *
     * @result Points (-1, .1), (0, 0) and (1, 0) should form an pi - .1 <= angle <= pi + .1 (true)
     */
    @DisplayName("Test angle with false result")
    @Test
    void angleFalseTest() {
        double xPoints[] = {-1.0, 0.0, 1.0};
        double yPoints[] = {.1, 0.0, 0.0};
        double epsilon = .1;
        Parameters p = new Parameters(0, 0, epsilon, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Decide d = new Decide(3, xPoints, yPoints, p, null, null);
        assertFalse(d.angle(0, 1, 2));
    }

    /**
     * Test areaCalculation function for calculation of a triangle area
     *
     * @result The area of triangle (0, 0), (-5, 1.2), (0, 2) should be 1.2
     */
    @DisplayName("Test area calculation")
    @Test
    void areaCalculationTest() {
        double xPoints[] = {0.0, -5.0, 2.0};
        double yPoints[] = {0.0, 1.2, 0.0};
        Decide d = new Decide(3, xPoints, yPoints, null, null, null);
        assertEquals(1.2, d.areaCalculation(0, 1, 2));
    }

    /**
     * Test the function for PUM calculation
     *
     * @result passes if the PUM is correctly calculated from the CMV and LCM
     */
    @DisplayName("Test PUM calculation")
    @Test
    void calculatePUMTest() {
        LCMEntries[][] lcm = {
            {LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ORR, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ORR, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED}
        };
        boolean cmv[] = {false, true, true, true, false, false, false, false, false, false, false, false, false, false, false};

        boolean[][] expected = {
            {false, false, true, false, true, true, true, true, true, true, true, true, true, true, true},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true}
        };

        Decide d = new Decide(2, null, null, null, lcm, null);
        assertArrayEquals(expected, d.calculatePUM(cmv));
    }

    /**
     * Test the function for FUV calculation
     *
     * @result passes if the FUV is correctly calculated from the PUV and PUM
     */
    @DisplayName("Test FUV calculation")
    @Test
    void calculateFUVTest() {
        boolean[] puv = {true, false, true, true, true, false, false, false, false, false, false, false, false, false, false};
        boolean[][] pum = {
            {false, false, true, false, true, true, true, true, true, true, true, true, true, true, true},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true}
        };


        boolean[] expected = {false, true, true, false, true, true, true, true, true, true, true, true, true, true, true};

        Decide d = new Decide(2, null, null, null, null, puv);
        assertArrayEquals(expected, d.calculateFUV(pum));
    }

    /**
     * Test launch calculation with an expected false
     *
     * @result should be true with input {true, true, true, true}
     */
    @DisplayName("Launch True test")
    @Test
    void calculateLaunchTrueTest(){
        Decide d = new Decide(5, new double[1], new double[1], null, new LCMEntries[1][1], new boolean[1]);
        boolean fuv[] = {true, true, true, true};
        assertTrue(d.calculateLaunch(fuv));
    }

    /**
     * Test launch calculation with an expected false
     *
     * @result should be false with input {false, true, true, true}
     */
    @DisplayName("Launch False test")
    @Test
    void calculateLaunchFalseTest(){
        Decide d = new Decide(5, new double[1], new double[1], null, new LCMEntries[1][1], new boolean[1]);
        boolean fuv[] = {false, true, true, true};
        assertFalse(d.calculateLaunch(fuv));
    }

    /**
     * Testing the entire program with valid arguments, expecting true
     */
    @DisplayName("Test function Calculate everything and print")
    @Test
    void calculateEverythingAndPrintTrueTest(){
        int numPoints = 10;
        double xPoints[] = {1.0, 0.0, -1.0, 2.0, -3.0, 1.5, 1.5, 0.0, -0.5, 1.0};
        double yPoints[] = {0.0, -1.0, -3.0, 0.0, 1.0, -2.5, 3.0, -2.0, 0.0, 0.0};
        LCMEntries[][] lcm = {
            {LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ORR, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ORR, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED}
        };
        boolean[] puv = {true, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        Parameters p = new Parameters(4, 0, 0, 0, 3, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0);
        p.dist = 2.0;
        p.n_pts = 3;
        Decide d = new Decide(numPoints, xPoints, yPoints, p, lcm, puv);
        assertTrue(d.calculateEverythingAndPrint());
    }

    /**
     * Test the entire program with invalid arguments, expecting an exception
     */
    @Test
    public void calculateEverythingAndPrintExceptTest() {
        int numPoints = 1;
        double xPoints[] = {1.0};
        double yPoints[] = {0.0};
        LCMEntries[][] lcm = {
            {LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ORR, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ORR, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.ANDD, LCMEntries.ORR, LCMEntries.ANDD, LCMEntries.ANDD, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED},
            {LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED, LCMEntries.NOTUSED}
        };
        boolean[] puv = {true, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        Parameters p = new Parameters(4, 0, 0, 0, 3, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0);
        p.dist = 2.0;
        p.n_pts = 3;
        // gives an exception if we try to create a instance of decide with less than 2 points
        assertThrows(IllegalArgumentException.class, () -> new Decide(numPoints, xPoints, yPoints, p, lcm, puv));
    }
}
