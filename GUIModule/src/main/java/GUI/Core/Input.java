package GUI.Core;

import GUI.Elements.Entity;
import GUI.Elements.Util;
import GUI.RootEntity;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input {

    // The entity that the mouse was pressed down on
    Entity pressedEntity;

    // The entity currently being hovered over
    Entity hoveredEntity = RootEntity.rootEntity;

    public Input( JPanel panel ){

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked( MouseEvent mouseEvent ) { }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

                // Find the entity we clicked on
                pressedEntity = Util.getEntityAtPoint( mouseEvent.getX(), mouseEvent.getY() );

                // Make sure we clicked an entity
                if( pressedEntity == null ){
                    return;
                }

                // Let the entity know about the action
                if( pressedEntity.passthroughInput ){
                    pressedEntity.getParent().onMouseDown( mouseEvent );
                }else{
                    pressedEntity.onMouseDown( mouseEvent );
                }


                // Let all of our parents know their child had some input
                alertParentsToAction( pressedEntity, mouseEvent );
            }

            @Override
            public void mouseReleased( MouseEvent mouseEvent ) {

                // If the release happened above the entity we initially pressed down on, that's a full click
                Entity entityReleasedOver = Util.getEntityAtPoint( mouseEvent.getX(), mouseEvent.getY() );

                if( entityReleasedOver == pressedEntity ){

                    // Let them know they were clicked
                    if( pressedEntity.passthroughInput ){
                        pressedEntity.getParent().onMouseClick( mouseEvent );
                    }else{
                        pressedEntity.onMouseClick( mouseEvent );
                    }

                }else{
                    // Otherwise, it was just a normal mouse up event

                    // Let whatever entity we initially pressed down on know we have released
                    if( pressedEntity != null ){
                        if( pressedEntity.passthroughInput ){
                            pressedEntity.getParent().onMouseUp( mouseEvent );
                        }else{
                            pressedEntity.onMouseUp( mouseEvent );
                        }
                    }
                }

                pressedEntity = null;
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                // If we're moving after pressing down on something, we're dragging it
                if( pressedEntity != null ){
                    if( pressedEntity != null ){
                        if( pressedEntity.passthroughInput ){
                            pressedEntity.getParent().onMouseDrag( mouseEvent );
                        }else{
                            pressedEntity.onMouseDrag( mouseEvent );
                        }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

                // What the mouse moved over
                Entity newHoveredEntity = Util.getEntityAtPoint(  mouseEvent.getX(), mouseEvent.getY()  );

                // If we've hovered over a new entity
                if( hoveredEntity != newHoveredEntity ){

                    // We're no longer hovering over what we were hovering on
                    if( hoveredEntity != null ){
                        hoveredEntity.onMouseExit( mouseEvent );
                    }

                    // Hover on the new entity
                    if( newHoveredEntity != null ){
                        newHoveredEntity.onMouseEnter( mouseEvent );
                    }

                    hoveredEntity = newHoveredEntity;

                }else{
                    hoveredEntity.onMouseHoverMove( mouseEvent );
                }

            }
        });

    }

    private void alertParentsToAction(Entity actionRootEntity, InputEvent e ){

        Entity currentEntity = actionRootEntity.getParent();

        while( currentEntity != RootEntity.rootEntity && currentEntity != null ){
            currentEntity.onChildInteraction( e );
            currentEntity = currentEntity.getParent();
        }

    }

}
