package com.example.acerpc.bucketdrop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.acerpc.bucketdrop.adapters.CompletedListener;

import static android.content.ContentValues.TAG;

/**
 * Created by AcerPC on 11/19/2016.
 */

public class DialogMark extends DialogFragment {

    Button btnComplete;
    ImageButton closeComplete;
    CompletedListener myCompletedListener;

    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_complete:
                    markAsComplete();
                    break;
            }
            dismiss();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    private void markAsComplete() {
        Bundle arguments = getArguments();
        if (myCompletedListener!=null && arguments!=null) {
            int position = arguments.getInt("POSITION");
            Log.d(TAG, "onViewCreated: " + position);
            myCompletedListener.onComplete(position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnComplete = (Button) view.findViewById(R.id.btn_complete);
        closeComplete = (ImageButton) view.findViewById(R.id.btn_close_complete_dialog);
        btnComplete.setOnClickListener(myClickListener);

    }

    public void setCompletedListener(CompletedListener completedListener) {
        this.myCompletedListener = completedListener;
    }
}
