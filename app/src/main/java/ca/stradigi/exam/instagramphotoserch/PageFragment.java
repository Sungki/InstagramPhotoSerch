package ca.stradigi.exam.instagramphotoserch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageFragment extends Fragment {
    private int mPageNumber;
    private int mPageTotal;
    private String mUrl;

    public static PageFragment create(int total, int pageNumber, String url) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        args.putInt("total", total);
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt("page");
        mPageTotal = getArguments().getInt("total");
        mUrl = getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page, container, false);
        ((TextView) rootView.findViewById(R.id.number)).setText(mPageNumber+1 + " / " + mPageTotal);

        ZoomImageView imageView = (ZoomImageView) rootView.findViewById(R.id.full_image_view);
        PhotoDownloader task = new PhotoDownloader(imageView);
        if (!task.searchPhoto(mUrl))
            task.execute(mUrl);

        return rootView;
    }
}
