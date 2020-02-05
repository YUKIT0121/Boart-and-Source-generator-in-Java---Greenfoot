import greenfoot.*;    // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * A TractorBeam is the actual "line" between a source generator and a boat. 
 */
public class TractorBeam extends Actor 
{
    private Source source;  // the generator "source" of the Tractor Beam
    private Boat target;    // the Boat that this Tractor Beam is pulling on
    
    private boolean yellowExterior; // used to alternate the coloring of the beam
    private int redrawCounter;      // how often the beam should be redrawn 
    
    /**
     * constant representing the maximum distance any beam should reach
     */
    public static int MAX_REACH = 150;
    
    /**
     * Constructor to build a TractorBeam. All TractorBeams are between a source
     * generator and a target Boat. Note that a TractorBeam will reposition itself 
     * to appear between the source generator and the boat automatically. In other
     * words, no matter where you try to add a TractorBeam object in a World, the
     * object will be moved to match the source generator and the associated boat.
     * 
     * @param src the source generator
     * @param target the boat that is being pulled by the beam
     */
    public TractorBeam(Source src, Boat target) 
    {
        // remember the source and target 
        source = src;
        this.target=target;
        
        // draw the (initial) image
        redraw();
        

        yellowExterior=false; // remember which version of teh image we are using
        redrawCounter=5;      // how long has it been since we last redrew image?
    }
    
    /** 
     * this is effectively the second half of the constructor. Since the TractorBeam
     * needs to be relocated to the apprpriate position from wherever the user
     * specified, this code needs to set the TractorBeam's location. Such can only
     * be done after this TractorBeam has been added to the world, so the code 
     * must be placed in this method.
     */
    protected void addedToWorld(World w)
    {        
        adjustLocation();  // move the TractorBeam to the appropriate location
        adjustRotation();  // rotate the TractorBeam's image to match invovled objects
    }
    
    
    /**
     * accessor that returns the referece to the source generator creating this
     * TractorBeam
     * 
     * @return the source generator for this TractorBeam
     */
    public Source getSource()
    {
        return source;
    }
    
    
    /**
     * accessor that returns the referece to the boat being pulled by this
     * TractorBeam
     * 
     * @return the bat being pulled by this TractorBeam
     */
    public Boat getTarget()
    {
        return target;
    }
    
    // move the beam's image so that it is between the source generator and the 
    // target boat
    private void adjustLocation()
    {
        // find the midpoint coordinates bewteen the source generator and the boat
        int midX = (source.getX()+target.getX())/2;
        int midY = (source.getY()+target.getY())/2;
        

        setLocation(midX, midY); // put this TractorBeam at the calculated midpoint
        adjustRotation();        // make sure Beam is in proper direction
    }
    
    // rotate this TractorBeam so that it connects the source generator and the boat
    private void adjustRotation()
    {
        // some math here ... find the direction and magnitude components
        // between the boat and the source generator.
        double deltaX = target.getX()-source.getX();
        double deltaY = target.getY()-source.getY();
        
        // calculate the angle between the components and the horizon
        double theta = -Math.atan(deltaX/deltaY);
        
        // convert it to degrees
        int degs = (int) Math.toDegrees(theta);
        
        // rotate the TractorBeam accordingly
        setRotation(degs); 
    }
    
    /**
     * redraw the TractorBeam's image. It is highly unlikely that yiou will
     * want to call this method from outside of the TractorBeam class, but it is
     * conceivable that you might want to do so (although if you do so, you are
     * making things MUCH harder than they should be).
     */
    public void redraw()
    {
        // how much x and y distance is there in the required image?
        double xdist = target.getX()-source.getX();
        double ydist = target.getY()-source.getY();
        
        // the image will be a square "line", which needs to cover the calculated
        // x and y distances. Using the Pythogorean theorm (also known as the 
        // "distance formula"), we can figure this out. 
        double dist = Math.sqrt(xdist*xdist + ydist*ydist);
        
        // turn the floating point distance into an appropriate integer
        int length = (int) Math.round(dist);
        
        // we cant build an image with a dimension of 0 (or less), so the
        // mimiumum is 1
        if (length<=0) 
            length=1;
        
        // build a new image, but only if necessary 
        GreenfootImage img = getImage();
        if (img==null || img.getHeight()!=length)
            img = new GreenfootImage(3,length);
        
        // set edge color to be opposite of what it is currently
        if (!yellowExterior)
            img.setColor(Color.YELLOW);
        else
            img.setColor(Color.ORANGE);
        img.fill();
        
        // set middle color to be oppodit of what it is now
        if(!yellowExterior)
            img.setColor(Color.YELLOW);
        else
            img.setColor(Color.ORANGE);
        img.fillRect(1,1,1,length);
        
        // rememebr to "flip" our color choices for next redraw. 
        yellowExterior = !yellowExterior;
        
        //use the image we just built. 
        setImage(img);
        
        // trun the amgae (the Actor actually) to orient the TractorBeam properly.
        adjustRotation();
    }
    
    /**
     * Act - do whatever the TractorBeam wants to do. Effectively, just keep
     * redrawing this TractorBeam.
     */
    public void act() 
    {
        // one more act call is one step closer to needing to redraw.
        redrawCounter--;
        
        // if we need to redraw ...
        if (redrawCounter<=0)
        {
            redraw();        // do the redraw ...
            redrawCounter=5; // delay the next redraw
        }
        
        // no matter what, do re-orient the image. 
        adjustLocation();
    }    
}
