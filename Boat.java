import greenfoot.*;    // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * A boat moves with the x and y velocity which are read from the input file. As the tractorbeams are added, the velocities 
 * change due to the fact that tractorbeams pull boats with calculated force.
 *
 */
public class Boat extends Actor {
    
    double xVel; //velocity in the x direction
    double yVel; //velocity in the y direction
    
    /**
     * Constructor to build a Boat. 
     * 
     * @param  xVel the velocity in x direction
     * @param  yVel the velocity in y direction
     */

    public Boat(double xVel, double yVel) {
        this.xVel = xVel; //remember x velocity
        this.yVel = yVel; //remember y velocity
    }

    /**
     * Act - calculate the distance between the boat and the source, and add/remove tractorbeam 
     * based on it, adding the pulling force
     */
    public void act() {
        
        World w = getWorld(); //allow accessing to the object in the world
        
        //get all objects in TractorBeam class
        List<TractorBeam> beams = getWorld().getObjects(TractorBeam.class);
        
        
       
        
        //after the beam is added...
        for(int k = 0; k < beams.size(); k++){
             List<Source> sources = w.getObjects(Source.class);//get all objects in Source class
        for (int i = 0; i < sources.size(); i++) {
           
            TractorBeam beam = beams.get(k); //get a tractorbeam object specifying the index
            Source src = sources.get(i); //get a source object specifying the index
            double dis = getDistance(this, src); //calculate the distance between this boat and the beam
            //if the distance is less than 150...
            if (dis >= 150 && beam.getTarget().equals(this)) {
                w.removeObject(beam); //remove the tractorbeam from the world
            }
           }//for i
        }//for k
        
        List<Source> sources = w.getObjects(Source.class);//get all objects in Source class
        beams = w.getObjects(TractorBeam.class); //get objects in TractorBeam class

        //check for all sources...
        for (int i = 0; i < sources.size(); i++) {
            Source src = sources.get(i);//get a source object specifying the index
            int dis = (int) getDistance(this, src); //calculate the distance between this boat and the source
            //if the distance is less than 150..

            if (dis <= 150) {
                boolean b = true; //boolean to check if the beam already exists
                for (int j = 0; j < beams.size(); j++) {
                    TractorBeam tb = beams.get(j); //get a tractorbeam object specifying the index
                    //if the tractorbeam already exists...
                    if (tb.getSource().equals(src) && tb.getTarget().equals(this)) {
                        b = false;
                    }
                }
               if(b)
                    {
                        TractorBeam tractorBeam = new TractorBeam(src, this);//create a new TractorBeam class
                        w.addObject(tractorBeam, 0, 0);//add the tractorbeam object
                    }

            }//if (dis <= 150)
            
            
        }//for loop
        
        double pullInX = 0;//pullig force by the beam in the x direction
        double pullInY = 0;//rpulling force by the beam in the y direction
        beams = w.getObjects(TractorBeam.class);//get objects in TractorBeam class
       
        //calculate the pulling force!
        for (int i = 0; i < beams.size(); i++) {
            TractorBeam tb = beams.get(i); //get a tractorbeam object specifying the index
            //if the tractorbeam is there...
            if (tb.getTarget().equals(this)) {
                int xB = getX(); //current x location of this boat
                int yB = getY(); //current y location of this boat
                int xTB = tb.getX(); //current x location of the tractorbeam
                int yTB = tb.getY(); //current y location of the tractorbeam
                int resX = xTB - xB; //x distance
                int resY = yTB - yB; //y distance
                double dis = Math.sqrt((resX * resX) + (resY * resY)); //distance
                //if the distance is not 0...
                if (dis!=0){
                double pullX = resX/dis;//x distance divided by the distance
                double pullY = resY/dis; //y distance divided by the distance
                pullInX += (pullX / 4.0);//final pulling force in the x direction
                pullInY += (pullY / 4.0);//final pulling force in the y direction
            }
            }
        }
        xVel += pullInX; //the velocity in x direction after adding the pulling force
        yVel += pullInY; //the velocity in y direction after adding the pulling force
        int finalX = getX() + (int)xVel;//the final X position (velocity)
        int finalY = getY() + (int)yVel;//the final Y position (velocity)
        setLocation(finalX, finalY); //set the new location (velocity)
        
        //remove this boat when it reaches the edge of the world 
        if (getX() == 799 || getX() <= 0 ||getY() == 599 || getY() <= 0){
        w.removeObject(this);
        }
        
    }//public void act
    
    /**
     * simply calculate the distance between two actors
     * 
     * @param Actor a, Actor b to specify the two actors
     * @return the distance between them
     * 
     */

    public double getDistance(Actor a, Actor b) {
        int xB = a.getX(); //get the x position of the actor a
        int yB = a.getY(); //get the y position of the actor a
        int xTB = b.getX(); //get the x position of the actor b
        int yTB = b.getY(); //get the y position of the actor b
        int resX = xTB - xB; //calculate the x distance
        int resY = yTB - yB; //calculate the y distance
        return Math.sqrt((resX * resX) + (resY * resY));//return the final distance

    }
}//public class boat

