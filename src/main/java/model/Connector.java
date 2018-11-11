package model;

public interface Connector {

    Response readAllTransaction();

    void writeTransaction(Transaction transaction);

    void editTransaction(Transaction transaction);

}
