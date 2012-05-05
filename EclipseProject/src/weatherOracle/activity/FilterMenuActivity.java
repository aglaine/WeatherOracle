package weatherOracle.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class FilterMenuActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = (TabHost) findViewById(R.layout.main);
        v.setVisibility(View.GONE);
        setContentView(R.layout.filter_menu_activity);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ConditionRuleViewerActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("notifications").setIndicator("Notifications",
                          res.getDrawable(R.drawable.ic_tab_weather))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, TimeRuleViewerActivity.class);
        spec = tabHost.newTabSpec("filters").setIndicator("Filters",
                          res.getDrawable(R.drawable.ic_tab_clock))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, FilterOptionsActivity.class);
        spec = tabHost.newTabSpec("tools").setIndicator("Tools",
                          res.getDrawable(R.drawable.ic_tab_tools))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(2);
    }
}