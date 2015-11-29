package swe574.boun.edu.androidproject.message.model;


import android.graphics.Bitmap;

public final class Person {
    private final String mFullName;
    private final String mDescription;
    private final Bitmap mImage;

    public Person(String mFullName, String mDescription, Bitmap mImage) {
        this.mFullName = mFullName;
        this.mDescription = mDescription;
        this.mImage = mImage;
    }

    public Person() {
        this("", "", null);
    }

    public String getmFullName() {
        return mFullName;
    }

    public Bitmap getmImage() {
        Bitmap image = mImage.copy(mImage.getConfig(), false);
        return image;
    }

    public String getmDescription() {
        return mDescription;
    }

}
