package GUI.Core;

import javax.swing.*;

public class MainWindow extends JFrame {

    public void init(){

        this.setSize( GUI.Settings.windowWidth, GUI.Settings.windowHeight );

        //this.setExtendedState( JFrame.MAXIMIZED_BOTH );
        //this.setUndecorated( true );

        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        try{
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        }catch( Exception e ){
            System.out.println( "Unable to set window look and feel" );
        }
        this.setResizable( true );

        // Add the main panel
        MainPanel mainPanel = new MainPanel();
        this.add( mainPanel );

        // Show the final window
        this.setVisible( true );

    }

}
