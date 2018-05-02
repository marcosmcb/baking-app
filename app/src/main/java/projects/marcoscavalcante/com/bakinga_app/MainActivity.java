package projects.marcoscavalcante.com.bakinga_app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import projects.marcoscavalcante.com.bakinga_app.adapters.RecipeAdapter;
import projects.marcoscavalcante.com.bakinga_app.models.Recipe;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>
{

    private ArrayList<Recipe> recipes;
    private RecyclerView mRecyclerViewRecipes;
    private RecipeAdapter mAdapterRecipes;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final int RECIPE_LOADER = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerViewRecipes = (RecyclerView) findViewById(R.id.rv_recipes);
        mAdapterRecipes = new RecipeAdapter( recipes );



        setLayoutManager( );
        setRecipeAdapterListener( );
        setRecyclerView();


        callLoader( );
    }

    private void setLayoutManager( )
    {
        mLayoutManager = new LinearLayoutManager(this);
    }


    private void setRecipeAdapterListener( )
    {
        mAdapterRecipes.setmOnEntryClickListener(new RecipeAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position)
            {
                String titleRecipe = recipes.get(position).getName();
                Toast.makeText( MainActivity.this, titleRecipe  , Toast.LENGTH_LONG ).show();
            }
        });
    }


    private void setRecyclerView( )
    {
        mRecyclerViewRecipes.setLayoutManager( mLayoutManager );
        mRecyclerViewRecipes.setHasFixedSize(true);
        mRecyclerViewRecipes.setAdapter( mAdapterRecipes );
    }


    private void callLoader( )
    {
        LoaderManager mLoaderManager = getSupportLoaderManager();
        Loader<String> recipeLoader  = mLoaderManager.getLoader( RECIPE_LOADER );

        if( recipeLoader == null )
        {
            mLoaderManager.initLoader( RECIPE_LOADER, null, this );
        }
        else
        {
            mLoaderManager.restartLoader( RECIPE_LOADER, null, this );
        }
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
