package com.example.duongthanhtin.myappapi.DataBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duongthanhtin.myappapi.R;

import java.util.List;

//public class ListAdapter extends ArrayAdapter<ListHistory>
//}
public class ListAdapter extends  ArrayAdapter<ListHistory>
{
    public ListAdapter(Context context, int textViewResourceId) {
            super(context,textViewResourceId);
    }
//}
   public  ListAdapter(Context context, int Resource, List<ListHistory> items)
   {
       super(context,Resource,items);
   }
   public View getView(int position, View convertView, ViewGroup parent)
   {
       View v = convertView;
       if(v == null)
       {
           LayoutInflater vi;
           vi = LayoutInflater.from(getContext());
           //v = vi.inflate(R.layout.activity_interface_row,null);
            v=vi.inflate(R.layout.activity_interface_row,null);
       }
       ListHistory lh = getItem(position);
       if(lh!=null)
       {
           TextView textName = (TextView)v.findViewById(R.id.idTextName);
           textName.setText(lh.Name);
           TextView textTime = (TextView)v.findViewById(R.id.idTextTime);
           textTime.setText(lh.Time);
           TextView textDate = (TextView)v.findViewById(R.id.idTextDay);
           textDate.setText(lh.Date);

           ImageView imagview = (ImageView) v.findViewById(R.id.imageView);
           Bitmap bitmap = BitmapFactory.decodeByteArray(lh.Image,0,lh.Image.length);
           imagview.setImageBitmap(bitmap);
       }
       return  v;
   }
}