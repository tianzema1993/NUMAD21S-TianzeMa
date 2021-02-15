package edu.neu.madcourse.numad21s_tianzema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

public class LinkActivity extends AppCompatActivity {
    private ArrayList<LinkCard> linkCardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        init(savedInstanceState);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                addItem(pos);
            }
        });

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Snackbar.make(viewHolder.itemView, "Delete an item", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                int position = viewHolder.getLayoutPosition();
                linkCardList.remove(position);

                linkAdapter.notifyItemRemoved(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {


        int size = linkCardList == null ? 0 : linkCardList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", linkCardList.get(i).getName());
            outState.putString(KEY_OF_INSTANCE + i + "1", linkCardList.get(i).getUrl());
            outState.putBoolean(KEY_OF_INSTANCE + i + "2", linkCardList.get(i).isEditable());
        }
        super.onSaveInstanceState(outState);

    }

    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {

        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (linkCardList == null || linkCardList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    boolean editable = savedInstanceState.getBoolean(KEY_OF_INSTANCE + i + "2");
                    LinkCard linkCard = new LinkCard(name, url, editable);
                    linkCardList.add(linkCard);
                }
            }
        }
    }

    private void createRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linkAdapter = new LinkAdapter(linkCardList);
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void addItemButtonClick(int position, String name, String url) {
                linkCardList.get(position).addItemButtonClick(position, name, url);
                linkAdapter.notifyItemChanged(position);
            }
        };
        linkAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(linkAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void addItem(int position) {
        linkCardList.add(position, new LinkCard());
        linkAdapter.notifyItemInserted(position);
    }
}