package com.taylorngo.shoppinglist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class EditItemDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextDesc;
    private Button saveButton;
    private CheckBox purchasedBox;
    private Spinner editTextCategory;
    private EditItemDialog.EditItemDialogListener listener;
    private long itemId;
    private ListItem item;

    public EditItemDialog(){}

    @SuppressLint("ValidFragment")
    public EditItemDialog(long id, ListItem item){
        super();
        this.itemId = id;
        this.item = item;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_item_dialog, null);
        builder.setView(view).setTitle("Edit Item");

        editTextName = view.findViewById(R.id.nameEdit);
        editTextName.setText(item.getName());

        editTextPrice = view.findViewById(R.id.priceEdit);
        editTextPrice.setText(String.valueOf(item.getPrice()));

        editTextDesc = view.findViewById(R.id.descriptionEdit);
        editTextDesc.setText(item.getDescription());

        purchasedBox = view.findViewById(R.id.purchasedEdit);
        purchasedBox.setChecked(item.isPurchased());

        saveButton = view.findViewById(R.id.saveEditButton);

        String[] categories = {"Animal", "Clothing", "Food", "Technology"};
        editTextCategory = view.findViewById(R.id.categoryEdit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextCategory.setAdapter(adapter);

        ArrayList<String> categories2 = new ArrayList<>(Arrays.asList(categories));
        editTextCategory.setSelection(categories2.indexOf(item.getCategory()));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String price = editTextPrice.getText().toString();
                String desc = editTextDesc.getText().toString();
                String category = editTextCategory.getSelectedItem().toString();
                boolean purchased = purchasedBox.isChecked();
                listener.applyTexts(name, price, desc, purchased, category, itemId);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (EditItemDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement dialog listener");
        }
    }

    public interface EditItemDialogListener {
        void applyTexts(String name, String price, String desc, boolean purchased, String category, long itemId);
    }
}
