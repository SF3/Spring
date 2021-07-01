package exercise.finish.part3;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class FlightsTableModel extends AbstractTableModel {
    private static SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat formatter2 = new SimpleDateFormat("HH.mm a");

    private String[] columnNames;
    private List<Flight> data;

    public FlightsTableModel() {
        super();
        columnNames = new String[]{"Origin", "Destination", "Date", "Time"};
        data = new ArrayList<Flight>();
    }

    public FlightsTableModel(List<Flight> extra) {
        this();
        data.addAll(extra);
    }

    public void addFlight(Flight flight) {
        data.add(flight);
        int index = data.size() - 1;
        fireTableRowsInserted(index, index);
    }

    public void addFlights(List<Flight> flights) {
        int start = data.size();
        data.addAll(flights);
        int end = data.size() - 1;
        fireTableRowsInserted(start, end);
    }

    public Flight getFlight(int row) {
        return data.get(row);
    }

    public String getColumnName(int index) {
        return columnNames[index];
    }

    public int getColumnCount() {
        return 4;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getValueAt(int x, int y) {
        Flight f = data.get(x);
        switch (y) {
            case 0:
                return f.getOrigin();
            case 1:
                return f.getDestination();
            case 2:
                return formatter1.format(f.getDeparture());
            default:
                return formatter2.format(f.getDeparture());
        }
    }
}
