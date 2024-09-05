package nl.fontys.ais.persistence;

import nl.fontys.ais.datarecords.CustomerData;

import java.util.List;

public class CustomerStorageServiceImpl implements CustomerStorageService{

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerData add(CustomerData data) throws PersistenceException {
        CustomerData cd = UtilityClass.addCustomer(data);
        return cd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CustomerData> getAll() throws PersistenceException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(CustomerData data) {

    }
}
