package jp.jaxa.iss.kibo.rpc.sampleapk;

        import geometry_msgs.Vector3;
        import gov.nasa.arc.astrobee.Kinematics;
        import gov.nasa.arc.astrobee.types.Vec3d;
        import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

        import java.util.ArrayList;
        import java.util.List;
        import gov.nasa.arc.astrobee.Result;
        import gov.nasa.arc.astrobee.android.gs.MessageType;
        import gov.nasa.arc.astrobee.types.Point;
        import gov.nasa.arc.astrobee.types.Quaternion;

        import org.opencv.core.Mat;
        import org.ros.internal.message.RawMessage;

        import java.lang.Math;

/**
 * Class meant to handle commands from the Ground Data System and execute them in Astrobee
 */

public class YourService extends KiboRpcService {

    double treshold = 0.2;
    int breaks = 3;

    final double[][] KIZMIN =    {{10.3, -10.2, 4.32},
            {9.5, -10.5, 4.02}};

    final double[][] KIZMAX =    {{11.55, -6, 5.57},
            {10.5, -9.6, 4.8}};

    final double[][] KOZMIN =    {{10.783, -9.8899, 4.8385},
            {10.8652, -9.0734, 4.3861},
            {10.185, -8.3826, 4.1475},
            {10.7955, -8.0635, 5.1055},
            {10.563, -7.1449, 4.6544}};

    final double[][] KOZMAX =    {{11.071, -9.6929, 5.0665},
            {10.9628, -8.7314, 4.6401},
            {11.665, -8.2826, 4.6725},
            {11.3525, -7.7305, 5.1305},
            {10.709, -6.8099, 4.8164}};

    final double[][] POINTS = {{11.2746, -9.92284, 5.2988},
            {10.612, -9.0709, 4.48},
            {10.71, -7.7, 4.48},
            {10.51, -6.7185, 5.1804},
            {11.114, -7.9756, 5.3393},
            {11.355, -8.9929, 4.7818},
            {11.369, -8.5518, 4.48}};

    final float[][] ORIENTATION = {{0f, 0f, -0.707f, 0.707f},
            {0.5f, 0.5f, -0.5f, 0.5f},
            {0f, 0.707f, 0f, 0.707f},
            {0f, 0f, -1f, 0f},
            {-0.5f, -0.5f, -0.5f, 0.5f},
            {0f, 0f, 0f, 1f},
            {0f, 0.707f, 0f, 0.707f}};

