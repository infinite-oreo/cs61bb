public  class NBody {
    public static double readRadius(String filename){
        //double：返回类型，表示这个方法返回一个 double 类型的值。
        //readRadius：方法名，表示这个方法的目的是读取宇宙的半径。
        //(String filename)：方法参数，filename 是一个字符串，表示要读取的文件名。
        In in = new In(filename);
        //创建 In 类的一个实例 in，并传入文件名 filename。这个实例用于从文件中读取数据。
        int num = in.readInt();
        //调用 in 实例的 readInt() 方法，从文件中读取一个整数 num，这通常表示行星的数量。
        double radius = in.readDouble();
        //调用 in 实例的 readDouble() 方法，从文件中读取一个双精度浮点数 radius，这表示宇宙的半径
        return radius;
    }
    public static Planet[] readPlanets(String filename){
        /*
        Planet[]：返回类型，表示这个方法返回一个 Planet 类型的数组;可以类比double，int
        readPlanets：方法名，表示这个方法的目的是读取行星数据。
         */
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[num];
        //创建一个 Planet 类型的数组 planets，其长度为 num，用于存储读取的行星对象。


        for (int i =0 ; i < num ; i++){
            double xp = in.readDouble();
            double yp = in.readDouble();
            double vx = in.readDouble();
            double vy = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xp,yp,vx,vy,m,img);
            //使用读取到的坐标、速度、质量和图像文件名创建一个新的 Planet 对象。
            // 这个新对象被赋值给 planets 数组的当前索引位置 i。
        }
        return planets;
        //方法结束，返回包含所有行星对象的 Planet[] 数组。
    }//这个循环的目的是逐个读取文件中每个行星的数据，并创建相应的 Planet 对象。这些对象随后被存储在 planets 数组中

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        /*
        创建一个 Double 类型的变量 T，并将命令行参数 args[0]（通常是程序的第一个参数）转换为 Double 类型并赋值给 T。
        这里假设 args[0] 是一个数字字符串。

        double 是一个基本数据类型，它直接存储数值。
        基本数据类型在 Java 中是值传递的，这意味着当你将一个 double 类型的变量传递给方法时，传递的是值的副本。

        Double 是一个引用类型，它存储的是一个对象。引用类型在 Java 中是引用传递的，
        这意味着当你将一个 Double 类型的变量传递给方法时，传递的是对象的引用。
         */
        double dt = Double.parseDouble(args[1]);
        /*
        *这行代码创建了一个 double 类型的变量 dt，并将命令行参数 args[1]（通常是程序的第二个参数）转换为 double 类型并赋值给 dt。
        * 这里使用了 Double.parseDouble(String) 方法，这是一个标准的方法，用于将字符串解析为 double 类型的数值。
         */
        String filename = args[2];
        double r = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        //set the universe scale
        StdDraw.setXscale(-r,r);
        //设置绘图的 x 轴范围，从 -r 到 r。这通常用于确保宇宙的中心位于屏幕的中心
        StdDraw.setYscale(-r,r);
        StdDraw.enableDoubleBuffering();
        //启用双缓冲，这有助于减少绘图时的闪烁，使动画更加平滑

        double t = 0;
        //初始化时间变量 t 为 0，用于记录模拟的时间。
        int num = planets.length;
        //获取行星数组 planets 的长度，并将其赋值给 num 变量。
        while (t <= T){
            //开始一个 while 循环，条件是 t 小于或等于模拟的总时间 T。
            // 循环会一直执行，直到时间超过 T。
            double[] xForces = new double[num];
            //创建一个 double 类型的数组 xForces，用于存储每个行星在 x 方向上的力。
            double[] yForces = new double[num];
            for(int i =0 ; i < num ; i++){
                xForces[i] = 0.0;
                yForces[i] = 0.0;
                for (int j = 0; j < num; j++) {
                    if (i != j) { // 确保不是同一个行星
                        xForces[i] += planets[i].calcForceExertedByX(planets[j]); // 计算i行星由j行星在X方向上施加的力
                        yForces[i] += planets[i].calcForceExertedByY(planets[j]); // 计算i行星由j行星在Y方向上施加的力
                    }
                }
            }

            for(int i = 0; i < num; i++){
                //再次遍历行星数组，更新每个行星的位置和速度。
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            // draw the background picture
            StdDraw.picture(0,0, "images/starfield.jpg");
            //在屏幕的 (0, 0) 位置绘制名为 "starfield.jpg" 的背景图片。

            //draw all the planets
            for(Planet planet:planets){
                planet.draw();
                //遍历行星数组，调用每个行星的 draw 方法，绘制行星。
            }

            StdDraw.show();
            //显示当前的绘图结果
            StdDraw.pause(10);
            //暂停 10 毫秒，控制动画的速度
            t += dt;
            //更新时间变量 t，为下一次循环做准备
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", r);
        for (int i =0 ; i < planets.length; i++){
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel,planets[i].mass, planets[i].imgFileName);
        }


    }


}