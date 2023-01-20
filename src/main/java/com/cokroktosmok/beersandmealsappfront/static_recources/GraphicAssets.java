package com.cokroktosmok.beersandmealsappfront.static_recources;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

public class GraphicAssets {

    static StreamResource imageResource = new StreamResource("logo.png",
            () -> GraphicAssets.class.getResourceAsStream("/logo.png"));
    private static Image logo=new Image(imageResource,"error");

    public static Image logoConfig(float width,float height){
        Image logo=new Image(imageResource,"error");
        logo.setWidth(width, Unit.PIXELS);
        logo.setHeight(height, Unit.PIXELS);
        return logo;
    }

}
