package application.pinerria.Fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.pinerria.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQs extends Fragment {


    public FAQs() {
        // Required empty public constructor
    }
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_faqs, container, false);
        getActivity().setTitle("FAQ");

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        return view;
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("How is this site helpful to me?");
        listDataHeader.add("What is meant by “Home Business”?");
        listDataHeader.add("Who all can see my post or ad?");
        listDataHeader.add("Do I need to pay for every post or ad?");
        listDataHeader.add("How do I sell or buy any of the products/services through this platform?");
        listDataHeader.add("What if my item get sold and I want to stop display of my ad?");
        listDataHeader.add("How will I come to know that the listing of my ad has expired? ");
        listDataHeader.add("Does this platform support delivering of Goods/Services?");
        listDataHeader.add("Can I choose the duration for which my post will be visible to others?");
        listDataHeader.add("Where can I seek help regarding this platform, if needed?");


        // Adding child data
        List<String> top1 = new ArrayList<String>();
        top1.add("This platform helps you post an ad for any of your items which you want you sell off, be it a household item, electronic gadget, electrical appliance, vehicle or property. Similarly, it helps you buy such an items advertised by other users.\n" +
                "It also helps you post an ad for any Home Business run by you  e.g. Dance classes, Art classes, Academic coaching, Chocolate or Cake making etc.\n" +
                "You can select the area where your posted ad will be displayed and also the duration of display.\n");


        List<String> top2 = new ArrayList<String>();
        top2.add("Any activity carried on by the user from the premises of his/her home is classified under home business. It may comprise of Coaching classes, Art classes, Tax consultancy, Tailoring services, Vaastu, Reiki etc. ");


        List<String> top3 = new ArrayList<String>();
        top3.add("While posting your ad you would be able to select the area where your ad would be visible. It may be\n" +
                "-Your Residential complex (Society), \n" +
                "- Your Nearby Area e.g. within Gurugram, within Greater Noida  OR\n" +
                "- Your entire Zone e.g. Delhi/NCR, Bangalore, Kolkata, Hyderabad etc.\n");

        List<String> top4 = new ArrayList<String>();
                top4.add("No, whether a posting is chargeable or not is dependent on a combination of factors. The charge is displayed in the ‘Post Ad Screen’, while you fill up the details.");

        List<String> top5 = new ArrayList<String>();
                top5.add("The selling and buying processes have been explained in the ‘How it works’ tab on the ‘Home‘ screen. Please visit the same.");
        List<String> top6 = new ArrayList<String>();
                top6.add("If you want to stop display of your ads for a product or home business, please follow below mentioned steps:\n" +
                        "For product – Home screen >>My Account >> My ads for Home Business>> Approved.   Now tick the ‘Tag Sold’ checkbox, and the display of your ad will be stopped.\n" +
                        "For Home Business – Home screen >> My Account >> My ads for products >> Approved.   Now tick the ‘Stop Display’ checkbox, and the display of your ad will be stopped.\n");
        List<String> top7 = new ArrayList<String>();
                top7.add("The expiry date of your posted ad is visible to you on the posting itself. Please visit:Home screen >> My Account >>Dashboard to view the listing of your ads.  You may also receive email/ notifications from this platform before the expiry of the ad." +
                        "Anyone can post any project on our website, even if, it is amateur. The contact information is verified. We can even post questions to the client before the lead is purchased. \n");
        List<String> top8 = new ArrayList<String>();
                top8.add(": No, this is only a listing platform. The delivery of goods/ services has to be managed by the seller and buyer themselves.");
        List<String> top9 = new ArrayList<String>();
                top9.add("Yes, you can choose the duration of display (3 months / 6 months) while posting the ad.");
        List<String> top10 = new ArrayList<String>();
                top10.add("The platform is quite intuitive itself. For any clarity regarding the steps, you may visit ‘How it works’ section. Alternatively, you may seek help from the ‘Business enabler’ under whom you have been registered. ");

        listDataChild.put(listDataHeader.get(0), top1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), top2);
        listDataChild.put(listDataHeader.get(2), top3);
        listDataChild.put(listDataHeader.get(3), top4);
        listDataChild.put(listDataHeader.get(4), top5);
        listDataChild.put(listDataHeader.get(5), top6);
        listDataChild.put(listDataHeader.get(6), top7);
        listDataChild.put(listDataHeader.get(7), top8);
        listDataChild.put(listDataHeader.get(8), top9);
        listDataChild.put(listDataHeader.get(9), top10);


    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item2, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

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

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group2, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
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



}
