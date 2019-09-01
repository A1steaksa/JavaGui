package GUI.Elements;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Image extends Entity {

    BufferedImage image;

    public Image(int startX, int startY, int startWidth, int startHeight, BufferedImage image ){
        super( startX, startY, startWidth, startHeight );
        this.image = image;
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {
        g.drawImage( image, x, y , getWidth(), getHeight(), null );
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
