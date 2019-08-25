package GUI;

import GUI.Elements.Entity;
import GUI.Elements.Image;
import GUI.Elements.Util;
import GUI.Elements.Window;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RootEntity extends Entity {

    public static RootEntity rootEntity;

    public void initializeDesktop(){

        Window window = new Window( 100, 100, 500, 300, "A very cool window" );


        BufferedImage bufferedImage = Util.readImage( "C:/Users/a1ste/Pictures/GUI/Smile.png" );
        Image img = new Image( 100, 100, 100, 100, bufferedImage ){
            @Override
            public void layoutEntity() {

                Rectangle childBounds = getParent().getChildBounds();

                setX( childBounds.x + childBounds.width - getWidth() );
                setY( childBounds.y + childBounds.height - getHeight() );

            }
        };
        img.passthroughInput = true;
        img.setParent( window );

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
}
