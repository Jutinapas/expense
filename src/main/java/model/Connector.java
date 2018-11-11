package model;

public interface Connector {

    Response readAllTransaction();

    void writeTranaction(Transaction transaction);

    void editTransaction(Transaction transaction);

}
