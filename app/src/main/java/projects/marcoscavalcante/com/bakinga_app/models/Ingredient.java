package projects.marcoscavalcante.com.bakinga_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient implements Parcelable
{
    private Double quantity;
    private String measure;
    private String name;

    public Ingredient( JSONObject ingredient ) throws JSONException
    {
        this.quantity = ingredient.getDouble( "quantity" );
        this.measure  = ingredient.getString( "measure" );
        this.name     = ingredient.getString( "ingredient" );
    }

    public Ingredient(Double quantity, String measure, String name)
    {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }


    protected Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        name = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(name);
    }
}
