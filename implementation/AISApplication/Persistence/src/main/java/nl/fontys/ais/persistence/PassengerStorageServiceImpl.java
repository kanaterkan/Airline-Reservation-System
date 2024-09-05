package nl.fontys.ais.persistence;

import nl.fontys.ais.datarecords.PassengerData;

import java.util.List;

public class PassengerStorageServiceImpl implements PassengerStorageService{
    /**
     * {@inheritDoc}
     */
    @Override
    public PassengerData add(PassengerData data) throws PersistenceException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PassengerData> getAll() throws PersistenceException {
        return null;
    }
    
    @Override
    public List<PassengerData> getPassengersByBookingId(int bookingId) throws PersistenceException{
            return UtilityClass.getPassengersRelatedToBooking(bookingId);
    }
    
    @Override
    public boolean setSeatNumber(String seatNumber, PassengerData pd) throws PersistenceException{
        if(!seatNumber.isEmpty()){
            return UtilityClass.setSeatNumber(seatNumber, pd);
        }
        return false;
    }
    
    @Override
    public boolean setMenuItem(String itemName, PassengerData pd) throws PersistenceException{
        if(!itemName.isEmpty()){
            return UtilityClass.setMenuItem(itemName, pd);
        }
        return false;
    }
    
    @Override
    public boolean setLuggageWeight(double weight, PassengerData pd) throws PersistenceException{
        if(weight != 0.0) {
            return UtilityClass.setLuggageWeight(weight, pd);
        }
        return false;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(PassengerData data) {

    }
}
