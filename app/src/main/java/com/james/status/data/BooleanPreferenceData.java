package com.james.status.data;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.james.status.R;
import com.james.status.utils.PreferenceUtils;

public class BooleanPreferenceData extends ItemData {

    public boolean value;
    private OnChangeListener listener;

    public BooleanPreferenceData(Context context, Identifier identifier, boolean defaultValue, OnChangeListener listener) {
        super(context, identifier);

        Boolean value = PreferenceUtils.getBooleanPreference(getContext(), identifier.getPreference());
        if (value == null) value = defaultValue;
        this.value = value;

        this.listener = listener;
    }

    public static ViewHolder getViewHolder(Context context) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_preference_boolean, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Identifier identifier = getIdentifier();

        SwitchCompat title = (SwitchCompat) holder.v.findViewById(R.id.title);
        TextView subtitle = (TextView) holder.v.findViewById(R.id.subtitle);

        title.setText(identifier.getTitle());
        title.setOnCheckedChangeListener(null);
        title.setChecked(value);
        title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                value = b;
                PreferenceUtils.putPreference(getContext(), getIdentifier().getPreference(), b);
                if (listener != null) listener.onPreferenceChange(b);
            }
        });

        subtitle.setText(identifier.getSubtitle());
    }

    public interface OnChangeListener {
        void onPreferenceChange(boolean preference);
    }
}
