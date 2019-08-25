package GUI.Elements;

import GUI.Core.Renderer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class Text extends Entity {

    private Color textColor = Color.black;
    private String text = "";
    private Font font;

    private FontMetrics metrics;

    public void setTextColor( Color textColor ){
        this.textColor = textColor;
    }

    public Color getTextColor(){
        return textColor;
    }

    public Text( int x, int y, Font font, Color textColor, String text ) {
        setFont( font );
        setPos( x, y );

        // Default behavior is to pass events to our parent
        this.passthroughInput = true;

        this.textColor = textColor;

        setText( text );
    }

    public void setFont( Font font ){
        this.font = font;
        metrics = Renderer.getGraphics().getFontMetrics( this.font );
    }

    public void setText( String text ){
        this.text = text;

        int stringWidth = metrics.stringWidth( this.text );
        setWidth( stringWidth );

        int stringHeight = metrics.getAscent();
        setHeight( stringHeight );
    }

    @Override
    public void layoutEntity() {

    }

    @Override
    public void drawEntity(Graphics2D g, int x, int y) {

        g.setColor( textColor );
        g.setFont( font );
        g.drawString( text, x, y + metrics.getAscent() );

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
