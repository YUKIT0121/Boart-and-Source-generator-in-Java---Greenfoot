import greenfoot.*;
import java.util.*;
import java.io.*;
import java.awt.FileDialog;

/**
 * World containing Source generators and Boats
 *
 * @author Yuki Tsukamoto
 * @version 11/21/2019
 */ 
public class MyWorld extends World {

    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorld() {
        // Create a new world with 800x600 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
    }
    /**
     * Act-When "L" is pressed, call the user prompt to choose an input file.
     * By reading an input file, put the boats and sources at the specified locations.
     * When "M" is pressd, "merge" occurs and the read input data is stored.
     */
    
    
    public void act()
    { 
     if(Greenfoot.isKeyDown("l")||Greenfoot.isKeyDown("L")||
     Greenfoot.isKeyDown("m")||Greenfoot.isKeyDown("M")) //check if "L" or "M" is pressed 
     {
            if(Greenfoot.isKeyDown("l")||Greenfoot.isKeyDown("L"))
            //when "L" (case insensitive) is pressed, remove all objects in the world
        {
            //remove all objects in TractorBeam class
            removeObjects(getObjects(TractorBeam.class));
            //remove all objects in Boat class
            removeObjects(getObjects(Boat.class));
            //remove all objects in Source class
            removeObjects(getObjects(Source.class));
        }
            
        // create dialog to choose the input file.
        FileDialog fd = null;
        //call a user prompt to choose an input fila
        fd = new FileDialog(fd, "Choose a Data File", FileDialog.LOAD); 
        //make the file visible
        fd.setVisible(true);
            
        // gets input file information.
        String filename = fd.getFile(); //the "what"
        String pathname = fd.getDirectory(); //the "where"
        String fullname = pathname + filename; //place + name
        
        // input file gets selected .
        File myFile = new File(fullname);
            
        //create scanner
        Scanner myReader = null;
        try 
        {   
            myReader = new Scanner(myFile);
        } 
        // if such input could not open or does not exist, 
        // deals with exception by printing the catch condition and quits the method.
        catch (FileNotFoundException e) 
        {
            System.out.println("File is not found: " + fullname);
            return;
        } 
         
        // reads the input file and executes the following...
        while(myReader.hasNext()) 
        {
            // every color that is ridden from the input file
            // creates a bubble with that color.
            String boatOrTractor = myReader.next();
            //if the string is "boat"...
            if(boatOrTractor.equalsIgnoreCase("boat"))
            {
              double xposOfBoat = myReader.nextDouble(); //x position of the boat
              double yposOfBoat = myReader.nextDouble(); //y position of the boat
              double xvel = myReader.nextDouble(); //velocity in the x direction of the boat
              double yvel = myReader.nextDouble(); //velocity in the y direction of the boat
              //add boat at the specified location, storing x velocity and y velocity
              addObject(new Boat(xvel, yvel), (int)xposOfBoat, (int)yposOfBoat);
            }
            //if the string is "tractor"...
            if(boatOrTractor.equalsIgnoreCase("tractor"))
            {
              int xposOfTractor = myReader.nextInt(); //x position of the source
              int yposOfTractor = myReader.nextInt(); //y position of the source
              //add source a the specified location
              addObject(new Source(), xposOfTractor, yposOfTractor);
            }
            }//while
       }//if
    }//public void act
}

