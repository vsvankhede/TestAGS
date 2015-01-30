package info.ags.tabsswipe;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

public class AddFragment extends Fragment {
    private static int LOAD_IMAGE_RESULTS = 1;
    Button btn_img,btn_add;
    EditText et_title, et_desc;
    private TimePicker timePicker1;
    private DatePicker datePicker;



    private String imagePath = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        btn_img = (Button)rootView.findViewById(R.id.btn_img);
        btn_add = (Button)rootView.findViewById(R.id.btn_save);

        et_title = (EditText)rootView.findViewById(R.id.et_title);
        et_desc = (EditText)rootView.findViewById(R.id.et_desc);

        timePicker1 = (TimePicker) rootView.findViewById(R.id.timePicker);
        datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);

        // Click Listener event for image gallery
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                getActivity().startActivityForResult(i, LOAD_IMAGE_RESULTS);
            }
        });
        // Click Listner event for save data
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString();
                String desc = et_desc.getText().toString();

                int hour = timePicker1.getCurrentHour();
                int min = timePicker1.getCurrentMinute();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                String dt_time = hour+":"+min;
                String dt_date = year+"/"+month+"/"+day;
                MyDatabase md = new MyDatabase(getActivity());
                md.setApnt(title,desc,dt_date,dt_time,getImagePath());

               }
        });



		return rootView;
	}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS ) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            setImagePath(cursor.getString(cursor.getColumnIndex(filePath[0])));

            imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            System.out.println(imagePath);
            // Now we need to set the GUI ImageView data with data read from the picked file.
            //image.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
