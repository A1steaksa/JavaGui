package GUI.Core;

import GUI.RootEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainPanel extends JPanel {

    public static MainPanel mainPanel;

    public MainPanel(){

        mainPanel = this;

        this.setDoubleBuffered( true );

        // Start tracking input for this panel
        Input input = new Input( this );

        // In the event that the main panel is resized, alert the root entity
        this.addComponentListener( new ComponentAdapter(){
            public void componentResized(ComponentEvent e ){

                if( RootEntity.rootEntity == null ){
                    return;
                }

                RootEntity.rootEntity.setHeight( e.getComponent().getHeight() );
                RootEntity.rootEntity.setWidth( e.getComponent().getWidth() );

                Renderer.drawFrame();

            }
        });
    }

    @Override
    public void paintComponent( Graphics g ){

        if( !Renderer.enabled ){
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        Renderer.drawAllEntities( g2d );

    }

}
