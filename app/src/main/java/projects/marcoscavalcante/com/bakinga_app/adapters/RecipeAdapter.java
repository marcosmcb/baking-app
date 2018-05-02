package projects.marcoscavalcante.com.bakinga_app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.annotation.Target;
import java.util.ArrayList;

import projects.marcoscavalcante.com.bakinga_app.R;
import projects.marcoscavalcante.com.bakinga_app.models.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>
{
    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private ArrayList<Recipe> mRecipesDataset;
    private int viewHolderCount;
    private OnEntryClickListener mOnEntryClickListener;

    public interface OnEntryClickListener
    {
        void onEntryClick( View view, int position );
    }

    public void setmOnEntryClickListener(OnEntryClickListener onEntryClickListener)
    {
        mOnEntryClickListener = onEntryClickListener;
    }

    public RecipeAdapter( ArrayList<Recipe> recipes )
    {
        this.mRecipesDataset = recipes;
        viewHolderCount = 0;
    }

    @Override
    public int getItemCount()
    {
        return mRecipesDataset != null ? mRecipesDataset.size() : 0;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context mContext = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_view_item;
        LayoutInflater inflater = LayoutInflater.from( mContext );
        boolean shouldAttachToParentImmediately = false;

        viewHolderCount++;

        View view = inflater.inflate( layoutIdForListItem, parent, shouldAttachToParentImmediately );

        return new RecipeViewHolder( view );
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position)
    {
        Recipe mRecipe   = mRecipesDataset.get( position );
        Context mContext = holder.itemView.getContext();
        View mView       = holder.itemView;

        ImageView mImageView = mView.findViewById( R.id.ivRecipePicture );
        TextView mTextView   = mView.findViewById( R.id.tvRecipeTitle );

    }




    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView mRecipeImage;

        public RecipeViewHolder(View itemView)
        {
            super( itemView );
            itemView.setOnClickListener( this );
            mRecipeImage = itemView.findViewById( R.id.ivRecipePicture );
        }

        @Override
        public void onClick(View v)
        {
            if( mOnEntryClickListener != null )
            {
                mOnEntryClickListener.onEntryClick( v, getLayoutPosition() );
            }
        }
    }
}
