package GUI.Elements;

import GUI.Core.GUIUtil;
import GUI.Core.Renderer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button extends Entity {

    public static Color BACKGROUND_COLOR = Color.lightGray;
    public static Color BORDER_COLOR = Color.black;
    public static Color BUTTON_TEXT_COLOR = Color.black;
    public static String buttonFontName = "Microsoft Sans Serif";

    private int textPadding = 3;

    // How far the button contents is offset when the button is pressed
    private int pressedContentOffset = 2;

    public Text buttonText;
    public Image buttonImage;

    private boolean heldDown = false;

    public Button( int x, int y, int width, int height, String startText ){
        super( x, y, width, height );

        // Button text
        int fontSize = 14;
        Font buttonFont = new Font( buttonFontName, Font.PLAIN, fontSize );

        // Create the text
        buttonText = new Text( 0, 0, buttonFont, BUTTON_TEXT_COLOR, startText );
        buttonText.setParent( this );

        // Because the text only knows it s own size after we've created it, we can now center it
        buttonText.setX( getWidth() / 2 - buttonText.getWidth() / 2 );

        buttonText.setY( textPadding );
    }
    
    public Button( int x, int y, int width, int height, BufferedImage buttonImage ){
        super( x, y, width, height );
        
        this.buttonImage = new Image( 0, 0, getWidth(), getHeight(), buttonImage );
        this.buttonImage.setParent( this );
        this.buttonImage.passthroughInput = true;
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {
        if( heldDown ){
            GUIUtil.getInstance().drawLoweredRect( g, x, y, getWidth(), getHeight() );
        }else{
            GUIUtil.getInstance().drawRaisedRect( g, x, y, getWidth(), getHeight() );
        }
    }

    @Override
    public void onMouseDown(MouseEvent e) {
        heldDown = true;

        if( buttonText != null ){
            buttonText.setPos(
                    buttonText.getX() + pressedContentOffset,
                    buttonText.getY() + pressedContentOffset
            );
        }
    
        if( buttonImage != null ){
            buttonImage.setPos(
                    buttonImage.getX() + pressedContentOffset,
                    buttonImage.getY() + pressedContentOffset
            );
        }
        
        

        Renderer.redrawEntityHierarchy( this, getGlobalX(), getGlobalY() );
    }

    @Override
    public void onMouseUp(MouseEvent e) {
        heldDown = false;
    
        if( buttonText != null ){
            buttonText.setPos(
                    buttonText.getX() - pressedContentOffset,
                    buttonText.getY() - pressedContentOffset
            );
        }
    
        if( buttonImage != null ){
            buttonImage.setPos(
                    buttonImage.getX() - pressedContentOffset,
                    buttonImage.getY() - pressedContentOffset
            );
        }

        Renderer.redrawEntityHierarchy( this, getGlobalX(), getGlobalY() );
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        heldDown = false;
    
        if( buttonText != null ){
            buttonText.setPos(
                    buttonText.getX() - pressedContentOffset,
                    buttonText.getY() - pressedContentOffset
            );
        }
    
        if( buttonImage != null ){
            buttonImage.setPos(
                    buttonImage.getX() - pressedContentOffset,
                    buttonImage.getY() - pressedContentOffset
            );
        }

        Renderer.redrawEntityHierarchy( this, getGlobalX(), getGlobalY() );
        clickAction();
    }

    @Override
    public void onMouseDrag(MouseEvent e) {

    }

    @Override
    public void onChildInteraction(InputEvent e) {

    }

    @Override
    public void onMouseEnter( MouseEvent e ) {

    }

    @Override
    public void onMouseExit( MouseEvent e ) {

    }

    @Override
    public void onMouseHoverMove( MouseEvent e ) {

    }

    /**
     * What action this button performs when clicked.
     * This should be overridden with your custom behavior.
     */
    public void clickAction(){ }
}
