package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.MenuData;

public class Menu {

    private final MenuData menuData;
    public Menu(MenuData menuData)  {
        this.menuData = menuData;
    }

    public MenuData getMenuData(){
        return menuData;
    }


    @Override
    public String toString(){
        return "Menu " + this.menuData.ID() + "-" + this.menuData.name() + "-" + this.menuData.price();
    }
}
