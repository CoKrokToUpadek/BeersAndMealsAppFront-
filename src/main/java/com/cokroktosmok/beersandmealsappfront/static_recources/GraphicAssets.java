package com.cokroktosmok.beersandmealsappfront.static_recources;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;



public class GraphicAssets {

    StreamResource imageResource = new StreamResource("logo.png",
            () -> GraphicAssets.class.getResourceAsStream("/logo.png"));
    private Image logo=new Image(imageResource,"error");
    Image mealImage=new Image();

    public Image logoConfig(float width,float height){
        Image logo=new Image(imageResource,"error");
        logo.setWidth(width, Unit.PIXELS);
        logo.setHeight(height, Unit.PIXELS);
        return logo;
    }

    public Image imageConfig(String path, int width, int height){
        mealImage.setSrc(path);
        mealImage.setAlt("/no_image.jpg");
        mealImage.setMaxHeight(height+"px");
        mealImage.setMaxWidth(width+"px");
        return mealImage;

    }

}

