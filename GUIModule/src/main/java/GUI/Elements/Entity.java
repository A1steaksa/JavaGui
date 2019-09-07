package GUI.Elements;

import GUI.Core.Renderer;
import GUI.RootEntity;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Entity {

    private Entity parent;
    public List<Entity> children = Collections.synchronizedList( new LinkedList<Entity>() );

    // The bounds around this Entity that it receives input in
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    // Whether input should be passed directly to the parent
    public boolean passthroughInput = false;

    public Entity( int x, int y, int width, int height ){
        this();
        setPos( x, y );
        setSize( width, height );
    }
    public Entity(){
        setParent( RootEntity.rootEntity );
    }

    //region Positioning

    public void setPos( int x, int y ){
        setX( x );
        setY( y );
    }

    public void setX( int x ){
        this.x = x;
    }

    public void setY( int y ){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getGlobalX(){
        // If we have no parent, our global X is our normal X
        if( parent == null ){
            return getX();
        }

        return parent.getGlobalX() + getX();
    }

    public int getGlobalY(){
        // If we have no parent, our global Y is our normal Y
        if( parent == null ){
            return getY();
        }
        return parent.getGlobalY() + getY();
    }

    // Localizes a given x coordinate local to this entity
    public int localizeX( int x ){
        return x - getGlobalX();
    }

    // Localizes a given y coordinate local to this entity
    public int localizeY( int y ){
        return y - getGlobalY();
    }

    //endregion

    //region Child bounds

    /**
     * The rectangle, in entity-local space, where this entity's children should be positioned and rendered
     */
    public Rectangle getChildBounds(){
        return new Rectangle(
            0,
            0,
            this.getWidth(),
            this.getHeight()
        );
    }

    public Rectangle getGlobalChildBounds(){
        Rectangle childBounds = getChildBounds();

        return new Rectangle(
            this.getGlobalX() + childBounds.x,
            this.getGlobalY() + childBounds.y,
            childBounds.width,
            childBounds.height
        );
    }

    public Rectangle getParentGlobalChildBounds(){
        if( getParent() != null ){
            return getParent().getParentGlobalChildBounds().intersection( getGlobalChildBounds() );
        }{
            return getGlobalChildBounds();
        }
    }

    //endregion

    //region Sizing

    public void setWidth( int width ){
        this.width = width;
        layoutHierarchy();
    }

    public void setHeight( int height ){
        this.height = height;
        layoutHierarchy();
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setSize( int width, int height ){
        setWidth( width );
        setHeight( height );
    }

    public Rectangle getBounds(){
        return new Rectangle( x, y, width, height );
    }

    public Rectangle getGlobalBounds(){
        return new Rectangle(
                this.getGlobalX(),
                this.getGlobalY(),
                this.getWidth(),
                this.getHeight()
        );
    }

    //endregion

    //region Parenting

    public void setParent( Entity newParent ){

        // In case our new parent doesn't exist, just don't do anything
        if( newParent == null ){
            return;
        }

        // Add us to our new parent's children
        newParent.children.add( this );

        // If we have a parent, remove us from them
        if( parent != null ){
            parent.children.remove( this );
        }

        parent = newParent;

        layoutHierarchy();
    }

    public Entity getParent(){
        return parent;
    }

    //endregion

    //region Rendering

    public final void drawHierarchy( Graphics2D g, int x, int y ){

        // Clip to parent's child bounds
        if( Renderer.clipEnabled ){
            if( getParent() != null ){



                //Rectangle clip = getParent().getGlobalChildBounds().intersection( this.getGlobalChildBounds() );
                Rectangle clip = getParentGlobalChildBounds();

                // The clips rectangle is off by 1
                clip.width += 1;
                clip.height += 1;

                //If this entity is entirely clipped off, no children can be drawn so we can stop drawing the hierarchy
                if( clip.width <= 0 || clip.height <= 0 ){
                    return;
                }

                g.setClip( clip );
            }else{
                g.setClip( this.getGlobalChildBounds() );
            }
        }

        // Draw this entity with its parent's clip
        drawEntity( g, x, y );

        // Draw our children
        for( Entity child : children ){
            child.drawHierarchy( g, child.getGlobalX(), child.getGlobalY() );
        }

    }

    /**
     * Lays out this entity, and all of its children
     */
    public final void layoutHierarchy(){
        layoutEntity();
        for( Entity child : children ){
            child.layoutHierarchy();
        }
    }

    /**
     * Called when this entity should recalculate its layout
     * Usually when its parent is resized
     */
    public abstract void layoutEntity();

    public abstract void drawEntity( Graphics2D g, int x, int y );

    //endregion

    //region Input

    public abstract void onMouseDown( MouseEvent e );
    public abstract void onMouseUp( MouseEvent e );
    public abstract void onMouseClick( MouseEvent e );
    public abstract void onMouseDrag( MouseEvent e );
    public abstract void onChildInteraction( InputEvent e );
    public abstract void onMouseEnter( MouseEvent e );
    public abstract void onMouseExit( MouseEvent e );
    public abstract void onMouseHoverMove( MouseEvent e );

    //endregion

}
