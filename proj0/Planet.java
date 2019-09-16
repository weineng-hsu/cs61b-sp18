public class Planet{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private final static double G = 6.67*1e-11;


	/**Create a Planet with
	*@param xP  X position of the planet
	*@param yP  Y position of the planet
	*@param xV  X volecity of the planet
	*@param yV  Y volecity of the planet
	*@param m   mass of the planet
	*@param img name of th eplanet
	*/
	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass  = m;
		imgFileName = img;
	}
	/**Copy a Planet from the Planet(double xP, double yP, double xV, double yV, double m, String img)*/
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass  = p.mass;
		imgFileName = p.imgFileName;
	}

	/**Calculate the distance between two planets
	* dis = (dx^2+dy^2)^0.5
	*/
	public double calcDistance(Planet p) {
		double xdis = this.xxPos-p.xxPos;
		double ydis = this.yyPos-p.yyPos;
		double dis = xdis*xdis + ydis*ydis;
		return Math.sqrt(dis);
	}

	/**Calculate the force excert between two planets
	* F= m * M * G / dis^2
	* m , M: the mass of two planets
	* G : static number
	* distance
	*/
	public double calcForceExertedBy (Planet p) {
		return this.mass*p.mass*G/ ( this.calcDistance(p) * this.calcDistance(p) );
	}

	/**Calculate the x direction force excert between two planets
	* Fx = F * dx / d (dx/d: cosine)
	*/
	public double calcForceExertedByX (Planet p) {
		return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p) ;
	}

	/**Calculate the y direction force excert between two planets
	* Fy = F * dy / d (dy/d: sine)
	*/
	public double calcForceExertedByY (Planet p) {
		return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p) ;
	}

	/**Calculate a group of planet excert X force on each other(have to exclude itself)*/
	public double calcNetForceExertedByX (Planet[] p) {
		double xFSum = 0;
		for (Planet o: p ) {
			if (o != this) 	{
				xFSum=xFSum+this.calcForceExertedByX(o);
				} 
		} return xFSum;
	}

	/**Calculate a group of planet excert Y force on each other(have to exclude itself)*/
	public double calcNetForceExertedByY (Planet[] p) {
		double yFSum = 0;
		for (Planet o: p ) {
			if (o != this) 	{
				yFSum=yFSum+this.calcForceExertedByY(o);
				}
		} return yFSum;
	}

	/**For a given time update planet conditions (xVel, yVel, xPos, yPos)
	*@param time time period
	*@param Fx Fx excert on planet
	*@param Fy Fy excert on planet*/

	public void update (double time, double Fx, double Fy) {
		double Ax = Fx / this.mass;
		double Ay = Fy / this.mass;
		this.xxVel = this.xxVel + time * Ax;
		this.yyVel = this.yyVel + time * Ay;
		this.xxPos = this.xxPos + time * this.xxVel;
		this.yyPos = this.yyPos + time * this.yyVel;
	}

	/** Draw pictiure from images file (using stdDraw database) 
	*@method stdDraw.pic (draw pic at Xposition, Yposition, file route )*/
	
	public void draw () {
		String filepic = this.imgFileName;
		String planetpic = "images/"+filepic;
		StdDraw.picture(this.xxPos, this.yyPos, planetpic);
	}

}