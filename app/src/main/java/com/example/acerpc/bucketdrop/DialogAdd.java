package com.example.acerpc.bucketdrop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.acerpc.bucketdrop.beans.BucketPickerView;
import com.example.acerpc.bucketdrop.beans.Drop;

import io.realm.Realm;

/**
 * Created by AcerPC on 11/8/2016.
 */

public class DialogAdd extends DialogFragment implements View.OnClickListener {

    Button add;
    ImageButton close;
    EditText dropName;
    BucketPickerView myBucketPicker;
    public DialogAdd(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_drop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add = (Button) view.findViewById(R.id.btn_add_drop);
        close = (ImageButton) view.findViewById(R.id.btn_close_dialog);
        dropName = (EditText) view.findViewById(R.id.drop_name);
        myBucketPicker = (BucketPickerView) view.findViewById(R.id.date_picker);
        close.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == close.getId()){
            dismiss();
        }else if (id == add.getId()){
            addDrop();
            System.out.println("drop added to real db");
            dismiss();
        }
    }

    private void addDrop() {
        long timeCreated = System.currentTimeMillis();
        Realm realm = Realm.getDefaultInstance();
        Drop myDrop = new Drop(false, dropName.getText().toString(), myBucketPicker.getTime(), timeCreated);

        realm.beginTransaction();
        realm.copyToRealm(myDrop);
        realm.commitTransaction();
    }
}
