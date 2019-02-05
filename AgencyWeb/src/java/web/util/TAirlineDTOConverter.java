package web.util;




import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import web.controller.TripsController;

@FacesConverter(value = "tairlineDTOConverter")
public class TAirlineDTOConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String id) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{tripsController}", TripsController.class);

        TripsController tripsController = (TripsController)vex.getValue(ctx.getELContext());
        
        TAirlineDTO airlineDTO;
        try {
            airlineDTO = tripsController.findAirline(Integer.valueOf(id));
            return airlineDTO;
        } catch (NoPermissionException ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object place) {
        return ((TAirlineDTO)place).getId().toString();
    }

}
