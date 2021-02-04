
import java.io.*;
import java.util.ArrayList;
import java.lang.Math;
import java.util.LinkedList;
import java.util.Queue;

class circle {

    int id;
    double centerX;
    double centerY;
    double radius;

    ArrayList<circle> adjList = new ArrayList<>();

}

public class bfs {


    static int[] level;// this array for the levels of graph
    static boolean[] isVisited;// this array for vertexes in order to not counting again




    public static void bfs(circle firstCircle, int numOfCircles){

        Queue<circle> queue = new LinkedList<circle>(){{add(firstCircle);}}; // creating queue and adding parameter firstCircle as a first element
        level[0] = 0; // giving zero for the first element depth level
                                    // because level difference begin after this point
        isVisited[0] = true;// in order to not visit again the first circle

        while (!queue.isEmpty()){ // while all elements are visiting
            circle c = queue.peek();// we take first level element
            queue.poll();// and take away from the queue the first element

            for(int i=0; i<c.adjList.size(); i++){
                if(isVisited[c.adjList.get(i).id] == false){

                    level[c.adjList.get(i).id] = level[c.id] + 1;// the depth level of visited node assign to depth level of parent plus 1
                    queue.add(c.adjList.get(i));// the ith adjacent element of visited node added to the queue
                    isVisited[c.adjList.get(i).id] = true; // in order to  not visit again isVisited value becomes "true" means it is visited
                }
            }
        }

    }



    public static boolean distance(circle circle1, circle circle2) {
            /*
                this function is a implementation of "distance of two points" formula.
                this formula is: "Square root of [(x1-x2)^2 + (y1-y2)^2]"
                    if the addition of radiouses of circles equal or bigger than the
                        distance between centers of the circles then I call these circles are adjacent
             */
        boolean flag;

        double distance1 = circle1.radius + circle2.radius;// addition of radiuses
        double distance2;// distance between two points

        double squareX = Math.pow((circle1.centerX - circle2.centerX), 2.0);
        double squareY = Math.pow((circle1.centerY - circle2.centerY), 2.0);

        distance2 = Math.abs( squareX + squareY);

        distance2 =  Math.sqrt(distance2);

        if(distance2 <= distance1) flag = true;// if the distance between centers <= addition of radiuses
                                                    // the flag return true to the calculateOverlap method
        else flag = false;// otherwise return false to the calculateOverlap method

        return flag;
    }

    public static circle[] calculateOverlap(circle[] circles) {
            // this function adds each circles to circle's adjacency list if distance() function returns true
                    // if true each circles adding to the their adjacency list
        boolean flag;

        for (int i = 0; i < circles.length; i++) {
            for (int j = i + 1; j < circles.length; j++) {

                flag = distance(circles[i], circles[j]);

                if(flag) {
                    circles[i].adjList.add(circles[j]);
                    circles[j].adjList.add(circles[i]);
                }
            }
        }


        return circles;
    }

    public static void main(String[] args) {

        try {

            File file = new File("C:\\Users\\osman\\AlgoHw1\\test.txt"); // creates a new file instance
            FileReader fr = new FileReader(file); // reads the file
            BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream


            File output = new File("C:\\Users\\osman\\AlgoHw1\\output1.txt");// creates a new file instance
            PrintStream out = new PrintStream(output);
            System.setOut(out);// setting system default output from console to the file

            String line;
            String splited[] = null;

            int numberOfCircle=0;

            int j = 0;

            circle[] circles = null;

            while ((line = br.readLine()) != null) {// starting read the input file

                if (line.contains("#") || line.isBlank()) {// if input line includes "#" mean "comments" this line skipped

                    continue;
                } else {// if line has not any "#" then line will be splitted in to splitted string array
                            // splitting factor is space and each element splitted by space is places one of the array cell
                    splited = line.split("\\s+");
                    if (splited.length == 1) {// if the splitted line I mean the input line has just one entry so this gives the number of circles
                        numberOfCircle = Integer.parseInt(splited[0]);

                        circles = new circle[numberOfCircle];// creating circle type circle array
                        level = new int[numberOfCircle];// initializing pre created variables
                        isVisited = new boolean[numberOfCircle];

                        for (int i = 0; i < numberOfCircle; i++) {// creating circle objects for each circle

                            circles[i] = new circle();
                            circles[i].id = i ;

                        }
                    } else if (line.length() > 3) {//according to project document the first column for the x coordinates
                                                                                    // second column for the y coordinates
                                                                                    // third column for the radius length


                        circles[j].centerX = Double.parseDouble(splited[0]);
                        circles[j].centerY = Double.parseDouble(splited[1]);
                        circles[j].radius = Double.parseDouble(splited[2]);
                        j++;
                    }
                }

            }

            circles = calculateOverlap(circles); // after setting up all circles
                                    // I calculate the overlaps and create adjacent lists of each circle

            bfs(circles[0], numberOfCircle);// applying breadth first search algorithm
                                // and give as a parameter first circle for calculate hop distance



            for(int i=0; i<circles.length;i++){
                System.out.println(circles[i].id + "              " + level[i]);
            }

            fr.close(); // closes the stream and release the resources

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
