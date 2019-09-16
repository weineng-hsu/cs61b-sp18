

public class NBody{

	/* background galaxy image: "images/starfield.jpg" */
	public static String background = "images/starfield.jpg";
	public static int numPlanet;
	public static double radius;

	/* This method reads galaxy radius from file: ex"data/planets.txt" 
	*using data base In class
	* The first int is the number of the bodys
	* The second double is the galaxy radius*/
	public static double readRadius (String route) {
		In in = new In(route);
		numPlanet = in.readInt();
		radius = in.readDouble();
		return radius;
	}

	/* This method reads plnaet data loop through the file: ex"data/planets.txt" */
	public static Planet[] readPlanets(String route) {
		In in = new In(route);
		numPlanet = in.readInt();
		double secondItemInFile = in.readDouble();
		Planet[] p = new Planet[numPlanet];
		for (int i=0; i < numPlanet; i++) {
			double xxPos=in.readDouble();
			double yyPos=in.readDouble();
			double xxVel=in.readDouble();
			double yyVel=in.readDouble();
			double mass =in.readDouble();
			String imageFileName=in.readString();
			p [i]= new Planet(xxPos,yyPos,xxVel,yyVel,mass,imageFileName);
		} return p;
	}

	/* Draw background galaxy image: "images/starfield.jpg" 
	* using StdDraw database
	*StdDraw.setScale(a,b): set canvas from x:a-b, y:a-b
	*StdDraw.clear(): clear image
	*StdDraw.picture(at x,y position, filename, size: x,y)
	*/
	public static void drawBackground(double radius, String background) {
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, background, radius*2, radius*2);
		StdDraw.show();
	}

	/*Input ex: java NBody 157788000.0 25000.0 data/planets.txt
	first double: total simulate time
	second double: dt
	third:	 txt file
	*/
	public static void main (String[] args) {
        
        //BufferedImage image = null; 
		double T =Double.parseDouble(args [0]);
		double dt = Double.parseDouble(args [1]);
		String filename = args [2]; 
		Planet[] Planets = readPlanets(filename);
		double radius = readRadius(filename);

		drawBackground(radius, background);

		for (int i = 0; i < Planets.length; i++) {
                Planets[i].draw();
         }

		StdOut.printf("%d\n", Planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < Planets.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
                  Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);   
		}
		 /*This is a graphics technique to prevent flickering in the animation. */
		StdDraw.enableDoubleBuffering();

		/*for every dt makes update on all planets*/
		for (double time = 0; time <= T; time += dt) {

			double [] Xforces = new double [Planets.length];
			for (int i = 0; i < Planets.length; i++ ) {
				Xforces[i] = Planets[i].calcNetForceExertedByX ( Planets );
			}
		
			double [] Yforces = new double [Planets.length];
			for (int i = 0; i < Planets.length; i++ ) {
				Yforces[i] = Planets[i].calcNetForceExertedByY ( Planets );
			}

			for (int i = 0; i < Planets.length; i++ ) {
				Planets[i].update(dt, Xforces[i], Yforces[i]);
			}

			StdDraw.picture(0, 0, background, 5e+11, 5e+11);

			for (int i = 0; i < Planets.length; i++) {
                Planets[i].draw();
            }

			StdDraw.show();
			StdDraw.pause(10);
		}
    }
}

