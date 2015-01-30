package info.ags.tabsswipe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragm extends ListFragment {

    private Cursor employees;
    private MyDatabase db;
    SimpleCursorAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_list, container, false);
		return rootView;
	}
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        db = new MyDatabase(getActivity());
        employees = db.getApnt();

        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.row_list,
                employees,
                new String[] {"title","desc","time","date","pics"},
                new int[] {R.id.item_title, R.id.item_desc,R.id.item_time,R.id.item_date,R.id.item_img});

        getListView().setAdapter(adapter);
        adapter.notifyDataSetChanged();

        super.onActivityCreated(savedInstanceState);
    }
}
