import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MetropolisesTest extends TestCase {
    private Connection connection;
    private Statement statement;
    private MetropolisesTableModel table;
    private void beforeEach(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test_3");
        dataSource.setUsername("root");
        dataSource.setPassword("123456789");

        table = new MetropolisesTableModel(dataSource);

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute("USE test_3");
            statement.execute("Delete from metropolises");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void testAddMetropolises(){
        beforeEach();
        table.add("Tbilisi", "Europe", "1000000");
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM metropolises");
            ArrayList<Metropolis> list = new ArrayList();
            while(rs.next()) {
                list.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(1, list.size());
            assertEquals("Tbilisi", list.get(0).get(0));
            assertEquals("Europe", list.get(0).get(1));
            assertEquals(1000000, list.get(0).get(2));

            table.add("Beijing", "Asia", "1000000000");
            rs = statement.executeQuery("SELECT * FROM metropolises");
            list.clear();
            while(rs.next()) {
                list.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(2, list.size());
            assertEquals("Beijing", list.get(1).get(0));
            assertEquals("Asia", list.get(1).get(1));
            assertEquals(1000000000, list.get(1).get(2));

            connection.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testSearchMetropolisesExactAndPartialMatch(){
        beforeEach();
        ArrayList<Metropolis> list;
        try {
            statement.execute("INSERT INTO metropolises VALUES\n" +
                    "\t(\"Mumbai\",\"Asia\",20400000),\n" +
                    "    (\"New York\",\"North America\",21295000),\n" +
                    "\t(\"San Francisco\",\"North America\",5780000),\n" +
                    "\t(\"London\",\"Europe\",8580000),\n" +
                    "\t(\"Rome\",\"Europe\",2715000),\n" +
                    "\t(\"Melbourne\",\"Australia\",3900000),\n" +
                    "\t(\"San Jose\",\"North America\",7354555),\n" +
                    "\t(\"Tokyo\",\"Asia\",12000000),\n" +
                    "\t(\"Buenos Aires\",\"South America\",17000000),\n" +
                    "\t(\"Rostov-on-Don\",\"Europe\",1052000);");


            //exact continent
            list = table.search("", "Europe", "",true, true);
            ResultSet rs = statement.executeQuery("SELECT * FROM metropolises WHERE continent = 'Europe';");
            ArrayList<Metropolis> queryList = new ArrayList<>();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }

            //partial match continent
            list = table.search("", "America", "",true, false);
            rs = statement.executeQuery("SELECT * FROM metropolises WHERE continent like '%America%';");
            queryList.clear();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void testSearchMetropolisesGreaterOrLess(){
        beforeEach();
        ArrayList<Metropolis> list;
        try {
            statement.execute("INSERT INTO metropolises VALUES\n" +
                    "\t(\"Mumbai\",\"Asia\",20400000),\n" +
                    "    (\"New York\",\"North America\",21295000),\n" +
                    "\t(\"San Francisco\",\"North America\",5780000),\n" +
                    "\t(\"London\",\"Europe\",8580000),\n" +
                    "\t(\"Rome\",\"Europe\",2715000),\n" +
                    "\t(\"Melbourne\",\"Australia\",3900000),\n" +
                    "\t(\"San Jose\",\"North America\",7354555),\n" +
                    "\t(\"Tokyo\",\"Asia\",12000000),\n" +
                    "\t(\"Buenos Aires\",\"South America\",17000000),\n" +
                    "\t(\"Rostov-on-Don\",\"Europe\",1052000);");


            //greater than population
            list = table.search("", "", "4000000",true, true);
            ResultSet rs = statement.executeQuery("SELECT * FROM metropolises WHERE population > 4000000;");
            ArrayList<Metropolis> queryList = new ArrayList<>();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }

            //less or equal to population
            list = table.search("", "", "10000000",false, true);
            rs = statement.executeQuery("SELECT * FROM metropolises WHERE population <= 10000000;");
            queryList.clear();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testSearchMetropolisesWildcardStrings(){
        beforeEach();
        ArrayList<Metropolis> list;
        try {
            statement.execute("INSERT INTO metropolises VALUES\n" +
                    "\t(\"Mumbai\",\"Asia\",20400000),\n" +
                    "    (\"New York\",\"North America\",21295000),\n" +
                    "\t(\"San Francisco\",\"North America\",5780000),\n" +
                    "\t(\"London\",\"Europe\",8580000),\n" +
                    "\t(\"Rome\",\"Europe\",2715000),\n" +
                    "\t(\"Melbourne\",\"Australia\",3900000),\n" +
                    "\t(\"San Jose\",\"North America\",7354555),\n" +
                    "\t(\"Tokyo\",\"Asia\",12000000),\n" +
                    "\t(\"Buenos Aires\",\"South America\",17000000),\n" +
                    "\t(\"Rostov-on-Don\",\"Europe\",1052000);");


            //name with wildcard characters with exact match
            list = table.search("%n%", "", "",true, true);
            ResultSet rs = statement.executeQuery("SELECT * FROM metropolises WHERE metropolis like '%n%';");
            ArrayList<Metropolis> queryList = new ArrayList<>();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }

            // name with wildcard characters with partial match
            list = table.search("%o%", "", "",true, false);
            rs = statement.executeQuery("SELECT * FROM metropolises WHERE metropolis like '%o%';");
            queryList.clear();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testSearchMetropolisesSeveralCriteria(){
        beforeEach();
        ArrayList<Metropolis> list;
        try {
            statement.execute("INSERT INTO metropolises VALUES\n" +
                    "\t(\"Mumbai\",\"Asia\",20400000),\n" +
                    "    (\"New York\",\"North America\",21295000),\n" +
                    "\t(\"San Francisco\",\"North America\",5780000),\n" +
                    "\t(\"London\",\"Europe\",8580000),\n" +
                    "\t(\"Rome\",\"Europe\",2715000),\n" +
                    "\t(\"Melbourne\",\"Australia\",3900000),\n" +
                    "\t(\"San Jose\",\"North America\",7354555),\n" +
                    "\t(\"Tokyo\",\"Asia\",12000000),\n" +
                    "\t(\"Buenos Aires\",\"South America\",17000000),\n" +
                    "\t(\"Rostov-on-Don\",\"Europe\",1052000);");


            //continent containing 'A' and population greater than 40000000
            list = table.search("S%", "", "6000000",false, true);
            ResultSet rs = statement.executeQuery("SELECT * FROM metropolises WHERE population <= 6000000 " +
                    "AND metropolis like 'S%';");
            ArrayList<Metropolis> queryList = new ArrayList<>();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }

            //less or equal to population
            list = table.search("", "", "10000000",false, true);
            rs = statement.executeQuery("SELECT * FROM metropolises WHERE population <= 10000000;");
            queryList.clear();
            while(rs.next()) {
                queryList.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            assertEquals(list.size(), queryList.size());
            for(int i = 0; i < list.size(); i ++){
                assertEquals( list.get(i).get(0), queryList.get(i).get(0));
                assertEquals( list.get(i).get(1), queryList.get(i).get(1));
                assertEquals( list.get(i).get(2), queryList.get(i).get(2));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
