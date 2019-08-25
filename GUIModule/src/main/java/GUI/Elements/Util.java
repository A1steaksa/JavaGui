package GUI.Elements;

import GUI.RootEntity;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Util {

    public static Entity getEntityAtPoint( int pointX, int pointY ){

        boolean keepSearching = true;

        // Start the search from the RootEntity
        Entity currentRoot = RootEntity.rootEntity;

        while( keepSearching ){

            // We only want to continue if we find a child containing the point
            keepSearching = false;

            // Check if any child of currentRoot contains the point
            for (int i = currentRoot.children.size() - 1; i >= 0; i-- ) {
                Entity child = currentRoot.children.get( i );

                // Adjust the child's bounds to be its global position
                Rectangle childBounds = child.getGlobalBounds();

                // If this child contains the point, there's our new currentRoot
                if( isPointWithinRectangle( pointX, pointY, childBounds ) ){

                    currentRoot = child;
                    keepSearching = true;

                    // Stop looking through children
                    break;

                }

            }

        }

        // Return whichever entity we found
        return currentRoot;

    }

    public static boolean isPointWithinRectangle( int x, int y, Rectangle rectangle ){
        boolean inRectangleXAxis =
                ( x >= rectangle.x ) &&
                        ( x <= rectangle.x + rectangle.width );
        boolean inRectangleYAxis =
                ( y >= rectangle.y ) &&
                        ( y <= rectangle.y + rectangle.height );

        return inRectangleXAxis && inRectangleYAxis;
    }

    public static BufferedImage readImage( String path ){
        try{
            return ImageIO.read( new File( path ) );
        }catch( IOException e ){
            System.out.println( "Unable to read image: " + path );
        }
        return null;
    }

}
