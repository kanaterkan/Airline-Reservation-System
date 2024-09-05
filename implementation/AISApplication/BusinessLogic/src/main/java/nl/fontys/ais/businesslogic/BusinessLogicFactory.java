/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nl.fontys.ais.businesslogic;

import nl.fontys.ais.persistence.PersistenceAPI;

/**
 *
 * @author Danylo
 */
public interface BusinessLogicFactory {
    static BusinessLogicAPI getImplementation(PersistenceAPI persistenceAPI) {
        return new BusinessLogicAPIImpl(persistenceAPI);
    }
}
