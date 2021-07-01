package exercise.finish.part4;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class FlightsListModel implements ListModel<String> {
    private List<String> items;
    private List<ListDataListener> listeners;

    public FlightsListModel(List<String> items) {
        super();
        this.items = new ArrayList<String>();
        this.items.addAll(items);
        listeners = new ArrayList<ListDataListener>();
    }

    public void addListDataListener(ListDataListener param) {
        listeners.add(param);
    }

    public String getElementAt(int index) {
        return items.get(index);
    }

    public int getSize() {
        return items.size();
    }

    public void removeListDataListener(ListDataListener param) {
        listeners.remove(param);
    }
}
