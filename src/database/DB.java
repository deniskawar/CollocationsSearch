package database;

import javafx.scene.control.TableView;
import main.AddRuleTableRow;
import main.Main;
import neuralNetwork.Layer;
import neuralNetwork.NeuralNetwork;
import neuralNetwork.Neuron;
import neuralNetwork.Synapse;
import words.Characteristic;
import words.Rule;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {
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

        try {
            neuralNetworkPreparedStatement.setInt(1, projectId);
            neuralNetworkPreparedStatement.addBatch();
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

                layerPreparedStatement.setInt(1, i + 1);
                layerPreparedStatement.setInt(2, neuralNetworkMaxId);
                layerPreparedStatement.addBatch();
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

                    neuronPreparedStatement.setInt(1, layerMaxId);
                    neuronPreparedStatement.setDouble(2, neuralNetwork.getLayers().get(i).getNeurons().get(j).getOutputValue());
                    neuronPreparedStatement.addBatch();
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

                        synapsePreparedStatement.setDouble(1, neuralNetwork.getLayers().get(i).getNeurons().get(j).getInputSynapses().get(k).getWeight());
                        synapsePreparedStatement.addBatch();
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

                        synapseNeuronPreparedStatement.setInt(1, synapseMaxId);
                        synapseNeuronPreparedStatement.setInt(2, neuronMaxId);
                        synapseNeuronPreparedStatement.addBatch();
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
    public static NeuralNetwork getNeuralNetworkFromDB(int projectId) {

        Connection con = null;

        ResultSet neuralNetworkResultSet = null;
        ResultSet layerResultSet = null;
        ResultSet neuronResultSet = null;
        ResultSet synapseResultSet = null;
        ResultSet synapseNeuronResultSet = null;


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

        String neuralNetworkQuery = "select neural_network_id from neural_network where project_id = ?";

        PreparedStatement neuralNetworkPreparedStatement = null;
        PreparedStatement layerPreparedStatement = null;
        PreparedStatement neuronPreparedStatement = null;
        PreparedStatement synapsePreparedStatement = null;
        PreparedStatement synapseNeuronPreparedStatement = null;

        int neuralNetworkId = 0;

        List<Layer> layers = new ArrayList<>();

        try {
            con = DriverManager.getConnection(url, user, password);
            neuralNetworkPreparedStatement = con.prepareStatement(neuralNetworkQuery);
            neuralNetworkPreparedStatement.setInt(1, projectId);
            neuralNetworkResultSet = neuralNetworkPreparedStatement.executeQuery();
            neuralNetworkResultSet.next();
            neuralNetworkId = neuralNetworkResultSet.getInt("neural_network_id");

            layerPreparedStatement = con.prepareStatement("select * from layer where neural_network_id = ? ORDER BY layer_id");
            layerPreparedStatement.setInt(1, neuralNetworkId);
            layerResultSet = layerPreparedStatement.executeQuery();

            while (layerResultSet.next()) {
                int layerId = layerResultSet.getInt("layer_id");
                Layer layer = new Layer();
                List<Neuron> neurons = new ArrayList<>();

                neuronPreparedStatement = con.prepareStatement("select * from neuron where layer_id = ? ORDER BY neuron_id");
                neuronPreparedStatement.setInt(1, layerId);
                neuronResultSet = neuronPreparedStatement.executeQuery();

                while (neuronResultSet.next()) {
                    int neuronId = neuronResultSet.getInt("neuron_id");
                    Neuron neuron = new Neuron();
                    neuron.setOutputValue(neuronResultSet.getDouble("output_value"));
                    List<Synapse> inputSynapses = new ArrayList<>();


                    synapseNeuronPreparedStatement = con.prepareStatement("select synapse_id from synapse_neuron where neuron_id = ? ORDER BY synapse_id");
                    synapseNeuronPreparedStatement.setInt(1, neuronId);
                    synapseNeuronResultSet = synapseNeuronPreparedStatement.executeQuery();

                    while (synapseNeuronResultSet.next()) {
                        int synapseId = synapseNeuronResultSet.getInt("synapse_id");

                        synapsePreparedStatement = con.prepareStatement("select weight from synapse where synapse_id = ?");
                        synapsePreparedStatement.setInt(1, synapseId);
                        synapseResultSet = synapsePreparedStatement.executeQuery();

                        while (synapseResultSet.next()) {
                            double weight = synapseResultSet.getDouble("weight");
                            Synapse synapse = new Synapse(weight);
                            inputSynapses.add(synapse);
                        }

                    }
                    neuron.setInputSynapses(inputSynapses);
                    neurons.add(neuron);
                }
                layer.setNeurons(neurons);
                layers.add(layer);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {con.close();}
            catch (SQLException se) {

            }
            try {neuralNetworkResultSet.close();}
            catch (SQLException se) {

            }
            try {layerResultSet.close();}
            catch (SQLException se) {

            }
            try {neuronResultSet.close();}
            catch (SQLException se) {

            }
            try {synapseNeuronResultSet.close();}
            catch (SQLException se) {

            }
            try {synapseResultSet.close();}
            catch (SQLException se) {

            }
        }
        NeuralNetwork neuralNetwork = new NeuralNetwork(layers.size());
        neuralNetwork.setLayers(layers);

        return neuralNetwork;
    }
    public static boolean isThereAnyNeuralNetworkInProject(int projectId) {

        Connection con = null;
        ResultSet resultSet = null;
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

        String query = "select COUNT(neural_network_id) from neural_network where project_id = ?";
        PreparedStatement preparedStatement;


        try {
            con = DriverManager.getConnection(url, user, password);
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int neuralNetworkCount = resultSet.getInt("COUNT(neural_network_id)");
            return (neuralNetworkCount > 0);
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {

            }
        }
        return false;
    }
    public static void deleteNeuralNetworkFromDB(int projectId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
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
        String query = "delete from neural_network where project_id = ?;";

        try {
            con = DriverManager.getConnection(url, user, password);
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            preparedStatement.execute();

            query = "delete from synapse where synapse_id not in (select synapse_id from synapse_neuron)";
            preparedStatement = con.prepareStatement(query);
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
    public static void setAutoIncrement() {
        Connection con = null;
        PreparedStatement preparedStatement = null;
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
        String query = "SELECT COUNT(project_id) from project;";

        try {
            con = DriverManager.getConnection(url, user, password);
            preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int projectsCount = resultSet.getInt("COUNT(project_id)");
            if (projectsCount == 0) {
                query = "ALTER TABLE project AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE neural_network AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE neuron AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE synapse AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE synapse_neuron AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE layer AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE characteristic AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE decision_word AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE homonym AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
                query = "ALTER TABLE rule_collocation AUTO_INCREMENT = 1;";
                preparedStatement = con.prepareStatement(query);
                preparedStatement.execute();
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        finally {
            try {con.close();}
            catch (SQLException se) {

            }
        }

    }
    public static void addRuleToDB(TableView table, int projectId, boolean isHomonymLeft) {

        //boolean firstIteration = true;
        Connection con = null;
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

        String ruleCollocationMaxIdQuery = "select max(rule_collocation_id) from rule_collocation";
        String homonymMaxIdQuery = "select max(homonym_id) from homonym";
        String decisionWordMaxIdQuery = "select max(decision_word_id) from decision_word";
        String characteristicMaxIdQuery = "select max(characteristic_id) from characteristic";

        String ruleCollocationQuery = "insert into rule_collocation(project_id, is_rule) values (?, ?);";
        String homonymQuery = "insert into homonym(rule_collocation_id, word, homonym_position) values ((select max(rule_collocation_id) from rule_collocation), null, ?);";
        String decisionWordQuery = "insert into decision_word(rule_collocation_id, word, decision_word_position) values ((select max(rule_collocation_id) from rule_collocation), null, ?);";
        String homonymCharacteristicQuery =
                "insert into characteristic(homonym_id, decision_word_id, default_value, alternative_value, maximum_value, name) values ((select max(homonym_id) from homonym), null, ?, ?, ?, ?);";
        String decisionWordCharacteristicQuery =
                "insert into characteristic(homonym_id, decision_word_id, default_value, alternative_value, maximum_value, name) values (null, (select max(decision_word_id) from decision_word), ?, null, ?, ?);";

        PreparedStatement preparedStatement = null;


        try {
            con = DriverManager.getConnection(url, user, password);
            preparedStatement = con.prepareStatement(ruleCollocationQuery);
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, 1);
            preparedStatement.execute();

            preparedStatement = con.prepareStatement(homonymQuery);
            preparedStatement.setInt(1, isHomonymLeft ? 1 : 2);
            preparedStatement.execute();

            preparedStatement = con.prepareStatement(decisionWordQuery);
            preparedStatement.setInt(1, isHomonymLeft ? 2 : 1);
            preparedStatement.execute();

            int index = 0;
            for (Map.Entry<String, Map<Integer,String>> entry : Main.getCharacteristicsInfoForRules().entrySet()) {
                String characteristicName = entry.getKey();
                String firstWordCharacteristicName = (String) ((AddRuleTableRow) table.getItems().get(index)).getFirstWordChoice().getSelectionModel().getSelectedItem();
                String secondWordCharacteristicName = (String) ((AddRuleTableRow) table.getItems().get(index)).getSecondWordChoice().getSelectionModel().getSelectedItem();
                String alternativeCharacteristicName = (String) ((AddRuleTableRow) table.getItems().get(index)).getAlternativeChoice().getSelectionModel().getSelectedItem();

                int firstWordCharacteristicValue = 0;
                int secondWordCharacteristicValue = 0;
                int alternativeCharacteristicValue = 0;

                for (Map.Entry<Integer, String> entry1 : entry.getValue().entrySet()) {
                    if (entry1.getValue().equals(firstWordCharacteristicName)) firstWordCharacteristicValue = entry1.getKey();
                    if (entry1.getValue().equals(secondWordCharacteristicName)) secondWordCharacteristicValue = entry1.getKey();
                    if (entry1.getValue().equals(alternativeCharacteristicName)) alternativeCharacteristicValue = entry1.getKey();
                }

                preparedStatement = con.prepareStatement(homonymCharacteristicQuery);
                preparedStatement.setInt(1, (isHomonymLeft) ? firstWordCharacteristicValue : secondWordCharacteristicValue);
                preparedStatement.setInt(2, alternativeCharacteristicValue);
                preparedStatement.setInt(3, entry.getValue().size());
                preparedStatement.setString(4, characteristicName);
                preparedStatement.execute();

                preparedStatement = con.prepareStatement(decisionWordCharacteristicQuery);
                preparedStatement.setInt(1, !(isHomonymLeft) ? firstWordCharacteristicValue : secondWordCharacteristicValue);
                preparedStatement.setInt(2, entry.getValue().size());
                preparedStatement.setString(3, characteristicName);
                preparedStatement.execute();

                index++;
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
    }
    public static List<Rule> getRulesFromDB(int projectId) {
        Connection con = null;
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

        String ruleCollocationQuery = "select * from rule_collocation where project_id = ? and is_rule = 1;";
        String homonymQuery = "select * from homonym where rule_collocation_id = ?";
        String decisionWordQuery = "select * from decision_word where rule_collocation_id = ?;";
        String homonymCharacteristicQuery = "select * from characteristic where homonym_id = ?;";
        String decisionWordCharacteristicQuery = "select * from characteristic where decision_word_id = ?;";

        PreparedStatement preparedStatement = null;

        List<Rule> rules = new ArrayList<>();

        try {
            con = DriverManager.getConnection(url, user, password);

            preparedStatement = con.prepareStatement(ruleCollocationQuery);
            preparedStatement.setInt(1, projectId);
            ResultSet ruleCollocationResultSet = preparedStatement.executeQuery();

            while (ruleCollocationResultSet.next()) {
                int ruleId = ruleCollocationResultSet.getInt("rule_collocation_id");

                preparedStatement = con.prepareStatement(homonymQuery);
                preparedStatement.setInt(1, ruleId);
                ResultSet homonymResultSet = preparedStatement.executeQuery();
                homonymResultSet.next();
                int homonymId = homonymResultSet.getInt("homonym_id");
                int homonymPosition = homonymResultSet.getInt("homonym_position");

                preparedStatement = con.prepareStatement(decisionWordQuery);
                preparedStatement.setInt(1, ruleId);
                ResultSet decisionWordResultSet = preparedStatement.executeQuery();
                decisionWordResultSet.next();
                int decisionWordId = decisionWordResultSet.getInt("decision_word_id");
                int decisionWordPosition = decisionWordResultSet.getInt("decision_word_position");

                preparedStatement = con.prepareStatement(homonymCharacteristicQuery);
                preparedStatement.setInt(1, homonymId);
                ResultSet homonymCharacteristicResultSet = preparedStatement.executeQuery();

                List<Characteristic> homonymCharacteristics = new ArrayList<>();
                List<Characteristic> alternativeCharacteristics = new ArrayList<>();
                while (homonymCharacteristicResultSet.next()) {
                    int defaultValue = homonymCharacteristicResultSet.getInt("default_value");
                    int maximumValue = homonymCharacteristicResultSet.getInt("maximum_value");
                    int alternativeValue = homonymCharacteristicResultSet.getInt("alternative_value");
                    String name = homonymCharacteristicResultSet.getString("name");
                    Characteristic homonymCharacteristic = new Characteristic(name, maximumValue);
                    Characteristic alternativeCharacteristic = new Characteristic(name, maximumValue);
                    homonymCharacteristic.setValue(defaultValue);
                    alternativeCharacteristic.setValue(alternativeValue);
                    homonymCharacteristics.add(homonymCharacteristic);
                    alternativeCharacteristics.add(alternativeCharacteristic);
                }

                preparedStatement = con.prepareStatement(decisionWordCharacteristicQuery);
                preparedStatement.setInt(1, decisionWordId);
                ResultSet decisionWordCharacteristicResultSet = preparedStatement.executeQuery();

                List<Characteristic> decisionWordCharacteristics = new ArrayList<>();
                while (decisionWordCharacteristicResultSet.next()) {
                    int defaultValue = decisionWordCharacteristicResultSet.getInt("default_value");
                    int maximumValue = decisionWordCharacteristicResultSet.getInt("maximum_value");
                    String name = decisionWordCharacteristicResultSet.getString("name");
                    Characteristic characteristic = new Characteristic(name, maximumValue);
                    characteristic.setValue(defaultValue);
                    decisionWordCharacteristics.add(characteristic);
                }
                Rule rule = new Rule(ruleId, homonymPosition == 1 ? homonymCharacteristics : decisionWordCharacteristics,
                        !(homonymPosition == 1) ? homonymCharacteristics : decisionWordCharacteristics,
                        alternativeCharacteristics, homonymPosition == 1);
                rules.add(rule);
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
        return rules;
    }
    public static void deleteRuleFromBD(int ruleId) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
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
        String query = "delete from rule_collocation where rule_collocation_id = ?;";

        try {
            con = DriverManager.getConnection(url, user, password);
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, ruleId);
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


}
