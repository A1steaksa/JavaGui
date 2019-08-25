package GUI.Elements;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class Button extends Entity {

    public static Color BACKGROUND_COLOR = Color.lightGray;
    public static Color BORDER_COLOR = Color.black;
    public static Color BUTTON_TEXT_COLOR = Color.black;
    public static String buttonFontName = "Courier New";

    private int textPadding = 2;

    String buttonText;

    public Button( int startX, int startY, int startWidth, int startHeight, String startText ){
        super();

        setPos( startX, startY );
        setWidth( startWidth );
        setHeight( startHeight );

        this.buttonText = startText;

        // Button text
        int fontSize = Math.max( getHeight() - textPadding * 2, 5 );
        Font buttonFont = new Font( buttonFontName, Font.PLAIN, fontSize );

        // Create the text
        Text buttonText = new Text( 0, 0, buttonFont, BUTTON_TEXT_COLOR, this.buttonText );
        buttonText.setParent( this );

        // Because the text only knows it s own size after we've created it, we can now center it
        buttonText.setX( getWidth() / 2 - buttonText.getWidth() / 2 );
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {

        // Background
        g.setColor( BACKGROUND_COLOR );
        g.fillRect( x, y, getWidth(), getHeight() );

        // Border
        g.setColor( BORDER_COLOR );
        g.drawRect( x, y, getWidth(), getHeight() );

    }

    @Override
    public void onMouseDown(MouseEvent e) {

    }

    @Override
    public void onMouseUp(MouseEvent e) {

    }

    @Override
    public void onMouseClick(MouseEvent e) {

    }

    @Override
    public void onMouseDrag(MouseEvent e) {

    }

    @Override
    public void onChildInteraction(InputEvent e) {

    }
}
