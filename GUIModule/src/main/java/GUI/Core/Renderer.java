package GUI.Core;

import GUI.Elements.Entity;
import GUI.RootEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer {

    // Whether or not to draw frames
    public static boolean enabled = false;

    // Whether or not to clip children when drawing them
    public static boolean clipEnabled = true;

    public static void drawAllEntities( Graphics2D g ){
        //Show every entity, starting with the root entity
        RootEntity.rootEntity.drawHierarchy( g, RootEntity.rootEntity.getGlobalX(), RootEntity.rootEntity.getGlobalY() );
    }

    public static Graphics2D getGraphics(){
        return (Graphics2D) MainPanel.mainPanel.getGraphics();
    }

    public static void drawFrame(){
        MainPanel.mainPanel.repaint();
    }

    public static void drawEntity(Entity entity, int x, int y ){
        Graphics2D g = getGraphics();
        entity.drawEntity( g, x, y );
    }

    public static void drawEntityHierarchy( Entity entity, int x, int y ){
        Graphics2D g = getGraphics();
        entity.drawHierarchy( g, x, y );
    }


    /**
     * Draws a given image 9-sliced with a given edge size
     * @param g
     * @param img The image to draw
     * @param edgeSize How wide/tall the corners are
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void drawSlicedImage(Graphics2D g, BufferedImage img, int edgeSize, int x, int y, int width, int height ){

        // Corner sub-images
        BufferedImage northWestCorner = img.getSubimage( 0, 0, edgeSize, edgeSize );
        BufferedImage northEastCorner = img.getSubimage( img.getWidth() - edgeSize, 0, edgeSize, edgeSize );
        BufferedImage southWestCorner = img.getSubimage( 0, img.getHeight() - edgeSize, edgeSize, edgeSize );
        BufferedImage southEastCorner = img.getSubimage( img.getWidth() - edgeSize, img.getHeight() - edgeSize, edgeSize, edgeSize );

        // Edge sub-images
        BufferedImage northEdge = img.getSubimage( edgeSize, 0, img.getWidth() - edgeSize * 2, edgeSize );
        BufferedImage eastEdge = img.getSubimage( img.getWidth() - edgeSize, edgeSize, edgeSize, img.getHeight() - edgeSize * 2 );
        BufferedImage southEdge = img.getSubimage( edgeSize, img.getHeight() - edgeSize, img.getWidth() - edgeSize * 2, edgeSize );
        BufferedImage westEdge = img.getSubimage( 0, edgeSize, edgeSize, img.getHeight() - edgeSize * 2 );

        // Center sub-image
        BufferedImage center = img.getSubimage( edgeSize, edgeSize, img.getWidth() - edgeSize * 2, img.getHeight() - edgeSize * 2 );

        // Draw corners
        g.drawImage( northWestCorner, x, y, edgeSize, edgeSize, null );
        g.drawImage( northEastCorner, x + width - edgeSize, y, edgeSize, edgeSize, null );
        g.drawImage( southWestCorner, x, y + height - edgeSize, edgeSize, edgeSize, null );
        g.drawImage( southEastCorner, x + width - edgeSize, y + height - edgeSize, edgeSize, edgeSize, null );

        // Draw edges
        g.drawImage( northEdge, x + edgeSize, y, width - edgeSize * 2, edgeSize, null );
        g.drawImage( eastEdge, x + width - edgeSize, y + edgeSize, edgeSize, height - edgeSize * 2, null );
        g.drawImage( southEdge, x + edgeSize, y + height - edgeSize, width - edgeSize * 2, edgeSize, null );
        g.drawImage( westEdge, x, y + edgeSize, edgeSize, height - edgeSize * 2, null );

        // Draw center
        g.drawImage( center, x + edgeSize, y + edgeSize, width - edgeSize * 2, height - edgeSize * 2, null );

    }

}
