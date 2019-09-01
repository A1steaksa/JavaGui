package GUI;

import GUI.Elements.*;
import GUI.Elements.Button;
import GUI.Elements.Image;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RootEntity extends Entity {

    public static RootEntity rootEntity;

    public void initializeDesktop(){

        NewWindow window = new NewWindow( 100, 100, 500, 500 );
        Button applyButton = new Button( 100, 100, 75, 23, "Cancel" );
        applyButton.setParent( window );

    }

    public RootEntity( int screenWidth, int screenHeight ){

        rootEntity = this;

        setX( 0 );
        setY( 0 );
        setWidth( screenWidth );
        setHeight( screenHeight );

    }

    @Override
    public Rectangle getChildBounds() {
        return this.getBounds();
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {

        // Draw the background
        g.setColor( Settings.BACKGROUND_COLOR );
        g.fillRect( getX(), getY(), getWidth(), getHeight() );

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

    @Override
    public void onMouseEnter( MouseEvent e ) {

    }

    @Override
    public void onMouseExit( MouseEvent e ) {

    }

    @Override
    public void onMouseHoverMove( MouseEvent e ) {

    }
}
