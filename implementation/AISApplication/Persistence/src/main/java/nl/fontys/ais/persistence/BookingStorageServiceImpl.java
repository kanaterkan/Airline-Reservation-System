package nl.fontys.ais.persistence;

import java.time.LocalDate;
import nl.fontys.ais.datarecords.BookingData;
import nl.fontys.ais.datarecords.PassengerData;

import java.util.List;

public class BookingStorageServiceImpl implements BookingStorageService{
    /**
     * {@inheritDoc}
     */
    @Override
    public BookingData add(BookingData data) throws PersistenceException {
       List<PassengerData> passengerDataList = data.getPassengers();
       for(PassengerData i: passengerDataList){
           String first = i.passengerFName();
           String last = i.passengerLName();
           LocalDate dob = i.dateOfBirth();
           if(first.isEmpty() ){
               String error = "First name";
               throw new PersistenceException(error,new Throwable());
           }
           if(last.isEmpty()){
               String error = "Last name";
               throw new PersistenceException(error, new Throwable());
           }
           if(dob.isAfter(LocalDate.now())){
               String error="Passenger DOB cant be in the future";
               throw new PersistenceException(error, new Throwable());
           }
       }
        UtilityClass.addBooking(data);
        return data;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookingData> getAll() throws PersistenceException {
            return UtilityClass.getBookings();
    }
    
    public List<BookingData> getBookingsById(int id) throws PersistenceException{
            return UtilityClass.getBookings(id);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(BookingData data) {

    }
}
