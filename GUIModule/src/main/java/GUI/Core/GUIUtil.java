package GUI.Core;

import GUI.Elements.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUIUtil {

    private static GUIUtil guiUtil;
    public static GUIUtil getInstance(){
        if( guiUtil == null ){
            guiUtil = new GUIUtil();
        }
        return guiUtil;
    }

    private static String raisedBorderPath = "GUIModule/src/main/resources/raised_border.png";
    private static String loweredBorderPath = "GUIModule/src/main/resources/lowered_border.png";

    private BufferedImage raisedBorder;
    private BufferedImage loweredBorder;

    public GUIUtil(){
        raisedBorder = Util.readImage( raisedBorderPath );
        loweredBorder = Util.readImage( loweredBorderPath );
    }

    public void drawRaisedRect( Graphics2D g, int x, int y, int width, int height ){
        Renderer.drawSlicedImage( g, raisedBorder, 2, x, y, width, height );
    }

    public void drawLoweredRect( Graphics2D g, int x, int y, int width, int height ){
        Renderer.drawSlicedImage( g, loweredBorder, 2, x, y, width, height );
    }



}
