package com.napchatalarms.napchatalarmsandroid.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;

import java.util.HashMap;
import java.util.List;

/**
 * The type Expandable list adapter.
 */
@SuppressWarnings("unused")
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final Context _context;
    private final List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private final HashMap<String, List<String>> _listDataChild;
    @SuppressWarnings("unused")
    private Drawable thunderId;

    /**
     * Instantiates a new Expandable list adapter.
     *
     * @param context        the context
     * @param listDataHeader the list data header
     * @param listChildData  the list child data
     */
    @SuppressWarnings("unused")
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
       Drawable softId = context.getDrawable(R.drawable.ic_rainbow);
       Drawable mildId = context.getDrawable(R.drawable.ic_rain);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //noinspection ConstantConditions
            convertView = inflater.inflate(R.layout.custom_tone_list_item, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.tone_list_item);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //noinspection ConstantConditions
            convertView = inflater.inflate(R.layout.custom_tone_list_group, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.lblListHeader);
        switch (groupPosition) {
            case 0:
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_rainbow, 0);
                break;
            case 1:
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_rain, 0);
                break;
            case 2:
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_thunderstorm, 0);
                break;
        }
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}