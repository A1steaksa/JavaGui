package GUI.Elements;

import GUI.Core.GUIUtil;
import GUI.Core.MainPanel;
import GUI.Core.Renderer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class NewWindow extends Entity {

    // Whether the window is currently being dragged
    private  boolean isDragging = false;

    // How far from the window origin the drag was started at
    private int draggingXOffset = 0;
    private int draggingYOffset = 0;

    // The height of the top window title bar
    private int titleBarHeight = 18;

    // The padding around the sides and top of the title bar
    private int titleBarPadding = 3;

    // How far from the edge of the window should we allow resizing in
    private int resizeBorderSize = 10;

    // How far into the window's visual area the resize bounds should extend
    private int resizeBorderInset = -3;

    // How far from the edges the most recent mouseDown event was
    private int northOffset = 0;
    private int eastOffset = 0;
    private int southOffset = 0;
    private int westOffset = 0;

    // The position and size of the window when we started resizing it
    private Rectangle resizeStartBounds = new Rectangle();

    // The size, around the visual bounds, taken up by the border itself
    int internalBorderSize = 3;

    // What direction, if any, we're currently resizing in
    public ResizeDirection resizeMode = ResizeDirection.None;

    public enum ResizeDirection {
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
    public Rectangle getChildBounds(){
        return getVisualBounds();
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {

        Rectangle bounds = getVisualBounds();
        bounds.x += x;
        bounds.y += y;


        //Background
        GUIUtil.getInstance().drawRaisedRect( g, bounds.x, bounds.y, bounds.width, bounds.height );

        // Title bar / Dragging handle
        g.setColor( new Color( 0, 0, 100 ) );
        g.fillRect( bounds.x + titleBarPadding, bounds.y + titleBarPadding, bounds.width - titleBarPadding * 2, titleBarHeight );

    }

    public void updateOffsets( int x, int y ){
        int localX = localizeX( x );
        int localY = localizeY( y );

        Rectangle bounds = getVisualBounds();

        // Where we clicked on the resizing edges
        northOffset = bounds.y - localY;
        eastOffset =  localX - ( bounds.x + bounds.width );
        southOffset = localY - ( bounds.y + bounds.height );
        westOffset = -( localX - bounds.x );
    }

    /**
     * Determines which, if any, resize direction is indicated by a given X, Y coordinate
     * @param x
     * @param y
     * @return
     */
    public ResizeDirection getResizeDirection( int x, int y ){
        updateOffsets( x, y );

        // Find out if any offsets indicate that the point is over a resizing border
        if( northOffset > resizeBorderInset && eastOffset > resizeBorderInset ){
            return ResizeDirection.NorthEast;
        }else if( southOffset > resizeBorderInset && eastOffset > resizeBorderInset ){
            return ResizeDirection.SouthEast;
        }else if( southOffset > resizeBorderInset && westOffset > resizeBorderInset ){
            return ResizeDirection.SouthWest;
        }else if( northOffset > resizeBorderInset && westOffset > resizeBorderInset ){
            return ResizeDirection.NorthWest;
        }else if( northOffset > resizeBorderInset ){
            return ResizeDirection.North;
        }else if( eastOffset > resizeBorderInset ){
            return ResizeDirection.East;
        }else if( southOffset > resizeBorderInset ){
            return ResizeDirection.South;
        }else if( westOffset > resizeBorderInset ){
            return ResizeDirection.West;
        }

        return ResizeDirection.None;
    }

    @Override
    public void onMouseDown( MouseEvent e ) {

        // Setting our resizing mode
        resizeMode = getResizeDirection( e.getX(), e.getY() );

        // Save the window's current position and bounds
        if( resizeMode != ResizeDirection.None ){
            resizeStartBounds = getGlobalBounds();
        }

        // Update and adjust offsets to take resize border into account for use later
        northOffset -= resizeBorderSize;
        eastOffset -= resizeBorderSize;
        southOffset -= resizeBorderSize;
        westOffset -= resizeBorderSize;

        /*
            Dragging
         */

        int localX = localizeX( e.getX() );
        int localY = localizeY( e.getY() );

        Rectangle bounds = getVisualBounds();

        Rectangle titleBar = new Rectangle(
                getGlobalX() + bounds.x + titleBarPadding,
                getGlobalY() + bounds.y + titleBarPadding,
                bounds.width - titleBarPadding * 2,
                titleBarHeight
        );

        // Check if we clicked on the title bar
        if( Util.isPointWithinRectangle(  e.getX(), e.getY(), titleBar ) ){
            isDragging = true;
            draggingXOffset = localX;
            draggingYOffset = localY;
        }

    }

    @Override
    public void onMouseUp(MouseEvent e) {
        resizeMode = ResizeDirection.None;
        isDragging = false;

    }

    @Override
    public void onMouseClick(MouseEvent e) {
        resizeMode = ResizeDirection.None;
        isDragging = false;
    }

    @Override
    public void onMouseDrag(MouseEvent e) {

        if( resizeMode != ResizeDirection.None ){

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
                    setWidth( westWidth );
                    setX( e.getX() + westOffset );

                    setHeight( northHeight );
                    setY( e.getY() + northOffset );
                    break;
                case West:
                    setWidth( westWidth );
                    setX( e.getX() + westOffset );
                    break;
                case SouthWest:
                    setWidth( westWidth );
                    setX( e.getX() + westOffset );

                    setHeight( southHeight );
                    break;
                case South:
                    setHeight( southHeight );
                    break;
                case SouthEast:
                    setWidth( eastWidth );

                    setHeight( southHeight );
                    break;
                case East:
                    setWidth( eastWidth );
                    break;
                case NorthEast:
                    setWidth( eastWidth );

                    setHeight( northHeight );
                    setY( e.getY() + northOffset );
                    break;
                case None:
                    break;
            }
        }

        if( isDragging ){
            setX( e.getX() - draggingXOffset );
            setY( e.getY() - draggingYOffset );
        }

        if( resizeMode != ResizeDirection.None || isDragging ){
            Renderer.drawFrame();
        }

    }

    @Override
    public void onChildInteraction(InputEvent e) {

    }

    @Override
    public void onMouseEnter( MouseEvent e ) {
        MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
    }

    @Override
    public void onMouseExit( MouseEvent e ) {
        MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
    }

    @Override
    public void onMouseHoverMove( MouseEvent e ) {

        ResizeDirection resizeDirection = getResizeDirection( e.getX(), e.getY() );

        switch ( resizeDirection ){
            case North:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.N_RESIZE_CURSOR ) );
                break;
            case NorthEast:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.NE_RESIZE_CURSOR ) );
                break;
            case East:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.E_RESIZE_CURSOR ) );
                break;
            case SouthEast:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.SE_RESIZE_CURSOR ) );
                break;
            case South:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.S_RESIZE_CURSOR ) );
                break;
            case SouthWest:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.SW_RESIZE_CURSOR ) );
                break;
            case West:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.W_RESIZE_CURSOR ) );
                break;
            case NorthWest:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.NW_RESIZE_CURSOR ) );
                break;
            case None:
                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
                break;
        }

    }
}
