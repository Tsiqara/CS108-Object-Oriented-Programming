import com.mysql.cj.x.protobuf.MysqlxExpr;
import com.sun.corba.se.spi.monitoring.StatisticMonitoredAttribute;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;


public class MetropolisesTableModel extends AbstractTableModel {
    static BasicDataSource dataSource;
    private final int colNum = 3;
    private ArrayList<Metropolis> list = new ArrayList<>();
    private String[] header = {"Metropolis", "Continent", "Population"};

    public MetropolisesTableModel(BasicDataSource source) {
        dataSource = source;
    }

    /**
     * Adds Metropolis, Continent, and Population as entered in the text boxes and enter them into the metropolises database.
     * When an add operation is performed, table is reseted to display the newly added entry only.
     * Do not worry about the possibility of duplicate entries.
     *
     * @param name Metropolis name from metropolisTextField
     * @param continent  Metropolis continent from continentTextField
     * @param population Metropolis population from populationTextField
     */
    public void add(String name, String continent, String population){
        if(name.isEmpty() || continent.isEmpty() || population.isEmpty()){
            return;
        }
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO metropolises VALUES ('" + name + "', '" + continent + "', '" + population + "');";
            list.clear();
            list.add(new Metropolis(name, continent, Integer.parseInt(population)));
            statement.execute(query);
            fireTableDataChanged();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for data in database based on passed search criteria.
     * If name, continent and population are empty, we will assume that the user does not care about that particular criterion.
     * If all fields are left blank, display all data in the database.
     * greaterThan determines if we want metropolitan areas with populations greater or smaller than the number entered in the Population Text Field.
     * It is ignored if the Population field is left empty.
     * exactMatch allows the user to determine if the Metropolis and Continent text fields contain substrings we are searching for or exact matches.
     * For example if this is set to "Exact Match" and the Continent text field contains "North" no matches will be found,
     * whereas if it iss set to "Partial Match" metropolitan areas in North America will be listed.
     * Regardless of the Match Type users are allowed to enter in SQL regular expressions (e.g., "North%").
     * Search results are displayed in Table.
     *
     * @param name Metropolis name from metropolisTextField
     * @param continent Metropolis continent from continentTextField
     * @param population Metropolis population from populationTextField
     * @param greaterThan boolean based on value of populationPulldown.
     *                    True is it's "Population larger than", false if "Population smaller than or equal to"
     * @param exactMatch boolean based on value of matchTypePulldown.
     *                   True if "Exact Match", false if "Partial match".
     */
    public ArrayList<Metropolis> search(String name, String continent, String population,boolean greaterThan, boolean exactMatch){
        ArrayList<Metropolis> res = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM METROPOLISES \n";
            boolean where = false;
            if(name.isEmpty() || continent.isEmpty() || population.isEmpty()) {
                statement.execute("SELECT * FROM METROPOLISES;");
                fireTableDataChanged();
            }
            if(exactMatch){
                if(!name.isEmpty()){
                    if(name.contains("%") || name.contains("_")){
                        query +=  "WHERE METROPOLISES.METROPOLIS LIKE '" + name+ "'\n";
                    }else {
                        query += "WHERE METROPOLISES.METROPOLIS = '" + name + "'\n";
                    }
                    where = true;
                }
                if(!continent.isEmpty()){
                    if(!where){
                        if(continent.contains("%") || continent.contains("_")){
                            query +=  "WHERE METROPOLISES.CONTINENT LIKE '" + continent+ "'\n";
                        }else {
                            query += "WHERE METROPOLISES.CONTINENT = '" + continent + "'\n";
                        }
                        where = true;
                    }else {
                        if(continent.contains("%") || continent.contains("_")){
                            query +=  "AND METROPOLISES.CONTINENT LIKE '" + continent+ "'\n";
                        }else {
                            query += "AND METROPOLISES.CONTINENT = '" + continent + "'\n";
                        }
                    }
                }
            }else{
                if(!name.isEmpty()){
                    if(name.contains("%") || name.contains("_")){
                        query +=  "WHERE METROPOLISES.METROPOLIS LIKE '" + name+ "'\n";
                    }else {
                        query += "WHERE METROPOLISES.METROPOLIS like '%" + name + "%"+ "'\n";
                    }
                    where = true;
                }
                if(!continent.isEmpty()){
                    if(!where){
                        if(continent.contains("%") || continent.contains("_")){
                            query +=  "WHERE METROPOLISES.METROPOLIS LIKE '" + continent+ "'\n";
                        }else {
                            query += "WHERE METROPOLISES.CONTINENT like '%" + continent + "%'" + "\n";
                        }
                        where = true;
                    }else {
                        if(continent.contains("%") || continent.contains("_")){
                            query +=  "AND METROPOLISES.METROPOLIS LIKE '" + continent+ "'\n";
                        }else {
                            query += "AND METROPOLISES.CONTINENT like '%" + continent + "%'" + "\n";
                        }
                    }
                }
            }

            if(!population.isEmpty()){
                if(greaterThan) {
                    if (!where) {
                        query += "WHERE metropolises.population > " + Integer.parseInt(population) + "\n";
                        where = true;
                    } else {
                        query += "AND metropolises.population > " + Integer.parseInt(population) + "\n";
                    }
                }else{
                    if (!where) {
                        query += "WHERE metropolises.population <= " + Integer.parseInt(population) + "\n";
                        where = true;
                    } else {
                        query += "AND metropolises.population <= " + Integer.parseInt(population) + "\n";
                    }
                }
            }

            query += ";";
//            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            list.clear();
            while (rs.next()){
                list.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
                res.add(new Metropolis(rs.getString(1),  rs.getString(2), Integer.parseInt(rs.getString(3))));
            }
            fireTableDataChanged();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Returns the number of rows in the model.
     * A JTable uses this method to determine how many rows it should display.
     * This method should be quick, as it is called frequently during rendering.
     * @return the number of rows in the model
     */
    @Override
    public int getRowCount() {
        return list.size();
    }

    /**
     * Returns the number of columns in the model.
     * A JTable uses this method to determine how many columns it should create and display by default.
     * @return  the number of columns in the model
     */
    @Override
    public int getColumnCount() {
        return colNum;
    }

    /**
     * Returns the value for the cell at columnIndex and rowIndex.
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return
     * the value Object at the specified cell
     * String or int (Metropolis name, continent or population)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return list.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int column){
        return header[column];

    }
}
