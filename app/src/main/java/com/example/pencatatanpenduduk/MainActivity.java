package com.example.pencatatanpenduduk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pencatatanpenduduk.Helpers.DBHelper;
import com.example.pencatatanpenduduk.model.Penduduk;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PendudukAdapter pendudukAdapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private ArrayList<Penduduk> penduduks;
    private ArrayList<Penduduk> penduduksCopy;
    private EditText search;
    private TextView searchNoDatas,noDataText1,noDataText2;
    private RelativeLayout emptyLayout;
    private boolean isSearch = false;
    private ImageView noDataVector;
    private RecyclerView.AdapterDataObserver dataObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.listPenduduk);
        recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        penduduks = new ArrayList<Penduduk>();
        searchNoDatas = findViewById(R.id.searchNoData);
        noDataText1 = findViewById(R.id.noDataText1);
        noDataText2 = findViewById(R.id.noDataText2);
        penduduksCopy = new ArrayList<>(penduduks);
        noDataVector = findViewById(R.id.vectorNoData);
        emptyLayout = (RelativeLayout) findViewById(R.id.emptyLayout);
        search = findViewById(R.id.searchEditText);

        //cek  data  menggunakan data observer
        dataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkisEmpty();
            }
        };

        //get all data sqlite
        Cursor cursor = new DBHelper(this).allData();
        while (cursor.moveToNext()){
            Penduduk obj = new Penduduk(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10));
            penduduks.add(obj);
        }


        pendudukAdapter = new PendudukAdapter(penduduks);
        pendudukAdapter.registerAdapterDataObserver(dataObserver);
        recyclerView.setAdapter(pendudukAdapter);
        checkisEmpty();


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    //search filter
    private  void filter(String newText){
        newText =   newText.toLowerCase();
        ArrayList<Penduduk> newPenduduk = new ArrayList<>();

        for (Penduduk penduduk : penduduks){
            String name = penduduk.getnamaLengkap().toLowerCase();
            if (name.contains(newText)){
                newPenduduk.add(penduduk);
                isSearch = true;
            }else{
                isSearch = false;
                searchNoDatas.setVisibility(View.VISIBLE);
            }
        }

        pendudukAdapter.filteredList(newPenduduk);
        pendudukAdapter.notifyDataSetChanged();
    }

    private  void checkisEmpty()
    {
        if (penduduks.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }

    //clearfocus Edittext on touch outside
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {

                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pendudukAdapter.unregisterAdapterDataObserver(dataObserver);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        pendudukAdapter.unregisterAdapterDataObserver(dataObserver);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "app Ter-resume", Toast.LENGTH_SHORT).show();
//    }

    //floating menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent intent = new Intent(this, AddPendudukActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                new AlertDialog.Builder(this)
                        .setTitle("About")
                        .setMessage("Nama : Afrizal Dwi Setiawan \n"+"Nim : 1905551162 \n"+"Judul Aplikasi : Pendaftaran E-sport")
                        .setPositiveButton("Tutup", null)
                        .setIcon(R.drawable.ic_baseline_info_24  )
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //ketika menuContext di tekan
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 101:
                pendudukAdapter.editItem(item.getGroupId());
                Snackbar.make(findViewById(R.id.coordinator),"Edited", Snackbar.LENGTH_SHORT).show();
                return  true;
            case 102:
                //alert Dialog
                new AlertDialog.Builder(this).setMessage("Apakah Anda Yakin Ingin Menghapus Data Ini?")
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pendudukAdapter.removeItem(item.getGroupId());
                                pendudukAdapter.notifyDataSetChanged();
                                recyclerView.setAdapter(pendudukAdapter);
                                Toast.makeText(MainActivity.this, "Data Dihapus", Toast.LENGTH_SHORT).show();

                                if (isSearch) {
                                    overridePendingTransition(0, 0);
                                    MainActivity.this.recreate();
                                    overridePendingTransition(0, 0);
                                }
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Snackbar.make(findViewById(R.id.coordinator),"Tindakan DIbatalakan", Snackbar.LENGTH_SHORT).show();
                            }
                        }).create().show();
                return  true;
        }

        return super.onContextItemSelected(item);

    }

}