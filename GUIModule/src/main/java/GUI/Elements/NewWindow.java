package GUI.Elements;

import GUI.Core.Renderer;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class NewWindow extends Entity {

    // How far from the edge of the window should we allow resizing in
    int resizeBorderSize = 15;

    // How far from the edges the most recent mouseDown event was
    private int northOffset = 0;
    private int eastOffset = 0;
    private int southOffset = 0;
    private int westOffset = 0;

    // The position and size of the window when we started resizing it
    private Rectangle resizeStartBounds;


    // What direction, if any, we're currently resizing in
    public ResizeMode resizeMode = ResizeMode.None;

    public enum ResizeMode {
        None,
        North,
        NorthEast,
        East,
        SouthEast,
        South,
        SouthWest,
        West,
        NorthWest
    }

    public NewWindow(int x, int y, int width, int height){
        super();

        setPos( x, y );
        setSize( width, height );

    }

    private Rectangle getVisualBounds(){
        return new Rectangle(
            resizeBorderSize,
            resizeBorderSize,
            getWidth() - resizeBorderSize * 2,
            getHeight() - resizeBorderSize * 2
        );
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {

        Rectangle bounds = getVisualBounds();
        bounds.x += x;
        bounds.y += y;

        // Full bounds
        g.setColor( new Color( 0.1f, 0.1f, 0.1f, 0.25f ) );
        g.fillRect( x, y, getWidth(), getHeight() );

        //Background
        g.setColor( Color.white );
        g.fillRect( bounds.x, bounds.y, bounds.width, bounds.height );

        // Border
        g.setColor( Color.black );
        g.drawRect( bounds.x, bounds.y, bounds.width, bounds.height );

    }

    @Override
    public void onMouseDown( MouseEvent e ) {

        int localX = localizeX( e.getX() );
        int localY = localizeY( e.getY() );

        Rectangle bounds = getVisualBounds();

        // Where we clicked on the resizing edges
        northOffset = bounds.y - localY;
        eastOffset =  localX - ( bounds.x + bounds.width );
        southOffset = localY - ( bounds.y + bounds.height );
        westOffset = -( localX - bounds.x );

        // Setting our resizing mode
        resizeMode = ResizeMode.None;
        if( northOffset > 0 && eastOffset > 0 ){
            resizeMode = ResizeMode.NorthEast;
        }else if( southOffset > 0 && eastOffset > 0 ){
            resizeMode = ResizeMode.SouthEast;
        }else if( southOffset > 0 && westOffset > 0 ){
            resizeMode = ResizeMode.SouthWest;
        }else if( northOffset > 0 && westOffset > 0 ){
            resizeMode = ResizeMode.NorthWest;
        }else if( northOffset > 0 ){
            resizeMode = ResizeMode.North;
        }else if( eastOffset > 0 ){
            resizeMode = ResizeMode.East;
        }else if( southOffset > 0 ){
            resizeMode = ResizeMode.South;
        }else if( westOffset > 0 ){
            resizeMode = ResizeMode.West;
        }

        // Adjust offsets for more convenient later user
        northOffset -= resizeBorderSize;
        eastOffset -= resizeBorderSize;
        southOffset -= resizeBorderSize;
        westOffset -= resizeBorderSize;

        // Save the window's current position and bounds
        if( resizeMode != ResizeMode.None ){
            resizeStartBounds = getGlobalBounds();
        }

    }

    @Override
    public void onMouseUp(MouseEvent e) {
        // Reset resize mode
        resizeMode = ResizeMode.None;

    }

    @Override
    public void onMouseClick(MouseEvent e) {
        // Reset resize mode
        resizeMode = ResizeMode.None;
    }

    @Override
    public void onMouseDrag(MouseEvent e) {

        // Defined here because Java cannot handle variable declarations in multiple cases
        int newWidth = 0;
        int newHeight = 0;

        // Calculate the sizes for the directions so we can user and combine them later
        int northHeight = ( ( resizeStartBounds.y + resizeStartBounds.height ) - e.getY() ) - northOffset;
        int eastWidth = ( e.getX() - getGlobalX() ) - eastOffset;
        int southHeight = ( e.getY() - getGlobalY() ) - southOffset;
        int westWidth = ( ( resizeStartBounds.x + resizeStartBounds.width ) - e.getX() ) - westOffset;

        // Resize the window based on which mode we're in using the sizes above
        switch ( resizeMode ) {
            case North:
                setHeight( northHeight );
                setY( e.getY() + northOffset );
                break;
            case NorthWest:
                setHeight( northHeight );
                setY( e.getY() + northOffset );

                setWidth( westWidth );
                setX( e.getX() + westOffset );
                break;
            case West:
                setWidth( westWidth );
                setX( e.getX() + westOffset );
                break;
            case SouthWest:
                setHeight( southHeight );

                setWidth( westWidth );
                setX( e.getX() + westOffset );
                break;
            case South:
                setHeight( southHeight );
                break;
            case SouthEast:
                setHeight( southHeight );

                setWidth( eastWidth );
                break;
            case East:
                setWidth( eastWidth );
                break;
            case NorthEast:
                setHeight( northHeight );
                setY( e.getY() + northOffset );

                setWidth( eastWidth );
                break;
            case None:
                break;
        }

        if( resizeMode != ResizeMode.None ){
            Renderer.drawFrame();
        }

    }

    @Override
    public void onChildInteraction(InputEvent e) {

    }
}
