import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


val Context.dataStore by preferencesDataStore(name = "search_preferences")


val RECENT_SEARCHES_KEY = stringSetPreferencesKey("recent_searches")

// add a recent search term to
suspend fun addRecentSearch(context: Context, searchTerm: String) {
    context.dataStore.edit { preferences ->
        val recentSearches = preferences[RECENT_SEARCHES_KEY]?.toMutableSet() ?: mutableSetOf()

        if (recentSearches.add(searchTerm)) {
            if (recentSearches.size > 10) {
                recentSearches.remove(recentSearches.first())
            }
            preferences[RECENT_SEARCHES_KEY] = recentSearches
        }
    }
}


fun getRecentSearches(context: Context): Flow<Set<String>> {
    return context.dataStore.data.map { preferences ->
        preferences[RECENT_SEARCHES_KEY] ?: mutableSetOf()
    }
}
