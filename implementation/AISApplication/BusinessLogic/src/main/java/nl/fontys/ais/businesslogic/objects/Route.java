/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.RouteData;

/**
 *
 * @author Danylo
 */
public class Route {

    private RouteData routeData;

    public Route(RouteData routeData) {
        this.routeData = routeData;
    }
    
    public RouteData getData(){
        return routeData;
    }

    @Override
    public String toString() {
        return this.routeData.departureAirport() + "-" + this.routeData.arrivalAirport();
    }
}
