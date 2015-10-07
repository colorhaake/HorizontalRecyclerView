package joseph.example.com.horizontalrecyclerview.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import joseph.example.com.horizontalrecyclerview.R;
import joseph.example.com.horizontalrecyclerview.adapter.DateListAdapter;
import joseph.example.com.horizontalrecyclerview.fragment.FragmentView;
import joseph.example.com.horizontalrecyclerview.layout.WCLinearLayoutManager;
import joseph.example.com.horizontalrecyclerview.listener.RecyclerItemClickListener;
import joseph.example.com.horizontalrecyclerview.util.DateUtil;

public class MainActivity extends Activity implements FragmentView.OnFragmentInteractionListener{

    private final int AROUND_DATE_RANGE = 3;

    private List<Date> around_current_dates;
    private List<FragmentView> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        setupView();
    }

    private void initData() {
        fragmentList = new ArrayList<FragmentView>();
    }

    private void setupView() {
        around_current_dates = getAroundCurrentDates(AROUND_DATE_RANGE);

        addDateView(around_current_dates);

        onClickDate(around_current_dates.get(around_current_dates.size() / 2));
    }

    private List getAroundCurrentDates(final int around_date_range) {
        List<Date> dateList = new ArrayList<Date>();

        Date current = DateUtil.getCurrentDate();
        for (int i = -around_date_range; i < around_date_range+1; i++) {
            dateList.add(DateUtil.addDay(current, i));
        }

        return dateList;
    }

    private void addDateView(List<Date> date_list) {
        DateListAdapter adapter = new DateListAdapter(date_list);

        WCLinearLayoutManager layoutManager = new WCLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(date_list.size() / 2 + 1);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.horizontal_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, onDateClickListener));
    }

    private RecyclerItemClickListener.OnItemClickListener onDateClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LinearLayout layout = (LinearLayout)view;
            Date date = (Date)layout.getChildAt(0).getTag();
            onClickDate(date);
        }
    };

    private void onClickDate(Date date) {
        FragmentView fragmentView = (FragmentView)getFragmentManager().findFragmentById(R.id.fragment_view);
        if (fragmentView == null || fragmentView.getDate() == null || !fragmentView.getDate().equals(date)) {
            FragmentView cache = findFragmentCache(date);

            fragmentView = (cache != null)? cache : FragmentView.newInstance(date);
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fragmentView);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private FragmentView findFragmentCache(Date date) {
        for (FragmentView fragment : fragmentList) {
            if (fragment.getDate().equals(date)) {
                return fragment;
            }
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
