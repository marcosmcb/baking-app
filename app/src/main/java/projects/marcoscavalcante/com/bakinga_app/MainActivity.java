package projects.marcoscavalcante.com.bakinga_app;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import projects.marcoscavalcante.com.bakinga_app.adapters.RecipeAdapter;
import projects.marcoscavalcante.com.bakinga_app.models.Ingredient;
import projects.marcoscavalcante.com.bakinga_app.models.Recipe;
import projects.marcoscavalcante.com.bakinga_app.models.Step;
import projects.marcoscavalcante.com.bakinga_app.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>
{
    private ArrayList<Recipe> mRecipes;
    private RecyclerView mRecyclerViewRecipes;
    private RecipeAdapter mRecipeAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final int RECIPE_LOADER = 10;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipes = new ArrayList<Recipe>();
        mRecyclerViewRecipes = (RecyclerView) findViewById(R.id.rv_recipes);
        mRecipeAdapter = new RecipeAdapter(mRecipes);

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
        mRecipeAdapter.setmOnEntryClickListener(new RecipeAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position)
            {
                String titleRecipe = mRecipes.get(position).getName();
                Toast.makeText( MainActivity.this, titleRecipe  , Toast.LENGTH_LONG ).show();
            }
        });
    }


    private void setRecyclerView( )
    {
        mRecyclerViewRecipes.setLayoutManager( mLayoutManager );
        mRecyclerViewRecipes.setHasFixedSize(true);
        mRecyclerViewRecipes.setAdapter(mRecipeAdapter);
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


    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args)
    {
        return new AsyncTaskLoader<String>(this)
        {
            String mRecipesJson;

            @Override
            protected void onStartLoading()
            {
                Log.d( TAG, "Starting Loader");
                if(mRecipesJson != null)    deliverResult(mRecipesJson);
                else                        forceLoad();
            }

            @Override
            public String loadInBackground()
            {
                try
                {
                    return NetworkUtils.getResponseFromApi();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }


    @Override
    public void onLoadFinished(Loader<String> loader, String data)
    {
        if( data != null && !data.isEmpty() )
        {

            try
            {
                JSONArray mJsonArray = new JSONArray( data );

                for(int i=0; i < mJsonArray.length(); i++)
                {
                    JSONObject mRecipeJson = mJsonArray.getJSONObject(i);

                    JSONArray mIngredientsJson = mRecipeJson.getJSONArray("ingredients");
                    ArrayList<Ingredient> mIngredients = new ArrayList<Ingredient>();

                    for(int j=0; j < mIngredientsJson.length(); j++ )
                        mIngredients.add( new Ingredient( mIngredientsJson.getJSONObject(j) ) );

                    JSONArray mStepsJson = mRecipeJson.getJSONArray("steps");
                    ArrayList<Step> mSteps = new ArrayList<Step>();

                    for(int j=0; j < mStepsJson.length(); j++ )
                        mSteps.add( new Step( mStepsJson.getJSONObject(j) ) );

                    Recipe mRecipe = new Recipe( mRecipeJson );
                    mRecipe.setIngredients( mIngredients );
                    mRecipe.setSteps( mSteps );
                    mRecipes.add( mRecipe );
                }

                mRecipeAdapter.notifyDataSetChanged();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
