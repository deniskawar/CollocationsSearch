package database;

import neuralNetwork.NeuralNetwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {
    public static void connectToBD() {
        /*
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/test_base"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        String user = "root";
        String password = "admin";
        String query = "select count(*) from collocations";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                 int count = rs.getInt(1);
                 System.out.println("Total number of collocations in the table : " + count);
             }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }*/
    }

    public void disconnectFromBD() {

    }

    public void readFromBD() {

    }
    public void writeToBD() {

    }
    public static Map<Integer, String> getProjectsFromDB() {
        Map<Integer, String> projects = new HashMap<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "select * from project";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("project_name");
                projects.put(projectId, projectName);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return projects;
    }
    public static int addProjectToDB(String projectName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "insert into project(project_name) values (?);";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, projectName);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
        }
        query = "select * from project";
        int projectId = 0;
        int maxProjectId = Integer.MIN_VALUE;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                projectId = rs.getInt("project_id");
                if (maxProjectId < projectId) maxProjectId = projectId;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return maxProjectId;
    }
    public static boolean projectRepeatCheckDB(String inputProjectName) {
        boolean repeat = false;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "select * from project";
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String projectName = rs.getString("project_name");
                if (projectName.equals(inputProjectName)) repeat = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
        return repeat;
    }
    public static void deleteProjectFromDBById(int projectId) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";
        String query = "delete from project where project_id = ?;";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            preparedStatement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
        }
    }

    public static void addNeuralNetworkToDB(NeuralNetwork neuralNetwork, int projectId) {
        /*
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search" +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";


        String query = "insert into neural_network(project_id) values (?);";
        try {
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            preparedStatement.execute();

        // ОСТАНОВИЛСЯ ТУТ!!!!!!!!!!
        for (int i = 0; i < neuralNetwork.getLayers().size(); i++) {
            // добавить каждый слой
            query = "insert into layer(layer_serial_number, neural_network_id) values (?, (select max(neural_network_id) from neural_network));";
            //con = DriverManager.getConnection(url, user, password);
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, i + 1);
            preparedStatement.execute();

            for (int j = 0; j < neuralNetwork.getLayers().get(i).getNeurons().size(); j++) {
                // добавить каждый нейрон
                query = "insert into neuron(layer_id, output_value) values ((select max(layer_id) from layer), ?);";
                //con = DriverManager.getConnection(url, user, password);
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setDouble(1, neuralNetwork.getLayers().get(i).getNeurons().get(j).getOutputValue());
                preparedStatement.execute();

                for (int k = 0; k < neuralNetwork.getLayers().get(i).getNeurons().get(j).getInputSynapses().size(); k++) {
                    query = "insert into synapse(weight) values (?);";
                    //con = DriverManager.getConnection(url, user, password);
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setDouble(1, neuralNetwork.getLayers().get(i).getNeurons().get(j).getInputSynapses().get(k).getWeight());
                    preparedStatement.execute();

                    query = "insert into synapse_neuron(synapse_id, neuron_id) values ((select max(synapse_id) from synapse),(select max(neuron_id) from neuron));";
                    //con = DriverManager.getConnection(url, user, password);
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.execute();
                    // добавить каждый синапс и связать с одним нейроном
                }
            }
        }
    }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
        }
        */
        boolean firstIteration = true;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/collocations_search" +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC" +
                "&allowPublicKeyRetrieval=true" +
                "&useSSL=false";
        String user = "root";
        String password = "admin";




        int neuralNetworkMaxId = 0;
        int layerMaxId = 0;
        int neuronMaxId = 0;
        int synapseMaxId = 0;
        int synapseNeuronQueryMaxId = 0;

        String neuralNetworkMaxIdQuery = "select max(neural_network_id) from neural_network";
        String layerMaxIdQuery = "select max(layer_id) from layer";
        String neuronMaxIdQuery = "select max(neuron_id) from neuron";
        String synapseMaxIdQuery = "select max(synapse_id) from synapse";

        String neuralNetworkQuery = "insert into neural_network(project_id) values (?);";
        String layerQuery = "insert into layer(layer_serial_number, neural_network_id) values (?, ?);";
        String neuronQuery = "insert into neuron(layer_id, output_value) values (?, ?);";
        String synapseQuery = "insert into synapse(weight) values (?);";
        String synapseNeuronQuery = "insert into synapse_neuron(synapse_id, neuron_id) values (?, ?);";

        PreparedStatement neuralNetworkPreparedStatement = null;
        PreparedStatement layerPreparedStatement = null;
        PreparedStatement neuronPreparedStatement = null;
        PreparedStatement synapsePreparedStatement = null;
        PreparedStatement synapseNeuronPreparedStatement = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);

            neuralNetworkPreparedStatement = con.prepareStatement(neuralNetworkQuery);
            layerPreparedStatement = con.prepareStatement(layerQuery);
            neuronPreparedStatement = con.prepareStatement(neuronQuery);
            synapsePreparedStatement = con.prepareStatement(synapseQuery);
            synapseNeuronPreparedStatement = con.prepareStatement(synapseNeuronQuery);
        }
        catch (SQLException ex) {

        }




        //String query;
        try {

            // ЗАКОНЧИЛ ЗДЕСЬ, ВСЕ MAX ID РАБОТАЮТ НАДО СДЕЛАТЬ BATCH ЗАПРОСЫ, ДЛЯ ЭТОГО НАДО КАЖДЫЙ ID КАЖДЫЙ РАЗ В СВОЕМ МЕСТЕ ПРИБАВЛЯТЬ


            neuralNetworkPreparedStatement.setInt(1, projectId);
            neuralNetworkPreparedStatement.addBatch();
            //neuralNetworkPreparedStatement.execute();
            if (firstIteration) {
                neuralNetworkPreparedStatement.executeBatch();
                neuralNetworkPreparedStatement.clearBatch();

                stmt = con.createStatement();
                rs = stmt.executeQuery(neuralNetworkMaxIdQuery);
                rs.next();
                neuralNetworkMaxId = rs.getInt("max(neural_network_id)");
            } else {
                neuralNetworkMaxId++;
            }

            for (int i = 0; i < neuralNetwork.getLayers().size(); i++) {
                // добавить каждый слой
                //query = "insert into layer(layer_serial_number, neural_network_id) values (?, ?);";

                //con = DriverManager.getConnection(url, user, password);

                layerPreparedStatement.setInt(1, i + 1);
                layerPreparedStatement.setInt(2, neuralNetworkMaxId);
                layerPreparedStatement.addBatch();
                //layerPreparedStatement.execute();
                if (firstIteration) {
                    layerPreparedStatement.executeBatch();
                    layerPreparedStatement.clearBatch();

                    stmt = con.createStatement();
                    rs = stmt.executeQuery(layerMaxIdQuery);
                    rs.next();
                    layerMaxId = rs.getInt("max(layer_id)");
                }
                else {
                    layerMaxId++;
                }

                for (int j = 0; j < neuralNetwork.getLayers().get(i).getNeurons().size(); j++) {
                    // добавить каждый нейрон
                    //query = "insert into neuron(layer_id, output_value) values (?, ?);";
                    //con = DriverManager.getConnection(url, user, password);
                    neuronPreparedStatement.setInt(1, layerMaxId);
                    neuronPreparedStatement.setDouble(2, neuralNetwork.getLayers().get(i).getNeurons().get(j).getOutputValue());
                    neuronPreparedStatement.addBatch();
                    //neuronPreparedStatement.execute();
                    if (firstIteration) {
                        neuronPreparedStatement.executeBatch();
                        neuronPreparedStatement.clearBatch();

                        stmt = con.createStatement();
                        rs = stmt.executeQuery(neuronMaxIdQuery);
                        rs.next();
                        neuronMaxId = rs.getInt("max(neuron_id)");
                    }
                    else {
                        neuronMaxId++;
                    }

                    for (int k = 0; k < neuralNetwork.getLayers().get(i).getNeurons().get(j).getInputSynapses().size(); k++) {
                        //query = "insert into synapse(weight) values (?);";
                        //con = DriverManager.getConnection(url, user, password);
                        synapsePreparedStatement.setDouble(1, neuralNetwork.getLayers().get(i).getNeurons().get(j).getInputSynapses().get(k).getWeight());
                        synapsePreparedStatement.addBatch();
                        //synapsePreparedStatement.execute();
                        if (firstIteration) {
                            synapsePreparedStatement.executeBatch();
                            synapsePreparedStatement.clearBatch();

                            stmt = con.createStatement();
                            rs = stmt.executeQuery(synapseMaxIdQuery);
                            rs.next();
                            synapseMaxId = rs.getInt("max(synapse_id)");
                        }
                        else {
                            synapseMaxId++;
                        }

                        //query = "insert into synapse_neuron(synapse_id, neuron_id) values ((select max(synapse_id) from synapse),(select max(neuron_id) from neuron));";
                        //con = DriverManager.getConnection(url, user, password);
                        synapseNeuronPreparedStatement.setInt(1, synapseMaxId);
                        synapseNeuronPreparedStatement.setInt(2, neuronMaxId);
                        synapseNeuronPreparedStatement.addBatch();
                        //synapseNeuronPreparedStatement.execute();
                        // добавить каждый синапс и связать с одним нейроном
                        firstIteration = false;
                    }
                }
            }
            neuralNetworkPreparedStatement.executeBatch();
            con.commit();
            layerPreparedStatement.executeBatch();
            con.commit();
            neuronPreparedStatement.executeBatch();
            con.commit();
            synapsePreparedStatement.executeBatch();
            con.commit();
            synapseNeuronPreparedStatement.executeBatch();
            con.commit();
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }

            try {stmt.close();}
            catch (SQLException se) {

            }
            try {rs.close();}
            catch (SQLException se) {

            }
        }
    }

    public static NeuralNetwork getNeuralNetworkFromDB() {
        NeuralNetwork neuralNetwork = new NeuralNetwork(3);
        return neuralNetwork;
    }
}
