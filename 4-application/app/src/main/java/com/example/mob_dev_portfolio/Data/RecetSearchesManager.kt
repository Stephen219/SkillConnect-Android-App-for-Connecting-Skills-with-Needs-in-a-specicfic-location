import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore to store recent searches
val Context.dataStore by preferencesDataStore(name = "search_preferences")

// key to store recent searches
val RECENT_SEARCHES_KEY = stringSetPreferencesKey("recent_searches")

// add a recent search term to

/**
 * Add a recent search term to the data store
 * @param context the context
 * @param searchTerm the search term to add
 */
suspend fun addRecentSearch(context: Context, searchTerm: String) {
    context.dataStore.edit { preferences ->
        val recentSearches = preferences[RECENT_SEARCHES_KEY]?.toMutableSet() ?: mutableSetOf()

        if (recentSearches.add(searchTerm)) {
            // keep only the last 10 recent searches
            if (recentSearches.size > 19) {
                recentSearches.remove(recentSearches.first())
            }
            preferences[RECENT_SEARCHES_KEY] = recentSearches
        }
    }
}

/**
 * Get the recent searches from the data store
 * @param context the context
 * @return a flow of the recent searches set meaning no duplicate searches
 */

fun getRecentSearches(context: Context): Flow<Set<String>> {
    return context.dataStore.data.map { preferences ->
        preferences[RECENT_SEARCHES_KEY] ?: mutableSetOf()
    }
}
