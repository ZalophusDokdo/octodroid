package com.mariogrip.octodroid.iu;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mariogrip.octodroid.R;
import com.mariogrip.octodroid.UpdateGraph;
import com.mariogrip.octodroid.memory;
import com.mariogrip.octodroid.util;
import com.mariogrip.octodroid.util_send;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by mariogrip on 01.12.14.
 */
public class temp_card extends Fragment {

    public temp_card() {
    }

    private static final String TAG = "CardListActivity";
    private ListView listView;
    private View rootView;
    private Integer setExt = 0;
    private Integer setBed = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.card_main, container, false);
        TextView oflline = (TextView) rootView.findViewById(R.id.textView_offline);
        if (memory.isServerUp()) {
            oflline.setText("");
            ProgressBar progresss = (ProgressBar) rootView.findViewById(R.id.progressBar);
            TextView texttimes = (TextView) rootView.findViewById(R.id.textView11_time);
            texttimes.setText(" " + util.toHumanRead(memory.job.progress.getPrintTimeLeft()));
            progresss.setProgress(util.getProgress());
        } else {
            oflline.setText("Offline");
        }
        ArrayList<Card> cards = new ArrayList<Card>();
        cardtest card = new cardtest(rootView.getContext(), "Heatbed", "HeatBed");

        cardtest2 card2 = new cardtest2(rootView.getContext(), "Extruder", "Extruder");

        cardtest3 card3 = new cardtest3(rootView.getContext());
        cards.add(card3);
        cards.add(card);
        cards.add(card2);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(rootView.getContext(), cards);
        mCardArrayAdapter.setInnerViewTypeCount(3);

        CardListView listView = (CardListView) rootView.findViewById(R.id.card_main_card_list);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }


        return rootView;
    }


    public class cardtest extends Card {

        protected String mTitleHeader;
        protected String mTitleMain;

        public cardtest(Context context, String titleHeader, String titleMain) {
            super(context, R.layout.card_bead);
            this.mTitleHeader = titleHeader;
            this.mTitleMain = titleMain;
            init();
        }

        private void init() {
            //  CardHeader header = new CardHeader(rootView.getContext());
            //header.setTitle(mTitleHeader);
            // addCardHeader(header);
            // setTitle(mTitleMain);
        }


        @Override
        public int getType() {
            return 0;
        }

        @Override
        public void setupInnerViewElements(final ViewGroup parent, View view) {
            try {
                TextView textbed = (TextView) parent.findViewById(R.id.textView_CurentTemp_bed_Tempcard);
                textbed.setText(memory.temp.current.getBed()[0] + "°C");
            } catch (NullPointerException v) {
            }
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
            String maxtempstring = prefs.getString("max_bed_heat", "100");
            Integer maxtemp = 100;
            try {
                maxtemp = Integer.parseInt(maxtempstring);
            } catch (Exception e) {

            }

            TextView max = (TextView) parent.findViewById(R.id.textView_max_bed);
            max.setText(maxtemp + "°C");
            SeekBar seekBar = (SeekBar) parent.findViewById(R.id.seekBar_bed);
            final Button setbutton = (Button) parent.findViewById(R.id.button_setBed);
            seekBar.setMax(maxtemp);
            int temp = (int) memory.temp.target.getBed()[0];
            if (temp > maxtemp) {
                temp = maxtemp;
            }
            seekBar.setProgress(temp);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                    progress = progresValue;
                    setBed = progress;
                    setbutton.setText("Set to " + setBed + "°C");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    setBed = progress;
                    setbutton.setText("Set to " + setBed + "°C");
                }
            });

            Button button2 = (Button) parent.findViewById(R.id.button_setBed);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    util_send.setBedTemp(setBed.toString());
                    Toast.makeText(parent.getContext(), "Temperature successfully set to " + setBed.toString() + "°C", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public class cardtest2 extends Card {

        protected String mTitleHeader;
        protected String mTitleMain;

        public cardtest2(Context context, String titleHeader, String titleMain) {
            super(context, R.layout.card_heat);
            this.mTitleHeader = titleHeader;
            this.mTitleMain = titleMain;
            init();
        }

        private void init() {
            // CardHeader header = new CardHeader(rootView.getContext());
            //header.setTitle(mTitleHeader);
            // addCardHeader(header);
            // setTitle(mTitleMain);
        }


        @Override
        public int getType() {
            //Very important with different inner layouts
            return 1;
        }

        @Override
        public void setupInnerViewElements(final ViewGroup parent, View view) {
            try {
                TextView textext = (TextView) parent.findViewById(R.id.textView_CurentTemp_ext_TempCard);
                textext.setText(memory.temp.current.getExt()[0] + "°C");
            } catch (NullPointerException v) {
                util.logD("NOOOOO! " + v.toString());
            }

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
            String maxtempstring = prefs.getString("max_ext_heat", "300");
            Integer maxtemp = 300;
            try {
                maxtemp = Integer.parseInt(maxtempstring);
            } catch (Exception e) {

            }

            TextView max = (TextView) parent.findViewById(R.id.textView_max_ext);
            max.setText(maxtemp + "°C");
            SeekBar seekBar = (SeekBar) parent.findViewById(R.id.seekBar_ext);
            seekBar.setMax(maxtemp);
            final Button setbutton = (Button) parent.findViewById(R.id.button_ext);
            Integer temp = (int) memory.temp.target.getExt()[0];
            if (temp > maxtemp) {
                temp = maxtemp;
            }
            seekBar.setProgress(temp);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                    progress = progresValue;
                    setExt = progress;
                    setbutton.setText("Set to " + setExt + "°C");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    setExt = progress;
                    setbutton.setText("Set to " + setExt + "°C");
                }
            });
            setbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    util_send.setExtTemp(setExt.toString());
                    Toast.makeText(parent.getContext(), "Temperature successfully set to " + setExt.toString() + "°C", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class cardtest3 extends Card {

        public cardtest3(Context context) {
            super(context, R.layout.card_graph);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            LineChart chart = (LineChart) parent.findViewById(R.id.chart);



            UpdateGraph.setupChart(chart, UpdateGraph.generateData(), getResources().getColor(R.color.graph_backGround));

        }

        @Override
        public int getType() {
            return 0;
        }


    }


    public class MyCardArrayAdapter extends CardArrayAdapter {

        /**
         * Constructor
         *
         * @param context The current context.
         * @param cards   The cards to represent in the ListView.
         */
        public MyCardArrayAdapter(Context context, List<Card> cards) {
            super(context, cards);
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }




}
