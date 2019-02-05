package web.util;




import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaneDTO;
import web.controller.TripsController;

@FacesConverter(value = "tplaneDTOConverter")
public class TPlaneDTOConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String id) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{tripsController}", TripsController.class);

        TripsController tripsController = (TripsController)vex.getValue(ctx.getELContext());
        
        TPlaneDTO planeDTO;
        try {
            planeDTO = tripsController.findPlane(Integer.valueOf(id));
            return planeDTO;
        } catch (NoPermissionException ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object plane) {
        return ((TPlaneDTO)plane).getId().toString();
    }

}