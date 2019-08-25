package GUI.Core;

import GUI.RootEntity;

public class GUIPrimary {

    public static void main(String[] args) {
        // Create the main window
        MainWindow mainWindow = new MainWindow();
        mainWindow.init();

        // Initialize the root entity
        RootEntity rootEntity = new RootEntity( mainWindow.getWidth(), mainWindow.getHeight() );

        // Set up desktop elements
        rootEntity.initializeDesktop();

        // Render the first proper frame
        Renderer.enabled = true;
        Renderer.drawFrame();
    }

}
