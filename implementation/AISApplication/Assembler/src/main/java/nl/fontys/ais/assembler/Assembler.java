/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package nl.fontys.ais.assembler;


import nl.fontys.ais.businesslogic.BusinessLogicAPI;
import nl.fontys.ais.businesslogic.BusinessLogicFactory;
import nl.fontys.ais.gui.GUIApp;
import nl.fontys.ais.persistence.PersistenceAPI;
import nl.fontys.ais.persistence.PersistenceFactory;

/**
 *
 * @author Danylo
 */
public class Assembler {

    public static void main(String[] args) {
      
        PersistenceAPI persistenceAPI = PersistenceFactory.getImplementation();
        BusinessLogicAPI businesslogicAPI = BusinessLogicFactory.getImplementation( persistenceAPI );
        
        new GUIApp( businesslogicAPI ).show();
    }
}