    @Override
    protected void runPlan1(){
        // the mission starts
        api.startMission();
        int loop_counter = 0;

        while (true){
            // get the list of active target id
            List<Integer> list = api.getActiveTargets();

            Point point = new Point();
            Quaternion quaternion = new Quaternion();

            int target_id ;
            Mat image;

            point = new Point(POINTS[0][0], POINTS[0][1], POINTS[0][2]);
            quatern ion = new Quaternion(ORIENTATION[0][0], ORIENTATION[0][1], ORIENTATION[0][2], ORIENTATION[0][3]);

            point = new Point(10.9387, -8.49293, 5.72005);
            api.moveTo(point, quaternion, true);

            /*
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i)){
                    case 1:
                        point = new Point(POINTS[0][0], POINTS[0][1], POINTS[0][2]);
                        quaternion = new Quaternion(ORIENTATION[0][0], ORIENTATION[0][1], ORIENTATION[0][2], ORIENTATION[0][3]);
                        break;
                    case 2:
                        point = new Point(POINTS[1][0], POINTS[1][1], POINTS[1][2]);
                        quaternion = new Quaternion(ORIENTATION[1][0], ORIENTATION[1][1], ORIENTATION[1][2], ORIENTATION[1][3]);
                        break;
                    case 3:
                        point = new Point(POINTS[2][0], POINTS[2][1], POINTS[2][2]);
                        quaternion = new Quaternion(ORIENTATION[2][0], ORIENTATION[2][1], ORIENTATION[2][2], ORIENTATION[2][3]);
                        break;
                    case 4:
                        point = new Point(POINTS[3][0], POINTS[3][1], POINTS[3][2]);
                        quaternion = new Quaternion(ORIENTATION[3][0], ORIENTATION[3][1], ORIENTATION[3][2], ORIENTATION[3][3]);
                        break;
                    case 5:
                        point = new Point(POINTS[4][0], POINTS[4][1], POINTS[4][2]);
                        quaternion = new Quaternion(ORIENTATION[4][0], ORIENTATION[4][1], ORIENTATION[4][2], ORIENTATION[4][3]);
                        break;
                    case 6:
                        point = new Point(POINTS[5][0], POINTS[5][1], POINTS[5][2]);
                        quaternion = new Quaternion(ORIENTATION[5][0], ORIENTATION[5][1], ORIENTATION[5][2], ORIENTATION[5][3]);
                        break;
                    case 7:
                        point = new Point(POINTS[6][0], POINTS[6][1], POINTS[6][2]);
                        quaternion = new Quaternion(ORIENTATION[6][0], ORIENTATION[6][1], ORIENTATION[6][2], ORIENTATION[6][3]);
                        break;
                }

                moveToTarget(point, quaternion, breaks);

                if(api.getMatNavCam() != null)
                {
                    image = api.getMatNavCam();
                }

                api.laserControl(true);

                target_id = list.get(i);

                api.takeTargetSnapshot(target_id);

                //CHECK TIME TO LOOP AGAIN
                //GETACTIVETARGETS AGAIN
            }*/

            /*
            //api.moveTo(point, quaternion, false);
            moveToTarget(point, quaternion, 3);

            // get a camera image
            image = api.getMatNavCam();

            // irradiate the laser
            api.laserControl(true);

            // take active target snapshots
            target_id = 1;
            api.takeTargetSnapshot(target_id);

            point = new Point(10.71d, -7.7d, 4.48d);
            quaternion = new Quaternion(0f, 0.707f, 0f, 0.707f);
            //api.moveTo(point, quaternion, false);
            moveToTarget(point, quaternion, 3);

            image = api.getMatNavCam();

            // irradiate the laser
            api.laserControl(true);

            // take active target snapshots
            target_id = 3;
            api.takeTargetSnapshot(target_id);

            /* ************************************************ */
            /* write your own code and repair the ammonia leak! */
            /* ************************************************ */


            // get remaining active time and mission time
            List<Long> timeRemaining = api.getTimeRemaining();

            // check the remaining milliseconds of mission time
            if (timeRemaining.get(1) < 60000){
                break;
            }

            loop_counter++;
            if (loop_counter == 2){
                break;
            }
        }
        // turn on the front flash light
        api.flashlightControlFront(0.05f);

        // get QR code content
        String mQrContent = yourMethod();

        // turn off the front flash light
        api.flashlightControlFront(0.00f);

        // notify that astrobee is heading to the goal
        api.notifyGoingToGoal();

        /* ********************************************************** */
        /* write your own code to move Astrobee to the goal positiion */
        /* ********************************************************** */

        // send mission completion
        api.reportMissionCompletion(mQrContent);
    }

    private boolean moveToTarget(Point targetPos, Quaternion quaternion, int breaks)
    {
        Kinematics k = api.getRobotKinematics();
        Point pos = k.getPosition();
        List<Point> newPoints = BreakLine(pos, targetPos, breaks);
        for (int i = 0; i < newPoints.size(); i++) {
            // p = CorrectPointPosition(newPoints.get(i));
            //api.moveTo(p, quaternion, true);
            api.moveTo(newPoints.get(i), quaternion, true);
        }
        return true;
    }

    private List<Point> BreakLine(Point a, Point b, int n)
    {
        n += 1;

        if(n < 0)
        {
            n = 1;
        }
        List<Point> newPoints = new ArrayList<>();
        Point t = Subtract(a, b);
        for (int i = 0; i < n; i++) {
            newPoints.add(Add(a, Multiply(t, (1/(double)n) * (i + 1))));
        }
        return newPoints;
    }

    private Point CorrectPointPosition(Point p)
    {
        Point koz = CheckKOZ(p);

        Point kiz1 = CheckKIZ1(koz);
        Point kiz2 = CheckKIZ2(koz);

        double change1 = 0, change2 = 0;

        Point t = Subtract(koz, kiz1);
        change1 = Math.abs(t.getX()) + Math.abs(t.getY()) + Math.abs(t.getZ());

        t = Subtract(koz, kiz2);
        change2 = Math.abs(t.getX()) + Math.abs(t.getY()) + Math.abs(t.getZ());

        if(change1 > change2)
        {
            return kiz2;
        }else {
            return kiz1;
        }

    }

