public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double G = 6.67e-11;


	// constructor, new object/instance, （）里面是接收传入参数
	public Planet(double xP, double yP, double xV,
				  				double yV, double m, String img)
	{
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}



	/*这个构造函数是一个复制构造函数，它接受一个 Planet 类型的参数 p，
	然后创建一个新的 Planet 对象，这个新对象的属性与 p 对象的属性完全相同。

	*在代码中，Planet 是类名，表示一个类，而 p 是这个类的一个实例。当你创建一个新的 Planet 对象时，你可以这样写：
	Planet myPlanet = new Planet(1.0, 2.0, 3.0, 4.0, 5.0, "myImage.gif");
	这里，myPlanet 是一个 Planet 类的实例，它有自己的属性值。

	*当你使用复制构造函数创建新对象时，你可以这样写：
	Planet anotherPlanet = new Planet(myPlanet);
	 */
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public  double calcDistance(Planet p) {
		return Math.sqrt(
				(xxPos - p.xxPos) * (xxPos - p.xxPos) +
						(yyPos - p.yyPos) * (yyPos - p.yyPos));
	}

	public double calcForceExertedBy(Planet p){
		double r = calcDistance(p);
		return G * mass * p.mass / (r * r);
	}


	public  double calcForceExertedByX(Planet p){
		double dx = p.xxPos - xxPos;
		double r = calcDistance(p);
		return  calcForceExertedBy(p) * dx/r;
	}

	public  double calcForceExertedByY(Planet p){
		double dy = p.yyPos - yyPos;
		double r = calcDistance(p);
		return  calcForceExertedBy(p) * dy/r;
	}

	public  double calcNetForceExertedByX(Planet[] allPlanets)
	//(Planet[] allPlanets)：方法参数，allPlanets 是一个 Planet 类型的数组，包含了所有行星的引用。
	{
		double totalForce = 0;
		for (Planet planet: allPlanets){
			//for 循环，遍历 allPlanets 数组中的每一个 Planet 对象。planet 是循环中的临时变量，代表当前迭代的行星。
			if (this.equals(planet)){
				//if 语句，检查当前迭代的行星 planet 是否与 this（当前对象）相同。this 关键字在 Java 中代表当前对象的引用。
				continue;
				//如果当前行星与 this 相同，continue 关键字会使循环跳过当前迭代，继续下一次迭代。这是为了避免计算一个行星对自己的作用力。
			}
			totalForce += calcForceExertedByX(planet);
			/*如果当前行星不是 this，调用 calcForceExertedByX 方法计算当前行星在 x 方向上对 this 的力，
			 并将其加到 totalForce 上
			 */
		}
		return totalForce;

	}

	public  double calcNetForceExertedByY(Planet[] allPlanets)
	//(Planet[] allPlanets)：方法参数，allPlanets 是一个 Planet 类型的数组，包含了所有行星的引用。
	{
		double totalForce = 0;
		for (Planet planet: allPlanets){
			//for 循环，遍历 allPlanets 数组中的每一个 Planet 对象。planet 是循环中的临时变量，代表当前迭代的行星。
			if (this.equals(planet)){
				//if 语句，检查当前迭代的行星 planet 是否与 this（当前对象）相同。this 关键字在 Java 中代表当前对象的引用。
				continue;
				//如果当前行星与 this 相同，continue 关键字会使循环跳过当前迭代，继续下一次迭代。这是为了避免计算一个行星对自己的作用力。
			}
			totalForce += calcForceExertedByY(planet);
			//如果当前行星不是 this，调用 calcForceExertedByX 方法计算当前行星在 x 方向上对 this 的力，并将其加到 totalForce 上。
		}
		return totalForce;

	}

	public  void update(double dt, double fX, double fY){
		//定义一个方法，void：返回类型，表示这个方法不返回任何值。
		// (double dt, double fX, double fY)：方法参数，dt 是时间步长（delta time）
		// fX 和 fY 分别是行星在 x 方向和 y 方向上受到的力
		double ax = fX / mass;
		double ay = fY / mass;
		xxVel += dt * ax;
		yyVel += dt * ay;
		xxPos += xxVel * dt;
		yyPos += yyVel * dt;
	}
	public void draw(){  //定义一个方法
		StdDraw.picture(xxPos,yyPos, "images/"+ imgFileName);
	}
	/*
	调用 StdDraw 类的 picture 方法来绘制行星。
	这个方法接受三个参数：行星的 x 和 y 坐标（xxPos 和 yyPos），以及行星图像的文件名（imgFileName）。
	图像文件名是通过在 imgFileName 前面加上 "images/" 路径来构造的。
	StdDraw 是一个简化的图形库，通常用于教学目的，它提供了一些基本的绘图功能。
	 */

}



