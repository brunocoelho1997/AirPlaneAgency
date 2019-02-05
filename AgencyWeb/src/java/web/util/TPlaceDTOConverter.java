package web.util;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import logic.TPlaceDTO;
import web.controller.TripsController;

/**
 *
 * @author bruno
 */
@FacesConverter(value = "tplaceDTOConverter")
public class TPlaceDTOConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String placeId) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{tripsController}", TripsController.class);

        TripsController tripsController = (TripsController)vex.getValue(ctx.getELContext());
        
        TPlaceDTO placeDTO = tripsController.findPlace(Integer.valueOf(placeId));
        System.out.println("\n\n\n\n\n\nTPlace Converter - " + placeDTO);
        return placeDTO;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object place) {
        return ((TPlaceDTO)place).getId().toString();
    }

}
