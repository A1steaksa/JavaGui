package GUI.Elements;

import GUI.Core.GUIUtil;
import GUI.Core.Renderer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class Button extends Entity {

    public static Color BACKGROUND_COLOR = Color.lightGray;
    public static Color BORDER_COLOR = Color.black;
    public static Color BUTTON_TEXT_COLOR = Color.black;
    public static String buttonFontName = "Microsoft Sans Serif";

    private int textPadding = 4;

    public Text buttonText;
    public Image buttonImage;

    private boolean heldDown = false;

    public Button( int startX, int startY, int startWidth, int startHeight, String startText ){
        super( startX, startY, startWidth, startHeight );

        // Button text
        int fontSize = Math.max( getHeight() - textPadding * 2, 5 );
        Font buttonFont = new Font( buttonFontName, Font.PLAIN, fontSize );

        // Create the text
        buttonText = new Text( 0, 0, buttonFont, BUTTON_TEXT_COLOR, startText );
        buttonText.setParent( this );

        // Because the text only knows it s own size after we've created it, we can now center it
        buttonText.setX( getWidth() / 2 - buttonText.getWidth() / 2 );

        buttonText.setY( textPadding / 2 );
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
        Renderer.drawEntityHierarchy( this, getGlobalX(), getGlobalY() );
    }

    @Override
    public void onMouseUp(MouseEvent e) {
        heldDown = false;
        Renderer.drawEntityHierarchy( this, getGlobalX(), getGlobalY() );
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        heldDown = false;
        Renderer.drawEntityHierarchy( this, getGlobalX(), getGlobalY() );
        clickAction();
    }

    @Override
    public void onMouseDrag(MouseEvent e) {

    }

    @Override
    public void onChildInteraction(InputEvent e) {

    }

    /**
     * What action this button performs when clicked.
     * This should be overridden with your custom behavior.
     */
    public void clickAction(){ }
}
