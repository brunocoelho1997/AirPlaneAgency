package web.util;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import logic.TTripDTO;
import logic.TUserDTO;
import web.controller.TripsController;
import web.controller.UserController;

@FacesConverter(value = "tuserDTOConverter")
public class TUserDTOConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String id) {
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{userController}", UserController.class);

        UserController usersController = (UserController)vex.getValue(ctx.getELContext());
        TUserDTO userDTO;
        userDTO = usersController.findUser(Integer.valueOf(id));
        return userDTO;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object entity) {
        return ((TUserDTO)entity).getId().toString();
    }

}