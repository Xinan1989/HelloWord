package com.tjnet.helloword;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity  extends AppCompatActivity {
    final private static String TAG = "SecondActivity";

    ListView listView;
    TextView textView;
    File currentParent;
    File[] currentFiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.path);

        Log.e(TAG, Environment.getExternalStorageDirectory().getPath());
        File root = new File("/mnt/");

        if (root.exists()) {
            currentParent = root;
            currentFiles = root.listFiles();
            inflateListView(currentFiles);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentFiles[position].isFile()) {
                    return;
                }

                File[] tmp = currentFiles[position].listFiles();
                if (tmp == null || tmp.length == 0) {
                    Toast.makeText(SecondActivity.this, "Current path is null.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    currentParent = currentFiles[position];
                    currentFiles = tmp;
                    inflateListView(currentFiles);
                }
            }
        });

        Button parent = (Button) findViewById(R.id.parent);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!currentParent.getCanonicalPath().equals("/mnt/")) {
                        currentParent = currentParent.getParentFile();
                        currentFiles = currentParent.listFiles();
                        inflateListView(currentFiles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void inflateListView(File[] files) {

        if (files == null || files.length <= 0) {
            return;
        }

        List<Map<String, Object>> listItems = new ArrayList<>();

        for (File file : files) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("icon", file.isDirectory() ? R.drawable.icon_fodler : R.drawable.icon_file);
            listItem.put("fileName", file.getName());
            listItems.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.line,
                new String[]{"icon", "fileName"}, new int[]{R.id.icon, R.id.file_name});

        listView.setAdapter(simpleAdapter);

        try {
            textView.setText(String.format("Current Path: %s", currentParent.getCanonicalPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