    private Point CheckKIZ1(Point p)
    {
        double x = p.getX(), y = p.getY(), z = p.getZ();

        if(p.getX() < KIZMIN[0][0] + treshold)
        {
            x = p.getX() + (KIZMIN[0][0] + treshold - p.getX());
        }else if(p.getX() > KIZMAX[0][0] - treshold)
        {
            x = p.getX() + (KIZMAX[0][0] - treshold - p.getX());
        }

        if(p.getY() < KIZMIN[0][1] + treshold)
        {
            y = p.getY() + (KIZMIN[0][1] + treshold - p.getY());
        }else if(p.getY() > KIZMAX[0][1] - treshold)
        {
            y = p.getY() + (KIZMAX[0][1] - treshold - p.getY());
        }

        if(p.getZ() < KIZMIN[0][2] + treshold)
        {
            z = p.getZ() + (KIZMIN[0][2] + treshold - p.getZ());
        }else if(p.getZ() > KIZMAX[0][2] - treshold)
        {
            z = p.getZ() + (KIZMAX[0][2] - treshold - p.getZ());
        }

        return new Point(x, y, z);
    }

    private Point CheckKIZ2(Point p)
    {
        double x = p.getX(), y = p.getY(), z = p.getZ();

        if(p.getX() < KIZMIN[1][0] + treshold)
        {
            x = p.getX() + (KIZMIN[1][0] + treshold - p.getX());
        }else if(p.getX() > KIZMAX[1][0] - treshold)
        {
            x = p.getX() + (KIZMAX[1][0] - treshold - p.getX());
        }

        if(p.getY() < KIZMIN[1][1] + treshold)
        {
            y = p.getY() + (KIZMIN[1][1] + treshold - p.getY());
        }else if(p.getY() > KIZMAX[1][1] - treshold)
        {
            y = p.getY() + (KIZMAX[1][1] - treshold - p.getY());
        }

        if(p.getZ() < KIZMIN[1][2] + treshold)
        {
            z = p.getZ() + (KIZMIN[1][2] + treshold - p.getZ());
        }else if(p.getZ() > KIZMAX[1][2] - treshold)
        {
            z = p.getZ() + (KIZMAX[1][2] - treshold - p.getZ());
        }

        return new Point(x, y, z);
    }

    private Point CheckKOZ(Point p)
    {
        double x = p.getX(), y = p.getY(), z = p.getZ();

        for (int i = 0; i < KOZMIN.length; i++) {

            if(p.getX() > KOZMIN[i][0] - treshold)
            {
                x = p.getX() + (KOZMIN[i][0] - treshold - p.getX());
            }else if(p.getX() < KOZMAX[i][0] + treshold)
            {
                x = p.getX() + (KOZMAX[i][0] + treshold - p.getX());
            }

            if(p.getY() > KOZMIN[i][1] - treshold)
            {
                y = p.getY() + (KOZMIN[i][1] - treshold - p.getY());
            }else if(p.getY() < KOZMAX[i][1] + treshold)
            {
                y = p.getY() + (KOZMAX[i][1] + treshold - p.getY());
            }

            if(p.getZ() > KOZMIN[i][2] - treshold)
            {
                z = p.getZ() + (KOZMIN[i][2] - treshold - p.getZ());
            }else if(p.getZ() < KOZMAX[i][2] + treshold)
            {
                z = p.getZ() + (KOZMAX[i][2] + treshold - p.getZ());
            }
        }

        return new Point(x, y, z);
    }

    private Point Multiply(Point a, double t)
    {
        return new Point(a.getX() * t, a.getY() * t, a.getZ() * t);
    }

    private Point Add(Point a, Point b)
    {
        return new Point(a.getX() + b.getX(), a.getY() + b.getY(), a.getY() + b.getZ());
    }

    private Point Subtract(Point a, Point b)
    {
        return new Point(a.getX() - (b.getX()), a.getY() - (b.getY()), a.getY() - (b.getZ()));
    }

    @Override
    protected void runPlan2(){
        // write your plan 2 here
    }

    @Override
    protected void runPlan3(){
        // write your plan 3 here
    }

    // You can add your method
    private String yourMethod(){
        return "your method";
    }
}