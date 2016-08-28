package example.com.androidcontact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class AppAdapter extends BaseAdapter {

    private List<AppContact> contacts;
    LayoutInflater inflater;

    public AppAdapter(List<AppContact> contacts, Context context) {
        this.contacts = contacts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void refreshList(){
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public AppContact getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contacts.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_contact, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView.findViewById(R.id.number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppContact appContact = getItem(position);
        holder.name.setText(appContact.getName());
        holder.number.setText("" + appContact.getNumber());


        return convertView;
    }


    private class ViewHolder {
        TextView name;
        TextView number;
    }
}
