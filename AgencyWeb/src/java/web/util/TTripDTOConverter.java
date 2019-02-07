/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import logic.NoPermissionException;
import logic.TPlaneDTO;
import logic.TTripDTO;
import web.controller.TripsController;

@FacesConverter(value = "ttripDTOConverter")
public class TTripDTOConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String id) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{tripsController}", TripsController.class);

        TripsController tripsController = (TripsController)vex.getValue(ctx.getELContext());
        TTripDTO tripDTO;
        tripDTO = tripsController.findTrip(Integer.valueOf(id));
        return tripDTO;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object entity) {
        return ((TTripDTO)entity).getId().toString();
    }

}