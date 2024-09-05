package nl.fontys.ais.persistence;

import nl.fontys.ais.datarecords.MenuData;

import java.util.ArrayList;
import java.util.List;

public class MenuStorageServiceImpl implements MenuStorageService {
   List<MenuData> list = new ArrayList<>();

   /**
    * {@inheritDoc}
    */
   @Override
   public MenuData add(MenuData data) throws PersistenceException {
      return data;
   }
   /**
    * {@inheritDoc}
    */
   @Override
    public List<MenuData> getAll() throws PersistenceException {
       return UtilityClass.getMenus();
   }
   /**
    * {@inheritDoc}
    */
   @Override
   public void delete(MenuData data) {

   }

}
