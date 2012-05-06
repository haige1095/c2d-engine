package info.u250.c2d.tests.android;

import info.u250.c2d.C2dTests;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidTestStarter extends ListActivity {

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<String> testNames = new ArrayList<String>();
		for (String name : C2dTests.getNames()) {
			testNames.add(name);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testNames.toArray(new String[0])));
	}

	protected void onListItemClick (ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Object o = this.getListAdapter().getItem(position);
		String testName = o.toString();

		Bundle bundle = new Bundle();
		bundle.putString("test", testName);
		Intent intent = new Intent(this, C2dTestActivity.class);
		intent.putExtras(bundle);

		startActivity(intent);
	}

}
