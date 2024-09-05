/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nl.fontys.ais.businesslogic.objects;

import nl.fontys.ais.datarecords.PlaneData;

/**
 *
 * @author Danylo
 */
public class Plane {

    private final PlaneData planeData;

    public Plane(PlaneData planeData) {
        this.planeData = planeData;
    }
    
    public PlaneData getData(){
        return planeData;
    }

    @Override
    public String toString() {
        return "Plane " + this.planeData.ID() + "-" + this.planeData.numberOfSeats() + "-" + this.planeData.capacity();
    }

}
