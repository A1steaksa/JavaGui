package GUI.Elements;

import GUI.Core.MainPanel;
import GUI.Core.Renderer;
import GUI.RootEntity;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Window extends Entity {


    /*
        Static variables
     */

    public static int windowHandleHeight = 25;
    private static int titleFontSize = 20;
    private static String fontName = "Courier New";

    private static Color BACKGROUND_COLOR = Color.gray;
    private static Color HANDLE_COLOR = Color.darkGray;
    private static Color BORDER_COLOR = Color.black;
    private static Color HANDLE_TEXT_COLOR = Color.white;

    public enum ResizeMode {
        Horizontal,
        Vertical,
        Both
    }

    private boolean beingDragged = false;
    private boolean beingResized = false;
    private int dragXOffset = 0;
    private int dragYOffset = 0;
    private ResizeMode resizeMode;
    private int resizeEdgeSize = 15;

    private int shadowOffsetX = 3;
    private int shadowOffsetY = 3;

    private Text titleText;
    private Button closeButton;

    private String title;

    public Window( int startX, int startY, int startWidth, int startHeight, String startTitle ){
        super();

        setPos( startX, startY );
        setWidth( startWidth );
        setHeight( startHeight );

        Rectangle visualBoundsOffset = getVisualBoundsOffset();

        title = startTitle;

        // Title text

        int titleTextLeftPadding = 5;
        int titleTextTopPadding = 2;

        Font titleFont = new Font( fontName, Font.PLAIN, titleFontSize );
        titleText = new Text( visualBoundsOffset.y + 5, titleTextTopPadding + visualBoundsOffset.y, titleFont, HANDLE_TEXT_COLOR, this.title );
        titleText.setParent( this );

        // Close button
        int closeButtonSize = 20;
        int closeButtonRightPadding = 2;
        int closeButtonTopPadding = 2;

        closeButton = new Button(
                visualBoundsOffset.x + ( getParent().getWidth() + visualBoundsOffset.width ) - getWidth() - closeButtonRightPadding,
                visualBoundsOffset.y + closeButtonRightPadding,
                closeButtonSize,
                closeButtonSize,
                ""
        ){
            @Override
            public void onMouseClick( MouseEvent e ){

                System.out.println( "Clicked" );

                closeWindow();
                Renderer.drawFrame();
            }

            public void layoutEntity(){
                if( getParent() instanceof Window ){
                    Rectangle visualBoundsOffset = ( (Window) getParent() ).getVisualBoundsOffset();

                    // Right align
                    setX( visualBoundsOffset.x + ( getParent().getWidth() + visualBoundsOffset.width ) - getWidth() - closeButtonRightPadding );
                    setY( visualBoundsOffset.y + closeButtonRightPadding );
                }
            }

        };
        closeButton.setParent( this );

        // Close button image
        BufferedImage closeBufferedImage = Util.readImage( "C:/Users/a1ste/Pictures/GUI/Close.png" );
        Image closeImage = new Image( 0, 0, closeButtonSize, closeButtonSize, closeBufferedImage );
        closeImage.passthroughInput = true;
        closeImage.setParent( closeButton );

    }

    @Override
    public void setX( int x ){
        super.setX( x - resizeEdgeSize );
    }

    @Override
    public void setY( int y ){
        super.setY( y - resizeEdgeSize );
    }

    @Override
    public void setWidth( int width ){
        super.setWidth( width + resizeEdgeSize * 2 );
        layoutHierarchy();
    }

    @Override
    public void setHeight( int height ){
        super.setHeight( height + resizeEdgeSize * 2 );
        layoutHierarchy();
    }

    /**
     * The position and size modification from the default that the window takes visually.
     * eg. Window X + Visual Bounds Offset X = X position you see it at
     * @return
     */
    public Rectangle getVisualBoundsOffset(){
        return new Rectangle(
            resizeEdgeSize,
            resizeEdgeSize,
            -resizeEdgeSize * 2,
            -resizeEdgeSize * 2
        );
    }

    public String getTitle(){
        return title;
    }

    public void setTitle( String title ){
        this.title = title;
    }

    public void closeWindow(){
        RootEntity.rootEntity.children.remove( this );
    }

    public void moveToFront(){
        getParent().children.remove( this );
        getParent().children.add( this );
        Renderer.drawFrame();
    }

    @Override
    public Rectangle getChildBounds() {

        return new Rectangle(
                resizeEdgeSize + 1,
                resizeEdgeSize + windowHandleHeight,
          getWidth() - 2 - resizeEdgeSize * 2,
          getHeight() - windowHandleHeight - 1 - resizeEdgeSize * 2
        );
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {

        // Disable the clip for the window
        Shape clip = g.getClip();
        g.setClip( RootEntity.rootEntity.getBounds() );

        // The visual size of the window, which does not include the resizable area
        Rectangle visualBounds = getVisualBoundsOffset();
        visualBounds.x += getX();
        visualBounds.y += getY();
        visualBounds.width += getWidth();
        visualBounds.height += getHeight();


        // Draw dropshadow
        g.setColor( Color.black );
        g.fillRect( visualBounds.x + shadowOffsetX, visualBounds.y + shadowOffsetY, visualBounds.width, visualBounds.height );

        // Draw background
        g.setColor( BACKGROUND_COLOR );
        g.fillRect( visualBounds.x, visualBounds.y, visualBounds.width, visualBounds.height );

        // Draw handle
        g.setColor( HANDLE_COLOR );
        g.fillRect( visualBounds.x, visualBounds.y, visualBounds.width, windowHandleHeight );

        // Draw outline
        g.setColor( HANDLE_COLOR );
        g.drawRect( visualBounds.x, visualBounds.y, visualBounds.width, visualBounds.height );

        //Draw the title and the close button outside of the clip
        Renderer.clipEnabled = false;
        titleText.drawHierarchy( g, titleText.getGlobalX(), titleText.getGlobalY() );
        closeButton.drawHierarchy( g, closeButton.getGlobalX(), closeButton.getGlobalY() );
        Renderer.clipEnabled = true;

        Rectangle globalBounds = this.getGlobalBounds();
        g.setColor( Color.blue );
        g.drawRect( globalBounds.x, globalBounds.y, globalBounds.width, globalBounds.height );

        Rectangle childBounds = this.getGlobalChildBounds();
        g.setColor( Color.red );
        g.drawRect( childBounds.x, childBounds.y, childBounds.width, childBounds.height );

        // Renable clip for everything else
        g.setClip( clip );

    }

    @Override
    public void onMouseDown(MouseEvent e) {
        moveToFront();

        // Get the local x and y of the event
        int localX = this.localizeX( e.getX() );
        int localY = this.localizeY( e.getY() );

        Rectangle visualBoundsOffset = getVisualBoundsOffset();

        // Check if the mouse was over the handle to start a drag
        if(
                localY >= visualBoundsOffset.y &&
                localY <=  visualBoundsOffset.y + windowHandleHeight &&
                localX >= visualBoundsOffset.x &&
                localX <= visualBoundsOffset.x + ( getWidth() + visualBoundsOffset.width )
        ){

            beingDragged = true;

            // Save the event's relative location
            dragXOffset = localX;
            dragYOffset = localY;

        }

        // Check if the mouse was over the bottom right corner
        boolean onRightEdge = localX >= getWidth() - resizeEdgeSize;
        boolean onBottomEdge = localY >= getHeight() - resizeEdgeSize;

        if( onRightEdge || onBottomEdge ){
            beingResized = true;

            if( onRightEdge && onBottomEdge ){
                resizeMode = ResizeMode.Both;

                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.SE_RESIZE_CURSOR ) );

            }else if( onRightEdge ){
                resizeMode = ResizeMode.Horizontal;

                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.E_RESIZE_CURSOR ) );

            }else{
                resizeMode = ResizeMode.Vertical;

                MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.S_RESIZE_CURSOR ) );

            }

            // Save the event's relative location
            dragXOffset = localX;
            dragYOffset = localY;
        }

    }

    @Override
    public void onMouseUp(MouseEvent e) {
        beingDragged = false;
        beingResized = false;
        MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor(  Cursor.DEFAULT_CURSOR ) );
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        beingDragged = false;
        beingResized = false;
        MainPanel.mainPanel.setCursor( Cursor.getPredefinedCursor(  Cursor.DEFAULT_CURSOR ) );
    }

    @Override
    public void onMouseDrag(MouseEvent e) {

        Rectangle visualBoundsOffset = getVisualBoundsOffset();

        // Move this window to wherever it's being dragged
        if( beingDragged ){
            setX( e.getX() - dragXOffset + visualBoundsOffset.x );
            setY( e.getY() - dragYOffset + visualBoundsOffset.y );
            Renderer.drawFrame();
        }

        // Set this window's width and height based on where its being dragged
        if( beingResized ){

            if( resizeMode == ResizeMode.Horizontal || resizeMode == ResizeMode.Both ){
                int newWidth = Math.max( e.getX() - getGlobalX() - visualBoundsOffset.x, titleText.getX() + titleText.getWidth() + closeButton.getWidth() ) ;
                setWidth( newWidth );
            }

            if( resizeMode == ResizeMode.Vertical || resizeMode == ResizeMode.Both ){
                int newHeight = Math.max( e.getY() - getGlobalY() - visualBoundsOffset.y, windowHandleHeight * 3 );
                setHeight( newHeight );
            }

            Renderer.drawFrame();

        }

    }

    @Override
    public void onChildInteraction(InputEvent e) {

        // If our child had some input on them, we should move ourselves to the front of the screen
        moveToFront();

    }

    @Override
    public String toString(){
        return "Window " + title;
    }
}
