package ddwucom.mobile.finalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Movie> movieList;
    private LayoutInflater layoutInflater;

    MovieDBHelper movieDBHelper;

    public MovieAdapter(Context context, int layout, ArrayList<Movie> movieList) {
        this.context = context;
        this.layout = layout;
        this.movieList = movieList;

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movieList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDirector = convertView.findViewById(R.id.tvDirector);
            viewHolder.tvGrade = convertView.findViewById(R.id.tvGrade);
            viewHolder.tvId = convertView.findViewById(R.id.tvId);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvId.setText(Integer.toString((int) movieList.get(pos).get_id()));
        viewHolder.tvTitle.setText(movieList.get(pos).getTitle());
        viewHolder.tvDirector.setText(movieList.get(pos).getDirector());
        viewHolder.tvGrade.setText(Double.toString(movieList.get(pos).getGrade()));

        return convertView;
    }


    static class ViewHolder {
        TextView tvTitle;
        TextView tvDirector;
        TextView tvGrade;
        TextView tvId;
    }
}
