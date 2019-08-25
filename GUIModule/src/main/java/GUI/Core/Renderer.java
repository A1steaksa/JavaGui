package GUI.Core;

import GUI.Elements.Entity;
import GUI.RootEntity;

import java.awt.*;

public class Renderer {

    // Whether or not to draw frames
    public static boolean enabled = false;

    // Whether or not to clip children when drawing them
    public static boolean clipEnabled = true;

    public static void drawAllEntities( Graphics2D g ){
        //Show every entity, starting with the root entity
        RootEntity.rootEntity.drawHierarchy( g, RootEntity.rootEntity.getGlobalX(), RootEntity.rootEntity.getGlobalY() );

    }

    public static Graphics2D getGraphics(){
        return (Graphics2D) MainPanel.mainPanel.getGraphics();
    }

    public static void drawFrame(){
        MainPanel.mainPanel.repaint();
    }

    public static void drawEntity(Entity entity, int x, int y ){
        Graphics2D g = getGraphics();
        entity.drawEntity( g, x, y );
    }

    public static void drawEntityHierarchy( Entity entity, int x, int y ){
        Graphics2D g = getGraphics();
        entity.drawHierarchy( g, x, y );
    }

}
