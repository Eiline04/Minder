package com.technovation.sagetech.minder.onBoardingSlider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.technovation.sagetech.minder.R;

import io.grpc.Contexts;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.family_picture,
            R.drawable.question_answer,
            R.drawable.video_tutorial
    };

    int headings[] = {
            R.string.firstSlideTitle,
            R.string.secondSlideTitle,
            R.string.thirdSlideTitle
    };

    int descriptions[] = {
            R.string.firstSlideDescription,
            R.string.secondSlideDescription,
            R.string.thirdSlideDescription
    };

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView = view.findViewById(R.id.familyImageOnboarding);
        TextView heading = view.findViewById(R.id.slidesTitle);
        TextView description = view.findViewById(R.id.slideDescription);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        description.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
