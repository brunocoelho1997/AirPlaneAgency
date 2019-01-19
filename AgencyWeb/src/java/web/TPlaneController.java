package web;

import web.util.JsfUtil;
import web.util.PaginationHelper;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import logic.NoPermissionException;
import logic.TPlaneDTO;
import logic.TripsManagement.TripsManagerLocal;

@Named("tPlaneController")
@SessionScoped
public class TPlaneController implements Serializable {

    private TPlaneDTO current;
    private DataModel items = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    @EJB
    private TripsManagerLocal tripsManager;

    public TPlaneController() {
        // Do nothing
    }

    public TPlaneDTO getSelected() {
        if (current == null) {
            current = new TPlaneDTO();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    try {
                        return tripsManager.findAllPlanes("admin").size();
                        
                    } catch (NoPermissionException ex) {
                        Logger.getLogger(TPlaneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    return 0;
                }

                @Override
                public DataModel createPageDataModel() {
                    try {
                        return new ListDataModel(tripsManager.findPlanesByRange("admin", getPageFirstItem(), getPageFirstItem() + getPageSize()));
                        
                    } catch (NoPermissionException ex) {
                        Logger.getLogger(TPlaneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    return null;
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (TPlaneDTO) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TPlaneDTO();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            tripsManager.addPlane(current, "admin");
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TPlaneCreated"));
            return prepareCreate();
            
        } catch (NoPermissionException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TPlaneDTO) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            tripsManager.editPlane(current, "admin");
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TPlaneUpdated"));
            return "View";
            
        } catch (NoPermissionException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TPlaneDTO) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";

        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            tripsManager.removePlane(current, "admin");
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TPlaneDeleted"));

        } catch (NoPermissionException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        try {
            int count = tripsManager.findAllPlanes("admin").size();
            
            if (selectedItemIndex >= count) {
                // selected index cannot be bigger than number of items:
                selectedItemIndex = count - 1;
                // go to previous page if last page disappeared:
                if (pagination.getPageFirstItem() >= count) {
                    pagination.previousPage();
                }
            }
            
            if (selectedItemIndex >= 0) {
                current = tripsManager.findPlanesByRange("admin", selectedItemIndex, selectedItemIndex + 1).get(0);
            }
            
        } catch (NoPermissionException ex) {
            Logger.getLogger(TPlaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        try {
            return JsfUtil.getSelectItems(tripsManager.findAllPlanes("admin"), false);

        } catch (NoPermissionException ex) {
            Logger.getLogger(TPlaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        try {
            return JsfUtil.getSelectItems(tripsManager.findAllPlanes("admin"), true);
            
        } catch (NoPermissionException ex) {
            Logger.getLogger(TPlaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public TPlaneDTO getTPlane(java.lang.Integer id) {
        try {
            return tripsManager.findPlane(id, "admin");
            
        } catch (NoPermissionException ex) {
            Logger.getLogger(TPlaneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @FacesConverter(forClass = TPlaneDTO.class)
    public static class TPlaneControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TPlaneController controller = (TPlaneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tPlaneController");
            return controller.getTPlane(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TPlaneDTO) {
                TPlaneDTO o = (TPlaneDTO) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TPlaneDTO.class.getName());
            }
        }

    }

}
