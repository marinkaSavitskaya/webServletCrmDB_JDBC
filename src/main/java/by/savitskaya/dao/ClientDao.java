package by.savitskaya.dao;

import by.savitskaya.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientDao {

    private static Map<Integer, Client> clientList = new ConcurrentHashMap<>();

    public static Map<Integer, Client> getClientList() {
        return clientList;
    }

    public static Client getClientById(Integer id){
        return clientList.get(id);
    }

    public static void addClient(Client client){
        client.setId(clientList.size()+1);
        clientList.put(client.getId(), client);
    }

    public static void updateClient(Client client){
        clientList.get(client.getId()).setFirstName(client.getFirstName());
        clientList.get(client.getId()).setLastName(client.getLastName());
        clientList.get(client.getId()).setAge(client.getAge());
        clientList.get(client.getId()).setNameCompany(client.getNameCompany());
    }

    public static void deleteClient(Integer id){
        clientList.remove(id);
    }

    public static Map<Integer, Client> getclientListByDB(Connection connection) throws SQLException {
        String sql = "SELECT id, firstName, lastName, age, nameCompany FROM \"Client\" ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        while(resultSet.next()){
            Client client = new Client();
            client.setId(resultSet.getInt(1));
            client.setFirstName(resultSet.getString(2));
            client.setLastName(resultSet.getString(3));
            client.setAge(resultSet.getString(4));
            client.setNameCompany(resultSet.getString(5));
            clientList.put(client.getId(), client);
        }

        return clientList;
    }

    public static void addClientToDB(Client client, Connection connection) throws SQLException {
        String sql = "INSERT INTO \"Client\"(id, firstName, lastName, age, nameCompany) VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, clientList.size() + 1);
        preparedStatement.setString(2, client.getFirstName());
        preparedStatement.setString(3, client.getLastName());
        preparedStatement.setInt(4, Integer.valueOf(client.getAge()));
        preparedStatement.setString(5, client.getNameCompany());

        preparedStatement.executeUpdate();


        clientList.put(clientList.size()+1, client);
    }

    public static void updateClientInDB(Client client, Connection connection) throws SQLException {
        String sql = "UPDATE \"Client\" SET firstname = ?, lastname = ?, age = ?, namecompany = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, client.getFirstName());
        preparedStatement.setString(2, client.getLastName());
        preparedStatement.setInt(3, Integer.valueOf(client.getAge()));
        preparedStatement.setString(4, client.getNameCompany());
        preparedStatement.setInt(5, client.getId());

        preparedStatement.executeUpdate();

        clientList.put(client.getId(), client);
    }

    public static void deleteClientFromDB(Integer id, Connection connection) throws SQLException {
        String sql = " Delete from \"Client\" where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        clientList.remove(id);
    }

}
